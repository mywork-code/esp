package com.apass.esp.search.utils;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/15
 * @see
 * @since JDK 1.8
 */
public class Esprop {
    private String clusterName;
    private String host;
    private int port;
    private String indice;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }
}
