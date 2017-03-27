package com.agoda.models;


import com.agoda.downloaders.Protocol;

import java.util.Objects;

public class Source {

    public Protocol protocol;
    public String sourceURL;
    public String outputFilePath;

    public Source(Protocol protocol, String sourceURL, String outputFilePath) {
        this.protocol = protocol;
        this.sourceURL = sourceURL;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public boolean equals(Object obj) {
        Source comparedObj = ((Source) (obj));
        return this.protocol == comparedObj.protocol &&
                Objects.equals(this.sourceURL, comparedObj.sourceURL) &&
                Objects.equals(this.outputFilePath, comparedObj.outputFilePath);
    }
}
