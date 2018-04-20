package com.yacob.weekend.structure;

/**
 * Created by Dhahri on 20/12/2017.
 */

import java.util.ArrayList;


public class Home_data
{
    public String getBool() {
        return Bool;
    }

    public void setBool(String bool) {
        Bool = bool;
    }

    public String getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        HouseNumber = houseNumber;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getBasement() {
        return basement;
    }

    public void setBasement(String basement) {
        this.basement = basement;
    }

    public String getDescruption() {
        return descruption;
    }

    public void setDescruption(String descruption) {
        this.descruption = descruption;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;

    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;

    }

    public String getMasterRooms() {
        return masterRooms;
    }

    public void setMasterRooms(String masterRooms) {
        this.masterRooms = masterRooms;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPrivateSwimmingPool() {
        return privateSwimmingPool;
    }

    public void setPrivateSwimmingPool(String privateSwimmingPool) {
        this.privateSwimmingPool = privateSwimmingPool;
    }

    public String getRentrules() {
        return rentrules;
    }

    public void setRentrules(String rentrules) {
        this.rentrules = rentrules;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getToilets() {
        return toilets;
    }

    public void setToilets(String toilets) {
        this.toilets = toilets;
    }

    public String getTypeOfPeopleAllowedToRent() {
        return typeOfPeopleAllowedToRent;
    }

    public void setTypeOfPeopleAllowedToRent(String typeOfPeopleAllowedToRent) {
        this.typeOfPeopleAllowedToRent = typeOfPeopleAllowedToRent;
    }
    public void setPos(int pos) {
        this.pos=pos;
    }

    public int getPos() {
        return pos;
    }


    private String Bool;
    private String HouseNumber;
    private String Latitude;
    private String Longitude;
    private String basement;
    private String descruption;
    private String floors;
    private String houseId;
    private String houseName;
    private String houseType;
    private String location;
    private String masterRooms;
    private String priority;
    private String privateSwimmingPool;
    private String rentrules;
    private String rooms;
    private String salon;
    private String toilets;
    private String typeOfPeopleAllowedToRent;
    private String whichLine;
    private String singleImagePhoto;
    private ArrayList<String> featuers;
    private ArrayList<String> photosGalaryURLs;
    private int pos;

    public String getStrFeatures() {
        return strFeatures;
    }

    public void setStrFeatures(String strFeatures) {
        this.strFeatures = strFeatures;
    }

    public String getStrPhotoGalleries() {
        return strPhotoGalleries;
    }

    public void setStrPhotoGalleries(String strPhotoGalleries) {
        this.strPhotoGalleries = strPhotoGalleries;
    }

    String strFeatures;
    String strPhotoGalleries;

    public String[] getFeatures() {
        return features;
    }

    public void setFeatures(String[] features) {
        this.features = features;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    private String[] features;
    private String[] photos;


    public String getWhichLine() {
        return whichLine;
    }

    public void setWhichLine(String whichLine) {
        this.whichLine = whichLine;
    }

    public String getSingleImagePhoto() {
        return singleImagePhoto;
    }

    public void setSingleImagePhoto(String singleImagePhoto) {
        this.singleImagePhoto = singleImagePhoto;
    }

    public ArrayList<String> getFeatuers() {
        return featuers;
    }

    public void setFeatuers(ArrayList<String> featuers) {
        this.featuers = featuers;
    }

    public ArrayList<String> getPhotosGalaryURLs() {
        return photosGalaryURLs;
    }

    public void setPhotosGalaryURLs(ArrayList<String> photosGalaryURLs) {
        this.photosGalaryURLs = photosGalaryURLs;
    }



    public Home_data(String Bool,String HouseNumber,String Latitude,String Longitude,String basement,String descruption,
                      String floors,String houseId,String houseName,String houseType,String location,String masterRooms,
                      String priority,String privateSwimmingPool,String rentrules,String rooms,String salon,String toilets,
                      String typeOfPeopleAllowedToRent,String whichLine,String[] features,String[] photos,
                      String singleImagePhoto,String strPhotoGalleries,String strFeatures,int pos)
    {
        this.strPhotoGalleries = strPhotoGalleries;
        this.strFeatures = strFeatures;
        this.photos = photos;
        this.features = features;
        this.whichLine = whichLine;
        this.typeOfPeopleAllowedToRent = typeOfPeopleAllowedToRent;
        this.toilets = toilets;
        this.salon = salon;
        this.rooms = rooms;
        this.rentrules = rentrules;
        this.Bool = Bool;
        this.HouseNumber = HouseNumber;
        this.salon = salon;
        this.rooms = rooms;
        this.rentrules = rentrules;
        this.privateSwimmingPool = privateSwimmingPool;
        this.priority = priority;
        this.masterRooms = masterRooms;
        this.location = location;
        this.houseType = houseType;
        this.houseName = houseName;
        this.houseId = houseId;
        this.floors = floors;
        this.descruption = descruption;
        this.basement = basement;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.featuers = featuers;
        this.photosGalaryURLs = photosGalaryURLs;
        this.singleImagePhoto = singleImagePhoto;
        this.pos=pos;
    }
     public Home_data(){
         this.strPhotoGalleries = null;
         this.strFeatures = null;
         this.photos = null;
         this.features = null;
         this.whichLine = null;
         this.typeOfPeopleAllowedToRent = null;
         this.toilets = null;
         this.salon = null;
         this.rooms = null;
         this.rentrules = null;
         this.Bool = null;
         this.HouseNumber = null;
         this.salon = null;
         this.rooms = null;
         this.rentrules = null;
         this.privateSwimmingPool = null;
         this.priority = null;
         this.masterRooms = null;
         this.location = null;
         this.houseType = null;
         this.houseName = null;
         this.houseId = null;
         this.floors = null;
         this.descruption = null;
         this.basement = null;
         this.Longitude = null;
         this.Latitude = null;
         this.featuers = null;
         this.photosGalaryURLs = null;
         this.singleImagePhoto = null;
         this.pos=0;
     }


}

