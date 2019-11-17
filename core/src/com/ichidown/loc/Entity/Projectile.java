package com.ichidown.loc.Entity;


import com.ichidown.loc.Entity.ConfigObjects.*;
import com.ichidown.loc.GameStates.State1.LevelState;

public class Projectile extends MapObject
{

    public Projectile(LevelState Lv , int team,MapObject lockedTarget , float startX,float startY, float endX,float endY, HitRules hitRules)
    {
        super(Lv,( endX-startX >= 0) ? true : false);

        this.hitRules=hitRules;
        //this.startPoint =new Vector2(startX,startY);
        //this.endPoint =new Vector2(endX,endY);
        this.atributes.team=team;
        this.lockedTarget=lockedTarget;
        //this.previousDirection.x = -startPoint.x;//this.endPoint.x-startPoint.x;
        //this.previousDirection.y = -startPoint.y;//this.endPoint.y-startPoint.y;

        levelState.projectileManager.projectileTypeList.add(this);
    }

    public Projectile(LevelState Lv)
    {
        super(Lv);
    }

    public  void cloneProjectile(Projectile projectile,float x,float y,float x2,float y2,int team)
    {
        cloneMapObject(projectile,x,y,team);
        //this.endPoint.x=x2;
        //this.endPoint.y=y2;
    }

}