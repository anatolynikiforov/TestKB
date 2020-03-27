package com.genestack.testkb.jooq.services

import com.genestack.testkb.dto.GlobalVersionInputDto
import com.genestack.testkb.jooq.repositories.JooqDocumentsRepository
import com.genestack.testkb.jooq.repositories.JooqGlobalVersionsRepository
import com.genestack.testkb.tables.pojos.GlobalVersions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

@Service
class JooqGlobalVersionsService @Autowired constructor(
        private val jooqGlobalVersionsRepository: JooqGlobalVersionsRepository,
        private val jooqDocumentsRepository: JooqDocumentsRepository
) {
    @Transactional
    fun save(input: GlobalVersionInputDto) {
        var globalVersion = jooqGlobalVersionsRepository.findByLabel(input.label)
        if (globalVersion != null) {
            val updatedGlobalVersion = GlobalVersions(
                    globalVersion.id,
                    globalVersion.label,
                    input.description,
                    globalVersion.createdAt,
                    globalVersion.authorData
            )
            jooqGlobalVersionsRepository.save(updatedGlobalVersion)
            return
        }

        globalVersion = jooqGlobalVersionsRepository.save(GlobalVersions(
                null,
                input.label,
                input.description,
                Timestamp(System.currentTimeMillis()),
                "dratuti author"
        ))

        jooqDocumentsRepository.updateGlobalVersions(globalVersion.id, globalVersion.createdAt)
    }
}
