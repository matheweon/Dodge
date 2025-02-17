//this class serves as the screen that appears when the player dies
// created by Rithesh Rajasekar

package com.mygdx.game.com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DodgeGame;
import com.mygdx.game.utilities.GameLevelManager;

import static com.mygdx.game.com.mygdx.game.screens.Level.currentLevelNumber;
import static com.mygdx.game.com.mygdx.game.screens.Level.isMuted;

public class GameOver implements Screen {
    private DodgeGame game;

    private Music music;

    private Texture levelSelectButton;
    private Texture retryButton;
    private Texture gameOverBackground;
    private static final int BUTTON_SIZE = 300;
    private static final int X_VALUE_RETRY_LEVEL = (int) (DodgeGame.WIDTH * 0.25) - BUTTON_SIZE / 2;
    private static final int X_VALUE_SELECT_LEVEL = (int) (DodgeGame.WIDTH * 0.75) - BUTTON_SIZE / 2;
    private static final int Y_VALUE_RETRY_LEVEL = 100;
    private static final int Y_VALUE_SELECT_LEVEL = 100;

    public static Sprite gameOverBackgroundSprite;
    public static String time;

    public GameOver(DodgeGame game) {
        this.game = game;
        levelSelectButton = new Texture("sprites/levelSelectButton.png");
        retryButton = new Texture("sprites/retryLevel.png");
        gameOverBackground = new Texture("sprites/gameOverBackground.png");
        gameOverBackgroundSprite = new Sprite(gameOverBackground);
        gameOverBackgroundSprite.scale(6);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/03 - Lose.mp3"));
        music.setLooping(false);
        music.setVolume(1f);
        if (!isMuted) {
            music.play();
        }
    }

    public void show() {

    }

    public void render(float delta) {
        //sets background
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        gameOverBackgroundSprite.setPosition(DodgeGame.WIDTH / 2 - gameOverBackgroundSprite.getWidth() / 2, DodgeGame.HEIGHT / 2 - gameOverBackgroundSprite.getHeight() / 2);
        gameOverBackgroundSprite.draw(game.batch);
        game.batch.draw(retryButton, X_VALUE_RETRY_LEVEL, Y_VALUE_RETRY_LEVEL, BUTTON_SIZE, BUTTON_SIZE);
        //sees if the retry button is clicked, and if it is, calls GameLevelManager.retry() which retries the level
        if (Gdx.input.getX() < X_VALUE_RETRY_LEVEL + BUTTON_SIZE &&
                Gdx.input.getX() > X_VALUE_RETRY_LEVEL &&
                DodgeGame.HEIGHT - Gdx.input.getY() > Y_VALUE_RETRY_LEVEL &&
                DodgeGame.HEIGHT - Gdx.input.getY() < Y_VALUE_RETRY_LEVEL + BUTTON_SIZE &&
                Gdx.input.justTouched() ||
                Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            music.stop();
            this.dispose();
            GameLevelManager.playLevel(game, currentLevelNumber);
        }
        game.batch.draw(levelSelectButton, X_VALUE_SELECT_LEVEL, Y_VALUE_SELECT_LEVEL, BUTTON_SIZE, BUTTON_SIZE);
        //sees is the level select button is selected.
        if (Gdx.input.getX() < X_VALUE_SELECT_LEVEL + BUTTON_SIZE &&
                Gdx.input.getX() > X_VALUE_SELECT_LEVEL &&
                DodgeGame.HEIGHT - Gdx.input.getY() > Y_VALUE_SELECT_LEVEL &&
                DodgeGame.HEIGHT - Gdx.input.getY() < Y_VALUE_SELECT_LEVEL + BUTTON_SIZE &&
                Gdx.input.justTouched() ||
                Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            music.stop();
            this.dispose();
            game.setScreen(new LevelSelect(game));
        }

        //displays time
        game.font.setColor(Color.BLUE);
        game.font.getData().setScale(4f);
        game.font.draw(game.batch, "Time: " + time, DodgeGame.WIDTH / 2 - 112, Y_VALUE_SELECT_LEVEL + BUTTON_SIZE / 2 - 35);

        game.batch.end();
    }

    public void resize(int width, int height) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void hide() {

    }

    public void dispose() {
        music.dispose();
    }
}
