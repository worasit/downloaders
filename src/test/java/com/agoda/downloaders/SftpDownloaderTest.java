package com.agoda.downloaders;

import org.junit.Assert;
import org.junit.Test;

public class SftpDownloaderTest {

    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        SftpDownloader sftpDownloader = null;
        String downloadURL = "sftp://and.also.this/ending.txt";
        String outputFilePath = "ending.txt";

        // Act
        sftpDownloader = new SftpDownloader(downloadURL, outputFilePath);

        // Assert
        Assert.assertTrue(sftpDownloader instanceof Downloader);
    }

    @Test
    public void download() throws Exception {
        // Arrange
        SftpDownloader sftpDownloader = null;
        String downloadURL = "sftp://agoda:1234@localhost/captain.mkv";
        String outputFilePath = "/Users/worasitdaimongkol/xxx/captain.mkv";
        sftpDownloader = new SftpDownloader(downloadURL, outputFilePath);

        // Act
//        sftpDownloader.download();


        // Assert


    }

}