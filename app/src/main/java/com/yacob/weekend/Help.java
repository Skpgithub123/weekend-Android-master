package com.yacob.weekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Help extends AppCompatActivity
{
    Toolbar mToolbar;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    DatabaseReference mRootRef,mchild;
    FirebaseDatabase database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Help");
        //mToolbar.setLogo(R.drawable.icon);
        mToolbar.setNavigationOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();

                    }
                });
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("/No5tha");
        mchild = mRootRef.child("freqQ/");


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        //listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
         listAdapter = new com.yacob.weekend.adapter.ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

       /* // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });*/

       /* // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });*/
    }

    /*
	 * Preparing the list data
	 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataHeader.clear();
        mchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String question = (ds.child("question").getValue().toString());
                    //String photo = (ds.child("Bool").getValue().toString());
                    listDataHeader.add(question);
                    String awnser = (ds.child("answer").getValue().toString());
                    List<String> top250 = new ArrayList<String>();
                    top250.add(awnser);
                    listDataChild.put(question, top250);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /* Adding child data
        for (int i = 1; i < 11; i++) {
            listDataHeader.add("Question "+i);
        }


        // Adding child data
        top250.add("Answer dsjfjdskfn dsfnjdsknf djnfjdsknf dsnfjdksnf djsfnjdsnf djsfnsdjnf dfndjnf dsnfdsjnf dfndsjfn dfndjnf dfnjndsjf dfnjdsnfjdn");

        for (int i = 0; i < listDataHeader.size(); i++) {
            listDataChild.put(listDataHeader.get(i), top250); // Header, Child data
        }*/

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Help.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
