package com.genestack.testkb.mybatis.controllers

import com.genestack.testkb.controllers.DocumentsController
import com.genestack.testkb.mybatis.mappers.DocumentsMapper
import com.genestack.testkb.mybatis.models.MybatisDocument
import com.genestack.testkb.mybatis.services.MybatisDocumentsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mybatis/documents")
class MyBatisDocumentsController @Autowired constructor(
        private val mybatisDocumentsService: MybatisDocumentsService,
        private val documentsMapper: DocumentsMapper
) : DocumentsController<MybatisDocument> {

    @GetMapping("/{id}")
    override fun findById(@PathVariable id: Int): MybatisDocument? {
        return documentsMapper.findById(id)
    }

    @GetMapping
    override fun findByName(
            @RequestParam("globalVersion") globalVersion: String,
            @RequestParam("name") name: String): List<MybatisDocument> {
        return mybatisDocumentsService.findByName(globalVersion, name)
    }
}
