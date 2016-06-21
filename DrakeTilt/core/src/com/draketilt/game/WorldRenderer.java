package com.draketilt.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
        renderDrops();
        batch.end();
    }

    public void renderDrake(){
        switch (world.drake.state) {
            case Drake.DRAKE_ALIVE:
                batch.draw(Assets.drakeAlive, world.drake.position.x, world.drake.position.y);
                break;
            case Drake.DRAKE_DEAD:
                batch.draw(Assets.drakeDead, world.drake.position.x, world.drake.position.y);
                break;
            default:
        }
    }

    public void renderDrops(){
        for(int i = 0; i < world.drops.size(); i++){
            Drops drop = world.drops.get(i);
            batch.draw(Assets.drop, drop.position.x, drop.position.y);
        }

    }

}
