package com.genestack.testkb.jooq.services

import com.genestack.testkb.jooq.repositories.JooqDocumentsRepository
import com.genestack.testkb.jooq.repositories.JooqGlobalVersionsRepository
import com.genestack.testkb.tables.pojos.Documents
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JooqDocumentsService @Autowired constructor(
        private val jooqDocumentsRepository: JooqDocumentsRepository,
        private val jooqGlobalVersionsRepository: JooqGlobalVersionsRepository
) {

    @Transactional
    fun findByName(globalVersionLabel: String, name: String): List<Documents> {
        if (name.isBlank()) {
            throw throw IllegalArgumentException("Document name can't be blank")
        }

        val globalVersion = jooqGlobalVersionsRepository.findByLabel(globalVersionLabel)
                ?: throw IllegalArgumentException(
                        "Global version with label: $globalVersionLabel not found"
                )


        return jooqDocumentsRepository.findByName(globalVersion.id, name)
    }
}
