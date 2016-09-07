package com.example.physicaltests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        //底部导航栏
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.Tab);
        bottomNavigationBar .addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp,"Home"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_location_on_white_24dp, "Location"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_music_note_white_24dp,"Music"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tv_white_24dp,"Movies & TV"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_favorite_white_24dp,"Favorite"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
            }
        });

    }
}
