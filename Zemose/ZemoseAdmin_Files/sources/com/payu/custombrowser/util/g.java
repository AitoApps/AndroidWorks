package com.payu.custombrowser.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class g extends SSLSocketFactory {
    private SSLSocketFactory a;

    public g() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext instance = SSLContext.getInstance("TLS");
        instance.init(null, null, null);
        this.a = instance.getSocketFactory();
    }

    public String[] getDefaultCipherSuites() {
        return this.a.getDefaultCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
        return this.a.getSupportedCipherSuites();
    }

    public Socket createSocket() throws IOException {
        return a(this.a.createSocket());
    }

    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return a(this.a.createSocket(s, host, port, autoClose));
    }

    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return a(this.a.createSocket(host, port));
    }

    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return a(this.a.createSocket(host, port, localHost, localPort));
    }

    public Socket createSocket(InetAddress host, int port) throws IOException {
        return a(this.a.createSocket(host, port));
    }

    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return a(this.a.createSocket(address, port, localAddress, localPort));
    }

    private Socket a(Socket socket) {
        if (socket != null && (socket instanceof SSLSocket)) {
            ((SSLSocket) socket).setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.1", "TLSv1"});
        }
        return socket;
    }
}
