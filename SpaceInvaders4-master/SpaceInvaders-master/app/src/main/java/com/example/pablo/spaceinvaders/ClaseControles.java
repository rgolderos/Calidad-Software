package com.example.pablo.spaceinvaders;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import static com.example.pablo.spaceinvaders.R.layout.controles;

/**
 * Created by Ruben on 20/10/2016.
 */

public class ClaseControles extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(controles);}
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onBackPressed();
        return true;
    }


}
