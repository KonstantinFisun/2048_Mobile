package com.example.game_2048;

public class Board {
    private final int[][] board;
    private final int size;

    // Инициализирует поле и заполняет его нулями
    public Board(int size){
        this.size = size;
        board = new int[size][size];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                board[i][j] = 0;
            }
        }
    }

    // Возвращает состояние ячейки поля по координатам
    public int getState(int x, int y){
        return board[x][y];
    }

    // Изменяет состояние ячейки поля по координатам
    public void setState(int x, int y, int state){
        board[x][y] = state;
    }

    // Изменяет столбец под номером i
    public void setColumn(int i, int[] newColumn) {
        for(int j = 0; j < size; j++){
            board[j][i] = newColumn[j];
        }
    }

    // Возвращает массив состояний ячеек столбца под номером i
    public int[] getColumn(int i) {
        int[] res = new int[size];

        for(int j = 0; j < size; j++){
            res[j] = board[j][i];
        }

        return res;
    }

    // Изменяет строку под номером i
    public void setLine(int i, int[] newLine) {
        if (size >= 0) System.arraycopy(newLine, 0, board[i], 0, size);
    }

    // Возвращает массив состояний ячеек строки под номером i
    public int[] getLine(int i) {
        int[] res = new int[size];

        System.arraycopy(board[i], 0, res, 0, size);

        return res;
    }

    // Генерация нового числа
    public int generate_random_number_cell(){
        // Шанс выпадения цифры 4 : 1 к 4
        int number = (int) (Math.random() * 4);
        if (number == 3) {
            return 4;
        } else {
            return 2;
        }
    }

    // Проверка, что есть свободные клетки
    public boolean checkEmptyCells(){
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board[i][j] == 0) return true;
            }
        }
        return false;
    }

    // Вывод поля
    public void printBoard(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Проверяем, что больше ходов нет
     **/
    public boolean checkEnd(){
        //Проверяем, что есть пустые
        if(checkEmptyCells()){
            return false;
        }
        else{
            //Проверяем, что можно сдвинуть по горизонтали
            for(int i = 0; i < size; i++) {
                for (int j = 0; j < size-1; j++) {
                    if(board[i][j] == board[i][j+1]) return false;
                }
            }
            //Проверяем, что можно сдвинуть по строке
            for(int i = 0; i < size-1; i++) {
                for (int j = 0; j < size; j++) {
                    if(board[i][j] == board[i+1][j]) return false;
                }
            }
        }

        return true;
    }


}
