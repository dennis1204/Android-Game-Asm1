package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.audio.Music;
public class GameScreen implements Screen{
    private MyGdxGame game;
    private SpriteBatch batch;
    private Texture background, background1;
    private Texture[] backgrounds, platforms;
    private float backgroundMaxScrollingSpeed, platformMaxScollingSpeed;
    private float[] backgroundOffsets= {0,0,0};
    private float[] platformOffsets= {0,0,0,0,0,0};
    private float x = 0, x1 = 0;
    private float speed = 300;
    private Player player;
    private Enemy enemy1, enemy2, enemy3;
    private Coins coins;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private ImageButton restartButton;
    private boolean isPaused = false;
    private Music backgroundMusic;
    private int currentScore = 0;
    private int highScore = 0;
    private Label scoreLabel;
    private Label highScoreLabel;

    public GameScreen (MyGdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        int screenWidth  = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        stage = new Stage(new ScreenViewport());

        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("PNG/Backgrounds/03/Layer01.png");
        backgrounds[1] = new Texture("PNG/Backgrounds/03/Layer02.png");
        backgrounds[2] = new Texture("PNG/Backgrounds/03/Layer03.png");

        backgroundMaxScrollingSpeed = (float)(screenWidth) / 4;
        //platforms
        platforms = new Texture[6];
        for (int i=0; i<6; i++){
            platforms[i] = new Texture("PNG/Platform/Water Sprite/"+(i+1)+".png");
        }

        player = new Player(game);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        viewport = new FitViewport(screenWidth, screenHeight, camera);

        enemy1 = new Enemy(game, new Vector2(screenWidth + 100, 0), Enemy.Type.TYPE1);
        enemy2 = new Enemy(game, new Vector2(screenWidth + 300, 200), Enemy.Type.TYPE2);
        enemy3 = new Enemy(game, new Vector2(screenWidth + 500, 0), Enemy.Type.TYPE3);
        coins = new Coins(this, new Vector2(screenWidth + 200, 300));

        highScore = loadHighScore();
        restartBtnUI();
        createPauseButton();
        loadMusic();
        createMusicToggleButton();
        // Setup UI for displaying scores
        setupScoreDisplay();
    }
    private void setupScoreDisplay() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(); // Or use your preferred font
        labelStyle.font.getData().setScale(3.0f);
        scoreLabel = new Label("Score: "+ currentScore, labelStyle);
        highScoreLabel = new Label("Highest Score: " + highScore, labelStyle);

        Table table = new Table();
        table.top().right();
        table.setFillParent(true);

        table.padRight(20).padTop(10);

        table.add(highScoreLabel).padTop(10);
        table.row();
        table.add(scoreLabel).padTop(10);

