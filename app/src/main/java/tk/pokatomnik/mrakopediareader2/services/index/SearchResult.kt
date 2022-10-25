package tk.pokatomnik.mrakopediareader2.services.index

import tk.pokatomnik.mrakopediareader2.domain.Category
import tk.pokatomnik.mrakopediareader2.domain.PageMeta

data class SearchResult(
    val pageMeta: Collection<PageMeta>,
    val categories: Collection<Category>
)