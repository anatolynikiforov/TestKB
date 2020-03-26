package com.genestack.testkb.hibernate.services

import com.genestack.testkb.dto.GlobalVersionInputDto
import com.genestack.testkb.hibernate.models.HibernateGlobalVersion
import com.genestack.testkb.hibernate.repositories.DocumentsRepository
import com.genestack.testkb.hibernate.repositories.GlobalVersionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HibernateGlobalVersionsService @Autowired constructor(
        private val documentsService: HibernateDocumentsService,
        private val globalVersionsRepository: GlobalVersionsRepository,
        private val documentsRepository: DocumentsRepository
) {

    @Transactional
    fun save(input: GlobalVersionInputDto) {
        var globalVersion = globalVersionsRepository.findByLabel(input.label)
        if (globalVersion != null) {
            globalVersion.description = input.description
            globalVersionsRepository.save(globalVersion)
            return
        }

        globalVersion = globalVersionsRepository.save(HibernateGlobalVersion(
                label = input.label,
                description = input.description,
                authorData = "dratuti author"
        ))

        documentsRepository.updateGlobalVersions(globalVersion.id!!, globalVersion.createdAt!!)
    }
}
