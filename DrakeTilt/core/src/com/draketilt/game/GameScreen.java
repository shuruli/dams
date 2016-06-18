package com.draketilt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by davidchang on 6/18/16.
 */
public class GameScreen extends ScreenAdapter {
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_OVER = 3;

    DrakeTilt game;

    int state;
    OrthographicCamera guiCam;
    World world;
    WorldRenderer renderer;

    public GameScreen(DrakeTilt game){
        this.game = game;
        state = GAME_READY;
        guiCam = new OrthographicCamera(800, 480);
        world = new World();
        renderer = new WorldRenderer(game.batcher, world);

    }

    public void update (float deltaTime){
        updateRunning(deltaTime);
    }

    private void updateRunning (float deltaTime){
        world.update(deltaTime, Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY());
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
        game.batcher.enableBlending();
        game.batcher.begin();
        switch (state){

        }
        game.batcher.end();
    }

    @Override
    public void render(float delta){
        update(delta);
        draw();
    }
}
