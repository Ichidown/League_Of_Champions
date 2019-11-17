package com.ichidown.loc.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ichidown.loc.Premade.Units.Minion;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;


public class B2WorldCreator
{

    public TiledMap map;
    private Rectangle rect;

    public B2WorldCreator(LevelState Lv)
    {
        map = Lv.getMap();

        //create colision blocks
        generateMapColision(Lv,map,3);

        //create brick bodies/fixtures
        //generateTileMap(Lv,map,2);

        //create all mobs
        //generateUnits(Lv,map,6);
    }

    private void generateMapColision(LevelState Lv , TiledMap map,int layerIndex)
    {
        //create body and fixture variables
        World world = Lv.getWorld();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Loc_Luncher.PPM, (rect.getY() + rect.getHeight() / 2) / Loc_Luncher.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Loc_Luncher.PPM, rect.getHeight() / 2 / Loc_Luncher.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    /**private void generateTileMap(LevelState Lv , TiledMap map , int layerIndex)
    {
        for(MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class))
        {
            //new Brick(Lv, object);
        }
    }*/

    public void generateUnits(LevelState Lv, TiledMap map, int layerIndex)
    {
        Minion minion = new Minion(Lv,2);


        for(MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class))
        {
            rect = ((RectangleMapObject) object).getRectangle();

            //Unit unit = new Minion(Lv, rect.getX() / Loc_Luncher.PPM, rect.getY() / Loc_Luncher.PPM,1);
            //unit.defineObject(unit, rect.getX() / Loc_Luncher.PPM,rect.getY() / Loc_Luncher.PPM);

            //units.add(unit);

            Lv.unitManager.activateUnit(minion, rect.getX() / Loc_Luncher.PPM, rect.getY() / Loc_Luncher.PPM, 2);
        }

    }

}
