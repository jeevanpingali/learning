package pingali.jeevan.alpakka.example1;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.FiniteDuration;

public class App {
    public static void main(String args[]) {
        final FileSystem fs = FileSystems.getDefault();
        final FiniteDuration pollingInterval = FiniteDuration.create(250, TimeUnit.MILLISECONDS);
        final int maxLineSize = 8192;

        File file = new File(".");
        
        String path = ".\\src\\main\\java\\pingali\\jeevan\\alpakka\\example1\\App.java";
        
        final Source<String, NotUsed> lines =
                akka.stream.alpakka.file.javadsl.FileTailSource.createLines(fs.getPath(path), maxLineSize, pollingInterval);

        final ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);

        final CompletionStage<Done> done = lines.runForeach((line) -> System.out.println(line), materializer);
        
        done.thenRun(() -> system.terminate());
    }
}
