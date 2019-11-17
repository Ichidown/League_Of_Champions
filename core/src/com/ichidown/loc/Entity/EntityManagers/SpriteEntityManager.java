package com.ichidown.loc.Entity.EntityManagers;

import com.badlogic.gdx.graphics.Texture;
import com.ichidown.loc.Entity.SpriteEntity;
import com.ichidown.loc.GameStates.State1.LevelState;

public class SpriteEntityManager
{
    protected int listIndex;
    protected boolean found;

    public void updateShape(SpriteEntity object)
    {
        object.shapeShifter.update();
    }

    public void updateTimers(SpriteEntity object)
    {
        if(object.lifeTime!=-1)
        {
            if (object.lifeTime > 0)
                object.lifeTime--;
            else
                object.dead=true;
        }
    }

    public void standardUpdates(SpriteEntity SE ,float dt)
    {
        /**DESTROY THE UNIT CODE : world.destroyBody(b2body);*/
        SE.stateTimer += dt;

        // update the sprite animation
        SE.typeObject.setAnimationFrame(SE);
        /*if(!SE.facingRight)
        {
            System.out.println(true);
            SE.region.getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        }*/
        //SE.region.getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);

        SE.setRegion(SE.region);

        SE.setAlpha(SE.shapeShifter.getAlpha());
        SE.setScale(SE.shapeShifter.getSize());
        SE.setRotation(SE.shapeShifter.getRotation());

    }

    public void updateObjectsVisibility(float x,float y,SpriteEntity se, LevelState levelState)
    {
        // determine if a unit is showing in gameCam
        if(isVisible(x,y, levelState))
            se.visibleToCam=true;
        else
            se.visibleToCam=false;
    }

    public boolean isVisible(float x,float y,LevelState lvl)
    {
        float drawBorder = -0.5f;

        if (    lvl.gamecam.position.x + lvl.cameraHalfWidth-drawBorder  >= x &&
                lvl.gamecam.position.x - lvl.cameraHalfWidth+drawBorder  <= x &&

                lvl.gamecam.position.y + lvl.cameraHalfHeight-drawBorder >= y &&
                lvl.gamecam.position.y - lvl.cameraHalfHeight+drawBorder <= y)
        {
            return true;
        }
        return false;
    }

    public void boddylessUpdates(SpriteEntity entity)
    {
        // update the position of the sprite
        if(entity.lockedTarget!=null)
        {
            entity.setPosition(
                    entity.lockedTarget.b2body.getPosition().x - entity.getWidth() / 2,
                    entity.lockedTarget.b2body.getPosition().y - entity.getHeight() / 2);
        }

        //set rotation
        entity.setRotation(entity.shapeShifter.directionalRotation);

    }

    public boolean disposeOfDisposables(SpriteEntity mapObject)
    {
        if(mapObject.dead)//mapObject.disposable)
        {
            return true;
        }
        else
            return false;
    }

}
