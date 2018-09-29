//package com.eservice.iot.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.eservice.iot.model.Person;
//import com.eservice.iot.model.ResponseModel;
//import com.eservice.iot.model.Staff;
//import com.eservice.iot.model.VisitRecord;
//import com.eservice.iot.util.Util;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//
//
///**
// * @author HT
// */
//@Component
//public class SignInService {
//
//    private final static Logger logger = LoggerFactory.getLogger(SignInService.class);
//
//    @Value("${park_base_url}")
//    private String PARK_BASE_URL;
//
//    @Autowired
//    private RestTemplate restTemplate;
//    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    /**
//     * Token
//     */
//    private String token;
//    /**
//     * 员工列表
//     */
//    private ArrayList<Staff> staffList = new ArrayList<>();
//
//    /**
//     * 当天已签到员工列表
//     */
//    private ArrayList<Person> staffSignInList = new ArrayList<>();
//
//    /**
//     * 当天已签到VIP员工列表
//     */
//    private ArrayList<Person> vipSignInList = new ArrayList<>();
//
//    @Autowired
//    private TokenService tokenService;
//
//
//    private ThreadPoolTaskExecutor mExecutor;
//
//    /**
//     * 查询开始时间,单位为秒
//     */
//    private Long queryStartTime = 0L;
//
//
//    public SignInService() {
//        //准备初始数据，此时获取到考勤列表后不去通知钉钉，初始化开始查询时间
//        queryStartTime = Util.getDateStartTime().getTime() / 1000;
//    }
//
//    /**
//     * 每秒查询一次考勤信息
//     */
//    @Scheduled(fixedRate = 1000)
//    public void fetchSignInScheduled() {
//        ///当员工列表数为0，或者已全部签核完成,以及当前处于程序初始化状态情况下，可以跳过不再去获取考勤数据
//
//        if (token == null && tokenService != null) {
//            token = tokenService.getToken();
//        }
//        querySignInStaff(queryStartTime);
//    }
//
//    private void querySignInStaff(Long startTime) {
//        if (token == null) {
//            token = tokenService.getToken();
//        }
//        HashMap<String, Object> postParameters = new HashMap<>();
//        postParameters.put("start", 0);
//        postParameters.put("limit", 0);
//        Long queryEndTime = System.currentTimeMillis() / 1000;
//        postParameters.put("end_timestamp", queryEndTime);
//        //只获取员工数据
//        ArrayList<String> identity = new ArrayList<>();
//        identity.add("STAFF");
//        postParameters.put("identity_list", identity);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
//        headers.add(HttpHeaders.AUTHORIZATION, token);
//        HttpEntity httpEntity = new HttpEntity<>(JSON.toJSONString(postParameters), headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PARK_BASE_URL + "/visit_record/query", httpEntity, String.class);
//        if (responseEntity.getStatusCodeValue() == ResponseCode.OK) {
//            String body = responseEntity.getBody();
//            if (body != null) {
//                processStaffSignInResponse(body, startTime.equals(Util.getDateStartTime().getTime() / 1000));
//                //query成功后用上一次查询的结束时间作为下一次开始时间，减去1秒形成闭区间
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
//                responseEntity = restTemplate.postForEntity(PARK_BASE_URL + "/visit_record/query", r, String.class);
//                if (responseEntity.getStatusCodeValue() == ResponseCode.OK) {
//                    String body = responseEntity.getBody();
//                    if (body != null) {
//                        processStaffSignInResponse(body, startTime.equals(Util.getDateStartTime().getTime() / 1000));
//                        //query成功后用上一次查询的结束时间作为下一次开始时间，减去1形成闭区间
//                        queryStartTime = queryEndTime - 1;
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 凌晨1点清除签到记录
//     */
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void resetStaffDataScheduled() {
//        logger.info("每天凌晨一点清除前一天签到记录：{}", formatter.format(new Date()));
//        if (staffSignInList != null & staffSignInList.size() > 0) {
//            staffSignInList.clear();
//        }
//    }
//
//    private void processStaffSignInResponse(String body, boolean initial) {
//        ResponseModel responseModel = JSONObject.parseObject(body, ResponseModel.class);
//        if (responseModel != null && responseModel.getResult() != null) {
//            ArrayList<VisitRecord> tempList = (ArrayList<VisitRecord>) JSONArray.parseArray(responseModel.getResult(), VisitRecord.class);
//            if (tempList != null && tempList.size() > 0) {
//                ArrayList<Person> sendSignInList = new ArrayList<>();
//                ArrayList<Person> sendVipList = new ArrayList<>();
//
//                if (mExecutor == null) {
//                    initExecutor();
//                }
//                mExecutor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        //TODO:发送Client
//                        logger.error("socket发送Client！");
//                    }
//                });
//            }
//        }
//    }
//
//    private void initExecutor() {
//        mExecutor = new ThreadPoolTaskExecutor();
//        mExecutor.setCorePoolSize(2);
//        mExecutor.setMaxPoolSize(5);
//        mExecutor.setThreadNamePrefix("YTTPS-");
//        mExecutor.initialize();
//    }
//
//    public ArrayList<Staff> getStaffList() {
//        return staffList;
//    }
//}
