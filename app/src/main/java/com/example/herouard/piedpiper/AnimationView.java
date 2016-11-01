package com.example.herouard.piedpiper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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

    //Ship explosé
    boolean xplosed;
    int countBeforeXplose;

    //Tire effectué
    boolean fired;
    //Variable de tire
    float[] touchedPosition;
    float[] distGaucheRest;
    float[] distDroitRest;
    float[] fireGauchePas;
    float[] fireDroitPas;
    int laserColor;

    public AnimationView(Context context, Bitmap[] bm, int vitesse,int intru,int laserColor) {
        super(context);
        InitParam(bm,vitesse,intru,laserColor);
    }

    public void InitParam(Bitmap[] bm, int vitesse, int intru, int laserColor) {

        //On recupere la taille de l'ecran
        screenSizeX = getResources().getDisplayMetrics().widthPixels;
        screenSizeY = getResources().getDisplayMetrics().heightPixels;

        //On recupere toutes nos images
        this.bm = bm;

        //On recupere la couleur du laser
        this.laserColor = laserColor;

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

        //On definit le pas
        step = vitesse;

        // On creer la matrice
        matrix = new Matrix();

        //Parametre dans le cas ou l'ecran est touché / pour les laser
        fired=false;
        touchedPosition = new float[2];
        distGaucheRest = new float[2];
        distGaucheRest[0]=0;
        distGaucheRest[1]=screenSizeY;
        fireGauchePas = new float[2];
        fireGauchePas[0]=0;
        fireGauchePas[1]=0;

        distDroitRest = new float[2];
        distDroitRest[0]=screenSizeX;
        distDroitRest[1]=screenSizeY;
        fireDroitPas = new float[2];
        fireDroitPas[0]=0;
        fireDroitPas[1]=0;

        //Au cas ou le vaiseau explose
        xplosed=false;
        countBeforeXplose=0;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        tan = new float[2];

        for(int j=0; j<bm.length;j++){
            if(distance[j] < pathLength[j]){
                pathMeasure[j].getPosTan(distance[j], pos[j], tan);

                matrix.reset();
                matrix.postTranslate(pos[j][0]-bm[j].getWidth()/2, pos[j][1]-bm[j].getHeight()/2);

                if(xplosed && j==intru){
                    distance[j] += 0;
                    countBeforeXplose++;
                    if(countBeforeXplose>20){
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.xplosion), matrix,null);
                    }else{
                        canvas.drawBitmap(resultBitmap[j], matrix,null);
                    }
                } else{
                    canvas.drawBitmap(resultBitmap[j], matrix,null);
                    distance[j] += step;
                }
            }else{
                distance[j] = 0;
            }
        }

        if(fired){
            Paint paint = new Paint();
            paint.setColor(laserColor);
            paint.setStrokeWidth(5);
            canvas.drawLine(distGaucheRest[0], distGaucheRest[1], distGaucheRest[0]+fireGauchePas[0], distGaucheRest[1]-fireGauchePas[1], paint);
            canvas.drawLine(distDroitRest[0], distDroitRest[1], distDroitRest[0]-fireDroitPas[0], distDroitRest[1]-fireDroitPas[1], paint);
        }
        if(distGaucheRest[0]<touchedPosition[0]){
            distGaucheRest[0]+=fireGauchePas[0];
        }
        if(distGaucheRest[1]>touchedPosition[1])
        {
            distGaucheRest[1]-=fireGauchePas[1];
        }
        if(distDroitRest[0]>touchedPosition[0]){
            distDroitRest[0]-=fireDroitPas[0];
        }
        if(distDroitRest[1]>touchedPosition[1])
        {
            distDroitRest[1]-=fireDroitPas[1];
        }

        if(distGaucheRest[0]<touchedPosition[0]||distGaucheRest[1]>touchedPosition[1]||distDroitRest[0]>touchedPosition[0]||distDroitRest[1]>touchedPosition[1]){

        } else{
            fired=false;
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
