import org.testcontainers.containers.PostgreSQLContainer
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet


class KPostgresContainer : PostgreSQLContainer<KPostgresContainer>("postgres:12") {

    fun getConnection(): Connection {
        return DriverManager.getConnection(getJdbcUrl(), getUsername(), getPassword())
    }

    fun getQueryStats(queryLike: String): QueryStats {
        val connection = getConnection()
        val statement = connection.prepareStatement("""
            select query, calls, mean_time from pg_stat_statements
            where query like ?
            """.trimIndent(),
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        )
        statement.setString(1, queryLike)
        val resultSet = statement.executeQuery().also {
            connection.close()
        }

        resultSet.last()
        if (resultSet.row != 1) {
            throw IllegalStateException("Result set should return only one row")
        }

        val query = resultSet.getString("query")
        val calls = resultSet.getInt("calls")
        val meanTimeMillis = resultSet.getDouble("mean_time")
        return QueryStats(query, calls, meanTimeMillis)
    }

    override fun configure() {
        super.configure()
        setCommand(*commandParts, "-c", "shared_preload_libraries=pg_stat_statements")
    }
}
