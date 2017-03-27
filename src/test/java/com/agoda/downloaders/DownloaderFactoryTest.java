package com.agoda.downloaders;

import com.agoda.models.Source;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;


public class DownloaderFactoryTest {
    @Test
    public void getDownloader_returnHttpDownloader_ifSourceIsHttp() throws Exception {

        // Arrange
        DownloaderFactory downloaderFactory = new DownloaderFactory();
        String outputDirectoryPath = "/Users/agoda/TestDownload";

        Source fakeSource = new Source(
                Protocol.HTTP,
                "http://my.file.com/file.txt",
                Paths.get(outputDirectoryPath, "file.txt").toString());

        // Act
        Downloadable downloader = downloaderFactory.getDownloader(fakeSource);

        // Assert
        Assert.assertTrue(downloader instanceof HttpDownloader);
    }

    @Test
    public void getDownloader_returnHttpDownloader_ifSourceIsHttps() throws Exception {

        // Arrange
        DownloaderFactory downloaderFactory = new DownloaderFactory();
        String outputDirectoryPath = "/Users/agoda/TestDownload";

        Source fakeSource = new Source(
                Protocol.HTTPS,
                "https://my.file.com/file.txt",
                Paths.get(outputDirectoryPath, "file.txt").toString());

        // Act
        Downloadable downloader = downloaderFactory.getDownloader(fakeSource);

        // Assert
        Assert.assertTrue(downloader instanceof HttpsDownloader);
    }

    @Test
    public void getDownloader_returnFtpDownloader_ifSourceIsFtp() throws Exception {

        // Arrange
        DownloaderFactory downloaderFactory = new DownloaderFactory();
        String outputDirectoryPath = "/Users/agoda/TestDownload";

        Source fakeSource = new Source(
                Protocol.FTP,
                "ftp://other.file.com/other.txt",
                Paths.get(outputDirectoryPath, "other.txt").toString());

        // Act
        Downloadable downloader = downloaderFactory.getDownloader(fakeSource);

        // Assert
        Assert.assertTrue(downloader instanceof FtpDownloader);
    }

    @Test
    public void getDownloader_returnSftpDownloader_ifSourceIsSftp() throws Exception {

        // Arrange
        DownloaderFactory downloaderFactory = new DownloaderFactory();
        String outputDirectoryPath = "/Users/agoda/TestDownload";

        Source fakeSource = new Source(
                Protocol.SFTP,
                "sftp://and.also.this/ending.txt",
                Paths.get(outputDirectoryPath, "ending.txt").toString());

        // Act
        Downloadable downloader = downloaderFactory.getDownloader(fakeSource);

        // Assert
        Assert.assertTrue(downloader instanceof SftpDownloader);
    }
}