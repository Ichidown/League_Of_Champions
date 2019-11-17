package com.ichidown.loc.Premade.Effects;



import com.badlogic.gdx.math.Vector2;
import com.ichidown.loc.Entity.ConfigObjects.ShapeShifter;
import com.ichidown.loc.Entity.FxObject;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Entity.ConfigObjects.State;
import com.ichidown.loc.Entity.SpriteEntity;
import com.ichidown.loc.GameStates.State1.LevelState;


public class HitExplosion extends FxObject
{

    public HitExplosion(LevelState levelState, boolean right, MapObject p, Vector2 SPoint, ShapeShifter shapeShifter)
    {
        super(levelState, right, p);
        initialiseProjectile();
        createTextureAtlas();
        customInitializing();

        //defineSprite(this);
        //shapeShifter.clone(this.shapeShifter);
        //setPosition(SPoint.x, SPoint.y);

    }

    public void initialiseProjectile()//needs to be in Zed's Package
    {
        hitSound = "";
        destroyedSound = "";


        spritesPath ="Fx.pack";

        shapeShifter.setRotateWithDirection(true);

        shapeShifter.initialWidth=90;
        shapeShifter.initialHeigth=90;

        lifeTime=100;
    }

    protected void customInitializing()
    {
        stateList.add(new State("IDLE", 10,loadAnimation(atlas, "explosion", 5, 0.05f, shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
    }

    public void setAnimationFrame(SpriteEntity SE)
    {
        SE.region = SE.stateList.get(0).getTextureAnimation().getKeyFrame(SE.stateTimer,true);

        if (!SE.facingRight && !SE.region.isFlipX())
            SE.region.flip(true, false);
        else if (SE.facingRight && SE.region.isFlipX())
            SE.region.flip(true, false);

    }


}