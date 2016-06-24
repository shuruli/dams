package com.draketilt.game;

/**
 * Created by davidchang on 6/18/16.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends ScreenAdapter{
    DrakeTilt game;
    OrthographicCamera guiCam;
    float initX, initY, initWidth, initHeight, heightPerLabel, initSoundX, initSoundY, initSoundWidth, initSoundHeight;
    Rectangle playBounds, leaderboardBounds, soundBounds, helpBounds;
    Vector3 touchPoint = new Vector3();

    public MainMenuScreen(DrakeTilt game){
        //coordinates for the full menu box
        initX = -Settings.GAME_WIDTH/2+Settings.GAME_WIDTH/14;
        initY = -Settings.GAME_HEIGHT/2 + Settings.GAME_HEIGHT/3;
        initWidth = Settings.GAME_WIDTH/4;
        initHeight = Settings.GAME_HEIGHT/3;
        heightPerLabel = initHeight/4;

        initSoundX = Settings.GAME_WIDTH/2 - Settings.GAME_WIDTH/14;
        initSoundY = Settings.GAME_HEIGHT/2 - Settings.GAME_HEIGHT/8;
        initSoundWidth = Settings.GAME_WIDTH/14;
        initSoundHeight = Settings.GAME_WIDTH/14;

        this.game = game;
        guiCam = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);

        playBounds = new Rectangle(initX, initY + 2*heightPerLabel, initWidth, heightPerLabel/2);
        leaderboardBounds = new Rectangle(initX, initY + 1*heightPerLabel, initWidth, heightPerLabel/2);
        helpBounds = new Rectangle(initX, initY, initWidth, heightPerLabel/2);
        soundBounds = new Rectangle(initSoundX, initSoundY, initSoundWidth, initSoundHeight);

    }

    public void update(){
        if (Settings.TOGGLE_SOUND){
            Assets.hotlinebling.play();
        }
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));
            Gdx.app.log("MainMenuScreen.java/update()", touchPoint.x + " " + touchPoint.y);
            Gdx.app.log("Toggle sound", " " + Settings.TOGGLE_SOUND);
            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.hotlinebling.pause();
                game.setScreen(new GameScreen(game));
                return;
            } else if (soundBounds.contains(touchPoint.x, touchPoint.y)){
                if (Settings.TOGGLE_SOUND){
                    Assets.hotlinebling.pause();
                }
                else if (!Settings.TOGGLE_SOUND){
                    Assets.hotlinebling.play();
                }
                Settings.TOGGLE_SOUND = !Settings.TOGGLE_SOUND;
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
        game.batcher.draw(Assets.mainMenuBackgroundRegion, -Settings.GAME_WIDTH/2, -Settings.GAME_HEIGHT/2, 800, 480);
        game.batcher.draw(Assets.sound, initSoundX, initSoundY, initSoundWidth, initSoundHeight);

        Assets.font.setColor(Color.BLACK);
        Assets.font.draw(game.batcher, Settings.PLAY, initX, initY + 3*heightPerLabel + heightPerLabel/2);
        Assets.font.draw(game.batcher, Settings.LEADERBOARDS, initX, initY + 2*heightPerLabel+ heightPerLabel/2);
        Assets.font.draw(game.batcher, Settings.HELP, initX, initY+ heightPerLabel+ heightPerLabel/2);
        game.batcher.end();
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }

}
