package com.draketilt.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by davidchang on 6/18/16.
 */
public class World {
    public final Drake drake = new Drake(0, 0);
    public Rectangle drakeContainer;
    public Array<Rectangle> raindrops;

    public World(){
        raindrops = new Array<Rectangle>();
        drakeContainer = new Rectangle();
        drakeContainer.x = 800 / 2 - 64 / 2;
        drakeContainer.y = 20;
        drakeContainer.width = 256;
        drakeContainer.height = 256;
    }


    public void update (float deltaTime, float accelX, float accelY){
        updateDrake(deltaTime, accelX, accelY);
    }

    private void updateDrake (float deltaTime, float accelX, float accelY){
        drake.velocity.x = -accelX * Drake.DRAKE_MOVE_VELOCITY;
        drake.velocity.y = -accelY * Drake.DRAKE_MOVE_VELOCITY;
        drake.update(deltaTime);
    }

}
