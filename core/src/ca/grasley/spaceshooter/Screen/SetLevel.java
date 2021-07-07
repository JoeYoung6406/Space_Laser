package ca.grasley.spaceshooter.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;



public class SetLevel extends AbstractScreen {

    private Music music;
    private Image setLevel;
    private final float WORLD_WIDTH = 360;
    private final float WORLD_HEIGHT = 640;
    private CheckBox lowLevel;
    private CheckBox mediumLevel;
    private CheckBox heightLevel;
    private ButtonGroup<CheckBox> checkBoxGroup;



    public SetLevel() {
    }

    @Override
    public void show() {
        super.show();
        //背景
        setLevel = new Image(super.getSkin(), "panel");
        setLevel.setSize(WORLD_WIDTH * 9 / 10, WORLD_HEIGHT * 9 / 10);
        setLevel.setPosition(18, 30);

        lowLevel = new CheckBox("LOW LEVEL", super.getSkin());
        mediumLevel = new CheckBox("MEDIUM LEVEL", super.getSkin());
        heightLevel = new CheckBox("HEIGHT LEVEL", super.getSkin());
        checkBoxGroup = new ButtonGroup<CheckBox>(lowLevel,mediumLevel,heightLevel );

        lowLevel.setPosition(WORLD_WIDTH / 2 - 130f, WORLD_HEIGHT / 2 + 180f);
        mediumLevel.setPosition(WORLD_WIDTH / 2 - 150f, WORLD_HEIGHT / 2 +50f);
        heightLevel.setPosition(WORLD_WIDTH / 2 - 150f, WORLD_HEIGHT / 2 - 80f);

        stage.addActor(setLevel);
        stage.addActor(lowLevel);
        stage.addActor(mediumLevel);
        stage.addActor(heightLevel);
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
