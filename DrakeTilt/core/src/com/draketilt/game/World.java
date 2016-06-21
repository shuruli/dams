package com.draketilt.game;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidchang on 6/18/16.
 */
public class World {
    public final Drake drake = new Drake(0, 0);
    public List<Drops> drops = new ArrayList<Drops>();
    public int WORLD_GAME_RUNNING = 0;
    public int WORLD_GAME_OVER = 1;
    public int state;
    public float difficulty = 0f;

    public void update (float deltaTime, float accelX, float accelY){
        difficulty += deltaTime;
        updateDrake(deltaTime, accelX, accelY);
        if(drake.state == drake.DRAKE_ALIVE) {
            updateDrops(deltaTime);
            addDrops();
            checkCollisions();
        }
        if (drake.state != drake.DRAKE_ALIVE){
            state = WORLD_GAME_OVER;
        }
    }

    private void updateDrake (float deltaTime, float accelX, float accelY){
        drake.velocity.x = -accelX * Drake.DRAKE_MOVE_VELOCITY;
        drake.velocity.y = -accelY * Drake.DRAKE_MOVE_VELOCITY;
        drake.update(deltaTime);
    }

    private void updateDrops (float deltaTime){
        for (int i = 0; i < drops.size(); i++){
            Drops drop = drops.get(i);
            drop.update(deltaTime);
            if (drop.position.y <= - Settings.GAME_HEIGHT / 2){
                drops.remove(drop);
            }
        }
    }

    private void addDrops (){
        if (MathUtils.random(1000.0f) < difficulty) {
            Drops drop = new Drops(MathUtils.random(-Settings.GAME_WIDTH / 2f, Settings.GAME_WIDTH / 2f), Settings.GAME_HEIGHT / 1.8f);
            drops.add(drop);
        }
    }

    private void checkCollisions(){
        for (int i = 0; i < drops.size(); i++){
            Drops drop = drops.get(i);
            if(drop.bounds.overlaps(drake.bounds)){
                drake.hitCD();
            }
        }
    }

}
