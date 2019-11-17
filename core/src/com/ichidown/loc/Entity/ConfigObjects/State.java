package com.ichidown.loc.Entity.ConfigObjects;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class State
{
    private int statePriority;
    private String StateName;
    private Animation<TextureRegion> textureAnimation;

    private int stateTime;
    private int timeRemaining=stateTime;
    public boolean Active;


    public State(String stateName,int stateTime,Animation<TextureRegion> textureAnimation)
    {
        StateName = stateName;
        this.textureAnimation = textureAnimation;

        this.stateTime = stateTime;
        this.timeRemaining = stateTime;
        Active=true;
    }

    public State()
    {
        Active=false;
    }

    public void cloneState(State state)
    {
        state.statePriority=statePriority;
        state.StateName=StateName;
        state.textureAnimation=textureAnimation;
        state.stateTime=stateTime;
        state.timeRemaining=stateTime;
        Active=true;
    }

    public void loopTime()
    {
        if(stateTime>=0 && timeRemaining>0)
        {
            timeRemaining--;
        }
    }

    public boolean isStateOver()
    {
        if(stateTime>=0 && timeRemaining==0)
            return true;
        else
            return false;
    }



    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public Animation<TextureRegion> getTextureAnimation() {
        return textureAnimation;
    }

    public void setTextureAnimation(Animation<TextureRegion> textureAnimation) {
        this.textureAnimation = textureAnimation;
    }
}
