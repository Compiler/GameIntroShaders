package com.intro.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.intro.main.ColorCore;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = true;
		config.vSyncEnabled = true;
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new ColorCore(), config);
	}
}
