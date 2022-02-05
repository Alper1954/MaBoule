package aperr.android.maboulelepoilu;

import android.graphics.RectF;
import android.util.Log;

/**
 * Created by perrault on 02/01/2017.
 */
public class Bloc {
    enum Type {MUR_H, MUR_V, TALUS_G, TALUS_D, TERRAIN};
    float x, y, sizeX, sizeY;

    Type mType = null;
    RectF mRectangle = null;

    public Bloc(Type pType, float pXleft, float pYtop, float pXright, float pYbottom){
        this.mType = pType;

        this.mRectangle = new RectF(pXleft, pYtop, pXright, pYbottom);
        x = pXleft;
        y = pYtop;
        sizeX = pXright - pXleft;
        sizeY = pYbottom - pYtop;
    }
}
