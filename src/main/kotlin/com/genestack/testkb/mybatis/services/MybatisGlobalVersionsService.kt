package com.genestack.testkb.mybatis.services

import com.genestack.testkb.dto.GlobalVersionInputDto
import com.genestack.testkb.hibernate.models.HibernateGlobalVersion
import com.genestack.testkb.hibernate.repositories.DocumentsRepository
import com.genestack.testkb.hibernate.repositories.GlobalVersionsRepository
import com.genestack.testkb.mybatis.mappers.DocumentsMapper
import com.genestack.testkb.mybatis.mappers.GlobalVersionsMapper
import com.genestack.testkb.mybatis.models.MybatisGlobalVersion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MybatisGlobalVersionsService @Autowired constructor(
        private val globalVersionsMapper: GlobalVersionsMapper,
        private val documentsMapper: DocumentsMapper
) {

    @Transactional
    fun save(input: GlobalVersionInputDto) {
        var globalVersion = globalVersionsMapper.findByLabel(input.label)
        if (globalVersion != null) {
            val updatedGlobalVersion = globalVersion.copy(description = input.description)
            globalVersionsMapper.update(updatedGlobalVersion)
            return
        }

        globalVersion = globalVersionsMapper.insert(MybatisGlobalVersion(
                label = input.label,
                description = input.description,
                authorData = "dratuti author"
        ))

        documentsMapper.updateGlobalVersions(globalVersion.id, globalVersion.createdAt)
    }
}
