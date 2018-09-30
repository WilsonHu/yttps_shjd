package com.eservice.iot.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eservice.iot.core.Result;
import com.eservice.iot.core.ResultGenerator;
import com.eservice.iot.model.*;
import com.eservice.iot.service.ResponseCode;
import com.eservice.iot.service.StaffService;
import com.eservice.iot.service.TokenService;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Class Description: xxx
 *
 * @author Wilson Hu
 * @date 2018/08/21.
 */
@RestController
@RequestMapping("/")
public class ReportController {

    private final static Logger logger = LoggerFactory.getLogger(ReportController.class);
    /**
     * Token
     */
    private String token;
    /**
     * 刷卡记录列表
     */
    private ArrayList<WinVisitorRecord> morningRecordList = new ArrayList<>();
    private ArrayList<WinVisitorRecord> noonRecordList = new ArrayList<>();
    private ArrayList<WinVisitorRecord> afternoonRecordList = new ArrayList<>();


    @Autowired
    private TokenService tokenService;
    @Resource
    private StaffService staffService;


    @Value("${park_base_url}")
    private String PARK_BASE_URL;

    @Autowired
    private RestTemplate restTemplate;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

    private ArrayList<WinVisitorRecord> staffList = new ArrayList<>();

    private List<WinVisitorRecord> resultList = new ArrayList<>();

    private ThreadPoolTaskExecutor mExecutor;

