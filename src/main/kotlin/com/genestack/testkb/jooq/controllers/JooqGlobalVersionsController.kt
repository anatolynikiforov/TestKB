package com.genestack.testkb.jooq.controllers

import com.genestack.testkb.controllers.GlobalVersionsController
import com.genestack.testkb.dto.GlobalVersionInputDto
import com.genestack.testkb.jooq.repositories.JooqGlobalVersionsRepository
import com.genestack.testkb.jooq.services.JooqGlobalVersionsService
import com.genestack.testkb.tables.pojos.GlobalVersions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/jooq/global_versions")
class JooqGlobalVersionsController @Autowired constructor(
        private val jooqGlobalVersionsRepository: JooqGlobalVersionsRepository,
        private val jooqGlobalVersionsService: JooqGlobalVersionsService
) : GlobalVersionsController<GlobalVersions> {

    @GetMapping
    override fun all(): List<GlobalVersions> {
        return jooqGlobalVersionsRepository.findAll()
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    override fun save(@RequestBody input: GlobalVersionInputDto) {
        jooqGlobalVersionsService.save(input)
    }
}
