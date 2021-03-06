package com.agoda.downloaders;

import com.agoda.source.Source;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;


public class DownloaderFactoryTest {
    @Test
    public void getDownloader_returnHttpDownloader_ifSourceIsHttp() throws Exception {

        // Arrange
        DownloaderFactory downloaderFactory = new DownloaderFactory();
        String outputDirectoryPath = "/Users/agoda/TestDownload";

        Source fakeSource = new Source(
                Protocol.HTTP,
                "http://my.file.com/file.txt",
                Paths.get(outputDirectoryPath, "file.txt").toString(), "my.file.com", null, null);

        // Act
        Downloadable downloader = downloaderFactory.getDownloader(fakeSource);

        // Assert
        Assert.assertTrue(downloader.getClass() == HttpDownloader.class);
    }

    @Test
    public void getDownloader_returnHttpsDownloader_ifSourceIsHttps() throws Exception {

        // Arrange
        DownloaderFactory downloaderFactory = new DownloaderFactory();
        String outputDirectoryPath = "/Users/agoda/TestDownload";

        Source fakeSource = new Source(
                Protocol.HTTPS,
                "https://my.file.com/file.txt",
                Paths.get(outputDirectoryPath, "file.txt").toString(), "my.file.com", null, null);

        // Act
        Downloadable downloader = downloaderFactory.getDownloader(fakeSource);

        // Assert
        Assert.assertTrue(downloader.getClass() == HttpsDownloader.class);
    }

    @Test
    public void getDownloader_returnFtpDownloader_ifSourceIsFtp() throws Exception {

        // Arrange
        DownloaderFactory downloaderFactory = new DownloaderFactory();
        String outputDirectoryPath = "/Users/agoda/TestDownload";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";

        Source fakeSource = new Source(
                Protocol.FTP,
                "ftp://other.file.com/other.txt",
                Paths.get(outputDirectoryPath, "other.txt").toString(),
                host,
                user,
                password);

        // Act
        Downloadable downloader = downloaderFactory.getDownloader(fakeSource);

        // Assert
        Assert.assertTrue(downloader.getClass() == FtpDownloader.class);
    }

    @Test
    public void getDownloader_returnSftpDownloader_ifSourceIsSftp() throws Exception {

        // Arrange
        DownloaderFactory downloaderFactory = new DownloaderFactory();
        String outputDirectoryPath = "/Users/agoda/TestDownload";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";

        Source fakeSource = new Source(
                Protocol.SFTP,
                "sftp://and.also.this/ending.txt",
                Paths.get(outputDirectoryPath, "ending.txt").toString(),
                host,
                user,
                password);
        // Act
        Downloadable downloader = downloaderFactory.getDownloader(fakeSource);

        // Assert
        Assert.assertTrue(downloader.getClass() == SftpDownloader.class);
    }
}