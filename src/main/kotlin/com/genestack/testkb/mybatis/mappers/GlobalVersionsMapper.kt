package com.genestack.testkb.mybatis.mappers

import com.genestack.testkb.mybatis.models.MybatisGlobalVersion
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface GlobalVersionsMapper {

    @Select("SELECT * FROM global_versions")
    fun findAll(): List<MybatisGlobalVersion>

    @Select("SELECT * FROM global_versions WHERE label = #{label}")
    fun findByLabel(label: String): MybatisGlobalVersion?

    @Update("UPDATE global_versions SET description = #{description} WHERE id = #{id}")
    fun update(globalVersion: MybatisGlobalVersion)

    // Select used instead of Insert in order to return value
    // https://github.com/mybatis/mybatis-3/issues/1293
    @Select("""
            INSERT INTO global_versions(label, description, created_at, author_data) 
            VALUES(#{label}, #{description}, #{createdAt}, #{authorData})
            RETURNING *
    """)
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    fun insert(globalVersion: MybatisGlobalVersion): MybatisGlobalVersion
}
