package com.eservice.iot.model;

/**
 * Class Description:
 *
 * @author Wilson Hu
 * @date 2018/9/7
 */
public class WinVisitorRecord {

    /**
     * create_timestamp : 1536276637
     * error_code : 0
     * face_image_id : 4398046523344
     * face_image_id_str : 4398046523344
     * feature_content_base64 : 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
     * image_uri : 100005/62d1df925d849e08fa84a303bc315e2d1109dd12
     * meta : {"cardId":"110018","name":"谢骏伟","passed":true}
     * person_id : 279
     * person_id_str : 279
     * repo_id : 100005
     * success : true
     * type : 2
     */

    private long create_timestamp;
    private int error_code;
    private long face_image_id;
    private String face_image_id_str;
    private String feature_content_base64;
    private String image_uri;
    private MetaBean meta;
    private int person_id;
    private String person_id_str;
    private int repo_id;
    private boolean success;
    private int type;

    public long getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(long create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public long getFace_image_id() {
        return face_image_id;
    }

    public void setFace_image_id(long face_image_id) {
        this.face_image_id = face_image_id;
    }

    public String getFace_image_id_str() {
        return face_image_id_str;
    }

    public void setFace_image_id_str(String face_image_id_str) {
        this.face_image_id_str = face_image_id_str;
    }

    public String getFeature_content_base64() {
        return feature_content_base64;
    }

    public void setFeature_content_base64(String feature_content_base64) {
        this.feature_content_base64 = feature_content_base64;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getPerson_id_str() {
        return person_id_str;
    }

    public void setPerson_id_str(String person_id_str) {
        this.person_id_str = person_id_str;
    }

    public int getRepo_id() {
        return repo_id;
    }

    public void setRepo_id(int repo_id) {
        this.repo_id = repo_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class MetaBean {
        /**
         * cardId : 110018
         * name : 谢骏伟
         * passed : true
         */

        private String cardId;
        private String name;
        private boolean passed;

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPassed() {
            return passed;
        }

        public void setPassed(boolean passed) {
            this.passed = passed;
        }
    }
}
