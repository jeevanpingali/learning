package pingali.jeevan.alpakka.example1;

public class App {
    public static void main(String args[]) {
        final FileSystem fs = FileSystems.getDefault();
        final FiniteDuration pollingInterval = FiniteDuration.create(250, TimeUnit.MILLISECONDS);
        final int maxLineSize = 8192;

        final Source<String, NotUsed> lines =
                akka.stream.alpakka.file.javadsl.FileTailSource.createLines(fs.getPath(path), maxLineSize, pollingInterval);

        lines.runForeach((line) -> System.out.println(line), materializer);
    }
}
