package com.ichidown.loc.Entity;


import com.ichidown.loc.GameStates.State1.LevelState;


public class Unit extends MapObject
{
    public String Hud_Bar;/** this shouldn't be loaded for every Unit ... just for our player */

    //Constructer
    public Unit(LevelState Lv, int Team,boolean facingRight)
    {
        super(Lv,facingRight);
        atributes.team =Team;
        levelState.unitManager.unitTypeList.add(this);
    }

    public Unit(LevelState Lv)
    {
        super(Lv);
    }

    public void cloneUnit(Unit unit,float x,float y,int team)
    {
        cloneMapObject(unit,x,y,team);
        unit.Hud_Bar=Hud_Bar;
    }

    //----------------------------------INITIALISATIONS---------------------------------------

    //UPDATES
    public void updateAiBehaviour()// A.I
    {

    }



}