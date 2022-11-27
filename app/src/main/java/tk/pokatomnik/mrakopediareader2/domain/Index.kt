package tk.pokatomnik.mrakopediareader2.domain

import java.time.Instant

data class Index(
    val mrakopediaIndex: Map<String, Category>,
    val goodStories: List<String>,
    val storiesOfMonth: List<String>,
    val creationDate: Instant
)