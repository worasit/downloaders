package com.agoda.downloaders;

import com.agoda.source.Source;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpDownloader.class, HttpsDownloader.class})
public class HttpsDownloaderTest {
    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        HttpsDownloader httpsDownloader = null;
        String downloadURL = "https://other.file.com/other.txt";
        String outputFilePath = "other.txt";
        Source source = new Source(Protocol.HTTPS, downloadURL, outputFilePath, "other.file.com", null, null);

        // Act
        httpsDownloader = new HttpsDownloader(source);

        // Assert
        Assert.assertTrue(httpsDownloader instanceof Downloader);
    }

    @Test
    public void download() throws Exception {
        // Arrange
        String downloadURL = "https://other.file.com/other.txt";
        String outputFilePath = "other.txt";
        Source source = new Source(Protocol.HTTPS, downloadURL, outputFilePath, "other.file.com", null, null);
        HttpsDownloader httpsDownloader = spy(new HttpsDownloader(source));

        doNothing()
                .when(httpsDownloader, "trustAllCertificate");

        suppress(method(HttpDownloader.class, "download"));

        // Act
        httpsDownloader.download();

        // Assert
        verifyPrivate(httpsDownloader, times(1))
                .invoke("trustAllCertificate");

        verifyPrivate((HttpDownloader) httpsDownloader, times(1))
                .invoke("download");
    }

}