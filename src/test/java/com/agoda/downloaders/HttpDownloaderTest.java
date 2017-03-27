package com.agoda.downloaders;

import org.junit.Assert;
import org.junit.Test;


public class HttpDownloaderTest {

    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        HttpDownloader httpDownloader = null;
        String downloadURL = "http://my.file.com/file.txt";
        String outputFilePath = "file.txt";

        // Act
        httpDownloader = new HttpDownloader(downloadURL, outputFilePath);

        // Assert
        Assert.assertTrue(httpDownloader instanceof Downloader);
    }

    @Test
    public void download() throws Exception {

    }

}