package com.agoda.downloaders;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class HttpDownloader extends Downloader {

    public HttpDownloader(String downloadURL, String outputFilePath) {
        super(downloadURL, outputFilePath);
    }

    @Override
    public void download() throws IOException {
        URLConnection urlConnection = getUrlConnection(this.downloadURL);
        try (ReadableByteChannel readableByteChannel = establishChannel(urlConnection.getInputStream())) {
            writeDataFromChannel(readableByteChannel, this.outputFilePath, urlConnection.getContentLengthLong());
        }
    }

    private void writeDataFromChannel(ReadableByteChannel readableByteChannel, String outputFilePath, long contentLength) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath)) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, contentLength);
        }
    }

    private ReadableByteChannel establishChannel(InputStream inputStream) throws IOException {
        return Channels.newChannel(inputStream);
    }

    private URLConnection getUrlConnection(String downloadURL) throws IOException {
        URL url = new URL(downloadURL);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "");
        return urlConnection;
    }
}
