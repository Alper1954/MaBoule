package aperr.android.maboulelepoilu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.Locale;

/**
 * Created by perrault on 01/03/2017.
 */
public class CustomView extends View {
    int l, h;

    Bitmap nuit1, nuit;

    float x0, y0, x1, y1, x11, y11, x2, y2, x3, y3, x4, y4, x5, y5, x6, y6, x7, y7, x8, y8;
    int width_boom, heigth_boom, width_boom_s, heigth_boom_s;
    Bitmap boom1, boom, boom_s;

    float x_maboule, y_maboule;
    int width_maboule, heigth_maboule;
    Bitmap maboule_bitmap1,maboule_bitmap;

    float x_text, y_text;
    int width_text, heigth_text;
    Bitmap text1, text;

    float x_canon, y_canon;
    int width_canon, heigth_canon;
    Bitmap canon_bitmap1, canon_bitmap;

    public CustomView(Context context){
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init(){

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        l = metrics.widthPixels;
        h = metrics.heightPixels;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        nuit1 = BitmapFactory.decodeResource(getResources(), R.drawable.nuit, options);
        nuit = Bitmap.createScaledBitmap(nuit1,l,h,false);

        width_maboule = 16*l/28;
        heigth_maboule = 16*l/28;
        x_maboule = (l - width_maboule)/2;
        y_maboule = h/24;
        maboule_bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.mabouler, options);
        maboule_bitmap = Bitmap.createScaledBitmap(maboule_bitmap1,width_maboule,heigth_maboule,false);


        width_boom = 3*l/28;
        heigth_boom = 3*l/28;
        width_boom_s = (int) (1.5*l/28);
        heigth_boom_s = (int) (1.5*l/28);

        x0 = 7*l/28; y0 = 0;
        x1 = 2*l/28; x2 = 6*l/28; y1 = h/24 + 2*heigth_maboule/16 - heigth_boom/2; y2 = h/24 + 2*heigth_maboule/16 - heigth_boom_s/2;
        x3 = l/28; x4 = 3*l/28; y3 = h/24 + 6*heigth_maboule/16 - heigth_boom_s/2; y4 = h/24 + 6*heigth_maboule/16 - heigth_boom/2;
        x5 = 0; x6 = 4*l/28; y5 = h/24 + 10*heigth_maboule/16 - heigth_boom/2; y6 = h/24 + 10*heigth_maboule/16 - heigth_boom_s/2;
        x7 = 2*l/28; x8 = 4*l/28; y7 = h/24 + 14*heigth_maboule/16 - heigth_boom_s/2; y8 = h/24 + 14*heigth_maboule/16 - heigth_boom/2;

        boom1 = BitmapFactory.decodeResource(getResources(), R.drawable.boom, options);
        boom = Bitmap.createScaledBitmap(boom1,width_boom,heigth_boom,false);
        boom_s = Bitmap.createScaledBitmap(boom1,width_boom_s,heigth_boom_s,false);


        width_text = 3*l/4;
        x_text = (l - width_text)/2;
        y_text = y_maboule + heigth_maboule + h/24;
        if(Locale.getDefault().getLanguage().equals("fr")) {
            heigth_text = (width_text * 150)/900;
            text1 = BitmapFactory.decodeResource(getResources(), R.drawable.texte, options);
            text = Bitmap.createScaledBitmap(text1, width_text, heigth_text, false);
        }else {
            heigth_text = (width_text * 150)/960;
            text1 = BitmapFactory.decodeResource(getResources(), R.drawable.texte_en, options);
            text = Bitmap.createScaledBitmap(text1, width_text, heigth_text, false);
        }

        width_canon = l;
        heigth_canon = (width_canon * 212)/515;

        int hmax = h-(3*h/32)- (int)y_text - heigth_text - h/24;


        if (heigth_canon > hmax ){
            heigth_canon = hmax;
            width_canon = (heigth_canon * 530)/220;
        }
        x_canon = (l - width_canon)/2;
        y_canon = y_text + heigth_text + h/48;
        canon_bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.canon_maboule_missile, options);
        canon_bitmap = Bitmap.createScaledBitmap(canon_bitmap1,width_canon,heigth_canon,false);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heigthMeasureSpec){
        setMeasuredDimension(l, h);
    }

    @Override
    protected void onDraw(Canvas canvas){

        canvas.drawColor(Color.parseColor("#000000"));

        canvas.drawBitmap(nuit, 0, 0, null);

        canvas.drawBitmap(boom, x0, y0, null);
        canvas.drawBitmap(boom, x1, y1, null);
        canvas.drawBitmap(boom_s, x2, y2, null);
        canvas.drawBitmap(boom_s, x3, y3, null);
        canvas.drawBitmap(boom, x4, y4, null);
        canvas.drawBitmap(boom, x5, y5, null);
        canvas.drawBitmap(boom_s, x6, y6, null);
        canvas.drawBitmap(boom_s, x7, y7, null);
        canvas.drawBitmap(boom, x8, y8, null);

        canvas.drawBitmap(boom, l-x0-3*l/28, y0, null);
        canvas.drawBitmap(boom, l-x1-3*l/28, y1, null);
        canvas.drawBitmap(boom_s, l-x2-l/28, y2, null);
        canvas.drawBitmap(boom_s, l-x3-l/28, y3, null);
        canvas.drawBitmap(boom, l-x4-3*l/28, y4, null);
        canvas.drawBitmap(boom, l-x5-3*l/28, y5, null);
        canvas.drawBitmap(boom_s, l-x6-l/28, y6, null);
        canvas.drawBitmap(boom_s, l-x7-l/28, y7, null);
        canvas.drawBitmap(boom, l-x8-3*l/28, y8, null);


        canvas.drawBitmap(maboule_bitmap, x_maboule, y_maboule, null);

        canvas.drawBitmap(text, x_text, y_text, null);

        canvas.drawBitmap(canon_bitmap, x_canon, y_canon, null);

    }


}
