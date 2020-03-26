package com.genestack.testkb.hibernate.models

import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "global_versions")
class HibernateGlobalVersion(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var label: String,
        var description: String,
        @CreationTimestamp
        var createdAt: Timestamp? = null,
        var authorData: String
)
