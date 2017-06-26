import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.assertEquals;

/**
 * @author ajay.kg created on 26/06/17.
 */
public class CompleteableFutureTest {

    private static ExecutorService service = Executors.newCachedThreadPool();

    @Test
    public void testCompletedFuture() throws Exception {
        String expectedValue = "the expected value";
        CompletableFuture<String> alreadyCompleted = CompletableFuture.completedFuture(expectedValue);
        assertEquals(alreadyCompleted.get(), expectedValue);
    }

    @Test
    public void testRunAsync() throws Exception {
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> System.out.println("running async task"), service);
        //utility testing method
        Thread.sleep(1000);
        assertEquals(runAsync.isDone(), true);
    }

//    @Test
//    public void test_supply_async() throws Exception {
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(simulatedTask(1, "Final Result"), service);
//        assertEquals(completableFuture.get(), "Final Result");
//    }

    @Test
    public void testExceptionally() throws Exception {
        AtomicInteger received = new AtomicInteger();

        CompletableFuture<Integer> future = new CompletableFuture<>();
        future.thenAccept(i -> i++)//
                .thenRun(() -> {
                    throw new RuntimeException("test");
                })//
                .thenRun(() -> "".toString())//
                .thenRun(() -> "".toString())//
                .exceptionally(t -> {
                    received.incrementAndGet();
                    return null;
                });
        future.exceptionally(t -> {
            received.incrementAndGet();
            received.incrementAndGet();
            return 0;
        });

        assertEquals(2, future.getNumberOfDependents());

        future.complete(1);
        assertEquals(1, received.get());
    }
}
