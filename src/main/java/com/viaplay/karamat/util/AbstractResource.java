package com.viaplay.karamat.util;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK."), 
        @ApiResponse(code = 201, message = "OK. Resource created."),
        @ApiResponse(code = 400, message = "Invalid request parameters."), 
        @ApiResponse(code = 401, message = "Bad credentials."),
        @ApiResponse(code = 403, message = "Forbidden."), 
        @ApiResponse(code = 404, message = "No results."),
        @ApiResponse(code = 500, message = "An unknown error occured.") })
public abstract class AbstractResource<T> {

    protected ResponseEntity<String> composeCreatedResponse(String path, Object pathParameter) {
        URI resourceLocation = ServletUriComponentsBuilder.fromCurrentRequest().path(path).buildAndExpand(pathParameter).toUri();

        return ResponseEntity.created(resourceLocation).build();
    }

    protected ResponseEntity<String> composeDeleteResponse() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    protected ResponseEntity<List<T>> composeResponseEntity(List<T> list, HttpHeaders httpHeaders) {
        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
    }

    protected ResponseEntity<T> composeResponseEntity(T object, HttpHeaders httpHeaders) {
        return new ResponseEntity<>(object, httpHeaders, HttpStatus.OK);
    }



}
