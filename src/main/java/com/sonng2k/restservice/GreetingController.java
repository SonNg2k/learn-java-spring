package com.sonng2k.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

// Handles GET requests for /greeting by returning a new instance of the Greeting class

// The @GetMapping annotation ensures that HTTP GET requests to /greeting are mapped to the greeting() method.

// @RequestParam binds the value of the query string parameter `name` into the `name` parameter of the greeting
// () method. If the name parameter is absent in the request, the `defaultValue` of `World` is used.

// The implementation of the method body creates and returns a new Greeting object with id and content
// attributes based on the next value from the counter and formats the given name by using the greeting
// template.

// A key difference between a traditional MVC controller and the RESTful web service controller shown
// here is the way that the HTTP response body is created. Rather than relying on a view technology to
// perform server-side rendering of the greeting data to HTML, this RESTful web service controller populates
// and returns a Greeting object. The object data will be written directly to the HTTP response as JSON.

// This code uses Spring @RestController annotation, which marks the class as a controller where every
// method returns a domain object instead of a view. It is shorthand for including both @Controller and
// @ResponseBody.

// Notice also how the id attribute has changed from 1 to 2 on the second request. This proves that you are
// working against the same GreetingController instance across multiple requests and that its counter field is
// being incremented on each call as expected.

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
