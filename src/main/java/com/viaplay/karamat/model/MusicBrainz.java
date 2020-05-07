package com.viaplay.karamat.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MusicBrainz {

	@JsonProperty("release-groups")
	List<Album> releaseGroups;
	List<Resource> relations;
	
}
