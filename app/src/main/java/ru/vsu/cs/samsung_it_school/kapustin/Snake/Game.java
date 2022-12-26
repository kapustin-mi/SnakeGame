package ru.vsu.cs.samsung_it_school.kapustin.Snake;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final Player player;

    private final Cell[][] gameField;

    private int headX;
    private int headY;

    private final Random rnd = new Random();

    public Game(Player player) {
        this.player = player;
        player.setPlayerState(PlayerState.PLAY);

        gameField = initField(Settings.getFieldHeight(), Settings.getFieldWidth(),
                Settings.getSnakeLength());

        addAppleToField();
    }

    private void addAppleToField() {
        List<Point> freePoints = new ArrayList<>();
        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[row].length; col++) {
                if (gameField[row][col].getCellState() == CellState.FREE) {
                    freePoints.add(new Point(col, row));
                }
            }
        }

        if (freePoints.size() == 0) {
            player.setPlayerState(PlayerState.WIN);
        }

        Point applePoint = freePoints.get(rnd.nextInt(freePoints.size()));
        gameField[applePoint.y][applePoint.x] = new Cell(CellState.APPLE);
    }

    private Cell[][] initField(int fieldHeight, int fieldWidth, int length) {
        headX = fieldWidth / 2;
        headY = fieldHeight / 2;

        Cell[][] field = new Cell[fieldHeight][fieldWidth];
        for (int r = 0; r < field.length; r++) {
            for (int c = 0; c < field[r].length; c++) {
                if (r == 0 || r == fieldHeight - 1  || c == 0 || c == fieldWidth - 1) {
                    field[r][c] = new Cell(CellState.WALL);
                } else {
                    field[r][c] = new Cell(CellState.FREE);
                }
            }
        }

        initSnake(field, length);

        return field;
    }

    private void initSnake(Cell[][] field, int snakeLength) {
        int curColIndex = headX;
        for (int curLength = snakeLength; curLength >= 1; curLength--) {
            field[headY][curColIndex] = new Cell(CellState.SNAKE, curLength);
            curColIndex--;
        }
    }



    public void move() {
        if (player.getPlayerState().equals(PlayerState.PLAY)) {
            int newHeadX = headX, newHeadY = headY;

            switch (player.direction) {
                case RIGHT:
                    newHeadX++;
                    break;
                case BOT:
                    newHeadY--;
                    break;
                case TOP:
                    newHeadY++;
                    break;
                case LEFT:
                    newHeadX--;
                    break;
            }

            Cell newHeadCell = gameField[newHeadY][newHeadX];
            if (newHeadCell.getCellState() == CellState.SNAKE || newHeadCell.getCellState() == CellState.WALL) {
                player.setPlayerState(PlayerState.LOSS);
            }

            if (newHeadCell.getCellState() == CellState.APPLE) {
                player.addLength();
                gameField[newHeadY][newHeadX] = new Cell(CellState.SNAKE, player.getLength());
                addAppleToField();
            } else {
                removeTail();

                gameField[newHeadY][newHeadX] = new Cell(CellState.SNAKE, player.getLength());
            }
            headX = newHeadX;
            headY = newHeadY;
        }
    }

    private void removeTail() {
        for (Cell[] cells : gameField) {
            for (Cell cell : cells) {
                if (cell.getCellState() == CellState.SNAKE) {
                    cell.minimize();
                }
            }
        }
    }

    public Cell[][] getGameField() {
        return gameField;
    }
}
