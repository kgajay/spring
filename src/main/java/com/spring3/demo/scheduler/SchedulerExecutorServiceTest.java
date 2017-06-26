package com.spring3.demo.scheduler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author ajay.kg created on 26/06/17.
 */
public class SchedulerExecutorServiceTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable() {
            public Object call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " Callable Executed!");
                return "Called!";
            }
        }, 5, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " Runnable Executed!");
            }
        }, 1, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable Executed! " + Thread.currentThread().getName());
            }
        }, 1, 1, TimeUnit.SECONDS);

        Collection<Callable<Integer>> tasks = new LinkedBlockingDeque<>();
        for (int i=0; i<10; i++) {
            Callable<Integer> task = (Callable<Integer>) new MyRunnable(new Random().nextInt(500), new Random().nextInt(200));
            tasks.add(task);
        }

        scheduledExecutorService.invokeAll(tasks);

        System.out.println("result = " + scheduledFuture.get());

        scheduledExecutorService.shutdown();
    }

    @AllArgsConstructor
    @Data
    public static class MyRunnable implements Callable {

        private Integer a;
        private Integer b;


        @Override
        public Integer call() throws Exception {
            Random rand = new Random();
            int randomNum = rand.nextInt(201) + 100;
            Thread.sleep(randomNum);
            System.out.println(" thread " + Thread.currentThread().getName() + " result " + (a+b));
            return a+b;
        }
    }
}
