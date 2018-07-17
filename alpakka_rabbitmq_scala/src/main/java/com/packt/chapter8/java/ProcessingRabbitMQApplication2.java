package com.packt.chapter8.java;

import akka.Done;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.alpakka.amqp.*;
import akka.stream.alpakka.amqp.javadsl.AmqpSink;
import akka.stream.javadsl.Sink;
import akka.util.ByteString;

import java.util.concurrent.CompletionStage;

public class ProcessingRabbitMQApplication2 {
    public static void main(String args[]) {
/*
        ActorSystem actorSystem = ActorSystem.create("SimpleStream");
        Materializer actorMaterializer = ActorMaterializer.create(actorSystem);

        String consumerQueueName = "akka_streams_consumer_queue";
        QueueDeclaration consumerQueueDeclaration = QueueDeclaration.create(consumerQueueName);

        AmqpLocalConnectionProvider localConnectionProvider = new AmqpLocalConnectionProvider();

        Sink<ByteString, CompletionStage<Done>> amqpSink =
                AmqpSink.createSimple(
                        AmqpSinkSettings.create(connectionProvider)
                                .withRoutingKey(consumerQueueName)
                                .withDeclarations(consumerQueueDeclaration));
*/

    }
}
