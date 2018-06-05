package pingali.jeevan.completable_future;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

class UseBlockingActionResult {
    private static Logger logger = Logger.getLogger(CompletableFutureApp.class.getName());

    void use(String value) {
        logger.info("Result: " + value);
    }
}
