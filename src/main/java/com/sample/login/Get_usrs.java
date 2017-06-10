package com.sample.login;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.security.AccessController.getContext;

public class Get_usrs extends AppCompatActivity {

    private DatabaseReference mdatabaseref;
    private List<Imageuploading> imglist;
    private ListView listView2;
    private ImageListAdapter imageListAdapter;
    private ProgressDialog progressDialog;
    String xyz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_usrs);
        Intent intent=getIntent();
        xyz=intent.getStringExtra("loader");
        imglist=new ArrayList<>();
        listView2=(ListView)findViewById(R.id.listView2);
        progressDialog=new ProgressDialog(Get_usrs.this);
        progressDialog.setMessage("plzz wait loading image list...");
        progressDialog.show();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        final String em = firebaseUser.getEmail();

        mdatabaseref= FirebaseDatabase.getInstance().getReference();
        mdatabaseref = mdatabaseref.child("images");
        mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Imageuploading img = dataSnapshot1.getValue(Imageuploading.class);
                    if(img.getEmail().equals(xyz))
                    imglist.add(img);
                }
                Collections.reverse(imglist);
                imageListAdapter=new ImageListAdapter(Get_usrs.this,R.layout.image_item,imglist);
                listView2.setAdapter(imageListAdapter);
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
