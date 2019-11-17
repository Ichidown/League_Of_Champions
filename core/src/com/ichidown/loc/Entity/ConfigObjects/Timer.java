package com.ichidown.loc.Entity.ConfigObjects;



public class Timer
{
    public int activations; // -1 == infinite
    public int initialDelay;
    public int delay; //-1 == activate all at once && less than -1 == X activations per wait
    public int waitTime; // negative == infinite wait

    public int activationsDone,initialDelayDone,delayDone,waitTimeDone;

    public boolean stopWhenSourceDead;
    public boolean isWaiting;

    //public MapObject source;
    public boolean Active;


    public Timer(int activations, int initialDelay, int delay,int waitTime, boolean stopWhenSourceDead)
    {
        this.activations = activations;
        this.initialDelay = initialDelay;
        this.delay = delay;
        this.stopWhenSourceDead = stopWhenSourceDead;
        this.waitTime=waitTime;

        Active = false;
        isWaiting=false;
    }

    public Timer()
    {
        this.activations = 1;
        this.initialDelay = 0;
        this.delay = 0;
        this.stopWhenSourceDead = false;
        this.waitTime=0;

        Active = false;
        isWaiting=false;
    }

    public void Clone(Timer T)
    {
        T.activations=activations;
        T.initialDelay=initialDelay;
        T.delay=delay;
        T.stopWhenSourceDead=stopWhenSourceDead;
        T.waitTime=waitTime;

        resetTimer(T);
    }

    public  void resetTimer(Timer T)
    {
        T.activationsDone=0;
        T.initialDelayDone=0;
        T.delayDone=0;
        T.waitTimeDone=0;
        T.isWaiting=false;
        T.Active =true;
    }

    public boolean canActivate()
    {
        if(isWaiting)
        {
            stopWait();
            loopActivationNumber();
            return true;
        }
        else return false;
    }

    public void stopWait()
    {
        isWaiting=false;
    }

    public int getActivationsNumber()
    {
        if(activations >= 0)
            return activations-activationsDone;
        else
            return activations;
    }

    public void loopActivationNumber()
    {
        if(activations>0)
            activationsDone++;
    }

    public  void loopTimer()
    {
        // if finished all activations number
        if(activationsDone==activations)
        {
            Active=false;
        }

        // if some activations remain
        else //if(activationsDone<activations)
        {
            if(isWaiting)
            {
                /**if(waitTime==0)// if there is no wait time
                {
                    if(waitTimeDone == waitTime)// if time to wait is down
                    {
                        isWaiting=false;
                        waitTimeDone=0;
                    }
                    else // still waits
                    {
                        waitTimeDone++;
                    }
                }*/

                if(waitTime>0)// if wait time isn't limitless : negative number / 0 == limitless
                {
                    if(waitTimeDone == waitTime)// if time to wait is down
                    {
                        isWaiting=false;
                        waitTimeDone=0;
                    }
                    else // still waits
                    {
                        waitTimeDone++;
                    }
                }
            }

            else // not waiting
            {
                if(initialDelayDone < initialDelay)// initial delay running
                {
                    initialDelayDone++;
                }
                else if(initialDelayDone == initialDelay)// initial delay stops
                {
                    isWaiting=true;
                    initialDelayDone++;//to pass to next delay
                }

                else // inter activations running
                {
                    if(delayDone == delay)
                    {
                        isWaiting=true;
                        delayDone=0;
                    }
                    else if (delayDone < delay)
                    {
                        delayDone++;
                    }
                }
            }

        }
    }


}
