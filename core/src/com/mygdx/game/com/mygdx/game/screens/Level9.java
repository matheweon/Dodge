// this class serves as the screen which is level9
// created by Rithik Rajasekar, Matt Seng, and Zak Asis


package com.mygdx.game.com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.DodgeGame;
import com.mygdx.game.entities.Boomerang;
import com.mygdx.game.entities.Boulder;
import com.mygdx.game.entities.Cannon;


public class Level9 extends Level implements Screen {
    public Level9(DodgeGame g) {
        currentLevelNumber = 9;
        game = g;
        levelTexture = new Texture("sprites/dodgeJungleBG.png");
        coins = 0;
        world = "JUNGLE";
        level = " LEVEL 9";

        Boulder.min = 2;
        Boulder.max = 4;
        Boulder.spawnInterval = 4;
        Boulder.spawnDelay = 2;

        Cannon.min = 2;
        Cannon.max = 4;
        Cannon.spawnInterval = 5;
        Cannon.spawnDelay = 2.5;

        Boomerang.min = 3;
        Boomerang.max = 5;
        Boomerang.spawnInterval = 4;
        Boomerang.spawnDelay = 3;

        playMusic("music/07 - Jungle World.mp3");
        createPlayer();
    }

    public void render(float delta) {
        displayBackground(levelTexture);

        //don't mess around with the order of the display methods unless you know what you're doing
        //it will cause a "SpriteBatch.begin must be called before draw" or "SpriteBatch music be called before end" error
        //for some reason displayTimer must be called first
        displayTimer(delta);
        displayWorldAndLevel();
        drawGrid();
        displayCoinCounter();
        displayInvincibilityBar();

        detectCollision();
        detectCoin();

        spawnEntities();
        renderEntities(delta);

        game.batch.end();
    }

    public void spawnEntities() {
        spawnCoins();
        spawnBoulders();
        spawnCannon();
        spawnBoomerang();
    }

    public void renderEntities(float delta) {
        renderPlayer(delta);
        renderCoins();
        renderBoulders(delta);
        renderCannons(delta);
        renderBoomerang(delta);
    }

    public void detectCollision() {
        detectBoulderCollision();
        detectCannonCollision();
        detectBoomerangCollision();
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
        super.dispose();
    }
}
