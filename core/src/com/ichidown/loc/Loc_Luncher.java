package com.ichidown.loc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ichidown.loc.GameStates.State1.LevelState;

public class Loc_Luncher extends Game
{
	//Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static int V_WIDTH = 1200;
	public static int V_HEIGHT = 700;
	public static final float PPM = 100; // pixels per meter : define the pixel number to define 1 meter

	//Box2D Collision Bits
	/** must be in lvl state */
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short PROJECTILE_BIT = 4;
	public static final short MOB_BIT = 16;
	//public static final short ENEMY_HEAD_BIT = 32;
	//public static final short MARIO_HEAD_BIT = 64;

	public static SpriteBatch gameBatch;
	public static SpriteBatch hudBatch;
	public LevelState levelState;

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around Assetmanager to those the classes that need it.
	We will use it in the static context to save time for now. */
	public static AssetManager manager;

	//public Game_Logic gameLogic;

	@Override
	public void create ()
	{

		gameBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();

		manager = new AssetManager();/***USE IT EVERY WERE : not used with textures .. only sounds **/
			manager.load("audio/music/LoginScreenLoop.ogg", Music.class);
			manager.load("audio/sounds/bump.wav", Sound.class);
			manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.finishLoading();


		levelState = new LevelState(this);
		//gameLogic = new Game_Logic(this);

		setScreen(levelState);

	}


	@Override
	public void dispose()
	{
		super.dispose();
		manager.dispose();
		gameBatch.dispose();
		//hudBatch.dispose();
	}

	@Override
	public void render ()
	{
		super.render();
	}
}
