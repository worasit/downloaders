package com.agoda;

import com.agoda.downloaders.Downloadable;
import com.agoda.downloaders.DownloaderFactory;
import com.agoda.source.Source;
import com.agoda.source.SourceExtractor;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public class Application {

    @Parameter(names = "--help", help = true)
    private boolean help;
    @Parameter(names = {"--sources"}, required = true, description = "Comma-separated list of sources URLs to be run (e.g. http://my.file.com/file,ftp://other.file.com/other,sftp://and.also.this/ending)")
    String sourcesURLs;
    @Parameter(names = {"--out"}, required = true, description = "Local output directory to save all downloaded files")
    String outputDirectoryPath;
    @Parameter(names = {"--ftpUser"}, description = "FTP user which has a permission to access, and download files")
    String ftpUser;
    @Parameter(names = {"--ftpPassword"}, description = "FTP password")
    String ftpPassword;
    @Parameter(names = {"--sftpUser"}, description = "SFTP user which has a permission to access, and download files")
    String sftpUser;
    @Parameter(names = {"--sftpPassword"}, description = "SFTP password")
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

        List<Source> sources = SourceExtractor.extract(sourcesURLs, outputDirectoryPath, ftpUser, ftpPassword, sftpUser, sftpPassword);
        DownloaderFactory downloaderFactory = new DownloaderFactory();

        for (Source source : sources) {

            Downloadable downloader = downloaderFactory.getDownloader(source);

            new Thread(() -> {
                try {
                    downloader.download();
                } catch (IOException | JSchException | URISyntaxException | SftpException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }
}
