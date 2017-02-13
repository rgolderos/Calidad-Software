package com.example.pablo.spaceinvaders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.pablo.spaceinvaders.R.layout.gameover;

/**
 * Created by Pablo on 04/12/2016.
 */

public class GameOver extends Activity {
    Button jugarDeNuevo;
    Button volverMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gameover);
        jugarDeNuevo = (Button) findViewById(R.id.button3);
        volverMenu = (Button) findViewById(R.id.button6);

        jugarDeNuevo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent gameoverintent = new Intent(GameOver.this, SpaceInvadersActivity.class);
                finish();
                startActivity(gameoverintent);
            }
        });

        volverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent menuintent = new Intent(GameOver.this, Inicio.class);
                finish();
                startActivity(menuintent);
            }
        });
    }

}
