package com.agoda.downloaders;


import java.io.FileOutputStream;
import java.io.IOException;
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

        ReadableByteChannel readableByteChannel = null;
        FileOutputStream fileOutputStream = null;

        try {
            URL url = new URL(this.downloadURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "");
            readableByteChannel = Channels.newChannel(urlConnection.getInputStream());
            fileOutputStream = new FileOutputStream(this.outputFilePath);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, urlConnection.getContentLengthLong());
        } finally {
            readableByteChannel.close();
            fileOutputStream.close();
        }


    }
}
