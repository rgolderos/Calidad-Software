package com.example.pablo.spaceinvaders;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.io.IOException;

/**
 * Created by Pablo on 28/09/2016.
 */

public class VistaSpaceInvaders extends SurfaceView implements Runnable {
    Context context;
    private Thread hilo = null;
    private SurfaceHolder holder;

    //Booleanos para controlar el juego
    private volatile boolean funcionando;
    private boolean pausado = true;

    private Canvas lienzo;
    private Paint paint;

    private long fps;
    private long timeThisFrame;

    private int pantallaX;
    private int pantallaY;

    private NaveJugador nave;
    private Disparo disparo;

    private Disparo[] disparoInvasores = new Disparo[200];
    private int sigDisparo;
    private int maxDisparos = 10;

    Invader[] invaders = new Invader[60];
    int numInvaders = 0;
    int invaderVivos;

    //Cubos defensivos
    private CuboDefensivo[] cubos = new CuboDefensivo[400];
    private int numCubos;
    //Sonidos
    private SoundPool soundPool;
    private int shootID = -1;
    private int playerExplodeID = -1;
    private int invaderExplodeID = -1;
    private int damageShelterID = -1;
    private int uhID = -1;
    private int ohID = -1;

    int nivel = 1;
    int puntuacion;
    int vidas = 3;


    private long intervaloAmenaza = 1000;
    private boolean uhOrOh;
    private long tiempoUltimaAmenaza = System.currentTimeMillis();

    public VistaSpaceInvaders(Context context, int x, int y) {
        super(context);
        this.context = context;

        holder = getHolder();
        paint = new Paint();
        pantallaX = x;
        pantallaY = y;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd("shoot.wav");
            shootID = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("uh.ogg");
            uhID = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("oh.ogg");
            ohID = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("invaderexplode.ogg");
            invaderExplodeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("damageshelter.ogg");
            damageShelterID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("playerexplode.ogg");
            playerExplodeID = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            Log.e("error", "failed to load sound files");
        }

