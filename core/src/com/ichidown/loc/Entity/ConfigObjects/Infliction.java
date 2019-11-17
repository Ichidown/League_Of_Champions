/**package com.ichidown.loc.Entity.ConfigObjects;


import com.ichidown.loc.Entity.MapObject;

public class Infliction
{
    private int amount;
    private int delay; /** should be incremental/decremental or random ... customisable */
 /**   private int initialDelay;
    private int delayCounter;
    private inflictionEffect effect;
    public MapObject target,source;

    public boolean readyToDispose;

    public boolean Active;


    public Infliction(MapObject target,MapObject source,int initialDelay, int delay, int amount, inflictionEffect effect)
    {
        this.target=target;
        this.source=source;
        this.delay = delay;
        this.effect = effect;
        this.amount=amount;

        readyToDispose=false;
        this.initialDelay=initialDelay;

        delayCounter=initialDelay+1;

        Active=true;

        source.levelState.inflictionManager.inflictionEffectTypeList.add(effect);
    }

    public Infliction()
    {
        Active=false;
    }

    public void cloneInfliction(Infliction inf)/**check if infliction list is at its limit 1st ... */
 /**   {
        inf.target=target;
        inf.source=source;
        inf.initialDelay=initialDelay;
        inf.delay=delay;
        inf.amount=amount;
        inf.readyToDispose=false;
        inf.delayCounter=initialDelay+1;

        inf.Active=true;

        effect.cloneInflictionEffect(inf.effect);

    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private void updateDelayCounter()
    {
        delayCounter--;

        if(delayCounter<0)// reset if hit max
            delayCounter=delay;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private void decrementAmount()
    {
        amount--;
    }

    public inflictionEffect getEffect() {
        return effect;
    }

    public void setEffect(inflictionEffect effect) {
        this.effect = effect;
    }


    public MapObject getTarget() {
        return target;
    }

    public void setTarget(MapObject target) {
        this.target = target;
    }

    public void update()
    {
            if(amount>0)
            {
                updateDelayCounter();

                if(delayCounter==0)
                {
                    decrementAmount();
                    effect.Do(target,source);
                }
            }
            else
            {
                effect.Undo(target);
                readyToDispose=true;
            }

    }


}
*/