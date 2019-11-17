package com.ichidown.loc.Premade.Actions.Movement;


import com.badlogic.gdx.math.Vector2;
import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.Timer;
import com.ichidown.loc.Entity.MapObject;

public class TargetedMove extends Action
{
    public TargetedMove(float velocity, float maxVelocity,Timer timer)
    {
        super(timer);
        this.maxVelocity = maxVelocity;
        this.velocity = velocity;
    }

    public void applyAction(Action A)
    {
        A.source.UpdatePointerPosition();
        A.direction.set(
                A.source.pointerPosition.x - A.source.b2body.getPosition().x,
                A.source.pointerPosition.y - A.source.b2body.getPosition().y);

        A.direction.nor();
        A.tempDirection.x = (Math.abs(A.source.b2body.getLinearVelocity().x)<A.maxVelocity) ? A.direction.x*A.velocity : 0;
        A.tempDirection.y = (Math.abs(A.source.b2body.getLinearVelocity().y)<A.maxVelocity) ? A.direction.y*A.velocity : 0;


        A.source.b2body.applyLinearImpulse(A.tempDirection, A.source.b2body.getWorldCenter(), true);

    }

}
