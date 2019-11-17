package com.ichidown.loc.Entity.EntityManagers;


import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.GameStates.State1.LevelState;

import java.util.ArrayList;

public class ActionManager
{
    private LevelState levelState;
    private int listIndex;
    private boolean found;
    private ArrayList<Action> actionList = new ArrayList<Action>();




    public ActionManager(LevelState levelState)
    {
        this.levelState=levelState;

        for(int i=0;i<levelState.simultaniousActiveActionsLimit;i++)
        {
            actionList.add(new Action(levelState));
        }
    }

    public void updateActionManager()
    {
        for(Action action:actionList)
        {
            if(action.Active)
            {
                action.timer.loopTimer();

                if(action.timer.canActivate())
                {
                    action.origin.applyAction(action);
                }

                if(!action.timer.Active)
                {
                    action.Active=false;
                }
            }
        }
    }

    public void activateAction(Action action,MapObject source)
    {
        listIndex=0;
        found = false;

        while (listIndex<actionList.size() && !found)
        {
            if (!actionList.get(listIndex).Active)
            {
                action.cloneAction(actionList.get(listIndex),source);
                //actionList.get(listIndex).Active=true;
                found=true;
            }
            else
            {
                listIndex++;
            }
        }
    }

}
