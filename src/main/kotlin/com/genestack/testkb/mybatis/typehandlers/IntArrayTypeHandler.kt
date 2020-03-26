package com.genestack.testkb.mybatis.typehandlers

import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedJdbcTypes
import org.apache.ibatis.type.MappedTypes
import org.springframework.stereotype.Component
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

@Component
@MappedJdbcTypes(JdbcType.ARRAY)
@MappedTypes(IntArray::class)
class IntArrayTypeHandler : BaseTypeHandler<IntArray>() {

    override fun getNullableResult(rs: ResultSet, columnName: String): IntArray {
        return rs.getArray(columnName).toIntArray()
    }

    override fun getNullableResult(rs: ResultSet, columnIndex: Int): IntArray {
        return rs.getArray(columnIndex).toIntArray()
    }

    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): IntArray {
        return cs.getArray(columnIndex).toIntArray()
    }

    override fun setNonNullParameter(ps: PreparedStatement,
                                     i: Int, parameter: IntArray,
                                     jdbcType: JdbcType) {
        val array = ps.connection.createArrayOf("int", parameter.toTypedArray())
        try {
            ps.setArray(i, array)
        } finally {
            array.free()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun java.sql.Array?.toIntArray(): IntArray {
        if (this == null) {
            return IntArray(0)
        }

        return (array as Array<Int>).toIntArray().also {
            this.free()
        }
    }
}
