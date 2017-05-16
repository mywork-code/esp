package com.apass.esp.search;

import com.google.common.base.Preconditions;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by xianzhi.wang on 2017/5/15.
 */
@Service
public class ESClientManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ESClientManager.class);

    private static volatile TransportClient client;

    @Value("${es.name}")
    private  String esName;

    @Value("${es.host}")
    private  String host;

    @Value("${es.port}")
    private  String port;


    private ESClientManager() {

    }


    public  Client getClient() {
        if (client == null) {
            synchronized (ESClientManager.class) {
                if (client == null) {


                        LOGGER.info("esName {}", esName);
                        LOGGER.info("host {}", host);
                        LOGGER.info("port {}", port);

                        Settings settings = Settings
                                .builder()
                                .put("cluster.name",esName)
                                .put("client.transport.ignore_cluster_name", true)
                                .put("client.transport.sniff", true).build();
                        client = new PreBuiltTransportClient(settings);

                    try {
                        client = client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.valueOf(port)));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }

                    int nodeSize = client.connectedNodes().size();
                        LOGGER.info("nodeSize {}", nodeSize);
                        Preconditions.checkArgument(nodeSize >= 1, "this is no available node");

                }

            }
        }

        return client;
    }


}

