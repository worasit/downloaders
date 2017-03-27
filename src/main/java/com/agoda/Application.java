package com.agoda;

import com.agoda.downloaders.Downloadable;
import com.agoda.downloaders.DownloaderFactory;
import com.agoda.models.Source;
import com.agoda.utilities.SourceExtractor;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public class Application {

    @Parameter(names = "--help", help = true)
    private boolean help;
    @Parameter(names = {"--sources"}, required = true, description = "Comma-separated list of sources URLs to be run (e.g. http://my.file.com/file,ftp://other.file.com/other,sftp://and.also.this/ending)")
    String sourcesUrls;
    @Parameter(names = {"--out"}, required = true, description = "Output directory to save all downloaded files")
    String outputDirectoryPath;
    @Parameter(names = {"--ftpUser"}, description = "FTP user which has a permission to access, and download sources")
    String ftpUser;
    @Parameter(names = {"--ftpPassword"}, description = "FTP password related to the FTP user")
    String ftpPassword;
    @Parameter(names = {"--sftpUser"}, description = "SFTP user which has a permission to access, and download sources")
    String sftpUser;
    @Parameter(names = {"--sftpPassword"}, description = "SFTP password related to the FTP user")
    String sftpPassword;

    public static void main(String[] args) throws Exception {
        Application application = new Application();
        JCommander jCommander = new JCommander(application, args);
        application.run(jCommander);
    }

    public void run(JCommander jCommander) throws MalformedURLException, URISyntaxException {

        if (this.help) {
            jCommander.usage();
            return;
        }

        List<Source> sources = SourceExtractor.extract(sourcesUrls, outputDirectoryPath);
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
