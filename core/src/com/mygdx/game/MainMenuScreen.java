package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Game game;

    public MainMenuScreen(Game game) {
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Set background color
        Gdx.gl.glClearColor(0, 0, 0, 1); // Black background

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        // Title
        BitmapFont titleFont = new BitmapFont();
        titleFont.getData().setScale(2.0f);
        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.WHITE);
        Label titleLabel = new Label("My Game Title", titleStyle);
        table.add(titleLabel).padBottom(50).row();

        // Load textures for buttons
        Texture upTexture = new Texture(Gdx.files.internal("PNG/User Interface/PlayBtn.png"));
        Texture downTexture = new Texture(Gdx.files.internal("PNG/User Interface/PlayBtn.png"));
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(upTexture)), new TextureRegionDrawable(new TextureRegion(downTexture)));
        table.add(playButton).size(300, 100).padBottom(20).row();

        Texture exitUpTexture = new Texture(Gdx.files.internal("PNG/User Interface/CloseBtn.png"));
        Texture exitDownTexture = new Texture(Gdx.files.internal("PNG/User Interface/CloseBtn.png"));
        ImageButton exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitUpTexture)), new TextureRegionDrawable(new TextureRegion(exitDownTexture)));
        table.add(exitButton).size(300, 100).padTop(20);

        // Add listeners to buttons
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen((MyGdxGame) game)); // Switch to GameScreen
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
