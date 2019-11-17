package com.ichidown.loc.Premade.Units;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ichidown.loc.Entity.ConfigObjects.Ability;
import com.ichidown.loc.Entity.ConfigObjects.State;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Entity.SpriteEntity;
import com.ichidown.loc.Entity.Unit;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;
import com.ichidown.loc.Tools.Ai;
import com.ichidown.loc.Tools.Shape;

public class Minion extends Unit
{

    //private BufferedImage[] sprites;
    private PolygonShape minionShape;

    public Minion(LevelState Lv, int team)
    {
        super(Lv, team, true);
        InitialisePlayer();
        createTextureAtlas();
        customInitializing();
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
        //shapeSize = .05f;

        spritesPath = "";
        hitSound = "";
        destroyedSound = "";

        atributes.team = 2;
        //for dying sprites : color per team difference


        //resources path
        spritesPath = "minions_sprites.pack";
        String dp = "/Sprites/Enemies/explosion_blue.png";


        //animations
        //numFrames = new int[]{3};//number of sprites for each animation
        //numDyingFrames=6;
        //idleAnimationDelay=10;

        //dyingAnimationDelay=100;
        //animationDelay =100;

        //animation actions
       /* IDLE=0;
        WALKING=0;
        JUMPING=0;
        FALLING=0;
        GLIDING=0;*/
        //FIREBALL=0;
        //SCRATCHING=0; creates a bug (must know why)




        abilities.add(new Ability("HIT",this,1,0,0,0,0,null));

        //player Object
        //originalPlayer=this;//..................................................

        //stats

        atributes.maxSpeed = 0.5f;
        atributes.moveSpeed = atributes.maxSpeed/20;

        atributes.maxHealth = 3000;
        atributes.health = atributes.maxHealth;


        //damage = 2000;
        atributes.experienceValue =450;
        /** GoldGain=15; **/

        atributes.jumpSpeed = 1.5f;//need to be changed into distance jump & not jump timer
        atributes.jumpDistance = atributes.remainingJumpDistance = 40;
        //fallSpeed=0.2;

        atributes.healthRegen = 5;

        //cwidth = originalCwidth = 60;
        //cheight = originalCheight = 60;
        shapeSize = 0.1f;

        mass_density = 5f;
        friction = 0.05f;
        bounciness = 0.5f;

        colisionLayer = Loc_Luncher.MOB_BIT;
        colidableLayers = Loc_Luncher.GROUND_BIT | Loc_Luncher.PLAYER_BIT | Loc_Luncher.PROJECTILE_BIT | Loc_Luncher.MOB_BIT;

        //deadEntity = new DeadEntity(width,height,10,dp,6,levelState, shapeShifter);
        shapeShifter.setRotateWithBody(true);
        shapeShifter.initialWidth=90;
        shapeShifter.initialHeigth=90;

        //colisionRadius=2f;


        gravityScale=1;

        //lifeTime= 100;



        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(-1, 2).scl(shapeSize);
        vertices[1] = new Vector2(-1, 0).scl(shapeSize);
        vertices[2] = new Vector2(0, -3).scl(shapeSize);
        vertices[3] = new Vector2(1, 0).scl(shapeSize);
        vertices[4] = new Vector2(1, 1).scl(shapeSize);

        minionShape = Shape.newPolygonShape(vertices);
    }

    public void updateAiBehaviour()
    {
        Ai.SimpleMovementAi(this);
    }

    protected void customInitializing()
    {
        /**USE for loop to automatize here*/

        //b2body.setActive(true);

        stateList.add(new State("IDLE",    -1,loadAnimation(atlas, "minion_red", 1, 1, shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 180)));
        stateList.add(new State("WALKING", -1,loadAnimation(atlas, "minion_red", 3, 0.1f, shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
        stateList.add(new State("JUMPING", -1,loadAnimation(atlas , "minion_red", 1, 1 , shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));
        stateList.add(new State("FALLING", -1, loadAnimation(atlas, "minion_red", 1, 1, shapeShifter.initialWidth, shapeShifter.initialHeigth, 0, 0)));

    }


    public void setAnimationFrame(SpriteEntity SE)
    {
        if(SE.b2body.getLinearVelocity().x == 0 && SE.b2body.getLinearVelocity().y==0)// if is still
        {
            SE.region = SE.stateList.get(0).getTextureAnimation().getKeyFrame(SE.stateTimer, true);
        }
        else    // if moving
        {
            if(SE.b2body.getLinearVelocity().x != 0) {
                SE.region = SE.stateList.get(1).getTextureAnimation().getKeyFrame(SE.stateTimer,true);
            }

            if(SE.b2body.getLinearVelocity().y != 0) {
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
        Inf.add(new Infliction(null,this,0,0,1,new Damage(3000)));

        passiveInflictions.addAll(Inf);*/
    }

    public void deathAction()
    {

    }

    public void defineShape(MapObject object)
    {
        object.fdef.shape = minionShape;
        object.b2body.createFixture(object.fdef).setUserData(this);/**!!!creation here!!!**/
    }
}


