package com.genestack.testkb.hibernate.controllers

import com.genestack.testkb.controllers.DocumentsController
import com.genestack.testkb.hibernate.models.HibernateDocument
import com.genestack.testkb.hibernate.repositories.DocumentsRepository
import com.genestack.testkb.hibernate.services.HibernateDocumentsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hibernate/documents")
class HibernateDocumentsController @Autowired constructor(
        private val documentsService: HibernateDocumentsService,
        private val documentsRepository: DocumentsRepository
) : DocumentsController<HibernateDocument> {

    @GetMapping("/{id}")
    override fun findById(@PathVariable id: Int): HibernateDocument? {
        return documentsRepository.findByIdOrNull(id)
    }

    @GetMapping
    override fun findByName(
            @RequestParam("globalVersion") globalVersion: String,
            @RequestParam("name") name: String): List<HibernateDocument> {
        return documentsService.findByName(globalVersion, name)
    }
}
