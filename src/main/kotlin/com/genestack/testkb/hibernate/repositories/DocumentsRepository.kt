package com.genestack.testkb.hibernate.repositories

import com.genestack.testkb.hibernate.models.HibernateDocument
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Timestamp

interface DocumentsRepository : JpaRepository<HibernateDocument, Int> {

    @Modifying
    @Query(
            """with current_versions as (
                    select max(d.id) as version
                    from documents d
                    where d.created_at <= :globalVersionCreatedAt
                    group by accession
                )
                update documents set global_versions = global_versions || :globalVersionId
                where id in (select version from current_versions)""",
            nativeQuery = true
    )
    fun updateGlobalVersions(@Param("globalVersionId") globalVersionId: Int,
                             @Param("globalVersionCreatedAt") globalVersionCreatedAt: Timestamp)

    @Query(
            """select * from documents d 
               where :globalVersionId = any(d.global_versions) 
               and d.name like '%' || :name || '%'""",
            nativeQuery = true
    )
    fun findByName(@Param("globalVersionId") globalVersionId: Int,
                   @Param("name") name: String): List<HibernateDocument>
}
