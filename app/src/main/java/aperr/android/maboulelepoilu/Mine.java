package aperr.android.maboulelepoilu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by perrault on 11/01/2017.
 */
public class Mine {

    float x, y, L, H, size;
    boolean inExplosion, visible;
    int explosionStep;

    public Mine(int num, float pL, float pH){

        this.L = pL;
        this.H = pH;
        inExplosion = false;
        size = MainActivity.mine.getWidth();

        switch (num){
            case 1:
                x = 5*L/28;
                y = 20*H/48;
                visible = true;
                break;
            case 2:
                x = 9*L/28;
                y = 20*H/48;
                visible = false;
                break;
            case 3:
                x = 13*L/28;
                y = 20*H/48;
                visible = true;
                break;
            case 4:
                x = 17*L/28;
                y = 20*H/48;
                visible = false;
                break;
            case 5:
                x = 21*L/28;
                y = 20*H/48;
                visible = true;
                break;
            case 6:
                x = 5*L/28;
                y = 32*H/48;
                visible = false;
                break;
            case 7:
                x = 9*L/28;
                y = 32*H/48;
                visible = true;
                break;
            case 8:
                x = 13*L/28;
                y = 32*H/48;
                visible = false;
                break;
            case 9:
                x = 17*L/28;
                y = 32*H/48;
                visible = true;
                break;
            case 10:
                x = 21*L/28;
                y = 32*H/48;
                visible = false;
                break;
        }
    }
}

