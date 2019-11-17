/**package com.ichidown.loc.Premade.Inflictions;


import com.ichidown.loc.Entity.MapObject;

public class Damage extends inflictionEffect
{
    public Damage(int amount)
    {
        this.amount=amount;
    }

    public void Do(MapObject target,MapObject source)
    {
        target.atributes.health = target.atributes.health - amount;//apply effect here

        CheckIfKilledBy(target, source);
    }

    private void CheckIfKilledBy(MapObject target, MapObject source)
    {
        if(target.atributes.health<=0)
        {
            gainExperience(target,source);
            target.dead=true;
            //target.disposable=true;
        }
    }

    private void gainExperience(MapObject dead, MapObject gainer)
    {
        gainer.atributes.experience += dead.atributes.experienceValue;
        if (gainer.atributes.experience > gainer.atributes.maxExperience)
        {
            gainer.atributes.experience=gainer.atributes.maxExperience;
        }
    }
}
*/