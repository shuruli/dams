package com.draketilt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Michael on 2016-07-12.
 */
public class HelpScreen extends ScreenAdapter {

    static final int SCREEN_1 = 0;
    static final int SCREEN_2 = 1;
    static final int SCREEN_3 = 2;
    static final int SCREEN_4 = 3;
    static final int SCREEN_5 = 4;
    int state;

    DrakeTilt game;

    public HelpScreen(DrakeTilt game){
        this.game = game;
        state = SCREEN_1;
    }

    public void update(){
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenuScreen(game));
            Gdx.input.setCatchBackKey(false);
            return;
        }
        if (Gdx.input.justTouched()){

            switch(state){
                case SCREEN_1:
                    state = SCREEN_2;
                    break;
                case SCREEN_2:
                    state = SCREEN_3;
                    break;
                case SCREEN_3:
                    state = SCREEN_4;
                    break;
                case SCREEN_4:
                    state = SCREEN_5;
                    break;
                case SCREEN_5:
                    game.setScreen(new MainMenuScreen(game));
                    Gdx.input.setCatchBackKey(false);
                    return;
            }
            Gdx.input.setCatchBackKey(false);
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch(state){
            case SCREEN_1:
                game.batcher.begin();
                game.batcher.draw(Assets.helpScreen1, -Settings.GAME_WIDTH/2,
                        -Settings.GAME_HEIGHT/2, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
                game.batcher.end();
                break;
            case SCREEN_2:
                game.batcher.begin();
                game.batcher.draw(Assets.helpScreen2, -Settings.GAME_WIDTH/2,
                        -Settings.GAME_HEIGHT/2, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
                game.batcher.end();
                break;
            case SCREEN_3:
                game.batcher.begin();
                game.batcher.draw(Assets.helpScreen3, -Settings.GAME_WIDTH/2,
                        -Settings.GAME_HEIGHT/2, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
                game.batcher.end();
                break;
            case SCREEN_4:
                game.batcher.begin();
                game.batcher.draw(Assets.helpScreen4, -Settings.GAME_WIDTH/2,
                        -Settings.GAME_HEIGHT/2, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
                game.batcher.end();
                break;
            case SCREEN_5:
                game.batcher.begin();
                game.batcher.draw(Assets.helpScreen5, -Settings.GAME_WIDTH/2,
                        -Settings.GAME_HEIGHT/2, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
                game.batcher.end();
                break;
        }
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }
}
