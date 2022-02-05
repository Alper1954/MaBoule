package aperr.android.maboulelepoilu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by perrault on 11/01/2017.
 */
public class Obus {

    Bitmap bitmap;

    float x, y, x1, y1, L, H, sizeX, sizeY, speed,speed1;
    private static final float SPEED_FACTOR = 2.0f;
    boolean inExplosion;
    int explosionStep;
    int num;



    public Obus(int num, float pL, float pH) {
        this.L = pL;
        this.H = pH;
        this.num = num;
        inExplosion = false;


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;


        switch (num) {
            case 1:
                x = 3 * L / 28;
                y = 8 * H / 48;
                bitmap = MainActivity.bitmap_orange_d;
                speed = (L * 20) / (1000 * SPEED_FACTOR);
                break;
            case 2:
                x = 3 * L / 28 + L / 2;
                y = 8 * H / 48;
                bitmap = MainActivity.bitmap_jaune_d;
                speed = (L * 20) / (1000 * SPEED_FACTOR);
                break;
            case 3:
                x = 25 * L / 28;
                y = 14 * H / 48;
                bitmap = MainActivity.bitmap_orange_g;
                speed = -(L * 20) / (1000 * SPEED_FACTOR);
                break;
            case 4:
                x = 25 * L / 28 - L / 2;
                y = 14 * H / 48;
                bitmap = MainActivity.bitmap_jaune_g;
                speed = -(L * 20) / (1000 * SPEED_FACTOR);
                break;
            case 5:
                x = 25 * L / 28;
                y = 26 * H / 48;
                bitmap = MainActivity.bitmap_jaune_g;
                speed = -(L * 20) / (1000 * SPEED_FACTOR);
                break;
            case 6:
                x = 25 * L / 28 - L / 2;
                y = 26 * H / 48;
                bitmap = MainActivity.bitmap_orange_g;
                speed = -(L * 20) / (1000 * SPEED_FACTOR);
                break;
            case 7:
                x = 3 * L / 28;
                y = 38 * H / 48;
                bitmap = MainActivity.bitmap_jaune_d;
                speed = (L * 20) / (1000 * SPEED_FACTOR);
                break;
            case 8:
                x = 3 * L / 28 + L / 2;
                y = 38 * H / 48;
                bitmap = MainActivity.bitmap_orange_d;
                speed = (L * 20) / (1000 * SPEED_FACTOR);
                break;
        }

        speed1 = 2*speed/3;

        sizeX = bitmap.getWidth();
        sizeY = bitmap.getHeight();
    }
}

