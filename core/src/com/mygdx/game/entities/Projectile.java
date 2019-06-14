package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.game.com.mygdx.game.screens.Level1.*;

public class Projectile extends Entity {
    //speed can not go above 1500 because that would be moving more than one tile per frame; it'll still work but is just not ideal
    public int speed;
    public float elapsedTime;
    public boolean isOnScreen;
    Animation projectileAnimation;
    public boolean spawned;
    public String type;
    public float waitTime;

    //x and y offset here are used to animate the movement of the projectile
    public int xOffset, yOffset;
    public float rotation;

    private boolean acrossScreen;

    public Projectile(String t, int dx, int dy, String d, int s, Animation a, float w) {
        isOnScreen = true;

        type = t;
        x = dx;
        y = dy;
        direction = d;
        speed = s;
        projectileAnimation = a;
        waitTime = w;

        //rotates projectile
        if (direction == "UP") {
            rotation = 0f;
        }
        if (direction == "LEFT") {
            rotation = 90f;
        }
        if (direction == "DOWN") {
            rotation = 180f;
        }
        if (direction == "RIGHT") {
            rotation = 270f;
        }
    }

    public static Animation createAnimation(String path, int width, int height, int rows, int cols, int frames) {
        int numTextures = rows * cols;

        Texture projectileTexture = new Texture(path);
        TextureRegion[] projectileAnimationFrames;
        TextureRegion[][] tmpFrames = TextureRegion.split(projectileTexture, width, height);
        projectileAnimationFrames = new TextureRegion[numTextures];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                projectileAnimationFrames[index++] = tmpFrames[i][j];
            }
        }
        return new Animation(1f / frames, projectileAnimationFrames);
    }

    //changes the entity x coordinates on its direction which is right
    public void rightBehavior(float deltaTime) {
        xOffset += speed * deltaTime;
        if (xOffset > PLAYER_MOVE_DISTANCE / 2) {
            xOffset -= PLAYER_MOVE_DISTANCE;
            x++;
        }
        if (x > 8) // checks if the projectile has moved across the screen, and if it has, sets isOnScreen to false
            isOnScreen = false;
    }
    //changes the entity x coordinates on its direction which is left
    public void leftBehavior(float deltaTime) {
        xOffset -= speed * deltaTime;
        if (-xOffset > PLAYER_MOVE_DISTANCE / 2) {
            xOffset += PLAYER_MOVE_DISTANCE;
            x--;
        }
        if (x < -1) // checks if the projectile has moved across the screen, and if it has, sets isOnScreen to false
            isOnScreen = false;
    }
    // changes the entity y coordinates based on its direction which is up
    public void upBehavior(float deltaTime) {
        yOffset += speed * deltaTime;
        if (yOffset > PLAYER_MOVE_DISTANCE / 2) {
            yOffset -= PLAYER_MOVE_DISTANCE;
            y++;
        }
        if (y > 8) // checks if the projectile has moved across the screen, and if it has, sets isOnScreen to false
            isOnScreen = false;
    }
    // changes the entity y coordinates based on its direction which is down
    public void downBehavior(float deltaTime) {
        yOffset -= speed * deltaTime;
        if (-yOffset > PLAYER_MOVE_DISTANCE / 2) {
            yOffset += PLAYER_MOVE_DISTANCE;
            y--;
        }
        if (y < -1) // checks if the projectile has moved across the screen, and if it has, sets isOnScreen to false
            isOnScreen = false;
    }

    // updates pos of entity based on direction which is then configured to the different behavior methods
    public void update(float deltaTime) {
        elapsedTime += deltaTime;
        //because a boomerang has to come across the screen and then back, this update method does exactly just that by using the across screen variable
        if (type == "Boomerang") {
            double pos;

            //smooth movement here function using a parabola
            pos = -8 * Math.pow(elapsedTime - 1, 2) + 7;

            int roundedPos = (int) Math.round(pos) + 1;
            if (direction == "RIGHT") {
                x = roundedPos - 1;
                xOffset = (int) ((pos - x) * PLAYER_MOVE_DISTANCE);
            } else if (direction == "UP") {
                y = roundedPos - 1;
                yOffset = (int) ((pos - y) * PLAYER_MOVE_DISTANCE);
            } else if (direction == "LEFT") {
                x = 8 - roundedPos;
                xOffset = (int) -((pos - roundedPos) * PLAYER_MOVE_DISTANCE) - PLAYER_MOVE_DISTANCE;
            } else if (direction == "DOWN") {
                y = 8 - roundedPos;
                yOffset = (int) -((pos - roundedPos) * PLAYER_MOVE_DISTANCE) - PLAYER_MOVE_DISTANCE;
            }

            //if the boomerang leaves the screen
            if (x < -1 || x > 8 || y < -1 || y > 8)
                isOnScreen = false;
        }

        else {
            if (direction == "RIGHT") {
                rightBehavior(deltaTime);
            } else if (direction == "UP") {
                upBehavior(deltaTime);
            } else if (direction == "LEFT") {
                leftBehavior(deltaTime);
            } else if (direction == "DOWN") {
                downBehavior(deltaTime);
            }
        }
    }

    public void wait(float deltaTime) {
        if (waitTime > 0)
            waitTime -= deltaTime;
        if (waitTime < 0)
            waitTime = 0;
    }

    public void render(SpriteBatch batch) {
        batch.draw(projectileAnimation.getKeyFrame(elapsedTime, true), xCoordToPixel(x) + xOffset, yCoordToPixel(y) + yOffset, PLAYER_WIDTH / 2, PLAYER_WIDTH / 2, PLAYER_WIDTH, PLAYER_WIDTH, 1, 1, rotation);
    }
}