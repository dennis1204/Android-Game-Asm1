package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Enemy implements CollidableObject {

    private MyGdxGame game;

    private Texture[] texture, texture1, texture2;
    private Texture[] explosion;

    private Vector2 position;

    private int speed = -200;

    private float frame = 0;

    enum Type {
        TYPE1, TYPE2, TYPE3 // Define as many types as you need
    }
    private Type type;
    enum State {ALIVE, DYING, DEAD}

    private State enemyState;
    private Random random = new Random();
    private float calculateRandomHeight() {
        // Calculate a new height for the enemy if necessary
        float minHeight = 50;
        float maxHeight = Gdx.graphics.getHeight() - 50;
        return (float) (Math.random() * (maxHeight - minHeight) + minHeight);
    }
    public void reset() {
        // Reset position to the initial starting position or a new position off-screen
        if(type == Type.TYPE1){
            this.position.set(Gdx.graphics.getWidth() + 100, 0);
        }else {
            this.position.set(Gdx.graphics.getWidth() + 100, calculateRandomHeight());
        }
        // Reset any other necessary attributes, like health, visibility, etc.
        this.enemyState = State.ALIVE;
    }
    public Enemy(MyGdxGame game, Vector2 initialPosition, Type type) {
        this.game = game;
        if (type == Type.TYPE1) {
            this.position = new Vector2(initialPosition.x, 0); // TYPE1 always at height 0
        } else {
            this.position = new Vector2(initialPosition.x, calculateRandomHeight()); // Other types get random height
        }
        this.type = type;
        switch (type) {
            case TYPE1:
                this.texture = loadTextures("PNG/Enemy Sprite/02/", 8);
                break;
            case TYPE2:
                this.texture = loadTextures("PNG/Enemy Sprite/09/", 4);
                break;
            case TYPE3:
                this.texture = loadTextures("PNG/Enemy Sprite/10/", 4); // Assuming you have another sprite folder
                break;
        }
        this.explosion = loadTextures("PNG/Collision FX/02/", 7);

        this.enemyState = State.ALIVE;
    }
    private Texture[] loadTextures(String path, int count) {
        Texture[] textures = new Texture[count];
        for (int i = 0; i < count; i++) {
            textures[i] = new Texture(path + (i + 1) + ".png");
        }
        return textures;
    }
    public void resetPosition() {
        if (this.type == Type.TYPE1) {
            this.position.set(Gdx.graphics.getWidth() + 200, 0);  // Ensure TYPE1 always resets to y = 0
        } else {
            this.position.set(Gdx.graphics.getWidth() + 200, calculateRandomHeight());  // Other types get a random height
        }
    }


    public void update() {
        float dt = Gdx.graphics.getDeltaTime();

        this.position.add(new Vector2(this.speed * dt, 0));

        if (this.enemyState == State.ALIVE){
            this.frame += 20 * dt;
            if (this.frame >= 8) {
                this.frame = 0;
            }
        }

        if (this.enemyState == Enemy.State.DYING){
            this.frame += 20 * dt;
            if (this.frame >= explosion.length) {
                this.frame = 0;
                this.enemyState = Enemy.State.ALIVE;
                resetPosition();
            }
        }
        if (this.position.x + texture[0].getWidth() < 0) {
            resetPosition();
        }

    }



    public void render(SpriteBatch batch) {
        if (this.enemyState == State.ALIVE) {
            batch.draw(this.texture[(int) this.frame % this.texture.length], this.position.x, this.position.y);
        } else if (this.enemyState == State.DYING) {
            batch.draw(this.explosion[(int) this.frame % this.explosion.length], this.position.x - 75, this.position.y - 75);
        }
        // No rendering for DEAD state as assumed
    }



    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(this.position.x + 40,this.position.y + 30,55,90);
    }

    @Override
    public void handleCollision() {
        if (this.enemyState == State.ALIVE) {
            this.enemyState = State.DYING;
            this.frame = 0;
        }
    }
}
