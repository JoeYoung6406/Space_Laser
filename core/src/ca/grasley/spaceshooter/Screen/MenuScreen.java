package ca.grasley.spaceshooter.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ca.grasley.spaceshooter.GameScreen;
import ca.grasley.spaceshooter.LodingScreen.BiginDemo;


public class MenuScreen implements Screen {
    public BiginDemo game;
    private Skin skin;
    private Stage stage;
    private GameScreen gameScreen = new GameScreen(game);
    private SetLevel setLevel = new SetLevel();
    private HighScore highScore = new HighScore();

    private Image bg;
    private TextButton startButton;
    private TextButton setLevelButton;
    private TextButton highScoreButton;
    private TextButton quitButton;
    private Label labelTitleUp;
    private Label labelTitleDown;


    private final float WORLD_WIDTH = 360;
    private final float WORLD_HEIGHT = 640;
    private Music music;

    public MenuScreen(BiginDemo game) {
        this.game=game;
    }
    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));
        stage = new Stage();

        //設置按鈕與背景外觀
        bg = new Image(skin, "panel");
        labelTitleUp = new Label("SPACE",skin);
        labelTitleDown = new Label("LASER",skin);
        startButton = new TextButton("PLAY", skin);
        setLevelButton = new TextButton("SET LEVEL", skin);
        highScoreButton = new TextButton("HIGH SCORE", skin);
        quitButton = new TextButton("QUIT", skin);

        //設定各種比例
        bg.setSize(WORLD_WIDTH*9/10, WORLD_HEIGHT*9/10);
        bg.setPosition(18,30);

        labelTitleUp.setPosition(WORLD_WIDTH/6.5f+75f, WORLD_HEIGHT/2+225f);
        labelTitleDown.setPosition(WORLD_WIDTH/6.5f+75f, WORLD_HEIGHT/2+175f);
        startButton.setPosition(WORLD_WIDTH/6.5f, WORLD_HEIGHT/2+60);
        setLevelButton.setPosition(WORLD_WIDTH/6.5f, WORLD_HEIGHT/2-50f);
        highScoreButton.setPosition(WORLD_WIDTH/6.5f, WORLD_HEIGHT/2-160f);
        quitButton.setPosition(WORLD_WIDTH/6.5f, WORLD_HEIGHT/2-270f);


        //跳轉螢幕畫面
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                music.dispose();
                ((Game)Gdx.app.getApplicationListener()).setScreen(gameScreen);
            }
        });
        setLevelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(setLevel);
            }
        });
        highScoreButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(highScore);
            }
        });
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(labelTitleUp);
        stage.addActor(labelTitleDown);
        stage.addActor(bg);
        stage.addActor(startButton);
        stage.addActor(setLevelButton);
        stage.addActor(highScoreButton);
        stage.addActor(quitButton);

        Gdx.input.setInputProcessor(stage);
        //sounds
        music = Gdx.audio.newMusic(Gdx.files.internal("bombing.mp3"));
        music.setLooping(true);
        music.setVolume(1.0f);
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0x64/255.0f,0x95/255.0f,0xed/255.0f,0xff/255.0f);
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
        skin.dispose();
        stage.dispose();
        music.dispose();
    }
}
