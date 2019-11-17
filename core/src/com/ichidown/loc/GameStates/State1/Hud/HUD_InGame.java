package com.ichidown.loc.GameStates.State1.Hud;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ichidown.loc.Entity.Unit;
import com.ichidown.loc.GameStates.State1.LevelState;

import java.util.ArrayList;


public class HUD_InGame /** THIS SHOULD NOT DEPEND ON PLAYER AS PARAMETER BUT THE PLAYER PARAMETERS THEMSELVES : SO THAT WHEN PLAYER == NULL , THE VALUES ARE IN STANDARD STATE(0 generally)*/
{

    private TextureAtlas hudAtlas;
    private Unit player;
    private LevelState LvlState;

    private Texture image,healthBar,staminaBar,expBar;
    private Texture mouseCrosshair;

    private Texture healthBarEmptyBots,healthBarBotsBLUE,healthBarBotsRED;
    private TextureRegion TEMPhealthBarBotsBLUE,TEMPhealthBarBotsRED,tempHealthBar,tempStaminaBar,tempExpBar;

    private Vector2 XY;
    private ArrayList<Unit> Players;


    float hudZoom = 100;
    private ArrayList<Unit> units;


    private Vector3 mousePosition= new Vector3();
    private Vector2 mouseResolution= new Vector2();


    private Vector3 imagePosition = new Vector3();
    private Vector3 helthPosition = new Vector3();
    private Vector3 staminPosition= new Vector3();
    private Vector3 lvlPosition   = new Vector3();

    ShapeRenderer shapeRenderer = new ShapeRenderer();
    ShaderProgram shaderProgram ;




    public HUD_InGame(Unit p, LevelState LV,ShaderProgram shaderProgram)
    {
        player = p;
        LvlState=LV;
        LoadResources();
        this.shaderProgram=shaderProgram;
    }

    public void LoadResources()
    {
        //hudAtlas = new TextureAtlas(player.Hud_Bar);

        //player Hud-
        image = new Texture("Hud/hud.png");
        healthBar = new Texture("Hud/health_bar.png");
        staminaBar = new Texture("Hud/stamina_bar.png");
        expBar = new Texture("Hud/exp_bar.png");

        //bots hud bars
        healthBarEmptyBots = new Texture("Hud/boot_healthbar_empty.png");
        healthBarBotsBLUE = new Texture("Hud/boot_healthbar_full_ally.png");
        healthBarBotsRED = new Texture("Hud/boot_healthbar_full_enemy.png");

        //mouse
        mouseCrosshair = new Texture("Hud/Mouse.png");


        tempHealthBar = new TextureRegion(healthBar, 0, 0, 0, 0);
        tempStaminaBar = new TextureRegion(staminaBar, 0, 0, 0, 0);
        tempExpBar = new TextureRegion(expBar, 0, 0, 0, 0);

        TEMPhealthBarBotsBLUE = new TextureRegion(healthBarBotsBLUE, 0, 0, 0, 0);
        TEMPhealthBarBotsRED = new TextureRegion(healthBarBotsRED, 0, 0, 0, 0);



    }


    public void drawUserHud(SpriteBatch g)/** exp bar may not be working : didn't check */
    {
        //Existing bug where : if the player dies hud/debug hud bugs(cause:no player data found/no player found)

        imagePosition.x = Gdx.graphics.getWidth()/3;
        imagePosition.y = Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/20;

        helthPosition.x = Gdx.graphics.getWidth()/2.3f;
        helthPosition.y = Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/12.5f;

        staminPosition.x= Gdx.graphics.getWidth()/2.3f;
        staminPosition.y= Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/15.8f;

        lvlPosition.x   = Gdx.graphics.getWidth()/2.5f;
        lvlPosition.y   = Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/12;

        LvlState.gamePort.unproject(imagePosition);
        LvlState.gamePort.unproject(helthPosition);
        LvlState.gamePort.unproject(staminPosition);
        LvlState.gamePort.unproject(lvlPosition);

        g.draw(image,
                imagePosition.x,
                imagePosition.y,
                image.getWidth()/hudZoom,
                image.getHeight()/hudZoom);

        if (player.atributes.health > 0)
        {
            tempHealthBar.setRegion(0, 0, healthBar.getWidth() * player.atributes.health / player.atributes.maxHealth, healthBar.getHeight());
            g.draw(tempHealthBar,
                    helthPosition.x,
                    helthPosition.y,
                    tempHealthBar.getRegionWidth() / hudZoom,
                    tempHealthBar.getRegionHeight() / hudZoom);
        }

        if (player.atributes.energy > 0)
        {
            tempStaminaBar.setRegion( 0, 0, staminaBar.getWidth() * player.atributes.energy / player.atributes.maxEnergy, staminaBar.getHeight());
            g.draw(tempStaminaBar,
                    staminPosition.x,
                    staminPosition.y,
                    tempStaminaBar.getRegionWidth() / hudZoom,
                    tempStaminaBar.getRegionHeight() / hudZoom);
        }


        if (player.atributes.level == player.atributes.maxLevel)
            tempExpBar.setRegion( 0, 0, expBar.getWidth(), expBar.getHeight());// at lvl max exp bar is full(actual exp bat isnt showing)

        else if ((player.atributes.experience - 1000) / 1000 != player.atributes.level)// to avoid glitch when leveling up , not sure if this is working
            tempExpBar.setRegion(0, 0, expBar.getWidth(), expBar.getHeight()- (expBar.getHeight() * (player.atributes.experience - (1000 * player.atributes.level)) / 1000));//we have a problem when leveling up more than 1 level in an instant


        g.draw(tempExpBar,
                    lvlPosition.x,
                    lvlPosition.y,
                    tempExpBar.getRegionWidth()/hudZoom,
                    tempExpBar.getRegionHeight()/hudZoom);
    }

