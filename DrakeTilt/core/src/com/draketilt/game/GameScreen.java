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
    int score = 0;
    public String scoreString = "";

    DrakeTilt game;

    int state;
    OrthographicCamera guiCam;
    World world;
    WorldRenderer renderer;

    public GameScreen(DrakeTilt game){
        this.game = game;
        state = GAME_RUNNING;
        guiCam = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        world = new World();
        renderer = new WorldRenderer(game.batcher, world);

    }

    public void update (float deltaTime){
        Assets.onedance.play();
        switch(state){
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_OVER:
                Assets.onedance.pause();
                updateGameOver();
                break;
        }

    }

    private void updateRunning (float deltaTime){
        world.update(deltaTime, Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY());
        score += 1;
        scoreString = "Score: " + Integer.toString(score);
        if (world.state == world.WORLD_GAME_OVER){
            state = GAME_OVER;
        }

    }

    private void updateGameOver(){
        Assets.backtoback.play();
        if (Gdx.input.justTouched()) {
            Assets.backtoback.pause();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batcher.begin();
        game.batcher.draw(Assets.backgroundRegion, -Settings.GAME_WIDTH / 2, -Settings.GAME_HEIGHT / 2, 800, 480);
        game.batcher.end();
        renderer.render();
        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
        game.batcher.enableBlending();
        game.batcher.begin();
        switch (state){
            case GAME_READY:
                presentReady();
                break;
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_OVER:
                presentOver();
                break;
        }
        game.batcher.end();
    }
    public void presentReady(){

    }

    public void presentRunning(){
        Assets.font.draw(game.batcher, scoreString, - Settings.GAME_WIDTH / 2 + 20, Settings.GAME_HEIGHT / 2 - 25);
    }

    public void presentOver(){
        Assets.font.draw(game.batcher, "GAME OVER", -50, 0);
        Assets.font.draw(game.batcher, scoreString, - Settings.GAME_WIDTH / 2 + 20, Settings.GAME_HEIGHT / 2 - 25);
    }

    @Override
    public void render(float delta){
        update(delta);
        draw();
    }
}
