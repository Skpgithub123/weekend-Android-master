package com.yacob.weekend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.yacob.weekend.structure.Author;
import com.yacob.weekend.structure.Message;

import java.util.Calendar;
import java.util.Date;

public class Chat extends AppCompatActivity {
    public MessagesList messagesList;
    public MessageInput input;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference mRootRef,mchild;
    String senderId;
    Author user,admin;
    MessagesListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messagesList = findViewById(R.id.messagesList);
        input = findViewById(R.id.input);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            senderId = auth.getCurrentUser().getUid();
            adapter = new MessagesListAdapter<>(senderId, null);
            user = new Author(senderId,auth.getCurrentUser().getDisplayName().toString(),"");
            admin = new Author("admin","Weekend","");
            messagesList.setAdapter(adapter);
            Log.d("auth", auth.getCurrentUser().getEmail());
            database = FirebaseDatabase.getInstance();
            mRootRef = database.getReference("/No5tha");
            mchild = mRootRef.child("userProfile/");
            Query query = mRootRef.child("conversations/messages/"+senderId).orderByChild("timestamp");
            //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));

            //Retriving data from Firebase Path
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            // do with your result
                            Date date = new Date();
                            date.setTime(Long.parseLong(ds.child("timestamp").getValue().toString()));
                           // Calendar calendar = Calendar.getInstance();
                           // calendar.setTimeInMillis(Long.parseLong(ds.child("timestamp").getValue().toString()));
                            if(ds.child("senderID").getValue().toString().equals(senderId)){
                            Message message = new Message("1",ds.child("payload").getValue().toString(),"text",user, date);
                            adapter.addToStart( message,true);
                            }else{
                                Message message = new Message("1",ds.child("payload").getValue().toString(),"text",admin, date);
                                adapter.addToStart( message,true);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            input.setInputListener(new MessageInput.InputListener() {
                @Override
                public boolean onSubmit(CharSequence input) {
                   long myCurrentTimeMillis = System.currentTimeMillis();
                    Message message = new Message("1",input.toString(),"text",user, Calendar.getInstance().getTime());
                    adapter.addToStart( message,true);
                    mRootRef.child("conversations").child("messages").child(senderId).child(String.valueOf(myCurrentTimeMillis)).child("payload").setValue(input.toString());
                    mRootRef.child("conversations").child("messages").child(senderId).child(String.valueOf(myCurrentTimeMillis)).child("senderID").setValue(senderId);
                    mRootRef.child("conversations").child("messages").child(senderId).child(String.valueOf(myCurrentTimeMillis)).child("timestamp").setValue(String.valueOf(myCurrentTimeMillis));
                    mRootRef.child("conversations").child("messages").child(senderId).child(String.valueOf(myCurrentTimeMillis)).child("type").setValue("text");
                    return true;
                }
            });

        } else {
            // not signed in
            Log.d("ici", "onCreate: ");
        }

    }

}
