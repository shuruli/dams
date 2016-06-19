package com.draketilt.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by davidchang on 6/18/16.
 */
public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;

    public WorldRenderer (SpriteBatch batch, World world) {
        this.world = world;
        this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
        this.batch = batch;
    }

    public void render(){
        renderBackground();
        renderObjects();
    }

    public void renderBackground(){
        batch.disableBlending();
        batch.begin();
        batch.end();
    }

    public void renderObjects(){
        batch.enableBlending();
        batch.begin();
        renderDrake();
        batch.end();
    }

    public void renderDrake(){
        batch.draw(Assets.drake, world.drake.position.x, world.drake.position.y);
    }

}
