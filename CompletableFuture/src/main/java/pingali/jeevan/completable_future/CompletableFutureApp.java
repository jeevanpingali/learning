package pingali.jeevan.completable_future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureApp {
    public static void main(String args[]) {
        BlockingAction blockingAction = new BlockingAction();

        UseBlockingActionResult useBlockingActionResult = new UseBlockingActionResult();

        CompletableFuture<String> step1 = CompletableFuture.supplyAsync(blockingAction::blockingActionMethod);
        CompletableFuture<Void> step2 = step1.thenAccept(useBlockingActionResult::use);
        step2.thenRun(() -> System.out.println("All steps completed..."));

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}