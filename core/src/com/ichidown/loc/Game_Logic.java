/**package com.ichidown.loc;


import com.ichidown.loc.GameStates.State1.LevelState;
 */
/**public class Game_Logic implements Runnable
{
    //game thread
    private Thread thread;
    private boolean running;
    public static int FPS = 120;
    private long targetTime = 1000/FPS ;

    long start;
    long elapsed;
    long wait;

    private Loc_Luncher game;

    public Game_Logic(Loc_Luncher game)
    {
        this.game=game;
        addNotify();
    }

    public void addNotify()// ?
    {
        if(thread == null)
        {
            thread = new Thread(this,"LogicThread");
            thread.start();/**pause this & u pause the game */
            /** need to synchronize threads & prioritise the logic then physics then renderer*/
  /**      }
    }

    @Override
    public void run()
    {
        initialiseGame();

        while(running)
        {
            start = System.nanoTime();

            update();

            elapsed=System.nanoTime()- start;

            wait = targetTime - elapsed / 1000000;//targetTime:mili seconds //elapsed:nano seconds


            if(wait<0)
            {
                wait = 0;//time to wait before start drawing //was 5
            }

            try
            {
                Thread.sleep(wait);
            }catch(Exception e){e.printStackTrace();}
        }
    }
    private void update()
    {
        game.levelState.update(wait);
    }

    private void initialiseGame()
    {
        running =true;
    }
}
*/