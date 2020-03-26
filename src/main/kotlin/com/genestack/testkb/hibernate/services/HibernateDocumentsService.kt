package com.genestack.testkb.hibernate.services

import com.genestack.testkb.hibernate.models.HibernateDocument
import com.genestack.testkb.hibernate.repositories.DocumentsRepository
import com.genestack.testkb.hibernate.repositories.GlobalVersionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HibernateDocumentsService @Autowired constructor(
        private val documentsRepository: DocumentsRepository,
        private val globalVersionsRepository: GlobalVersionsRepository
) {

    @Transactional
    fun findByName(globalVersionLabel: String, name: String): List<HibernateDocument> {
        if (name.isBlank()) {
            throw throw IllegalArgumentException("Document name can't be blank")
        }

        val globalVersion = globalVersionsRepository.findByLabel(globalVersionLabel)
                ?: throw IllegalArgumentException(
                        "Global version with label: $globalVersionLabel not found"
                )

        return documentsRepository.findByName(globalVersion.id!!, name)
    }
}
