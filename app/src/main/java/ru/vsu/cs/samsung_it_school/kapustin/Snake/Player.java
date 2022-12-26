package ru.vsu.cs.samsung_it_school.kapustin.Snake;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;

    private int length;
    private int points;

    protected MovementVector direction;

    protected PlayerState playerState;

    private static final Map<MovementVector, MovementVector> oppositeVector;

    static {
        oppositeVector = new HashMap<>();
        oppositeVector.put(MovementVector.BOT, MovementVector.TOP);
        oppositeVector.put(MovementVector.TOP, MovementVector.BOT);
        oppositeVector.put(MovementVector.RIGHT, MovementVector.LEFT);
        oppositeVector.put(MovementVector.LEFT, MovementVector.RIGHT);
    }

    public Player(String name) {
        this.name = name;
        this.length = Settings.DEFAULT_SNAKE_LENGTH;
        this.direction = Settings.DEFAULT_DIRECTION;
    }

    public void addLength() {
        length++;
    }

    public void addPoints() {
        points++;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public MovementVector getDirection() {
        return direction;
    }

    public void setDirection(MovementVector direction) {
        if (!direction.equals(oppositeVector.get(this.direction))) {
            this.direction = direction;
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
}
