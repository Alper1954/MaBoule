package aperr.android.maboulelepoilu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by perrault on 10/02/2017.
 */
public class Canon {

    float x, y, xbig, ybig, xsmall, ysmall, xsmaller, ysmaller;
    float sizeX, sizeY;
    int smoke_heigth_big, smoke_width_big, smoke_heigth_small, smoke_width_small, smoke_heigth_smaller, smoke_width_smaller;

    public Canon(float pL, float pH) {
        x = 10*pL/28;
        y = 0;

        smoke_heigth_big = (int) (2.5*pH/48);
        smoke_width_big = (int) (2.5*pH/48);
        smoke_heigth_small = (int) (1.5*pH/48);
        smoke_width_small = (int) (1.5*pH/48);
        smoke_heigth_smaller = (int) (pH/48);
        smoke_width_smaller = (int) (pH/48);

        xbig = 10*pL/28 + 10*pH/48;
        ybig = 0;
        xsmall = xbig + (smoke_width_big - smoke_width_small)/2;
        ysmall = ybig +(smoke_heigth_big - smoke_heigth_small)/2;
        xsmaller = xbig + (smoke_width_big - smoke_width_smaller)/2;
        ysmaller = ybig +(smoke_heigth_big - smoke_heigth_smaller)/2;

        sizeX = MainActivity.canon.getWidth();
        sizeY = MainActivity.canon.getHeight();
    }
}
