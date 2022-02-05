package aperr.android.maboulelepoilu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by perrault on 21/12/2016.
 */
public class MaBouleView extends SurfaceView implements SurfaceHolder.Callback {

    static int niveau; //niveau du jeu

    private  MaBouleActivity mActivity;
    SurfaceHolder mSurfaceHolder;
    DrawingThread mThread;
    Boolean draw;

    private List<Bloc> blocList = null;
    private List<Obus> obusList = null;
    private List<Mine> mineList = null;

    MaBouleEngine mEngine = null;
    MaBoule maBoule;
    Canon canon;
    Paint mPaint1, mPaint2, mPaint3, mPaint4;
    Shader linearGradient;

    public SoundPool _soundPool;
    private int _playbackFile = 0;
    private int _playbackFile2 = 0;

    Float L;
    Float H;

    Bitmap bitmap_boom1, bitmap_boom;



    public MaBouleView(MaBouleActivity pActivity){
        super(pActivity);
        mActivity = pActivity;

        _soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);
        _playbackFile = _soundPool.load(getContext(),R.raw.explosion, 0);
        _playbackFile2 = _soundPool.load(getContext(),R.raw.chute, 0);

        niveau = 1;

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mThread = new DrawingThread();

    }
    private void canontir(int num){
        _soundPool.play(_playbackFile,1,1,0,0,1);
        for(Obus o: obusList){
            if(o.num == num){
                o.inExplosion = true;
                o.explosionStep = 0;
                o.x1 = o.x;
                o.y1 = o.y;
            }
        }
    }

    public void doDraw(Canvas pCanvas){
        if(pCanvas == null){return;}

        pCanvas.drawColor(Color.parseColor("#000000"));

        for(Bloc b : blocList){
            if(b.mType== Bloc.Type.TERRAIN){
                pCanvas.drawRect(b.mRectangle, mPaint1);
            }else if(b.mType== Bloc.Type.TALUS_G){
                pCanvas.drawRect(b.mRectangle, mPaint3);
            }else if(b.mType== Bloc.Type.TALUS_D){
                pCanvas.drawRect(b.mRectangle, mPaint4);
            }else {
                pCanvas.drawRect(b.mRectangle, mPaint2);
            }
        }

        if(mEngine.maBouleCanon){

            int step = mEngine.canonStep;
            if(mEngine.doStep){
                pCanvas.drawBitmap(MainActivity.canon_maboule_inter, canon.x, canon.y, null);
                if(step == 10){
                    mEngine.doStep = false;
                }
            }else{
                pCanvas.drawBitmap(MainActivity.canon_maboule, canon.x, canon.y, null);

                if ((niveau == 1)||(niveau == 2)||(niveau == 3)){
                    if(step == 30){
                        canontir(1);
                    }else if(step == 45){
                        canontir(2);
                    }else if(step == 60){
                        canontir(3);
                    }else if(step == 75){
                        canontir(4);
                    }
                }

                if ((niveau == 2)||(niveau == 3)){
                    if(step == 90){
                        canontir(5);
                    }else if(step == 105){
                        canontir(6);
                    }
                }

                if(niveau == 3){
                    if(step == 120){
                        canontir(7);
                    }else if(step == 135){
                        canontir(8);
                    }
                }

                if (niveau == 1){
                    if(step == 150){
                        draw = false;
                        mActivity.EndOfGame(MaBouleActivity.SUCCESS_NIV1);
                        niveau = 2;
                    }
                }else if(niveau == 2){
                    if(step == 150){
                        draw = false;
                        mActivity.EndOfGame(MaBouleActivity.SUCCESS_NIV2);
                        niveau = 3;
                    }
                }else{
                    if(step == 150){
                        draw = false;
                        mActivity.EndOfGame(MaBouleActivity.SUCCESS_NIV3);
                    }
                }

                for(Mine m:mineList) {
                    if(m.visible){
                        pCanvas.drawBitmap(MainActivity.mine, m.x, m.y, null);
                    }
                }


                for(Obus o: obusList){
                    if(!o.inExplosion){
                        pCanvas.drawBitmap(o.bitmap, o.x, o.y, null);
                    }else{
                        int step2 = o.explosionStep;
                        if( (step2>=0)&&(step2<10)    ){
                            pCanvas.drawBitmap(bitmap_boom, o.x1, o.y1, null);
                        }
                        if( (step2>=0)&&(step2<5)    ){
                            pCanvas.drawBitmap(MainActivity.smoke_smaller, canon.xsmaller, canon.ysmaller, null);
                        }else if( (step2>=5)&&(step2<10) ){
                            pCanvas.drawBitmap(MainActivity.smoke_small, canon.xsmall, canon.ysmall, null);
                        }else if( (step2>=10)&&(step2<15) ){
                            pCanvas.drawBitmap(MainActivity.smoke_big, canon.xbig, canon.ybig, null);
                        }else if( (step2>=15)&&(step2<20) ){
                            pCanvas.drawBitmap(MainActivity.smoke_small, canon.xsmall, canon.ysmall, null);
                        }else if( (step2>=20)&&(step2<25 ) ) {
                            pCanvas.drawBitmap(MainActivity.smoke_smaller, canon.xsmaller, canon.ysmaller, null);
                        }
                        o.explosionStep = step2 + 1;
                    }
                }
            }
            mEngine.canonStep = step + 1;

        }else if(mEngine.explosionMine){
            pCanvas.drawBitmap(MainActivity.canon, canon.x, canon.y, null);

            for(Mine m:mineList) {
                if(m.visible){
                    if (!m.inExplosion) {
                        pCanvas.drawBitmap(MainActivity.mine, m.x, m.y, null);
                    }else{
                        int step = m.explosionStep;
                        if(step == 0){
                            _soundPool.play(_playbackFile,1,1,0,0,1);
                        }
                        if(step == 50){
                            draw = false;
                            mActivity.EndOfGame(MaBouleActivity.DEFEAT_MINE);
                        }
                        if( (step>=0)&&(step<20)    ){
                            pCanvas.drawBitmap(bitmap_boom, m.x, m.y, null);
                        }
                        m.explosionStep = step + 1;
                    }
                }
            }

            for(Obus o: obusList){
                pCanvas.drawBitmap(o.bitmap, o.x, o.y, null);
            }

        }else if(mEngine.explosionObus){
            pCanvas.drawBitmap(MainActivity.canon, canon.x, canon.y, null);

            for(Obus o: obusList){
                if(!o.inExplosion){
                    pCanvas.drawBitmap(o.bitmap, o.x, o.y, null);
                }else{
                    int step = o.explosionStep;
                    if(step == 0){
                        _soundPool.play(_playbackFile,1,1,0,0,1);
                    }
                    if(step == 50){
                        draw = false;
                        mActivity.EndOfGame(MaBouleActivity.DEFEAT_OBUS);
                    }
                    if( (step>=0)&&(step<20)    ){
                        pCanvas.drawBitmap(bitmap_boom, o.x1, o.y1, null);
                    }
                    o.explosionStep = step + 1;
                }

            }

            for(Mine m:mineList) {
                if(m.visible){
                    pCanvas.drawBitmap(MainActivity.mine, m.x, m.y, null);
                }
            }

        }else if(mEngine.maBouleChute){
            pCanvas.drawBitmap(MainActivity.canon, canon.x, canon.y, null);

            if(!mEngine.maBouleChute){
                pCanvas.drawBitmap(MainActivity.maboule, maBoule.x, maBoule.y, null);
            }else{
                int step = maBoule.chuteStep;
                if(step == 0){
                    _soundPool.play(_playbackFile2,1,1,0,0,1);
                }
                if(step == 90){
                    draw = false;
                    mActivity.EndOfGame(MaBouleActivity.DEFEAT_CHUTE);
                }
                if( (step>=0)&&(step<15)    ) {
                    pCanvas.drawBitmap(MainActivity.maboule_small1, maBoule.x1, maBoule.y, null);
                    maBoule.chuteStep = step + 1;
                }else if( (step>=15)&&(step<30)    ) {
                    pCanvas.drawBitmap(MainActivity.maboule_small2, maBoule.x2, maBoule.y, null);
                    maBoule.chuteStep = step + 1;
                }else if( (step>=30)&&(step<45) ){
                    pCanvas.drawBitmap(MainActivity.maboule_small3, maBoule.x3, maBoule.y, null);
                    maBoule.chuteStep = step + 1;
                }else if( (step>=45)&&(step<60 ) ) {
                    pCanvas.drawBitmap(MainActivity.maboule_small4, maBoule.x4, maBoule.y, null);
                    maBoule.chuteStep = step + 1;
                }else if( (step>=60)&&(step<75)    ) {
                    pCanvas.drawBitmap(MainActivity.maboule_small5, maBoule.x5, maBoule.y, null);
                    maBoule.chuteStep = step + 1;
                }else if((step<100)){
                    maBoule.chuteStep = step + 1;
                }
            }

            for(Mine m:mineList) {
                if(m.visible){
                    pCanvas.drawBitmap(MainActivity.mine, m.x, m.y, null);
                }
            }

            for(Obus o: obusList){
                pCanvas.drawBitmap(o.bitmap, o.x, o.y, null);
            }

        }else{
            pCanvas.drawBitmap(MainActivity.canon, canon.x, canon.y, null);
            pCanvas.drawBitmap(MainActivity.maboule, maBoule.x, maBoule.y, null);

            for(Mine m:mineList) {
                if(m.visible){
                    pCanvas.drawBitmap(MainActivity.mine, m.x, m.y, null);
                }
            }

            for(Obus o: obusList){
                pCanvas.drawBitmap(o.bitmap, o.x, o.y, null);
            }
        }
    }

    public void resume(){

        for(Mine m:mineList){
            m.inExplosion = false;
        }

        for(Obus o: obusList){
            if(o.inExplosion){
                o.inExplosion = false;
            }
        }


        maBoule.reset();
        mEngine.maBouleMove = true;
        mEngine.maBouleCanon = false;
        mEngine.explosionMine = false;
        mEngine.explosionObus = false;
        mEngine.maBouleChute = false;


        draw = true;

        mEngine.mineStep = 0;

    }


    @Override
    public void surfaceCreated(SurfaceHolder pHolder){

        L = (float)getWidth();
        H = (float)getHeight();


        maBoule = new MaBoule(L, H);

        canon = new Canon(L, H);

        mEngine = new MaBouleEngine(mActivity, L, H);
        mEngine.setBoule(maBoule);
        mEngine.setCanon(canon);
        blocList = mEngine.buildBlocs(maBoule.sizeX);
        obusList = mEngine.BuildObus();
        mineList = mEngine.BuildMine();

        mPaint1 = new Paint();
        linearGradient = new LinearGradient(3*L/28, 0, L/2, 0, Color.parseColor("#00561B"), Color.parseColor("#008000"), Shader.TileMode.MIRROR );
        mPaint1.setShader(linearGradient);

        mPaint2 = new Paint();
        linearGradient = new LinearGradient(0, 0, 2*L/28, 0, Color.parseColor("#3f2204"), Color.parseColor("#d2b48c"), Shader.TileMode.REPEAT );
        mPaint2.setShader(linearGradient);

        mPaint3 = new Paint();
        linearGradient = new LinearGradient(L/28, 0, 3*L/28, 0, Color.parseColor("#000000"), Color.parseColor("#00561B"), Shader.TileMode.REPEAT );
        mPaint3.setShader(linearGradient);

        mPaint4 = new Paint();
        linearGradient = new LinearGradient(25*L/28, 0, 27*L/28, 0, Color.parseColor("#00561B"), Color.parseColor("#000000"), Shader.TileMode.REPEAT );
        mPaint4.setShader(linearGradient);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap_boom1 = BitmapFactory.decodeResource(getResources(), R.drawable.boom, options);
        int width_boom = (int) (3.*H/48);
        int heigth_boom = (int) (3.*H/48);
        bitmap_boom = Bitmap.createScaledBitmap(bitmap_boom1, width_boom, heigth_boom,false);


        mThread.keepDrawing = true;
        draw = true;
        mThread.start();

        mEngine.maBouleMove = true;
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder pHolder){
        mThread.keepDrawing = false;
        boolean retry = true;
        while(retry){
            try{
                mThread.join();
                retry = false;
            }catch (InterruptedException e){}
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder pHolder, int pFormat, int pWidth, int pHeight){
        //
    }


    private class DrawingThread extends Thread{
        boolean keepDrawing = true;
        private final static int FRAMES_PER_SECOND = 60;
        private final static int SKIP_TICKS = 1000/FRAMES_PER_SECOND;

        @Override
        public void run(){
            Canvas canvas;
            long startTime;
            long sleepTime;

            while (keepDrawing){
                if(draw) {
                        canvas = null;
                        startTime = System.currentTimeMillis();
                        try {
                            canvas = mSurfaceHolder.lockCanvas();
                            synchronized (mSurfaceHolder) {
                                doDraw(canvas);
                                mEngine.updateObus();
                                if(niveau == 3){
                                    mEngine.updateMine();
                                }
                            }

                        } finally {
                            if (canvas != null) mSurfaceHolder.unlockCanvasAndPost(canvas);
                        }
                        sleepTime = SKIP_TICKS - (System.currentTimeMillis() - startTime);

                        try {
                            if (sleepTime >= 0) {
                                Thread.sleep(sleepTime);
                            }

                            //Thread.sleep(20);
                        } catch (InterruptedException e) {
                    }
                }
            }
        }
    }
}

