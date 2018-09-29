//package com.eservice.iot.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.dingtalk.api.response.OapiUserListResponse;
//import com.eservice.iot.model.*;
//import com.eservice.iot.util.Util;
//import com.taobao.api.ApiException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//
///**
// * @author HT
// */
//@Component
//public class VisitorService {
//
//    private final static Logger logger = LoggerFactory.getLogger(VisitorService.class);
//
//    @Value("${park_base_url}")
//    private String PARK_BASE_URL;
//
//    /**
//     * 长期有效的访客
//     */
//    public static final String LONG_TIME_VALID = "0";
//
//    /**
//     * 临时访客
//     */
//    public static final String TMP_VALID = "1";
//
//    @Autowired
//    private RestTemplate restTemplate;
//    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//
//    /**
//     * Token
//     */
//    private String token;
//    /**
//     * 访客列表
//     */
//    private List<Visitor> visitorList = new ArrayList<>();
//
//    /**
//     * 当天访客到访列表
//     */
//    private ArrayList<VisitRecord> visitorSignInList = new ArrayList<>();
//
//    /**
//     * 当天已签到VIP访客列表
//     */
//    private ArrayList<Person> vipVisitorList = new ArrayList<>();
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Autowired
//    private TagService tagService;
//
//    @Autowired
//    private SignInService signInService;
//
//    @Autowired
//    private DingDingService dingDingService;
//
//    /**
//     * 查询开始时间,单位为秒
//     */
//    private Long queryStartTime = 0L;
//
//    private int visitorNum = 0;
//
//    public VisitorService() {
//        //准备初始数据，此时获取到访客列表后不去通知，初始化开始查询时间
//        queryStartTime = Util.getDateStartTime().getTime() / 1000;
//    }
//
//    /**
//     * 每秒查询访客记录
//     */
//    @Scheduled(fixedRate = 1000)
//    public void fetchVisitorRecordScheduled() {
//        boolean skip = visitorList.size() <= 0 || visitorList.size() == visitorSignInList.size()
//                || tagService == null || !tagService.isTagInitialFinished();
//        if (skip) {
//            return;
//        }
//
//        if (token == null && tokenService != null) {
//            token = tokenService.getToken();
//        }
//        querySignInVisitor(queryStartTime);
//    }
//
//    /**
//     * cron: 秒、分、时、日、月、年
//     * 早上六点至傍晚八点，每10秒钟获取一次当天访客信息
//     */
////    @Scheduled(cron = "0/10 * 0-23 * * ?")
//    @Scheduled(fixedRate = 10000)
//    public void fetchVisitorListScheduled() {
//        if (token == null) {
//            token = tokenService.getToken();
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.ACCEPT, "application/json");
//        headers.add("Authorization", token);
//        HttpEntity entity = new HttpEntity(headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(PARK_BASE_URL + "/visitors", HttpMethod.GET, entity, String.class);
//        if (responseEntity.getStatusCodeValue() == ResponseCode.OK) {
//            String body = responseEntity.getBody();
//            if (body != null) {
//                processVisitorResponse(body);
//            } else {
//                fetchVisitorListScheduled();
//            }
//        } else if (responseEntity.getStatusCodeValue() == ResponseCode.TOKEN_INVALID) {
//            //token失效,重新获取token后再进行数据请求
//            token = tokenService.getToken();
//            if (token != null) {
//                headers.add("Authorization", token);
//                responseEntity = restTemplate.exchange(PARK_BASE_URL + "/visitors", HttpMethod.GET, entity, String.class);
//                if (responseEntity.getStatusCodeValue() == ResponseCode.OK) {
//                    String body = responseEntity.getBody();
//                    if (body != null) {
//                        processVisitorResponse(body);
//                    }
//                }
//            }
//        } else {
//            fetchVisitorListScheduled();
//        }
//    }
//
//    private void processVisitorResponse(String body) {
//        ResponseModel responseModel = JSONObject.parseObject(body, ResponseModel.class);
//        if (responseModel != null && responseModel.getResult() != null) {
//            List<Visitor> tmpList = JSONArray.parseArray(responseModel.getResult(), Visitor.class);
//            if (tmpList != null && tmpList.size() > 0) {
////                if( visitorNum > tmpList.size() || visitorNum == 0) {
////                    ///后端删除访客或者初始化
////                    visitorNum = tmpList.size();
////                } else if(tmpList.size() > visitorNum) {
////                    //取访客的前面部分
////                    tmpList = tmpList.subList(0, tmpList.size() - visitorNum);
////                    for (Visitor item : tmpList) {
////                        if (signInService != null && signInService.getStaffList().size() > 0) {
////                            List<Staff> list = signInService.getStaffList();
////                            for (int i = 0; i < list.size(); i++) {
////                                if (list.get(i).getPersonInformation().getName().equals(item.getPerson_information().getVisitee_name())) {
////                                    if (dingDingService != null) {
////                                        List<OapiUserListResponse.Userlist> dingDingUserList = dingDingService.getDingDingUserList();
////                                        for (int j = 0; j < dingDingUserList.size(); j++) {
////                                            if(dingDingUserList.get(j).getMobile().equals(list.get(i).getPersonInformation().getPhone())) {
////                                                try {
////                                                    String msg = dingDingUserList.get(j).getName() + "，您好！ 您有访客【" + item.getPerson_information().getName() + "】，联系方式【" + item.getPerson_information().getPhone() + "】,提交时间：" + formatter.format(new Date());
////                                                    dingDingService.sendTextMessage(dingDingUserList.get(j).getUserid(), msg);
////                                                    visitorNum++;
////                                                } catch (ApiException e) {
////                                                    e.printStackTrace();
////                                                }
////                                            }
////                                        }
////                                    }
////                                }
////                            }
////                        }
////                    }
////                }
//                boolean changed = false;
//                if (tmpList.size() != visitorList.size()) {
//                    changed = true;
//                } else {
//                    if (!tmpList.equals(visitorList)) {
//                        changed = true;
//                    }
//                }
//                if (changed) {
//                    logger.info("访客总列表更新：{} ==> {}", visitorList.size(), tmpList.size());
//                    visitorList = tmpList;
//                }
//            }
//        }
//    }
//
//    private void processVisitorRecordResponse(String body, boolean initial) {
//        ResponseModel responseModel = JSONObject.parseObject(body, ResponseModel.class);
//        if (responseModel != null && responseModel.getResult() != null) {
//            ArrayList<VisitRecord> tempList = (ArrayList<VisitRecord>) JSONArray.parseArray(responseModel.getResult(), VisitRecord.class);
//            if (tempList != null && tempList.size() > 0) {
//                ArrayList<Person> sendVipList = new ArrayList<>();
//                for (VisitRecord visitRecord : tempList) {
//                    List<String> tagList = visitRecord.getPerson().getTag_id_list();
//                    boolean isVIP = false;
//                    if (tagList != null) {
//                        for (int i = 0; i < tagList.size() && !isVIP; i++) {
//                            ///在VIP标签列表中
//                            if (tagService.getVIPTagIdList().contains(tagList.get(i))) {
//                                isVIP = true;
//                            }
//                        }
//                    }
//                    if (isVIP) {
//                        boolean exit = false;
//                        for (int i = 0; i < vipVisitorList.size() && !exit; i++) {
//                            if (vipVisitorList.get(i).getPerson_id().equals(visitRecord.getPerson().getPerson_id())) {
//                                exit = true;
//                            }
//                        }
//                        if (!exit) {
//                            vipVisitorList.add(visitRecord.getPerson());
//                            if (!initial) {
//                                sendVipList.add(visitRecord.getPerson());
//                            }
//                        }
//                    }
//                    if (sendVipList.size() > 0) {
//                        visitorSignInList.add(visitRecord);
//                        //如果是程序initial，则不推送钉钉
//                        if (!initial) {
//                            //todo:推送client
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//    private void querySignInVisitor(Long startTime) {
//        HashMap<String, Object> postParameters = new HashMap<>();
//        ///考勤记录查询开始时间
//        postParameters.put("start_timestamp", startTime);
//        ///考勤记录查询结束时间
//        Long queryEndTime = System.currentTimeMillis() / 1000;
//        postParameters.put("end_timestamp", queryEndTime);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
//        headers.add(HttpHeaders.AUTHORIZATION, token);
//        //只获取访客数据
//        ArrayList<String> identity = new ArrayList<>();
//        identity.add("VISITOR");
//        HttpEntity httpEntity = new HttpEntity<>(JSON.toJSONString(postParameters), headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PARK_BASE_URL + "/visitor_visit/record", httpEntity, String.class);
//        if (responseEntity.getStatusCodeValue() == ResponseCode.OK) {
//            String body = responseEntity.getBody();
//            if (body != null) {
//                processVisitorRecordResponse(body, startTime.equals(Util.getDateStartTime().getTime() / 1000));
//                queryStartTime = queryEndTime - 1;
//            }
//        } else if (responseEntity.getStatusCodeValue() == ResponseCode.TOKEN_INVALID) {
//            //token失效,重新获取token后再进行数据请求
//            token = tokenService.getToken();
//            if (token != null) {
//                ///考勤记录查询开始时间
//                postParameters.put("start_timestamp", startTime);
//                ///考勤记录查询结束时间
//                postParameters.put("end_timestamp", queryEndTime);
//                headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
//                headers.add(HttpHeaders.AUTHORIZATION, token);
//                HttpEntity r = new HttpEntity<>(JSON.toJSONString(postParameters), headers);
//                responseEntity = restTemplate.postForEntity(PARK_BASE_URL + "/visitor_visit/record", r, String.class);
//                if (responseEntity.getStatusCodeValue() == ResponseCode.OK) {
//                    String body = responseEntity.getBody();
//                    if (body != null) {
//                        processVisitorRecordResponse(body, startTime.equals(Util.getDateStartTime().getTime() / 1000));
//                        queryStartTime = queryEndTime - 1;
//                    }
//                }
//            }
//        }
//    }
//}
