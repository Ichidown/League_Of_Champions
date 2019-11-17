package com.ichidown.loc.Premade.Actions.Movement;


import com.badlogic.gdx.math.Vector2;
import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.Timer;
import com.ichidown.loc.Entity.MapObject;

public class DirectedMove extends Action
{

    public DirectedMove( Vector2 direction, float velocity, float maxVelocity ,Timer timer)
    {
        super(timer);
        this.maxVelocity = maxVelocity;
        this.velocity = velocity;
        this.direction = direction.nor();
    }

    public void applyAction(Action A)
    {
        A.tempDirection.x = (Math.abs(A.target.b2body.getLinearVelocity().x)<A.maxVelocity) ? A.direction.x*A.velocity : 0;
        A.tempDirection.y = (Math.abs(A.target.b2body.getLinearVelocity().y)<A.maxVelocity) ? A.direction.y*A.velocity : 0;
        A.target.b2body.applyLinearImpulse(A.tempDirection, A.target.b2body.getWorldCenter(), true);
    }

}
