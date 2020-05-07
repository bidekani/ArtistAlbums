package com.viaplay.karamat.springsetup;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class IndexController {

    @GetMapping("/index")
    public ResourceSupport index() {
        ResourceSupport index = new ResourceSupport();
        index.add(new Link("/index.html"));
        return index;
    }
}
