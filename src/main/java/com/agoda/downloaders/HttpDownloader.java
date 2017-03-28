package com.agoda.downloaders;


import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.ReadableByteChannel;

public class HttpDownloader extends Downloader {

    public HttpDownloader(String downloadURL, String outputFilePath) {
        super(downloadURL, outputFilePath);
    }

    @Override
    public void download() throws IOException {
        URLConnection urlConnection = getUrlConnection(this.sourceURL);
        try (ReadableByteChannel readableByteChannel = establishChannel(urlConnection.getInputStream())) {
            writeDataFromChannel(readableByteChannel, this.outputFilePath, urlConnection.getContentLengthLong());
        }
    }

    private URLConnection getUrlConnection(String downloadURL) throws IOException {
        URL url = new URL(downloadURL);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "");
        return urlConnection;
    }
}
