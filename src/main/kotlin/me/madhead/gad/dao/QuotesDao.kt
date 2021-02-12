package me.madhead.gad.dao

import me.madhead.gad.entity.Quote

interface QuotesDao {
    fun list(): List<Quote>
}
