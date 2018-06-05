package pingali.jeevan.completable_future;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.concurrent.*;
import java.util.logging.Logger;

public class CompletableFutureApp {
    private static Logger logger = Logger.getLogger(CompletableFutureApp.class.getName());
    private static ThreadPoolExecutor firstPool;
    private static ThreadPoolExecutor secondPool;
    private static ThreadPoolExecutor thirdPool;
    private static final MetricRegistry metrics = new MetricRegistry();
    private static final Timer responses = metrics.timer(CompletableFutureApp.class.getName());
    private static int NO_OF_REQUESTS = 100;

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
                step2.thenRunAsync(() -> logger.info("Third step also completed.."), thirdPool); // this doesn't require a pool for such a simple computation.
            } finally {
                context.stop();
            }
        }


        try {
            while(anyPendingWork()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("All steps of all tasks submitted completed...");
        closePools();

        reporter.report();
    }

    private static void closePools() {
        firstPool.shutdown();
        secondPool.shutdown();
        thirdPool.shutdown();
    }

    private static Boolean anyPendingWork() {

        return firstPool.getCompletedTaskCount() != NO_OF_REQUESTS || secondPool.getCompletedTaskCount() != NO_OF_REQUESTS || thirdPool.getCompletedTaskCount() != NO_OF_REQUESTS;
    }

    private static void createPools() {
        firstPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        secondPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        thirdPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    }
}