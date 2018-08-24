package pingali.jeevan.alpakka.example1;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.actor.Terminated;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.alpakka.file.javadsl.FileTailSource;
import akka.stream.javadsl.Source;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

public class App {
    public static void main(String args[]) throws IOException {
        final FileSystem fs = FileSystems.getDefault();
        final FiniteDuration pollingInterval = FiniteDuration.create(250, TimeUnit.MILLISECONDS);
        final int maxLineSize = 100;

        String path = ".\\src\\main\\java\\pingali\\jeevan\\alpakka\\example1\\App.java";
        
        final Source<String, NotUsed> lines =
                FileTailSource.createLines(fs.getPath(path), maxLineSize, pollingInterval);

        final ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);

        final CompletionStage<Done> done = lines.runForeach((line) -> System.out.println(line), materializer);
        
        done.thenRun(() -> close(system, fs));
    }
    
    private static void close(ActorSystem system, FileSystem fs) {
        try {
            fs.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        Future<Terminated> terminate = system.terminate();
        
        System.out.println("Is Completed?: " + terminate.isCompleted());        
    }
}
