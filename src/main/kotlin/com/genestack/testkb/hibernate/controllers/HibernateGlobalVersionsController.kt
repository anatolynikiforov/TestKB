package com.genestack.testkb.hibernate.controllers

import com.genestack.testkb.controllers.GlobalVersionsController
import com.genestack.testkb.dto.GlobalVersionInputDto
import com.genestack.testkb.hibernate.models.HibernateGlobalVersion
import com.genestack.testkb.hibernate.repositories.GlobalVersionsRepository
import com.genestack.testkb.hibernate.services.HibernateGlobalVersionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hibernate/global_versions")
class HibernateGlobalVersionsController @Autowired constructor(
        private val globalVersionsService: HibernateGlobalVersionsService,
        private val globalVersionsRepository: GlobalVersionsRepository)
    : GlobalVersionsController<HibernateGlobalVersion> {

    @GetMapping
    override fun all(): List<HibernateGlobalVersion> {
        return globalVersionsRepository.findAll()
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    override fun save(@RequestBody input: GlobalVersionInputDto) {
        globalVersionsService.save(input)
    }
}
