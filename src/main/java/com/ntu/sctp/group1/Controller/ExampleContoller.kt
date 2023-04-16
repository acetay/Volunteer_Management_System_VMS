package com.ntu.sctp.group1.Controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/example")
class ExampleController {

    @GetMapping
    fun getExample(): ResponseEntity<String> {
        return ResponseEntity.ok("This is an example GET request")
    }
}