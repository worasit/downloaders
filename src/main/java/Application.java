import com.agoda.downloaders.Downloadable;
import com.agoda.downloaders.DownloaderFactory;
import com.agoda.models.Source;
import com.agoda.utilities.SourceExtractor;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {

        String sourceURLs = args[0];
        String outputDirectoryPath = args[1];

        List<Source> sources = SourceExtractor.extract(sourceURLs, outputDirectoryPath);
        DownloaderFactory downloaderFactory = new DownloaderFactory();

        for (Source source : sources) {

            Downloadable downloader = downloaderFactory.getDownloader(source);

            new Thread(() -> {
                try {
                    downloader.download();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }
}
