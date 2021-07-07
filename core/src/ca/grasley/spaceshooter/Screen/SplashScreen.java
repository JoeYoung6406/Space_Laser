package ca.grasley.spaceshooter.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import ca.grasley.spaceshooter.LodingScreen.BiginDemo;


public class SplashScreen implements Screen {
    public BiginDemo game;
    private final float WORLD_WIDTH = 360;
    private final float WORLD_HEIGHT = 640;
    private Image splashImage = new Image(new Texture(Gdx.files.internal("spaceShipIcon.png")));
    private Stage stage = new Stage();
    MenuScreen menuScreen = new MenuScreen(game);
    private Music music;

    public SplashScreen() {
    }

    @Override
    public void show() {


        splashImage.setSize(WORLD_WIDTH*1/3 ,WORLD_HEIGHT*1/3);
        splashImage.setPosition(WORLD_WIDTH/2 - splashImage.getWidth()/2,WORLD_HEIGHT/2 - splashImage.getHeight()/2);
        stage.addActor(splashImage);
        splashImage.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.fadeIn(1.5f),
                Actions.delay(1),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        music.dispose();
                        ((Game) Gdx.app.getApplicationListener()).setScreen(menuScreen);
                    }
                })));
        music = Gdx.audio.newMusic(Gdx.files.internal("laser2.mp3"));
        music.setVolume(2.0f);
        music.play();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void hide() {
        dispose();
    }
    @Override
    public void dispose() {
        stage.dispose();

    }
}
