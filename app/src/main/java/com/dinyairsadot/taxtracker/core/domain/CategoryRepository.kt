package com.dinyairsadot.taxtracker.core.domain

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
    suspend fun addCategory(category: Category)
}