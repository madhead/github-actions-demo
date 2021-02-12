package me.madhead.gad.service

import me.madhead.gad.dao.QuotesDao

class QuotesService(
        private val quotesDao: QuotesDao
) {
    fun getRandomQuote(): String {
        return quotesDao.list().random().quote
    }
}
