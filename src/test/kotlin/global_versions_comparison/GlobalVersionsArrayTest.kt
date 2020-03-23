package global_versions_comparison

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class GlobalVersionsArrayTest : BaseGlobalVersionsTest("global_versions_array_init.sql") {

    @Test
    fun `Global versions array search documents for specific global version`() {
        executeGlobalVersionsQuery("""
            select * from documents d
            join global_versions g on g.id = any(d.global_versions)
            where g.label = ?
            and d.name like '%12345%';
        """.trimIndent())

        val queryStats = postgres.getQueryStats(
                queryLike = "%join global_versions g on g.id = any(d.global_versions)%"
        )

        assertEquals(versions.size, queryStats.calls)
        queryStats.print("Global versions array search documents for specific global version")
    }
}

