package tk.pokatomnik.mrakopediareader2.domain

import java.time.Instant

data class Index(
    val mrakopediaIndex: Map<String, Category>,
    val storiesOfMonth: StoriesOfMonth,
    val creationDate: Instant
)