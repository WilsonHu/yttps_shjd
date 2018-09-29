package com.eservice.iot.model;

import java.util.ArrayList;
import java.util.List;

public class Policy {

    /**
     * tag_id_list : ["string"]
     * device_id_list : ["string"]
     * name : string
     * id : string
     * valid_time : {"end_date":"string","mode":"string","start_time":"string","end_timestamp":0,"start_timestamp":0,"end_time":"string","start_date":"string","valid_weekday":[0]}
     * enabled : true
     */
    private ArrayList<String> tag_id_list;
    private ArrayList<String> device_id_list;
    private String name;
    private String id;
    private Valid_timeEntity valid_time;
    private boolean enabled;

    public void setTag_id_list(ArrayList<String> tag_id_list) {
        this.tag_id_list = tag_id_list;
    }

    public void setDevice_id_list(ArrayList<String> device_id_list) {
        this.device_id_list = device_id_list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValid_time(Valid_timeEntity valid_time) {
        this.valid_time = valid_time;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList<String> getTag_id_list() {
        return tag_id_list;
    }

    public ArrayList<String> getDevice_id_list() {
        return device_id_list;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Valid_timeEntity getValid_time() {
        return valid_time;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public class Valid_timeEntity {
        /**
         * end_date : string
         * mode : string
         * start_time : string
         * end_timestamp : 0
         * start_timestamp : 0
         * end_time : string
         * start_date : string
         * valid_weekday : [0]
         */
        private String end_date;
        private String mode;
        private String start_time;
        private int end_timestamp;
        private int start_timestamp;
        private String end_time;
        private String start_date;
        private List<Integer> valid_weekday;

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public void setEnd_timestamp(int end_timestamp) {
            this.end_timestamp = end_timestamp;
        }

        public void setStart_timestamp(int start_timestamp) {
            this.start_timestamp = start_timestamp;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public void setValid_weekday(List<Integer> valid_weekday) {
            this.valid_weekday = valid_weekday;
        }

        public String getEnd_date() {
            return end_date;
        }

        public String getMode() {
            return mode;
        }

        public String getStart_time() {
            return start_time;
        }

        public int getEnd_timestamp() {
            return end_timestamp;
        }

        public int getStart_timestamp() {
            return start_timestamp;
        }

        public String getEnd_time() {
            return end_time;
        }

        public String getStart_date() {
            return start_date;
        }

        public List<Integer> getValid_weekday() {
            return valid_weekday;
        }
    }
}