        stage.addActor(table);
    }
    public void addScore(int value) {
        currentScore += value;
        scoreLabel.setText("Score: " + currentScore);
        if (currentScore > highScore) {
            highScore = currentScore;
            highScoreLabel.setText("Highest Score: " + highScore);
            saveHighScore(highScore);
        }
    }
    private int loadHighScore() {
        return Gdx.app.getPreferences("MyPreferences").getInteger("highScore", 0);
    }

    private void saveHighScore(int score) {
        Gdx.app.getPreferences("MyPreferences").putInteger("highScore", score).flush();
    }

    private void loadMusic() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/bgMusic1.wav"));
        backgroundMusic.setLooping(true);  // Set the music to loop
        backgroundMusic.setVolume(0.5f);   // Set volume to 50%
        backgroundMusic.play();

    }
    private void restartBtnUI() {
        Texture restartButtonNormal = new Texture(Gdx.files.internal("PNG/User Interface/RestartBtn.png"));
        Texture restartButtonPressed = new Texture(Gdx.files.internal("PNG/User Interface/RestartBtn.png"));
        Drawable restartDrawableNormal = new TextureRegionDrawable(new TextureRegion(restartButtonNormal));
        Drawable restartDrawablePressed = new TextureRegionDrawable(new TextureRegion(restartButtonPressed));

        restartButton = new ImageButton(restartDrawableNormal, restartDrawablePressed);
        restartButton.setPosition( (stage.getWidth() - restartButton.getWidth()) / 2,
                (stage.getHeight() - restartButton.getHeight()) / 2); // Assuming a screen size of 800x480
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                restartGame();
            }
        });
        restartButton.setVisible(false);
        stage.addActor(restartButton);

        // Ensure to set the stage as the input processor or part of an input multiplexer
        Gdx.input.setInputProcessor(stage);
    }
    private void restartGame() {
        // Logic to reset the game elements
        player.reset();
        enemy1.reset();
        enemy2.reset();
        enemy3.reset();
        coins.reset();
        // Reset the score
        currentScore = 0;
        scoreLabel.setText("Score: " + currentScore); // Update the score label to reflect the reset
        restartButton.setVisible(false);
    }
    private void createPauseButton() {
        Texture pauseBtnTexture = new Texture(Gdx.files.internal("PNG/User Interface/PauseBtn.png"));
        Drawable pauseDrawable = new TextureRegionDrawable(new TextureRegion(pauseBtnTexture));

        ImageButton pauseButton = new ImageButton(pauseDrawable);
        pauseButton.setPosition(20, viewport.getWorldHeight() - 140); // Top-left corner
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isPaused = !isPaused;
            }
        });
        stage.addActor(pauseButton);
    }
    private void createMusicToggleButton() {
        Texture musicOnTexture = new Texture(Gdx.files.internal("PNG/User Interface/SoundON.png"));
        Texture musicOffTexture = new Texture(Gdx.files.internal("PNG/User Interface/SoundON.png"));
        Drawable musicOnDrawable = new TextureRegionDrawable(new TextureRegion(musicOnTexture));
        Drawable musicOffDrawable = new TextureRegionDrawable(new TextureRegion(musicOffTexture));

        // Create the button with initial (music on) and toggled (music off) states
        ImageButton musicToggleButton = new ImageButton(musicOnDrawable, null, musicOffDrawable);

        // Set initial unchecked state to represent music being on initially
        musicToggleButton.setChecked(false);

        // Positioning the button
        musicToggleButton.setPosition(140, viewport.getWorldHeight() - 140);

        // Add listener to handle toggle behavior
        musicToggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (musicToggleButton.isChecked()) {
                    backgroundMusic.pause();
                } else {
                    backgroundMusic.play();
                }
            }
        });


        stage.addActor(musicToggleButton);
    }

    @Override
    public void render (float delta) {
        if (!player.isAlive()) {
            restartButton.setVisible(true);
        }
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderBackground(delta);
        player.render(batch);
        enemy1.render(batch);
        enemy2.render(batch);
        enemy3.render(batch);
        coins.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        // Optionally draw collision bounding boxes
        shapeRenderer.end();
        stage.act(delta);
        stage.draw();
    }

    private void renderBackground(float deltaTime) {
        // Adjust background scrolling speed relative to deltaTime
        float[] bgLayerSpeeds = {0.25f, 0.5f, 1.0f};  // Adjust speeds for parallax effect
        float[] ptLayerSpeeds = {0.03125f, 0.0625f, 0.125f, 0.25f, 0.5f, 1.0f};
        for (int layer = 0; layer < backgrounds.length; layer++) {
            backgroundOffsets[layer] += deltaTime * backgroundMaxScrollingSpeed * bgLayerSpeeds[layer];
            backgroundOffsets[layer] %= backgrounds[layer].getWidth();  // Ensure the background wraps around
            batch.draw(backgrounds[layer], -backgroundOffsets[layer], 0);
            batch.draw(backgrounds[layer], -backgroundOffsets[layer] + backgrounds[layer].getWidth(), 0);
        }
        float viewportWidth = viewport.getWorldWidth();  // Get the current width of the viewport

        for (int layer = 0; layer < platforms.length; layer++) {
            platformOffsets[layer] += deltaTime * backgroundMaxScrollingSpeed * ptLayerSpeeds[layer];
            platformOffsets[layer] %= platforms[layer].getWidth();  // Use the platform's own width for wrapping

            // Render enough copies of the platform to cover the entire width of the viewport
            float x = -platformOffsets[layer];
            while (x < viewportWidth) {  // Use the viewport width dynamically
                batch.draw(platforms[layer], x, 0);  // Adjust Y position as necessary for your game design
                x += platforms[layer].getWidth();
            }
        }
    }

    public void update(float delta ) {
        if (player.isAlive()) {
            // Game logic
            restartButton.setVisible(false); // Ensure the button is hidden during normal gameplay
        } else {
            restartButton.setVisible(true); // Show the button when the player is not alive
        }

        if (!isPaused) {
            player.update();
            enemy1.update();
            enemy2.update();
            enemy3.update();
            coins.update();
            checkCollisions();
        }
        // I need to update the camera in case I made some changes.
        stage.act(delta);
        camera.update();
    }
    private void checkCollisions() {
        // Check collisions for each enemy with the player's projectiles
        checkProjectileCollision(this.player.projectile1);
        checkProjectileCollision(this.player.projectile2);
        checkProjectileCollision(this.player.projectile3);
        checkProjectileCollision(this.player.projectile4);
        checkProjectileCollision(this.player.projectile5);

        // Check collision with coins
        if (this.player.getBoundingBox().overlaps(this.coins.getBoundingBox())) {
            this.coins.handleCollision();
        }

        // Check enemy collisions with the player (if needed)
        checkEnemyCollision(this.enemy1);
        checkEnemyCollision(this.enemy2);
        checkEnemyCollision(this.enemy3);
    }

    private void checkProjectileCollision(Projectile projectile) {
        if (!projectile.isActive()) return; // Only check active projectiles

        if (projectile.getBoundingBox().overlaps(this.enemy1.getBoundingBox())) {
            this.enemy1.handleCollision();
            projectile.handleCollision(); // Deactivate the projectile on collision
        }
        if (projectile.getBoundingBox().overlaps(this.enemy2.getBoundingBox())) {
            this.enemy2.handleCollision();
            projectile.handleCollision();
        }
        if (projectile.getBoundingBox().overlaps(this.enemy3.getBoundingBox())) {
            this.enemy3.handleCollision();
            projectile.handleCollision();
        }
    }

    private void checkEnemyCollision(Enemy enemy) {
        if (this.player.getBoundingBox().overlaps(enemy.getBoundingBox())) {
            this.player.handleCollision();
            enemy.handleCollision();
        }
    }
    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void dispose () {
        batch.dispose();
        for (Texture bg : backgrounds) bg.dispose();
        for (Texture plat : platforms) plat.dispose();
        player.dispose();
        stage.dispose();
        if (restartButton.getStyle().imageUp instanceof TextureRegionDrawable) {
            Texture upTexture = ((TextureRegionDrawable) restartButton.getStyle().imageUp).getRegion().getTexture();
            if (upTexture != null) {
                upTexture.dispose();
            }
        }

        if (restartButton.getStyle().imageDown instanceof TextureRegionDrawable) {
            Texture downTexture = ((TextureRegionDrawable) restartButton.getStyle().imageDown).getRegion().getTexture();
            if (downTexture != null) {
                downTexture.dispose();
            }
        }
        backgroundMusic.dispose();

    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage); // Add the stage first to capture UI interactions
        multiplexer.addProcessor(player); // Then add the player to handle game inputs
        Gdx.input.setInputProcessor(multiplexer);
        backgroundMusic.play();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0); // Reset the camera position
    }

    @Override
    public void pause() {
        isPaused = true;
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
//        backgroundMusic.pause();
    }

    @Override
    public void resume() {
        isPaused = false;
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
//        backgroundMusic.play();
    }

    @Override
    public void hide() {
//        backgroundMusic.stop();
    }

}
