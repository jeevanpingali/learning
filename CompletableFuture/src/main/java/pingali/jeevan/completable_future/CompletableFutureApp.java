package pingali.jeevan.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureApp {
    private static ExecutorService firstPool;
    private static ExecutorService secondPool;
    private static ExecutorService thirdPool;

    public static void main(String args[]) {

        createPools();

        BlockingAction blockingAction = new BlockingAction();

        UseBlockingActionResult useBlockingActionResult = new UseBlockingActionResult();

        CompletableFuture<String> step1 = CompletableFuture.supplyAsync(blockingAction::blockingActionMethod, firstPool);
        CompletableFuture<Void> step2 = step1.thenAcceptAsync(useBlockingActionResult::use, secondPool);
        step2.thenRunAsync(() -> System.out.println("All steps completed..."), thirdPool);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        closePools();
    }

    private static void closePools() {
        firstPool.shutdown();
        secondPool.shutdown();
        thirdPool.shutdown();
    }

    private static void createPools() {
        firstPool = Executors.newFixedThreadPool(10);
        secondPool = Executors.newFixedThreadPool(10);
        thirdPool = Executors.newFixedThreadPool(10);
    }
}