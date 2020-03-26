package com.genestack.testkb.mybatis.models

import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import javax.persistence.*

data class MybatisGlobalVersion(
        val id: Int = 0,
        val label: String,
        val description: String,
        val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),
        val authorData: String
)
