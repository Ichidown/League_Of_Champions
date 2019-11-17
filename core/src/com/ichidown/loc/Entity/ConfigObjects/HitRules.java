package com.ichidown.loc.Entity.ConfigObjects;

import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;

import java.util.ArrayList;

public class HitRules
{
    /**must add collective hit rule : exp .. a unit get to be hit x times from x+y projectiles collided */

    //protected double range;
    public ArrayList<Hits> HitList =new ArrayList<Hits>();
    public int localHitTimes =1;
    public int localHitDelay=0;


    public int globalHitTimes =-1;

    public int globalHitDelay=0;
    public int GlobalHitDelayCounter =0;

    public int waitTime,localWaitTime;
    public int waitCounter = 0;

    private int listIndex;
    private boolean found;


    public HitRules(int localHitTimes, int globalHitTimes, int localHitDelay, int globalHitDelay,int waitTime,int localWaitTime,LevelState levelState)
    {
        this.localHitTimes = localHitTimes;
        this.globalHitTimes = globalHitTimes;
        this.localHitDelay = localHitDelay;
        this.globalHitDelay = globalHitDelay;
        this.waitTime=waitTime;
        this.localWaitTime=localWaitTime;
    }

    public HitRules(LevelState levelState)
    {
        for(int i=0;i<levelState.HitListLimit;i++)
        {
            HitList.add(new Hits());
        }
    }

    public void cloneHitRules(HitRules hitRules)
    {
        hitRules.localHitDelay=localHitDelay;
        hitRules.localHitTimes=localHitTimes;
        hitRules.localWaitTime=localWaitTime;
        hitRules.globalHitDelay=globalHitDelay;
        hitRules.globalHitTimes=globalHitTimes;
        hitRules.GlobalHitDelayCounter=GlobalHitDelayCounter;
        hitRules.waitTime=waitTime;
        hitRules.waitCounter=waitCounter;

        for(int i=0;i< HitList.size();i++)
        {
            if(HitList.get(i).Active)
                HitList.get(i).cloneHits(hitRules.HitList.get(i));
            else
                hitRules.HitList.get(i).Active=false;
        }
    }

    public void loopGlobalHitDelay()
    {
        if(globalHitTimes!=0)
        {
            if (GlobalHitDelayCounter > 0)// can't hit yet ...
            {
                GlobalHitDelayCounter--;
            } else if (GlobalHitDelayCounter == 0)// 0 means its time to wait for hit
            {
                if (waitCounter >= 0)// negative means wait infinitely
                {
                    if (waitCounter == 0)// re loop when wait time is up ... even when it didn't hit
                    {
                        GlobalHitDelayCounter = globalHitDelay;
                        waitCounter = waitTime;
                    } else {
                        waitCounter--;
                    }
                }
            }
        }
    }

    public void resetGlobalHitDelayCounter()// needed in case of wait to hit : when hit .. re loop the hit counter
    {
        GlobalHitDelayCounter = globalHitDelay;
    }

    /*public void newVictim(MapObject mapObject)
    {
        HitList.add(new Hits(mapObject,localHitTimes,localHitDelay,localWaitTime));
    }*/

    public void newVictim(MapObject victim)/** APPLY THIS WHEN TOUCH-DOWN **/
    {
        listIndex=0;
        found = false;

        while (listIndex<HitList.size() && !found)
        {
            if (!HitList.get(listIndex).Active)
            {
                HitList.get(listIndex).activate(victim);
                found=true;
            }
            else
            {
                listIndex++;
            }
        }
    }


    public void loopHitRules()
    {
        /** loop everything here + all hits **/
    }
}
