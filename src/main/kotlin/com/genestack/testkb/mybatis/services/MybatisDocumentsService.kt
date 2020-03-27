package com.genestack.testkb.mybatis.services

import com.genestack.testkb.mybatis.mappers.DocumentsMapper
import com.genestack.testkb.mybatis.mappers.GlobalVersionsMapper
import com.genestack.testkb.mybatis.models.MybatisDocument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MybatisDocumentsService @Autowired constructor(
        private val documentsMapper: DocumentsMapper,
        private val globalVersionsMapper: GlobalVersionsMapper
) {
    @Transactional
    fun findByName(globalVersionLabel: String, name: String): List<MybatisDocument> {
        if (name.isBlank()) {
            throw throw IllegalArgumentException("Document name can't be blank")
        }

        val globalVersion = globalVersionsMapper.findByLabel(globalVersionLabel)
                ?: throw IllegalArgumentException(
                        "Global version with label: $globalVersionLabel not found"
                )

        return documentsMapper.findByName(globalVersion.id, name)
    }
}
