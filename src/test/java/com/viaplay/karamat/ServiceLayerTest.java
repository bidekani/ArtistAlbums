package com.viaplay.karamat;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.viaplay.karamat.dto.Response;
import com.viaplay.karamat.handler.ApiCallService;
import com.viaplay.karamat.handler.ArtistHandler;
import com.viaplay.karamat.model.Album;
import com.viaplay.karamat.model.MusicBrainz;
import com.viaplay.karamat.model.Resource;
import com.viaplay.karamat.model.ResourceUrl;

@RunWith(MockitoJUnitRunner.class)
public class ServiceLayerTest {

	@InjectMocks
	ArtistHandler handler;

	@Mock
	ApiCallService artistService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void MusicBrainzApiCallTest() throws Exception {
		List<Album> releaseGroups = new ArrayList<>();
		List<Resource> relations = new ArrayList<>();
		MusicBrainz musicBrainz = new MusicBrainz();

		Album alb1 = new Album("1", "11", "111");
		Album alb2 = new Album("2", "22", "222");
		Album alb3 = new Album("3", "33", "333");

		releaseGroups.add(alb1);
		releaseGroups.add(alb2);
		releaseGroups.add(alb3);

		Resource res1 = new Resource();
		res1.setType("test");
		res1.setUrl(new ResourceUrl("123", "api.discogs.com/artists/123"));

		relations.add(res1);

		musicBrainz.setRelations(relations);
		musicBrainz.setReleaseGroups(releaseGroups);

		when(artistService.getAlbumLists("123")).thenReturn(musicBrainz);
		when(artistService.ResourceApiCall("123")).thenReturn("this is a test profile");

		// test
		Response test1 = handler.artistAlbums("123");
		assertEquals(test1.getAlbums().size(), 3);
		assertEquals(test1.getDescription(), "this is a test profile");

	}

}
