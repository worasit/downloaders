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
        String user = "agoda";
        String password = "1234";

        // Act
        ftpDownloader = new FtpDownloader(downloadURL, outputFilePath, user, password);

        // Assert
        Assert.assertTrue(ftpDownloader instanceof Downloader);
    }

    @Test
    public void download() throws Exception {
        // Arrange
        FtpDownloader ftpDownloader = null;
        String downloadURL = "ftp://agoda:1234@localhost/Users/worasitdaimongkol/FTP/captain.mkv";
        String outputFilePath = "/Users/worasitdaimongkol/xxx/captain.mkv";
        String user = "agoda";
        String password = "1234";
        ftpDownloader = new FtpDownloader(downloadURL, outputFilePath, user, password);

        // Act
//        ftpDownloader.download();


        // Assert


    }

}