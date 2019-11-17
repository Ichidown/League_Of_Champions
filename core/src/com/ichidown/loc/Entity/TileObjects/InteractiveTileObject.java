/**package com.ichidown.loc.Entity.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;


public abstract class InteractiveTileObject
{
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected LevelState levelState;
    protected MapObject object;

    protected String hitSound = "";
    protected String destroyedSound = "";

    protected Fixture fixture;

    public InteractiveTileObject(LevelState Lv, MapObject object)
    {
        this.object = object;

        this.levelState = Lv;
        this.world = levelState.getWorld();

        this.map = levelState.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Loc_Luncher.PPM, (bounds.getY() + bounds.getHeight() / 2) / Loc_Luncher.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Loc_Luncher.PPM, bounds.getHeight() / 2 / Loc_Luncher.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);


        initializeTile();
    }

    protected abstract void initializeTile();

    public void setCategoryFilter(short filterBit)
    {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(int HCell , int VCell) // VCell & HCell might be swiped
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * Loc_Luncher.PPM / HCell),
                (int)(body.getPosition().y * Loc_Luncher.PPM / VCell));
    }

    public abstract void Hit(com.ichidown.loc.Entity.MapObject userData);
}
*/