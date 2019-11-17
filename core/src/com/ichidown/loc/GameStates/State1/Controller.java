package com.ichidown.loc.GameStates.State1;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ichidown.loc.Loc_Luncher;

public class Controller
{
    public Viewport viewport;
    public Stage stage;
    public boolean upPressed,leftPressed,rightPressed;
    Image upImg;//,downImg,leftImg,rightImg;


    private Skin touchpadSkin;
    private OrthographicCamera camera;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Touchpad touchpad;

    private LevelState levelState;


    public Controller(LevelState levelState)
    {
        this.levelState=levelState;

        //Create a touchpad skin
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("Controls/Bg.png"));
        touchpadSkin.add("touchKnob", new Texture("Controls/Tip.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");


        //Create new TouchPad with the created style
        touchpad = new Touchpad(10,touchpadStyle);
        touchpad.setSize(200, 200);
        touchpad.setPosition(150,150,Align.center);

        viewport = new FillViewport(Loc_Luncher.V_WIDTH,Loc_Luncher.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,Loc_Luncher.gameBatch);
        stage.addActor(touchpad);

        Gdx.input.setInputProcessor(stage);
        setTouchpadListener();
        //stage.setDebugAll(true);





        /*
        viewport = new FillViewport(Loc_Luncher.V_WIDTH,Loc_Luncher.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,Loc_Luncher.gameBatch);
        */

        //Vector3 tabPosition = new Vector3();
        //viewport.unproject(tabPosition);

        upImg = new Image(new Texture("Controls/up.png"));
        /**downImg = new Image(new Texture("Controls/down.png"));
        leftImg = new Image(new Texture("Controls/left.png"));
        rightImg = new Image(new Texture("Controls/right.png"));*/

        upImg.setSize(150,150);
        /*downImg.setSize(80,80);
        leftImg.setSize(80, 80);
        rightImg.setSize(80, 80);*/

        //Gdx.input.setInputProcessor(stage);//set the stage as an input listener
        setButtonListeners();

        Table table = new Table();

        //table.add();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        /*table.add();
        table.row();
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.row();
        table.add();
        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        table.add();*/

        table.right().bottom();
        table.pad(150, 150, 100, 100);
        table.setPosition(1200, 0);
        //table.setPosition(tabPosition.x, tabPosition.y);
        //System.out.println(tabPosition);


        stage.addActor(table);


        //stage.setDebugAll(true);

    }

    public void draw()
    {
        stage.draw();
    }


    public void resize(int width,int height)
    {
        viewport.update(width, height);
    }

    public void setTouchpadListener()
    {
        touchpad.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                if(touchpad.getKnobPercentX()>0)
                    rightPressed=true;
                else if(touchpad.getKnobPercentX()<0)
                    leftPressed=true;

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                rightPressed=false;
                leftPressed=false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer)
            {
                if(touchpad.getKnobPercentX()>0)
                {
                    rightPressed=true;
                    leftPressed=false;
                }
                else if(touchpad.getKnobPercentX()<0)
                {
                    rightPressed=false;
                    leftPressed=true;
                }
            }
        });
    }


    public void setButtonListeners()
    {
        upImg.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

        });

        /**
        downImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });


        leftImg.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                leftPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                leftPressed=false;
            }
        });


        rightImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });*/

    }


}
