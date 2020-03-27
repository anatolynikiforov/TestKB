package com.genestack.testkb.jooq.controllers

import com.genestack.testkb.controllers.DocumentsController
import com.genestack.testkb.jooq.repositories.JooqDocumentsRepository
import com.genestack.testkb.jooq.services.JooqDocumentsService
import com.genestack.testkb.tables.pojos.Documents
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/jooq/documents")
class JooqDocumentsController @Autowired constructor(
        private val jooqDocumentsService: JooqDocumentsService,
        private val jooqDocumentsRepository: JooqDocumentsRepository
) : DocumentsController<Documents> {

    @GetMapping("/{id}")
    override fun findById(@PathVariable id: Int): Documents? {
        return jooqDocumentsRepository.findById(id)
    }

    @GetMapping
    override fun findByName(globalVersion: String, name: String): List<Documents> {
        return jooqDocumentsService.findByName(globalVersion, name)
    }
}
