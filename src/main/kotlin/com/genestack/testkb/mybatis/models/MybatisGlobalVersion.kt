package com.genestack.testkb.mybatis.models

import java.sql.Timestamp

data class MybatisGlobalVersion(
        val id: Int = 0,
        val label: String,
        val description: String,
        val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),
        val authorData: String
)
