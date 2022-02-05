package aperr.android.maboulelepoilu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by perrault on 06/01/2017.
 */
public class MaBoule {

    float x, y, L, H, sizeX, sizeY;
    float x1, x2, x3, x4, x5;
    int chuteStep;


    public MaBoule(float L, float H){
        this.L = L;
        this.H = H;
        x = L/2;
        y = H;

        sizeX = (float)MainActivity.maboule.getWidth();
        sizeY = (float)MainActivity.maboule.getHeight();
    }

    public void reset(){
        x = L/2;
        y = H;
    }
}

