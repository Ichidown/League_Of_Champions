package com.ichidown.loc.Entity.EntityManagers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.Entity.Projectile;
import com.ichidown.loc.Entity.ConfigObjects.EntityGenerator;
import com.ichidown.loc.GameStates.State1.LevelState;
import com.ichidown.loc.Tools.Calculate;

import java.util.ArrayList;

public class ProjectileManager extends MapObjectManager
{
    private LevelState levelState;

    //private int ProjectileToGenerateLoad;
    public ArrayList<EntityGenerator> ProjectileToGenerate = new ArrayList<EntityGenerator>();
    public ArrayList<Projectile> Projectiles=new ArrayList<Projectile>();
    public ArrayList<Projectile> projectileTypeList=new ArrayList<Projectile>();
    private float DisplacedX;
    private float DisplacedY;
    private Vector2 source,destination;
    private Vector3 unprojectedPosition;

    private double newX,newY,calculatedDeviation;


    private float degree;
    private int PNumber;
    private int z;



    public ProjectileManager(LevelState levelState )
    {
        this.levelState=levelState;

        source = new Vector2();
        destination = new Vector2();
        unprojectedPosition = new Vector3();

        for(int i = 0;i<levelState.projectileLimit;i++)
        {
            Projectiles.add(new Projectile(levelState));
        }

        for(int i = 0;i<levelState.projectileGeneratorMaxNumber;i++)
        {
            ProjectileToGenerate.add(new EntityGenerator(levelState));
        }
    }

    public void activateProjectileGenerator(EntityGenerator pg, MapObject source)
    {
        listIndex=0;
        found = false;

        while (listIndex < ProjectileToGenerate.size() && !found)
        {
            if (!ProjectileToGenerate.get(listIndex).Active)
            {
                pg.cloneEntityGenerator(ProjectileToGenerate.get(listIndex),source);
                found=true;
            }
            else
            {
                listIndex++;
            }
        }
    }

    public void updateProjectileManager(float dt)/**must check where to position this code*/
    {
        UpdateProjectileGenerators(ProjectileToGenerate);

        for(int i = 0; i < Projectiles.size(); i++)
        {
            if(Projectiles.get(i).Active)
            {
            updateAbilities(Projectiles.get(i));
            updateDistanceMoved(Projectiles.get(i));
            updateObjectsVisibility(Projectiles.get(i).getX(), Projectiles.get(i).getY(), Projectiles.get(i), levelState);
            updateShape(Projectiles.get(i));
            updateTimers(Projectiles.get(i));
            standardUpdates(Projectiles.get(i), dt);


            Projectiles.get(i).hitRules.loopGlobalHitDelay();

            boddyUpdates(Projectiles.get(i));

            applyPassiveActions(Projectiles.get(i));

            Projectiles.get(i).previousDirection=Projectiles.get(i).b2body.getLinearVelocity().nor();

            //check if dead
            if(disposeOfDisposables(Projectiles.get(i)))
            {
                Projectiles.get(i).origin.deathAction(Projectiles.get(i));
                Projectiles.get(i).deActivate();
            }
            }
        }
    }

    public void UpdateProjectileGenerators(ArrayList<EntityGenerator> ProjectileGList)// does it still work if this unit is dead
    {
        for(EntityGenerator EGen:ProjectileGList)
        {
            if (EGen.Active)
            {
                // when delay == -1 : creating all the projectiles at once
                if (EGen.timer.delay == -1)
                {
                    GenerateXProjectile(EGen.timer.activations, EGen);
                }

                //when delay is < -1 : creating x number of projectile per frame
                else if (EGen.timer.delay < -1)
                {
                    GenerateXProjectile(-EGen.timer.delay,EGen);
                }

                //create 1 projectile after each delay
                else
                {
                    if (EGen.timer.canActivate())//EGen.timer.delayDone == 0)
                    {
                        GenerateOneProjectile(EGen);
                    }
                }

                EGen.timer.loopTimer();

                // check if depleted or when source is dead
                if (!EGen.timer.Active || (EGen.timer.stopWhenSourceDead && EGen.getSource().dead))//EGen.getNumber() == 0 || (EGen.isStopWhenSourceDead() && EGen.getSource().dead))
                {
                    EGen.Active=false;
                }
            }
        }
    }

    public void GenerateOneProjectile(EntityGenerator EGen)
    {
        createProjectile(EGen);
    }

    public void GenerateXProjectile(int i,EntityGenerator EGen)
    {
        while (EGen.timer.getActivationsNumber()>0 && i>0)
        {
            createProjectile(EGen);
            EGen.timer.loopActivationNumber();
            i--;
        }
    }

