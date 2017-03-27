package com.agoda.downloaders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class HttpsDownloaderTest {
    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        HttpsDownloader httpsDownloader = null;
        String downloadURL = "https://other.file.com/other.txt";
        String outputFilePath = "other.txt";

        // Act
        httpsDownloader = new HttpsDownloader(downloadURL, outputFilePath);

        // Assert
        Assert.assertTrue(httpsDownloader instanceof Downloader);
    }

    @Test
    public void download() throws Exception {

    }

}