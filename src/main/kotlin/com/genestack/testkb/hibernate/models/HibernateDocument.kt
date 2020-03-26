package com.genestack.testkb.hibernate.models

import org.hibernate.annotations.Type
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "documents")
class HibernateDocument(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int,
        var accession: String,

        @Type(type = "com.vladmihalcea.hibernate.type.array.ListArrayType")
        @Column(columnDefinition = "int[]")
        var globalVersions: List<Int>,
        var createdAt: Timestamp,
        var name: String
)
