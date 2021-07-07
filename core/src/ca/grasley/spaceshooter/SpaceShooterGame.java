package ca.grasley.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

import ca.grasley.spaceshooter.LodingScreen.BiginDemo;

public class SpaceShooterGame extends Game {

	public BiginDemo game;

	GameScreen gameScreen;

	public static Random random = new Random();


	@Override
	public void create() {
		gameScreen = new GameScreen(game);
		setScreen(gameScreen);
	}


	@Override
	public void dispose() {
		gameScreen.dispose();
	}


	@Override
	public void render() {
		super.render();
	}


	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}
}
