package com.viaplay.karamat;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viaplay.karamat.dto.Response;
import com.viaplay.karamat.handler.ArtistHandler;
import com.viaplay.karamat.model.Album;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControlerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArtistHandler artistHandler;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Response response = new Response("123", "some description comes here", new ArrayList<Album>());
		when(artistHandler.artistAlbums("123")).thenReturn(response);
	}

	@Test
	public void whenValidInput_thenReturns200() throws Exception {
		String x = this.mockMvc.perform(get("/v1/artist/123")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andReturn().getResponse()
				.getContentAsString();
		Response res = new ObjectMapper().readValue(x, Response.class);
		assertEquals(res.getMbId(), "123");
		assertEquals(res.getDescription(), "some description comes here");
		assertEquals(res.getAlbums().size(), 0);
	}

}
