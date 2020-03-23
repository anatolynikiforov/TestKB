package com.genestack.testkb


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

//TODO https://genestack.atlassian.net/browse/JBKB-773
fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
