package com.ichidown.loc.Entity.EntityManagers;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ichidown.loc.Entity.ConfigObjects.State;
import com.ichidown.loc.Entity.FxObject;
import com.ichidown.loc.GameStates.State1.LevelState;

import java.util.ArrayList;


public class FxManager extends SpriteEntityManager
{
    private LevelState levelState;
    public ArrayList<FxObject> FxList =new ArrayList<FxObject>();
    public ArrayList<FxObject> FxTypeList =new ArrayList<FxObject>();

    //private boolean resizable;
    private int ListLength;

    public FxManager(LevelState levelState)// , boolean resizable)
    {
        this.levelState=levelState;
        //this.resizable=resizable;

        ListLength=0;

        for (int i=0;i<levelState.FxLimit;i++)
        {
            FxList.add(new FxObject(levelState));
        }
    }

    public void activateFx(FxObject fxObj)
    {
        listIndex=0;
        found = false;

        while (listIndex<FxList.size() && !found)
        {
            if (!FxList.get(listIndex).Active)
            {
                fxObj.cloneFxObject(FxList.get(listIndex));
                found=true;
            }
            else
            {
                listIndex++;
            }
        }
    }


    public void updateFxs(float dt)
    {
        for (int f=0;f<FxList.size();f++)
        {
            if(FxList.get(f).Active)
            {
                for (State St : FxList.get(f).getStateList())/**we are looping all animations ... wrong !!*/
                {
                    if(St.Active)
                        St.loopTime();/** should be in animation **/
                }

                updateObjectsVisibility(FxList.get(f).getX(), FxList.get(f).getY(), FxList.get(f), levelState);
                updateShape(FxList.get(f));
                updateTimers(FxList.get(f));
                standardUpdates(FxList.get(f), dt);


                boddylessUpdates(FxList.get(f));

                /**if (FxList.get(f).getActiveState() == FxList.get(f).getStateList().size)
                {

                }*/

                if(disposeOfDisposables(FxList.get(f)))
                    FxList.get(f).Active=false;
            }
        }
    }

    public void drawFxs(SpriteBatch gameBatch)
    {
        for (FxObject Fx : FxList)
        {
            if(Fx.Active && Fx.visibleToCam)
                Fx.draw(gameBatch);
        }
    }




}
