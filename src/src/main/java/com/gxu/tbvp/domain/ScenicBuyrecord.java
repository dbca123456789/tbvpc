package com.gxu.tbvp.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "scenic_buyrecord")
public class ScenicBuyrecord {
    @Id
    private Integer id;

    @Column(name = "userId")
    private Integer userid;

    @Column(name = "scenicId")
    private Integer scenicid;

    @Column(name = "buy_time")
    private Date buyTime;

    private Double price;

    @Column(name = "access_count")
    private Integer accessCount;

    @Column(name = "buy_count")
    private Integer buyCount;

    @Column(name = "buy_ways")
    private Byte buyWays;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return userId
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return scenicId
     */
    public Integer getScenicid() {
        return scenicid;
    }

    /**
     * @param scenicid
     */
    public void setScenicid(Integer scenicid) {
        this.scenicid = scenicid;
    }

    /**
     * @return buy_time
     */
    public Date getBuyTime() {
        return buyTime;
    }

    /**
     * @param buyTime
     */
    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    /**
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return access_count
     */
    public Integer getAccessCount() {
        return accessCount;
    }

    /**
     * @param accessCount
     */
    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    /**
     * @return buy_count
     */
    public Integer getBuyCount() {
        return buyCount;
    }

    /**
     * @param buyCount
     */
    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    /**
     * @return buy_ways
     */
    public Byte getBuyWays() {
        return buyWays;
    }

    /**
     * @param buyWays
     */
    public void setBuyWays(Byte buyWays) {
        this.buyWays = buyWays;
    }
}