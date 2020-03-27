package com.genestack.testkb.mybatis.controllers

import com.genestack.testkb.controllers.GlobalVersionsController
import com.genestack.testkb.dto.GlobalVersionInputDto
import com.genestack.testkb.mybatis.mappers.GlobalVersionsMapper
import com.genestack.testkb.mybatis.models.MybatisGlobalVersion
import com.genestack.testkb.mybatis.services.MybatisGlobalVersionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mybatis/global_versions")
class MybatisGlobalVersionsController @Autowired constructor(
        private val mybatisGlobalVersionsService: MybatisGlobalVersionsService,
        private val globalVersionsMapper: GlobalVersionsMapper
) : GlobalVersionsController<MybatisGlobalVersion> {

    @GetMapping
    override fun all(): List<MybatisGlobalVersion> {
        return globalVersionsMapper.findAll()
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    override fun save(@RequestBody input: GlobalVersionInputDto) {
        mybatisGlobalVersionsService.save(input)
    }
}
