/**package com.ichidown.loc.Entity.Character.Bots;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ichidown.loc.Entity.Character.Unit;


public class Goomba extends Unit
{

    public enum State { RUNNING };
    public State currentState;
    public State previousState;

    // Tile animation textures
    private Animation<TextureRegion> walkAnimation;


    public Goomba(PlayScreen screen,float x ,float y)
    {
        super(screen, x, y);
    }

    @Override
    public void initializeObject()
    {
        pixelWidth = pixelHeight = 90; // set the width/height of the sprite
        initialWidth = initialHeigth = 90; // set the unit apearance size
        shapeSize = 20;

        currentState = State.RUNNING;
        previousState = State.RUNNING;

        spritePackPath = "minions_sprites.pack";
        hitSound = "";//audio/sounds/stomp.wav";
        destroyedSound = "";

        runningRight = true;
    }

    @Override
    protected void loadAssets()
    {
        atlas = new TextureAtlas(spritePackPath);
        walkAnimation = loadAnimation(atlas ,"minion_red",3,0.4f,pixelWidth,pixelHeight,0,0);
    }



    public void update(float dt)
    {
        standardUpdates(dt,getAnimationFrame(dt));
        updateMovement();
    }


    public void updateMovement()
    {
        //b2body.applyLinearImpulse(new Vector2(0, 0.3f), b2body.getWorldCenter(), true);
        b2body.setLinearVelocity(velocity);
    }

    public TextureRegion getAnimationFrame(float dt)
    {
        TextureRegion region = walkAnimation.getKeyFrame(stateTimer, true);

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX())
        {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX())
        {
            region.flip(true, false);
            runningRight = true;
        }

        return walkAnimation.getKeyFrame(stateTimer, true);
    }

}
*/