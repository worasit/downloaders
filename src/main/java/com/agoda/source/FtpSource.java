package com.agoda.source;


import com.agoda.downloaders.Protocol;

public class FtpSource extends Source {

    public String host;
    public String user;
    public String password;

    public FtpSource(Protocol protocol, String sourceURL, String outputFilePath, String host, String user, String password) {
        super(protocol, sourceURL, outputFilePath);
        this.host = host;
        this.user = user;
        this.password = password;
    }
}
