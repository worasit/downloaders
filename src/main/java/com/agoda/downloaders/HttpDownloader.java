package com.agoda.downloaders;


import com.agoda.source.Source;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class HttpDownloader extends Downloader {

    public HttpDownloader(Source source) {
        super(source);
    }

    @Override
    public void download() throws IOException {
        URLConnection urlConnection = getUrlConnection(this.getSource().getSourceURL());
        this.writeStreamData(urlConnection.getInputStream(), urlConnection.getContentLengthLong());
    }

    private URLConnection getUrlConnection(String downloadURL) throws IOException {
        URL url = new URL(downloadURL);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "");
        return urlConnection;
    }
}
