package com.ichidown.loc.Premade.Actions;

import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.EntityGenerator;
import com.ichidown.loc.Entity.ConfigObjects.Timer;
import com.ichidown.loc.GameStates.State1.LevelState;


public class Summon extends Action
{
    public Summon(EntityGenerator projectileGenerator,  LevelState levelState,Timer timer)
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
