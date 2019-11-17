package com.ichidown.loc.Premade.Units;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ichidown.loc.Entity.ConfigObjects.Ability;
import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.HitRules;
import com.ichidown.loc.Entity.ConfigObjects.Timer;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Entity.SensorObject;
import com.ichidown.loc.Entity.SpriteEntity;
import com.ichidown.loc.Entity.Unit;
import com.ichidown.loc.Premade.Actions.Movement.DirectedMove;
import com.ichidown.loc.Premade.Actions.Summon;
import com.ichidown.loc.Entity.ConfigObjects.EntityGenerator;
import com.ichidown.loc.Entity.ConfigObjects.State;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;
import com.ichidown.loc.Premade.Projectiles.ArrowBar;
import com.ichidown.loc.Tools.Shape;

import java.util.ArrayList;

import sun.management.Sensor;


public class Zed extends Unit
{

    private CircleShape sensorShape;
    private PolygonShape bodyShape;

    public Zed(LevelState Lv, int Team,boolean facingRight)
    {
        super(Lv, Team, facingRight);
        InitialisePlayer();
        createTextureAtlas();
        customInitializing();
        //defineObject(this);
        setAbilities();

    }

    protected void InitialisePlayer()
    {
        /**automatise this*/
        //width = 90;
        //height = 90;
        //cwidth = originalCwidth = 60;
        //cheight = originalCheight =60;
        /*****************/

        //fallSpeed = 0.3;/**fallSpeed & jump distance are related : must remove this */
        atributes.maxFallSpeed = 6;

        atributes.experience = 1000;
        atributes.maxExperience = 18000;
        atributes.level = atributes.experience / 1000;

        dead=false;
        facingRight = true;//depend on team number

        //width = height = 90; // set the width/height of the sprite
        shapeShifter.initialWidth = shapeShifter.initialHeigth = 90; // set the sprite size
        //shapeSize = shapeShifter.initialWidth /2;

        spritesPath = "";
        hitSound = "";
        destroyedSound = "";

        atributes.team = 1;//modifiable by player

        //resources path
        Hud_Bar = "Hud.pack"; /** there should be a standard hud
                                            for the screen & a standard hud for the
                                            in game and a custom in game hud packed
                                            with the player sprites !!!!!!!!!!!! */

        spritesPath = "Zed_sprites.pack";


        /**link mouvement speed with movement animation delay & attack speed with attack animation delay & so on ... */

        shapeSize = 0.15f;
        mass_density = 0f;
        friction = 0.7f;
        bounciness=0.1f;

        colisionLayer = Loc_Luncher.PLAYER_BIT;
        colidableLayers = Loc_Luncher.GROUND_BIT | Loc_Luncher.MOB_BIT;

        //character stats

        atributes.dashSpeed = 5;
        atributes.maxSpeed = 3;//X3
        atributes.moveSpeed = atributes.maxSpeed/30;//X3

        atributes.stopSpeed = 0.15;//stop movement speed
        // glideSlow= -5;

        // need to fix the jump system + add jet pack mechanism : inverted gliding with limited time / distance use
        atributes.jumpSpeed = 8;
        atributes.jumpDistance = atributes.remainingJumpDistance = 200;
        //fallSpeed = 10;

        /**Try to use this as the speed of jump degrades before/at max height **/
        //stopJumpSpeed = 0;/** ??? **/

        atributes.health = 6000;
        atributes.maxHealth = 20000;
        atributes.healthRegen = 50;

        atributes.energy = atributes.maxEnergy = 25000;
        atributes.energyRegen = 15;

        //****************************************************
        //colisionRadius=0.5f;

        selectedTarget=this;

        passiveActionDelay=initPassiveActionDelay=10;

        shapeShifter.initialWidth=90;
        shapeShifter.initialHeigth=90;

        gravityScale=1;



        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(-1, 2).scl(shapeSize);
        vertices[1] = new Vector2(-1, 0).scl(shapeSize);
        vertices[2] = new Vector2(0, -3).scl(shapeSize);
        vertices[3] = new Vector2(1, 0).scl(shapeSize);
        vertices[4] = new Vector2(1, 1).scl(shapeSize);

        bodyShape = Shape.newPolygonShape(vertices);

        sensorShape = Shape.newCircleShape(shapeSize*6);
    }

