package completable_future;

class BlockingAction {
    String blockingActionMethod() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Jeevan";
    }
}
