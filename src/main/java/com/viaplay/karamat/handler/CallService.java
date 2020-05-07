package com.viaplay.karamat.handler;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viaplay.karamat.model.MusicBrainz;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallService {

	protected String sendRequest(URIBuilder uri) {
		CloseableHttpClient httpClient;

		try {
			httpClient = HttpClients.custom()
					.setSSLSocketFactory(new SSLConnectionSocketFactory(
							SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build()))
					.build();

			HttpGet request = new HttpGet(uri.build());
			request.addHeader("Content-Type", "application/json");
			CloseableHttpResponse response = httpClient.execute(request);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			log.error("Error on API Call " + e.getMessage());
		}
		return null;
	}

	protected MusicBrainz deserializeResponse(String responseStr) {
		// to skip extra fields from API
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);

		try {
			return objectMapper.readValue(responseStr, MusicBrainz.class);
		} catch (IOException e) {
			log.error("Error on deserializing response" + e.getMessage());
		}
		return null;
	}

}