package com.ichidown.loc.GameStates.State1.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ichidown.loc.Loc_Luncher;


public class Gui implements Disposable{

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;
    private GuiLogic guiLogic;

    //variables to store Old values
    private static Integer oldScore;
    private Integer oldWorldTimer;


    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;

    //private OrthographicCamera gamecam;
    private OrthographicCamera guiCam;

    public Gui(SpriteBatch sb, GuiLogic guiLogic , OrthographicCamera gamecam)
    {

        // initialize old values
        //this.gamecam=gamecam;
        this.guiLogic=guiLogic;
        oldWorldTimer = this.guiLogic.getWorldTimer();
        oldScore = this.guiLogic.getScore();

        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        guiCam = new OrthographicCamera();
        guiCam.position.z = -100;


        viewport = new FitViewport(
                Loc_Luncher.V_WIDTH / Loc_Luncher.PPM * Gdx.graphics.getPpcX(),
                Loc_Luncher.V_HEIGHT/ Loc_Luncher.PPM * Gdx.graphics.getPpcY()
                );

        stage = new Stage(viewport, sb);






        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", oldWorldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =new Label(String.format("%06d", oldScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("ZED", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        //add our table to the stage
        stage.addActor(table);

    }

    /**public void updateVScreen()
    {
        //viewport = new FitViewport(Loc_Luncher.V_WIDTH, Loc_Luncher.V_HEIGHT, new OrthographicCamera());
        viewport = new FillViewport(Loc_Luncher.V_WIDTH/ Loc_Luncher.PPM,Loc_Luncher.V_HEIGHT/ Loc_Luncher.PPM,guiCam);
        stage.setViewport(viewport);
    }*/

    public void updateGui()
    {
        if(oldWorldTimer != guiLogic.getWorldTimer())
        {
            oldWorldTimer = guiLogic.getWorldTimer();
            countdownLabel.setText(String.format("%03d", oldWorldTimer));
        }

        if(oldScore != guiLogic.getScore())
        {
            oldScore = guiLogic.getScore();
            scoreLabel.setText(String.format("%06d", oldScore));
        }

        //guiCam.position.z = 100;
        //guiCam.update();

        //updateVScreen();
    }

    @Override
    public void dispose() { stage.dispose(); }


}
