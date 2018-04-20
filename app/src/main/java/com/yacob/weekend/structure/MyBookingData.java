package com.yacob.weekend.structure;

/**
 * Created by Pravin on 14-11-2017.
 */

public class MyBookingData
{
    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public int getItemPhoto() {
        return itemPhoto;
    }

    public void setItemPhoto(int itemPhoto) {
        this.itemPhoto = itemPhoto;
    }

    private String houseName,fromDate,toDate,price,houseId;
    int itemPhoto;

    public MyBookingData(String houseName,String fromDate,String toDate,String price,String houseId,int itemPhoto)
    {
        this.itemPhoto = itemPhoto;
        this.houseId = houseId;
        this.houseName = houseName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.price = price;
    }
}
