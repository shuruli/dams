package com.draketilt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidchang on 6/18/16.
 */
public class World {
    public int score = 0;
    public final Drake drake = new Drake(0, 0);
    public List<Drops> drops = new ArrayList<Drops>();
    public int WORLD_GAME_RUNNING = 0;
    public int WORLD_GAME_OVER = 1;
    public int WORLD_GAME_REGULAR = 2;
    public int WORLD_GAME_SLOW = 3;
    public int state = WORLD_GAME_REGULAR;
    public float difficulty = 0f;
    public float slow_time = 0f;
    public float slow_time_threshold = 1000.0f;

    public void update (float deltaTime, float accelX, float accelY){
        score += 1;
        if (state == WORLD_GAME_SLOW || state == WORLD_GAME_RUNNING){
            slow_time += 1;
            if (slow_time > slow_time_threshold){
                state = WORLD_GAME_RUNNING;
                if (drops.size() == 0) {
                    state = WORLD_GAME_REGULAR;
                    slow_time = 0f;
                }
            }
        }
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
        drake.velocity.x = accelX * Drake.DRAKE_MOVE_VELOCITY;
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
        if (state == WORLD_GAME_REGULAR) {
            if (MathUtils.random(1000.0f) < difficulty) {
                Drops drop = new Drops(MathUtils.random(-Settings.GAME_WIDTH / 2f, Settings.GAME_WIDTH / 2f), Settings.GAME_HEIGHT / 1.8f);
                drops.add(drop);
            }
        }
        else if (state == WORLD_GAME_SLOW){
            if (MathUtils.random(1000.0f) * 5 < difficulty) {
                Drops drop = new Drops(MathUtils.random(-Settings.GAME_WIDTH / 2f, Settings.GAME_WIDTH / 2f), Settings.GAME_HEIGHT / 1.8f);
                drop.RANDOM_VELOCITY = drop.RANDOM_VELOCITY / 3;
                drops.add(drop);
            }
        }
    }

    private void checkCollisions(){
        for (int i = 0; i < drops.size(); i++){
            Drops drop = drops.get(i);
            if(drop.bounds.overlaps(drake.bounds)){
                if (drop.type == Drops.DISS_TRACK){
                    score = 0;
                    drake.hitCD();
                }
                else if (drop.type == Drops.RIHANNA_TRACK){
                    drops.remove(drop);
                    score += 1000;
                    if (Settings.TOGGLE_SOUND) {Assets.riri.play();}
                }
                else if (drop.type == Drops.SIX_TRACK){
                    for (int j = 0; j < drops.size(); j++){
                        Drops existing = drops.get(i);
                        existing.RANDOM_VELOCITY = existing.RANDOM_VELOCITY / 3;
                    }
                    state = WORLD_GAME_SLOW;
                    slow_time = 0f;
                    drops.remove(drop);
                    if (Settings.TOGGLE_SOUND) {Assets.knowyourself.play();}
                }
            }
        }
    }

}
