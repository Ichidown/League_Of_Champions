package com.ichidown.loc.Entity.ConfigObjects;


public class Atributes
{

    /**each time the experience change its value : we will need to calculate : level function && level*/
    public int level;/** calculated from : experience +  levelFunction(defined in update stats method) */
    public int maxLevel=10;

    public int experience; // the experience this unit have
    public int maxExperience;

    public int experienceValue;//the experient we get from this unit death

    public int energy=100;
    public int maxEnergy=100;
    public int energyRegen=1;


    //stats & states
    public int team;

    public int health;
    public int maxHealth;
    public int healthRegen;

    //movement attributes
    public float moveSpeed;//gota link with animation speed !!!
    public float dashSpeed;
    public float maxSpeed;

    public double stopSpeed;/**replace this with b2body world resistance*/

    public double maxFallSpeed;//not used
    public float jumpSpeed,jumpDistance, remainingJumpDistance;



    public Atributes()
    {

    }

    public void cloneAtributes(Atributes atributes,int team)
    {
        atributes.level=level;
        atributes.maxLevel=maxLevel;
        atributes.experience=experience;
        atributes.maxExperience=maxExperience;
        atributes.experienceValue=experienceValue;
        atributes.energy=energy;
        atributes.maxEnergy=maxEnergy;
        atributes.energyRegen=energyRegen;
        atributes.team=team;
        atributes.health=health;
        atributes.maxHealth=maxHealth;
        atributes.healthRegen=healthRegen;
        atributes.moveSpeed=moveSpeed;
        atributes.dashSpeed=dashSpeed;
        atributes.maxSpeed=maxSpeed;
        atributes.stopSpeed=stopSpeed;
        atributes.maxFallSpeed=maxFallSpeed;
        atributes.jumpSpeed=jumpSpeed;
        atributes.jumpDistance=jumpDistance;
        atributes.remainingJumpDistance=remainingJumpDistance;
    }


}
