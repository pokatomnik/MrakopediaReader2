package tk.pokatomnik.mrakopediareader2.screens.stories

import tk.pokatomnik.mrakopediareader2.domain.PageMeta
import tk.pokatomnik.mrakopediareader2.ui.components.SortDirection

internal enum class SortingType {
    ALPHA,
    RATING,
    VOTED,
    TIME
}

internal interface Sorting {
    val sortDirection: SortDirection
    val sortType: SortingType
    fun sorted(items: List<PageMeta>): List<PageMeta>
}

internal class AlphaASC : Sorting {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.ALPHA
    override fun sorted(items: List<PageMeta>): List<PageMeta> {
        return items.sortedWith { a, b ->
            a.title.lowercase().compareTo(b.title.lowercase())
        }
    }
}

internal class AlphaDESC : Sorting {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.ALPHA
    override fun sorted(items: List<PageMeta>): List<PageMeta> {
        return items.sortedWith { a, b ->
            b.title.lowercase().compareTo(a.title.lowercase())
        }
    }
}

internal class RatingASC : Sorting {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.RATING
    override fun sorted(items: List<PageMeta>): List<PageMeta> {
        return items.sortedWith { a, b ->
            (a.rating ?: 0) - (b.rating ?: 0)
        }
    }
}

internal class RatingDESC : Sorting {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.RATING
    override fun sorted(items: List<PageMeta>): List<PageMeta> {
        return items.sortedWith { a, b ->
            (b.rating ?: 0) - (a.rating ?: 0)
        }
    }
}

internal class VotedASC : Sorting {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.VOTED
    override fun sorted(items: List<PageMeta>): List<PageMeta> {
        return items.sortedWith { a, b ->
            (a.voted ?: 0) - (b.voted ?: 0)
        }
    }
}

internal class VotedDESC : Sorting {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.VOTED
    override fun sorted(items: List<PageMeta>): List<PageMeta> {
        return items.sortedWith { a, b ->
            (b.voted ?: 0) - (a.voted ?: 0)
        }
    }
}

internal class TimeASC : Sorting {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.TIME
    override fun sorted(items: List<PageMeta>): List<PageMeta> {
        return items.sortedWith { a, b ->
            a.charactersInPage - b.charactersInPage
        }
    }
}

internal class TimeDESC : Sorting {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.TIME
    override fun sorted(items: List<PageMeta>): List<PageMeta> {
        return items.sortedWith { a, b ->
            b.charactersInPage - a.charactersInPage
        }
    }
}