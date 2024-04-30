package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Projectile implements CollidableObject{
    Texture texture;
    Vector2 position;
    float speed = 400;  // Projectile speed
    boolean isActive = true;
    public Projectile(String texturePath) {
        texture = new Texture(texturePath);
        position = new Vector2(-100, -100);  // Default position off-screen
    }

    public void update(float deltaTime) {
        if(isActive) {
            position.x += speed * deltaTime;  // Move the projectile
        }
    }

    public void render(Batch batch) {
        if (isActive && position.x < Gdx.graphics.getWidth()) {  // Only draw if within screen bounds
            batch.draw(texture, position.x, position.y);
        }
    }

    public void shoot(float startX, float startY) {
        position.set(startX, startY);
        isActive = true; // Set the starting position of the projectile
    }
    public boolean isActive() {
        return isActive && position.x < Gdx.graphics.getWidth();  // Check if the projectile is still active
    }
    public void reset() {
        position.set(-100, -100);  // Move back off-screen
        isActive = false;  // Deactivate
    }
    public void dispose() {
        texture.dispose();
    }

    @Override
    public Rectangle getBoundingBox() {
        // Assume the texture dimensions are the dimensions of the projectile
        if (texture != null) {
            return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        }
        return null;
    }

    @Override
    public void handleCollision() {
        isActive = false;

    }
}