    public void createProjectile(EntityGenerator pg)/**to check the order of code in here**/
    {
        //SOURCE
        // x/y displacement
        DisplacedX = pg.getXDisplacement();
        DisplacedY = pg.getYDisplacement();

        //orientation
        if(!pg.getSource().facingRight)
            DisplacedX = -DisplacedX;

        //random displacement
        if(pg.getRandomDisplacement()!= 0)
        {
            DisplacedX+= Calculate.getRandomFloatNegativePositive(pg.getRandomDisplacement());
            DisplacedY+= Calculate.getRandomFloatNegativePositive(pg.getRandomDisplacement());
        }

        /**the source can be a specific position in the world (case of generating an object mid-air)*/
        if(pg.lockedSource)
        {
            source.x = pg.getSource().b2body.getPosition().x + DisplacedX;
            source.y = pg.getSource().b2body.getPosition().y + DisplacedY;
        }
        else
        {
            source.x = pg.getFixedSource().x + DisplacedX;
            source.y = pg.getFixedSource().y + DisplacedY;
        }

        unprojectedPosition.x=Gdx.input.getX();
        unprojectedPosition.y=Gdx.input.getY();
        unprojectedPosition = levelState.gamecam.unproject(unprojectedPosition);

        destination.x = unprojectedPosition.x;
        destination.y = unprojectedPosition.y;


        // adjust the angle of the destination
        calculatedDeviation = pg.getDegreeDisplacement()+CalculateDeviation(pg);
        calculatedDeviation = Math.toRadians(calculatedDeviation);

        newX = (destination.x-source.x) * Math.cos(calculatedDeviation) - (destination.y-source.y) * Math.sin(calculatedDeviation) + source.x;
        newY = (destination.x-source.x) * Math.sin(calculatedDeviation) + (destination.y-source.y) * Math.cos(calculatedDeviation) + source.y;

        destination.set((float)newX,(float)newY);

        activateProjectile(pg);
    }

    private void activateProjectile(EntityGenerator pg)
    {
        listIndex=0;
        found=false;

        while(listIndex<Projectiles.size() && !found)
        {
            if(!Projectiles.get(listIndex).Active)
            {
                CreateProjectile(pg.entityToGenerate.getClass().toString(), source, pg.getSource(), destination);
                found=true;
            }
            else
            {
                listIndex++;
            }
        }
    }

    private float CalculateDeviation(EntityGenerator pg)
    {
            if (pg.getOrder().equals("random"))
                return getRandomDeviation(pg);
            if (pg.timer.getActivationsNumber() == 1)//in case of 1 projectile
                return 0;
            else if (pg.getOrder().equals("rotate"))
                return getRotateDeviation(pg);
            else if (pg.getOrder().equals("unrotate"))
                return getUnRotateDeviation(pg);
            else if (pg.getOrder().equals("center"))
                return getCenterDeviation(pg);
            else if (pg.getOrder().equals("sides"))
                return getSidesDeviation(pg);
            else
                return 0;
    }

    private float getRandomDeviation(EntityGenerator pg)
    {
        return Calculate.getRandomFloatNegativePositive(pg.getAccuracy() / 2);
    }

    private float getRotateDeviation(EntityGenerator pg)
    {
        if (pg.getCurrentDeviation() == -1)//1st shoot
        {
            degree = pg.getAccuracy() / 2;// /2 so that we start from one half of the full deviation

            /**if (pg.timer.getActivationsNumber() == 1)//in case of 1 projectile
                return 0;
            else //when more than one projectile
            {*/
                PNumber = pg.timer.activationsDone;
                if (degree * 2 >= -180 && degree * 2 <= 180)//to avoid a bug where for exp: at 360 we get 2 projectiles with the same deviation
                    PNumber--;

                pg.setAdditionalDeviation(Math.abs(degree * 2 / PNumber));
                pg.setCurrentDeviation(degree);
                return degree;
            //}
        } else {
            pg.setCurrentDeviation(pg.getCurrentDeviation() - pg.getAdditionalDeviation());
            return pg.getCurrentDeviation();
        }
    }

    private float getUnRotateDeviation(EntityGenerator pg)
    {
        if (pg.getCurrentDeviation() == -1)//1st shoot
        {
            degree = pg.getAccuracy() / 2;// /2 so that we start from one half of the full deviation

            /**if (pg.getNumber() <= 1)//i case of 1 projectile (or other)
                return 0;
            else //when more than one projectile
            {*/
                PNumber = pg.timer.getActivationsNumber();
                if (degree * 2 >= -180 && degree * 2 <= 180)//to avoid a bug where for exp: at 360 we get 2 projectiles with the same deviation
                    PNumber--;

                pg.setAdditionalDeviation(Math.abs(degree * 2 / Math.abs(PNumber)));
                pg.setCurrentDeviation(-degree);

                return -degree;
            //}
        }
        else
        {
            pg.setCurrentDeviation(pg.getCurrentDeviation() + pg.getAdditionalDeviation());

            return pg.getCurrentDeviation();
        }
    }

