package com.example.toando.appbanhang.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.example.toando.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        ActionBar(); //nut menu
        ActionViewFlipper(); //chay banner quang cao
    }

    private void ActionViewFlipper() {
        ArrayList<String> quangcao = new ArrayList<>(); //tao mang kieu list
        quangcao.add("https://cdn.tgdd.vn/2020/06/banner/S20-800-300-800x300-3.png");
        quangcao.add("https://cdn.tgdd.vn/2020/05/banner/60749522-6C82-4FF3-BEF0-76FD21BB4D25-800x300.png");
        quangcao.add("https://cdn.tgdd.vn/2020/06/banner/800-300-800x300-26.png");
        //gan link vao ImageView sau do gan imageView vao viewFlipper
        for (int i = 0; i<quangcao.size(); i++){

            ImageView imageView = new ImageView(getApplicationContext());
            //su dung thu viet de lay hinh anh tu url
            Picasso.with(getApplicationContext()).load(quangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        //bat su kien cho viewFlipper tu chay
        viewFlipper.setFlipInterval(5000); //chay trong 5s
        viewFlipper.setAutoStart(true); //tu dong chay
        //hieu ung banner in and out
        Animation anim_banner_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation anim_banner_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(anim_banner_in);
        viewFlipper.setOutAnimation(anim_banner_out);
    }

    private  void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa(){
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        recyclerViewHome = (RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewHome = (ListView) findViewById(R.id.listviewHome);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
    }
}
