package com.testgame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


import ca.grasley.spaceshooter.LodingScreen.BiginDemo;
import ca.grasley.spaceshooter.SpaceShooterGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=360;
		config.height=640;

		config.addIcon("spaceShipIcon.png", Files.FileType.Internal);
		new LwjglApplication(new BiginDemo(), config);
	}
}
