package pingali.jeevan.akka.async_sequencing.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import pingali.jeevan.akka.async_sequencing.messages.FirstSystemMessage;
import pingali.jeevan.akka.async_sequencing.messages.SecondSystemMessage;
import pingali.jeevan.akka.async_sequencing.messages.Status;

import java.util.Random;

public class FirstSystemCallActor extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private Random random = new Random();
    private final ActorRef secondSystemCallActor;

    private FirstSystemCallActor(ActorRef secondSystemCallActor) {
        this.secondSystemCallActor = secondSystemCallActor;
    }

    static public Props props(ActorRef secondSystemCallActor) {
        return Props.create(FirstSystemCallActor.class, () -> new FirstSystemCallActor(secondSystemCallActor));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(FirstSystemMessage.class, firstsystemMessage -> {
                    log.info(firstsystemMessage.getMessage());
                    Boolean bool = random.nextBoolean();
                    Status status = Status.FIRST_SYSTEM_FAILURE;
                    if(bool) {
                        status = Status.FIRST_SYSTEM_SUCCESS;
                    }

                    secondSystemCallActor.tell(new SecondSystemMessage(status), getSelf());
                })
                .build();
    }
}
