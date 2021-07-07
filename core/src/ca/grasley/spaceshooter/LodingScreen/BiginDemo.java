package ca.grasley.spaceshooter.LodingScreen;

import com.badlogic.gdx.Game;

import ca.grasley.spaceshooter.Screen.MenuScreen;
import ca.grasley.spaceshooter.Screen.SplashScreen;

public class BiginDemo extends Game {
    ca.grasley.spaceshooter.Screen.SplashScreen splashScreen;
//    MenuScreen menuScreen = new MenuScreen();

    @Override
    public void create() {
        splashScreen = new SplashScreen();
        setScreen(splashScreen);
    }
}
