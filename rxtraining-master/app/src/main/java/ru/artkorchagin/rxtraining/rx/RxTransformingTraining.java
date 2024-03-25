package ru.artkorchagin.rxtraining.rx;

import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;
import ru.artkorchagin.rxtraining.entity.Entity;
import ru.artkorchagin.rxtraining.exceptions.NotImplementedException;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 13.11.18
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class RxTransformingTraining {

    /* Тренировочные методы */

    /**
     * Преобразование чисел в строки
     *
     * @param intObservable - источник
     * @return {@link Observable<String>} - который эммитит строки,
     * преобразованные из чисел в {@code intObservable}
     */
    public Observable<String> transformIntToString(Observable<Integer> intObservable) {
        return intObservable.map(Object::toString);
    }

    /**
     * Преобразование {@link Observable<Integer>} эммитящих идентификаторы сущностей в сами
     * сущности, которые должны быть получены с помощью метода {@link #requestApiEntity(int)}
     *
     * @param idObservable - идентификаторы сущностей
     * @return {@link Observable<Entity>} эммитит сущности, соответствующие идентификаторам из
     * {@code idObservable}
     */
    public Observable<Entity> requestEntityById(Observable<Integer> idObservable) {
        return idObservable.concatMap(this::requestApiEntity);
    }

    /**
     * Распределение имён из {@code namesObservable} по первой букве имени, в отдельные
     * {@link GroupedObservable}
     *
     * @param namesObservable - {@link Observable<String>} с именами
     * @return {@link Observable} который эммитит {@link GroupedObservable} - сгруппированный
     * поток имён объединённых первой буквой в имени
     */
    public Observable<GroupedObservable<Character, String>> distributeNamesByFirstLetter(Observable<String> namesObservable) {
        return namesObservable.groupBy(a -> a.charAt(0));
    }

    /**
     * Объединить элементы, полученные из {@code intObservable} в списки {@link List} с максимальным
     * размером {@code listsSize}
     *
     * @param listsSize      максимальный размер списка элементов
     * @param intObservable  {@link Observable} с произвольным количеством рандомных чисел
     * @return {@code Observable} который эммитит списки чисел из {@code intObservable}
     */
    public Observable<List<Integer>> collectsIntsToLists(int listsSize, Observable<Integer> intObservable) {
        List<Integer> list = intObservable.toList().blockingGet();
        int fullChunks = (list.size() - 1) / listsSize;
        Observable<List<Integer>> result = intObservable.map(n -> list.subList(n * listsSize, n == fullChunks ? list.size() : (n + 1) * listsSize));
        return null;
    }

    /* Вспомогательные методы */

    /**
     * Выполнение HTTP запроса и эммит полученной сущности, соответствующей заданному идентификатору
     * (Вспомогательный метод! Не изменять!)
     *
     * @param id - Идентификатор сущности {@link Entity}
     * @return {@link Observable<Entity>} который эммитит полученную сущность
     */
    Observable<Entity> requestApiEntity(int id) {
        // Выполнение запроса и эммит сущности
        return Observable.just(new Entity(id));
    }

}
