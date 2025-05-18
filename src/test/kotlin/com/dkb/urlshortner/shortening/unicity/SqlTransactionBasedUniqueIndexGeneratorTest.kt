package com.dkb.urlshortner.shortening.unicity

import com.dkb.urlshortner.shortening.unicity.postgres.index.SqlTransactionBasedUniqueIndexGenerator
import com.dkb.urlshortner.shortening.unicity.postgres.index.model.AutoIncrementedIndex
import com.dkb.urlshortner.shortening.unicity.postgres.index.repository.AutoIncrementedIndexRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever
import java.util.*

internal class SqlTransactionBasedUniqueIndexGeneratorTest {

    private val repo = mock<AutoIncrementedIndexRepository>()
    private val generator = SqlTransactionBasedUniqueIndexGenerator(repo)

    @Test
    fun `nextIndex increments and saves`() {
        // given
        val record = AutoIncrementedIndex(id = 1L, currentIndex = 42L)
        whenever(repo.findById(1L)).thenReturn(Optional.of(record))

        // when
        val next = generator.nextIndex()

        // then
        assertEquals(43L, next, "Should return incremented index")
        // record itself was mutated
        assertEquals(43L, record.currentIndex)

        // verify save was called with the updated record
        verify(repo).save(record)
    }

    @Test
    fun `nextIndex when record missing throws NoSuchElementException`() {
        whenever(repo.findById(1L)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            generator.nextIndex()
        }
        verify(repo, never()).save(any())
    }
}
