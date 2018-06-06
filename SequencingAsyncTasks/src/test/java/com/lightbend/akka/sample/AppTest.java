package com.lightbend.akka.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pingali.jeevan.akka.async_sequencing.actors.SecondSystemCallActor;
import pingali.jeevan.akka.async_sequencing.actors.FirstSystemCallActor;
import pingali.jeevan.akka.async_sequencing.messages.FirstSystemMessage;

public class AppTest {
    private static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("sequencing");
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testGreeterActorSendingOfGreeting() {
        final TestKit testProbe = new TestKit(system);

        final ActorRef secondSystemCallActor = system.actorOf(SecondSystemCallActor.props(), "secondSystemCall");
        final ActorRef firstsystemCallActor = system.actorOf(FirstSystemCallActor.props(secondSystemCallActor), "firstSystemCall");

        for(int i = 0; i < 10; i++) {
            FirstSystemMessage firstSystemMessage = new FirstSystemMessage();
            firstSystemMessage.setMessage("FirstSystemMessage Number: " + i);

            firstsystemCallActor.tell(firstSystemMessage, ActorRef.noSender());
        }

    }
}
