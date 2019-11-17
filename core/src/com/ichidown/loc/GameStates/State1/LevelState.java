package com.ichidown.loc.GameStates.State1;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.ichidown.loc.Entity.EntityManagers.ActionManager;
import com.ichidown.loc.Entity.EntityManagers.FxManager;
import com.ichidown.loc.Entity.EntityManagers.SensorManager;
import com.ichidown.loc.Entity.Unit;
import com.ichidown.loc.Premade.Units.Zed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.ichidown.loc.Entity.EntityManagers.ProjectileManager;
import com.ichidown.loc.Entity.EntityManagers.UnitManager;
import com.ichidown.loc.GameStates.State1.Hud.HUD_InGame;
import com.ichidown.loc.Loc_Luncher;
import com.ichidown.loc.Tools.B2WorldCreator;
import com.ichidown.loc.Tools.WorldContactListener;
import com.ichidown.loc.postprocessing.PostProcessor;
import com.ichidown.loc.postprocessing.effects.Bloom;
import com.ichidown.loc.postprocessing.utils.ShaderLoader;

public class LevelState implements Screen
{
    //Reference to our Game, used to set Screens
    private Loc_Luncher game;
    //private TextureAtlas atlas;
    private int zoom = 0;
    private String texturePackPath , levelPath , backGroundMusicPath;
    public int gravity = -10;
    public float simulationStepSize ;

    //basic playscreen variables
    public OrthographicCamera gamecam;
    public float camTween = 0.07f;
    public Viewport gamePort;
    private Viewport StateViewPort;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //private RayHandler rayHandler;
    //private PointLight mainLight;
    //private Filter lightFilter;

    //sprites
    public Unit player;

    private Music music;

    private float selectionPrecision;

    private ShaderProgram shaderprogram;

    private Vector3 mousePosition = new Vector3();





    /********************************************************************
     //set all the dead entities into a cache table : so that we avoid entity creation when not necessary
    *******************************************************************/

    //private Unit FirstPlayer;
    private double minStelectedLength;

    public ActionManager actionManager;

    public SensorManager sensorManager;

    private int screenFocusType;

    public Vector2 cursor_Pos;

    private HUD_InGame hud;

    // entity managers
    public UnitManager unitManager ;
    public ProjectileManager projectileManager;
    public FxManager fxManager;

    private String VERT,FRAG;//shader programs paths
    private int gutterW,gutterH,rhWidth,rhHeight;

    double xDiff,yDiff,tempLength;

    public MapProperties mapProp;
    public int mapLeft,mapRight,mapBottom,mapTop;
    public float cameraHalfWidth,cameraHalfHeight,cameraLeft,cameraRight,cameraBottom,cameraTop;

    private BitmapFont font;




    /************** MEMORY MANAGEMENT *******************************/
    public int statePerEntity = 5;
    public int HitListLimit = 3; // number of units hit
    public int unitLimit = 20;
    public int projectileLimit=500;
    public int projectileGeneratorMaxNumber = 5;
    //public int maxInflictions=5;
    public int actionListLimit=5;
    public int simultaniousActiveActionsLimit=100;
    public int abilityCap=5;
    public int FxLimit = 30;
    public int sensorActionsLimit = 5;
    public int simultaniousActiveSensors=50;


    /***************************************************************/

    private Controller controller;
    private float shiftDistance = 150/Loc_Luncher.PPM;;

    /********POSTPROCESSING************************/
    private PostProcessor postProcessor;


    public LevelState(Loc_Luncher game)//GameStateManager gsm,
    {
        initializing();
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort =  new FillViewport(Loc_Luncher.V_WIDTH/ Loc_Luncher.PPM,Loc_Luncher.V_HEIGHT/ Loc_Luncher.PPM,gamecam);
        maploader = new TmxMapLoader();
        map = maploader.load(levelPath);

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1  / Loc_Luncher.PPM);

