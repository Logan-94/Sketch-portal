package com.sample.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Details extends AppCompatActivity implements View.OnClickListener {

    private TextView name;
    private TextView email;
    private ImageView image_details;
    private TextView desc_det;
    public String email_det;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
        name=(TextView)findViewById(R.id.name_details);
        email=(TextView)findViewById(R.id.email_details);
        image_details=(ImageView)findViewById(R.id.image_details);
        String name_det=intent.getStringExtra("name");
        email_det=intent.getStringExtra("email");
        String url=intent.getStringExtra("url");
        desc_det=(TextView)findViewById(R.id.desc_details);
        name.setText(name_det);
        email.setText(email_det);
        String description =intent.getStringExtra("desc");
        desc_det.setText(description);

        Glide.with(getApplicationContext()).load(url).into(image_details);

        email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==email)
        {
            Intent i = new Intent(getApplicationContext(),Get_usrs.class);
            i.putExtra("loader",email_det);
            startActivity(i);

        }


    }
}
