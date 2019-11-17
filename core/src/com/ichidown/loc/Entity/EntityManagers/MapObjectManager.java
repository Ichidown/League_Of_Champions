package com.ichidown.loc.Entity.EntityManagers;


import com.ichidown.loc.Entity.ConfigObjects.Ability;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Loc_Luncher;


public class MapObjectManager extends SpriteEntityManager
{


    public MapObjectManager()
    {

    }



    public void updateDistanceMoved(MapObject object)
    {
        if(object.maxDistance!=-1)
        {
            object.moveDistance += Math.sqrt(
                    Math.abs(object.previousPosition.x * object.previousPosition.x) +
                            Math.abs(object.previousPosition.y * object.previousPosition.y));

            if(object.moveDistance >= object.maxDistance)// stop here
            {
                object.maxDistance=-1;// set as -1 so we dont update the distance anymore
                object.dead=true;
                //object.disposable=true;
            }
            else//update previous position
            {
                object.previousPosition.x = object.b2body.getPosition().x;
                object.previousPosition.x = object.b2body.getPosition().y;
            }
        }
    }

    public void boddyUpdates(MapObject mapObject)
    {
        // update the position of the sprite
        //setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        mapObject.setPosition(
                mapObject.b2body.getPosition().x - mapObject.getWidth() / 2,
                mapObject.b2body.getPosition().y - mapObject.getHeight() / 2);

        if(mapObject.shapeShifter.isRotateWithDirection())
        {
            if(mapObject.facingRight)
                mapObject.setRotation(mapObject.getRotation()+mapObject.b2body.getLinearVelocity().angle());
            else
                mapObject.setRotation(mapObject.getRotation()+mapObject.b2body.getLinearVelocity().angle()-180);


            /**System.out.println(mapObject.b2body.getLinearVelocity().angle());
            if(mapObject.b2body.getLinearVelocity().angle()>90&&mapObject.b2body.getLinearVelocity().angle()<=270)//change orientation depending on movement direction
                mapObject.facingRight=true;
            else
                mapObject.facingRight=false;*/

        }


        if(mapObject.shapeShifter.isScalabeColision())
            mapObject.b2body.getFixtureList().get(0).getShape().setRadius(mapObject.shapeSize *mapObject.shapeShifter.getSize());

        if (mapObject.shapeShifter.isRotateWithBody())
            mapObject.setRotation(mapObject.getRotation()+mapObject.b2body.getAngle() * Loc_Luncher.PPM);/**non precise rotation*/


        mapObject.shapeShifter.directionalRotation = mapObject.previousDirection.angle();//update shape shifter body rotation value

    }


    /**public void Destroy(MapObject object) put this in MapObject ... for now
    {
        if(object.destroyedSound != "")
            Loc_Luncher.manager.get(object.destroyedSound, Sound.class).play();
    }*/




    public void updateAbilities(MapObject mapObject)
    {
        for(Ability ability:mapObject.abilities)//int i=0;i<mapObject.abilities.size();i++)
        {
            if(ability.Active)
            {
                ability.updateCharges();
                ability.updateCooldown();
                //ability.updateActions();

                //ability.updateDelay();
                //ability.updateAbilityActivations();
            }
        }
    }

    public void applyPassiveActions(MapObject mp)
    {
        if (mp.initPassiveActionDelay>=0)
        {
            mp.incrementPassiveActionDelay();
            if (mp.passiveActionDelay == 0)
                mp.origin.passiveAction(mp);
        }
    }

    /**public static boolean isVisible(MapObject mp, OrthographicCamera camera)
    {

        float upX = camera.position.x + (- Gdx.graphics.getWidth()  / 2 - mp.) * camera.zoom;
        float upY = camera.position.y + (+ Gdx.graphics.getHeight() / 2 + Game.OFFSET) * camera.zoom;

        float downX = camera.position.x + (+ Gdx.graphics.getWidth()  / 2 + Game.OFFSET) * camera.zoom;
        float downY = camera.position.y + (- Gdx.graphics.getHeight() / 2 - Game.OFFSET) * camera.zoom;

        if (upX <= x && upY >= y &&	downX >= x && downY <= y)
        {
            return true;
        }
        return false;
    }*/

}
