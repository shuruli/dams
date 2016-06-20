package com.draketilt.game;

/**
 * Created by davidchang on 6/18/16.
 */
public class Drake extends DynamicGameObject {
    public static final float DRAKE_MOVE_VELOCITY = 50;
    public static final float DRAKE_WIDTH = 0.8f;
    public static final float DRAKE_HEIGHT = 0.8f;

    public Drake (float x, float y){
        super(x, y, DRAKE_WIDTH, DRAKE_HEIGHT);
    }

    public void update (float deltaTime){
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
    }
}
