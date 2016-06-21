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
        guiCam = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        playBounds = new Circle(0, 0, (float) (Assets.play.getHeight() / 3.14));
    }

    public void update(){
        Assets.hotlinebling.play();
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));

            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.hotlinebling.pause();
                game.setScreen(new GameScreen(game));
                return;
            }
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
        game.batcher.begin();
        game.batcher.draw(Assets.backgroundRegion, -Settings.GAME_WIDTH / 2, -Settings.GAME_HEIGHT / 2, 800, 480);
        game.batcher.end();

        game.batcher.enableBlending();
        game.batcher.begin();
        game.batcher.draw(Assets.play, -Assets.play.getWidth() / 2, -Assets.play.getHeight() / 2);
        game.batcher.end();
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }

}
