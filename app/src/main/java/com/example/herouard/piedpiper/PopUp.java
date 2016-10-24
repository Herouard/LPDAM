package com.example.herouard.piedpiper;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by lpsil02 on 18/10/2016.
 */
public class PopUp extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.popLayout);
        RelativeLayout ll2 = (RelativeLayout) findViewById(R.id.popUpInerLayout);

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
        ll2.setBackgroundResource(R.drawable.heart);
    }
}
