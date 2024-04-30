package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Text;

public class Player implements CollidableObject, InputProcessor {
    MyGdxGame game;

    Texture[] texture = new Texture[8];
    Texture[] flying = new Texture[4];
    Texture[] dieTextures = new Texture[4];  // Textures for the die animation

    float x = 0;
    float y = 0;

    float FallingSpeed ;

    float gravity = -210;

    float frame = 0;
    private float flyingSpeed;
    boolean isFlying = false;

    Projectile projectile1, projectile2, projectile3, projectile4, projectile5;
    int nextProjectile = 0;
    enum State {
        ALIVE, DYING, DEAD
    }
    State state = State.ALIVE;
    float dieFrame = 0;
    int dieAnimationLoops = 0;
    public Player(MyGdxGame game) {
        // As I am moving to an array of Textures, I need to load the values into the array.
        for (int i = 0 ; i < 8; i++) {
            this.texture[i] = new Texture("PNG/Characters/08/Walk/" + (i+1) + ".png");
        }
        for (int i = 0; i < 4; i++){
            this.flying[i] = new Texture("PNG/Characters/Jetpack Smoke/"+(i+1)+".png");
        }
        for (int i = 0; i < 4; i++) {
            dieTextures[i] = new Texture("PNG/Characters/08/Die/" + (i + 1) + ".png");
        }
        projectile1 = new Projectile("PNG/Projectile/10.png");  // Assuming you have a texture for the projectile
        projectile2 = new Projectile("PNG/Projectile/10.png");
        projectile3 = new Projectile("PNG/Projectile/10.png");
        projectile4 = new Projectile("PNG/Projectile/10.png");
        projectile5 = new Projectile("PNG/Projectile/10.png");
        this.game = game;
    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();
        if (state == State.ALIVE) {
            this.FallingSpeed = gravity * dt;
            // Update player's vertical position
            if (isFlying) {
                flyingSpeed = 250;
                // If flying, adjust the y position based on flying speed
                this.y += flyingSpeed * dt;
            } else {
                // If not flying, move the player down based on gravity
                this.y += FallingSpeed;
                if (this.y <= 0) {
                    this.y = 1; // Ensure the player doesn't fall below the ground level
                }
            }
        }else if (state == State.DYING) {
            dieFrame += 20 * dt;  // Increase frame count for die animation
            if (dieFrame >= dieTextures.length) {
                dieFrame = 0;
                dieAnimationLoops++;  // Increment loop counter
                if (dieAnimationLoops >= 10) {  // Check if it has looped twice
                    state = State.DEAD;
                    dieAnimationLoops = 0;  // Reset the counter
                }
            }
        }

        // Update player's horizontal position
        if (this.x <= 200) {
            this.x += 100 * dt;
        } else {
            this.x = 200;
            // Move background
            // this.game.setX(this.game.getX() - (300 * dt));
        }

        // Update animation frame
        updateFrame(dt);
        projectile1.update(dt);
        projectile2.update(dt);
        projectile3.update(dt);
        projectile4.update(dt);
        projectile5.update(dt);
    }

    private void updateFrame(float dt) {
        int maxFrames = isFlying ? 4 : 8;
        this.frame += 20 * dt;
        if (this.frame >= maxFrames) {
            this.frame = 0;
        }
    }
    public Vector2 getPosition() {
        return new Vector2(this.x, this.y);
    }
    @Override
    public void handleCollision() {
        if (state == State.ALIVE) {
            state = State.DYING;  // Start dying animation
            dieFrame = 0;  // Reset die animation frame
        }
    }



    public void render(Batch batch) {
        if (state == State.ALIVE) {
            // Always draw the current frame of the walking animation
            batch.draw(this.texture[(int) this.frame % this.texture.length], this.x, this.y);
            // If the player is flying, also draw the jetpack smoke animation on top
            if (isFlying) {
                batch.draw(this.flying[(int) this.frame % this.flying.length], this.x - 60, this.y - 100);
            }
        }else if (state == State.DYING) {
            batch.draw(dieTextures[(int)dieFrame], this.x, this.y);
        }
        else if (state == State.DEAD) {
            batch.draw(dieTextures[dieTextures.length - 1], this.x, this.y);
        }

        // Render projectiles
        projectile1.render(batch);
        projectile2.render(batch);
        projectile3.render(batch);
        projectile4.render(batch);
        projectile5.render(batch);
    }

    @Override
    public Rectangle getBoundingBox()
    {
        // I'm guessing a bit with these values. But there is a trick using a ShapeRender that makes
        // this easy to work with. I'll share that separately, as it warrants a video.
        return new Rectangle(x + 30,y + 30,65,90);
    }



    public void dispose() {
        for (Texture t : texture) t.dispose();
        for (Texture f : flying) f.dispose();
        for (Texture d : dieTextures) d.dispose();
        projectile1.dispose();
        projectile2.dispose();
        projectile3.dispose();
        projectile4.dispose();
        projectile5.dispose();
    }
    public boolean isAlive() {
        return state == State.ALIVE;
    }
    public void reset() {
        x = 0;  // Reset position to the far left of the map
        y = 100; // Start at ground level or an appropriate starting Y position
        state = State.ALIVE;  // Set state to ALIVE
        isFlying = false;  // Ensure the player isn't flying on reset
        frame = 0;  // Reset animation frame
        dieFrame = 0;  // Reset die animation frame
        dieAnimationLoops = 0;  // Reset die animation loop counter

        // Reset all projectiles to their initial state if needed
        projectile1.reset();
        projectile2.reset();
        projectile3.reset();
        projectile4.reset();
        projectile5.reset();
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
            // Start flying when backspace key is pressed
            isFlying = true;
            Gdx.app.log("Player", "Up key pressed");
            return true; // Consume the event
        }
        if (keycode == Input.Keys.RIGHT) {
             shootProjectile();// Shoot from a position relative to the player
            return true;
        }
        return false;
    }
    private void shootProjectile() {
        switch (nextProjectile) {
            case 0:
                projectile1.shoot(this.x + 50, this.y + 20);
                break;
            case 1:
                projectile2.shoot(this.x + 50, this.y + 20);
                break;
            case 2:
                projectile3.shoot(this.x + 50, this.y + 20);
                break;
            case 3:
                projectile4.shoot(this.x + 50, this.y + 20);
                break;
            case 4:
                projectile5.shoot(this.x + 50, this.y + 20);
                break;
        }
        nextProjectile = (nextProjectile + 1) % 5; // Cycle through projectiles
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP) {
            // Stop flying when backspace key is released
            isFlying = false;
            return true; // Consume the event
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
