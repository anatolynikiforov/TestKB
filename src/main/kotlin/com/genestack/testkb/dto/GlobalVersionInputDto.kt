package com.genestack.testkb.dto

import org.springframework.data.annotation.CreatedDate
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

data class GlobalVersionInputDto(
        val label: String,
        val description: String,
        val authorData: String
)
