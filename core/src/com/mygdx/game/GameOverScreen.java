//package com.mygdx.game;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputProcessor;
//import com.badlogic.gdx.graphics.Camera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector3;
//
//public class GameOverScreen implements InputProcessor {
//    private Camera camera;
//    private Texture restartButtonTexture;
//    private Rectangle restartButtonBounds;
//    private GameScreen gameScreen;
//    public GameOverScreen(GameScreen gameScreen) {
//        this.gameScreen = gameScreen;
//        restartButtonTexture = new Texture("PNG/User Interface/RestartBtn.png");
//        // Set the position and size of the restart button
//        restartButtonBounds = new Rectangle(24, 2,0.1f,0.1f);
//    }
//
//    public void render(SpriteBatch batch) {
//        // Render game over message
//        BitmapFont font = new BitmapFont(); // Load your desired font here
//        font.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
//
//        // Render restart button
//        batch.draw(restartButtonTexture, restartButtonBounds.getX(), restartButtonBounds.getY(),30, 50);
//
//        // Check if restart button is clicked
//        if (Gdx.input.justTouched()) {
//            float touchX = Gdx.input.getX();
//            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
//            if (restartButtonBounds.contains(touchX, touchY)) {
//                // Restart the game
//                gameScreen.restartGame();
//            }
//        }
//    }
//
//    @Override
//    public boolean keyDown(int keycode) {
//        return false;
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        return false;
//    }
//
//    @Override
//    public boolean keyTyped(char character) {
//        return false;
//    }
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        // Convert screen coordinates to world coordinates
//        Vector3 touchPoint = new Vector3(screenX, screenY, 0);
//        camera.unproject(touchPoint);
//
//        // Check if the touch point is inside the restart button bounds
//        if (restartButtonBounds.contains(touchPoint.x, touchPoint.y)) {
//            // Restart button is touched, return true to consume the event
//            return true;
//        }
//
//        // If not touching restart button, return false
//        return false;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        // Convert screen coordinates to world coordinates
//        Vector3 touchPoint = new Vector3(screenX, screenY, 0);
//        camera.unproject(touchPoint);
//
//        // Check if the touch point is inside the restart button bounds
//        if (restartButtonBounds.contains(touchPoint.x, touchPoint.y)) {
//            // Restart button is released, trigger restart and return true to consume the event
//            gameScreen.restartGame();
//            return true;
//        }
//
//        // If not releasing restart button, return false
//        return false;
//    }
//
//    @Override
//    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        return false;
//    }
//
//    @Override
//    public boolean mouseMoved(int screenX, int screenY) {
//        return false;
//    }
//
//    @Override
//    public boolean scrolled(float amountX, float amountY) {
//        return false;
//    }
//}
