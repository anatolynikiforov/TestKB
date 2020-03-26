package com.genestack.testkb.controllers

import com.genestack.testkb.dto.GlobalVersionInputDto

interface GlobalVersionsController<T> {
    fun all(): List<T>
    fun save(input: GlobalVersionInputDto)
}
