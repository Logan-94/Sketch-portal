package com.sample.login.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.login.Details;
import com.sample.login.ImageListActivity;
import com.sample.login.ImageListAdapter;
import com.sample.login.Imageuploading;
import com.sample.login.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class frag_home extends Fragment {

    private DatabaseReference mdatabaseref;
    private List<Imageuploading> imglist;
    private ListView listView;
    private ImageListAdapter imageListAdapter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_frag_home,container,false);
        imglist=new ArrayList<>();
        listView=(ListView)v.findViewById(R.id.listView);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("loading image list...");
        progressDialog.show();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        final String em = firebaseUser.getEmail();




        mdatabaseref= FirebaseDatabase.getInstance().getReference();
        mdatabaseref = mdatabaseref.child("images");
        mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                imglist.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Imageuploading img =snapshot.getValue(Imageuploading.class);
                        imglist.add(img);
                }
                Collections.reverse(imglist);
                imageListAdapter=new ImageListAdapter(getActivity(),R.layout.image_item,imglist);
                listView.setAdapter(imageListAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       // Toast.makeText(getContext(),imglist.get(position).getEmail(),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getContext(),Details.class);
                        intent.putExtra("name",imglist.get(position).getName());
                        intent.putExtra("email",imglist.get(position).getEmail());
                        intent.putExtra("url",imglist.get(position).getUrl());
                        intent.putExtra("desc",imglist.get(position).getDesc());

                        startActivity(intent);
                       // Fragment fragment3=new frag_details();
                       // FragmentManager fragmentManager= getSupportFragmentManager();

                        // Glide.with(context).load(imglist.get(position).getUrl()).into(imageView);
                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}