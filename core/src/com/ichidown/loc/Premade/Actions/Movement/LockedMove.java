package com.ichidown.loc.Premade.Actions.Movement;


import com.badlogic.gdx.math.Vector2;
import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.Timer;
import com.ichidown.loc.Entity.MapObject;

public class LockedMove extends Action
{
    public LockedMove(MapObject projectile,MapObject target, float velocity, float maxVelocity,Timer timer)
    {
        super(timer);
        this.source = projectile;
        this.maxVelocity = maxVelocity;
        this.velocity = velocity;
        this.target=target;
    }

    public void applyAction(Action A)
    {
        try
        {
            A.target=A.source.lockedTarget;

            A.direction.x = A.target.b2body.getPosition().x - A.source.b2body.getPosition().x;
            A.direction.y = A.target.b2body.getPosition().y - A.source.b2body.getPosition().y;
            A.direction.nor();

            A.direction.x *= A.velocity;
            A.direction.y *= A.velocity;

            A.source.b2body.applyLinearImpulse(A.direction,A.source.b2body.getWorldCenter(),true);
        }
        catch (Exception e)
        {
            //System.out.println("target lost : " + target);
        }

    }

}
