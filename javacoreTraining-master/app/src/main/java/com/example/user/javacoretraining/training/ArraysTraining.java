package com.example.user.javacoretraining.training;

/**
 * Набор тренингов по работе с массивами в java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see ArraysTrainingTest.
 */
public class ArraysTraining {

    /**
     * Метод должен сортировать входящий массив
     * по возрастранию пузырьковым методом
     *
     * @param valuesArray массив для сортировки
     * @return отсортированный массив
     */
    public int[] sort(int[] valuesArray) {

        for (int i = 0; i < valuesArray.length; i++) {
            for (int j = 1; j < (valuesArray.length - i); j++) {
                if (valuesArray[j - 1] > valuesArray[j]) {
                    int temp = valuesArray[j - 1];
                    valuesArray[j - 1] = valuesArray[j];
                    valuesArray[j] = temp;
                }
            }
        }

        return valuesArray;
    }

    /**
     * Метод должен возвращать максимальное
     * значение из введенных. Если входящие числа
     * отсутствуют - вернуть 0
     *
     * @param values входящие числа
     * @return максимальное число или 0
     */
    public int maxValue(int... values) {

        if (values == null) return 0;
        int max = 0;

        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    /**
     * Переставить элементы массива
     * в обратном порядке
     *
     * @param array массив для преобразования
     * @return входящий массив в обратном порядке
     */
    public int[] reverse(int[] array) {

        for (int i = 0; i < array.length / 2; i++){
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }

        return array;
    }
    /**
     * Метод должен вернуть массив,
     * состоящий из чисел Фибоначчи
     *
     * @param numbersCount количество чисел Фибоначчи,
     *                     требуемое в исходящем массиве.
     *                     Если numbersCount < 1, исходный
     *                     массив должен быть пуст.
     * @return массив из чисел Фибоначчи
     */
    public int[] fibonacciNumbers(int numbersCount) {

        if (numbersCount < 1) return new int[]{};

        int[] fibonacciArr = new int[numbersCount];

        for (int i = 0; i < numbersCount; i++) {
            if (i == 0 || i == 1) fibonacciArr[i] = 1;
            else fibonacciArr[i] = fibonacciArr[i - 1] + fibonacciArr[i - 2];
        }

        return fibonacciArr;
    }

    /**
     * В данном массиве найти максимальное
     * количество одинаковых элементов.
     *
     * @param array массив для выборки
     * @return количество максимально встречающихся
     * элементов
     */
    public int maxCountSymbol(int[] array) {

        if (array.length == 0) return 0;

        int max = Integer.MIN_VALUE;
        int[] count = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] == array[i]) count[i]++;
            }
        }

        for (int elem : count) {
            if (elem > max) {
                max = elem;
            }
        }

        return max + 1;
    }
}
