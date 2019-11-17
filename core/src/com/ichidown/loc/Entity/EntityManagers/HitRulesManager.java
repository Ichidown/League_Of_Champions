package com.ichidown.loc.Entity.EntityManagers;


import com.ichidown.loc.Entity.ConfigObjects.HitRules;



public class HitRulesManager
{

    //private boolean foundVictim;

    public  HitRulesManager()
    {

    }


    public void updateHitRules(HitRules hitRules)
    {
        hitRules.loopGlobalHitDelay();

        for(int i = 0 ;i<hitRules.HitList.size();i++)
        {
            hitRules.HitList.get(i).loopLocalHitDelayCounter();

            /** if loop ended : deactivate **/
        }


        /** if loop ended : deactivate **/

    }

    /**public void applyEffects(HitRules hitRules)//apply actions then delete all targets
    {
        for(Hits hits:hitRules.HitList)
        {
            hitRules.
        }
        hitRules.HitList.removeAll(hitRules.HitList);
    }*/
}
