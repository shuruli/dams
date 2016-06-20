package com.draketilt.game;

/**
 * Created by davidchang on 6/18/16.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends ScreenAdapter{
    DrakeTilt game;
    OrthographicCamera guiCam;
    Circle playBounds;
    Vector3 touchPoint = new Vector3();

    public MainMenuScreen(DrakeTilt game){
        this.game = game;
        guiCam = new OrthographicCamera(800, 480);
        playBounds = new Circle(0, 0, 80);
    }

    public void update(){
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));

            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new GameScreen(game));
                return;
            }
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
        game.batcher.disableBlending();
        game.batcher.begin();
        game.batcher.end();

        game.batcher.enableBlending();
        game.batcher.begin();
        game.batcher.draw(Assets.mainMenu, -64 / 2, -64 / 2);
        game.batcher.end();
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }

}
