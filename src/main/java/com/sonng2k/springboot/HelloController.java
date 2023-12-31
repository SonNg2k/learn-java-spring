package com.sonng2k.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Create a web controller

// @RestController tells Spring that this class is a Component of type RestController and capable of handling
// HTTP requests.

// @GetMapping marks a method as a handler method

// The class is flagged as a @RestController, meaning it is ready for use by Spring MVC to handle web
// requests.
// @GetMapping maps / to the index() method. When invoked from a browser or by using curl on the command line,
// the method returns pure text. That is because @RestController combines @Controller and @ResponseBody, two
// annotations that result in web requests returning data rather than a view.

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
