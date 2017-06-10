package com.sample.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mdatabase;
    private EditText mname;
    private EditText memail;
    private TextView mview;
    private Button mfirebasebtn;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthListner;
    private TextView mage;
    private TextView forgot;
    private TextView mSignUp;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    //private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mfirebasebtn = (Button) findViewById(R.id.fb_btn);

        mdatabase = FirebaseDatabase.getInstance().getReference();
        mname = (EditText) findViewById(R.id.user_text);
        mSignUp=(TextView)findViewById((R.id.signup_text));
        memail = (EditText) findViewById(R.id.password_text);
        forgot=(TextView)findViewById(R.id.forgot_text);

        mauth =FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        if(mauth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), Main3Activity.class));
        }

       // mview = (TextView) findViewById(R.id.ret_text);
        //mage = (TextView) findViewById(R.id.age_text);


  //      mfirebasebtn.setOnClickListener(new View.OnClickListener() {
      //      @Override
    //        public void onClick(View v) {
//                mdatabase.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String xyz = dataSnapshot.child("Name").getValue().toString();
//                        mview.setText("Name :" + xyz);
//                        String s = dataSnapshot.child("Age").getValue().toString();
//                        mage.setText("Age:" + s);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//                Toast.makeText(getApplication(), "ygbkmk", Toast.LENGTH_SHORT).show();

                // log obj = new log("mohit");
                // System.out.println("vgbdxn");
                // System.out.println(mdatabase.toString());


          //  }
        //});
        mfirebasebtn.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        forgot.setOnClickListener(this);
//
    }
    public void passwordreset() {
        String emailaddress = mname.getText().toString().trim();
        mauth.sendPasswordResetEmail(emailaddress);
    }


    public void Startsignin()
    {

        String email = mname.getText().toString().trim();
        String password = memail.getText().toString().trim();
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
        {
            Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_LONG).show();
            return;
        }

        else {
            progressDialog.setMessage("Signing In..");
            progressDialog.show();

            //System.out.println("kuy"

            mauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "SignIn failed", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    else
                    {
                        if(task.isSuccessful())
                        {
                           // progressDialog.setMessage("Signing In...");
                           // progressDialog.show();
                            startActivity(new Intent(MainActivity.this,Main3Activity.class));

                        }
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mfirebasebtn){
            Startsignin();
        }
        if(v == mSignUp){

            startActivity(new Intent(this, screen2.class));

        }
        if(v==forgot)
        {
            Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_LONG).show();
            passwordreset();
        }
    }
}