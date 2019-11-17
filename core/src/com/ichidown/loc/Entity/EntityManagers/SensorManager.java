package com.ichidown.loc.Entity.EntityManagers;


import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.Hits;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Entity.SensorObject;
import com.ichidown.loc.GameStates.State1.LevelState;

import java.util.ArrayList;

public class SensorManager
{
    private LevelState levelState;
    private int listIndex;
    private boolean found;
    public ArrayList<SensorObject> sensorObjectList = new ArrayList<SensorObject>();


    public SensorManager(LevelState levelState)
    {
        this.levelState=levelState;

        for(int i=0;i<levelState.simultaniousActiveSensors;i++)
        {
            sensorObjectList.add(new SensorObject(levelState));
        }

    }

    public void updateSensorManager()
    {
        for(SensorObject sensorObject:sensorObjectList)
        {
            if(sensorObject.Active)
            {
                updateTransformation(sensorObject);

                for(Hits hits:sensorObject.hitRules.HitList)
                {
                    if(hits.Active && hits.canBeHit())/** can activate effect **/
                    {
                        for(Action action:sensorObject.actions)
                        {
                            action.target=hits.objectHit;
                            action.origin.applyAction(action);
                        }

                    }

                }
            }

        }

    }

    private void updateTransformation(SensorObject sensorObject)
    {
        sensorObject.b2body.setTransform(
                sensorObject.source.b2body.getPosition().x,
                sensorObject.source.b2body.getPosition().y,
                sensorObject.rotation);
    }

    public void activateSensor(SensorObject sensor,MapObject source)
    {
        listIndex=0;
        found = false;

        while (listIndex<sensorObjectList.size() && !found)
        {
            if (!sensorObjectList.get(listIndex).Active)
            {
                sensor.cloneSensorObject(source,sensorObjectList.get(listIndex));
                found=true;
            }
            else
            {
                listIndex++;
            }
        }
    }


}
