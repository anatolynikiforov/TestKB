package global_versions_comparison

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class GlobalVersionsDocumentsTableTest :
        BaseGlobalVersionsTest("global_versions_documents_table_init.sql") {

    @Test
    fun `Global versions documents table search documents for specific global version`() {
        executeGlobalVersionsQuery("""
            select * from documents d
            join documents_global_versions dgv on d.id = dgv.document_id
            join global_versions g on dgv.global_version_id = g.id
            where label = ?
            and d.name like '%12345%';
        """.trimIndent())

        val queryStats = postgres.getQueryStats(
                queryLike = "%join documents_global_versions dgv on d.id = dgv.document_id%"
        )

        assertEquals(versions.size, queryStats.calls)
        queryStats.print(
                "Global versions documents table search documents for specific global version"
        )
    }
}