        //create our Box2D world, setting no gravityScale in X, -10 gravityScale in Y, and allow bodies to sleep
        world = new World(new Vector2(0, gravity), true);

        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        world.setContactListener(new WorldContactListener(this));

        music = Loc_Luncher.manager.get(backGroundMusicPath, Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();


        screenFocusType = 1;

        controller = new Controller(this);

        /** tileMap.setTween(7); **/

        selectionPrecision=1f;

        // entity managers$
        unitManager = new UnitManager(this);
        projectileManager = new ProjectileManager(this);
        fxManager = new FxManager(this);
        actionManager = new ActionManager(this);
        sensorManager = new SensorManager(this);

        // generate unitsList ..................
        player = unitManager.unitsList.get(unitManager.activateUnit(new Zed(this, 1, true), 3, 3, 1));

        creator = new B2WorldCreator(this);
        creator.generateUnits(this, map, 4);





        /****************Light : SHOULD NOT BE HERE*************/
        /**rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.9f);

        lightFilter = new Filter();
        lightFilter.categoryBits=Loc_Luncher.PROJECTILE_BIT;

        mainLight = new PointLight(rayHandler,360, Color.GRAY,10,0,0);
        mainLight.setContactFilter(lightFilter);

        mainLight.attachToBody(player.b2body);
        mainLight.setSoftnessLength(-0.1f);
        mainLight.setColor(1f, 1f, 1f, 0.6f);*/
        /******************************************************/

        // SHADERS ..............................................................................
        //VERT = Gdx.files.internal("Shaders/vertexShader.glsl").readString();
        //FRAG = Gdx.files.internal("Shaders/fragmentShader.glsl").readString();
        VERT = Gdx.files.internal("Shaders/basicLightVertexShader.glsl").readString();
        FRAG = Gdx.files.internal("Shaders/basicLightFragmentShader.glsl").readString();
        shaderprogram = new ShaderProgram(VERT,FRAG);

        if(!shaderprogram.isCompiled()) {
            Gdx.app.log("Problem loading shader:", shaderprogram.getLog());
        }
        //ShaderProgram.pedantic = false;//so that event when uniforms are missing it dose not crash
        game.gameBatch.setShader(shaderprogram);

        //.......................................................................................

        setMapBoundairies();

        // for text
        font = new BitmapFont();
        font.getData().setScale(0.07f);

        //

        // HUD
        hud = new HUD_InGame(player,this,shaderprogram);




        //PostProcessing
        ShaderLoader.BasePath = "Shaders/";
        postProcessor = new PostProcessor(false, false, false);//last for isDesktop :  32 bits-per-pixel precision only on the desktop
        Bloom bloom = new Bloom( (int)(Gdx.graphics.getWidth() * 0.50f), (int)(Gdx.graphics.getHeight() * 0.50f) );
        //Vignette vignette = new Vignette( (int)(Gdx.graphics.getWidth() * 0.25f), (int)(Gdx.graphics.getHeight() * 0.25f) ,false);
        //CrtMonitor crtMonitor = new CrtMonitor (Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true, true, CrtScreen.RgbMode.RgbShift , 0);

        postProcessor.addEffect( bloom );
        //postProcessor.addEffect( vignette );
        //postProcessor.addEffect(crtMonitor);




    }

    public void initializing()
    {
        texturePackPath = "Mario_and_Enemies.pack";
        levelPath = "level3.tmx";
        gravity = -10;
        simulationStepSize = 1 / 60f; //takes 1 step in the physics simulation(60 times per second)
        backGroundMusicPath = "audio/music/LoginScreenLoop.ogg";
    }


    @Override
    public void show()
    {


    }


    public void update(float dt)
    {
        //handle user input first
        handleInput();

        //physics update
        world.step(simulationStepSize, 6, 2);

        //rayHandler.update();

        //update unitsList
        unitManager.updateUnits(dt);

        projectileManager.updateProjectileManager(dt);

        fxManager.updateFxs(dt);

        sensorManager.updateSensorManager();

        actionManager.updateActionManager();

        SetTarget();


        updateMousePosition();

        //update our gamecam with correct coordinates after changes
        updateScreenFocus();
        updateCamPositions();
        //correctScreenFocus();

        gamecam.update();


        mapRenderer.setView(gamecam);

        //rayHandler.setCombinedMatrix(gamecam);
    }


