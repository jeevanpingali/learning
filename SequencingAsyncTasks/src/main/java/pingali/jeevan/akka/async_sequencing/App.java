package pingali.jeevan.akka.async_sequencing;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import pingali.jeevan.akka.async_sequencing.actors.SecondSystemCallActor;
import pingali.jeevan.akka.async_sequencing.actors.FirstSystemCallActor;
import pingali.jeevan.akka.async_sequencing.messages.FirstSystemMessage;

import java.io.IOException;

public class App {
    public static void main(String args[]) throws IOException {
        final ActorSystem system = ActorSystem.create("sequencing");
        final ActorRef secondSystemCallActor = system.actorOf(SecondSystemCallActor.props(), "secondSystemCall");
        final ActorRef firstsystemCallActor = system.actorOf(FirstSystemCallActor.props(secondSystemCallActor), "firstSystemCall");

        for(int i = 0; i < 10; i++) {
            FirstSystemMessage firstSystemMessage = new FirstSystemMessage();
            firstSystemMessage.setMessage("FirstSystemMessage Number: " + i);

            firstsystemCallActor.tell(firstSystemMessage, ActorRef.noSender());
        }


        System.in.read();

        system.terminate();
    }
}