    protected void customInitializing()
    {
        stateList.add(new State("IDLE",    -1,loadAnimation(atlas , "still"  , 5, 0.1f , shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
        stateList.add(new State("WALKING", -1,loadAnimation(atlas , "running", 8, 0.05f, shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
        stateList.add(new State("JUMPING", -1,loadAnimation(atlas , "jumping", 1, 1    , shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
        stateList.add(new State("FALLING", -1,loadAnimation(atlas , "falling", 2, 0.1f , shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
        stateList.add(new State("THROWING",-1,loadAnimation(atlas , "throwing",2, 0.2f , shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
    }



    public void setAnimationFrame(SpriteEntity SE)
    {
        /**if(Gdx.input.isTouched())
        {
            //region = stateList.get(4).getTextureAnimation().getKeyFrame(stateTimer, true);
        }
        else*/

        if(SE.b2body.getLinearVelocity().x == 0 && SE.b2body.getLinearVelocity().y==0)// if is still
        {
            SE.region = SE.stateList.get(0).getTextureAnimation().getKeyFrame(SE.stateTimer, true);
        }
        else    // if moving
        {
            if(SE.b2body.getLinearVelocity().x != 0)
            {
                SE.region = SE.stateList.get(1).getTextureAnimation().getKeyFrame(SE.stateTimer,true);
            }

            if(SE.b2body.getLinearVelocity().y != 0)
            {
                if (SE.b2body.getLinearVelocity().y > 0)
                {
                    SE.region = SE.stateList.get(2).getTextureAnimation().getKeyFrame(SE.stateTimer, true);
                }
                else if (SE.b2body.getLinearVelocity().y < 0)
                {
                    SE.region = SE.stateList.get(3).getTextureAnimation().getKeyFrame(SE.stateTimer, true);
                }
            }
        }

        // flip the frame if necessairy
        if ((SE.b2body.getLinearVelocity().x < 0 || !SE.facingRight) && !SE.region.isFlipX())
        {
            SE.region.flip(true, false);
            SE.facingRight = false;
        }
        else if ((SE.b2body.getLinearVelocity().x > 0 || SE.facingRight) && SE.region.isFlipX())
        {
            SE.region.flip(true, false);
            SE.facingRight = true;
        }
    }


    protected void setAbilities()
    {
        /**ArrayList<Infliction> Inf=new ArrayList<Infliction>();
        Inf.add(new Infliction(null,this,0,50,5,new Damage(100)));*/

        HitRules SHhitRules = new HitRules( 1,  1,  0,  0, 0, 0, levelState);

        /** passiveInflictions use is replicated */
        EntityGenerator shurikens = new EntityGenerator();
        //shurikens.setNumber(5);
        //shurikens.setInitialDelay(10);
        shurikens.timer = new Timer(10, 0, 5,0, false);
        //shurikens.setSource(this);
        shurikens.setRandomDisplacement(0.3f);
        shurikens.setAccuracy(0);/************************ NOT WORKING **/
        shurikens.setOrder("random");
        shurikens.entityToGenerate= new ArrowBar(levelState,atributes.team ,lockedTarget, 0,0, 0,0,SHhitRules);


        ArrayList<Action> ThrowShuriken = new ArrayList<Action>();
        ThrowShuriken.add(new Summon(shurikens, levelState,new Timer()));

        ArrayList<Action> MoveRight = new ArrayList<Action>();
        MoveRight.add(new DirectedMove(new Vector2(1,0),atributes.moveSpeed,atributes.maxSpeed,new Timer()));

        ArrayList<Action> MoveLeft = new ArrayList<Action>();
        MoveLeft.add(new DirectedMove(new Vector2(-1,0),atributes.moveSpeed,atributes.maxSpeed,new Timer()));

        ArrayList<Action> Jump = new ArrayList<Action>();
        Jump.add(new DirectedMove(new Vector2(0,1),atributes.jumpSpeed,atributes.maxSpeed*1000,new Timer()));

        abilities.add(new Ability("ThrowShuriken",this,1000,0,0,3000,0,ThrowShuriken));
        abilities.add(new Ability("MoveRight",this,20,0,0,0,0,MoveRight));
        abilities.add(new Ability("MoveLeft",this,20,0,0,0,0,MoveLeft));
        abilities.add(new Ability("Jump",this,200,0,0,0,0,Jump));
    }

    public void passiveAction()/**replace this by placing an ability in activeAbilityList*/
    {

    }

    public void defineShape(MapObject object)
    {
        sensorShape.setPosition(new Vector2(0,0.4f));

        /* FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = sensorShape;
        fixtureDef.isSensor=true;
        object.b2body.createFixture(fixtureDef).setUserData(this);*/

        object.fdef.shape = bodyShape;
        object.b2body.createFixture(object.fdef).setUserData(this);/**!!!creation here!!!**/

        levelState.sensorManager.sensorObjectList.add(new SensorObject(sensorShape,new ArrayList<Action>(),10,0,(short)1,(short)1,new Vector2(),0));
    }
}