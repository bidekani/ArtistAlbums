package com.viaplay.karamat.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.viaplay.karamat.dto.Response;
import com.viaplay.karamat.model.MusicBrainz;
import com.viaplay.karamat.util.NoResourceException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArtistHandler {
	@Autowired
	ApiCallService apiCallService;
	private Response response = new Response();

	@Cacheable(value = "artistCache")
	public Response artistAlbums(String mbId) {

		MusicBrainz resp = null;

		try {
			resp = apiCallService.getAlbumLists(mbId);
			response.setMbId(mbId);
			response.setAlbums(resp.getReleaseGroups());
			response.setDescription(getProfile(resp));
		} catch (Exception e) {
			log.error("error getting alubumlist for  artist with MBID = " + mbId + e.getMessage() + ".");
			throw new NoResourceException(
					"error getting alubumlist for  artist with MBID = " + mbId + e.getMessage() + ".");
		}
		return response;
	}

	private String getProfile(MusicBrainz resp) throws Exception {
		// pattern to extract id from 'discogs' resource
		Pattern profileIdpattern = Pattern.compile("(\\D+)(\\d+)");
		if (resp.getRelations().get(0) != null) {
			String urlStr = resp.getRelations().get(0).getUrl().getResource();
			Matcher m = profileIdpattern.matcher(urlStr);
			if (m.matches())
				return apiCallService.ResourceApiCall(m.group(2));
		}
		throw new NoResourceException("Can Not Read Profile from given URL");
	}

	@Scheduled(cron = "0 45 23 ? * SAT")
	@CacheEvict(value = "artistCache", allEntries = true)
	protected void clearCache() {
		log.info("cach cleared.");
	}

}
