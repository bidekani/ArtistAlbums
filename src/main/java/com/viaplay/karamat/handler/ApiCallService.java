package com.viaplay.karamat.handler;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viaplay.karamat.model.Description;
import com.viaplay.karamat.model.MusicBrainz;

import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtArchiveClient;
import fm.last.musicbrainz.coverart.impl.DefaultCoverArtArchiveClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiCallService {
	private static final String PROFILE = "discogs";
	CallService callService = new CallService();
	String url = "musicbrainz.org/ws/2/artist/";
	String profileUrl = "api.discogs.com/artists/";

	public MusicBrainz getAlbumLists(String mbId) {
		URIBuilder uri = new URIBuilder().setScheme("https").setHost(this.url + mbId);

		uri.setParameter("fmt", "json");
		uri.setParameter("inc", "url-rels+release-groups");

		String responseStr = callService.sendRequest(uri);
		if (responseStr != null) {
			MusicBrainz apiResponse = callService.deserializeResponse(responseStr);
			if (apiResponse != null) {
				apiResponse.getReleaseGroups().stream().forEach(item -> {
					item.setImage(coverArtApiCall(item.getId()));
				});
				apiResponse.setRelations(apiResponse.getRelations().stream()
						.filter(descr -> descr.getType().equals(PROFILE)).collect(Collectors.toList()));
				return apiResponse;
			}
		}
		return null;
	}

	public String ResourceApiCall(String id) {
		URIBuilder uri = new URIBuilder().setScheme("https").setHost(this.profileUrl + id);

		String responseStr = callService.sendRequest(uri);
		if (responseStr != null) {
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			try {
				return objectMapper.readValue(responseStr, Description.class).getProfile();
			} catch (IOException e) {
				log.error("Error on mapping object into description" + e.getMessage());
			}
		}
		return null;
	}

	public String coverArtApiCall(String id) {
		CoverArtArchiveClient client = new DefaultCoverArtArchiveClient();
		UUID mbid = UUID.fromString(id);
		CoverArt coverArt = null;
		try {
			coverArt = client.getReleaseGroupByMbid(mbid);
			if (coverArt != null) {
				String front = coverArt.getFrontImage().getImageUrl();
				if (front != null)
					return front;
				else
					return coverArt.getImages().get(0).getImageUrl();
			}
		} catch (Exception e) {
			log.error("Error calling CoverArt API." + e.getMessage());
		}
		return null;
	}

}
