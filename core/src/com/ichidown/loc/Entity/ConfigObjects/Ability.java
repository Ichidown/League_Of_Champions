package com.ichidown.loc.Entity.ConfigObjects;


import com.ichidown.loc.Entity.MapObject;
import com.ichidown.loc.GameStates.State1.LevelState;

import java.util.ArrayList;

public class Ability
{
    private int Cost;
    private String Name;

    //private int initDelay,delay,activations;

    //private int activations;

    private  int initCouldown; // replace by an object that contains all possible ability restrictions
    private int cooldown; // cooldown counter

    //private ArrayList<Ability> abilityList = new ArrayList<Ability>();
    private ArrayList<Action> actionList = new ArrayList<Action>();

    private MapObject source,target;

    public int maxCharges,chargeCost,charges;

    public int chanelTime;
    public boolean applyBeforeChanelEnd;
    public boolean Active;



    public Ability(String Name,MapObject source, int Cost,int cooldown ,int Delay,int maxCharges,int chargeCost,ArrayList<Action> actionList)//,ArrayList<Ability> abilityList)
    {
        this.Name=Name;
        this.Cost=Cost;
        //this.initDelay =Delay;
        this.initCouldown =cooldown;
        //this.abilityList=abilityList;
        if(actionList!=null) this.actionList=actionList;
        this.maxCharges=maxCharges;
        this.chargeCost=chargeCost;

        this.cooldown=0;
        //delay=initDelay;
        //activations=0;

        charges = maxCharges;
        chanelTime=0;
        applyBeforeChanelEnd=false;
        Active=true;
    }

    public Ability(LevelState levelState,MapObject source)//,ArrayList<Ability> abilityList)
    {
        this.source=source;

        for(int i=0;i<levelState.actionListLimit;i++)
        {
            actionList.add(new Action(source.levelState));
        }
        Active=false;
    }

    public void cloneAbility(Ability ability , MapObject source)
    {
        ability.Name=Name;
        ability.Cost=Cost;
        //ability.initDelay=initDelay;
        ability.initCouldown=initCouldown;
        //ability.target=ability.source;
        //ability.source=source;
        ability.maxCharges=maxCharges;
        ability.chargeCost=chargeCost;
        ability.cooldown=0;
        //ability.delay=initDelay;
        //ability.activations=0;
        ability.charges=maxCharges;

        //ability.abilityList=abilityList;
        //ability.actionList=actionList;
        ability.chanelTime=chanelTime;
        ability.applyBeforeChanelEnd=applyBeforeChanelEnd;
        ability.Active=true;



        //reset
        for(Action action:ability.actionList)
            action.Active=false;

        //copy
        for(int i=0;i<actionList.size();i++)
        {
            actionList.get(i).cloneAction(ability.actionList.get(i),source);
        }

    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    /*public int getInitDelay() {
        return initDelay;
    }

    public void setInitDelay(int initDelay) {
        this.initDelay = initDelay;
    }*/

    public int getInitCouldown() {
        return initCouldown;
    }

    public void setInitCouldown(int initCouldown) {
        this.initCouldown = initCouldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void updateCharges()
    {
        if(charges<maxCharges)
        {
            charges++;
        }
    }

    public void updateCooldown()
    {
        if(initCouldown > 0 && cooldown >0)
        {
            cooldown--;
        }
    }

    /**public  void updateActions()
    {
        for(Action action:actionList)
        {
            if(action.Active)
            {
                System.out.println("oh boy");
                //action.timer.loopTimer();
                //if(action.timer.canActivate())
                //{
                    source.levelState.actionManager.activateAction(action,source);
                    //action.origin.applyAction(action);
                //}
            }
        }
    }*/

    /**public void updateDelay()
    {
        if(initDelay>0)
        {
            delay--;
            if (delay < 0)
                delay = initDelay;
        }
    }*/


    /**public void updateAbilityActivations()
    {
        if(activations>0)
        {
            /**if(delay==0)
            {*/
         /**       activations--;
                activateAbility();
            //}
        }

    }*/

    private void activateAbility()
    {
        if (actionList != null)
            for (Action action:actionList)//int i = 0; i < actionList.size(); i++)
            {
                if(action.Active)
                {
                    //action.origin.applyAction(action);
                    //action.activateAction();
                    source.levelState.actionManager.activateAction(action,source);
                }
            }

        /**if (abilityList != null)
            for (int i = 0; i < abilityList.size(); i++) {
                abilityList.get(i).useAbility();
            }*/
    }

    public void useAbility() /**return int : 0 == done , 1 == energy , 2 == cooldown , 3 == charges*/
    {
        if(source.atributes.energy >= Cost)
        {
            if(cooldown==0)
            {
                if(charges>=chargeCost) {

                    //activations++;
                    activateAbility();

                    charges -= chargeCost;

                    source.atributes.energy -= Cost;//update source energy

                    if (initCouldown > 0)
                        cooldown = initCouldown;
                }
                else
                {
                    System.out.println("not enough charges");
                }
            }
            else
            {
                System.out.println("ability not ready");
            }
        }
        else
        {

            System.out.println("not enough energy");
        }

    }


}
