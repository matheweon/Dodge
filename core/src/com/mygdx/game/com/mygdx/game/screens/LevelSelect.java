package com.mygdx.game.com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DodgeGame;
import com.mygdx.game.utilities.FileStreaming;
import com.mygdx.game.utilities.GameLevelManager;

import static com.mygdx.game.com.mygdx.game.screens.Level.NUM_LEVELS;

public class LevelSelect implements Screen {
    private DodgeGame game;
    private Music music;

    private final Texture grass = new Texture("sprites/dodgeGrassBGS.png");
    private final Texture sand = new Texture("sprites/dodgeDesertBGS.png");
    private final Texture jungle = new Texture("sprites/dodgeJungleBGS.png");
    private final Texture hell = new Texture("sprites/dodgeHellBGS.png");
    private final Texture homeButton = new Texture("sprites/homeButton.png");
    private final Texture zeroStar = new Texture("sprites/dodge0Star.png");
    ;
    private final Texture oneStar = new Texture("sprites/dodge1Star.png");
    private final Texture twoStar = new Texture("sprites/dodge2Star.png");
    private final Texture threeStar = new Texture("sprites/dodge3Star.png");
    private final Texture[] buttons = new Texture[NUM_LEVELS];

    private final Sprite grassBackground = new Sprite(grass);
    private final Sprite sandBackground = new Sprite(sand);
    private final Sprite jungleBackground = new Sprite(jungle);
    private final Sprite hellBackground = new Sprite(hell);

    public static final int X_VALUE_LEFTMOST = 100;
    public static final int X_VALUE_LEFT = 430;
    public static final int X_VALUE_RIGHT = 750;
    public static final int X_VALUE_RIGHTMOST = 1070;
    public static final int Y_VALUE_TOP = 600;
    public static final int Y_VALUE_MIDDLE = 350;
    public static final int Y_VALUE_BOTTOM = 100;
    public static final int X_VALUE_HOME_BUTTON = 1180;
    public static final int Y_VALUE_HOME_BUTTON = 620;
    public static final int BUTTON_SIZE = 100;
    public static final int HOME_BUTTON_SIZE = 100;
    public static final int[][] BUTTON_COORDS = {
            {X_VALUE_LEFTMOST, Y_VALUE_TOP},
            {X_VALUE_LEFTMOST, Y_VALUE_MIDDLE},
            {X_VALUE_LEFTMOST, Y_VALUE_BOTTOM},
            {X_VALUE_LEFT, Y_VALUE_TOP},
            {X_VALUE_LEFT, Y_VALUE_MIDDLE},
            {X_VALUE_LEFT, Y_VALUE_BOTTOM},
            {X_VALUE_RIGHT, Y_VALUE_TOP},
            {X_VALUE_RIGHT, Y_VALUE_MIDDLE},
            {X_VALUE_RIGHT, Y_VALUE_BOTTOM},
            {X_VALUE_RIGHTMOST, Y_VALUE_TOP},
            {X_VALUE_RIGHTMOST, Y_VALUE_MIDDLE},
            {X_VALUE_RIGHTMOST, Y_VALUE_BOTTOM},
    };

    public LevelSelect(DodgeGame game) {
        this.game = game;
        //assigns button textures
        for (int i = 0; i < NUM_LEVELS; i++) {
            buttons[i] = new Texture("sprites/buttonNum" + (i + 1) + ".png");
        }

        grassBackground.scale(7);
        sandBackground.scale(7);
        jungleBackground.scale(7);
        hellBackground.scale(7);

        music = Gdx.audio.newMusic(Gdx.files.internal("music/01 - Menu.mp3"));
        music.setLooping(true);
        music.setVolume(1f);
        music.play();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        //displays backgrounds
        grassBackground.setPosition(DodgeGame.WIDTH / 8, DodgeGame.HEIGHT / 2 - grassBackground.getHeight() / 2);
        grassBackground.draw(game.batch);
        sandBackground.setPosition(DodgeGame.WIDTH / 8 + 320, DodgeGame.HEIGHT / 2 - grassBackground.getHeight() / 2);
        sandBackground.draw(game.batch);
        jungleBackground.setPosition(DodgeGame.WIDTH / 8 + 320 + 320, DodgeGame.HEIGHT / 2 - grassBackground.getHeight() / 2);
        jungleBackground.draw(game.batch);
        hellBackground.setPosition(DodgeGame.WIDTH / 8 + 320 + 320 + 320, DodgeGame.HEIGHT / 2 - grassBackground.getHeight() / 2);
        hellBackground.draw(game.batch);

        //draws buttons
        for (int i = 0; i < NUM_LEVELS; i++) {
            game.batch.draw(buttons[i], BUTTON_COORDS[i][0], BUTTON_COORDS[i][1], BUTTON_SIZE, BUTTON_SIZE);
        }

        //checks button clicks and begins the corresponding level
        for (int i = 0; i < NUM_LEVELS; i++) {
            if (FileStreaming.unlockedLevel >= i + 1 &&
                    Gdx.input.getX() > BUTTON_COORDS[i][0] &&
                    Gdx.input.getX() < BUTTON_COORDS[i][0] + BUTTON_SIZE &&
                    DodgeGame.HEIGHT - Gdx.input.getY() > BUTTON_COORDS[i][1] &&
                    DodgeGame.HEIGHT - Gdx.input.getY() < BUTTON_COORDS[i][1] + BUTTON_SIZE) {
                if (Gdx.input.isTouched()) {
                    this.dispose();
                    GameLevelManager.playLevel(game, i + 1);
                }
            }
        }

        //draws home button
        game.batch.draw(homeButton, X_VALUE_HOME_BUTTON, Y_VALUE_HOME_BUTTON, HOME_BUTTON_SIZE, HOME_BUTTON_SIZE);

        //checks home button click
        if (Gdx.input.getX() > X_VALUE_HOME_BUTTON &&
                Gdx.input.getX() < X_VALUE_HOME_BUTTON + HOME_BUTTON_SIZE &&
                DodgeGame.HEIGHT - Gdx.input.getY() > Y_VALUE_HOME_BUTTON &&
                DodgeGame.HEIGHT - Gdx.input.getY() < Y_VALUE_HOME_BUTTON + HOME_BUTTON_SIZE) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new Start(game));
            }
        }

        game.batch.end();
        displayStars();// calls displayStars which displays the stars!
    }

    public void displayStars() {
        game.batch.begin();

        for (int i = 0; i < NUM_LEVELS; i++) {
            Texture starTexture = zeroStar;
            switch (FileStreaming.stars[i]) {
                case 1:
                    starTexture = oneStar;
                    break;
                case 2:
                    starTexture = twoStar;
                    break;
                case 3:
                    starTexture = threeStar;
                    break;
            }
            game.batch.draw(starTexture, BUTTON_COORDS[i][0] - 56, BUTTON_COORDS[i][1] - 100, 224, 70);
        }

        game.batch.end();
    }

    public void show() {

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
        grass.dispose();
        sand.dispose();
        jungle.dispose();
        hell.dispose();
        homeButton.dispose();
        for (int i = 0; i < NUM_LEVELS; i++) {
            buttons[i].dispose();
        }
        music.dispose();
    }
}