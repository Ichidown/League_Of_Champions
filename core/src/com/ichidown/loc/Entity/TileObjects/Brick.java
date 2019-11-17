/**package com.ichidown.loc.Entity.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;


public class Brick extends InteractiveTileObject
{

    public Brick(LevelState Lv, MapObject object)
    {
        super(Lv, object);
        fixture.setUserData(this);
        setCategoryFilter(Loc_Luncher.PROJECTILE_BIT);
    }

    @Override
    protected void initializeTile()
    {
        hitSound = "audio/sounds/bump.wav";
        destroyedSound = "";
    }

    @Override
    public void Hit(com.ichidown.loc.Entity.MapObject u)
    {
        Loc_Luncher.manager.get(hitSound, Sound.class).play();
    }

}*/
