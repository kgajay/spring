package com.spring3.demo.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ajay.kg created on 26/06/17.
 */
public class SampleTest {

    public static void main(String[] args) throws InterruptedException {
        Flowable.just("Hello world").subscribe(System.out::println);

        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
//            throw new RuntimeException();
             return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }
}
