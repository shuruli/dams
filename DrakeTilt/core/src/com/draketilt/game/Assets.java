package com.draketilt.game;

/**
 * Created by davidchang on 6/18/16.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;
    public static TextureRegion mainMenuBackgroundRegion;
    public static Texture mainMenuBackground;
    public static Texture drakeAlive;
    public static Texture drakeDead;
    public static Texture play;
    public static Texture drop;
    public static Texture sound;
    public static BitmapFont font;
    public static BitmapFont titleFont;

    public static Music hotlinebling;
    public static Music onedance;
    public static Music backtoback;


    public static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

    public static void load(){
        background = loadTexture("background.png");
        mainMenuBackground = loadTexture("mainMenuBackground.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 800, 480);
        mainMenuBackgroundRegion = new TextureRegion(mainMenuBackground, 0, 0, 800, 480);

        play = loadTexture("play.png");
        drakeAlive = loadTexture("drakealive.png");
        drakeDead = loadTexture("drakedead.png");
        sound = loadTexture("soundOn.png");
        drop = loadTexture("cd.png");
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        titleFont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        hotlinebling = Gdx.audio.newMusic(Gdx.files.internal("hotlinebling.mp3"));
        hotlinebling.setLooping(true);
        onedance = Gdx.audio.newMusic(Gdx.files.internal("onedance.mp3"));
        onedance.setLooping(true);
        backtoback = Gdx.audio.newMusic(Gdx.files.internal("backtoback.mp3"));
        backtoback.setLooping(false);
        if (Settings.TOGGLE_SOUND) { hotlinebling.play(); }
    }

}
