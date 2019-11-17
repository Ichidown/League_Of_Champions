/**package com.ichidown.loc.Entity.EntityManagers;


import com.ichidown.loc.Entity.ConfigObjects.inflictionEffect;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Entity.Projectile;
import com.ichidown.loc.Entity.Unit;
import com.ichidown.loc.GameStates.State1.LevelState;

import java.util.ArrayList;

public class InflictionManager /** THIS NEEDS TO BE FUSED WITH ABILITY **/
/***when shooting too many proectiles ... the infliction seem to stop working : maybe caused by too many b2bodies  ***/
/**{

    private LevelState levelState;
    public ArrayList<inflictionEffect> inflictionEffectTypeList =new ArrayList<inflictionEffect>();

    public InflictionManager(LevelState levelState)
    {
        this.levelState = levelState;
    }

    public void updateUnitsInflictions(ArrayList<Unit> units)
    {
        for (int i=0;i<units.size();i++)
        {
            updateMapObjectInflictions(units.get(i));
        }
    }

    public void updateProjectilesInflictions(ArrayList<Projectile> projectiles)
    {
        for (int i=0;i<projectiles.size();i++)
        {
            updateMapObjectInflictions(projectiles.get(i));
        }
    }

    private void updateMapObjectInflictions(MapObject mapObject)
    {
        for(int j=0;j<mapObject.activeInflictions.size();j++)
        {
            mapObject.activeInflictions.get(j).update();

            if(mapObject.activeInflictions.get(j).readyToDispose)//in case of : end of infliction
            {
                mapObject.activeInflictions.remove(j);
                j--;
            }
        }
    }

}
*/