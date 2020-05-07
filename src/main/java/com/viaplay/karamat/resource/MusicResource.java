package com.viaplay.karamat.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viaplay.karamat.dto.Response;
import com.viaplay.karamat.handler.ArtistHandler;
import com.viaplay.karamat.util.AbstractResource;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/v1")
@RestController
@EnableAutoConfiguration
public class MusicResource extends AbstractResource<Response>  {
	
@Autowired
private ArtistHandler artistHandler;

public  MusicResource(ArtistHandler artistHandler) {
	this.artistHandler = artistHandler;
}
	
	@RequestMapping(value="/artist/{mbId}", method = RequestMethod.GET, produces = { "application/json" }  )
	@ApiOperation(value="list artist albums")	
	protected Response getallData( @PathVariable String mbId) throws Exception {  
	        return this.artistHandler.artistAlbums(mbId);
	}
	
	
}
