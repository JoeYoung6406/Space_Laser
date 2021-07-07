package ca.grasley.spaceshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosion {

    private Animation<TextureRegion> explosionAnimation;
    private float explosionTimer;

    private Rectangle boundingBox;

    Explosion(Texture texture, Rectangle boundingBox, float totalAnimationTimer){
        this.boundingBox = boundingBox;
        //將圖片以二維陣列set到一維陣列裡 因為圖片是4*4
        TextureRegion[][] textureRegions2D = TextureRegion.split(texture, 64,64);

        TextureRegion[] textureRegions1D = new TextureRegion[16];

        int index = 0;
        for (int i= 0; i<4; i++){
            for (int j =0; j<4 ;j++){
                textureRegions1D[index] = textureRegions2D[i][j];
                index++;
            }
        }
        //每一幀的輸出
        explosionAnimation = new Animation<TextureRegion>(totalAnimationTimer/16, textureRegions1D);
        explosionTimer = 0;
    }

    public void update(float deltaTime){
        explosionTimer += deltaTime;
    }
    public void draw(SpriteBatch batch){
        //繪製16禎圖片
        batch.draw(explosionAnimation.getKeyFrame(explosionTimer),boundingBox.x, boundingBox.y,boundingBox.width,boundingBox.height);
    }

    public boolean isFinished(){
        //16禎都輸出了嗎
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }
}
