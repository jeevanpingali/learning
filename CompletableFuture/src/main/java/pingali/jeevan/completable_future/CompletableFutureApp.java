package pingali.jeevan.completable_future;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureApp {
    private static ExecutorService firstPool;
    private static ExecutorService secondPool;
    private static ExecutorService thirdPool;
    private static final MetricRegistry metrics = new MetricRegistry();
    private static final Timer responses = metrics.timer(CompletableFutureApp.class.getName());

    public static void main(String args[]) {

        createPools();

        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

        for(int i = 0; i < 100; i++) {
            final Timer.Context context = responses.time();
            try {
                BlockingAction blockingAction = new BlockingAction();

                UseBlockingActionResult useBlockingActionResult = new UseBlockingActionResult();

                CompletableFuture<String> step1 = CompletableFuture.supplyAsync(blockingAction::blockingActionMethod, firstPool);
                CompletableFuture<Void> step2 = step1.thenAcceptAsync(useBlockingActionResult::use, secondPool);
                step2.thenRunAsync(() -> System.out.println("All steps completed..."), thirdPool);
            } finally {
                context.stop();
            }
        }


        try {
            Thread.sleep(3000);
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