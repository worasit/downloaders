package com.agoda.downloaders;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class HttpsDownloader extends HttpDownloader {
    public HttpsDownloader(String downloadURL, String outputFilePath) {
        super(downloadURL, outputFilePath);
    }

    @Override
    public void download() throws IOException {
        trustAllCertificate();
        super.download();
    }

    private void trustAllCertificate() {

        try {
            TrustManager[] trustManagers = initTrustManagers();
            SSLContext sslContext = initSSLContext(trustManagers);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Exception ignored) {
        }
    }

    private SSLContext initSSLContext(TrustManager[] trustManagers) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustManagers, new java.security.SecureRandom());
        return sslContext;
    }

    private TrustManager[] initTrustManagers() {
        return new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(
                                X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(
                                X509Certificate[] certs, String authType) {
                        }
                    }
            };
    }
}