    @Override
    public void render(float delta)
    {
        postProcessor.capture();
        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*************************************************************************/
        shaderprogram.begin();
        shaderprogram.setUniformf("u_lightPos", new Vector2(player.b2body.getPosition().x, player.b2body.getPosition().y));
        shaderprogram.end();


        mapRenderer.getBatch().setShader(shaderprogram);
        game.gameBatch.setShader(shaderprogram);

        /*************************************************************************/

        mapRenderer.render();

        game.gameBatch.setProjectionMatrix(gamecam.combined);


        game.gameBatch.begin();
        unitManager.drawUnits(game.gameBatch, shaderprogram);
            projectileManager.drawProjectiles(game.gameBatch);
        fxManager.drawFxs(game.gameBatch);
        font.draw(game.gameBatch,
                String.valueOf(Gdx.graphics.getFramesPerSecond()),
                (float) (gamecam.position.x - gamecam.viewportWidth * .3),
                (float) (gamecam.position.y + gamecam.viewportHeight * .3));
        game.gameBatch.end();




        //light system rendering
        //rayHandler.render();

        //mapRenderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);


        game.gameBatch.setShader(null);

        //draw hud
        hud.draw(game.gameBatch);

        //controlers
        controller.draw();
        postProcessor.render();
    }

    @Override
    public void resize(int width, int height)
    {
        //updated our game viewport
        gamePort.update(width, height);

        updateCamDimentions();

        controller.resize(width, height);

        // to fix the issue : using box2dLight changes the viewPort to fitViewPort
        //gutterW = gamePort.getLeftGutterWidth();
        //gutterH = gamePort.getTopGutterHeight();
        //rhWidth = width - (2 * gutterW);
        //rhHeight = height - (2 * gutterH);

        //rayHandler.useCustomViewport(gutterW, gutterH, rhWidth, rhHeight);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume()
    {
        postProcessor.rebind();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        //dispose of all our opened resources
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2dr.dispose();
        //gui.dispose();
        //rayHandler.dispose();
        //hud.dispose();
        postProcessor.dispose();
    }

    public void updateMousePosition()
    {
        mousePosition.x=Gdx.input.getX();
        mousePosition.y=Gdx.input.getY();

        mousePosition = gamecam.unproject(mousePosition);
    }

    public void updateScreenFocus()
    {
        //attach our gamecam to our unitsList.x coordinate

        switch (screenFocusType)
        {
            case 0://player position + face left/right oriented
                if (player.facingRight)
                    moveCamTo(player.b2body.getPosition().x + shiftDistance,player.b2body.getPosition().y);
                else
                    moveCamTo(player.b2body.getPosition().x - shiftDistance,player.b2body.getPosition().y);
                break;

            case 1 ://player position
                    moveCamTo(player.b2body.getPosition().x,player.b2body.getPosition().y);
                break;

            case 2 :// player position + mouse position
                moveCamTo(player.b2body.getPosition().x-(player.b2body.getPosition().x- mousePosition.x)/3
                        ,player.b2body.getPosition().y-(player.b2body.getPosition().y- mousePosition.y)/3);

                break;

           /* case 3 ://free navigation*/




        }
    }

    private void moveCamTo(float X,float Y)
    {
        gamecam.position.x += (-gamecam.position.x+X) * camTween;
        gamecam.position.y += (-gamecam.position.y+Y) * camTween;
    }

    private void correctScreenFocus()
    {

        // Horizontal axis
        if(mapRight < gamecam.viewportWidth)
        {
            gamecam.position.x = mapRight / 2;
        }
        else if(cameraLeft <= mapLeft)
        {
            gamecam.position.x = mapLeft + cameraHalfWidth;
        }
        else if(cameraRight >= mapRight)
        {
            gamecam.position.x = mapRight - cameraHalfWidth;
        }

        // Vertical axis
        if(mapTop < gamecam.viewportHeight)
        {
            gamecam.position.y = mapTop / 2;
        }
        else if(cameraBottom <= mapBottom)
        {
            gamecam.position.y = mapBottom + cameraHalfHeight;
        }
        else if(cameraTop >= mapTop)
        {
            gamecam.position.y = mapTop - cameraHalfHeight;
        }
    }

    private void updateCamDimentions()
    {
        // The camera dimensions, halved
        cameraHalfWidth = gamecam.viewportWidth * .5f;
        cameraHalfHeight = gamecam.viewportHeight * .5f;
    }

    private void updateCamPositions()
    {
        cameraLeft = gamecam.position.x - cameraHalfWidth;
        cameraRight = gamecam.position.x + cameraHalfWidth;
        cameraBottom = gamecam.position.y - cameraHalfHeight;
        cameraTop = gamecam.position.y + cameraHalfHeight;
    }

    private void setMapBoundairies()
    {
        mapProp = map.getProperties();

        // The left boundary of the map (x)
        mapLeft = 0;
        // The right boundary of the map (x + width)
        mapRight = (int) (mapProp.get("width", Integer.class)*mapProp.get("tilewidth", Integer.class)/Loc_Luncher.PPM);
        // The bottom boundary of the map (y)
        mapBottom = 0;
        // The top boundary of the map (y + height)
        mapTop = (int) (mapProp.get("height", Integer.class)*mapProp.get("tileheight", Integer.class)/Loc_Luncher.PPM);
    }

    public void SetTarget()//get the selected target
    {

        minStelectedLength=selectionPrecision;

        if(player!=null)
        {
            player.selectedTarget=null;

            for (int i = 0; i < unitManager.unitsList.size(); i++)/**replace this with a box2D sensor for more optimisations & controls */
            {
                if(unitManager.unitsList.get(i).Active)
                {
                    xDiff = (mousePosition.x - unitManager.unitsList.get(i).b2body.getPosition().x);
                    yDiff = (mousePosition.y - unitManager.unitsList.get(i).b2body.getPosition().y);
                    tempLength = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                    if (minStelectedLength > tempLength) {
                        player.selectedTarget = unitManager.unitsList.get(i);
                        minStelectedLength = tempLength;
                    }
                }
            }
        }
    }

    // CONTROLLS ........................................................................

    public void handleInput()
    {
        /** KEY **/
        if(Gdx.app.getType()== Application.ApplicationType.Desktop)
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z))
                player.abilities.get(3).useAbility();
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                player.abilities.get(1).useAbility();
            if (Gdx.input.isKeyPressed(Input.Keys.Q))
                player.abilities.get(2).useAbility();
        }


        /** TOUCH **/
        if(controller.rightPressed)
            player.abilities.get(1).useAbility();
        else if(controller.leftPressed)
            player.abilities.get(2).useAbility();

        if(controller.upPressed)
        {
            controller.upPressed=false;
            player.abilities.get(3).useAbility();
        }

        if(Gdx.input.justTouched() && !controller.upPressed && !controller.leftPressed && !controller.rightPressed)
        {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
                player.abilities.get(0).useAbility();
            if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
                player.lockedTarget=player.selectedTarget;
        }



        /** FOR PHONE **/
        /**if(Gdx.input.isTouched())
        {
            if(Gdx.input.isTouched(1))
            {
                if (Gdx.input.justTouched())
                    player.abilities.get(3).useAbility();
            }
            else if(Gdx.input.getY()>Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4)
            {
                if (Gdx.input.getX()>Gdx.graphics.getWidth()/2)
                    player.abilities.get(1).useAbility();
                else
                    player.abilities.get(2).useAbility();
            }
            else if(Gdx.input.justTouched())
            {
                player.lockedTarget=player.selectedTarget;
                player.abilities.get(0).useAbility();
            }
        }*/

    }
}
