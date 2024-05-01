package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;
public class Coins implements CollidableObject{
    private MyGdxGame game;
    private GameScreen gameScreen;
    private Texture[] texture;
    private Texture[] explosion;

    private Vector2 position;

    private int speed = -200;

    private float frame = 0;


    enum State { NOT_COLLECTED, COLLECTED }
    private State state;
    private Random random = new Random();
    public void reset() {
        // Reset position and collected state
        this.position.set(Gdx.graphics.getWidth() + 200, calculateRandomHeight());
        this.state = State.NOT_COLLECTED;
    }

    private float calculateRandomHeight() {
        // Similar to enemy, calculate a new height
        float minHeight = 100;
        float maxHeight = Gdx.graphics.getHeight() - 100;
        return (float) (Math.random() * (maxHeight - minHeight) + minHeight);
    }

    public Coins(GameScreen gameScreen, Vector2 initialPosition){
//        this.game = game;
        this.gameScreen = gameScreen;
        this.position =  new Vector2(initialPosition.x, calculateRandomHeight()); // Set initial y to a random height
        this.texture = new Texture[8];
        for (int i = 0 ; i < 8; i++) {
            this.texture[i] = new Texture("PNG/Items Sprite/Coins/" + (i+1) + ".png");
        }
        this.explosion = new Texture[7];
        for (int i = 0 ; i < 7; i++) {
            this.explosion[i] = new Texture("PNG/Collision FX/02/" + (i+1) + ".png");
        }
        this.state = State.NOT_COLLECTED;
    }
    public void resetPosition() {
        this.position.set(Gdx.graphics.getWidth() + 200, calculateRandomHeight());  // Reset x to the right of the screen and y to a random height
    }
    public void update() {
        float dt = Gdx.graphics.getDeltaTime();

        this.position.add(new Vector2(this.speed * dt, 0));

        if (this.state == State.NOT_COLLECTED){
            this.frame += 20 * dt;
            if (this.frame >= 8) {
                this.frame = 0;
            }
        }

        if (this.state == State.COLLECTED){
            this.frame += 20 * dt;
            if (this.frame >= explosion.length) {
                this.frame = 0;
                this.state = State.NOT_COLLECTED;
                resetPosition();
            }
        }
        // Check if coins move off the left screen and reset their position
        if (this.position.x + texture[0].getWidth() < 0) {
            resetPosition();
        }

    }
    public void render(SpriteBatch batch) {
        if (this.state == State.NOT_COLLECTED) {
            batch.draw(this.texture[(int) this.frame], this.position.x, this.position.y);
        } else if (this.state == State.COLLECTED) {
            batch.draw(this.explosion[(int)this.frame], this.position.x - 75, this.position.y - 75);
        }

//        if (this.enemyState == Enemy.State.DEAD) {
//        }
    }


    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(this.position.x,this.position.y ,56,56);
    }

    @Override
    public void handleCollision() {
        if (this.state == State.NOT_COLLECTED) {
            Gdx.app.log("Collision", "Coin collected");
            this.state = State.COLLECTED;
            this.frame = 0;
            gameScreen.addScore(1);
        }
    }
}
