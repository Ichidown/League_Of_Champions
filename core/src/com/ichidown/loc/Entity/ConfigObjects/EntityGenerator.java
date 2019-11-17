package com.ichidown.loc.Entity.ConfigObjects;


import com.badlogic.gdx.math.Vector2;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Entity.SpriteEntity;
import com.ichidown.loc.GameStates.State1.LevelState;

import java.util.ArrayList;

public class EntityGenerator
{
    private float XDisplacement=0,YDisplacement=0;
    private float RandomDisplacement=0;
    private float accuracy=0;

    private String order="random";

    private float currentDeviation=-1;
    private float additionalDeviation;//additional deviation is the degree added between each projectile creation
    private boolean shouldIncrementDeviation=true;
    //private MapObject target;
    //private ArrayList<Infliction> inflictions=new ArrayList<Infliction>();/**inflictions need to be in sensorrs , not here */
    private float degreeDisplacement=0;

    /**PROJECTILE OBJECT SHOULD BE HERE + creationMethod : to determine what proectile to generate */

    public boolean Active;
    public SpriteEntity entityToGenerate;

    private MapObject source;
    private Vector2 fixedSource=new Vector2();
    public boolean lockedSource=true;

    public Timer timer = new Timer();


    public EntityGenerator()//int ProjectileId)//, ArrayList<Infliction> inflictions)//, int number, int initialDelay, MapObject source, MapObject target , int XDisplacement, int YDisplacement, int RandomDisplacement, int accuracy, String order)
    {
        Active=true;
    }

    public EntityGenerator(LevelState levelState)
    {

        /**for(int i = 0;i<levelState.maxInflictions;i++)
        {
            inflictions.add(new Infliction());
        }*/

        Active=false;
    }

    public void cloneEntityGenerator(EntityGenerator newPg,MapObject source)
    {
        //transfer variables
        newPg.lockedSource=lockedSource;

        newPg.source=source;

        newPg.fixedSource.x=source.getX();
        newPg.fixedSource.y=source.getY();

        //newPg.setProjectileId(ProjectileId);
        newPg.entityToGenerate=entityToGenerate;

        /**newPg.setNumber(number);
        newPg.setInitialDelay(initialDelay);
        newPg.setStopWhenSourceDead(stopWhenSourceDead);*/

        timer.Clone(newPg.timer);

        //newPg.setSource(source);
        //newPg.setTarget(target);

        newPg.setXDisplacement(XDisplacement);
        newPg.setYDisplacement(YDisplacement);
        newPg.setRandomDisplacement(RandomDisplacement);
        newPg.setAccuracy(accuracy);
        newPg.setOrder(order);
        newPg.setDegreeDisplacement(degreeDisplacement);
        newPg.fixedSource.x=fixedSource.x;
        newPg.fixedSource.y=fixedSource.y;

        newPg.Active=true;

        //clear all
        /**for(int i=0;i<newPg.inflictions.size();i++)
        {
            newPg.inflictions.get(i).Active=false;
        }
        //copy
        for(int i=0;i<inflictions.size();i++)
        {
            inflictions.get(i).cloneInfliction(newPg.inflictions.get(i));
        }*/
    }



    /**public void setNumber(int number)
    {
        if(number >=0)
            this.number = number;
        else
        {
            this.number = number;
            //this.initialNumber=number;
        }
    }*/

    /**public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }*/

    public void setXDisplacement(float XDisplacement) {
        this.XDisplacement = XDisplacement;
    }

    public void setYDisplacement(float YDisplacement) {
        this.YDisplacement = YDisplacement;
    }

    public void setRandomDisplacement(float randomDisplacement) {
        RandomDisplacement = randomDisplacement;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    /**public ArrayList<Infliction> getInflictions()
    {
        return inflictions;
    }*/

    /**public void decrementNumber()/** when number is negative , negative delay don't work properly**/
    /**{
            number--;
    }

    public void emptyNumber()/** when number is negative , negative delay don't work properly**/
    /**{
            number=0;
    }*/

    /**public void loopDelay()
    {
        if(delay>0)
        {
            delay--;

        }
        else // delay == 0
        {
            delay=initialDelay;
        }

    }*/

    /**public int getNumber() {
        return number;
    }

    public int getDelay() {
        return delay;
    }*/

    /*public int getProjectileId() {
        return ProjectileId;
    }*/

    /*public void setProjectileId(int projectileId) {
        ProjectileId = projectileId;
    }*/

    /**public int getInitialDelay() {
        return initialDelay;
    }*/

    public MapObject getSource()
    {
        return source;
    }

    public void setSource(MapObject source) {
        this.source = source;
    }

    /**public MapObject getTarget() {
        return target;
    }

    public void setTarget(MapObject target) {
        this.target = target;
    }*/

    public float getXDisplacement() {
        return XDisplacement;
    }

    public float getYDisplacement() {
        return YDisplacement;
    }

    public float getRandomDisplacement() {
        return RandomDisplacement;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public Vector2 getFixedSource() {
        return fixedSource;
    }

    public void setFixedSource(Vector2 fixedSource) {
        this.fixedSource = fixedSource;
    }

    /**public boolean isStopWhenSourceDead() {
        return stopWhenSourceDead;
    }

    public void setStopWhenSourceDead(boolean stopWhenSourceDead) {
        this.stopWhenSourceDead = stopWhenSourceDead;
    }*/

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public float getAdditionalDeviation() {
        return additionalDeviation;
    }

    public void setAdditionalDeviation(float additionalDeviation) {
        this.additionalDeviation = additionalDeviation;
    }

    public float getCurrentDeviation() {
        return currentDeviation;
    }

    public void setCurrentDeviation(float currentDeviation) {
        this.currentDeviation = currentDeviation;
    }

    public boolean isShouldIncrementDeviation() {
        return shouldIncrementDeviation;
    }

    public void setShouldIncrementDeviation(boolean shouldIncrementDeviation) {
        this.shouldIncrementDeviation = shouldIncrementDeviation;
    }

    public float getDegreeDisplacement() {
        return degreeDisplacement;
    }

    public void setDegreeDisplacement(float degreeDisplacement) {
        this.degreeDisplacement = degreeDisplacement;
    }
}
