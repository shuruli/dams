package com.draketilt.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by davidchang on 6/18/16.
 */
public class Drake extends DynamicGameObject {
    public static final float DRAKE_MOVE_VELOCITY = 150;
    public static final float DRAKE_WIDTH = Assets.drakeAlive.getWidth() - (Assets.drakeAlive.getWidth()/4);
    public static final float DRAKE_HEIGHT = Assets.drakeAlive.getHeight();
    public static final int DRAKE_ALIVE = 0;
    public static final int DRAKE_DEAD = 1;

    int state;

    public Drake (float x, float y){
        super(x, y, DRAKE_WIDTH, DRAKE_HEIGHT);
        state = DRAKE_ALIVE;
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
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;
    }

    public void hitCD(){
        state = DRAKE_DEAD;
    }
}
