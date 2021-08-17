package ru.geekbrains.homework_4;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static char[][] chArr;
    public static final int fSize = 6; // Размер игрового поля, код игры предполагает использование широкого диапазона размеров полей
    public static final char xChar = 'X';
    public static final char oChar = 'O';
    public static final char emChar = '♦';
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        fillArr(); // Заполняем поле "пустыми" символами
        while (true) {
            userMove(); // Ход игрока
            printArr(); // Вывод игрового поля в консоль
            if (whoWin(xChar)) { // Проверка на заполненность крестиками
                System.out.println("Вы победили!");
                break;
            }
            if (!fField()) { // Проверка на заполненность поля
                System.out.println("Ничья!");
                break;
            }
            aiMove(); // Ход компьютера
            printArr(); // Вывод игрового поля в консоль
            if (whoWin(oChar)) { // Проверка на заполненность ноликами
                System.out.println("Компьютер победил!");
                break;
            }
            if (!fField()) { // Проверка на заполненность поля
                System.out.println("Ничья!");
                break;
            }
        }
    }

    public static void fillArr() { // Метод заполнения игрового поля "пустыми" символами
        chArr = new char[fSize][fSize];
        for (int i = 0; i < fSize; i++) {
            for (int j = 0; j < fSize; j++) {
                chArr[i][j] = emChar;
            }
        }
    }

    public static void printArr() { // Метод вывода игрового поля в консоль
        for (int i = 0; i < fSize + 1; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < fSize; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < fSize; j++) {
                System.out.print(chArr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean chEmpty(int x, int y) { // Метод проверки на доступность координат поля для ввода
        if (x < 0 || x >= fSize || y < 0 || y >= fSize)
            return false; // Координаты не могут выходить за пределы размеров поля
        return chArr[x][y] == emChar; // Наличие "пустого" символа по заданным координатам
    }

    public static void userMove() { // Метод хода пользователя
        int x, y;
        System.out.println();
        do {
            System.out.println("Сделайте ход в формате X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!chEmpty(x, y)); // Проверка на доступность координат
        chArr[x][y] = xChar;
    }

    public static void aiMove() { // Метод хода компьютера
        int x, y, r;
        System.out.println();
        System.out.println("Ход компьютера");
        if (fSize % 2 != 0 && chEmpty(fSize / 2, fSize / 2)) { // Если размеры поля не делятся без остатка на 2 и центр поля свободен, поставить туда ноль
            chArr[fSize / 2][fSize / 2] = oChar;
        } else if (aiCheck()) { // Проверка на выполняемость условий поведения ai и вызов метода ai на исполнение
        } else if (!aiCheck()) { // Если условия поведения ai не выполняются, символ ставится в случайное место поля
            r = rand.nextInt(3);
            switch (r) { // Ставить нолики в конечные ячейки поля, так как цикл for не охватывает конечные столбцы и строки
                case 0:
                    x = fSize - 1;
                    do {
                        y = rand.nextInt(fSize);
                    } while (!chEmpty(x, y));  // Проверка на доступность координат
                    chArr[x][y] = oChar;
                    break;
                case 1:
                    y = fSize - 1;
                    do {
                        x = rand.nextInt(fSize);
                    } while (!chEmpty(x, y));  // Проверка на доступность координат
                    chArr[x][y] = oChar;
                    break;
                case 2:
                    do {
                        x = rand.nextInt(fSize);
                        y = rand.nextInt(fSize);
                    } while (!chEmpty(x, y));  // Проверка на доступность координат
                    chArr[x][y] = oChar;
                    break;
            }


        }
    }

    public static boolean fField() { // Проверка поля на наличие "пустых" символов
        for (int i = 0; i < fSize; i++) {
            for (int j = 0; j < fSize; j++) {
                if (chArr[i][j] == emChar) return true;
            }
        }
        return false;
    }

    public static boolean whoWin(char chMove) { // Метод проверки на победу пользователя или компьютера
        int chCount1 = 0;
        int chCount2;
        int chCount3;
        int chCount4 = 0;
        int fiveDet; // Переменная для хранения значения 4 клеток подряд при размере поля 5х5
        if (fSize == 5) { // Для поля 5х5 выигрышной будет комбинация из четырёх символов
            fiveDet = 4;
        } else {
            fiveDet = fSize;
        }
        for (int i = 0; i < fSize; i++) {
            chCount2 = 0;
            chCount3 = 0;
            if (chArr[i][fSize - i - 1] == chMove) { // Проверка побочной диагонали на заполненность символами
                chCount1++;
                if (chCount1 == fiveDet) return true;
            }
            for (int j = 0; j < fSize; j++) {
                if (chArr[i][j] == chMove) { // Проверка строк на заполненность символами
                    chCount2++;
                    if (chCount2 == fiveDet) return true;
                }
                if (chArr[j][i] == chMove) { // Проверка столбцов на заполненность символами
                    chCount3++;
                    if (chCount3 == fiveDet) return true;
                }
                if (chArr[i][j] == chMove && i == j) { // Проверка главной диагонали на заполненность символами
                    chCount4++;
                    if (chCount4 == fiveDet) return true;
                }
            }
        }
        return false;
    }

    public static boolean aiCheck() {
        for (int i = 0; i < fSize - 1; i++) {
            for (int j = 0; j < fSize - 1; j++) {
                if (chArr[i][j] == xChar && chArr[i][j + 1] == xChar) { // Проверка строк на наличие от двух символов подряд и вставка нуля после или перед символами
                    if (chEmpty(i, j + 2)) {
                        chArr[i][j + 2] = oChar; // Блокировка продолжения линии символов после обнаруженных
                        return true;
                    } else if (chEmpty(i, j - 1)) {
                        chArr[i][j - 1] = oChar; // Блокировка продолжения линии символов перед обнаруженными
                        return true;
                    }
                } else if (chArr[j][i] == xChar && chArr[j + 1][i] == xChar) { // Проверка столбцов на наличие от двух символов подряд и вставка нуля после или перед символами
                    if (chEmpty(j + 2, i)) {
                        chArr[j + 2][i] = oChar; // Блокировка продолжения линии символов после обнаруженных
                        return true;
                    } else if (chEmpty(j - 1, i)) {
                        chArr[j - 1][i] = oChar; // Блокировка продолжения линии символов перед обнаруженными
                        return true;
                    }
                } else if (chArr[i][i] == xChar && chArr[i + 1][i + 1] == xChar) { // Проверка главной диагонали на наличие от двух символов подряд и вставка нуля после или перед символами
                    if (chEmpty(i + 2, i + 2)) {
                        chArr[i + 2][i + 2] = oChar; // Блокировка продолжения линии символов после обнаруженных
                        return true;
                    } else if (chEmpty(i - 1, i - 1)) {
                        chArr[i - 1][i - 1] = oChar; // Блокировка продолжения линии символов перед обнаруженными
                        return true;
                    }
                } else if (chArr[i][fSize - (i + 1)] == xChar && chArr[i + 1][fSize - (i + 2)] == xChar) { // Проверка побочной диагонали на наличие от двух символов подряд и вставка нуля после или перед символами
                    if (chEmpty(i + 2, fSize - (i + 3))) {
                        chArr[i + 2][fSize - (i + 3)] = oChar; // Блокировка продолжения линии символов после обнаруженных
                        return true;
                    } else if (chEmpty(i - 1, fSize - i)) {
                        chArr[i - 1][fSize - i] = oChar; // Блокировка продолжения линии символов перед обнаруженными
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
