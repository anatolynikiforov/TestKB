package com.genestack.testkb.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DratutiController {

    @GetMapping("/dratuti")
    fun getDratuti(): String {
        return "dratuti ${System.currentTimeMillis()}"
    }
}
