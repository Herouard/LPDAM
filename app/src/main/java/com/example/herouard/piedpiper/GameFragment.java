package com.example.herouard.piedpiper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class GameFragment extends Fragment implements View.OnTouchListener{

    int entite,vitesse, teamChosen, shipChosen,laserColor,numberOfShots;
    Bitmap[] bm;
    int intru;
    AnimationView animV;
    LinearLayout Container;
    OnWinListener winListener;

    int[] tabPref;
    C_LocalData localData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intru = 0;
        laserColor = Color.GREEN;
        numberOfShots = 0;

        localData = new C_LocalData(getContext());

        if(localData != null){
            this.tabPref=localData.recupPref();
            this.entite = tabPref[0];
            this.teamChosen = tabPref[1];
            this.shipChosen = tabPref[2];
            this.vitesse= tabPref[3]+15;
        }
        else{
            vitesse = 15;
            entite = 5;
            teamChosen = 0;
            shipChosen = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Container = (LinearLayout) inflater.inflate(R.layout.activity_game_fragment, null);
        bm = new Bitmap[entite];
        for(int j=0; j<bm.length;j++){
            if(j==intru){
                bm[j] = BitmapFactory.decodeResource(getResources(), getDrawableFromIds(1,shipChosen,false));
            } else{
                bm[j] = BitmapFactory.decodeResource(getResources(), getDrawableFromIds(teamChosen,shipChosen,false));
            }
        }
        if(teamChosen==0){
            laserColor=Color.GREEN;
        } else {laserColor=Color.RED;}

        animV = new AnimationView(getContext(),bm,vitesse,intru,laserColor);
        Container.addView(animV);
        animV.setOnTouchListener(this);

        return Container;
    }

    public void setText(int entite, int vitesse, int teamChosen, int shipChosen){
        this.entite = entite;
        this.vitesse = 15+vitesse;
        this.teamChosen = teamChosen;
        this.shipChosen = shipChosen;

        bm = new Bitmap[this.entite];
        for(int j=0; j<bm.length;j++){
            if(j==intru){
                bm[j] = BitmapFactory.decodeResource(getResources(), getDrawableFromIds(this.teamChosen,this.shipChosen,true));
            } else{
                bm[j] = BitmapFactory.decodeResource(getResources(), getDrawableFromIds(this.teamChosen,this.shipChosen,false));
            }
        }

        if(teamChosen==0){
            laserColor=Color.GREEN;
        } else {laserColor=Color.RED;}


        animV.InitParam(this.bm,this.vitesse,this.intru,this.laserColor);
    }

    public int getDrawableFromIds(int teamChosen, int shipChosen, boolean isIntruder){

            if(!isIntruder){
                if(teamChosen==1){
                    switch (shipChosen) {
                        case 0:
                            return R.drawable.ea;
                        case 1:
                            return R.drawable.eb;
                        case 2:
                            return R.drawable.ec;
                        case 3:
                            return R.drawable.ed;
                        default:
                            return R.drawable.ea;
                    }
                }else{
                    switch (shipChosen) {
                        case 0:
                            return R.drawable.aa;
                        case 1:
                            return R.drawable.ab;
                        case 2:
                            return R.drawable.ac;
                        case 3:
                            return R.drawable.ad;
                        default:
                            return R.drawable.aa;
                    }
                }
            } else{
                if(teamChosen==1){
                    switch (shipChosen) {
                        case 0:
                            return R.drawable.aa;
                        case 1:
                            return R.drawable.ab;
                        case 2:
                            return R.drawable.ac;
                        case 3:
                            return R.drawable.ad;
                        default:
                            return R.drawable.aa;
                    }
                }else{
                    switch (shipChosen) {
                        case 0:
                            return R.drawable.ea;
                        case 1:
                            return R.drawable.eb;
                        case 2:
                            return R.drawable.ec;
                        case 3:
                            return R.drawable.ed;
                        default:
                            return R.drawable.ea;
                    }
                }
            }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        animV.fired=true;
        animV.touchedPosition[0]=event.getX();
        animV.touchedPosition[1]=event.getY();

        animV.distGaucheRest[0]=0;
        animV.distGaucheRest[1]=animV.screenSizeY;
        animV.fireGauchePas[0]=event.getX()/20;
        animV.fireGauchePas[1]=(animV.screenSizeY-event.getY())/20;

        animV.distDroitRest[0]=animV.screenSizeX;
        animV.distDroitRest[1]=animV.screenSizeY;
        animV.fireDroitPas[0]=(animV.screenSizeX-event.getX())/20;
        animV.fireDroitPas[1]=(animV.screenSizeY-event.getY())/20;

        numberOfShots++;

        if(animV.pos[intru][0]-100<event.getX() && event.getX()<animV.pos[intru][0]+100 &&
                animV.pos[intru][1]-100<event.getY() && event.getY()<animV.pos[intru][1]+100) {
            winListener.onWin(true);
            animV.xplosed=true;
        } else {
            winListener.onWin(false);
        }

        return false;
    }

    public interface OnWinListener {
        void onWin(boolean winStatus);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            winListener = (OnWinListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnWinListener");
        }
    }
}
