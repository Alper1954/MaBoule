package aperr.android.maboulelepoilu;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by perrault on 30/01/2017.
 */
public class MaBouleDialog extends DialogFragment {
    int mNum, l, h;
    Boolean exit;
    View myview;
    ImageButton imageButton;


    static MaBouleDialog newInstance(int num, int l, int h) {
        MaBouleDialog f = new MaBouleDialog();
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putInt("l", l);
        args.putInt("h", h);
        f.setArguments(args);
        return f;
    }

    public interface MaBouleDialogListener{
        void onClickSortir(DialogFragment dialog);
        void onClickRejouez(DialogFragment dialog);
    }

    MaBouleDialogListener mListener;

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mListener = (MaBouleDialogListener)activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement MaBouleDialogListener");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(5*l/6, 5*l/6);
        window.setGravity(Gravity.CENTER);
    }

   @Override
    public void onDestroy(){
        super.onDestroy();
        imageButton.setImageBitmap(null);
        imageButton = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");
        l = getArguments().getInt("l");
        h = getArguments().getInt("h");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        myview = inflater.inflate(R.layout.maboule_dialog, container);
        Window window = getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);


        imageButton = (ImageButton) myview.findViewById(R.id.imageButton);
        ViewGroup.LayoutParams params = imageButton.getLayoutParams();
        params.height = 5*l/6;
        params.width = 5*l/6;
        imageButton.setLayoutParams(params);
        imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);


        switch (mNum){
            case MaBouleActivity.DEFEAT_MINE:
                if(Locale.getDefault().getLanguage().equals("fr")) {
                    imageButton.setImageResource(R.drawable.tombe_mine);
                }else {
                    imageButton.setImageResource(R.drawable.tombe_mine_en);
                }
                exit = true;
                break;
            case MaBouleActivity.DEFEAT_OBUS:
                if(Locale.getDefault().getLanguage().equals("fr")) {
                    imageButton.setImageResource(R.drawable.tombe_missile);
                }else {
                    imageButton.setImageResource(R.drawable.tombe_missile_en);
                }
                exit = true;
                break;
            case MaBouleActivity.DEFEAT_CHUTE:
                if(Locale.getDefault().getLanguage().equals("fr")) {
                    imageButton.setImageResource(R.drawable.tombe_chute);
                }else {
                    imageButton.setImageResource(R.drawable.tombe_chute_en);
                }
                exit = true;
                break;
            case MaBouleActivity.SUCCESS_NIV1:
                if(Locale.getDefault().getLanguage().equals("fr")) {
                    imageButton.setImageResource(R.drawable.maboule_victoire_1);
                }else {
                    imageButton.setImageResource(R.drawable.maboule_victoire_1_en);
                }
                exit = false;
                break;
            case MaBouleActivity.SUCCESS_NIV2:
                if(Locale.getDefault().getLanguage().equals("fr")) {
                    imageButton.setImageResource(R.drawable.maboule_victoire_2);
                }else {
                    imageButton.setImageResource(R.drawable.maboule_victoire_2_en);
                }
                exit = false;
                break;
            case MaBouleActivity.SUCCESS_NIV3:
                if(Locale.getDefault().getLanguage().equals("fr")) {
                    imageButton.setImageResource(R.drawable.maboule_victoire_3);
                }else {
                    imageButton.setImageResource(R.drawable.maboule_victoire_3_en);
                }
                exit = true;
                break;
        }

        if(exit){
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickSortir(MaBouleDialog.this);
                }
            });
        }else{
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickRejouez(MaBouleDialog.this);
                }
            });
        }

        return myview;

    }
}
