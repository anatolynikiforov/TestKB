package com.genestack.testkb.mybatis.mappers

import com.genestack.testkb.hibernate.models.HibernateDocument
import com.genestack.testkb.mybatis.models.MybatisDocument
import org.apache.ibatis.annotations.*
import org.apache.ibatis.type.ArrayTypeHandler
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.sql.Timestamp

@Mapper
interface DocumentsMapper {
    @Update(
            """with current_versions as (
                    select max(d.id) as version
                    from documents d
                    where d.created_at <= #{globalVersionCreatedAt}
                    group by accession
                )
                update documents set global_versions = global_versions || #{globalVersionId}
                where id in (select version from current_versions)"""
    )
    fun updateGlobalVersions(@Param("globalVersionId") globalVersionId: Int,
                             @Param("globalVersionCreatedAt") globalVersionCreatedAt: Timestamp)

    @Select("""select d.* from documents d
               where #{globalVersionId} = any(d.global_versions)
               and d.name like '%' || #{name} || '%'""")
    fun findByName(@Param("globalVersionId") globalVersionId: Int,
                   @Param("name") name: String): List<MybatisDocument>

    @Select("select * from documents d where d.id = #{id}")
    fun findById(@Param("id") id: Int): MybatisDocument?
}
