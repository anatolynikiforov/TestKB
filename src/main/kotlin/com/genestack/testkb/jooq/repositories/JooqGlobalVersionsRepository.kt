package com.genestack.testkb.jooq.repositories

import com.genestack.testkb.Tables.GLOBAL_VERSIONS
import com.genestack.testkb.tables.pojos.GlobalVersions
import com.genestack.testkb.tables.records.GlobalVersionsRecord
import org.jooq.DSLContext
import org.jooq.Record5
import org.jooq.SelectJoinStep
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class JooqGlobalVersionsRepository @Autowired constructor(private val dslContext: DSLContext) {
    fun findAll(): List<GlobalVersions> {
        return dslContext.selectGlobalVersions().fetchInto(GlobalVersions::class.java)
    }

    fun findByLabel(label: String): GlobalVersions? {
        return dslContext.selectGlobalVersions()
                .where(GLOBAL_VERSIONS.LABEL.eq(label))
                .fetchOneInto(GlobalVersions::class.java)
    }

    // I guess it could be simpler
    fun save(globalVersions: GlobalVersions): GlobalVersions {
        val record: GlobalVersionsRecord
        if (globalVersions.id != null) {
            record = dslContext.update(GLOBAL_VERSIONS)
                    .set(GLOBAL_VERSIONS.DESCRIPTION, globalVersions.description)
                    .where(GLOBAL_VERSIONS.ID.eq(globalVersions.id))
                    .returning()
                    .fetchOne()
        } else {
            record = dslContext.insertInto(GLOBAL_VERSIONS)
                    .columns(GLOBAL_VERSIONS.LABEL,
                            GLOBAL_VERSIONS.DESCRIPTION,
                            GLOBAL_VERSIONS.CREATED_AT,
                            GLOBAL_VERSIONS.AUTHOR_DATA
                    )
                    .values(
                            globalVersions.label,
                            globalVersions.description,
                            globalVersions.createdAt,
                            globalVersions.authorData
                    ).returning()
                    .fetchOne()
        }

        return GlobalVersions(
                record.id, record.label, record.description, record.createdAt, record.authorData
        )
    }

    private fun DSLContext.selectGlobalVersions():
            SelectJoinStep<Record5<Int, String, String, Timestamp, String>> {
        return dslContext.select(
                GLOBAL_VERSIONS.ID,
                GLOBAL_VERSIONS.LABEL,
                GLOBAL_VERSIONS.DESCRIPTION,
                GLOBAL_VERSIONS.CREATED_AT,
                GLOBAL_VERSIONS.AUTHOR_DATA
        )
                .from(GLOBAL_VERSIONS)
    }
}
