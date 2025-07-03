package com.example.user.javacoretraining.training;

import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Набор тренингов по работе со строками в java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see StringsTrainingTest.
 */
public class StringsTraining {

    /**
     * Метод по созданию строки,
     * состоящей из нечетных символов
     * входной строки в том же порядке
     * (нумерация символов идет с нуля)
     *
     * @param text строка для выборки
     * @return новая строка из нечетных
     * элементов строки text
     */
    public String getOddCharacterString(String text) {
        StringBuilder result = new StringBuilder();
        char[] charText = text.toCharArray();
        for (int i = 0; i < charText.length; i++) {
            if (i % 2 != 0) {
                result.append(charText[i]);
            }
        }
        return result.toString();
    }

    /**
     * Метод для определения количества
     * символов, идентичных последнему
     * в данной строке
     *
     * @param text строка для выборки
     * @return массив с номерами символов,
     * идентичных последнему. Если таких нет,
     * вернуть пустой массив
     */
    public int[] getArrayLastSymbol(String text) {
        List<Integer> result = new ArrayList<>();
        char[] charText = text.toCharArray();

        for (int i = 0; i < charText.length - 1; i++) {
            if (charText[i] == charText[charText.length - 1]) {
                result.add(i);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return result.stream().mapToInt(i -> i).toArray();
        } else {
            int[] resultArr = new int[result.size()];
            for (int i = 0; i < resultArr.length; i++) {
                resultArr[i] = result.get(i);
            }
            return resultArr;
        }
    }

    /**
     * Метод по получению количества
     * цифр в строке
     *
     * @param text строка для выборки
     * @return количество цифр в строке
     */
    public int getNumbersCount(String text) {
        int result = 0;
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) result++;

        return result;
    }

    /**
     * Дан текст. Заменить все цифры
     * соответствующими словами.
     *
     * @param text текст для поиска и замены
     * @return текст, где цифры заменены словами
     */
    public String replaceAllNumbers(String text) {
        Map<String, String> map = new HashMap<>();
        map.put("0", "zero");
        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");
        map.put("4", "four");
        map.put("5", "five");
        map.put("6", "six");
        map.put("7", "seven");
        map.put("8", "eight");
        map.put("9", "nine");

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            String key = text.substring(i, i + 1);
            if (map.containsKey(key)) {
                String digit = map.get(key);
                result.append(digit);
            } else result.append(key);
        }
        return result.toString();
    }

    /**
     * Метод должен заменить заглавные буквы
     * на прописные, а прописные на заглавные
     *
     * @param text строка для изменения
     * @return измененная строка
     */
    public String capitalReverse(String text) {
        char[] charText = text.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : charText) {
            if (Character.isUpperCase(c)) {
                result.append(String.valueOf(c).toLowerCase());
            } else {
                result.append(String.valueOf(c).toUpperCase());
            }
        }
        return result.toString();
    }

}
