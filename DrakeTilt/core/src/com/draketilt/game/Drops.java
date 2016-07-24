package com.draketilt.game;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by davidchang on 6/21/16.
 */
public class Drops extends GameObject {
    public static final float SIZE = 64;
    static public int DISS_TRACK = 0;
    static public int RIHANNA_TRACK = 1;
    static public int SIX_TRACK = 2;
    public int type = DISS_TRACK;
    float seed = MathUtils.random(1, 25);
    float RANDOM_VELOCITY = MathUtils.random(100, 300);
    public Drops(float x, float y){
        super(x, y, SIZE, SIZE);
        bounds.x = position.x - bounds.width;
        if (seed == 1){
            type = RIHANNA_TRACK;
        }
        else if (seed == 2){
            type = SIX_TRACK;
        }
    }

    public void update(float deltaTime){
        position.y += -deltaTime * RANDOM_VELOCITY;
        bounds.y = position.y - bounds.height / 2;
    }
}