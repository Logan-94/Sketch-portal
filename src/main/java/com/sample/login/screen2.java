package com.sample.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity ;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.ArrayList;

public class screen2 extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mdatabase;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthListner;
    private Button Signup;
    private EditText Email,Password;
    private TextView Signin;
    ProgressDialog progressDialog;
   // mdatabase = FirebaseDatabase.getInstance().getReference();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
      mdatabase = FirebaseDatabase.getInstance().getReference();
        Signup=(Button) findViewById(R.id.signup_btn);
        Email=(EditText)findViewById(R.id.reg_email);
        Password=(EditText)findViewById((R.id.reg_pass));
        Signin=(TextView)findViewById((R.id.signin_text));
        progressDialog=new ProgressDialog(this);

        mauth=FirebaseAuth.getInstance();
       Signup.setOnClickListener(this);
        Signin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==Signup){
            register();
        }
        if(v==Signin)
        {
            finish();
            startActivity(new Intent(screen2.this,MainActivity.class));
        }

    }

    public void register()
    {
        System.out.println("hgnvnh");
        String emailaddr=Email.getText().toString().trim();
        String password =Password.getText().toString().trim();

        if(TextUtils.isEmpty(emailaddr)||TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_LONG).show();
            return;

        }
        progressDialog.setMessage("Registering");
        progressDialog.show();

         mauth.createUserWithEmailAndPassword(emailaddr, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(screen2.this, "Signup Successful", Toast.LENGTH_LONG).show();
                    String emailentered = Email.getText().toString().trim();
                    String passwordentered = Password.getText().toString().trim();
                    Users save =new Users(emailentered,passwordentered);
                    mdatabase.child("users").push().setValue(save);
                    mauth.signOut();
                    startActivity(new Intent(screen2.this, MainActivity.class));


                }
                else {
                    Toast.makeText(getApplicationContext(),"fields cant be empty",Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
