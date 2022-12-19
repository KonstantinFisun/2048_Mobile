package com.example.game_2048;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Game extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private int count; // Размер поля

    private int score; //Сумма всех чисел на поле
    private boolean endOfGame; //Флаг для завершения основного цикла программы
    private boolean is2048; //Хранит информацию о том, удалось ли игроку создать плитку 2048 (флаг победы)
    private Board board; // Игровое поле
    private GestureDetectorCompat gd;
    private boolean firstGeneration;

    DBHelper dbHelper; // БД

    // Для жестов
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    GridLayout gridLayout; // Грид с полем
    Cell[][] cells; // Клетки поля
    TextView scoreView; // Текста окна со счетом
    TextView recordView; // Текста окна с рекордом


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridLayout = findViewById(R.id.grid); // Грид с полем
        scoreView = findViewById(R.id.score_number); // Текст со счетом
        recordView = findViewById(R.id.record_number); // Текст с рекордом


        count = 4; // Размер поля
        score = 0; // Счет
        endOfGame = false;
        is2048 = false; // Достигли 2048
        board = new Board(count); // Игровое поле

        // Создание БД
        dbHelper = new DBHelper(this);

        // Детектор жестов
        gd = new GestureDetectorCompat(this, this);
        gd.setIsLongpressEnabled(true);

        // количество строк
        gridLayout.setRowCount(count);
        // количество столбцов
        gridLayout.setColumnCount(count);


        // Создание ячеек
        cells = new Cell[count][count];;

        for(int i = 0; i < count; i++){
            for(int j = 0; j < count; j++){
                cells[i][j] = new Cell(this);

                //Размер
                cells[i][j].setWidth(470 / count * 2);
                cells[i][j].setHeight(470 / count * 2);

                //Добавление
                gridLayout.addView(cells[i][j]);
            }
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, "score");

        if(cursor!=null){
            if(cursor.moveToLast()  ){
                String str = cursor.getString(1);
                recordView.setText(str);
            }else{
                recordView.setText("Ещё нету");
            }
            cursor.close();
        }

        createInitialCells(); // Генерация первых клеток
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //Раскраска ячейки
    public void drawCell(Board board){
        for(int i = 0; i < count; i++){
            for(int j = 0; j < count; j++){
                cells[i][j].draw(board.getState(i,j));
            }
        }
    }

    // Создаём на поле начальные ячейки
    private void createInitialCells() {
        firstGeneration = true;
        for (int i = 0; i < count / 2; i++) {
            generateNewCell();
        }
        drawCell(board); // Отрисовка поля
    }

    // Генерация случайной клетки на поле
    private void generateNewCell() {


        //Проверяем, что есть свободные места
        if (!board.checkEmptyCells() && !firstGeneration) {
            System.err.println("Невозможно генерация случайной клетки.");
            System.exit(-1);
        }
        firstGeneration = false;
        int num; // Новая клетка
        // Цикл пока не найдем свободную клетку
        while (true) {
            // Выбираем случайное положение
            int randomX = (int) (Math.random() * count);
            int randomY = (int) (Math.random() * count);

            // Если клетка свободна, то ок
            if (board.getState(randomX, randomY) == 0) {
                num = board.generate_random_number_cell(); // Генерация нового числа
                score += num; // Обновляем счет
                scoreView.setText(String.valueOf(score));
                board.setState(randomX, randomY, num);
                break;
            }
        }

    }

    /**
     * Основная логика игры.
     * Если пользователь определил направление, вызывает метод сдвига.
     * Если сдвиг удался, создаёт новую плитку.
     */
    private void logic(Direction direction) {
        if (direction != Direction.AWAITING && direction != null) {

            if (shift(direction)) generateNewCell();

            if(board.checkEnd() && !endOfGame){
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                Toast toast = Toast.makeText(getApplicationContext(),
                        String.format("Игра окончена! Ваш счет : %s", score), Toast.LENGTH_SHORT);
                toast.show();
                endOfGame = true;

                contentValues.put(DBHelper.KEY_SCORE, score);

                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
            }

            drawCell(board); // Отрисовка поля
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            logic(Direction.LEFT);
            return false; // справа налево
        }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            logic(Direction.RIGHT);
            return false; // слева направо
        }

        if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            logic(Direction.DOWN);
            return false; // снизу вверх
        }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            logic(Direction.UP);
            return false; // сверху вниз
        }
        return false;
    }

    /**
     * Результат работы метода сдвига shiftRow().
     * Содержит изменённую строку и информацию о том, эквивалентна ли она начальной.
     */
    private static class ShiftRowResult {
        boolean didAnythingMove; // Эквивалентна ли она начальной
        int[] shiftedRow; // Измененная строка
    }

    /**
     * Изменяет board, сдвигая все ячейки в указанном направлении,
     * вызывая shiftRow() для каждой строки/столбца (в зависимости от направления) **/
    private boolean shift(Direction direction) {
        boolean ret = false;
        switch (direction) {
            case DOWN:
            case UP:

                // По очереди сдвигаем числа всех столбцов в нужном направлении
                for (int i = 0; i < count; i++) {
                    // Запрашиваем очередной столбец
                    int[] arg = board.getColumn(i);

                    // В зависимости от направления сдвига, меняем или не меняем порядок чисел на противоположный
                    if (direction == Direction.UP) {
                        int[] tmp = new int[arg.length];
                        for (int e = 0; e < tmp.length; e++) {
                            tmp[e] = arg[tmp.length - e - 1];
                        }
                        arg = tmp;
                    }

                    // Пытаемся сдвинуть числа в этом столбце
                    ShiftRowResult result = shiftRow(arg);

                    // Возвращаем линию в исходный порядок*
                    if (direction == Direction.UP) {
                        int[] tmp = new int[result.shiftedRow.length];
                        for (int e = 0; e < tmp.length; e++) {
                            tmp[e] = result.shiftedRow[tmp.length - e - 1];
                        }
                        result.shiftedRow = tmp;
                    }

                    // Записываем изменённый столбец
                    board.setColumn(i, result.shiftedRow);

                    //Если хоть одна линия была изменена, значит было изменено всё поле
                    ret = ret || result.didAnythingMove;
                }
                break;
            case RIGHT:

            case LEFT:

                // По очереди сдвигаем числа всех строк в нужном направлении
                for (int i = 0; i < count; i++) {
                    // Запрашиваем очередную строку
                    int[] arg = board.getLine(i);

                    // В зависимости от направления сдвига, меняем или не меняем порядок чисел на противоположный
                    if (direction == Direction.RIGHT) {
                        int[] tmp = new int[arg.length];
                        for (int e = 0; e < tmp.length; e++) {
                            tmp[e] = arg[tmp.length - e - 1];
                        }
                        arg = tmp;
                    }

                    // Пытаемся сдвинуть числа в этом столбце
                    ShiftRowResult result = shiftRow(arg);

                    // Возвращаем линию в исходный порядок
                    if (direction == Direction.RIGHT) {
                        int[] tmp = new int[result.shiftedRow.length];
                        for (int e = 0; e < tmp.length; e++) {
                            tmp[e] = result.shiftedRow[tmp.length - e - 1];
                        }
                        result.shiftedRow = tmp;
                    }

                    // Записываем изменённую строку
                    board.setLine(i, result.shiftedRow);

                    /*Если хоть одна линия была изменена, значит было изменено всё поле*/
                    ret = ret || result.didAnythingMove;
                }

                break;
            default:
                System.err.println("Невозможно сдвинуть клетки. Возможно неверные параметры.");
                System.exit(-2);
                break;
        }

        return ret;
    }

    /**
     * Получаем изменения при сдвиги
     **/
    private ShiftRowResult shiftRow(int[] oldRow) {
        ShiftRowResult ret = new ShiftRowResult();

        // Выкидываем все нули
        int[] oldRowWithoutZeroes = new int[oldRow.length];

        int q = 0; // Счетчик количества занятых клетов в строке

        for (int i = 0; i < oldRow.length; i++) {
            if (oldRow[i] != 0) {
                if (q != i) {
                    /*
                     * Это значит, что мы передвинули ячейку
                     * на место какого-то нуля (пустой плитки)
                     */
                    ret.didAnythingMove = true;
                }

                oldRowWithoutZeroes[q] = oldRow[i];
                q++;
            }
        }


        /*
        Если с ним можно совместить следующее за ним число (наш указатель +1), то переписываем в новый массив лишь их сумму,
        затем ставим указатель на третье число (второго уже нет).
        Иначе переписываем только первое, и ставим указатель на второе число.
         */

        // Массив с изменненной строкой
        ret.shiftedRow = new int[oldRowWithoutZeroes.length];


        q = 0;
        int i = 0;
        // Идем пока есть числа
        while (i < oldRowWithoutZeroes.length) {
            //Если следующие число совпадает
            if ((i + 1 < oldRowWithoutZeroes.length) && (oldRowWithoutZeroes[i] == oldRowWithoutZeroes[i + 1])
                    && oldRowWithoutZeroes[i] != 0) {
                ret.didAnythingMove = true; // Фиксируем что было изменение
                ret.shiftedRow[q] = oldRowWithoutZeroes[i] * 2; // Присваиваем число в два раза больше
                if(ret.shiftedRow[q] == 2048){
                    is2048 = true;
                }
                i++; // Следующие число не смотрим
            } else {
                ret.shiftedRow[q] = oldRowWithoutZeroes[i];
            }

            q++;
            i++;
        }

        return ret;
    }



}