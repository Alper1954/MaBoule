package aperr.android.maboulelepoilu;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by perrault on 21/12/2016.
 */
public class MaBouleEngine {
    private SensorManager sensorManager = null;

    private static final float COMPENSATEUR = 3.0f;

    List<Bloc> blocList = null;
    List<Obus> obusList = null;
    List<Mine> mineList = null;
    float L, H, speedX, speedY;

    private MaBoule maBoule = null;
    private Canon canon = null;

    Boolean maBouleCanon = false;
    Boolean explosionObus = false;
    Boolean explosionMine = false;
    Boolean maBouleChute= false;
    Boolean doStep;
    int canonStep;

    int mineStep = 0;

    Boolean maBouleMove = false;



    public MaBouleEngine(Context pContext, float L, float H){
        this.L = L;
        this.H = H;

        SensorManager sensorManager = (SensorManager)pContext.getSystemService(Context.SENSOR_SERVICE);
        Sensor mAccelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(mSensorEventListener, mAccelerometre, SensorManager.SENSOR_DELAY_GAME);

    }

    public void setBoule(MaBoule maBoule){
        this.maBoule = maBoule;
    }

    public void setCanon(Canon canon){
        this.canon = canon;
    }


    SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent pEvent) {
            speedX = - (pEvent.values[0] * COMPENSATEUR);
            speedY = pEvent.values[1] * COMPENSATEUR;


            if(maBoule != null) {
                if(maBouleMove){
                    float maBouleX = maBoule.x+ speedX;
                    if((maBouleX < 0)){
                        maBouleX = 0;

                        maBouleChute = true;
                        maBoule.x1 = 0;
                        maBoule.x2 = 0;
                        maBoule.x3 = 0;
                        maBoule.x4 = 0;
                        maBoule.x5 = 0;
                        maBoule.chuteStep = 0;
                        maBouleMove = false;
                    }
                    if((maBouleX > L - maBoule.sizeX)){
                        maBouleX = L - maBoule.sizeX;

                        maBouleChute = true;
                        maBoule.x1 = L - (2.5f*H/48);
                        maBoule.x2 = L - (2.f*H/48);
                        maBoule.x3 = L - (1.5f*H/48);
                        maBoule.x4 = L - (H/48);
                        maBoule.x5 = L - (0.5f*H/48);
                        maBoule.chuteStep = 0;
                        maBouleMove = false;
                    }

                    float maBouleY = maBoule.y+ speedY;
                    if((maBouleY < 0)){
                        maBouleY = 0;
                    }
                    if((maBouleY > H - maBoule.sizeY)){
                        maBouleY = H - maBoule.sizeY;
                    }

                    checkCollisionMur(maBouleX, maBouleY);
                    checkExplosion();
                    checkCanon();
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor pSensor, int pAccuracy) {
        }
    };



    public List<Bloc> buildBlocs(float sizeX){
        blocList = new ArrayList<Bloc>();

        blocList.add(new Bloc(Bloc.Type.TERRAIN, 3*L/28, 0, 25*L/28, H));

        blocList.add(new Bloc(Bloc.Type.TALUS_G, L/28, 0, 3*L/28, H));
        blocList.add(new Bloc(Bloc.Type.TALUS_D, 25*L/28, 0, 27*L/28, H));

        blocList.add(new Bloc(Bloc.Type.MUR_V, 3*L/28, 7*H/8, 4*L/28, H));
        blocList.add(new Bloc(Bloc.Type.MUR_H, 4*L/28+1.5f*sizeX, 7*H/8, L-4*L/28-1.5f*sizeX, 43*H/48));
        blocList.add(new Bloc(Bloc.Type.MUR_V, 24*L/28, 7*H/8, 25*L/28, H));

        blocList.add(new Bloc(Bloc.Type.MUR_V, 3*L/28, 0, 4*L/28, H/8));
        blocList.add(new Bloc(Bloc.Type.MUR_H, 4*L/28+1.5f*sizeX, 5*H/48, L-4*L/28-1.5f*sizeX, H/8));
        blocList.add(new Bloc(Bloc.Type.MUR_V, 24*L/28, 0, 25*L/28, H/8));

        return  blocList;
    }

    public List<Obus> BuildObus(){
        obusList = new ArrayList<Obus>();

        obusList.add(new Obus(1, L, H));
        obusList.add(new Obus(2, L, H));
        obusList.add(new Obus(3, L, H));
        obusList.add(new Obus(4, L, H));
        obusList.add(new Obus(5, L, H));
        obusList.add(new Obus(6, L, H));
        obusList.add(new Obus(7, L, H));
        obusList.add(new Obus(8, L, H));

        return obusList;
    }

    public List<Mine> BuildMine(){
        mineList = new ArrayList<Mine>();

        mineList.add(new Mine(1, L, H));
        mineList.add(new Mine(2, L, H));
        mineList.add(new Mine(3, L, H));
        mineList.add(new Mine(4, L, H));
        mineList.add(new Mine(5, L, H));
        mineList.add(new Mine(6, L, H));
        mineList.add(new Mine(7, L, H));
        mineList.add(new Mine(8, L, H));
        mineList.add(new Mine(9, L, H));
        mineList.add(new Mine(10, L, H));

        return mineList;
    }

    public void updateObus(){
        for(Obus o: obusList){
            if(MaBouleView.niveau == 1) {
                o.x = o.x + o.speed1;
            }else {
                o.x = o.x + o.speed;
            }
            if(o.x <= (-o.sizeX)){
                o.x = L;
            }else if(o.x >= L){
                o.x = -o.sizeX;
            }
        }
    }

    public void updateMine(){
        mineStep++;
        if(mineStep >= 300){
            for(Mine m:mineList){
                if(m.visible){
                    m.visible = false;
                }else{
                    m.visible = true;
                }
            }
            mineStep = 0;
        }
    }

    private void checkCollisionMur(float X, float Y){
        
        RectF maBouleR = new RectF(X, Y, X+maBoule.sizeX, Y+maBoule.sizeY);

        float Xnew = X;
        float Ynew = Y;

        for(Bloc m:blocList){
            if((m.mType == Bloc.Type.MUR_V)||(m.mType == Bloc.Type.MUR_H)){

                if((inMur(X, Y, m))&&(inMur(X+maBoule.sizeX, Y, m))){
                    //1 et 2
                    //Log.i("alain", "passe par 1 ");
                    Ynew = m.y + m.sizeY;
                }else if((inMur(X, Y+maBoule.sizeY, m))&&(inMur(X+maBoule.sizeX, Y+maBoule.sizeY, m))){
                    //3 et 4
                    //Log.i("alain", "passe par 2 ");
                    Ynew = m.y - maBoule.sizeY;
                }else if((inMur(X, Y, m))&&(inMur(X, Y+maBoule.sizeY, m))){
                    //1 et 3
                    //Log.i("alain", "passe par 3 ");
                    Xnew = m.x + m.sizeX;
                }else if((inMur(X+maBoule.sizeX, Y, m))&&(inMur(X+maBoule.sizeX, Y+maBoule.sizeY, m))){
                    //2 et 4
                    //Log.i("alain", "passe par 4 ");
                    Xnew = m.x - maBoule.sizeX;
                }else if((inMur(X, Y, m))&&(!inMur(X+maBoule.sizeX, Y, m))&&(!inMur(X, Y+maBoule.sizeY, m))){
                    //coin 1
                    //Log.i("alain", "passe par 5 ");
                    RectF inter = new RectF(m.mRectangle);
                    if(inter.intersect(maBouleR)){
                        float tgVit = Math.abs(speedY)/Math.abs(speedX);
                        float tgLim = inter.height()/inter.width();
                        if(tgVit < tgLim){
                            Xnew = m.x + m.sizeX;
                        }else if(tgVit > tgLim){
                            Ynew = m.y + m.sizeY;
                        }else if(tgVit == tgLim){
                            Xnew = m.x + m.sizeX;
                            Ynew = m.y + m.sizeY;
                        }
                    }
                }else if((inMur(X, Y+maBoule.sizeY, m))&&(!inMur(X+maBoule.sizeX, Y+maBoule.sizeY, m))&&(!inMur(X, Y, m)) ){
                    //coin 3
                    //Log.i("alain", "passe par 6 ");
                    RectF inter = new RectF(m.mRectangle);
                    if(inter.intersect(maBouleR)){
                        float tgVit = Math.abs(speedY)/Math.abs(speedX);
                        float tgLim = inter.height()/inter.width();
                        if(tgVit < tgLim){
                            Xnew = m.x + m.sizeX;
                        }else if(tgVit > tgLim){
                            Ynew = m.y - maBoule.sizeY;
                        }else if(tgVit == tgLim){
                            Xnew = m.x + m.sizeX;
                            Ynew = m.y;
                        }
                    }
                }else if((inMur(X+maBoule.sizeX, Y+maBoule.sizeY, m))&&(!inMur(X, Y+maBoule.sizeY, m))&&(!inMur(X+maBoule.sizeX, Y, m)) ){
                    //coin 4
                    //Log.i("alain", "passe par 7 ");
                    RectF inter = new RectF(m.mRectangle);
                    if(inter.intersect(maBouleR)){
                        float tgVit = Math.abs(speedY)/Math.abs(speedX);
                        float tgLim = inter.height()/inter.width();
                        if(tgVit < tgLim){
                            Xnew = m.x - maBoule.sizeX;
                        }else if(tgVit > tgLim){
                            Ynew = m.y - maBoule.sizeY;
                        }else if(tgVit == tgLim){
                            Xnew = m.x;
                            Ynew = m.y;
                        }
                    }
                }else if((inMur(X+maBoule.sizeX, Y, m))&&(!inMur(X, Y, m))&&(!inMur(X+maBoule.sizeX, Y+maBoule.sizeY, m)) ){
                    //coin 2
                    //Log.i("alain", "passe par 8 ");
                    RectF inter = new RectF(m.mRectangle);
                    if(inter.intersect(maBouleR)){
                        float tgVit = Math.abs(speedY)/Math.abs(speedX);
                        float tgLim = inter.height()/inter.width();
                        if(tgVit < tgLim){
                            Xnew = m.x - maBoule.sizeX;
                        }else if(tgVit > tgLim){
                            Ynew = m.y + m.sizeY;
                        }else if(tgVit == tgLim){
                            Xnew = m.x;
                            Ynew = m.y +m.sizeY;;
                        }
                    }
                }else{
                    RectF inter = new RectF(m.mRectangle);
                    if(inter.intersect(maBouleR)){
                        //Log.i("alain", "intersection ");
                        if(m.mType == Bloc.Type.MUR_H){
                            if(speedX>0){
                                Xnew = m.x - maBoule.sizeX;
                            }else{
                                Xnew = m.x + m.sizeX;
                            }
                        }else{
                            if(speedY>0){
                                Ynew = m.y-maBoule.sizeY;
                            }else{
                                Ynew = m.y+ m.sizeY;
                            }
                        }
                    }
                }

            }
        }
        maBoule.x = Xnew;
        maBoule.y = Ynew;

    }

    private boolean inMur(float x, float y, Bloc b){
        if((x>=b.x)&&(x<=b.x+b.sizeX)&&(y>=b.y)&&(y<=b.y+b.sizeY)){
            return true;
        }else{
            return false;
        }
    }

    private void checkExplosion(){

        RectF maBouleR = new RectF(maBoule.x, maBoule.y, maBoule.x+maBoule.sizeX, maBoule.y+maBoule.sizeY);

        for(Mine m:mineList){
            if(m.visible){
                RectF inter = new RectF(m.x, m.y, m.x+m.size, m.y+m.size);
                if(inter.intersect(maBouleR)){
                    explosionMine = true;
                    m.inExplosion = true;
                    m.explosionStep = 0;
                    maBouleMove = false;
                }
            }

        }

        for(Obus o: obusList){
            RectF inter = new RectF(o.x, o.y, o.x+o.sizeX, o.y+o.sizeY);
            if(inter.intersect(maBouleR)){
                explosionObus = true;
                o.inExplosion = true;
                o.explosionStep = 0;
                o.x1 = o.x;
                o.y1 = o.y;
                maBouleMove = false;
            }
        }
    }

    private void checkCanon(){
        RectF maBouleR = new RectF(maBoule.x, maBoule.y, maBoule.x+maBoule.sizeX, maBoule.y+maBoule.sizeY);
        RectF inter = new RectF(canon.x, canon.y, canon.x+canon.sizeX, canon.y+canon.sizeY );
        if(inter.intersect(maBouleR)){
            maBouleCanon = true;
            canonStep = 0;
            maBouleMove = false;
            doStep = false;
            if(speedX<0){
                doStep = true;
                canonStep = 0;
            }
        }

    }

}

