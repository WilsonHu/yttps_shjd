package com.eservice.iot.model;

/**
 * Class Description:
 *
 * @author Wilson Hu
 * @date 2018/9/7
 */
public class CreateTimestampBean {
    /**
     * $gte : 1536274800
     */

    private long gte;
    private long lte;

    public long getGte() {
        return gte;
    }

    public void setGte(long gte) {
        this.gte = gte;
    }

    public long getLte() {
        return lte;
    }

    public void setLte(long lte) {
        this.lte = lte;
    }
}
