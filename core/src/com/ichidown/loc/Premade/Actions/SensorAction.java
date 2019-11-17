package com.ichidown.loc.Premade.Actions;


import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.HitRules;
import com.ichidown.loc.GameStates.State1.LevelState;

public class SensorAction extends Action
{
    public SensorAction(LevelState levelState,HitRules timer)
    {
        super(timer);
        this.entityGenerator =projectileGenerator;
        this.levelState=levelState;
    }

    public void applyAction(Action A)
    {
        A.levelState.projectileManager.activateProjectileGenerator(A.entityGenerator, A.source);
    }

}
