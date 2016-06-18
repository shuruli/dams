package com.draketilt.game;

/**
 * Created by davidchang on 6/18/16.
 */
public class World {
    public final Drake drake = new Drake(0, 0);


    public void update (float deltaTime, float accelX, float accelY){
        updateDrake(deltaTime, accelX, accelY);
    }

    private void updateDrake (float deltaTime, float accelX, float accelY){
        drake.velocity.x = -accelX * Drake.DRAKE_MOVE_VELOCITY;
        drake.velocity.y = -accelY * Drake.DRAKE_MOVE_VELOCITY;
        drake.update(deltaTime);
    }

}
