package com.example.herouard.piedpiper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Herouard on 25/10/2016.
 */
public class C_LocalData {

    public static final String SP_NAME = "userPref";
    SharedPreferences LocalData;

    public C_LocalData(Context context) {
        LocalData = context.getSharedPreferences(SP_NAME,0);
    }

    public void envoiPref(int shipNumb, int chosenTeam, int chosenShip, int speed){
        SharedPreferences.Editor spEditor = LocalData.edit();
        spEditor.putInt("shipNumb", shipNumb);
        spEditor.putInt("chosenTeam", chosenTeam);
        spEditor.putInt("chosenShip", chosenShip);
        spEditor.putInt("speed", speed);
        spEditor.commit();

    }

    public int[] recupPref(){
        int[] pref = new int[4];

        pref[0] = LocalData.getInt("shipNumb",0);
        pref[1] = LocalData.getInt("chosenTeam",0);
        pref[2] = LocalData.getInt("chosenShip",0);
        pref[3] = LocalData.getInt("speed",0);

        return pref;
    }

    public void clearPref(){
        SharedPreferences.Editor spEditor = LocalData.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
