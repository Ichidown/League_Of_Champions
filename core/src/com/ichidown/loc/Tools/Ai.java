package com.ichidown.loc.Tools;


import com.ichidown.loc.Entity.Unit;


public class Ai
{
    public static void SimpleMovementAi(Unit u)
    {
        /**if(u.dx == 0)//dx == 0 means it hit a wall
        {
            if(u.b2body.getLinearVelocity().x<0)
            {
                u.facingRight = false;
                //u.CommandMovement(-u.moveSpeed, 0,u.maxSpeed);
            }
            else if(u.b2body.getLinearVelocity().x>0)
            {
                u.facingRight = true;
                //u.CommandMovement(u.moveSpeed, 0,u.maxSpeed);
            }
            SmartSlowAi(u);
        }*/

    }
    public static void SimpleJumpAi(Unit u)
    {
        /**if(u.dx == 0)//dx == 0 means it hit a wall
        {
            //u.setJumping(true);
            SmartSlowAi(u);
        }*/

    }

    public static void SmartSlowAi(Unit u)
    {
        u.atributes.moveSpeed= u.atributes.maxSpeed / 50;
    }


}
