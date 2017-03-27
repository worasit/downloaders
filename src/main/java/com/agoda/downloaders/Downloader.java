package com.agoda.downloaders;


public abstract class Downloader implements Downloadable {

    protected String downloadURL;
    protected String outputFilePath;

    public Downloader(String downloadURL, String outputFilePath) {
        this.downloadURL = downloadURL;
        this.outputFilePath = outputFilePath;
    }
}

