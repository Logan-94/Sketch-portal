package com.sample.login;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

@SuppressWarnings("VisibleForTests")
public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button logout;
    private FirebaseAuth firebaseAuth;
    private StorageReference mstorage;
    private DatabaseReference mdatabse;
    private Button imagelists;
    private ImageView imageView;
    private EditText imagetext;
    private Uri imageuri;
    public static final String FB_STORAGEPATH="image/";
    public static final String FB_DATABASEPATH="image";
    private Button browse;
    private Button upload;
    public static final int REQUEST_CODE=1234;
    private EditText desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        logout=(Button)findViewById(R.id.button2);
        mstorage= FirebaseStorage.getInstance().getReference();
        mstorage = mstorage.child("image");
        mdatabse= FirebaseDatabase.getInstance().getReference();
        imageView=(ImageView)findViewById(R.id.imageView);
        imagetext=(EditText)findViewById(R.id.image_text);
        browse=(Button)findViewById(R.id.browse_btn);
        upload=(Button)findViewById(R.id.upload_btn);
        logout.setOnClickListener(this);
        browse.setOnClickListener(this);
        upload.setOnClickListener(this);
        imagelists=(Button)findViewById(R.id.listofimages);
        imagelists.setOnClickListener(this);
        desc=(EditText)findViewById(R.id.des_text);

    }



    public void imagebrowse()
    {
        Intent intent=new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            imageuri=data.getData();
            try {
                Bitmap bm= MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                imageView.setImageBitmap(bm);
            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }catch (IOException e)
            {
                 e.printStackTrace();
            }

        }
    }
    @SuppressWarnings("VisibleForTests")
    public String getImageExt(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void imageupload()
    {
        if(imageuri!=null)
        {
            final ProgressDialog dialog=new ProgressDialog(this);
            dialog.setTitle("uploading...");
            dialog.show();

            StorageReference ref=mstorage.child(imagetext.getText()+"."+ getImageExt(imageuri));
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    dialog.dismiss();
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                    String email=firebaseUser.getEmail();
                    Toast.makeText(getApplicationContext(),"Imageuploaded.. ",Toast.LENGTH_LONG).show();
                    Imageuploading imageupload=new Imageuploading(imagetext.getText().toString(),taskSnapshot.getDownloadUrl().toString(),email,desc.getText().toString());
                    mdatabse.child("images").push().setValue(imageupload);
                    startActivity(new Intent(getApplicationContext(),Main3Activity.class));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress =(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    dialog.setMessage("uploaded "+ (int)progress+("%"));


                }
            });

        }
        else {
            Toast.makeText(getApplicationContext(),"please select image..",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClick(View v) {
        if(v==logout)
        {
            firebaseAuth = FirebaseAuth.getInstance();
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("finish",true);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            firebaseAuth.signOut();
            startActivity(i);
            finish();
        }
        if(v==browse)
        {
            imagebrowse();
        }
        if(v==upload)
        {
            imageupload();

        }
       /* if(v==imagelists)
        {
            startActivity(new Intent(getApplicationContext(),ImageListActivity.class));
        }*/
    }
}
