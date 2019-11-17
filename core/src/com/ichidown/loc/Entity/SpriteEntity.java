package com.ichidown.loc.Entity;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.ichidown.loc.Entity.ConfigObjects.ShapeShifter;
import com.ichidown.loc.Entity.ConfigObjects.State;
import com.ichidown.loc.GameStates.State1.LevelState;

public abstract class SpriteEntity extends Sprite
{
    //Active or not
    public boolean Active;

    public Body b2body;

    //public String typeName;
    public SpriteEntity typeObject;

    //non clonable
    public SpriteEntity origin;
    public LevelState levelState;
    public String spritesPath;

    public boolean dead; // means its dead but can have some after death effects
    //public boolean disposable; // means there are no after death effects anymore (means we can dispose of this object / deActivate it )
    public boolean visibleToCam;


    //clonable
    public float stateTimer;
    public TextureAtlas atlas;
    public MapObject lockedTarget=null;//if null means : dosent stick to anything
    public TextureRegion region = null;
    public String hitSound = "";
    public String destroyedSound = "";
    public boolean facingRight;/** know how to get "facing right from b2body" & delete this */
    public int lifeTime=-1;

    //complex
    public ShapeShifter shapeShifter =new ShapeShifter();
    public Array<State> stateList = new Array<State>();


    public SpriteEntity(LevelState levelState, boolean facingRight)//used for pre-made entities
    {

        this.levelState=levelState;
        this.facingRight=facingRight;

        //loadStateList();
        stateTimer = 0;
        origin=null;
        dead=false;
        Active=true;
    }

    public SpriteEntity(LevelState levelState)//used when creating game assets (empty entities)
    {
        this.levelState=levelState;
        Active=false;
        dead=true;

        for(int i=0;i<levelState.statePerEntity;i++)
        {
            stateList.add(new State());
        }
    }


    protected void cloneSpriteEntity(SpriteEntity SE)
    {
        SE.dead=false;
        SE.typeObject=this;
        SE.stateTimer=stateTimer;
        SE.atlas = atlas;
        SE.lockedTarget=lockedTarget;
        SE.region=region;
        SE.hitSound=hitSound;
        SE.destroyedSound=destroyedSound;
        SE.facingRight=facingRight;
        SE.lifeTime=lifeTime;

        SE.origin=this;
        SE.Active=true;

        shapeShifter.clone(SE.shapeShifter);

        //reset
        for(int i=0;i<SE.stateList.size;i++)
        {
            SE.stateList.get(i).Active=false;
        }
        //copy
        for(int i=0;i<stateList.size;i++)
        {
            stateList.get(i).cloneState(SE.stateList.get(i));
        }
    }


    protected TextureRegion loadSingleTexture(TextureAtlas atlas,String region , int Pwidth,int pHeight,int frameVStart,int frameHStart)
    {
        return new TextureRegion(atlas.findRegion(region), frameVStart, frameHStart, Pwidth, pHeight);
    }

    protected Animation<TextureRegion> loadAnimation(TextureAtlas atlas, String region ,int framesNumber,float AnimationSpacing ,int Pwidth,int pHeight,int frameVStart,int frameHStart)
    {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < framesNumber; i++)
        {
            frames.add(new TextureRegion(atlas.findRegion(region), frameHStart + i * Pwidth, frameVStart, Pwidth, pHeight));
        }

        Animation<TextureRegion> animation = new Animation(AnimationSpacing, frames);

        frames.clear();

        return animation;
    }

    public void setAnimationFrame(SpriteEntity SE)
    {

    }

    protected void createTextureAtlas() // all initialisations that depend on the initialization of an extension of this Object
    {
        //load sprite sheet
        atlas = new TextureAtlas(spritesPath);//loadSpritePack(spritesPath,atlas);

        /**for(Texture texture:atlas.getTextures())
        {
            texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Nearest);
        }*/

    }

    /**public void defineSprite(SpriteEntity object)
    {
        object.setBounds(
                object.getX(),
                object.getY(),
                object.shapeShifter.initialWidth / Loc_Luncher.PPM,
                object.shapeShifter.initialHeigth / Loc_Luncher.PPM);

        object.setOrigin(object.getWidth()/2, object.getHeight()/2);
    }*/

    public void draw(Batch batch)
    {
        super.draw(batch);
    }


}
