package pingali.jeevan.akka.async_sequencing.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import pingali.jeevan.akka.async_sequencing.messages.SecondSystemMessage;

public class SecondSystemCallActor extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static public Props props() {
        return Props.create(SecondSystemCallActor.class, SecondSystemCallActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SecondSystemMessage.class, secondSystemMessage -> log.info(secondSystemMessage.getMessage()))
                .build();
    }
}
