package com.example.herouard.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

public class MenuFragment extends Fragment implements View.OnClickListener{

    OnActionListener mListener;
    int color, VitesseValue, EntiteValue;
    TextView[] shipsList;
    TextView[] teamList;
    int teamChosen;
    int shipChosen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_menu_fragment, null);


        color = Color.RED;

        VitesseValue = 0;
        EntiteValue = 5;
        teamChosen = 0;
        shipChosen = 0;

        NumberPicker np = (NumberPicker) rootView.findViewById(R.id.Np);
        np.setMinValue(5);
        np.setMaxValue(20);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                EntiteValue=newVal;
                mListener.onAction(EntiteValue,VitesseValue,teamChosen,shipChosen);
            }
        });

        SeekBar sb1 = (SeekBar) rootView.findViewById(R.id.seekBar);
        sb1.setMax(10);
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                VitesseValue=progress;
                mListener.onAction(EntiteValue,VitesseValue,teamChosen,shipChosen);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        teamList = new TextView[2];
        teamList[0] = (TextView) rootView.findViewById(R.id.tv1);
        teamList[0].setOnClickListener(this);
        teamList[1] = (TextView) rootView.findViewById(R.id.tv2);
        teamList[1].setOnClickListener(this);

        shipsList = new TextView[4];
        shipsList[0] = (TextView) rootView.findViewById(R.id.tv3);
        shipsList[0].setOnClickListener(this);
        shipsList[1] = (TextView) rootView.findViewById(R.id.tv4);
        shipsList[1].setOnClickListener(this);
        shipsList[2] = (TextView) rootView.findViewById(R.id.tv5);
        shipsList[2].setOnClickListener(this);
        shipsList[3] = (TextView) rootView.findViewById(R.id.tv6);
        shipsList[3].setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //----Choix de l'equipe -------------------------------
                case R.id.tv1:

                    teamChosen = 0;
                    setShipsForTeam(teamChosen);
                    mListener.onAction(EntiteValue,VitesseValue,teamChosen,shipChosen);

                    break;

                case R.id.tv2:

                    teamChosen = 1;
                    setShipsForTeam(teamChosen);
                    mListener.onAction(EntiteValue,VitesseValue,teamChosen,shipChosen);

                    break;

            //----Choix du vaisseau -------------------------------
            case R.id.tv3:

                shipChosen=0;
                mListener.onAction(EntiteValue,VitesseValue,teamChosen,shipChosen);

                break;

            case R.id.tv4:

                shipChosen=1;
                mListener.onAction(EntiteValue,VitesseValue,teamChosen,shipChosen);

                break;

            case R.id.tv5:

                shipChosen=2;
                mListener.onAction(EntiteValue,VitesseValue,teamChosen,shipChosen);

                break;

            case R.id.tv6:

                shipChosen=3;
                mListener.onAction(EntiteValue,VitesseValue,teamChosen,shipChosen);

                break;

            default:

                    break;
        }
    }

    public void setShipsForTeam(int teamChosen){
        if(teamChosen==1){
            shipsList[0].setBackgroundResource(R.drawable.ea);
            shipsList[1].setBackgroundResource(R.drawable.eb);
            shipsList[2].setBackgroundResource(R.drawable.ec);
            shipsList[3].setBackgroundResource(R.drawable.ed);
        } else {
            shipsList[0].setBackgroundResource(R.drawable.aa);
            shipsList[1].setBackgroundResource(R.drawable.ab);
            shipsList[2].setBackgroundResource(R.drawable.ac);
            shipsList[3].setBackgroundResource(R.drawable.ad);
        }
    }

    // Container Activity must implement this kind of interface
    public interface OnActionListener {
        public void onAction(int entite, int vitesse, int teamChosen, int shipChosen);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnActionListener");
        }
    }

}