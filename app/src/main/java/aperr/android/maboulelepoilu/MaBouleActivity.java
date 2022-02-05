package aperr.android.maboulelepoilu;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

public class MaBouleActivity extends Activity implements MaBouleDialog.MaBouleDialogListener {
    private MaBouleView mView = null;
    public static final int DEFEAT_MINE = 1;
    public static final int DEFEAT_OBUS = 2;
    public static final int DEFEAT_CHUTE = 3;
    public static final int SUCCESS_NIV1 = 4;
    public static final int SUCCESS_NIV2 = 5;
    public static final int SUCCESS_NIV3 = 6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new MaBouleView(this);
        setContentView(mView);
    }

    public void EndOfGame(int num){

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int l = metrics.widthPixels;
        int h = metrics.heightPixels;

        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = MaBouleDialog.newInstance(num, l, h);
        newFragment.setCancelable(false);
        newFragment.show(fm, "MaBouleDialog");
    }

    public void onClickRejouez(DialogFragment dialog){
        dialog.dismiss();
        mView.resume();
    }

    public void onClickSortir(DialogFragment dialog){
        mView._soundPool.release();
        finish();
    }

}