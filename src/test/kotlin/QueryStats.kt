data class QueryStats(val query: String, val calls: Int, val meanTimeMillis: Double) {
    fun print(header: String = "") {
        println("""
            |------------------------------------------------
            |------------------------------------------------
            | $header
            |------------------------------------------------
            |query: $query
            |------------------------------------------------
            |called: $calls times
            |------------------------------------------------
            |meanTime: $meanTimeMillis ms
            |------------------------------------------------
            |------------------------------------------------
        """.trimMargin()
        )
    }
}