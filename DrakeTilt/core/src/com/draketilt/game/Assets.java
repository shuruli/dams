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
    public static Texture drake;
    public static Texture mainMenu;
    public static Texture raindrop;
    public static BitmapFont font;


    public static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }
    public static Texture loadTexture(Pixmap pixmap) {return new Texture(pixmap);}

    public static void load(){
        Pixmap pixmap = new Pixmap( 64, 64, Pixmap.Format.RGBA8888 );
        pixmap.setColor( 0, 1, 0, 0.75f );
        pixmap.fillCircle( 32, 32, 32 );

        background = loadTexture("background.png");
        mainMenu = new Texture("play.png");
        drake = new Texture("drake.png");
        raindrop = loadTexture(pixmap);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
    }
}
