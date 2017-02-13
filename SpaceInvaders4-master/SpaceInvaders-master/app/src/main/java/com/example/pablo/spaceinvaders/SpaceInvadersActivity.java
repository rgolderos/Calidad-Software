package com.example.pablo.spaceinvaders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.os.Handler;

public class SpaceInvadersActivity extends Activity {

    VistaSpaceInvaders vista;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        vista = new VistaSpaceInvaders(this, size.x, size.y);
        setContentView(vista);
    }

    @Override
    protected void onResume(){
        super.onResume();
        vista.continuar();
    }
    @Override
    protected void onPause() {
        super.onPause();
        vista.pausa();
    }


}
