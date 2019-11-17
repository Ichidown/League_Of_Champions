package com.ichidown.loc.GameStates.State1.Hud;


public class GuiLogic
{
    //Player score/time Tracking Variables
    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private static Integer score;

    public GuiLogic()
    {
        //define our tracking variables
        worldTimer = 300;
        timeCount = 0;
        score = 0;
    }

    public void update(float dt)
    {
        timeCount += dt;
        if(timeCount >= 1)
        {
            if (worldTimer > 0)
            {
                worldTimer--;
            } else {
                timeUp = true;
            }
            timeCount = 0;
        }
    }


    public static void addScore(int value){
        score += value;
    }

    public boolean isTimeUp() { return timeUp; }

    public Integer getWorldTimer() {
        return worldTimer;
    }

    public static Integer getScore() {
        return score;
    }
}
