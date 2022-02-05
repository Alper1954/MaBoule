package aperr.android.maboulelepoilu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Locale;

public class MainActivity extends Activity {

    public static Bitmap bitmap_orange_d, bitmap_orange_g, bitmap_jaune_d, bitmap_jaune_g;
    public static Bitmap bitmap_orange_d1, bitmap_orange_g1, bitmap_jaune_d1, bitmap_jaune_g1;
    public static Bitmap mine1, mine;
    public static Bitmap maboule1, maboule, maboule_small1, maboule_small2, maboule_small3, maboule_small4, maboule_small5;
    public static Bitmap canon1, canon, canon_maboule1, canon_maboule, canon_maboule_inter1, canon_maboule_inter;
    public static Bitmap smoke, smoke_big, smoke_small, smoke_smaller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int l = metrics.widthPixels;
        int h = metrics.heightPixels;


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;


        int obus_heigth = 2 * h / 48;
        int obus_width = (int) (2.5 * l / 28);
        bitmap_orange_d1 = BitmapFactory.decodeResource(getResources(), R.drawable.obus_orange_d, options);
        bitmap_orange_d = Bitmap.createScaledBitmap(bitmap_orange_d1, obus_width, obus_heigth, false);
        bitmap_jaune_d1 = BitmapFactory.decodeResource(getResources(), R.drawable.obus_jaune_d, options);
        bitmap_jaune_d = Bitmap.createScaledBitmap(bitmap_jaune_d1, obus_width, obus_heigth, false);
        bitmap_orange_g1 = BitmapFactory.decodeResource(getResources(), R.drawable.obus_orange_g, options);
        bitmap_orange_g = Bitmap.createScaledBitmap(bitmap_orange_g1, obus_width, obus_heigth, false);
        bitmap_jaune_g1 = BitmapFactory.decodeResource(getResources(), R.drawable.obus_jaune_g, options);
        bitmap_jaune_g = Bitmap.createScaledBitmap(bitmap_jaune_g1, obus_width, obus_heigth, false);

        int mine_heigth = 2*h/48;
        int mine_width = (int) (2*h/48);
        mine1 = BitmapFactory.decodeResource(getResources(), R.drawable.mine, options);
        mine = Bitmap.createScaledBitmap(mine1,mine_width,mine_heigth,false);

        int maboule_heigth = 3*h/48;
        int maboule_width = (int) (3*h/48);
        int maboule_heigth_small1 = (int) (2.5*h/48);
        int maboule_heigth_small2 = 2*h/48;
        int maboule_heigth_small3 = (int) (1.5*h/48);
        int maboule_heigth_small4 = h/48;
        int maboule_heigth_small5 = (int) (0.5*h/48);
        int maboule_width_small1 = (int) (2.5*h/48);
        int maboule_width_small2 = 2*h/48;
        int maboule_width_small3 = (int) (1.5*h/48);
        int maboule_width_small4 = h/48;
        int maboule_width_small5 = (int) (0.5*h/48);
        maboule1 = BitmapFactory.decodeResource(getResources(), R.drawable.maboule, options);
        maboule = Bitmap.createScaledBitmap(maboule1,maboule_width,maboule_heigth,false);
        maboule_small1 = Bitmap.createScaledBitmap(maboule1,maboule_width_small1,maboule_heigth_small1,false);
        maboule_small2 = Bitmap.createScaledBitmap(maboule1,maboule_width_small2,maboule_heigth_small2,false);
        maboule_small3 = Bitmap.createScaledBitmap(maboule1,maboule_width_small3,maboule_heigth_small3,false);
        maboule_small4 = Bitmap.createScaledBitmap(maboule1,maboule_width_small4,maboule_heigth_small4,false);
        maboule_small5 = Bitmap.createScaledBitmap(maboule1,maboule_width_small5,maboule_heigth_small5,false);

        int canon_heigth = (int) (5.11*h/48);
        int canon_width = (int) (9.39*h/48);
        canon1 = BitmapFactory.decodeResource(getResources(), R.drawable.canon, options);
        canon = Bitmap.createScaledBitmap(canon1,canon_width,canon_heigth,false);
        canon_maboule1 = BitmapFactory.decodeResource(getResources(), R.drawable.canon_maboule, options);
        canon_maboule = Bitmap.createScaledBitmap(canon_maboule1,canon_width,canon_heigth,false);
        canon_maboule_inter1 = BitmapFactory.decodeResource(getResources(), R.drawable.canon_maboule_inter, options);
        canon_maboule_inter = Bitmap.createScaledBitmap(canon_maboule_inter1,canon_width,canon_heigth,false);


        int smoke_heigth_big = (int) (2.5*h/48);
        int smoke_width_big = (int) (2.5*h/48);
        int smoke_heigth_small = (int) (1.5*h/48);
        int smoke_width_small = (int) (1.5*h/48);
        int smoke_heigth_smaller = (int) (h/48);
        int smoke_width_smaller = (int) (h/48);
        smoke = BitmapFactory.decodeResource(getResources(), R.drawable.smoke, options);
        smoke_big = Bitmap.createScaledBitmap(smoke,smoke_width_big,smoke_heigth_big,false);
        smoke_small = Bitmap.createScaledBitmap(smoke,smoke_width_small,smoke_heigth_small,false);
        smoke_smaller = Bitmap.createScaledBitmap(smoke,smoke_width_smaller,smoke_heigth_smaller,false);


        ImageView buttonJouez = (ImageView) findViewById(R.id.buttonJouez);
        ViewGroup.LayoutParams params = buttonJouez.getLayoutParams();
        params.height = 4*h / 48;
        params.width = (4*h * 299) / (48 * 123);
        buttonJouez.setLayoutParams(params);
        buttonJouez.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        if (Locale.getDefault().getLanguage().equals("fr")) {
            buttonJouez.setImageResource(R.drawable.tjouez);
        } else {
            buttonJouez.setImageResource(R.drawable.tplay);
        }
    }

    public void ClickJouez(View view){
        Intent intent = new Intent(MainActivity.this, MaBouleActivity.class);
        startActivity(intent);
    }

}
