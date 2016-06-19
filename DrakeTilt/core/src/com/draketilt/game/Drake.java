package com.draketilt.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by davidchang on 6/18/16.
 */
public class Drake extends DynamicGameObject {
    public static final float DRAKE_MOVE_VELOCITY = 100;
    public static final float DRAKE_WIDTH = Assets.drake.getWidth();
    public static final float DRAKE_HEIGHT = Assets.drake.getWidth();

    public Drake (float x, float y){
        super(x, y, DRAKE_WIDTH, DRAKE_HEIGHT);
    }

    public void update (float deltaTime){
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        if (position.x < 0 - Settings.GAME_WIDTH / 2){
            position.x = 0 - Settings.GAME_WIDTH / 2;
        }
        if (position.x > Settings.GAME_WIDTH / 2 - DRAKE_WIDTH){
            position.x = Settings.GAME_WIDTH / 2 - DRAKE_WIDTH;
        }
        if (position.y < 0 - Settings.GAME_HEIGHT / 2){
            position.y = 0 - Settings.GAME_HEIGHT / 2;
        }
        if (position.y > Settings.GAME_HEIGHT / 2 - DRAKE_HEIGHT){
            position.y = Settings.GAME_HEIGHT / 2 - DRAKE_HEIGHT;
        }
    }
}
