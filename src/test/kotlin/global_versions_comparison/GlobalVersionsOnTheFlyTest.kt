package global_versions_comparison

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class GlobalVersionsOnTheFlyTest : BaseGlobalVersionsTest("global_versions_on_the_fly_init.sql") {

    @Test
    fun `On-the-fly search documents for specific global version`() {
        executeGlobalVersionsQuery("""
            with current_versions as (
                select max(d.id) as version
                from documents d
                join global_versions gv on d.createdAt <= gv.createdAt
                where gv.label = ?
                group by accession
            )
            select * from documents d 
            join current_versions cv on d.id = cv.version 
            where name like '%12345%'
        """.trimIndent())

        val queryStats = postgres.getQueryStats(queryLike = "with current_versions as (%")
        assertEquals(versions.size, queryStats.calls)

        queryStats.print("On-the-fly search documents for specific global version")
    }
}

