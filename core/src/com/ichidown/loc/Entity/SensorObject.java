package com.ichidown.loc.Entity;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.ichidown.loc.Entity.ConfigObjects.Action;
import com.ichidown.loc.Entity.ConfigObjects.HitRules;
import com.ichidown.loc.GameStates.State1.LevelState;
import java.util.ArrayList;

public class SensorObject
{
    public LevelState levelState;
    public MapObject source;
    public ArrayList<Action> actions;
    public ArrayList<MapObject> tagetList;
    public int maxTargetNumber; // 0 == infinite;
    public int targetSelectionStrategy; // 0 == randomly , 1 == closest , 2 == farthest ...

    public HitRules hitRules;

    public boolean Active;

    //sensor atribs ...
    public Body b2body;
    public BodyDef bdef;
    public FixtureDef fdef;
    public short colisionLayer,colidableLayers;


    public Vector2 relativePosition;
    public int rotation;
    public Shape shape;





    public SensorObject(LevelState levelState)// blank creation
    {
        this.levelState=levelState;


        tagetList = new ArrayList<MapObject>();
        bdef=new BodyDef();
        fdef = new FixtureDef();

        maxTargetNumber=0;
        targetSelectionStrategy=1;

        colisionLayer=0;
        colidableLayers=1;

        defineBody(this);

        actions = new ArrayList<Action>();
        for(int i=0;i<levelState.sensorActionsLimit;i++)
        {
            actions.add(new Action(levelState));
        }

        Active=false;
    }

    public SensorObject(Shape shape,ArrayList<Action> actions,int maxTargetNumber,int targetSelectionStrategy,short colisionLayer,short colidableLayers,Vector2 relativePosition,int rotation)//pre-made creation
    {
        this.shape=shape;
        this.actions=actions;
        this.maxTargetNumber=maxTargetNumber;
        this.targetSelectionStrategy=targetSelectionStrategy;
        this.colisionLayer=colisionLayer;
        this.colidableLayers=colidableLayers;
        this.relativePosition=relativePosition;
        this.rotation=rotation;
    }

    public void cloneSensorObject(MapObject source, SensorObject sensorObject)// creation
    {
        sensorObject.source=source;
        sensorObject.maxTargetNumber=maxTargetNumber;
        sensorObject.targetSelectionStrategy=targetSelectionStrategy;
        sensorObject.colisionLayer=colisionLayer;
        sensorObject.colidableLayers=colidableLayers;

        for(int i=0;i<actions.size();i++)
        {
            actions.get(i).cloneAction(sensorObject.actions.get(i),source);
        }


        sensorObject.fdef.shape = shape;

        reDefineBody(sensorObject);

        sensorObject.Active=true;
    }

    public void defineBody(SensorObject object)
    {
        object.bdef.type = BodyDef.BodyType.DynamicBody;
        object.bdef.gravityScale= 0;

        object.b2body = object.levelState.getWorld().createBody(object.bdef);/**!! creation here !!**/

        object.b2body.setAwake(false);
    }

    public void reDefineBody(SensorObject object)
    {
        object.b2body.createFixture(fdef).setUserData(object);/**!!!creation here!!!**/
        object.b2body.setTransform(relativePosition.x,relativePosition.y,rotation);
    }

    public void deActivate()
    {
        Active=false;
        b2body.getFixtureList().removeIndex(0);
        b2body.setTransform(0,0,0);
        b2body.setAwake(false);
    }

}
