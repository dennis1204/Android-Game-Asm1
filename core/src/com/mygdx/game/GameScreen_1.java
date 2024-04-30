//package com.mygdx.game;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.InputProcessor;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Camera;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.utils.viewport.StretchViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.sun.security.auth.callback.TextCallbackHandler;
//
//public class GameScreen implements Screen, InputProcessor {
//    //screen
//    private Camera camera;
//    private Viewport viewport;
//    ShapeRenderer shapeRenderer;
//    //graphic
//    private SpriteBatch batch;
//    private TextureAtlas textureAtlas;
//
//    private TextureRegion[] playerFrame;
//    private float backgroundHeight;
//    private TextureRegion currentFrame,playerTextureRegion, playerAttackTextureRegion,enemyTextureRegion, enemyAttackTextureRegion, ItemsTextureRegion, ProjectileTextureRegion;
//    private Animation animation;
//    private int frameIndex;
//    private float stateTime;
//
//    private Texture[] backgrounds, platforms;
////    private Texture player;
//    //timing
//    private float[] backgroundOffsets= {0,0,0};
//    private float[] platformOffsets= {0,0,0,0,0,0};
//
//    private float backgroundMaxScrollingSpeed, platformMaxScollingSpeed;
//    //world parameters
////    public final int WORLD_WIDTH = 480;
////    public final int WORLD_HEIGHT = 800;
//    private Vector2 playerPosition;
//    //game object
//    private final int PLAYER_WIDTH = 7;
//    private final int PLAYER_HEIGHT = 15;
//    Player player;
//    Enemy enemy;
//    float x = 0;
//    float x1= 0;
//    float speed = 300;
//
//   //flying
//    private float flyingSpeed;
//    private static final float GRAVITY = -15f;
//    private boolean isFlying = false;
//   //collision
//   private Rectangle playerBounds;
//   private Rectangle[] platformBounds;
//   private GameOverScreen gameOverScreen;
//   private boolean isGameOver = false;
//    GameScreen(){
//        int WORLD_WIDTH = Gdx.graphics.getWidth();
//        int WORLD_HEIGHT = Gdx.graphics.getHeight();
//        camera = new OrthographicCamera();
//        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
////backgrounds
//        backgrounds = new Texture[3];
//        backgrounds[0] = new Texture("PNG/Backgrounds/03/Layer01.png");
//        backgrounds[1] = new Texture("PNG/Backgrounds/03/Layer02.png");
//        backgrounds[2] = new Texture("PNG/Backgrounds/03/Layer03.png");
//
//        backgroundMaxScrollingSpeed = (float)(WORLD_WIDTH) / 4;
//        //platforms
//        platforms = new Texture[6];
//        for (int i=0; i<6; i++){
//            platforms[i] = new Texture("PNG/Platform/Water Sprite/"+(i+1)+".png");
//        }
//
////        this.player = new Player(this);
////        playerFrame = new TextureRegion[8];
////        for (int i =0; i <8; i++){
////            playerFrame[i] = new TextureRegion(new Texture("PNG/Characters/01/Walk/"+(i+1)+".png"));
////        }
//
////        animation = new Animation(0.033f, playerFrame);
////        stateTime = 0.0f;
////        playerPosition = new Vector2(1, 50 );
//// Initialize player and platform bounding boxes
////        playerBounds = new Rectangle(playerPosition.x, playerPosition.y, PLAYER_WIDTH, PLAYER_HEIGHT);
////        platformBounds = new Rectangle[platforms.length];
////        for (int i = 0; i < platforms.length; i++) {
////            // Adjust platform position and dimensions based on offsets
////            float platformX = -platformOffsets[i];
////            float platformY = -100; // Assuming y-offset is constant for all platforms
////            float platformWidth = WORLD_WIDTH;
////            float platformHeight = WORLD_HEIGHT;
////
////            // Create bounding box
////            platformBounds[i] = new Rectangle(platformX, platformY, platformWidth, platformHeight);
////        }
////        gameOverScreen = new GameOverScreen(this);
//        //set up game object
////        player = new Player(2,3,10, 10, WORLD_WIDTH/2, WORLD_HEIGHT/4, playerTextureRegion, playerAttackTextureRegion);
////        enemy = new Player(2,1,10, 10, WORLD_WIDTH/2, WORLD_HEIGHT*3/4, enemyTextureRegion, enemyAttackTextureRegion);
//
//        batch = new SpriteBatch();
//        this.shapeRenderer = new ShapeRenderer();
//        Gdx.input.setInputProcessor(this);
//
//
//    }
//
//    @Override
//    public void show() {
//
//    }
//
//    @Override
//    public void render(float deltaTime) {
//        update();
////        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
////        stateTime += Gdx.graphics.getDeltaTime();
////        currentFrame = (TextureRegion) (animation.getKeyFrame(stateTime, true));
//        batch.begin();
//        // Scrolling
//        renderBackground(deltaTime);
//
//        // Player
////        this.player.render(this.batch);
////        if (!isGameOver) {
////            if (isFlying) {
////                // Update player position to simulate flying
////                flyingSpeed = 100;
////                playerPosition.y += flyingSpeed * deltaTime;
////            } else {
////                // Apply gravity
////                float gravityDelta = GRAVITY * deltaTime;
////                playerPosition.y += gravityDelta;
////                if (playerPosition.y <= 1) {
////                    playerPosition.y = 1;
////                }
////            }
////            // Update player bounding box
////            playerBounds.setPosition(playerPosition.x, playerPosition.y);
////
//////            // Render player
////            batch.draw(currentFrame, playerPosition.x, playerPosition.y, 7, 15);
//
////            // Check for collision
////            for (Rectangle platform : platformBounds) {
////                if (playerBounds.overlaps(platform)) {
////                    // Collision detected, handle it
////                    showGameOverScreen();
////                    break; // Exit the loop since the game is over
////                }
////            }
////        } else {
////            // If game over, render game over screen and stop further rendering
//////            showGameOverScreen();
////        }
//        player.render(batch);
//
//        enemy.render(batch);
//        batch.end();
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//
//        shapeRenderer.setColor(Color.RED);
//
//        if (player.getBoundingBox() != null) {
//            shapeRenderer.rect(player.getBoundingBox().x, player.getBoundingBox().y,
//                    player.getBoundingBox().width, player.getBoundingBox().height);
//        }
//
//        if (enemy.getBoundingBox() != null) {
//            shapeRenderer.rect(enemy.getBoundingBox().x, enemy.getBoundingBox().y,
//                    enemy.getBoundingBox().width, enemy.getBoundingBox().height);
//        }
//
//        shapeRenderer.end();
//    }
//
//    private void update() {
//        float dt = Gdx.graphics.getDeltaTime();
//
//        player.update();
//
//        enemy.update();
//
//        // Move background
//        x -= speed * dt;
//        x1 -= (speed * 2) * dt;
//
//        //Gdx.app.log("MyGdxGame", "Player: " + this.player.getBoundingBox().x);
//
//
//        if (player.getBoundingBox().overlaps(enemy.getBoundingBox())) {
//            enemy.handleCollision();
//        }
//
//        if (x < -backgrounds[0].getWidth()) x += backgrounds[0].getWidth();
//
//        // I need to update the camera in case I made some changes.
//        camera.update();
//    }
//
//    private void renderBackground(float deltaTime){
//        backgroundOffsets[0] += deltaTime*backgroundMaxScrollingSpeed/4;
//        backgroundOffsets[1] += deltaTime*backgroundMaxScrollingSpeed/2;
//        backgroundOffsets[2] += deltaTime*backgroundMaxScrollingSpeed;
//        platformOffsets[0] += deltaTime*backgroundMaxScrollingSpeed/10;
//        platformOffsets[1] += deltaTime*backgroundMaxScrollingSpeed/8;
//        platformOffsets[2] += deltaTime*backgroundMaxScrollingSpeed/6;
//        platformOffsets[3] += deltaTime*backgroundMaxScrollingSpeed/4;
//        platformOffsets[4] += deltaTime*backgroundMaxScrollingSpeed/2;
//        platformOffsets[5] += deltaTime*backgroundMaxScrollingSpeed ;
//
//
//        for (int layer = 0; layer < backgroundOffsets.length; layer++){
//            if(backgroundOffsets[layer]>backgrounds[0].getWidth()){
//                backgroundOffsets[layer]=0;
//            }
//            batch.draw(backgrounds[layer], -backgroundOffsets[layer], 0, backgrounds[0].getWidth(), backgrounds[0].getHeight());
//            batch.draw(backgrounds[layer], -backgroundOffsets[layer]+backgrounds[0].getWidth(), 0, backgrounds[0].getWidth(), backgrounds[0].getHeight());
//        }
//          for (int layer = 0; layer < platformOffsets.length; layer++) {
//              if (platformOffsets[layer] > backgrounds[0].getWidth()) {
//                  platformOffsets[layer] = 0;
//              }
//              batch.draw(platforms[layer], -platformOffsets[layer], -90, backgrounds[0].getWidth(), backgrounds[0].getHeight());
//              batch.draw(platforms[layer], -platformOffsets[layer] + backgrounds[0].getWidth(), -90, backgrounds[0].getWidth(), backgrounds[0].getHeight());
//          }
//    }
//    public void restartGame() {
//        // Reset player position
//        playerPosition.set(1, 50); // Assuming initial player position is (1, 50)
//
//        // Reset game over flag
//        isGameOver = false;
//
//        // Reset any other relevant game state variables
//        // Example:
//        // score = 0;
//        // lives = INITIAL_LIVES;
//
//        // Reset any other game objects or states as needed
//        // Example:
//        // resetEnemies();
//        // resetPlatforms();
//        // resetItems();
//    }
//    private void showGameOverScreen() {
//        // Initialize the GameOverScreen if not already initialized
//        if (gameOverScreen == null) {
//            gameOverScreen = new GameOverScreen(this);
//        }
//
//        // Render the GameOverScreen
//        gameOverScreen.render(batch);
//
//        // Stop player movement or any other gameplay actions
//        isFlying = false; // Stop player movement
//
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height, true);
//        batch.setProjectionMatrix(camera.combined);
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        for (Texture background : backgrounds) {
//            background.dispose();
//        }
//        for (Texture platform : platforms) {
//            platform.dispose();
//        }
//        this.player.dispose();
//    }
//
//    @Override
//    public boolean keyDown(int keycode) {
//        if (keycode == Input.Keys.UP) {
//            // Start flying when backspace key is pressed
//            isFlying = true;
//            return true; // Consume the event
//        }
//        return false;
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        if (keycode == Input.Keys.UP) {
//            // Stop flying when backspace key is released
//            isFlying = false;
//            return true; // Consume the event
//        }
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
//        return false;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
