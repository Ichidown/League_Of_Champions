package com.ichidown.loc.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Entity.Projectile;
import com.ichidown.loc.Entity.Unit;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Loc_Luncher;


public class WorldContactListener implements ContactListener
{
    private LevelState levelState;
    private Fixture fixA ;
    private Fixture fixB ;
    private int cDef;


    public WorldContactListener(LevelState levelState)
    {
        this.levelState=levelState;
    }

    @Override
    public void beginContact(Contact contact)
    {
        afterContactAction(contact);
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {
        /**try
        {
            System.out.println(((MapObject) contact.getFixtureA().getUserData()).b2body.getAngle());

        }catch (Exception e)
        {
            System.out.println(((MapObject) contact.getFixtureB().getUserData()).b2body.getAngle());
        }*/

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void afterContactAction(Contact contact)
    {
        fixA = contact.getFixtureA();
        fixB = contact.getFixtureB();

        cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef)
        {
            case Loc_Luncher.GROUND_BIT | Loc_Luncher.PROJECTILE_BIT :
                try
                {
                    ((MapObject) fixA.getUserData()).updateBounces();
                }catch (Exception e)
                {
                    ((MapObject) fixB.getUserData()).updateBounces();
                }
                break;

            /**case  Loc_Luncher.MOB_BIT | Loc_Luncher.PLAYER_BIT  : /** automatise this */
                /**((Unit) fixB.getUserData()).Hit((Unit) fixA.getUserData());
                break;*/

            case  Loc_Luncher.MOB_BIT | Loc_Luncher.PROJECTILE_BIT : /** automatise this */

                try
                {
                    //((Unit) fixA.getUserData()).Hit((Projectile) fixB.getUserData());
                    ((Projectile) fixB.getUserData()).updateBounces();
                }catch (Exception e)
                {
                    //((Unit) fixB.getUserData()).Hit((Projectile) fixA.getUserData());
                    ((Projectile) fixA.getUserData()).updateBounces();
                }

                //((MapObject)fixB.getUserData()).Hit((MapObject) fixA.getUserData());

                break;
        }
    }



}
