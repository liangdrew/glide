package com.andrewliang.glide;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.lang.Math;

public class Player extends Activity
{
    private final Paint PLAYER_COLOUR = new Paint();
    private final int length;
    private int screenWidth;
    private int screenHeight;
    private int xPos; //center
    private int yPos; //center
    private int startX;
    private int startY;
    private int stopX;
    private int stopY;
    private double angle; //radians
    private double velocity = 0.1;
    private int gravity;

    Player (int width, int height)
    {
        length = width/12;
        screenWidth = width;
        screenHeight = height;
        xPos = width/2;
        yPos = height/6;
        gravity = width/150;
    }

    public void changeAngle(double deltaAngle)
    {
        this.angle += deltaAngle;
    }

    public void drawPlayer(Canvas canvas)
    {
        PLAYER_COLOUR.setColor(Color.DKGRAY);
        PLAYER_COLOUR.setAntiAlias(true);
        startX = (int) (xPos - Math.cos(angle) * length/2);
        stopX = (int) (xPos + Math.cos(angle) * length/2);
        startY = (int) (yPos + Math.sin(angle) * length/2);
        stopY = (int) (yPos - Math.sin(angle) * length/2);
        canvas.drawLine(startX, startY, stopX, stopY, PLAYER_COLOUR);
    }
    public double getStartX(){return (double) startX;}
    public double getStopX(){return (double) stopX;}
    public double getStartY(){return (double) startY;}
    public double getStopY(){return (double) stopY;}

    public void updatePlayer(int screenWidth, int screenHeight)
    {
        PLAYER_COLOUR.setStrokeWidth(screenHeight/120);
        double acceleration = gravity * (-Math.sin(angle) * 0.1) - 0.01 * velocity;
        velocity += acceleration;
        xPos += (int) (velocity * Math.cos(angle));
        yPos -= (int) (velocity * Math.sin(angle));
        //Log.d("updatePlayer", "stopX = " + stopX);

        //keep the angle between 0 and 2pi
        if (angle >= Math.PI * 2) {angle -= Math.PI * 2;}
        else if (angle <= 0) {angle += Math.PI * 2;}

        //move it to the other side of the screen if half of it crosses the screen boundary
        if (xPos > screenWidth) {xPos = 0;}
        else if (xPos < 0) {xPos = screenWidth;}

        if (yPos > screenHeight) {yPos = 0;}
        else if (yPos < 0) {yPos = screenHeight;}
    }
}
