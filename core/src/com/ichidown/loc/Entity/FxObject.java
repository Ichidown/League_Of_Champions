package com.ichidown.loc.Entity;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ichidown.loc.Entity.ConfigObjects.ShapeShifter;
import com.ichidown.loc.Entity.ConfigObjects.State;
import com.ichidown.loc.GameStates.State1.LevelState;

public class FxObject extends SpriteEntity
{

    public FxObject(LevelState levelState, boolean facingRight, MapObject lockedTarget)
    {
        super(levelState,facingRight);
        this.lockedTarget=lockedTarget;
        levelState.fxManager.FxTypeList.add(this);
    }

    public FxObject(LevelState levelState)
    {
        super(levelState);
    }

    public void cloneFxObject(FxObject fx)
    {
        cloneSpriteEntity(fx);
    }

    public int getActiveState()
    {
        int frameIndex = -1;
        do
        {
            frameIndex++;
        }
        while (stateList.size > frameIndex && stateList.get(frameIndex).Active &&stateList.get(frameIndex).isStateOver());

        return  frameIndex;
    }




    public Array<State> getStateList()
    {
        return stateList;
    }

    public ShapeShifter getShapeShifter()
    {
        return shapeShifter;
    }

    public Vector2 getTargettedPosition()
    {
        return lockedTarget.b2body.getPosition();
    }
}
