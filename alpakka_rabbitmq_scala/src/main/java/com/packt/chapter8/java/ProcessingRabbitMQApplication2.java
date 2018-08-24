package com.packt.chapter8.java;

import akka.Done;
import akka.actor.ActorSystem;
import akka.japi.Pair;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.alpakka.amqp.*;
import akka.stream.alpakka.amqp.javadsl.AmqpSink;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class ProcessingRabbitMQApplication2 {
    public static void main(String args[]) {
        ActorSystem actorSystem = ActorSystem.create("SimpleStream");
        Materializer actorMaterializer = ActorMaterializer.create(actorSystem);

        String consumerQueueName = "akka_streams_consumer_queue2";
        QueueDeclaration consumerQueueDeclaration = QueueDeclaration.create(consumerQueueName);

        AmqpConnectionProvider connectionProvider = AmqpLocalConnectionProvider.getInstance();


        Sink<ByteString, CompletionStage<Done>> amqpSink =
                AmqpSink.createSimple(
                        AmqpSinkSettings.create(connectionProvider)
                                .withRoutingKey(consumerQueueName)
                                .withDeclarations(consumerQueueDeclaration));

        List<String> input = Arrays.asList("one", "two", "three", "four", "five");
        Source.from(input).map(ByteString::fromString).runWith(amqpSink, actorMaterializer);

    }
}
