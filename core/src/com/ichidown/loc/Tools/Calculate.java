package com.ichidown.loc.Tools;


import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Calculate
{
    private static float xdiff,ydiff,distance;
    private static Random Rnumber = new Random();
    private static Vector2 distanceHV = new Vector2();

    public static float Distance(Vector2 P1, Vector2 P2)
    {
        xdiff = (P1.x-P2.x);
        ydiff = (P1.y-P2.y);
        distance = (float) Math.sqrt(xdiff*xdiff+ydiff*ydiff);
        return distance;
    }

    public static Vector2 DistanceHV(Vector2 P1, Vector2 P2)
    {
        distanceHV.x=(P1.x-P2.x);
        distanceHV.y=(P1.y-P2.y);

        return distanceHV;
    }

    public static int getRandomIntPercentage(int percent)
    {
        //this randomise from -percent/2 to percent/2
        return  Rnumber.nextInt(percent+1)-percent/2;
    }
    public static float getRandomFloatNegativePositive(float percent)
    {
        return (-percent) + Rnumber.nextFloat() * (percent - (-percent));
    }

    public static float getRandomFloatRange(float min,float max)
    {
        return min+((max-min)/2) + getRandomFloatNegativePositive((max-min)/2);
    }


}
