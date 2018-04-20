package com.yacob.weekend.data;

/**
 * Created by Dhahri on 06/02/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yacob.weekend.structure.Home_data;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    static String TAG = "MYDBHELPER";
    //Database Name
    static String dbName = "dbWeekend";
    static int dbVer = 1; // 14-9-2017
    // Hotel Table
    static String tblHotels = "tblHotels";
    // Columns declaration Hotel Table
    static String col_tblHotels_id = "id";
    static String col_tblHotels_Bool = "Bool";
    static String col_tblHotels_HouseNumber = "HouseNumber";
    static String col_tblHotels_Latitude = "Latitude";
    static String col_tblHotels_Longitude = "Longitude";
    static String col_tblHotels_basement = "basement";
    static String col_tblHotels_descruption = "descruption";
    static String col_tblHotels_floors = "floors";
    static String col_tblHotels_houseId = "houseId";
    static String col_tblHotels_houseName = "houseName";
    static String col_tblHotels_houseType = "houseType";
    static String col_tblHotels_location = "location";
    static String col_tblHotels_masterRooms = "masterRooms";
    static String col_tblHotels_priority = "priority";
    static String col_tblHotels_privateSwimmingPool = "privateSwimmingPool";
    static String col_tblHotels_rentrules = "rentrules";
    static String col_tblHotels_rooms = "rooms";
    static String col_tblHotels_salon = "salon";
    static String col_tblHotels_toilet = "toilet";
    static String col_tblHotels_typeOfPeopleAllowedToRent = "typeOfPeopleAllowedToRent";
    static String col_tblHotels_whichLineOnSea = "whichLineOnSea";
    static String col_tblHotels_singleImagePhoto = "singleImagePhoto";
    static String col_tblHotels_features = "features";
    static String col_tblHotels_photoGalleries = "photoGalleries";
    // Favourite Hotel Table
    static String tblFavHotels = "tblFavHotels";
    // Columns declaration of Favourite Hotel Table
    static String col_tblFavHotels_id = "id";
    static String col_tblFavHotels_Bool = "Bool";
    static String col_tblFavHotels_HouseNumber = "HouseNumber";
    static String col_tblFavHotels_Latitude = "Latitude";
    static String col_tblFavHotels_Longitude = "Longitude";
    static String col_tblFavHotels_basement = "basement";
    static String col_tblFavHotels_descruption = "descruption";
    static String col_tblFavHotels_floors = "floors";
    static String col_tblFavHotels_houseId = "houseId";
    static String col_tblFavHotels_houseName = "houseName";
    static String col_tblFavHotels_houseType = "houseType";
    static String col_tblFavHotels_location = "location";
    static String col_tblFavHotels_masterRooms = "masterRooms";
    static String col_tblFavHotels_priority = "priority";
    static String col_tblFavHotels_privateSwimmingPool = "privateSwimmingPool";
    static String col_tblFavHotels_rentrules = "rentrules";
    static String col_tblFavHotels_rooms = "rooms";
    static String col_tblFavHotels_salon = "salon";
    static String col_tblFavHotels_toilet = "toilet";
    static String col_tblFavHotels_typeOfPeopleAllowedToRent = "typeOfPeopleAllowedToRent";
    static String col_tblFavHotels_whichLineOnSea = "whichLineOnSea";
    static String col_tblFavHotels_singleImagePhoto = "singleImagePhoto";
    static String col_tblFavHotels_features = "features";
    static String col_tblFavHotels_photoGalleries = "photoGalleries";

    public DBHelper(Context context) {

		/*super(context, Environment.getExternalStorageDirectory()
				+ File.separator + "livevidarbha" + File.separator + dbName,
				null, dbVer);*/
        // TODO Auto-generated constructor stub
        super(context, dbName, null, dbVer);
    }
    //Create query for Favourite Hotels Table and Hotels Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sqlTblHotels = "CREATE TABLE " + tblHotels + " ("
                + col_tblHotels_id + " INTEGER PRIMARY KEY,"
                + col_tblHotels_Bool + " TEXT,"
                + col_tblHotels_HouseNumber + " TEXT,"
                + col_tblHotels_Latitude + " TEXT,"
                + col_tblHotels_Longitude + " TEXT,"
                + col_tblHotels_basement + " TEXT,"
                + col_tblHotels_descruption + " TEXT,"
                + col_tblHotels_floors + " TEXT,"
                + col_tblHotels_houseId + " UNIQUE,"
                + col_tblHotels_houseName + " TEXT,"
                + col_tblHotels_houseType + " TEXT,"
                + col_tblHotels_location + " TEXT,"
                + col_tblHotels_masterRooms + " TEXT,"
                + col_tblHotels_priority + " TEXT,"
                + col_tblHotels_privateSwimmingPool + " TEXT,"
                + col_tblHotels_rentrules + " TEXT,"
                + col_tblHotels_rooms + " TEXT,"
                + col_tblHotels_salon + " TEXT,"
                + col_tblHotels_toilet + " TEXT,"
                + col_tblHotels_typeOfPeopleAllowedToRent + " TEXT,"
                + col_tblHotels_whichLineOnSea + " TEXT,"
                + col_tblHotels_features + " TEXT,"
                + col_tblFavHotels_singleImagePhoto + " TEXT,"
                + col_tblHotels_photoGalleries + " TEXT)";
        db.execSQL(sqlTblHotels);

        String sqlTblFavHotels = "CREATE TABLE " + tblFavHotels + " ("
                + col_tblFavHotels_id + " INTEGER PRIMARY KEY,"
                + col_tblFavHotels_Bool + " TEXT,"
                + col_tblFavHotels_HouseNumber + " TEXT,"
                + col_tblFavHotels_Latitude + " TEXT,"
                + col_tblFavHotels_Longitude + " TEXT,"
                + col_tblFavHotels_basement + " TEXT,"
                + col_tblFavHotels_descruption + " TEXT,"
                + col_tblFavHotels_floors + " TEXT,"
                + col_tblFavHotels_houseId + " UNIQUE,"
                + col_tblFavHotels_houseName + " TEXT,"
                + col_tblFavHotels_houseType + " TEXT,"
                + col_tblFavHotels_location + " TEXT,"
                + col_tblFavHotels_masterRooms + " TEXT,"
                + col_tblFavHotels_priority + " TEXT,"
                + col_tblFavHotels_privateSwimmingPool + " TEXT,"
                + col_tblFavHotels_rentrules + " TEXT,"
                + col_tblFavHotels_rooms + " TEXT,"
                + col_tblFavHotels_salon + " TEXT,"
                + col_tblFavHotels_toilet + " TEXT,"
                + col_tblFavHotels_typeOfPeopleAllowedToRent + " TEXT,"
                + col_tblFavHotels_whichLineOnSea + " TEXT,"
                + col_tblFavHotels_features + " TEXT,"
                + col_tblFavHotels_singleImagePhoto + " TEXT,"
                + col_tblFavHotels_photoGalleries + " TEXT)";
        db.execSQL(sqlTblFavHotels);

        Log.d(TAG, "TABLE CREATED " + tblFavHotels);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + tblHotels);
        onCreate(db);
    }
    //Method for Inserting Hotels Data
    public long insertHotelsData(Home_data hotelsData) {
        long result = -1;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(col_tblHotels_Bool, hotelsData.getBool());
        values.put(col_tblHotels_HouseNumber, hotelsData.getHouseNumber());
        values.put(col_tblHotels_Latitude, hotelsData.getLatitude());
        values.put(col_tblHotels_Longitude, hotelsData.getLongitude());
        values.put(col_tblHotels_basement, hotelsData.getBasement());
        values.put(col_tblHotels_descruption, hotelsData.getDescruption());
        values.put(col_tblHotels_floors, hotelsData.getFloors());
        values.put(col_tblHotels_houseId, hotelsData.getHouseId());
        values.put(col_tblHotels_houseName, hotelsData.getHouseName());
        values.put(col_tblHotels_houseType, hotelsData.getHouseType());
        values.put(col_tblHotels_location, hotelsData.getLocation());
        values.put(col_tblHotels_masterRooms, hotelsData.getMasterRooms());
        values.put(col_tblHotels_priority, hotelsData.getPriority());
        values.put(col_tblHotels_privateSwimmingPool, hotelsData.getPrivateSwimmingPool());
        values.put(col_tblHotels_rentrules, hotelsData.getRentrules());
        values.put(col_tblHotels_rooms, hotelsData.getRooms());
        values.put(col_tblHotels_salon, hotelsData.getSalon());
        values.put(col_tblHotels_toilet, hotelsData.getToilets());
        values.put(col_tblHotels_typeOfPeopleAllowedToRent, hotelsData.getTypeOfPeopleAllowedToRent());
        values.put(col_tblHotels_whichLineOnSea, hotelsData.getWhichLine());
        values.put(col_tblHotels_singleImagePhoto, hotelsData.getSingleImagePhoto());
        //values.put(col_tblHotels_features, hotelsData.getFeatures()[]);
        //values.put(col_tblHotels_photoGalleries, hotelsData.getSlug());
        result = db.insert(tblHotels, null, values);
        if (result == -1) {
            // db.update(tbl_news, values, col_news_postid+"=?", new
            // String[]{news.getPostid()});
        }
        Log.d("TAG_SQL", " HOTEL INSERT " + hotelsData.getHouseId());
        db.close();
        return result;
    };
    //Method for Retriving Hotels Data
    public ArrayList<Home_data> GetHotelsData() {
        ArrayList<Home_data> hotelsDatas = new ArrayList<Home_data>();
        ArrayList<String> features = new ArrayList<>();
        ArrayList<String> photoGalleries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tblHotels, null);


        if (cursor != null) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String Bool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Bool));
                String HouseNumber = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_HouseNumber));
                String Latitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Latitude));
                String Longitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Longitude));
                String basement = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_basement));
                String descruption = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_descruption));
                String floors = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_floors));
                String houseId = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseId));
                String houseName = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseName));
                String houseType = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseType));
                String location = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_location));
                String masterRooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_masterRooms));
                String priority = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_priority));
                String privateSwimmingPool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_privateSwimmingPool));
                String rentrules = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rentrules));
                String rooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rooms));
                String salon = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_salon));
                String toilet = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_toilet));
                String typeOfPeopleAllowedToRent = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_typeOfPeopleAllowedToRent));
                String whichLineOnSea = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_whichLineOnSea));
                String singleImagePhoto = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_singleImagePhoto));

				/*hotelsDatas.add(new HotelsData(Bool,HouseNumber,Double.valueOf(Latitude),Double.valueOf(Longitude),basement,descruption,floors,houseId,houseName,
						 houseType,location,masterRooms,Integer.parseInt(priority),privateSwimmingPool,rentrules,rooms,salon,toilet,
						 typeOfPeopleAllowedToRent,whichLineOnSea,features,photoGalleries,singleImagePhoto,"",""));*/
                Log.d("TAG_SQL", "houseId = " + houseId);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return hotelsDatas;
    }
    //Method for Deleting Hotels Data
    public int deleteHotelData()
    {
        int result = -1;
        SQLiteDatabase db = getWritableDatabase();
        result = db.delete(tblHotels, null, null);
        db.close();
        Log.d("TAG_SQL", "DELETED ALL HOTELS DATA");
        return result;
    };
    //Method for Inserting Favourite Hotels Data
    public long insertFavHotelsData(Home_data hotelsData) {
        long result = -1;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(col_tblFavHotels_Bool, hotelsData.getBool());
        values.put(col_tblFavHotels_HouseNumber, hotelsData.getHouseNumber());
        values.put(col_tblFavHotels_Latitude, hotelsData.getLatitude());
        values.put(col_tblFavHotels_Longitude, hotelsData.getLongitude());
        values.put(col_tblFavHotels_basement, hotelsData.getBasement());
        values.put(col_tblFavHotels_descruption, hotelsData.getDescruption());
        values.put(col_tblFavHotels_floors, hotelsData.getFloors());
        values.put(col_tblFavHotels_houseId, hotelsData.getHouseId());
        values.put(col_tblFavHotels_houseName, hotelsData.getHouseName());
        values.put(col_tblFavHotels_houseType, hotelsData.getHouseType());
        values.put(col_tblFavHotels_location, hotelsData.getLocation());
        values.put(col_tblFavHotels_masterRooms, hotelsData.getMasterRooms());
        values.put(col_tblFavHotels_priority, hotelsData.getPriority());
        values.put(col_tblFavHotels_privateSwimmingPool, hotelsData.getPrivateSwimmingPool());
        values.put(col_tblFavHotels_rentrules, hotelsData.getRentrules());
        values.put(col_tblFavHotels_rooms, hotelsData.getRooms());
        values.put(col_tblFavHotels_salon, hotelsData.getSalon());
        values.put(col_tblFavHotels_toilet, hotelsData.getToilets());
        values.put(col_tblFavHotels_typeOfPeopleAllowedToRent, hotelsData.getTypeOfPeopleAllowedToRent());
        values.put(col_tblFavHotels_whichLineOnSea, hotelsData.getWhichLine());
        values.put(col_tblFavHotels_singleImagePhoto, hotelsData.getSingleImagePhoto());
        values.put(col_tblFavHotels_features, hotelsData.getStrFeatures());
        values.put(col_tblFavHotels_photoGalleries, hotelsData.getStrPhotoGalleries());
        result = db.insert(tblFavHotels, null, values);
        if (result == -1) {
            // db.update(tbl_news, values, col_news_postid+"=?", new
            // String[]{news.getPostid()});
        }
        Log.d("TAG_SQL", " HOTEL INSERT " + hotelsData.getHouseId());
        db.close();
        return result;
    };
    //Method for Retriving Favourite Hotels Data
    public ArrayList<Home_data> GetFavHotelsData() {
        ArrayList<Home_data> hotelsDatas = new ArrayList<Home_data>();
        //ArrayList<String> features = new ArrayList<>();
        //ArrayList<String> photoGalleries = new ArrayList<>();
        String[] features = {},photoGalleries = {};
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tblFavHotels, null);


        if (cursor != null) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String Bool = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_Bool));
                String HouseNumber = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_HouseNumber));
                String Latitude = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_Latitude));
                String Longitude = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_Longitude));
                String basement = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_basement));
                String descruption = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_descruption));
                String floors = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_floors));
                String houseId = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_houseId));
                String houseName = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_houseName));
                String houseType = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_houseType));
                String location = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_location));
                String masterRooms = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_masterRooms));
                String priority = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_priority));
                String privateSwimmingPool = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_privateSwimmingPool));
                String rentrules = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_rentrules));
                String rooms = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_rooms));
                String salon = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_salon));
                String toilet = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_toilet));
                String typeOfPeopleAllowedToRent = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_typeOfPeopleAllowedToRent));
                String whichLineOnSea = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_whichLineOnSea));
                String singleImagePhoto = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_singleImagePhoto));
                String photos = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_photoGalleries));
                String featuresStr = cursor.getString(cursor
                        .getColumnIndex(col_tblFavHotels_features));

                hotelsDatas.add(new Home_data(Bool,HouseNumber,Latitude,Longitude,basement,descruption,floors,houseId,houseName,
                        houseType,location,masterRooms,priority,privateSwimmingPool,rentrules,rooms,salon,toilet,
                        typeOfPeopleAllowedToRent,whichLineOnSea,features,photoGalleries,singleImagePhoto,photos,featuresStr,0));
                Log.d("TAG_SQL", "houseId = " + houseId);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return hotelsDatas;
    }
    //Method for Deleting Favourite Hotels Data
    public int deleteFavHotelData()
    {
        int result = -1;
        SQLiteDatabase db = getWritableDatabase();
        result = db.delete(tblFavHotels, null, null);
        db.close();
        Log.d("TAG_SQL", "FAV DELETED ALL HOTELS DATA");
        return result;
    };
    public boolean deleteSingleFavHotelData(Home_data home)
    {
        int result = -1;
        SQLiteDatabase db = getWritableDatabase();
        result = db.delete(tblFavHotels, col_tblFavHotels_HouseNumber +"="+home.getHouseNumber(), null);
        db.close();
        Log.d("TAG_SQL", "FAV DELETED");
        return result>0;
    };

    public ArrayList<Home_data> GetHotelsWithPrivateSwimingPool(String data) {
        ArrayList<Home_data> hotelsDatas = new ArrayList<Home_data>();
        ArrayList<String> features = new ArrayList<>();
        ArrayList<String> photoGalleries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tblHotels+" WHERE "+col_tblHotels_privateSwimmingPool+" = '"+data+"'", null);

        if (cursor != null) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String Bool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Bool));
                String HouseNumber = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_HouseNumber));
                String Latitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Latitude));
                String Longitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Longitude));
                String basement = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_basement));
                String descruption = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_descruption));
                String floors = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_floors));
                String houseId = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseId));
                String houseName = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseName));
                String houseType = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseType));
                String location = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_location));
                String masterRooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_masterRooms));
                String priority = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_priority));
                String privateSwimmingPool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_privateSwimmingPool));
                String rentrules = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rentrules));
                String rooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rooms));
                String salon = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_salon));
                String toilet = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_toilet));
                String typeOfPeopleAllowedToRent = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_typeOfPeopleAllowedToRent));
                String whichLineOnSea = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_whichLineOnSea));
                String singleImagePhoto = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_singleImagePhoto));

				/*hotelsDatas.add(new HotelsData(Bool,HouseNumber,Double.valueOf(Latitude),Double.valueOf(Longitude),basement,descruption,floors,houseId,houseName,
						houseType,location,masterRooms,Integer.parseInt(priority),privateSwimmingPool,rentrules,rooms,salon,toilet,
						typeOfPeopleAllowedToRent,whichLineOnSea,features,photoGalleries,singleImagePhoto,"",""));*/
                Log.d("TAG_SQL", "houseId = " + houseId);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return hotelsDatas;
    }

    public ArrayList<Home_data> GetHotelsWithWhichLine(String data) {
        ArrayList<Home_data> hotelsDatas = new ArrayList<Home_data>();
        ArrayList<String> features = new ArrayList<>();
        ArrayList<String> photoGalleries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tblHotels+" WHERE "+col_tblHotels_whichLineOnSea+" = '"+data+"'", null);

        if (cursor != null) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String Bool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Bool));
                String HouseNumber = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_HouseNumber));
                String Latitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Latitude));
                String Longitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Longitude));
                String basement = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_basement));
                String descruption = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_descruption));
                String floors = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_floors));
                String houseId = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseId));
                String houseName = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseName));
                String houseType = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseType));
                String location = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_location));
                String masterRooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_masterRooms));
                String priority = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_priority));
                String privateSwimmingPool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_privateSwimmingPool));
                String rentrules = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rentrules));
                String rooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rooms));
                String salon = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_salon));
                String toilet = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_toilet));
                String typeOfPeopleAllowedToRent = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_typeOfPeopleAllowedToRent));
                String whichLineOnSea = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_whichLineOnSea));
                String singleImagePhoto = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_singleImagePhoto));

				/*hotelsDatas.add(new HotelsData(Bool,HouseNumber,Double.valueOf(Latitude),Double.valueOf(Longitude),basement,descruption,floors,houseId,houseName,
						houseType,location,masterRooms,Integer.parseInt(priority),privateSwimmingPool,rentrules,rooms,salon,toilet,
						typeOfPeopleAllowedToRent,whichLineOnSea,features,photoGalleries,singleImagePhoto,"",""));*/
                Log.d("TAG_SQL", "houseId = " + houseId);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return hotelsDatas;
    }

    public ArrayList<Home_data> GetHotelsWithHouseType(String data) {
        ArrayList<Home_data> hotelsDatas = new ArrayList<Home_data>();
        ArrayList<String> features = new ArrayList<>();
        ArrayList<String> photoGalleries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tblHotels+" WHERE "+col_tblHotels_houseType+" = '"+data+"'", null);

        if (cursor != null) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String Bool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Bool));
                String HouseNumber = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_HouseNumber));
                String Latitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Latitude));
                String Longitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Longitude));
                String basement = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_basement));
                String descruption = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_descruption));
                String floors = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_floors));
                String houseId = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseId));
                String houseName = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseName));
                String houseType = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseType));
                String location = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_location));
                String masterRooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_masterRooms));
                String priority = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_priority));
                String privateSwimmingPool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_privateSwimmingPool));
                String rentrules = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rentrules));
                String rooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rooms));
                String salon = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_salon));
                String toilet = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_toilet));
                String typeOfPeopleAllowedToRent = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_typeOfPeopleAllowedToRent));
                String whichLineOnSea = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_whichLineOnSea));
                String singleImagePhoto = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_singleImagePhoto));

				/*hotelsDatas.add(new HotelsData(Bool,HouseNumber,Double.valueOf(Latitude),Double.valueOf(Longitude),basement,descruption,floors,houseId,houseName,
						houseType,location,masterRooms,Integer.parseInt(priority),privateSwimmingPool,rentrules,rooms,salon,toilet,
						typeOfPeopleAllowedToRent,whichLineOnSea,features,photoGalleries,singleImagePhoto,"",""));*/
                Log.d("TAG_SQL", "houseId = " + houseId);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return hotelsDatas;
    }

    public ArrayList<Home_data> GetHotelsWithTypeOfPeopleAllowed(String data) {
        ArrayList<Home_data> hotelsDatas = new ArrayList<Home_data>();
        ArrayList<String> features = new ArrayList<>();
        ArrayList<String> photoGalleries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tblHotels+" WHERE "+col_tblHotels_typeOfPeopleAllowedToRent+" = '"+data+"'", null);

        if (cursor != null) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String Bool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Bool));
                String HouseNumber = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_HouseNumber));
                String Latitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Latitude));
                String Longitude = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_Longitude));
                String basement = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_basement));
                String descruption = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_descruption));
                String floors = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_floors));
                String houseId = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseId));
                String houseName = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseName));
                String houseType = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_houseType));
                String location = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_location));
                String masterRooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_masterRooms));
                String priority = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_priority));
                String privateSwimmingPool = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_privateSwimmingPool));
                String rentrules = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rentrules));
                String rooms = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_rooms));
                String salon = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_salon));
                String toilet = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_toilet));
                String typeOfPeopleAllowedToRent = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_typeOfPeopleAllowedToRent));
                String whichLineOnSea = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_whichLineOnSea));
                String singleImagePhoto = cursor.getString(cursor
                        .getColumnIndex(col_tblHotels_singleImagePhoto));

				/*hotelsDatas.add(new HotelsData(Bool,HouseNumber,Double.valueOf(Latitude),Double.valueOf(Longitude),basement,descruption,floors,houseId,houseName,
						houseType,location,masterRooms,Integer.parseInt(priority),privateSwimmingPool,rentrules,rooms,salon,toilet,
						typeOfPeopleAllowedToRent,whichLineOnSea,features,photoGalleries,singleImagePhoto,"",""));*/
                Log.d("TAG_SQL", "houseId = " + houseId);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return hotelsDatas;
    }
}
