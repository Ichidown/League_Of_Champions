package com.ichidown.loc.Entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ichidown.loc.Entity.ConfigObjects.Ability;
import com.ichidown.loc.Entity.ConfigObjects.Atributes;
import com.ichidown.loc.Entity.ConfigObjects.HitRules;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;

import java.util.ArrayList;


public abstract class MapObject extends SpriteEntity
{
    public MapObject origin;
    public float mass_density;
    public float friction;
    public float bounciness;
    public float gravityScale =0;
    public short colisionLayer;// = 0;
    public short colidableLayers;// = 1;
    public float shapeSize = 0.1f;
    public int initPassiveActionDelay=-1;
    public int passiveActionDelay=0;
    public MapObject selectedTarget;
    public int bounces=-1;
    // in case the map object is linked with a destination : needed when the obj get attracted to another ..
    //public Vector2 startPoint=new Vector2();//the starting point of the projectile
    //public Vector2 endPoint=new Vector2();

    //public ArrayList<Infliction> passiveInflictions = new ArrayList<Infliction>(); // the inflictions that affect other unitsList
    //public ArrayList<Infliction> activeInflictions = new ArrayList<Infliction>(); // inflictions that affect this unit

    public ArrayList<Ability> abilities = new ArrayList<Ability>();

    public Vector2 pointerPosition=new Vector2();
    public Vector3 pointerPositionUnproected = new Vector3();

    public float maxDistance = -1;
    public float moveDistance = 0;
    public Vector2 previousPosition=new Vector2();
    public Vector2 previousDirection=new Vector2();


    public Atributes atributes=new Atributes();

    public HitRules hitRules=new HitRules(levelState);

    public BodyDef bdef=new BodyDef();
    public FixtureDef fdef = new FixtureDef();
    //public CircleShape shape = Shape.newCircleShape(1);









    //constructor
    public MapObject(LevelState lv,boolean facingRight)
    {
        super(lv,facingRight);
    }

    public MapObject(LevelState lv)
    {
        super(lv);

        for(int i=0; i<lv.abilityCap;i++)
        {
            abilities.add(new Ability(lv,this));
        }

        defineObject(this);
    }





    public void cloneMapObject(MapObject mp,float x,float y,int team)
    {
        cloneSpriteEntity(mp);

        mp.origin=this;
        mp.origin=this;
        mp.mass_density=mass_density;
        mp.friction=friction;
        mp.bounciness=bounciness;
        mp.gravityScale=gravityScale;
        mp.colisionLayer=colisionLayer;
        mp.colidableLayers=colidableLayers;
        mp.shapeSize = shapeSize;
        //mp.colisionRadius=colisionRadius;
        mp.initPassiveActionDelay=initPassiveActionDelay;
        mp.passiveActionDelay=passiveActionDelay;
        mp.bounces=bounces;
        //mp.startPoint.x=x;
        //mp.startPoint.y=y;
        //mp.endPoint.x=x;
        //mp.endPoint.y=y;
        mp.pointerPosition.x=x;
        mp.pointerPosition.y=y;
        mp.pointerPositionUnproected.x=0;
        mp.pointerPositionUnproected.y=0;
        mp.pointerPositionUnproected.z=0;
        mp.maxDistance=maxDistance;
        mp.moveDistance=moveDistance;
        mp.previousPosition.x=x;
        mp.previousPosition.y=y;
        mp.previousDirection.x=0;
        mp.previousDirection.y=0;

        mp.selectedTarget=selectedTarget;

        //defineObject(mp, 0, 0);

        reDeffineObject(mp,x,y);

        hitRules.cloneHitRules(mp.hitRules);
        atributes.cloneAtributes(mp.atributes,team);

        //mp.passiveInflictions=passiveInflictions;

        //reset inflictions
        /**for(int i=0;i< mp.activeInflictions.size();i++)
        {
            mp.activeInflictions.get(i).Active=false;
        }
        //copy inflictions
        for(int i=0;i< activeInflictions.size();i++)
        {
            activeInflictions.get(i).cloneInfliction(mp.activeInflictions.get(i));
        }*/

        //reset abilities
        for(int i=0;i< mp.abilities.size();i++)
        {
            mp.abilities.get(i).Active=false;
        }

        //copy abilities
        for(int i=0;i< abilities.size();i++)
        {
            abilities.get(i).cloneAbility(mp.abilities.get(i),mp);
        }

    }

    public void deActivate()
    {
        Active=false;
        fdef.filter.categoryBits=0;
        fdef.filter.maskBits=1;

        for(Fixture fixture:b2body.getFixtureList())
        {
            fixture.setFilterData(fdef.filter);
        }

        b2body.setTransform(0,0,0);

        b2body.setAwake(false);
        b2body.setGravityScale(0);
    }








    public void incrementPassiveActionDelay()
    {
        passiveActionDelay--;
        if(passiveActionDelay<0)
        {
            passiveActionDelay=initPassiveActionDelay;
        }
    }






    /*public void Hit(MapObject userData)// hit actions here : apply passiveInflictions
    {
        /**for (int i = 0 ; i<userData.passiveInflictions.size() ; i++)
        {
            activeInflictions.add(userData.passiveInflictions.get(i).cloneInfliction());
            activeInflictions.get(activeInflictions.size()-1).setTarget(this);
        }*/
    /*}*/

