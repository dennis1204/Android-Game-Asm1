package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

//import sun.security.util.Debug;

public class MyGdxGame extends Game {
//	MainMenuScreen mainMenuScreen;
//	SpriteBatch batch;
//	Texture background;
//	Texture background1;
//	float x = 0;
//	float x1= 0;
//	float speed = 300;
//	Player player;
//
//	Enemy enemy1, enemy2, enemy3;
//	Coins coins;
//
//	OrthographicCamera camera;
//	Viewport viewport;
//
//	ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		System.out.println("MainMenuScreen set as current screen");
		this.setScreen(new MainMenuScreen(this));
		System.out.println("MainMenuScreen set as current screen");
//		this.batch = new SpriteBatch();
//		this.shapeRenderer = new ShapeRenderer();
//
//		this.background = new Texture("PNG/Backgrounds/04/Layer01.png");
//		this.background1 = new Texture("PNG/Backgrounds/04/Layer02.png");
//		this.player = new Player(this);
//		Gdx.input.setInputProcessor(this.player);
//
//		int screenWidth = Gdx.graphics.getWidth();
//		int screenHeight = Gdx.graphics.getHeight();
//
//		float screenRatio = 1000.0f / screenHeight;
//		this.camera = new OrthographicCamera();
//		this.camera.setToOrtho(false, screenWidth * screenRatio, screenHeight * screenRatio);
//		this.viewport = new FitViewport(screenWidth * screenRatio, screenHeight * screenRatio);
//
//		this.enemy1 = new Enemy(this, new Vector2(screenWidth + 100, 0), Enemy.Type.TYPE1);
//		this.enemy2 = new Enemy(this, new Vector2(screenWidth + 300, 200), Enemy.Type.TYPE2);
//		this.enemy3 = new Enemy(this, new Vector2(screenWidth + 500, 0), Enemy.Type.TYPE3);
//		this.coins = new Coins(this, new Vector2(screenWidth + 200, 300));

	}

	@Override
	public void render () {
		super.render();
//		this.update();
//
//		ScreenUtils.clear(0, 0, 0, 1);
//		this.batch.begin();
//		int screenWidth = Gdx.graphics.getWidth();
//
//		renderBackground(screenWidth);
//
//		this.player.render(this.batch);
//
//		this.enemy1.render(this.batch);
//		this.enemy2.render(this.batch);
//		this.enemy3.render(this.batch);
//
//		this.coins.render(this.batch);
//
//		this.batch.end();
//
//		this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//
////		this.shapeRenderer.setColor(Color.RED);
//
////		if (this.player.getBoundingBox() != null) {
////			this.shapeRenderer.rect(this.player.getBoundingBox().x, this.player.getBoundingBox().y,
////					this.player.getBoundingBox().width, this.player.getBoundingBox().height);
////		}
////
////		if (this.enemy.getBoundingBox() != null) {
////			this.shapeRenderer.rect(this.enemy.getBoundingBox().x, this.enemy.getBoundingBox().y,
////					this.enemy.getBoundingBox().width, this.enemy.getBoundingBox().height);
////		}
////		if (this.coins.getBoundingBox() != null) {
////			this.shapeRenderer.rect(this.coins.getBoundingBox().x, this.coins.getBoundingBox().y,
////					this.coins.getBoundingBox().width, this.coins.getBoundingBox().height);
////		}
//
//		this.shapeRenderer.end();
	}
//	private void renderBackground(int screenWidth) {
//		float backgroundX = this.x;
//		while (backgroundX + this.background.getWidth() < screenWidth * 2) {
//			this.batch.draw(this.background, backgroundX, 0);
//			backgroundX += this.background.getWidth();
//		}
//
//		backgroundX = this.x1;
//		while (backgroundX + this.background1.getWidth() < screenWidth * 2) {
//			this.batch.draw(this.background1, backgroundX, 0);
//			backgroundX += this.background1.getWidth();
//		}
//	}
	public void update() {
//		float dt = Gdx.graphics.getDeltaTime();
//
//		this.player.update();
//
//		this.enemy1.update();
//		this.enemy2.update();
//		this.enemy3.update();
//
//
//		this.coins.update();
//
//		// Move background
//		this.x -= this.speed * dt;
//		this.x1 -= (this.speed * 2) * dt;
//
//		checkCollisions();
//		// I need to update the camera in case I made some changes.
//		this.camera.update();
	}
//	private void checkCollisions() {
//		// Check collisions for each enemy with the player's projectiles
//		checkProjectileCollision(this.player.projectile1);
//		checkProjectileCollision(this.player.projectile2);
//		checkProjectileCollision(this.player.projectile3);
//		checkProjectileCollision(this.player.projectile4);
//		checkProjectileCollision(this.player.projectile5);
//
//		// Check collision with coins
//		if (this.player.getBoundingBox().overlaps(this.coins.getBoundingBox())) {
//			this.coins.handleCollision();
//		}
//
//		// Check enemy collisions with the player (if needed)
//		checkEnemyCollision(this.enemy1);
//		checkEnemyCollision(this.enemy2);
//		checkEnemyCollision(this.enemy3);
//	}

//	private void checkProjectileCollision(Projectile projectile) {
//		if (!projectile.isActive()) return; // Only check active projectiles
//
//		if (projectile.getBoundingBox().overlaps(this.enemy1.getBoundingBox())) {
//			this.enemy1.handleCollision();
//			projectile.handleCollision(); // Deactivate the projectile on collision
//		}
//		if (projectile.getBoundingBox().overlaps(this.enemy2.getBoundingBox())) {
//			this.enemy2.handleCollision();
//			projectile.handleCollision();
//		}
//		if (projectile.getBoundingBox().overlaps(this.enemy3.getBoundingBox())) {
//			this.enemy3.handleCollision();
//			projectile.handleCollision();
//		}
//	}

//	private void checkEnemyCollision(Enemy enemy) {
//		if (this.player.getBoundingBox().overlaps(enemy.getBoundingBox())) {
//			this.player.handleCollision();
//			enemy.handleCollision();
//		}
//	}
//	public float getX() {
//		return this.x;
//	}
//
//	public void setX(float x) {
//		this.x = x;
//	}

//	@Override
//	public void dispose () {
////		batch.dispose();
////		this.background.dispose();
////		this.background1.dispose();
////		this.player.dispose();
//	}
}
