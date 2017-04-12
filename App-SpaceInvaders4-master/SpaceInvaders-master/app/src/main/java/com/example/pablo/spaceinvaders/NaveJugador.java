package com.example.pablo.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

/**
 * Created by Pablo on 28/09/2016.
 */

public class NaveJugador {
    RectF rect;
    private Bitmap bitmap;
    private float anchura;
    private float altura;
    public int nave = 1;


    private float x;
    private float y;

    private float velocidadNave;
    public final int PARADO = 0;
    public final int IZQ = 1;
    public final int DER = 2;

    private int movimientoNave = PARADO;

    public NaveJugador (Context context, int pantallaX, int pantallaY, int nave){
        rect = new RectF();
        anchura = pantallaX/10;
        altura = pantallaY/10;
        x = pantallaX / 2;
        y = pantallaY;
        if (nave == 1){
            bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.nave);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)(anchura), (int)(altura), false);}
        else if (nave == 2){
            bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.nave2);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)(anchura), (int)(altura), false);
        }
        velocidadNave = 350;
    }

    public RectF getRect(){
        return rect;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public float getX(){
        return x;
    }
    public float getAnchura(){
        return anchura;
    }
    public void setEstadoMovimiento(int estado){
        movimientoNave = estado;
    }
    public float getAltura(){ return altura;}

    public void actualizar(long fps){
        if(movimientoNave == IZQ && x >= 1){
            x = x - velocidadNave / fps;
        }else if(movimientoNave == DER && x <= anchura*9){
            x = x + velocidadNave / fps;
        }
        rect.top = y - altura;
        rect.bottom = y ;
        rect.left = x;
        rect.right = x + anchura;
    }

    public void setNave (Bitmap b){
        this.bitmap =b;
    }

}
