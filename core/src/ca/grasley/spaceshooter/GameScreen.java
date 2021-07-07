package ca.grasley.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

import ca.grasley.spaceshooter.LodingScreen.BiginDemo;
import ca.grasley.spaceshooter.Screen.GameManager;
import ca.grasley.spaceshooter.Screen.MenuScreen;

public class GameScreen implements Screen {
    private GameManager manager;
//    public int hightScore;
    //screen
    private Camera camera;
    private Viewport viewport;


    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Texture explosionTexture;

    private TextureRegion[] backgrounds;
    private float backgroundHeight; //height of background in World units


    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion,
            enemyShipTextureRegion, enemyShieldTextureRegion,
            playerLaserTextureRegion, enemyLaserTextureRegion;

    //timing
    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;
    private float timeBetweenEnemySpawn = 3f;
    private float enemySpawnTimer = 0;


    //world parameters
    private final float WORLD_WIDTH = 72;
    private final float WORLD_HEIGHT = 128;
    private final float TOUCH_MOVEMENT_THRESHOLD = 0.5f;

    //stuff
    int i = 0;
    int x = 0;
    int y = 0;
    int k = 0;
    int q = 0;
    int z = 0;
    float randomStuffTime;
    private Texture stuffTextureEnergy;
    private Texture stuffTextureHeart;
    private Texture stuffTextureSkeleton;


    //game objects
    private PlayerShip playerShip;
    private EnemyShip enemyShip;
    private LinkedList<EnemyShip> enemyShipsList;
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;
    private LinkedList<Explosion> explosionList;
    private plusStuff energy;
    private plusStuff heart;
    private plusStuff skeleton;
    private Sound bomb;

    private int score;


    //sounds
    Music music;
    Sound laserSound;

    //顯示行
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;

    public BiginDemo game;

    public GameScreen(BiginDemo game) {
        this.game = game;
    }


