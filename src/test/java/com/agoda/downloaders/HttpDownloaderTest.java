package com.agoda.downloaders;

import com.agoda.source.Source;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;
import java.net.URLConnection;
import java.nio.channels.ReadableByteChannel;

import static org.mockito.Matchers.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpDownloader.class)
public class HttpDownloaderTest {

    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        HttpDownloader httpDownloader = null;
        String downloadURL = "http://my.file.com/file.txt";
        String outputFilePath = "file.txt";
        Source source = new Source(Protocol.HTTP, downloadURL, outputFilePath, "my.file.com", null, null);

        // Act
        httpDownloader = new HttpDownloader(source);

        // Assert
        Assert.assertTrue(httpDownloader instanceof Downloader);
    }

    @Test
    public void download() throws Exception {
        // Arrange
        String downloadURL = "http://my.file.com/file.txt";
        String outputFilePath = "file.txt";
        long contentLength = Long.MAX_VALUE;
        Source source = new Source(Protocol.HTTP, downloadURL, outputFilePath, "my.file.com", null, null);


        HttpDownloader httpDownloader = spy(new HttpDownloader(source));
        InputStream mockInputStream = mock(InputStream.class);
        URLConnection mockUrlConnection = mock(URLConnection.class);
        ReadableByteChannel mockReadableByteChannel = mock(ReadableByteChannel.class);


        doReturn(mockInputStream)
                .when(mockUrlConnection, "getInputStream");
        doReturn(contentLength)
                .when(mockUrlConnection, "getContentLengthLong");

        doReturn(mockUrlConnection)
                .when(httpDownloader, "getUrlConnection", anyString());
        doReturn(mockReadableByteChannel)
                .when(httpDownloader, "establishChannel", any(InputStream.class));
        doNothing()
                .when(httpDownloader, "writeDataFromChannel", any(ReadableByteChannel.class), anyString(), anyLong());

        // Act
        httpDownloader.download();

        // Assert
        verifyPrivate(httpDownloader, times(1))
                .invoke("getUrlConnection", downloadURL);

        verifyPrivate(httpDownloader, times(1))
                .invoke("establishChannel", mockInputStream);

        verifyPrivate(httpDownloader, times(1))
                .invoke("writeDataFromChannel", mockReadableByteChannel, outputFilePath, contentLength);
    }


}