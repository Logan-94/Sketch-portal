package com.sample.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ImageListAdapter extends ArrayAdapter<Imageuploading> {

    private Activity context;
    private int resource;
    private List<Imageuploading> listimage;
    public ImageListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Imageuploading> objects) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        listimage=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        @SuppressLint("ViewHolder") View v=inflater.inflate(resource,null);
        TextView tvname=(TextView) v.findViewById(R.id.tvimage);
        ImageView imageView=(ImageView)v.findViewById(R.id.imgView);
        TextView tvemail=(TextView)v.findViewById(R.id.tvemail);
        tvname.setText(listimage.get(position).getName());
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String email=firebaseUser.getEmail();
        tvemail.setText(listimage.get(position).getEmail());
        Glide.with(context).load(listimage.get(position).getUrl()).into(imageView);
        return v;

    }
}