        prepararNivel();
    }

    private void prepararNivel() {
        //Aquí se inicializa
        intervaloAmenaza = 1000;
        nave = new NaveJugador(context, pantallaX, pantallaY, ClaseNaves.getNave());
        disparo = new Disparo(pantallaY);
        for (int i = 0; i < disparoInvasores.length; i++) {
            disparoInvasores[i] = new Disparo(pantallaY);
        }

        numInvaders = 0;
        for (int columna = 0; columna < 6; columna++) {
            for (int fila = 0; fila < nivel; fila++) {
                invaders[numInvaders] = new Invader(context, fila, columna, pantallaX, pantallaY);
                numInvaders++;
            }
        }
        invaderVivos = numInvaders;
        numCubos = 0;
        for (int numEscudos = 0; numEscudos < 4; numEscudos++) {
            for (int columna = 0; columna < 10; columna++) {
                for (int fila = 0; fila < 5; fila++) {
                    cubos[numCubos] = new CuboDefensivo(fila, columna, numEscudos, pantallaX, pantallaY);
                    numCubos++;
                }
            }
        }

    }


    @Override
    public void run() {
        while (funcionando) {
            long startFrameTime = System.currentTimeMillis();
            if (!pausado) {
                actualizar();
            }
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

            if (!pausado) {
                if ((startFrameTime - tiempoUltimaAmenaza) > intervaloAmenaza) {
                    if (uhOrOh) {
                        soundPool.play(uhID, 1, 1, 0, 0, 1);
                    } else {
                        soundPool.play(ohID, 1, 1, 0, 0, 1);
                    }
                    tiempoUltimaAmenaza = System.currentTimeMillis();
                    uhOrOh = !uhOrOh;
                }
            }
        }

    }

    private void actualizar() {
        boolean bumped = false;
        boolean perdido = false;
        nave.actualizar(fps);

        for (int i = 0; i < disparoInvasores.length; i++) {
            if (disparoInvasores[i].getPuntoImpactoY() > pantallaY) {
                disparoInvasores[i].setInactiva();
            }
        }

        if (disparo.getEstado()) {
            for (int i = 0; i < numInvaders; i++) {
                if (invaders[i].getVisibilidad()) {
                    if (RectF.intersects(disparo.getRect(), invaders[i].getRect())) {
                        invaders[i].setInvisible();
                        soundPool.play(invaderExplodeID, 1, 1, 0, 0, 1);
                        disparo.setInactiva();
                        disparo.actualizar(fps);
                        invaderVivos--;
                        puntuacion = puntuacion + 10;

                        if (invaderVivos == 0) {
                            if (nivel < 5) {
                                pausado = true;
                                vidas = 3;
                                nivel++;
                                prepararNivel();
                            }

                        }
                    }
                }
            }
        }

        for (int i = 0; i < disparoInvasores.length; i++) {
            if (disparoInvasores[i].getEstado()) {
                for (int j = 0; j < numCubos; j++) {
                    if (cubos[j].getVisibilidad()) {
                        if (RectF.intersects(disparoInvasores[i].getRect(), cubos[j].getRect())) {
                            disparoInvasores[i].setInactiva();
                            cubos[j].setInvisible();
                            soundPool.play(damageShelterID, 1, 1, 0, 0, 1);
                        }
                    }
                }
            }
        }

        if (disparo.getEstado()) {
            for (int i = 0; i < numCubos; i++) {
                if (cubos[i].getVisibilidad()) {
                    if (RectF.intersects(disparo.getRect(), cubos[i].getRect())) {
                        disparo.setInactiva();
                        disparo.actualizar(fps);
                        cubos[i].setInvisible();
                        soundPool.play(damageShelterID, 1, 1, 0, 0, 1);
                    }
                }
            }
        }

        for (int i = 0; i < disparoInvasores.length; i++) {
            if (disparoInvasores[i].getEstado()) {
                if (RectF.intersects(nave.getRect(), disparoInvasores[i].getRect())) {
                    disparoInvasores[i].setInactiva();
                    vidas--;
                    soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);

                    if (vidas == 0) {
                        GameOverMethod();
                        System.exit(0);
                    }
                }
            }
        }

        for (int i = 0; i < numInvaders; i++) {
            if (invaders[i].getVisibilidad()) {
                invaders[i].actualizar(fps); //Mueve al siguien invasor
                if (invaders[i].takeAim(nave.getX(), nave.getAnchura())) {
                    if (disparoInvasores[sigDisparo].disparar(invaders[i].getX() + invaders[i].getAnchura() / 2, invaders[i].getY(), disparo.DOWN)) {
                        sigDisparo++;

                        if (sigDisparo == maxDisparos) {
                            sigDisparo = 0;
                        }
                    }
                }
                if (invaders[i].getX() > pantallaX - invaders[i].getAnchura() || invaders[i].getX() < 0) {
                    bumped = true;
                }
            }
        }

        if (bumped) {
            for (int i = 0; i < numInvaders; i++) {
                invaders[i].dropDownAndReverse();
                if (invaders[i].getY() > pantallaY - (pantallaY / 8 * 2) - pantallaY / 40) {
                    pausado = true;
                    perdido = true;
                }
            }
            intervaloAmenaza = intervaloAmenaza - 80;
        }
        for (int i = 0; i < disparoInvasores.length; i++) {
            if (disparoInvasores[i].getEstado()) {
                disparoInvasores[i].actualizar(fps);
            }
        }
        if (perdido) {
            GameOverMethod();
            System.exit(0);
        }
        if (disparo.getEstado()) {
            disparo.actualizar(fps);
            if (disparo.getPuntoImpactoY() < 0) {
                disparo.setInactiva();
                disparo.actualizar(fps);
            }

        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            lienzo = holder.lockCanvas();

            lienzo.drawColor(Color.argb(255, 0, 0, 0));
            paint.setColor(Color.argb(255, 255, 255, 255));
            if (disparo.getEstado()) {
                lienzo.drawRect(disparo.getRect(), paint);
            }
            for (int i = 0; i < disparoInvasores.length; i++) {
                if (disparoInvasores[i].getEstado()) {
                    lienzo.drawRect(disparoInvasores[i].getRect(), paint);
                }
            }
            //Dibuja Cosas

            lienzo.drawBitmap(nave.getBitmap(), nave.getX(), pantallaY - nave.getAltura(), paint);
            paint.setColor(Color.argb(255, 255, 255, 255));

            for (int i = 0; i < numInvaders; i++) {
                if (invaders[i].getVisibilidad()) {
                    if (uhOrOh) {
                        lienzo.drawBitmap(invaders[i].getBitmap(), invaders[i].getX(), invaders[i].getY(), paint);
                    } else {
                        lienzo.drawBitmap(invaders[i].getBitmap2(), invaders[i].getX(), invaders[i].getY(), paint);
                    }
                }
            }
            for (int i = 0; i < numCubos; i++) {
                if (cubos[i].getVisibilidad()) {
                    lienzo.drawRect(cubos[i].getRect(), paint);
                }
            }
            paint.setTextSize(40);
            lienzo.drawText(puntuacion + " ptos", 10, 50, paint);
            lienzo.drawText("Vidas: " + vidas, pantallaX - pantallaX / 8, 50, paint);
            holder.unlockCanvasAndPost(lienzo);
        }
    }

    public void pausa() {
        funcionando = false;
        try {
            hilo.join();
        } catch (InterruptedException e) {
            Log.e("Error: ", "joining thread");
        }
    }

    public void continuar() {
        funcionando = true;
        hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                pausado = false;
                if (motionEvent.getY() >= pantallaY - pantallaY / 5) {
                    if (motionEvent.getX() > pantallaX / 2) {
                        nave.setEstadoMovimiento(nave.DER);
                    } else {
                        nave.setEstadoMovimiento(nave.IZQ);
                    }

                }
                if (motionEvent.getY() < pantallaY - pantallaY / 5) {
                    if (disparo.disparar(nave.getX() + nave.getAnchura() / 2, pantallaY, disparo.UP)) {
                        soundPool.play(shootID, 1, 1, 0, 0, 1);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (motionEvent.getY() > pantallaY - pantallaY / 5) {
                    nave.setEstadoMovimiento((nave.PARADO));
                }
                break;
        }
        return true;
    }

    private void GameOverMethod(){
        context.startActivity(new Intent(context, GameOver.class));
    }

}
