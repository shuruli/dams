package com.draketilt.game;

/**
 * Created by davidchang on 6/18/16.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    public static Texture background;
    public static Texture drakeAlive;
    public static Texture drakeDead;
    public static Texture play;
    public static Texture drop;
    public static BitmapFont font;


    public static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

    public static void load(){
        Pixmap pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888 );
        pixmap.setColor( 0, 1, 0, 0.75f );
        pixmap.fillCircle( 32, 32, 32 );
        Texture dropAsset = new Texture(pixmap);
        background = loadTexture("background.png");
        play = new Texture("play.png");
        drakeAlive = new Texture("drakealive.png");
        drakeDead = new Texture("drakedead.png");
        drop = dropAsset;
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
    }
}
