package ca.grasley.spaceshooter.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ca.grasley.spaceshooter.LodingScreen.BiginDemo;


public abstract class AbstractScreen implements Screen {
    public BiginDemo game;
    protected Skin skin;
    protected Stage stage;
    protected TextButton backButton;

    //w,h
    private final float WORLD_WIDTH = 360;
    private final float WORLD_HEIGHT = 640;

    public AbstractScreen() {
    }

    protected Skin getSkin() {
        if (skin == null) {
            skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));
        }
        return skin;
    }

    @Override
    public void show() {
        final MenuScreen menuScreen = new MenuScreen(game);
        stage = new Stage();
        backButton = new TextButton("BACK" , getSkin());

        backButton.setPosition(WORLD_WIDTH / 6.5f, WORLD_HEIGHT / 2 - 200f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(menuScreen);
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public abstract void resize(int width, int height);

    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (skin != null) skin.dispose();
        if (stage != null) stage.dispose();
    }
}
