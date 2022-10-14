package tk.pokatomnik.mrakopediareader2.screens.categories

import tk.pokatomnik.mrakopediareader2.services.index.MrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.LazyListItem
import tk.pokatomnik.mrakopediareader2.ui.components.SortDirection

enum class SortingType {
    ALPHA,
    RATING,
    VOTED,
    QUANTITY
}

interface Sorting {
    val sortDirection: SortDirection
    val sortType: SortingType
    fun sorted(items: List<LazyListItem>): List<LazyListItem>
}

class AlphaASC : Sorting {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.ALPHA
    override fun sorted(items: List<LazyListItem>): List<LazyListItem> {
        return items.sortedWith { a, b ->
            a.title.lowercase().compareTo(b.title.lowercase())
        }
    }
}

class AlphaDESC : Sorting {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.ALPHA
    override fun sorted(items: List<LazyListItem>): List<LazyListItem> {
        return items.sortedWith { a, b ->
            b.title.lowercase().compareTo(a.title.lowercase())
        }
    }
}

class RatingASC(private val mrakopediaIndex: MrakopediaIndex) : Sorting {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.RATING
    override fun sorted(items: List<LazyListItem>): List<LazyListItem> {
        return items.sortedWith { a, b ->
            val category1 = mrakopediaIndex.getCategory(a.title)
            val category2 = mrakopediaIndex.getCategory(b.title)
            category1.avgRating - category2.avgRating
        }
    }
}

class RatingDESC(private val mrakopediaIndex: MrakopediaIndex) : Sorting {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.RATING
    override fun sorted(items: List<LazyListItem>): List<LazyListItem> {
        return items.sortedWith { a, b ->
            val category1 = mrakopediaIndex.getCategory(a.title)
            val category2 = mrakopediaIndex.getCategory(b.title)
            category2.avgRating - category1.avgRating
        }
    }
}

class VotedASC(private val mrakopediaIndex: MrakopediaIndex) : Sorting {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.VOTED
    override fun sorted(items: List<LazyListItem>): List<LazyListItem> {
        return items.sortedWith { a, b ->
            val category1 = mrakopediaIndex.getCategory(a.title)
            val category2 = mrakopediaIndex.getCategory(b.title)
            category1.avgVoted - category2.avgVoted
        }
    }
}

class VotedDESC(private val mrakopediaIndex: MrakopediaIndex) : Sorting {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.VOTED
    override fun sorted(items: List<LazyListItem>): List<LazyListItem> {
        return items.sortedWith { a, b ->
            val category1 = mrakopediaIndex.getCategory(a.title)
            val category2 = mrakopediaIndex.getCategory(b.title)
            category2.avgVoted - category1.avgVoted
        }
    }
}

class QuantityASC(private val mrakopediaIndex: MrakopediaIndex) : Sorting {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.QUANTITY
    override fun sorted(items: List<LazyListItem>): List<LazyListItem> {
        return items.sortedWith { a, b ->
            val category1 = mrakopediaIndex.getCategory(a.title)
            val category2 = mrakopediaIndex.getCategory(b.title)
            category1.size - category2.size
        }
    }
}

class QuantityDESC(private val mrakopediaIndex: MrakopediaIndex) : Sorting {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.QUANTITY
    override fun sorted(items: List<LazyListItem>): List<LazyListItem> {
        return items.sortedWith { a, b ->
            val category1 = mrakopediaIndex.getCategory(a.title)
            val category2 = mrakopediaIndex.getCategory(b.title)
            category2.size - category1.size
        }
    }
}