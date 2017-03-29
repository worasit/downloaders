package com.agoda.source;


import com.agoda.downloaders.Protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Source {

    protected Protocol protocol;
    protected String sourceURL;
    protected String outputFilePath;
    protected String host;
    protected String user;
    protected String password;

    protected URI uri;

    public Source(Protocol protocol, String sourceURL, String outputFilePath, String host, String user, String password) {
        this.protocol = protocol;
        this.sourceURL = sourceURL;
        this.outputFilePath = outputFilePath;
        this.host = host;
        this.user = user;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        Source comparedObj = ((Source) (obj));
        return this.protocol == comparedObj.protocol &&
                Objects.equals(this.sourceURL, comparedObj.sourceURL) &&
                Objects.equals(this.outputFilePath, comparedObj.outputFilePath);
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public URI getUri() throws URISyntaxException {
        if (this.uri == null) {
            this.uri = new URI(this.sourceURL);
        }
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
