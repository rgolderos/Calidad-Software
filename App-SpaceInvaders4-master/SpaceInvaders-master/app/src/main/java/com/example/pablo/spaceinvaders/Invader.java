package com.example.pablo.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.util.Random;


/**
 * Created by Pablo on 28/09/2016.
 */

public class Invader {
    RectF rect;
    Random generator = new Random();

    private Bitmap bitmap1;
    private Bitmap bitmap2;

    // cuanto de alto y largo va a ser
    private float anchura;
    private float altura;

    private float x;
    private float y;

    private float naveVel;
    public final int IZQ = 1;
    public final int DER = 2;

    private int movimientoNave = DER;
    boolean esVisible;

    public Invader (Context context, int fila, int columna, int pantallaX, int pantallaY){
        rect = new RectF();
        anchura = pantallaX /20;
        altura = pantallaY /20;
        esVisible = true;
        int relleno = pantallaX / 25;
        x = columna*(anchura + relleno);
        y = fila*(anchura + relleno/4);
        //Inicializamos el bitmap
        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.invader1);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.invader2);

        bitmap1 = Bitmap.createScaledBitmap(bitmap1, (int)(anchura), (int)(altura), false);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int)(anchura), (int)(altura), false);
        naveVel = 40;
    }

    public void setInvisible(){
        esVisible = false;
    }
    public boolean getVisibilidad(){
        return esVisible;
    }
    public RectF getRect(){
        return rect;
    }
    public Bitmap getBitmap(){
        return bitmap1;
    }
    public Bitmap getBitmap2(){
        return bitmap2;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getAnchura(){
        return anchura;
    }


    public void actualizar (long fps){
        if (movimientoNave == IZQ){
            x = x - naveVel / fps;
        }
        if (movimientoNave == DER){
            x = x + naveVel / fps;
        }

        rect.top = y;
        rect.bottom = y + altura;
        rect.left = x;
        rect.right = x + anchura;
    }

    public void dropDownAndReverse(){
        if(movimientoNave == IZQ){
            movimientoNave = DER;
        }else{
            movimientoNave = IZQ;
        }
        y = y+altura;
        naveVel = naveVel*1.18f;
    }

    public boolean takeAim (float naveJugadorX, float naveJugadorAnchura){
        int numeroRandom = -1;

        //Si está cerca del jugador
        if((naveJugadorX+naveJugadorAnchura > x && naveJugadorX+naveJugadorAnchura < x+anchura) || (naveJugadorX > x && naveJugadorX < x+anchura)){
            numeroRandom = generator.nextInt(150);
            if(numeroRandom == 0){
                return true;
            }
        }
         //Si está en cualquier lado dispara aleatorio, prob 1/5000
        numeroRandom = generator.nextInt(2000);
        if(numeroRandom == 0){
            return true;
        }
        return false;
    }


}
