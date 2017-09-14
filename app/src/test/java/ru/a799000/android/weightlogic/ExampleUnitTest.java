package ru.a799000.android.weightlogic;

import org.junit.Test;

import java.util.Observable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        rx.Observable<String> o = rx.Observable.just(1,2,3)
                .filter(integer -> {return integer > 1;})
                .doOnNext(integer -> {System.out.println("r " + integer);})
                .map(i-> "st" + i);

        rx.Observable<String> o1 = rx.Observable.just(5,6,7)
                .filter(integer -> {return integer > 1;})
                .doOnNext(integer -> {System.out.println("r " + integer);})
                .map(i-> "st" + i);

        rx.Observable.zip(o,o1,(s, s2) -> {
            return s + s2;
        })
                .subscribe(s -> {
                    System.out.println(s);
                });



    }
}