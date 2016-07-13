package com.draketilt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.transformation.SortedList;

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
    boolean handledSaveData;
    OrthographicCamera guiCam;
    World world;
    WorldRenderer renderer;

    Preferences preferences;
    HighScores highScores;

    public GameScreen(DrakeTilt game){
        this.game = game;
        state = GAME_RUNNING;
        guiCam = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        world = new World();
        renderer = new WorldRenderer(game.batcher, world);
        preferences = Gdx.app.getPreferences("MyPreferences");
        handledSaveData = false;
    }

    public void update (float deltaTime){
        if (Settings.TOGGLE_SOUND) {
            Assets.onedance.play();
        }
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
        world.update(deltaTime, Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerX());
        score += 1;
        scoreString = "Score: " + Integer.toString(score);
        if (world.state == world.WORLD_GAME_OVER){
            state = GAME_OVER;
        }

    }

    private void updateGameOver(){
        if(Settings.TOGGLE_SOUND) {
            Assets.backtoback.play();
        }
        if (Gdx.input.justTouched()) {
            Assets.backtoback.pause();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private List<Score> shiftList(Score score, List<Score> list, int start, int end){
        List<Score> listToReturn = list;
        for (int i = end; i >=start; i--){
            if (i == start){
                listToReturn.set(i, score);
            } else {
                listToReturn.set(i, listToReturn.get(i - 1));
            }
        }
        return listToReturn;
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
        Assets.font.draw(game.batcher, Settings.GAME_OVER, -50, 0);
        Assets.font.draw(game.batcher, scoreString, - Settings.GAME_WIDTH / 2 + 20, Settings.GAME_HEIGHT / 2 - 25);

        if (!handledSaveData) {
            Date date = new Date(TimeUtils.millis());

            String todaysDate = date.toString();
            Score scoreObject = new Score();

            scoreObject.date = todaysDate;
            scoreObject.score = this.score;

            List<Score> listScores = new ArrayList<Score>();
            Json json = new Json();

            String jsonText = preferences.getString("highScores");
            highScores = json.fromJson(HighScores.class, jsonText);

            if (highScores != null && highScores.scores != null && highScores.scores.size() != 0) {
                if (highScores.isFull){
                    for (int i = 0; i < highScores.scores.size(); i++) {
                        if (score > highScores.scores.get(i).score) {
                            listScores = shiftList(scoreObject, highScores.scores, i, highScores.scores.size() - 1);
                        }
                    }
                } else {
                    for (int i = 0; i < highScores.scores.size(); i++) {
                        if (score > 0 && score > highScores.scores.get(i).score) {
                            listScores = shiftList(scoreObject, highScores.scores, i, highScores.scores.size() - 1);
                            if (i == 4){
                                highScores.isFull = true;
                            }
                            break;
                        }
                    }
                }
            } else {
                highScores = new HighScores();
                scoreObject = new Score();
                scoreObject.score = this.score;
                scoreObject.date = todaysDate;
                listScores.add(scoreObject);

                scoreObject = new Score();
                scoreObject.score = 0;
                scoreObject.date = "";

                for (int i = 1; i < 5; i++) {
                    listScores.add(scoreObject);
                }

                highScores.isFull = false;
            }
            highScores.scores = (ArrayList) listScores;

            json.setElementType(HighScores.class, "scores", Score.class);
            preferences.putString("highScores", json.prettyPrint(highScores));
            preferences.flush();

            preferences.getString("highScores");
            handledSaveData = true;
        }
    }

    @Override
    public void render(float delta){
        update(delta);
        draw();
    }
}
