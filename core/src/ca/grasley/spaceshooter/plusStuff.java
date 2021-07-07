package ca.grasley.spaceshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class plusStuff{
    //position and dimensions
    Rectangle boundingBox;

    //laser physical characteristics
    float movementSpeed; //world units per second

    //graphics
    Texture stuffTexture;

    public plusStuff(float xCentre, float yCentre, float width, float height, float movementSpeed, Texture stuffTexture) {
        this.boundingBox = new Rectangle(xCentre - width / 2, yCentre , width, height);
        this.movementSpeed = movementSpeed;
        this.stuffTexture = stuffTexture;
        this.boundingBox = new Rectangle(xCentre - width / 2, yCentre - height / 2, width, height);
    }
    public void draw(Batch batch) {
        batch.draw(stuffTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
