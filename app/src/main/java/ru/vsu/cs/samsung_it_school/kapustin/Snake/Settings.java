package ru.vsu.cs.samsung_it_school.kapustin.Snake;

public class Settings {
    public static final MovementVector DEFAULT_DIRECTION = MovementVector.RIGHT;

    public static final int DEFAULT_SNAKE_LENGTH = 3;
    public static final int DEFAULT_FIELD_WIDTH = 13;
    public static final int DEFAULT_FIELD_HEIGHT = 22;
    public static final int DEFAULT_FIELD_SIZE = 10;

    public static int screenWidth;
    public static int screenHeight;
    public static int snakeLength;
    public static int fieldWidth;
    public static int fieldHeight;
    public static int fieldSize;

    public static MovementVector direction;

    public static MovementVector getDirection() {
        return direction == null? DEFAULT_DIRECTION : direction;
    }

    public static int getSnakeLength() {
        return snakeLength == 0? DEFAULT_SNAKE_LENGTH : snakeLength;
    }

    public static int getFieldWidth() {
        return fieldWidth == 0? DEFAULT_FIELD_WIDTH : fieldWidth;
    }

    public static int getFieldHeight() {
        return fieldHeight == 0? DEFAULT_FIELD_HEIGHT : fieldHeight;
    }

    public static int getFieldSize() {
        return fieldSize == 0? DEFAULT_FIELD_SIZE : fieldSize;
    }
}
