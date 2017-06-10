package com.sample.login.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sample.login.ImageListAdapter;
import com.sample.login.Imageuploading;
import com.sample.login.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sample.login.R.id.listView;

public class frag_uploads extends Fragment {

    private DatabaseReference mdatabaseref;
    private List<Imageuploading> imglist;
    private ListView listView1;
    private ImageListAdapter imageListAdapter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_frag_uploads,container,false);
        imglist=new ArrayList<>();
        listView1=(ListView)v.findViewById(R.id.listView2);
        progressDialog=new ProgressDialog(getActivity());
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
                progressDialog.dismiss();

                imglist.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Imageuploading img =snapshot.getValue(Imageuploading.class);
                    if(img.getEmail().equals(em))
                    {
                        imglist.add(img);
                    }

                }
                Collections.reverse(imglist);
                imageListAdapter=new ImageListAdapter(getActivity(),R.layout.image_item,imglist);
                listView1.setAdapter(imageListAdapter);

                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(),imglist.get(position).getEmail(),Toast.LENGTH_SHORT).show();
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