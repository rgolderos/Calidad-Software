package com.example.pablo.spaceinvaders;

import android.graphics.RectF;

/**
 * Created by Pablo on 28/09/2016.
 */

public class CuboDefensivo {
    private RectF rect;
    private boolean esVisible;

    public CuboDefensivo(int fila, int columna, int numeroEscudo, int pantallaX, int pantallaY){
        int anchura = pantallaX /90;
        int altura = pantallaY / 40;
        esVisible = true;

        int rellenoCubo = 1;

        int rellenoEscudo = pantallaX / 9;
        int inicioAltura = pantallaY - (pantallaY / 8*2);

        rect = new RectF(columna*anchura + rellenoCubo + (rellenoEscudo*numeroEscudo) + rellenoEscudo + rellenoEscudo*numeroEscudo, fila*altura + rellenoCubo + inicioAltura,columna*anchura + anchura - rellenoCubo + (rellenoEscudo*numeroEscudo) + rellenoEscudo + rellenoEscudo*numeroEscudo, fila*altura + altura - rellenoCubo + inicioAltura );
    }

    public RectF getRect(){
        return this.rect;
    }
    public void setInvisible(){
        esVisible = false;
    }
    public boolean getVisibilidad(){
        return esVisible;
    }



}
