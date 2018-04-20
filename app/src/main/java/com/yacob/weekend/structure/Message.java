package com.yacob.weekend.structure;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

/**
 * Created by Dhahri on 23/03/2018.
 */
public class Message implements IMessage {
        public String id, payload, type;
        Date timestamp;
        Author author;

        public Message (String id,String payload,String type,Author author,Date timestamp){

            this.payload=payload;
            this.type=type;
            this.author=author;
            this.id=id;
            this.timestamp = timestamp;


        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getText() {
            return payload;
        }

        @Override
        public Author getUser() {
            return author;
        }

        @Override
        public Date getCreatedAt() {
            return timestamp;
        }
    }



