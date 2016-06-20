package com.draketilt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by davidchang on 6/18/16.
 */
public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;
    private long lastDropTime;


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
        renderRaindrops();
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) renderRaindrops();

        Iterator<Rectangle> iter = this.world.raindrops.iterator();
        while(iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0) iter.remove();
            if(raindrop.overlaps(this.world.drakeContainer)) {
                iter.remove();
            }
        }
        batch.end();
    }

    public void renderDrake(){
        batch.draw(Assets.drake, world.drakeContainer.x, world.drakeContainer.y);
    }

    public void renderRaindrops(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, Gdx.graphics.getWidth());
        raindrop.y = Gdx.graphics.getHeight();
        raindrop.width = 64;
        raindrop.height = 64;
        this.world.raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }


}
