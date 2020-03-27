package com.genestack.testkb.mybatis.models

import java.sql.Timestamp

data class MybatisDocument(
        val id: Int,
        val accession: String,
        val globalVersions: IntArray,
        val createdAt: Timestamp,
        val name: String
)
