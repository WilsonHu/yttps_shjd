package com.eservice.iot.web;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiUserListResponse;
import com.eservice.iot.core.Result;
import com.eservice.iot.core.ResultGenerator;
import com.eservice.iot.model.*;
import com.eservice.iot.model.user.User;
import com.eservice.iot.service.ResponseCode;
import com.eservice.iot.service.TokenService;
import com.eservice.iot.service.impl.UserServiceImpl;
import com.eservice.iot.util.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* Class Description: xxx
* @author Wilson Hu
* @date 2018/08/21.
*/
@RestController
@RequestMapping("/")
public class ReportController {
    /**
     * Token
     */
    private String token;
    /**
     * 刷卡记录列表
     */
    private ArrayList<WinVisitorRecord> recordList = new ArrayList<>();
    private ArrayList<WinVisitorRecord> morningRecordList = new ArrayList<>();
    private ArrayList<WinVisitorRecord> noonRecordList = new ArrayList<>();
    private ArrayList<WinVisitorRecord> afternoonRecordList = new ArrayList<>();


    @Autowired
    private TokenService tokenService;


    @Value("${park_base_url}")
    private String PARK_BASE_URL;

    @Autowired
    private RestTemplate restTemplate;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/correct")
    public Result correct() {
        if (token == null) {
            token = tokenService.getToken();
        }
        HashMap<String, Object> postParameters = new HashMap<>();
        postParameters.put("start", 0);
        postParameters.put("limit", 0);
        //Condition
        Condition condition = new Condition();
        condition.setType(2);
        List<Integer> deviceList = new ArrayList();
        deviceList.add(100003);
        deviceList.add(100005);
        RepoIdBean idBean = new RepoIdBean();
//        idBean.setIn(deviceList);
        condition.setRepo_id(idBean);
        CreateTimestampBean timestampBean = new CreateTimestampBean();
        //以当天凌晨为开始统计时间
        timestampBean.setGte(Util.getDateStartTime().getTime() / 1000);
//        condition.setCreate_timestamp(timestampBean);

        postParameters.put("condition", condition);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, token);
        String jsonString = JSON.toJSONString(postParameters);
        jsonString = jsonString.replace("in", "$in");
        jsonString = jsonString.replace("gte", "$gte");
        HttpEntity httpEntity = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PARK_BASE_URL + "/face/v1/framework/face/search", httpEntity, String.class);
        if (responseEntity.getStatusCodeValue() == ResponseCode.OK) {
            String body = responseEntity.getBody();
            if (body != null) {
                ResponseModel responseModel = JSONObject.parseObject(body, ResponseModel.class);
                if (responseModel != null && responseModel.getResults() != null) {
                    morningRecordList.clear();
                    noonRecordList.clear();
                    afternoonRecordList.clear();
                    List<WinVisitorRecord> tmpList = JSONArray.parseArray(responseModel.getResults(), WinVisitorRecord.class);
                    if (tmpList != null && tmpList.size() > 0) {
                        for (WinVisitorRecord item: tmpList) {
                            boolean exit = false;
                            Date date = new Date(item.getCreate_timestamp()*1000);
                            ///早上
                            if(date.getHours() < 10) {
                                for (int i = 0; i < morningRecordList.size() && !exit; i++) {
                                    if(morningRecordList.get(i).getPerson_id() == item.getPerson_id()) {
                                        exit = true;
                                    }
                                }
                                if(!exit) {
                                    morningRecordList.add(item);
                                }
                            } else if(date.getHours() >= 10 && date.getHours() <= 15) {
                                for (int i = 0; i < noonRecordList.size() && !exit; i++) {
                                    if(noonRecordList.get(i).getPerson_id() == item.getPerson_id()) {
                                        exit = true;
                                    }
                                }
                                if(!exit) {
                                    noonRecordList.add(item);
                                }
                            } else {
                                for (int i = 0; i < afternoonRecordList.size() && !exit; i++) {
                                    if(afternoonRecordList.get(i).getPerson_id() == item.getPerson_id()) {
                                        exit = true;
                                    }
                                }
//                                if(!exit) {
                                    afternoonRecordList.add(item);
//                                }
                            }
                        }
                    }
                }

                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet1 = workbook.createSheet("早上");
                HSSFSheet sheet2 = workbook.createSheet("中午");
                HSSFSheet sheet3 = workbook.createSheet("傍晚");

                ///设置要导出的文件的名字
                String fileName = formatter2.format(new Date()) + "-刷脸成功记录"  + ".xls";
                //新增数据行，并且设置单元格数据

                int rowNum = 1;

                HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
                String[] excelHeaders = { "刷脸照片", "姓名", "卡号", "刷卡地址", "刷卡时间"};
                //headers表示excel表中第一行的表头

                HSSFRow row = sheet1.createRow(0);
                //在excel表中添加表头

                for(int i=0;i<excelHeaders.length;i++){
                    HSSFCell cell = row.createCell(i);
                    HSSFRichTextString text = new HSSFRichTextString(excelHeaders[i]);
                    cell.setCellValue(text);
                }
                //在表中存放查询到的数据放入对应的列
                for (WinVisitorRecord record : morningRecordList) {
                    HSSFRow row1 = sheet1.createRow(rowNum);
//                        row1.setHeight(Short.valueOf("1000"));
                    /**
                     dx1 - the x coordinate within the first cell.//定义了图片在第一个cell内的偏移x坐标，既左上角所在cell的偏移x坐标，一般可设0
                     dy1 - the y coordinate within the first cell.//定义了图片在第一个cell的偏移y坐标，既左上角所在cell的偏移y坐标，一般可设0
                     dx2 - the x coordinate within the second cell.//定义了图片在第二个cell的偏移x坐标，既右下角所在cell的偏移x坐标，一般可设0
                     dy2 - the y coordinate within the second cell.//定义了图片在第二个cell的偏移y坐标，既右下角所在cell的偏移y坐标，一般可设0
                     col1 - the column (0 based) of the first cell.//第一个cell所在列，既图片左上角所在列
                     row1 - the row (0 based) of the first cell.//图片左上角所在行
                     col2 - the column (0 based) of the second cell.//图片右下角所在列
                     row2 - the row (0 based) of the second cell.//图片右下角所在行
                     */
//                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 0, rowNum, (short) 0, rowNum);
//                        //插入图片
//                        patriarch.createPicture(anchor, workbook.addPicture(record.getFeature_content_base64().getBytes(), HSSFWorkbook.PICTURE_TYPE_PICT));
                    row1.createCell(1).setCellValue(record.getMeta().getName());
                    row1.createCell(2).setCellValue(record.getMeta().getCardId());
                    row1.createCell(3).setCellValue(record.getRepo_id() == 100003 ? "302" : "001");
                    row1.createCell(4).setCellValue(formatter.format(record.getCreate_timestamp() * 1000));
                    rowNum++;
                }

                rowNum = 1;
                String[] excelHeaders2 = { "刷脸照片", "姓名", "卡号", "刷卡地址", "刷卡时间"};
                //headers表示excel表中第一行的表头

                HSSFRow row2 = sheet2.createRow(0);
                //在excel表中添加表头

                for(int i=0;i<excelHeaders2.length;i++){
                    HSSFCell cell = row2.createCell(i);
                    HSSFRichTextString text = new HSSFRichTextString(excelHeaders2[i]);
                    cell.setCellValue(text);
                }
                //在表中存放查询到的数据放入对应的列
                for (WinVisitorRecord record : noonRecordList) {

                    HSSFRow row1 = sheet2.createRow(rowNum);
//                        row1.setHeight(Short.valueOf("1000"));
                    /**
                     dx1 - the x coordinate within the first cell.//定义了图片在第一个cell内的偏移x坐标，既左上角所在cell的偏移x坐标，一般可设0
                     dy1 - the y coordinate within the first cell.//定义了图片在第一个cell的偏移y坐标，既左上角所在cell的偏移y坐标，一般可设0
                     dx2 - the x coordinate within the second cell.//定义了图片在第二个cell的偏移x坐标，既右下角所在cell的偏移x坐标，一般可设0
                     dy2 - the y coordinate within the second cell.//定义了图片在第二个cell的偏移y坐标，既右下角所在cell的偏移y坐标，一般可设0
                     col1 - the column (0 based) of the first cell.//第一个cell所在列，既图片左上角所在列
                     row1 - the row (0 based) of the first cell.//图片左上角所在行
                     col2 - the column (0 based) of the second cell.//图片右下角所在列
                     row2 - the row (0 based) of the second cell.//图片右下角所在行
                     */
//                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 0, rowNum, (short) 0, rowNum);
//                        //插入图片
//                        patriarch.createPicture(anchor, workbook.addPicture(record.getFeature_content_base64().getBytes(), HSSFWorkbook.PICTURE_TYPE_PICT));
                    row1.createCell(1).setCellValue(record.getMeta().getName());
                    row1.createCell(2).setCellValue(record.getMeta().getCardId());
                    row1.createCell(3).setCellValue(record.getRepo_id() == 100003 ? "302" : "001");
                    row1.createCell(4).setCellValue(formatter.format(record.getCreate_timestamp() * 1000));
                    rowNum++;
                }

                rowNum = 1;
                String[] excelHeaders3 = { "刷脸照片", "姓名", "卡号", "刷卡地址", "刷卡时间"};
                //headers表示excel表中第一行的表头

                HSSFRow row3 = sheet3.createRow(0);
                //在excel表中添加表头

                for(int i=0;i<excelHeaders.length;i++){
                    HSSFCell cell = row3.createCell(i);
                    HSSFRichTextString text = new HSSFRichTextString(excelHeaders3[i]);
                    cell.setCellValue(text);
                }
                //在表中存放查询到的数据放入对应的列
                for (WinVisitorRecord record : afternoonRecordList) {

                    HSSFRow row1 = sheet3.createRow(rowNum);
//                        row1.setHeight(Short.valueOf("1000"));
                    /**
                     dx1 - the x coordinate within the first cell.//定义了图片在第一个cell内的偏移x坐标，既左上角所在cell的偏移x坐标，一般可设0
                     dy1 - the y coordinate within the first cell.//定义了图片在第一个cell的偏移y坐标，既左上角所在cell的偏移y坐标，一般可设0
                     dx2 - the x coordinate within the second cell.//定义了图片在第二个cell的偏移x坐标，既右下角所在cell的偏移x坐标，一般可设0
                     dy2 - the y coordinate within the second cell.//定义了图片在第二个cell的偏移y坐标，既右下角所在cell的偏移y坐标，一般可设0
                     col1 - the column (0 based) of the first cell.//第一个cell所在列，既图片左上角所在列
                     row1 - the row (0 based) of the first cell.//图片左上角所在行
                     col2 - the column (0 based) of the second cell.//图片右下角所在列
                     row2 - the row (0 based) of the second cell.//图片右下角所在行
                     */
//                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 0, rowNum, (short) 0, rowNum);
//                        //插入图片
//                        patriarch.createPicture(anchor, workbook.addPicture(record.getFeature_content_base64().getBytes(), HSSFWorkbook.PICTURE_TYPE_PICT));
                    row1.createCell(1).setCellValue(record.getMeta().getName());
                    row1.createCell(2).setCellValue(record.getMeta().getCardId());
                    row1.createCell(3).setCellValue(record.getRepo_id() == 100003 ? "302" : "001");
                    row1.createCell(4).setCellValue(formatter.format(record.getCreate_timestamp() * 1000));
                    rowNum++;
                }
                try {
                    FileOutputStream out = new FileOutputStream("./" + fileName);
                    workbook.write(out);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/error")
    public Result error() {

        return ResultGenerator.genSuccessResult();
    }
}
