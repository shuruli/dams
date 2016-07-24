package com.draketilt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sudhanvahuruli on 7/3/16.
 */

public class LeaderboardScreen extends ScreenAdapter {
    DrakeTilt game;
    OrthographicCamera guiCam;
    float initTitleY = Settings.GAME_HEIGHT/2 - Settings.GAME_HEIGHT/12;
    float initTitleX = -Settings.GAME_WIDTH/2 + Settings.GAME_WIDTH/12;

    float initLeaderboardX, initLeaderboardY, heightPerLabel;

    HighScores highScores;
    Preferences preferences;
    List<Integer> scoreList = new ArrayList<Integer>(5);
    List<String> dateList = new ArrayList<String>(5);

    Vector3 touchPoint = new Vector3();

    public LeaderboardScreen(DrakeTilt game){

        initLeaderboardX = -Settings.GAME_WIDTH/2+Settings.GAME_WIDTH/14;
        initLeaderboardY = -Settings.GAME_HEIGHT/2 + Settings.GAME_HEIGHT/3;
        heightPerLabel = Settings.GAME_HEIGHT/12;

        this.game = game;

        if (Gdx.app.getPreferences("DrakeTiltPreferences") == null){
            return;
        }

        this.preferences = Gdx.app.getPreferences("DrakeTiltPreferences");
        guiCam = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);

    }

    public void update(){
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenuScreen(game));
            Gdx.input.setCatchBackKey(false);
            return;
        }

        if (Settings.TOGGLE_SOUND){
            Assets.hotlinebling.play();
        }
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));
            Gdx.app.log("MainMenuScreen.java/update()", touchPoint.x + " " + touchPoint.y);
            Gdx.app.log("Toggle sound", " " + Settings.TOGGLE_SOUND);
        }

        Assets.titleFont.getData().setScale(2,2);
        // access the file system to get the top five stored
        Json json = new Json();
        String jsonText = preferences.getString("highScores");
        highScores = json.fromJson(HighScores.class, jsonText);


        int score = 0;
        String date = "07032016";

        // populate necessary objects from DB
        scoreList.clear();
        dateList.clear();

        if (highScores == null){
            for (int i =0; i < 5; i++){
                scoreList.add(0);
                dateList.add("");
            }
        } else if (highScores.scores == null){
            for (int i =0; i < 5; i++){
                scoreList.add(0);
                dateList.add("");
            }
        } else {
            for (int i = 0; i < 5; i++) {
                scoreList.add(highScores.scores.get(i).score);
                dateList.add(highScores.scores.get(i).date);
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
        Assets.titleFont.draw(game.batcher, Settings.LEADERBOARDS, initLeaderboardX , initTitleY);
        for (int i = 1; i <= 5; i++){
            Assets.font.draw(game.batcher, (6 - i)+". "+ scoreList.get(5-i) + " "+ dateList.get(5 - i), initLeaderboardX, initLeaderboardY + heightPerLabel*(i-1));
        }
        game.batcher.end();
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }


}

