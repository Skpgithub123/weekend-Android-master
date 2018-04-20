package com.yacob.weekend.structure;

/**
 * Created by Dhahri on 17/01/2018.
 */

public class Profile_data {
    private String  email,phoneNumber,name;
    private int drawable;
    public Profile_data(String name,int drawable){
        this.drawable=drawable;
        this.name=name;
    }
    public void setName (String email){this.name=name;}
    public void setDrawable(int drawable){this.drawable=drawable;}
    public String getName(){return name;}
    public int getDrawable (){ return drawable;}

}
