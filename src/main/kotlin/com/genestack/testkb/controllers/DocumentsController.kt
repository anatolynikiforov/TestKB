package com.genestack.testkb.controllers

interface DocumentsController<T> {
    fun findById(id: Int): T?
    fun findByName(globalVersion: String, name: String): List<T>
}
