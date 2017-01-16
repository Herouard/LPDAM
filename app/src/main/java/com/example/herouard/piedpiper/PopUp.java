package com.example.herouard.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lpsil02 on 18/10/2016.
 */
public class PopUp extends Activity {

    //Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //realm = Realm.getInstance(this);

        setContentView(R.layout.popupwindow);
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.popLayout);
        RelativeLayout ll2 = (RelativeLayout) findViewById(R.id.popUpInerLayout);
        TextView img = (TextView) findViewById(R.id.textView5);
        TextView t1 = (TextView) findViewById(R.id.textView3);
        TextView t2 = (TextView) findViewById(R.id.textView4);

        Intent intent = getIntent();
        int teamchosen = intent.getIntExtra("team",0);
        int numberOfShots = intent.getIntExtra("numberOfShots",1);
/*
        RealmResults<BDD_Score> result = realm.where(BDD_Score.class)
                .findAll();

        int idKey = 0;
        for (BDD_Score bdd_score : result) {
            idKey = bdd_score.getId();
        }
        idKey++;

        realm.beginTransaction();
        BDD_Score bdd_score = realm.createObject(BDD_Score.class);
        bdd_score.setScore(5);
        bdd_score.setScore(10);
        realm.commitTransaction();
        realm.close();
*/
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return false;
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, height);
        getWindow().setBackgroundDrawableResource(R.color.translucent_black);
        ll2.setBackgroundColor(Color.TRANSPARENT);
        t2.setText("En "+numberOfShots+" tirs !");
        if(teamchosen==1){
            img.setBackgroundResource(R.drawable.win1);
            t1.setTextColor(Color.parseColor("#ff0000"));
            t2.setTextColor(Color.parseColor("#ff0000"));
        } else {
            img.setBackgroundResource(R.drawable.win2);
            t1.setTextColor(Color.parseColor("#30ff00"));
            t2.setTextColor(Color.parseColor("#30ff00"));
        }
        //Color.parseColor("#ff0000")//Color.parseColor("#30ff00")
    }
}
