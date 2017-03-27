package com.agoda.downloaders;

import org.junit.Assert;
import org.junit.Test;


public class FtpDownloaderTest {
    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        FtpDownloader ftpDownloader = null;
        String downloadURL = "ftp://other.file.com/other.txt";
        String outputFilePath = "other.txt";

        // Act
        ftpDownloader = new FtpDownloader(downloadURL, outputFilePath);

        // Assert
        Assert.assertTrue(ftpDownloader instanceof Downloader);
    }

    @Test
    public void download() throws Exception {
        // Arrange
        FtpDownloader ftpDownloader = null;
        String downloadURL = "ftp://agoda:1234@worasit.local./captain.mkv";
        String outputFilePath = "/Users/worasitdaimongkol/xxx/captain.mkv";
        ftpDownloader = new FtpDownloader(downloadURL, outputFilePath);

        // Act
        ftpDownloader.download();


        // Assert


    }

}