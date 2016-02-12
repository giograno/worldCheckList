package it.cedacri.elasticUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class TransportClientUtils {

	/**
	 * Get the transport client for localhost
	 * 
	 * @param cluster
	 * @param port
	 * @return
	 * @throws UnknownHostException
	 */
	public static Client getLocalTransportClient(String cluster, int port)
			throws UnknownHostException {

		Settings settings = Settings.settingsBuilder().put("cluster.name", cluster).build();

		Client client = TransportClient.builder().settings(settings).build().addTransportAddress(
				new InetSocketTransportAddress(InetAddress.getByName("localhost"), port));

		return client;
	}
}
