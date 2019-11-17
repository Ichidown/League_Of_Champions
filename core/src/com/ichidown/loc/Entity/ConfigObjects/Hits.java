package com.ichidown.loc.Entity.ConfigObjects;


import com.ichidown.loc.Entity.MapObject;

public class Hits
{
    public MapObject objectHit;
    public int hitTimes;

    protected int localHitDelay;
    public int LocalHitDelayCounter=0;

    public int waitTime;
    public int waitCounter = 0;

    public boolean Active;


    public Hits(MapObject unit,int hitTimes,int localHitDelay,int waitTime)
    {
        this.objectHit = unit;
        this.hitTimes=hitTimes;
        this.localHitDelay=localHitDelay;
        this.waitTime=waitTime;

        this.Active=false;
    }

    public Hits()
    {
        this.Active=false;
    }

    public void cloneHits(Hits hits)
    {
        hits.hitTimes=hitTimes;
        hits.localHitDelay=localHitDelay;
        hits.LocalHitDelayCounter=LocalHitDelayCounter;
        hits.waitTime=waitTime;
        hits.waitCounter=waitCounter;
        hits.Active=false;
    }

    public void activate(MapObject mapObject)
    {
        objectHit=mapObject;
        Active=false;
    }

    public void deactivate()
    {
        Active=false;
    }

    public  void loopLocalHitDelayCounter()/**same as with hit rules .. duplicated code */
    {
        if(hitTimes!=0)
        {
            if (LocalHitDelayCounter > 0)// can't hit yet ...
            {
                LocalHitDelayCounter--;
            } else if (LocalHitDelayCounter == 0)// 0 means its time to wait for hit
            {
                if (waitCounter >= 0)// negative means wait infinitely
                {
                    if (waitCounter == 0)// re loop when wait time is up ... even when it didn't hit
                    {
                        LocalHitDelayCounter = localHitDelay;
                        waitCounter = waitTime;
                    } else {
                        waitCounter--;
                    }
                }
            }
        }
    }


    public void resetLocalHitDelayCounter()
    {
        LocalHitDelayCounter =localHitDelay;
    }


    public boolean canBeHit()
    {
        /** if is waiting or smthn return true **/

        return true;
    }

}
