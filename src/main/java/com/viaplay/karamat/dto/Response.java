package com.viaplay.karamat.dto;

import java.util.List;

import com.viaplay.karamat.model.Album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

	String mbId;
	String description;
	List<Album> albums;
}
