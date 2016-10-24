package com.example.herouard.piedpiper;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener, MenuFragment.OnActionListener, GameFragment.OnWinListener {

    ViewPager mViewPager;
    ExamplePagerAdapter mExemplePagerAdaper;
    GameFragment tf1;
    MenuFragment npf1;
    ActionBar actionBar;
    Vibrator v;
    MediaPlayer mMediaPlayer;

    //param pour la next view
    int entite,vitesse,teamChosen,shipChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        tf1 = new GameFragment();
        npf1 = new MenuFragment();

        mViewPager =(ViewPager) findViewById(R.id.pager);

        mExemplePagerAdaper = new ExamplePagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mExemplePagerAdaper);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Menu")
                        .setTabListener(this)
        );
        actionBar.addTab(
                actionBar.newTab()
                        .setText("Jeu")
                        .setTabListener(this));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onAction(int entite, int vitesse, int teamChosen, int shipChosen) {

        this.entite=entite;
        this.vitesse=vitesse;
        this.teamChosen=teamChosen;
        this.shipChosen=shipChosen;

        tf1.setText(this.entite,this.vitesse,this.teamChosen,this.shipChosen,false);

    }

    @Override
    public void onWin(boolean winStatus) {
        if(winStatus==true){
            mMediaPlayer = MediaPlayer.create(this,R.raw.xplosion);
            mMediaPlayer.start();
            v.vibrate(500);
            //On en est la avec ce truc a verifier qu'il ne soient pas vide avant de le lancer
            tf1.setText(this.entite,this.vitesse,this.teamChosen,this.shipChosen,true);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("win: ", "WIIIIIIIIIIN");
                    mViewPager.setCurrentItem(0,false);
                    startActivity(new Intent(MainActivity.this,PopUp.class));
                }
            }, 1000);
        }
        else{
            mMediaPlayer = MediaPlayer.create(this,R.raw.laser);
            mMediaPlayer.start();
            v.vibrate(100);
        }
    }


    class ExamplePagerAdapter extends FragmentStatePagerAdapter {

        public ExamplePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            switch(i){
                case 0 : // Definir ici les fragment à utiliser
                    return npf1;
                default:
                    return tf1;
            }
        }
        @Override
        public int getCount() {
            //le nombre d’onglet
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return "myFrag";
        }
    }


}