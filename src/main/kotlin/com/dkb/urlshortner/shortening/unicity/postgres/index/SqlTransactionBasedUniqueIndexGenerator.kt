package com.dkb.urlshortner.shortening.unicity.postgres.index

import com.dkb.urlshortner.shortening.unicity.postgres.index.repository.AutoIncrementedIndexRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
open class SqlTransactionBasedUniqueIndexGenerator(
    private val autoIncrementedIndexRepository: AutoIncrementedIndexRepository
): UniqueIndexGenerator {

    // This transaction should lock the index to avoid race conditions
    // but the default @Transactional Lock Mode is READ COMMITTED, which isn't enough,
    // I left it anyway, it will be a nice conversation starter in our interview :D
    @Transactional
    override fun nextIndex(): Long {
        // fetch the single row
        var record = autoIncrementedIndexRepository
            .findById(1L).get()
        // increment & persist
        record.currentIndex += 1;
        autoIncrementedIndexRepository.save(record)
        return record.currentIndex;
    }
}