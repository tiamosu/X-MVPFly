package com.xia.fly.http.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import androidx.annotation.NonNull;

/**
 * @author xia
 * @date 2018/9/27.
 */
@SuppressWarnings("WeakerAccess")
public class TLSSocketFactory extends SSLSocketFactory {
    private final SSLSocketFactory delegate;

    public TLSSocketFactory(@NonNull SSLContext context) {
        delegate = context.getSocketFactory();
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(address, port, localAddress, localPort));
    }

    private Socket enableTLSOnSocket(Socket socket) {
        if (socket instanceof SSLSocket) {
            final String[] protocols = ((SSLSocket) socket).getEnabledProtocols();
            final List<String> supports = new ArrayList<>();
            if (protocols != null && protocols.length > 0) {
                supports.addAll(Arrays.asList(protocols));
            }
            Collections.addAll(supports, "TLSv1.1", "TLSv1.2");
            ((SSLSocket) socket).setEnabledProtocols(supports.toArray(new String[0]));
        }
        return socket;
    }
}
