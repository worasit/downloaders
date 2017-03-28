package com.agoda.downloaders;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.net.URISyntaxException;


public interface Downloadable {
    void download() throws IOException, URISyntaxException, SftpException, JSchException;
}