    public void prepareHud() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        font = fontGenerator.generateFont(fontParameter);
        font.getData().setScale(0.08f);
        float hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
    }

    @Override
    public void show() {
        manager = new GameManager();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up the texture atlas 將所有圖片打包成TextureAtlas
        textureAtlas = new TextureAtlas("images.atlas");

        //setting up the background 四個背景設置為陣列
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundHeight = WORLD_HEIGHT * 2; //背景高度全部*2
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 5;

        //initialize texture regions
        playerShipTextureRegion = textureAtlas.findRegion("playerShip2_blue");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyRed3");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(false, true); //敵方盾牌轉向

        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed03");

        explosionTexture = new Texture("exp2_0.png");
        stuffTextureEnergy = new Texture("battery.png");
        stuffTextureHeart = new Texture("heart.png");
        stuffTextureSkeleton = new Texture("skeleton.png");


        //set up game objects
        playerShip = new PlayerShip(WORLD_WIDTH / 2, WORLD_HEIGHT / 4,
                10, 10,
                48, 10,
                0.4f, 4, 48, 0.5f,
                playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion);
//        enemyShip = new EnemyShip(SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - 10) + 5,
//                WORLD_HEIGHT - 5,
//                10, 10,
//                48, 5,
//                0.3f, 5, 50, 0.8f,
//                enemyShipTextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion);

        energy = new plusStuff(WORLD_WIDTH / 2, WORLD_HEIGHT, 5, 5, 30, stuffTextureEnergy);
        heart = new plusStuff(WORLD_WIDTH / 2, WORLD_HEIGHT, 5, 5, 30, stuffTextureHeart);
        skeleton = new plusStuff(WORLD_WIDTH / 2, WORLD_HEIGHT, 5, 5, 30, stuffTextureSkeleton);

        enemyShipsList = new LinkedList<>();
        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        explosionList = new LinkedList<>();
        batch = new SpriteBatch();
        prepareHud();
        //sounds
        music = Gdx.audio.newMusic(Gdx.files.internal("spacemusic.mp3"));
        music.setLooping(true);
        music.setVolume(1.0f);
        music.play();
        bomb = Gdx.audio.newSound(Gdx.files.internal("bomb.mp3"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("laser2.mp3"));
        long Sound = laserSound.play(0.5f);
        laserSound.setLooping(Sound, true);
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();

        //scrolling background 背景滾動圖
        renderBackground(deltaTime);

        detectInput(deltaTime);
        //船狀態update
        playerShip.update(deltaTime);
        manager.updateHighScore(score);
        spawnEnemyShips(deltaTime);


        //player ship 玩家船
        playerShip.draw(batch);
        renderEnemyShip(deltaTime);
        //stuff 物資
        renderStuffEnergy(deltaTime);
        renderStuffHeart(deltaTime);
        renderStuffSkeleton(deltaTime);

        //lasers 雷射
        renderLasers(deltaTime);

        //detect collisions 偵測是否碰撞
        detectCollisions();

        //explosions 產生爆炸
        renderExplosion(deltaTime);

        //產生字幕
        updateAndRenderHUD();

        batch.end();
    }

    private void renderEnemyShip(float deltaTime) {
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipsList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            moveEnemy(enemyShip, deltaTime);
            enemyShip.update(deltaTime);
            //enemy ships 敵人船
            enemyShip.draw(batch);
        }
    }

    private void renderStuffHeart(float deltaTime) {
        y++;
        if (y > 400) {//間隔下一次施放時間
            randomStuffTime += deltaTime;
            if (randomStuffTime > deltaTime)
                batch.draw(stuffTextureHeart, heart.boundingBox.x, heart.boundingBox.y, 5, 5);

            heart.boundingBox.y -= deltaTime * backgroundMaxScrollingSpeed *3/2;
        }
        if (k == 0) { //x座標隨機
            k++;
            heart.boundingBox.x = (float) Math.random() * WORLD_WIDTH - 1.0f;
        }

        if (heart.boundingBox.y >= 800 || heart.boundingBox.y < 0) {
            y = 0;
            k = 0;
            heart.boundingBox.y = WORLD_HEIGHT;
        }
        if (playerShip.intersects(heart.boundingBox)) {
            heart.boundingBox.y = 1000;
            playerShip.lives++;
            y = 0;
        }
    }

    private void renderStuffSkeleton(float deltaTime) {
        z++;
        if (z > 800) {//間隔下一次施放時間
            randomStuffTime += deltaTime;
            if (randomStuffTime > deltaTime)
                batch.draw(stuffTextureSkeleton, skeleton.boundingBox.x, skeleton.boundingBox.y, 10, 10);

            skeleton.boundingBox.y -= deltaTime * backgroundMaxScrollingSpeed ;
        }
        if (z > 200) { //能力時長
//            playerShip.timeBetweenShots = 0.5f;
            playerShip.movementSpeed = 48;
        }
        if (q == 0) { //x座標隨機
            q++;
            skeleton.boundingBox.x = (float) Math.random() * WORLD_WIDTH - 1.0f;
        }

        if (skeleton.boundingBox.y >= 800 || skeleton.boundingBox.y < 0) {
            z = 0;
            q = 0;
            skeleton.boundingBox.y = WORLD_HEIGHT;
        }
        if (playerShip.intersects(skeleton.boundingBox)) {
            skeleton.boundingBox.y = 1000;
//            playerShip.timeBetweenShots = 2.0f;
            playerShip.movementSpeed = 10;
            z = 0;
        }
    }

    private void renderStuffEnergy(float deltaTime) {
        x++;
        if (x > 800) {//間隔下一次施放時間
            randomStuffTime += deltaTime;
            if (randomStuffTime > deltaTime)
                batch.draw(stuffTextureEnergy, energy.boundingBox.x, energy.boundingBox.y, 5, 5);

            energy.boundingBox.y -= deltaTime * backgroundMaxScrollingSpeed * 2;
        }
        if (x > 300) { //能力時長
            playerShip.laserHeight = 4.0f;
            playerShip.timeBetweenShots = 0.5f;
            playerShip.laserMovementSpeed = 48;
            playerShip.laserWidth = 0.4f;
        }
        if (i == 0) { //x座標隨機
            i++;
            energy.boundingBox.x = (float) Math.random() * WORLD_WIDTH - 1.0f;
        }

        if (energy.boundingBox.y >= 800 || energy.boundingBox.y < 0) {
            x = 0;
            i = 0;
            energy.boundingBox.y = WORLD_HEIGHT;
        }
        if (playerShip.intersects(energy.boundingBox)) {
            energy.boundingBox.y = 1000;
            playerShip.laserHeight = 6.0f;
            playerShip.timeBetweenShots = 0.1f;
            playerShip.laserMovementSpeed = 100;
            playerShip.laserWidth = 4.0f;
            x = 0;
        }
    }

    private void updateAndRenderHUD() {
        //第一行字
        font.draw(batch, "score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Shield", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "Lives", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //第二行字
        font.draw(batch, String.format(Locale.getDefault(), "%04d", score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.lives), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
    }

    private void spawnEnemyShips(float deltaTime) {

        enemySpawnTimer += deltaTime;

        if (enemySpawnTimer > timeBetweenEnemySpawn) {
            enemyShipsList.add(new EnemyShip(SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - 10) + 5,
                    WORLD_HEIGHT - 5,
                    10, 10,
                    48, 5,
                    0.3f, 5, 50, 0.8f,
                    enemyShipTextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion));
            enemySpawnTimer -= timeBetweenEnemySpawn;

        }
    }

    private void moveEnemy(EnemyShip enemyShip, float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyShip.boundingBox.x;
        downLimit = (float) WORLD_HEIGHT / 2 - enemyShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
        upLimit = WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

        // 斜邊長除以斜邊長速度再乘以x邊長=x邊長速度, 與y邊長速度
        float xMove = enemyShip.getDirectionVector().x * enemyShip.movementSpeed * deltaTime;
        float yMove = enemyShip.getDirectionVector().y * enemyShip.movementSpeed * deltaTime;

        //判斷是否超過邊界
        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove, downLimit);

        enemyShip.translate(xMove, yMove);
    }

    private void detectInput(float deltaTime) {
        //設定邊界條件
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = (float) WORLD_HEIGHT / 2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) { //判斷是否出界
            //判斷何者較小，防止直接超出邊界
            playerShip.translate(Math.min(playerShip.movementSpeed * deltaTime, rightLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            playerShip.translate(Math.max(-playerShip.movementSpeed * deltaTime, leftLimit), 0f);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            playerShip.translate(0f, Math.min(playerShip.movementSpeed * deltaTime, upLimit));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            playerShip.translate(0f, Math.max(-playerShip.movementSpeed * deltaTime, downLimit));
        }

        //觸控Input
        if (Gdx.input.isTouched()) {
            //判斷觸控位置
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            //位置是Pixels，需轉換為WORLD_UNIT
            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint); //轉換為world_unit

            //將玩家的中心值轉換
            Vector2 playerShipCentre = new Vector2(playerShip.boundingBox.x + playerShip.boundingBox.width / 2,
                    playerShip.boundingBox.y + playerShip.boundingBox.height / 2);

            float touchDistance = touchPoint.dst(playerShipCentre);//觸碰點跟玩家中心的距離

            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD) { //防止抖動
                //計算兩點之間的平行距離
                float xTouchDifference = touchPoint.x - playerShipCentre.x;
                float yTouchDifference = touchPoint.y - playerShipCentre.y;

                // 斜邊長除以斜邊長速度再乘以x邊長=x邊長速度, 與y邊長速度
                float xMove = xTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;
                float yMove = yTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;

                //判斷是否超過邊界
                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);

                playerShip.translate(xMove, yMove);
            }
        }
    }

    private void renderExplosion(float deltaTime) {
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if (explosion.isFinished()) {
                explosionListIterator.remove();
            } else {
                explosion.draw(batch);
            }
        }
    }

    public void detectCollisions() {
        //for each player laser, check whether it intersects an
        ListIterator<Laser> playerLaserListIterator = playerLaserList.listIterator();
        while (playerLaserListIterator.hasNext()) {
            Laser laser = playerLaserListIterator.next();
            ListIterator<EnemyShip> enemyShipListIterator = enemyShipsList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                EnemyShip enemyShip = enemyShipListIterator.next();
                //檢測船跟雷射的Rectangle有無overlap
                if (enemyShip.intersects(laser.boundingBox)) {
                    if (enemyShip.hitAndCheckDestroyed(laser)) {
                        enemyShipListIterator.remove();
                        explosionList.add(
                                new Explosion(
                                        explosionTexture, new Rectangle(enemyShip.boundingBox), 0.7f));
                        score += 100;
//                        if (score > hightScore) {
//                            hightScore = score;
//                        }
                        bomb.play();
                    }
                    playerLaserListIterator.remove();
                    break;
                }
            }
        }

        ListIterator<Laser> enemyLaserListIterator = enemyLaserList.listIterator();
        while (enemyLaserListIterator.hasNext()) {
            Laser laser = enemyLaserListIterator.next();
            if (playerShip.intersects(laser.boundingBox)) {
                if (playerShip.hitAndCheckDestroyed(laser)) {
                    explosionList.add(
                            new Explosion(
                                    explosionTexture, new Rectangle(playerShip.boundingBox), 2.0f));
                    playerShip.shield = 3;
                    playerShip.lives--;
                    if (playerShip.lives == 0) {
//                        hightScore = score;
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(game));
                        music.dispose();
                        laserSound.dispose();
                    }
                }
                enemyLaserListIterator.remove();
                bomb.play();
            }
        }
    }

    private void renderLasers(float deltaTime) {
        //create new lasers
        //player lasers
        if (playerShip.canFireLaser()) {
            Laser[] lasers = playerShip.fireLasers();
            for (Laser laser : lasers) {
                playerLaserList.add(laser);
            }
        }
        //enemy lasers
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipsList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()) {
                Laser[] lasers = enemyShip.fireLasers();
                for (Laser laser : lasers) {
                    enemyLaserList.add(laser);
                }
            }
        }
        //draw lasers
        //remove old lasers 迭代器將雷射一一射出
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);

            laser.boundingBox.y += laser.movementSpeed * deltaTime;
            if (laser.boundingBox.y > WORLD_HEIGHT) {
                iterator.remove();
            }
        }
        iterator = enemyLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.movementSpeed * deltaTime;
            if (laser.boundingBox.y < 0) {
                iterator.remove();
            }
        }
    }

    private void renderBackground(float deltaTime) {
        //update position of background images
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        //draw each background layer
        for (int i = 0; i < 4; i++) {
            if (backgroundOffsets[i] > WORLD_HEIGHT) {
                backgroundOffsets[i] = 0;
            }
            batch.draw(backgrounds[i], 0, -backgroundOffsets[i],
                    WORLD_WIDTH, backgroundHeight);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        batch.dispose();
        bomb.dispose();
    }
}
