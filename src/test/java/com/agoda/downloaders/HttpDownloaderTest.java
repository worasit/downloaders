package com.agoda.downloaders;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URLConnection;
import java.nio.channels.ReadableByteChannel;

import static org.mockito.Matchers.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpDownloader.class)
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
        // Arrange
        String downloadURL = "http://my.file.com/file.txt";
        String outputFilePath = "file.txt";
        long contentLength = Long.MAX_VALUE;

        HttpDownloader httpDownloader = PowerMockito.spy(new HttpDownloader(downloadURL, outputFilePath));
        URLConnection mockUrlConnection = PowerMockito.mock(URLConnection.class);
        ReadableByteChannel mockReadableByteChannel = PowerMockito.mock(ReadableByteChannel.class);

        PowerMockito
                .doReturn(mockUrlConnection)
                .when(httpDownloader, "getUrlConnection", anyString());
        PowerMockito
                .doReturn(mockReadableByteChannel)
                .when(httpDownloader, "establishChannel", any(URLConnection.class));
        PowerMockito
                .doNothing()
                .when(httpDownloader, "writeDataFromChannel", any(ReadableByteChannel.class), anyString(), anyLong());
        PowerMockito
                .doReturn(contentLength)
                .when(mockUrlConnection, "getContentLengthLong");

        // Act
        httpDownloader.download();

        // Assert
        PowerMockito
                .verifyPrivate(httpDownloader, times(1))
                .invoke("getUrlConnection", downloadURL);

        PowerMockito
                .verifyPrivate(httpDownloader, times(1))
                .invoke("establishChannel", mockUrlConnection);

        PowerMockito
                .verifyPrivate(httpDownloader, times(1))
                .invoke("writeDataFromChannel", mockReadableByteChannel, outputFilePath, contentLength);

    }
}