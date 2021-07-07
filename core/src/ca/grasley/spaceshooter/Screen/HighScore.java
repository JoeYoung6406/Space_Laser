package ca.grasley.spaceshooter.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import ca.grasley.spaceshooter.GameScreen;
import ca.grasley.spaceshooter.LodingScreen.BiginDemo;


public class HighScore extends AbstractScreen {
    public BiginDemo game;
    private Image highScore;
    private final float WORLD_WIDTH = 360f;
    private final float WORLD_HEIGHT = 640f;
    private Label highScoreLabel;
    private Label highScoreLabelShow;
    private Music music;
    private GameManager manager;

    public HighScore() {
    }

    @Override
    public void show() {

        super.show();
        manager = new GameManager();

        highScore = new Image(super.getSkin(), "panel");
        highScore.setSize(WORLD_WIDTH * 9 / 10, WORLD_HEIGHT * 9 / 10);
        highScore.setPosition(18, 30);

        highScoreLabel = new Label("HIGHSCORE :", super.getSkin());
        highScoreLabelShow = new Label("" +manager.getHighScoreString(), super.getSkin());
        highScoreLabel.setSize(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2);
        highScoreLabel.setPosition(WORLD_WIDTH / 2 - 90f, WORLD_HEIGHT / 2 - 50f);
        highScoreLabelShow.setSize(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        highScoreLabelShow.setPosition(WORLD_WIDTH / 2 - 30f, WORLD_HEIGHT / 2 - 100f);


        stage.addActor(highScoreLabelShow);
        stage.addActor(highScoreLabel);
        stage.addActor(highScore);
        stage.addActor(backButton);
        //sounds
        music = Gdx.audio.newMusic(Gdx.files.internal("carbgS.mp3"));
        music.setLooping(true);
        music.setVolume(1.0f);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

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
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }
}
