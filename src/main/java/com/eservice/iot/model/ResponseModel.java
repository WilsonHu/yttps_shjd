package com.eservice.iot.model;

public class ResponseModel {
    private String results;

    private int rtn;

    private String message;

    private int total;

    private String session_id;

    public String getResults() {
        return results;
    }

    public int getRtn() {
        return rtn;
    }

    public String getMessage() {
        return message;
    }

    public void setResults(String result) {
        this.results = result;
    }

    public void setRtn(int rtn) {
        this.rtn = rtn;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
