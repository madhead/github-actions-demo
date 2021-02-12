package me.madhead.gad.service

import me.madhead.gad.dao.QuotesDao
import me.madhead.gad.entity.Quote
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class QuotesServiceTest {
    @Test
    fun `getRandomQuote should return a quote`() {
        val sut = QuotesService(object : QuotesDao {
            override fun list(): List<Quote> {
                return listOf(
                        Quote("1", "Quote 1"),
                        Quote("2", "Quote 2"),
                        Quote("3", "Quote 3"),
                )
            }
        })

        Assertions.assertNotNull(sut.getRandomQuote())
    }

    @Test
    fun `getRandomQuote should fail when there are not quotes`() {
        val sut = QuotesService(object : QuotesDao {
            override fun list(): List<Quote> {
                return emptyList()
            }
        })

        Assertions.assertThrows(NoSuchElementException::class.java, sut::getRandomQuote)
    }
}
