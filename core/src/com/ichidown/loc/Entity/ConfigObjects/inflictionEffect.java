/**package com.ichidown.loc.Entity.ConfigObjects;


import com.ichidown.loc.Entity.MapObject;

public class inflictionEffect
{
    public int amount=0;
    public float savedValue=0;
    public String effectName="";

    public void Do(MapObject target,MapObject source)
    {

    }

    public void Undo(MapObject target)
    {

    }

    public void cloneInflictionEffect(inflictionEffect effect)
    {
        effect.amount=amount;
        effect.savedValue=savedValue;
        effect.effectName=effectName;
    }

}
*/