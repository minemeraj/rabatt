package de.rwthaachen.webtech.rabatt.config;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
public class EmbeddedElasticsearchServer {

	static EmbeddedElasticsearchServer elasticsearchServer;
	
    private static final String DEFAULT_DATA_DIRECTORY = System.getProperty("java.io.tmpdir") + File.pathSeparator + "elasticsearch-data";


    
    private final Node node;
    private final String dataDirectory;

    public static EmbeddedElasticsearchServer getInstance() {
    	if (elasticsearchServer == null) {
    		elasticsearchServer = new EmbeddedElasticsearchServer();
		}
		return elasticsearchServer;
	}
    
    private EmbeddedElasticsearchServer() {
        this(DEFAULT_DATA_DIRECTORY);
    }

    public EmbeddedElasticsearchServer(String dataDirectory) {
        this.dataDirectory = dataDirectory;

        Settings.Builder elasticsearchSettings = Settings.settingsBuilder()
                .put("http.enabled", "false")
                .put("path.home", new File(dataDirectory).toPath().toAbsolutePath().toString())
                .put("path.data", dataDirectory);

        node = nodeBuilder()
                .local(true)
                .settings(elasticsearchSettings.build())
                .node();
    }

    public Client getClient() {
        return node.client();
    }

    public void shutdown() {
        node.close();
        deleteDataDirectory();
    }

    private void deleteDataDirectory() {
        try {
            FileUtils.deleteDirectory(new File(dataDirectory));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete data directory of embedded elasticsearch server", e);
        }
    }
}