    public void updateBounces()
    {
        if(bounces>=0)
            bounces--;
        if(bounces==-1)
            dead=true;
            //disposable=true;
    }


    public void defineObject(MapObject object)
    {
        //set initial values for marios location, width and height.
        //  + initial frame as marioStand.


        /*object.setBounds(
                object.getX(),
                object.getY(),
                object.shapeShifter.initialWidth / Loc_Luncher.PPM,
                object.shapeShifter.initialHeigth / Loc_Luncher.PPM);*/

        // set the colision box/circle/or any ...
        //object.bdef.position.set(object.previousPosition.x, object.previousPosition.y);
        object.bdef.type = BodyDef.BodyType.DynamicBody;
        object.bdef.gravityScale= 0;


        object.b2body = object.levelState.getWorld().createBody(object.bdef);/**!! creation here !!**/

        //object.fdef.filter.categoryBits = object.colisionLayer;
        //object.fdef.filter.maskBits = object.colidableLayers;

        //object.fdef.density=object.mass_density;
        //object.fdef.friction=object.friction;
        //object.fdef.restitution=object.bounciness;
        //object.shape.setRadius(shapeSize);

        //object.fdef.shape = shape;


        /*********************************************/

        //object.defineShape(object);

        /**Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(-1, 2).scl(shapeSize);
        vertices[1] = new Vector2(-1, 0).scl(shapeSize);
        vertices[2] = new Vector2(0, -3).scl(shapeSize);
        vertices[3] = new Vector2(1, 0).scl(shapeSize);
        vertices[4] = new Vector2(1, 1).scl(shapeSize);

        PolygonShape sensor = Shape.newPolygonShape(vertices);

        object.fdef.shape = sensor;

        object.b2body.createFixture(fdef).setUserData(this);/**!!!creation here!!!**/

        /*********************************************/



        // set the rotation center point
        //object.setOrigin(object.getWidth()/2, object.getHeight()/2);

        object.b2body.setAwake(false);

        //object.b2body.getFixtureList().get(0).getShape().setRadius(shapeSize);


        /** POLYGON SHAPE */
        /**Vector2[] vertices = new Vector2[4];
         vertices[0] = new Vector2(-5, 8).scl(1 / Loc_Luncher.PPM);
         vertices[1] = new Vector2(5, 8).scl(1 / Loc_Luncher.PPM);
         vertices[2] = new Vector2(-3, 3).scl(1 / Loc_Luncher.PPM);
         vertices[3] = new Vector2(3, 3).scl(1 / Loc_Luncher.PPM);
         fdef.shape = Shape.newPolygonShape(vertices);
         fdef.restitution = 0.5f;
         fdef.filter.categoryBits = Loc_Luncher.ENEMY_HEAD_BIT;
         b2body.createFixture(fdef).setUserData(this);*/


        /** EDGE SHAPE */
        /**fdef.shape = Shape.newLineShape(-2 / Loc_Luncher.PPM, 6 / Loc_Luncher.PPM, 2 / Loc_Luncher.PPM, 6 / Loc_Luncher.PPM);
         fdef.filter.categoryBits = Loc_Luncher.MARIO_HEAD_BIT;
         fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);*/

    }


    public void reDeffineObject(MapObject object,float x,float y)
    {
        defineShape(object);

        //object.setPosition(x, y);
        object.b2body.setAwake(true);

        object.setBounds(
                object.getX(),
                object.getY(),
                object.shapeShifter.initialWidth / Loc_Luncher.PPM,
                object.shapeShifter.initialHeigth / Loc_Luncher.PPM);

        object.fdef.filter.categoryBits = object.colisionLayer;
        object.fdef.filter.maskBits = object.colidableLayers;

        for(Fixture fixture:object.b2body.getFixtureList())
        {
            fixture.setFilterData(object.fdef.filter);
            fixture.setDensity(object.mass_density);
            fixture.setFriction(object.friction);
            fixture.setRestitution(object.bounciness);
            //fixture.getShape().setRadius(object.shapeSize);
        }

        object.b2body.setGravityScale(object.gravityScale);

        // set the rotation center point
        object.setOrigin(object.getWidth() / 2, object.getHeight() / 2);

        object.b2body.setTransform(x, y, 0);

    }


    public void defineShape(MapObject object)
    {

    }


    public void UpdatePointerPosition()
    {
        pointerPositionUnproected.x=Gdx.input.getX();
        pointerPositionUnproected.y=Gdx.input.getY();
        pointerPositionUnproected = levelState.gamecam.unproject(pointerPositionUnproected);

        pointerPosition.x = pointerPositionUnproected.x;
        pointerPosition.y = pointerPositionUnproected.y;
    }

    /**public void disposeOfFixtures()
    {
        for(int f=0;f<b2body.getFixtureList().size;f++)
        {
            b2body.destroyFixture(b2body.getFixtureList().get(f));
        }

        b2body=null;
    }*/


    public void deathAction(MapObject SE)
    {

    }

    public void startAction(MapObject SE)
    {

    }

    public void passiveAction(MapObject SE)
    {

    }

}