    @GetMapping("/checktime")
    public String checkTime(@RequestParam String kqStartDt, @RequestParam String kqEndDt) {

        token = tokenService.getToken();
        HashMap<String, Object> postParameters = new HashMap<>();
        postParameters.put("start", 0);
        postParameters.put("limit", 0);
        //Condition
        Condition condition = new Condition();
        //type为2返回过人记录
        condition.setType(2);
        RepoIdBean idBean = new RepoIdBean();
        condition.setRepo_id(idBean);
        Date startDate;
        Date endDate;
        //记录开始时间
        try {
            startDate = formatter2.parse(kqStartDt);
            endDate = formatter2.parse(kqEndDt);
        } catch (ParseException e) {
            e.printStackTrace();
            return "开始或结束时间格式错误！";
        }
        CreateTimestampBean timestampBean = new CreateTimestampBean();
        timestampBean.setGte(startDate.getTime() / 1000);
        timestampBean.setLte(endDate.getTime() / 1000);
        condition.setCreate_timestamp(timestampBean);

        postParameters.put("condition", condition);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, token);
        String jsonString = JSON.toJSONString(postParameters);
        jsonString = jsonString.replace("in", "$in");
        jsonString = jsonString.replace("gte", "$gte");
        jsonString = jsonString.replace("lte", "$lte");
        HttpEntity httpEntity = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PARK_BASE_URL + "/face/v1/framework/face/search", httpEntity, String.class);
        if (responseEntity.getStatusCodeValue() == ResponseCode.OK) {
            String body = responseEntity.getBody();
            if (body != null) {
                ResponseModel responseModel = JSONObject.parseObject(body, ResponseModel.class);
                if (responseModel != null && responseModel.getResults() != null) {
                    List<WinVisitorRecord> tmpList = JSONArray.parseArray(responseModel.getResults(), WinVisitorRecord.class);
                    resultList.clear();
                    if (tmpList != null && tmpList.size() > 0) {
                        staffList = staffService.getStaffList();
                        //remove the same data firstly
                        for (int i = 0; i < tmpList.size(); i++) {
                            //去除刷脸不通过得到记录
                            if(!tmpList.get(i).getMeta().isPassed()) {
                                continue;
                            }
                            WinVisitorRecord record = insertIDInfo(tmpList.get(i));
                            if (resultList.size() == 0) {
                                if(record != null) {
                                    resultList.add(record);
                                }
                            } else {
                                try {
                                    //如果记录中前一条是同一个person且不再同一天,则不插入

                                    boolean needInsert = false;
                                    if(tmpList.get(i).getPerson_id() != resultList.get(resultList.size() - 1).getPerson_id()) {
                                        //TODO:去重逻辑还欠缺
                                        needInsert = true;
                                    } else {
                                        //不在同一天
                                        Date date1 = new Date(tmpList.get(i).getCreate_timestamp()*1000);
                                        Date date2 = new Date(resultList.get(resultList.size() - 1).getCreate_timestamp() *1000);
                                        if(date1.getDay() != date2.getDay()) {
                                            needInsert = true;
                                        }
                                    }
                                    if (needInsert) {
                                        if(record != null) {
                                            resultList.add(record);
                                        }
                                    }
                                } catch (Exception e) {
                                    logger.error(e.getMessage());
                                }
                            }
                        }
                        //format the result for customer
                        String resultStr = "";
                        if (resultList.size() > 0) {
                            for (int i = 0; i < resultList.size(); i++) {
                                if(resultList.get(i).getMeta().getCardId() == null || resultList.get(i).getMeta().getCardId().equals("")) {
                                    logger.error("编号为空 ==> 姓名 : {}, 部门 ： {}, 编号: {}", resultList.get(i).getMeta().getName(), resultList.get(i).getMeta().getDepartment(), resultList.get(i).getMeta().getExternal_id());
                                } else {
                                    resultStr += resultList.get(i).getMeta().getCardId() + "#" + formatter.format(new Date(resultList.get(i).getCreate_timestamp()*1000)) + " ";
                                }
                            }
                            //Generate excel report
                            if (mExecutor == null) {
                                initExecutor();
                            }
                            mExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    generateExcelReport(resultList);
                                }
                            });
                        }
                        return resultStr;
                    }
                }
            }
        }
        return "获取考勤数据出错！";
    }

    private WinVisitorRecord insertIDInfo(WinVisitorRecord tmp) {
        WinVisitorRecord record = null;
        for (int j = 0; j < staffList.size() && (record == null); j++) {
            if (staffList.get(j).getPerson_id() == tmp.getPerson_id()) {
                record = tmp;
                record.getMeta().setCardId(staffList.get(j).getMeta().getExternal_id());
            }
        }
        if (record == null) {
            logger.error("刷脸记录没找到对应员工 ==> 名字：{}, ID: {}", tmp.getMeta().getName(), tmp.getPerson_id());
        }
        return record;
    }

    private void generateExcelReport(List<WinVisitorRecord> list) {
        morningRecordList.clear();
        noonRecordList.clear();
        afternoonRecordList.clear();
        for (WinVisitorRecord item : list) {
            boolean exit = false;
            Date date = new Date(item.getCreate_timestamp() * 1000);
            ///早上
            if (date.getHours() < 10) {
                for (int i = 0; i < morningRecordList.size() && !exit; i++) {
                    if (morningRecordList.get(i).getPerson_id() == item.getPerson_id()) {
                        exit = true;
                    }
                }
                if (!exit) {
                    morningRecordList.add(item);
                }
            } else if (date.getHours() >= 10 && date.getHours() <= 15) {
                for (int i = 0; i < noonRecordList.size() && !exit; i++) {
                    if (noonRecordList.get(i).getPerson_id() == item.getPerson_id()) {
                        exit = true;
                    }
                }
                if (!exit) {
                    noonRecordList.add(item);
                }
            } else {
                for (int i = 0; i < afternoonRecordList.size() && !exit; i++) {
                    if (afternoonRecordList.get(i).getPerson_id() == item.getPerson_id()) {
                        exit = true;
                    }
                }
                if (!exit) {
                    afternoonRecordList.add(item);
                }
            }
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet1 = workbook.createSheet("早上");
        HSSFSheet sheet2 = workbook.createSheet("中午");
        HSSFSheet sheet3 = workbook.createSheet("傍晚");

        ///设置要导出的文件的名字
        String fileName = formatter2.format(new Date()) + "-刷脸成功记录" + ".xls";
        //新增数据行，并且设置单元格数据

        insertDataInSheet(sheet1, morningRecordList);
        insertDataInSheet(sheet2, noonRecordList);
        insertDataInSheet(sheet3, afternoonRecordList);
        try {
            FileOutputStream out = new FileOutputStream("./" + fileName);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertDataInSheet(HSSFSheet sheet, List<WinVisitorRecord> list) {
        int rowNum = 1;
        String[] excelHeaders = {"刷脸照片", "姓名", "卡号", "刷卡地址", "刷卡时间"};
        //headers表示excel表中第一行的表头

        HSSFRow row3 = sheet.createRow(0);
        //在excel表中添加表头

        for (int i = 0; i < excelHeaders.length; i++) {
            HSSFCell cell = row3.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(excelHeaders[i]);
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应的列
        for (WinVisitorRecord record : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(1).setCellValue(record.getMeta().getName());
            row1.createCell(2).setCellValue(record.getMeta().getCardId());
            row1.createCell(3).setCellValue(record.getRepo_id() == 100003 ? "302" : "001");
            row1.createCell(4).setCellValue(formatter.format(record.getCreate_timestamp() * 1000));
            rowNum++;
        }
    }


    private void initExecutor() {
        mExecutor = new ThreadPoolTaskExecutor();
        mExecutor.setThreadNamePrefix("YTTPS-");
        mExecutor.initialize();
    }

    @GetMapping("/error")
    public Result error() {
        return ResultGenerator.genSuccessResult();
    }
}
