package com.ichidown.loc.Premade.Effects;



import com.badlogic.gdx.math.Vector2;
import com.ichidown.loc.Entity.ConfigObjects.Ability;
import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.Timer;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Premade.Actions.Movement.DirectedMove;
import com.ichidown.loc.Entity.ConfigObjects.HitRules;
import com.ichidown.loc.Entity.Projectile;
import com.ichidown.loc.Entity.ConfigObjects.State;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;

import java.util.ArrayList;

public class GoldCoin extends Projectile
{

    public GoldCoin(LevelState Lv,int team ,MapObject lockedTarget, float startX,float startY, float endX,float endY, HitRules hitRules)
    {
        super(Lv,team ,lockedTarget,startX,startY, endX,endY, hitRules);

        //CalculateCourse();
        //LoadSprites();

        initialiseProjectile();
        createTextureAtlas();
        customInitializing();
        setAbilities();

    }

    /** must add setters to customise projectiles*/
    public void initialiseProjectile()//needs to be in Zed's Package
    {
        //width = height = 90; // set the width/height of the sprite
        shapeShifter.initialWidth = shapeShifter.initialHeigth = 90; // set the sprite size
        shapeSize = shapeShifter.initialWidth /2;

        spritesPath = "";
        hitSound = "";
        destroyedSound = "";

        spritesPath ="Fx.pack";
        //spriteNumber=1;
        //spriteNumber=1;
        //ProjectileHitPath="/Sprites/Player/ShurikenHit.png";
        //ProjectileHitSpriteNumber=4;

        //speedChange = -0.1;
        //animationDelay=15;
        //animationHitDelay=500;


        //hitSpriteNumber=4;

        atributes.maxSpeed = 0.001f;
        atributes.moveSpeed = atributes.maxSpeed/30; // need to define hard speed limit

        //width = height = 8;



        mass_density = 10f;
        friction = 10f;
        bounciness = 0.8f;
       // doRotate=true;

        colisionLayer = Loc_Luncher.PROJECTILE_BIT;
        colidableLayers = Loc_Luncher.GROUND_BIT;



        // Resistance=0;

        //ignoreObjectsColision=true;
        //ignoreMapCollision=false;

        //stopMidair=true;
        //minDistance=500;
        //maxDistance=100;

        //countDistance=true;

        //rolls =false;/**not working properly*/

        //bounceResistance=2;


        //maxLifeTime =100;  //add range : insted use this for levitating timer
        //minimumRange = 5;//not yet implemented //must modify to true range not a timer like in here

        //shapeShifter = new ShapeShifter(0,0,0);
        //shapeShifter.setRdegree(100);
        //shapeShifter.setRotateWithDirection(true);


        //deadEntity = new DeadEntity(width,height,50,"/Sprites/Player/Shuriken.png",1,player.levelState,customHitShape);

        /** boolean resetHitRulesOnBounce **/

        //orientedRotation = true;/**isnt working properly */


        //TO PUT IN PROJECTILE

        //canEffectAlly=boolean;
        //canEffectEnemy=boolean;

        //recoil=int;  this is an inverted dash

        //CastRange=int;//0 means any range

        //guided=boolean;//folow player click & not the mouse
        //guidancePrecision=int;

        //followObject=MapObect; // null means dont folow
        //followPrecision=int; follow degree , the followForce is movementSpeed
        //folowRange=int;

        //explosionTime=int;
        //AreaOfEffect=int;

        //stopAtMouseClicPoint=boolean;

        //castDelay=int;
        //castChargeTime=int;

        //rotationDegree=int;
        //rotationDelay=int;
        //rotationTimes=int; if -1 infinite

        //Freeze
        //FreezeTimer=int;//0 if it dose not stop
        //need to store direction + speed (needed if not freezed anymore)


        //Aplly Old_Infliction:root stun slow shield dmg ...
        //InflictionX=Old_Infliction;


        //lifeTime=30;
        //bounces=2;
        gravityScale=1f;
        //maxDistance=500;

        //shapeShifter.setRotateWithDirection(true);
        shapeShifter.setSize(0.1f);
        //shapeShifter.setAdegree(-0.03f);
        shapeShifter.initialWidth=shapeShifter.initialHeigth=8;

        shapeSize = 0.05f;

    }

    protected void setAbilities()
    {
        ArrayList<Action> actions = new ArrayList<Action>();
        actions.add(new DirectedMove(new Vector2(0,1) , atributes.moveSpeed, atributes.maxSpeed,new Timer()));

        abilities.add(new Ability("MOVE",this,0,0,0,0,0,actions));
    }

    protected void customInitializing()
    {
        stateList.add(new State("IDLE", -1,loadAnimation(atlas, "goldCoin", 7, 0.1f, shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
    }

    public void setAnimationFrame()
    {
        region = stateList.get(0).getTextureAnimation().getKeyFrame(stateTimer, true);

        //if unit is standing left and the texture isnt facing left... flip it.
        if(!shapeShifter.isRotateWithDirection())
        {
            if (!facingRight && !region.isFlipX()) {
                region.flip(true, false);
                facingRight = false;
            }
            else if (facingRight && region.isFlipX())
            {
                region.flip(true, false);
                facingRight = true;
            }
        }
        setRegion(region);
    }


}