package com.dkb.urlshortner.shortening.unicity.postgres.index

import com.dkb.urlshortner.shortening.unicity.postgres.index.repository.AutoIncrementedIndexRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
open class SqlTransactionBasedUniqueIndexGenerator(
    private val autoIncrementedIndexRepository: AutoIncrementedIndexRepository
): UniqueIndexGenerator {

    // This transaction should lock the index to avoid race conditions
    // but the default @Transactional Lock Mode is READ COMMITTED, But in our case we need atomicity,
    // that's what SERIALIZABLE isolation offers, it will be a nice conversation starter in our interview :D
    @Transactional(isolation=Isolation.SERIALIZABLE)
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