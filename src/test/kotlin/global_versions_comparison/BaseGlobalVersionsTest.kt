package global_versions_comparison

import KPostgresContainer
import QueryStats
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import java.sql.Connection

open class BaseGlobalVersionsTest(initScriptPath: String) {

    @Rule
    val postgres: KPostgresContainer = KPostgresContainer()
            .withInitScript(initScriptPath)

    lateinit var connection: Connection
    lateinit var versions: List<String>

    @BeforeEach
    fun setUp() {
        postgres.start()
        connection = postgres.getConnection()
        versions = listOf("v1", "v10", "v25", "v50", "v75", "v100")
    }

    @AfterEach
    fun tearDown() {
        connection.close()
        postgres.stop()
    }

    fun executeGlobalVersionsQuery(query: String) {
        val statement = connection.prepareStatement(query)
        for (version in versions) {
            statement.setString(1, version)
            statement.execute()
        }

        statement.close()
    }
}
