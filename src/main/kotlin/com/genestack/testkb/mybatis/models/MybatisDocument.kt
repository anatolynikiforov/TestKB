package com.genestack.testkb.mybatis.models

import org.hibernate.annotations.Type
import java.sql.Timestamp
import javax.persistence.*

data class MybatisDocument(
        val id: Int,
        val accession: String,
        val globalVersions: IntArray,
        val createdAt: Timestamp,
        val name: String
)
