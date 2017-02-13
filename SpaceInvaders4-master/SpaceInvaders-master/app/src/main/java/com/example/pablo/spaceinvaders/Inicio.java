package com.example.pablo.spaceinvaders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.pablo.spaceinvaders.R.layout.menu;

/**
 * Created by Ruben on 20/10/2016.
 */

public class Inicio extends Activity {
    Button jugar;
    Button controles;
    Button naves;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(menu);
        jugar = (Button) findViewById(R.id.button);
        controles = (Button) findViewById(R.id.button2);
        naves = (Button) findViewById(R.id.button3);
        jugar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent iniciointent = new Intent(Inicio.this, SpaceInvadersActivity.class);
                startActivity(iniciointent);
            }
        });
        controles.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent iniciointent = new Intent(Inicio.this, ClaseControles.class);
                startActivity(iniciointent);
            }
        });
        naves.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent iniciointent = new Intent(Inicio.this, ClaseNaves.class);
                startActivity(iniciointent);
            }

        });
    }
    }