    public void drawMouse(SpriteBatch g)/**not centered*/
    {
        mousePosition.x = Gdx.input.getX();
        mousePosition.y = Gdx.input.getY();
        LvlState.gamePort.unproject(mousePosition);

        g.draw(mouseCrosshair,
                mousePosition.x ,
                mousePosition.y ,
                mouseCrosshair.getWidth() / hudZoom,
                mouseCrosshair.getHeight() / hudZoom);
    }

    public void drawBotsHud(SpriteBatch g)/**not centered*/
    {

        units = LvlState.unitManager.unitsList;

        for(int i = 0;i< units.size();i++)
        {
            if(units.get(i).Active&&units.get(i)!=LvlState.player && units.get(i).visibleToCam) //do not draw our player life bar or invisible to cam unitsList
            {
                g.draw(healthBarEmptyBots,
                        units.get(i).b2body.getPosition().x - units.get(i).b2body.getFixtureList().get(0).getShape().getRadius(),
                        units.get(i).b2body.getPosition().y + units.get(i).b2body.getFixtureList().get(0).getShape().getRadius(),
                        healthBarEmptyBots.getWidth() / hudZoom,
                        healthBarEmptyBots.getHeight() / hudZoom);

                if (units.get(i).atributes.health > 0)
                {
                    if (units.get(i).atributes.team == player.atributes.team)
                    {
                        // cut the texture before draw
                        TEMPhealthBarBotsBLUE.setRegion(0, 0, healthBarBotsBLUE.getWidth() * units.get(i).atributes.health / units.get(i).atributes.maxHealth, healthBarBotsBLUE.getHeight());

                        g.draw(TEMPhealthBarBotsBLUE,
                                units.get(i).b2body.getPosition().x - units.get(i).b2body.getFixtureList().get(0).getShape().getRadius(),
                                units.get(i).b2body.getPosition().y + units.get(i).b2body.getFixtureList().get(0).getShape().getRadius(),
                                TEMPhealthBarBotsBLUE.getRegionWidth() / hudZoom,
                                TEMPhealthBarBotsBLUE.getRegionHeight() / hudZoom);
                    } else
                    {
                        // cut the texture before draw
                        TEMPhealthBarBotsRED.setRegion(0, 0, healthBarBotsRED.getWidth() * units.get(i).atributes.health / units.get(i).atributes.maxHealth, healthBarBotsRED.getHeight());

                        g.draw(TEMPhealthBarBotsRED,
                                units.get(i).b2body.getPosition().x - units.get(i).b2body.getFixtureList().get(0).getShape().getRadius(),
                                units.get(i).b2body.getPosition().y + units.get(i).b2body.getFixtureList().get(0).getShape().getRadius(),
                                TEMPhealthBarBotsRED.getRegionWidth() / hudZoom,
                                TEMPhealthBarBotsRED.getRegionHeight() / hudZoom);
                    }
                }
            }
        }
    }

    public void drawDebugHud(SpriteBatch sb)
    {
        shapeRenderer.setProjectionMatrix(LvlState.gamecam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        if(player.selectedTarget!=null)
        {
            shapeRenderer.setColor(1, 1, 0, 1);
            shapeRenderer.rect(
                    player.selectedTarget.b2body.getPosition().x-0.5f,
                    player.selectedTarget.b2body.getPosition().y-0.5f,
                    1f,
                    1f);
        }

        shapeRenderer.setColor(1, 0, 0, 1);
        if(player.lockedTarget!=null&&player.lockedTarget.b2body!=null)
        {
            shapeRenderer.rect(
                    player.lockedTarget.b2body.getPosition().x-0.5f,
                    player.lockedTarget.b2body.getPosition().y-0.5f,
                    1f,
                    1f);
        }

        shapeRenderer.end();

    }

    public void draw(SpriteBatch sb)
    {
        sb.begin(); /**the draws in here should be direct : no if statements or aditional operations**/

        //shaderProgram.begin();
        //sb.setShader(shaderProgram);
        drawBotsHud(sb);
        //sb.setShader(null);
        //shaderProgram.end();

            drawUserHud(sb);
            drawMouse(sb);
            drawDebugHud(sb);

        sb.end();


    }


}













