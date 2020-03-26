package com.genestack.testkb.hibernate.repositories

import com.genestack.testkb.hibernate.models.HibernateGlobalVersion
import org.springframework.data.jpa.repository.JpaRepository

interface GlobalVersionsRepository: JpaRepository<HibernateGlobalVersion, Int> {

    fun findByLabel(label: String): HibernateGlobalVersion?
}