    private float getCenterDeviation(EntityGenerator pg)
    {
        if (pg.getCurrentDeviation() == -1)//1st shoot
        {
            degree = pg.getAccuracy() / 2;// /2 so that we start from one half of the full deviation

            /**if (pg.getNumber() <= 1)//i case of 1 projectile (or other)
                return 0;
            else //when more than one projectile
            {*/
                PNumber = pg.timer.getActivationsNumber();
                if (degree * 2 >= -180 && degree * 2 <= 180)//to avoid a bug where for exp: at 360 we get 2 projectiles with the same deviation
                    PNumber--;

                pg.setAdditionalDeviation(Math.abs(degree * 2 / PNumber));

                if (pg.timer.getActivationsNumber() % 2 == 0) //pair
                {
                    pg.setCurrentDeviation(pg.getAdditionalDeviation() / 2);
                    pg.setShouldIncrementDeviation(false);
                    return pg.getCurrentDeviation();
                } else//impair
                {
                    pg.setCurrentDeviation(0);
                    return 0;
                }
            //}
        } else {
            if (pg.isShouldIncrementDeviation()) {
                pg.setCurrentDeviation(pg.getCurrentDeviation() + pg.getAdditionalDeviation());
                pg.setShouldIncrementDeviation(false);
            } else {
                pg.setCurrentDeviation(-pg.getCurrentDeviation());
                pg.setAdditionalDeviation(-pg.getAdditionalDeviation());
                pg.setShouldIncrementDeviation(true);
            }

            return pg.getCurrentDeviation();
        }
    }

    private float getSidesDeviation(EntityGenerator pg)
    {
        if (pg.getCurrentDeviation() == -1)//1st shoot
        {
            degree = pg.getAccuracy() / 2;// /2 so that we start from one half of the full deviation

            /**if (pg.getNumber() <= 1)//i case of 1 projectile (or other)
                return 0;
            else //when more than one projectile
            {*/
                PNumber = pg.timer.getActivationsNumber();

                if (degree * 2 >= -180 && degree * 2 <= 180)//to avoid a bug where for exp: at 360 we get 2 projectiles with the same deviation
                    PNumber--;

                pg.setAdditionalDeviation(Math.abs(degree * 2 / PNumber));

                if (pg.timer.getActivationsNumber() % 2 == 0) //pair
                {
                    if (degree * 2 >= -180 && degree * 2 <= 180)
                        pg.setCurrentDeviation(degree);
                    else
                        pg.setCurrentDeviation(degree - pg.getAdditionalDeviation() / 2);

                    pg.setShouldIncrementDeviation(false);
                    return pg.getCurrentDeviation();
                } else//impair
                {
                    //pg.setShouldIncrementDeviation(false);
                    pg.setCurrentDeviation(0);
                    return 0;
                }

            //}
        } else {
            if (pg.isShouldIncrementDeviation()) {
                pg.setCurrentDeviation(pg.getCurrentDeviation() - pg.getAdditionalDeviation());
                pg.setShouldIncrementDeviation(false);
            } else {
                pg.setCurrentDeviation(-pg.getCurrentDeviation());
                pg.setAdditionalDeviation(-pg.getAdditionalDeviation());
                pg.setShouldIncrementDeviation(true);
            }

            return pg.getCurrentDeviation();
        }
    }



    protected void CreateProjectile(String proectileType,Vector2 source,MapObject mpSource,Vector2 Target)
    {
        /**switch(AbilityNumber)/**NEED TO BE AUTOMATISED*/
        /**{
            case 0: p = new ArrowBar( levelState, source.x, source.y, mpSource.facingRight, mpSource,source, Target,0,inflictions,new HitRules(1,-1,0,0,-1,-1));
                break;
        }*/
        for(int i=0;i<projectileTypeList.size();i++)
        {
            if(proectileType.equals(projectileTypeList.get(i).getClass().toString()))
            {
                projectileTypeList.get(i).cloneProjectile(Projectiles.get(listIndex),source.x,source.y,Target.x,Target.y,mpSource.atributes.team);

                projectileTypeList.get(i).startAction(Projectiles.get(listIndex));
            }
        }



        //p.defineObject(p, source.x, source.y);// define fixture object
        //p.UpdatePointerPosition();//set the target

        //p.startAction();

        //return p;
    }


    public void drawProjectiles(SpriteBatch gameBatch)
    {
        //gameBatch.begin();
        //draw all proectiles
        for (Projectile projectile : Projectiles)
        {
            if(projectile.Active && projectile.visibleToCam)
                projectile.draw(gameBatch);
        }
        //gameBatch.end();
    }


}
