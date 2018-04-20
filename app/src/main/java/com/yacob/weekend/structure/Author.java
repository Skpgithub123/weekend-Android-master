package com.yacob.weekend.structure;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by Dhahri on 23/03/2018.
 */

public class Author implements IUser {
    private String id,name,avatar;

    public Author(String id,String name,String avatar){
        this.id=id;
        this.name=name;
        this.avatar=avatar;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }
}
