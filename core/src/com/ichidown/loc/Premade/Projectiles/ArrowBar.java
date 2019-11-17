package com.ichidown.loc.Premade.Projectiles;



import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ichidown.loc.Entity.ConfigObjects.Ability;
import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.Timer;
import com.ichidown.loc.Entity.FxObject;
import com.ichidown.loc.Entity.SpriteEntity;
import com.ichidown.loc.Premade.Actions.Movement.LockedMove;
import com.ichidown.loc.Premade.Actions.Movement.TargetedMove;
import com.ichidown.loc.Entity.ConfigObjects.HitRules;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Entity.Projectile;
import com.ichidown.loc.Entity.ConfigObjects.State;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;
import com.ichidown.loc.Premade.Effects.HitExplosion;
import com.ichidown.loc.Tools.Shape;

import java.util.ArrayList;

public class ArrowBar extends Projectile
{

    private FxObject explosion;
    private PolygonShape barShape;


    public ArrowBar(LevelState Lv,int team ,MapObject lockedTarget, float startX,float startY, float endX,float endY, HitRules hitRules)
    {
        super(Lv,team ,lockedTarget,startX,startY, endX,endY, hitRules);

        //CalculateCourse();
        //LoadSprites();

        initialiseProjectile();
        createTextureAtlas();
        customInitializing();

        setAbilities();

        explosion = new HitExplosion(levelState, facingRight, null, new Vector2(), shapeShifter);
    }

    /** must be able to customise projectiles */

    public void initialiseProjectile()//needs to be in Zed's Package
    {
        shapeShifter.initialWidth = shapeShifter.initialHeigth = 60; // set the sprite size

        hitSound = "";
        destroyedSound = "";


        spritesPath ="projectiles.pack";

        atributes.dashSpeed = 8f;
        atributes.maxSpeed = 90f;
        atributes.moveSpeed = atributes.maxSpeed/500; // need to define hard speed limit

        shapeSize = 0.1f;

        mass_density = 0.0003f;
        friction = 1f;
        bounciness = 0.8f;

        colisionLayer = Loc_Luncher.PROJECTILE_BIT;
        colidableLayers = Loc_Luncher.MOB_BIT | Loc_Luncher.GROUND_BIT;



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


        //lifeTime=30;
        bounces=1;
        //gravityScale=Calculate.getRandomFloatRange(0.5f,1.0f);
        //maxDistance=10000;
        lifeTime = 200;

        shapeShifter.setRotateWithDirection(true);
        //shapeShifter.setSize(gravityScale * 2);
        shapeShifter.initialHeigth=16;
        shapeShifter.initialWidth=90;

        passiveActionDelay=initPassiveActionDelay=1;

        gravityScale=1f;



        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(0, 0.5f).scl(shapeSize);
        vertices[1] = new Vector2(-2, 0).scl(shapeSize);
        vertices[2] = new Vector2(0, -0.5f).scl(shapeSize);
        vertices[3] = new Vector2(4, 0).scl(shapeSize);

        barShape = Shape.newPolygonShape(vertices);

    }

    protected void setAbilities()
    {
        ArrayList<Action> actions = new ArrayList<Action>();
        actions.add(new TargetedMove(atributes.dashSpeed,atributes.maxSpeed,new Timer()));

        abilities.add(new Ability("MOVE",this,0,0,0,0,0,actions));

        ArrayList<Action> actions2 = new ArrayList<Action>();
        actions2.add(new LockedMove(this,lockedTarget,atributes.moveSpeed,atributes.maxSpeed,new Timer()));

        abilities.add(new Ability("FOLLOW",this,0,0,0,0,0,actions2));
    }

    protected void customInitializing()
    {
        stateList.add(new State("IDLE", -1,loadAnimation(atlas, "hook", 1, 1, shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
    }

    public void setAnimationFrame(SpriteEntity SE)
    {
        SE.region = SE.stateList.get(0).getTextureAnimation().getKeyFrame(SE.stateTimer, true);

        //if unit is standing left and the texture isnt facing left... flip it.
        //if(!shapeShifter.isRotateWithDirection())
        //{
            if (!SE.facingRight && !SE.region.isFlipX())
            {
                SE.region.flip(true, false);
                //facingRight = false;
            } else if (SE.facingRight && SE.region.isFlipX())
            {
                SE.region.flip(true, false);
                //facingRight = true;
            }
        //}
    }

    public void startAction(MapObject mp)
    {
        mp.abilities.get(0).useAbility();
    }

    public void passiveAction(MapObject mp)
    {
        mp.abilities.get(1).useAbility();
    }

    public void deathAction(MapObject mp)
    {
        mp.levelState.fxManager.activateFx(explosion);
    }


    public void defineShape(MapObject object)
    {

        object.fdef.shape = barShape;

        object.b2body.createFixture(object.fdef).setUserData(this);/**!!!creation here!!!**/
    }
}