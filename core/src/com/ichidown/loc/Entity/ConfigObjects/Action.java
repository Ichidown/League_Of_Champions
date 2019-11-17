package com.ichidown.loc.Entity.ConfigObjects;


import com.badlogic.gdx.math.Vector2;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.GameStates.State1.LevelState;

import javax.xml.transform.Source;

public class Action
{
    /** all actions like : summon , command movement , activate a sensor ... extends from this class**/

    public boolean Active=false;

    public Timer timer;// = new Timer();

    //used in calculations
    public Action origin;
    public EntityGenerator entityGenerator=new EntityGenerator();
    public MapObject source,target;
    public float maxVelocity,velocity;
    public Vector2 direction=new Vector2();
    public LevelState levelState;

    //set for calculations
    public float savedValue=0;
    public Vector2 tempDirection=new Vector2();


    public Action(Timer timer)
    {
        Active=true;
        this.timer=timer;
        origin=this;

        /** ADD THIS IN ACTION TYPE LIST **/
        /**********************************/
    }

    public Action(LevelState levelState)
    {
        Active=false;
        this.levelState=levelState;
        timer = new Timer();
    }

    public void cloneAction(Action action , MapObject source)
    {
        timer.Clone(action.timer);

        action.source=source;
        action.target=source;

        action.origin=origin;
        action.Active=true;

        action.maxVelocity=maxVelocity;
        action.velocity=velocity;


        action.direction.x=direction.x;
        action.direction.y=direction.y;

        entityGenerator.cloneEntityGenerator(action.entityGenerator,action.source);
    }

    /**public void activateAction()
    {
        Active=true;
        /**timer.resetTimer(timer);*/ // not sure if realy needed
    /**}*/


    public void applyAction(Action A)
    {

    }

    public void cancelAction(Action A)
    {

    }
}
