package com.draketilt.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by davidchang on 6/18/16.
 */

public class DynamicGameObject extends GameObject {
    public final Vector2 velocity;
    public final Vector2 accel;

    public DynamicGameObject (float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();
    }
}
