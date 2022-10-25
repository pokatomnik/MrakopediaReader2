package tk.pokatomnik.mrakopediareader2.domain

data class Index(
    val mrakopediaIndex: Map<String, Category>,
    val storiesOfMonth: StoriesOfMonth
)