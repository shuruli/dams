package com.draketilt.game;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by davidchang on 6/21/16.
 */
public class Drops extends GameObject {
    public static final float SIZE = 64;
    float RANDOM_VELOCITY = MathUtils.random(100, 300);
    public Drops(float x, float y){
        super(x, y, SIZE, SIZE);
    }

    public void update(float deltaTime){
        position.y += -deltaTime * RANDOM_VELOCITY;
        bounds.y = position.y - bounds.height / 2;
    }
}
