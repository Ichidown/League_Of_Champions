package com.ichidown.loc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ichidown.loc.Loc_Luncher;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=700;
		config.height=420;
		new LwjglApplication(new Loc_Luncher(), config);
	}
}
