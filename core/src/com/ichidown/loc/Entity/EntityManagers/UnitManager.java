package com.ichidown.loc.Entity.EntityManagers;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.ichidown.loc.Entity.ConfigObjects.Atributes;
import com.ichidown.loc.Entity.Unit;
import com.ichidown.loc.GameStates.State1.LevelState;

import java.util.ArrayList;

public class UnitManager extends MapObjectManager
{
    private LevelState levelState;

    //public int unitListLoad;
    public ArrayList<Unit> unitsList =new ArrayList<Unit>();//contain alive units
    public ArrayList<Unit> unitTypeList =new ArrayList<Unit>();
    //public ArrayList<Unit> storedUnitsList =new ArrayList<Unit>();//contains non disposable disabled/dead units


    public UnitManager(LevelState levelState)
    {
        this.levelState=levelState;

        //unitListLoad=0;

        for(int i = 0;i<levelState.unitLimit;i++)
        {
            unitsList.add(new Unit(levelState));
        }
    }

    public void updateUnits( float dt)
    {
        for(int i = 0;i< unitsList.size();i++)
        {
            if(unitsList.get(i).Active)
            {
                updateAbilities(unitsList.get(i));
                updateDistanceMoved(unitsList.get(i));
                updateStats(unitsList.get(i).atributes);
                unitsList.get(i).updateAiBehaviour();/**not sure about this being in here*/

                updateObjectsVisibility(unitsList.get(i).getX(), unitsList.get(i).getY(), unitsList.get(i), levelState);
                updateShape(unitsList.get(i));
                updateTimers(unitsList.get(i));
                standardUpdates(unitsList.get(i), dt);


                boddyUpdates(unitsList.get(i));

                applyPassiveActions(unitsList.get(i));

                //check if dead
                if (disposeOfDisposables(unitsList.get(i)))
                {
                    unitsList.get(i).origin.deathAction(unitsList.get(i));
                    unitsList.get(i).deActivate();// remove after all projectiles are gone  & remove previous line ..
                }
            }
        }
    }


    /**public void addUnits(Unit UnitType,int number,int team)
    {
        for(int i=0;i<number;i++)
        {
            activateUnit(UnitType,0,0,team);
        }
    }*/

    public int activateUnit(Unit UnitType, float x, float y, int team)//if full stop
    {
        listIndex=0;
        found = false;

        while (listIndex < unitsList.size() && !found)
        {
            if (!unitsList.get(listIndex).Active)
            {
                UnitType.cloneUnit(unitsList.get(listIndex), x, y, team);
                found=true;
                return listIndex;
            }
            else
            {
                listIndex++;
            }
        }
        return -1;
    }


    public void drawUnits(SpriteBatch gameBatch,ShaderProgram shaderProgram)
    {
        //gameBatch.begin();

        //draw all unitsList
        for (Unit unit : unitsList)
        {

            if(unit.Active && unit.visibleToCam)
            {
                unit.draw(gameBatch);
            }

        }
        //gameBatch.end();
    }

    public void updateStats(Atributes atribs)
    {


        // energy & health regen
        //if(!unit.dead)
        //{
            atribs.energy += atribs.energyRegen; //energy regen
            if (atribs.energy > atribs.maxEnergy) atribs.energy = atribs.maxEnergy;//energy energy cap
            atribs.health += atribs.healthRegen; //health regen
            if (atribs.health > atribs.maxHealth) atribs.health = atribs.maxHealth;//health cap
        //}

        //update characteristics
        if(atribs.level !=atribs.experience/1000)//if there is a level up
        {
            atribs.level = atribs.experience / 1000;//must customise the "1000"

            atribs.maxHealth+=atribs.maxHealth/2;
            atribs.health+=atribs.health/2;


            atribs.energy +=atribs.energy /2;
            atribs.maxEnergy+=atribs.maxEnergy/2;

            atribs.healthRegen += atribs.healthRegen/2;
            atribs.energyRegen += atribs.energyRegen /2;
        }

    }

}
