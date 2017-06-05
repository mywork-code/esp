package com.apass.esp.search.manager;


import com.apass.esp.search.enums.IndexType;
import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.net.InetAddress;

/**
 * Created by xianzhi.wang on 2017/5/15.
 * 暂时用不到，先注释掉
 */
//@Component
public class ESClientManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ESClientManager.class);

    private static volatile TransportClient client;

    @Value("${es.name}")
    private String esName;

    @Value("${es.host}")
    private String host;

    @Value("${es.port}")
    private String port;

    @Value("${es.indice}")
    private String indice;

    private ESClientManager() {
    }

    public Client getClient() {
        if (client == null) {
            synchronized (ESClientManager.class) {
                if (client == null) {
                    try {
                        LOGGER.info("esName {}", esName);
                        LOGGER.info("host {}", host);
                        LOGGER.info("port {}", port);
                        Settings settings = Settings
                                .builder()
                                .put("cluster.name", esName)
                                .put("client.transport.ignore_cluster_name", true)
                                .put("client.transport.sniff", true).build();
                        client = new PreBuiltTransportClient(settings);

                        client = client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.valueOf(port)));

                        int nodeSize = client.connectedNodes().size();
                        LOGGER.info("nodeSize {}", nodeSize);
                        Preconditions.checkArgument(nodeSize >= 1, "this is no available node");
                        initIndex();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return client;
    }

    /**
     * 初始化索引
     * @throws Exception
     */
    private void initIndex() throws Exception {
        //创建一个空的
        boolean a = client.admin().indices().prepareExists(indice).get().isExists();
        LOGGER.info("index isExists  {}", a);
        if(!client.admin().indices().prepareExists(indice).get().isExists()){
            CreateIndexResponse createIndexResponse = client.admin().indices().prepareCreate(indice).get();
            LOGGER.info("create index {}", createIndexResponse);
        }
        for (IndexType type : IndexType.values()) {
            TypesExistsResponse typesExistsResponse = client.admin().indices().typesExists(new TypesExistsRequest(new String[]{indice}, type.getDataName())).get();
            if (typesExistsResponse.isExists()) {
                continue;
            }
            String esMapper =  type.getMapper();
            InputStream in = ESClientManager.class.getResourceAsStream(esMapper);
            String mappingStr = IOUtils.toString(in).trim();
            IndicesAdminClient c = client.admin().indices();
            client.admin().indices().preparePutMapping(indice).setType(type.getDataName()).setSource(mappingStr).get();
        }
    }

}

