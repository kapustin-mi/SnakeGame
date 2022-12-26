package ru.vsu.cs.samsung_it_school.kapustin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import ru.vsu.cs.samsung_it_school.kapustin.Snake.Cell;
import ru.vsu.cs.samsung_it_school.kapustin.Snake.CellState;
import ru.vsu.cs.samsung_it_school.kapustin.Snake.Game;
import ru.vsu.cs.samsung_it_school.kapustin.Snake.MovementVector;
import ru.vsu.cs.samsung_it_school.kapustin.Snake.Player;
import ru.vsu.cs.samsung_it_school.kapustin.Snake.Settings;

public class GameView extends View {
    private Bitmap appleBitmap;
    private Bitmap grass;
    private Bitmap snakeBody;
    private Bitmap snakeHead;
    private Bitmap snakeAngle;

    private Player player;

    private Game game;

    private Cell[][] gameField;

    private int cellSize;

    private Paint paint;

    private Timer timer;

    private double clickX;
    private double clickY;
    private double diffX;
    private double diffY;

    private final int HOLDING_LENGTH = 100;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();

        startGame();

        appleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
        appleBitmap = Bitmap.createScaledBitmap(appleBitmap, cellSize, cellSize, true);

        grass = BitmapFactory.decodeResource(getResources(), R.drawable.grass2);
        grass = Bitmap.createScaledBitmap(grass, cellSize, cellSize, true);

        snakeBody = BitmapFactory.decodeResource(getResources(), R.drawable.snake_body);
        snakeBody = Bitmap.createScaledBitmap(snakeBody, cellSize, cellSize, true);

        snakeHead = BitmapFactory.decodeResource(getResources(), R.drawable.head_snake);
        snakeHead = Bitmap.createScaledBitmap(snakeHead, cellSize, cellSize, true);

        snakeAngle = BitmapFactory.decodeResource(getResources(), R.drawable.snake_angle);
        snakeAngle = Bitmap.createScaledBitmap(snakeAngle, cellSize, cellSize, true);

    }

    private void startGame() {
        player = new Player("Вася");

        game = new Game(player);
        gameField = game.getGameField();

        if (Settings.screenHeight > Settings.screenWidth) {
            cellSize = Settings.screenWidth / gameField[0].length;
        } else {
            cellSize = Settings.screenHeight / gameField.length;
        }

        timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                game.move();
                invalidate();
            }
        }, 0, 250);
    }

    public void draw(Canvas canvas) {
        int curX, curY;

        CellState curCellState;
        for (int i = 0; i < gameField.length; i++) {
            curY = cellSize * i;
            for (int j = 0; j < gameField[i].length; j++) {
                curX = cellSize * j;

                curCellState = gameField[i][j].getCellState();
                switch (curCellState) {
                    case WALL:
                        paint.setColor(getResources().getColor(R.color.wallColor));
                        canvas.drawRect(new Rect(curX, curY, curX + cellSize, curY + cellSize), paint);
                        break;
                    case SNAKE:
                        //определить, какой элемент нужен, повернуть его и отрисовать
                        break;
                    case APPLE:
                        drawBitmapOnGrass(appleBitmap, curX, curY, canvas);
                        break;
                    case FREE:
                        canvas.drawBitmap(grass, curX, curY, null);
                        break;
                }
            }
        }

        super.draw(canvas);
    }

    // можно дважды быстро изменить направление и получится, что направление змейки будет противо-
    // положным его текущего направления, игрок проиграет, хотя не должен

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                clickX = event.getX();
                clickY = event.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                diffX = event.getX() - clickX;
                diffY = event.getY() - clickY;

                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (diffX > HOLDING_LENGTH) {
                        player.setDirection(MovementVector.RIGHT);
                    }

                    if (diffX < -HOLDING_LENGTH) {
                        player.setDirection(MovementVector.LEFT);
                    }
                } else if (Math.abs(diffX) < Math.abs(diffY)) {
                    if (diffY > HOLDING_LENGTH) {
                        player.setDirection(MovementVector.TOP);
                    }

                    if (diffY < -HOLDING_LENGTH) {
                        player.setDirection(MovementVector.BOT);
                    }
                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                clickX = 0;
                clickY = 0;
                break;
            }
        }

        return true;
    }

    private Bitmap rotateBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, cellSize, cellSize, true);

        return Bitmap.createBitmap(scaledBitmap, 0, 0, cellSize, cellSize, matrix, true);
    }

    private void drawBitmapOnGrass(Bitmap bitmap, int x, int y, Canvas canvas) {
        canvas.drawBitmap(grass, x, y, null);
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
