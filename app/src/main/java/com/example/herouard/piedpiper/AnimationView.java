package com.example.herouard.piedpiper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class AnimationView extends View {

    //Taille de l'ecran
    int screenSizeX;
    int screenSizeY;

    //Tableau d'Image as bimap
    Bitmap[] bm;
    //Tableau de Chemins a suivre
    Path[] animPath;
    //Tableau de Mesures concernant chaque chemins
    PathMeasure[] pathMeasure;
    //Tableau de Logueur des chemins
    float[] pathLength;
    //Tableau des position (x = 0; y=1)
    float[][] pos;
    // Tableau des Distances de deplacement de chaque objets
    float[] distance;

    float[] tan;

    // Pas de deplacement
    float step;

    // Qui est l'intru
    int intru;

    // Filtre pour couleur du bitmap;
    Bitmap[] resultBitmap;

    //Matrice de mouvement
    Matrix matrix;

    public AnimationView(Context context, Bitmap[] bm, int vitesse,int intru) {
        super(context);
        InitParam(bm,vitesse,intru);
    }

    public void InitParam(Bitmap[] bm, int vitesse,int intru){;

        //On recupere la taille de l'ecran
        screenSizeX = getResources().getDisplayMetrics().widthPixels;
        screenSizeY = getResources().getDisplayMetrics().heightPixels;

        //On recupere toutes nos images
        this.bm = bm;

        //Et celui qui est l'intru
        this.intru = intru;

        //On creer nos tableau avec comme longueur notre nombre d'images
        pathMeasure = new PathMeasure[this.bm.length];
        pathLength = new float[this.bm.length];
        pos = new float[this.bm.length][2]; // ici 2 positions, x et y
        animPath = new Path[this.bm.length];
        distance = new float[this.bm.length];
        resultBitmap = new Bitmap[this.bm.length];

        //On boucle pour instancier chaque objets de nos tableaux
        for(int j=0;j<this.bm.length;j++){

            animPath[j]= getRandomPath();
            pathMeasure[j] = new PathMeasure(animPath[j],false);
            pathLength[j] = pathMeasure[j].getLength();

            resultBitmap[j] = Bitmap.createBitmap(bm[j], 0, 0,
                    bm[j].getWidth() - 1, bm[j].getHeight() - 1);
        }

        //On definit le pas et la distance
        step = vitesse;

        // On creer la matrice
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        tan = new float[2];

        for(int j=0; j<bm.length;j++){
            if(distance[j] < pathLength[j]){
                pathMeasure[j].getPosTan(distance[j], pos[j], tan);

                matrix.reset();
                matrix.postTranslate(pos[j][0]-bm[j].getWidth()/2, pos[j][1]-bm[j].getHeight()/2);

                canvas.drawBitmap(resultBitmap[j], matrix,null);

                distance[j] += step;
            }else{
                distance[j] = 0;
            }
        }
        invalidate();
    }

    public Path getRandomPath(){
        Random randX = new Random();
        Random randY = new Random();

        Path p = new Path();
        p.moveTo(randX.nextInt(screenSizeX), randY.nextInt(screenSizeY));
        for(int j=0; j<10;j++) {
            p.lineTo(randX.nextInt(screenSizeX), randY.nextInt(screenSizeY));
        }
        p.close();

        return p;
    }
}
