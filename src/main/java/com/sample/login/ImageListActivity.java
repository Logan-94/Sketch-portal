package com.sample.login;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {

    private DatabaseReference mdatabaseref;
    private List<Imageuploading>imglist;
    private ListView listView;
    private ImageListAdapter imageListAdapter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_list);

        imglist=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listView);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("plzz wait loading image list...");
        progressDialog.show();

        mdatabaseref= FirebaseDatabase.getInstance().getReference();
        mdatabaseref = mdatabaseref.child("images");
        mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Imageuploading img =snapshot.getValue(Imageuploading.class);
                    imglist.add(img);

                }
                imageListAdapter=new ImageListAdapter(ImageListActivity.this,R.layout.image_item,imglist);
                listView.setAdapter(imageListAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });

    }
}
