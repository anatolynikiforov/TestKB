package com.genestack.testkb.jooq.repositories

import com.genestack.testkb.Tables.DOCUMENTS
import com.genestack.testkb.tables.pojos.Documents
import org.jooq.DSLContext
import org.jooq.Record5
import org.jooq.SelectJoinStep
import org.jooq.impl.DSL
import org.jooq.util.postgres.PostgresDSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.PathVariable
import java.sql.Timestamp

@Repository
class JooqDocumentsRepository @Autowired constructor(
        private val jooqGlobalVersionsRepository: JooqGlobalVersionsRepository,
        private val dslContext: DSLContext
) {

    fun findById(@PathVariable id: Int): Documents? {
        return dslContext.selectDocuments()
                .where(DOCUMENTS.ID.eq(id))
                .fetchOneInto(Documents::class.java)
    }

    fun findByName(globalVersionId: Int, name: String): List<Documents> {
        return dslContext.selectDocuments()
                .where(
                        DSL.`val`(globalVersionId).eq(DSL.any(DOCUMENTS.GLOBAL_VERSIONS))
                )
                .and(DOCUMENTS.NAME.like("%$name%"))
                .fetchInto(Documents::class.java)
    }

    private fun DSLContext.selectDocuments():
            SelectJoinStep<Record5<Int, String, Array<Int>, Timestamp, String>> {
        return dslContext.select(
                DOCUMENTS.ID,
                DOCUMENTS.ACCESSION,
                DOCUMENTS.GLOBAL_VERSIONS,
                DOCUMENTS.CREATED_AT,
                DOCUMENTS.NAME
        )
                .from(DOCUMENTS)
    }

    fun updateGlobalVersions(id: Int, createdAt: Timestamp) {
        dslContext.update(DOCUMENTS).set(
                DOCUMENTS.GLOBAL_VERSIONS, PostgresDSL.arrayAppend(DOCUMENTS.GLOBAL_VERSIONS, id)
        ).where(
                DOCUMENTS.ID.`in`(
                        dslContext.select(DSL.max(DOCUMENTS.ID))
                                .from(DOCUMENTS)
                                .where(DOCUMENTS.CREATED_AT.le(createdAt))
                                .groupBy(DOCUMENTS.ACCESSION)

                )
        ).execute()
    }
}
