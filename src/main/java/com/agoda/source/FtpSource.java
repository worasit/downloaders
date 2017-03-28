package com.agoda.source;


import com.agoda.downloaders.Protocol;

public class FtpSource extends Source {

    public String user;
    public String password;

    public FtpSource(Protocol protocol, String sourceURL, String outputFilePath, String user, String password) {
        super(protocol, sourceURL, outputFilePath);
        this.user = user;
        this.password = password;
    }
}
