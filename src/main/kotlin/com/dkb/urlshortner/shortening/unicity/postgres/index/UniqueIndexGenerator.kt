package com.dkb.urlshortner.shortening.unicity.postgres.index

// Provides a strictly increasing numeric value for use
// as the next unique index.
interface UniqueIndexGenerator {
    fun nextIndex(): Long;
}