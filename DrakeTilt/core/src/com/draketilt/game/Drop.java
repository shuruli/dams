package com.draketilt.game;

/**
 * Created by sudhanvahuruli on 6/20/16.
 */
public class Drop extends DynamicGameObject {
    public static final float DROP_MOVE_VELOCITY = 50;
    public static final float DROP_WIDTH = 0.8f;
    public static final float DROP_HEIGHT = 0.8f;

    public Drop(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void update (float deltaTime){
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
    }
}
