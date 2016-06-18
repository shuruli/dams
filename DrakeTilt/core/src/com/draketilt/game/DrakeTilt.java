package com.draketilt.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class DrakeTilt extends ApplicationAdapter {
	SpriteBatch batch;
    private Texture dropImage;
    private Texture bucketImage;
    private OrthographicCamera camera;
    private Rectangle bucket;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    Texture img;
	
	@Override
	public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

        Pixmap pixmap = new Pixmap( 64, 64, Pixmap.Format.RGBA8888 );
        pixmap.setColor( 0, 1, 0, 0.75f );
        pixmap.fillCircle( 32, 32, 32 );

        Pixmap pixmapBucket = new Pixmap( 256, 256, Pixmap.Format.RGB888 );
        pixmapBucket.setColor( 0, 1, 0, 0.75f );

        dropImage = new Texture(pixmap);
        bucketImage = new Texture(pixmapBucket);

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 256;
        bucket.height = 256;

        raindrops = new Array<Rectangle>();
        spawnFallingItems();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        for(Rectangle raindrop: raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        batch.end();

        // process user input
        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > 800 - 64) bucket.x = 800 - 64;

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnFallingItems();

        Iterator<Rectangle> iter = raindrops.iterator();
        while(iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0) iter.remove();
            if(raindrop.overlaps(bucket)) {
                iter.remove();
            }
        }

    }

    private void spawnFallingItems() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, Gdx.graphics.getWidth());
        raindrop.y = Gdx.graphics.getHeight();
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose(){
        dropImage.dispose();
        batch.dispose();
    }
}
