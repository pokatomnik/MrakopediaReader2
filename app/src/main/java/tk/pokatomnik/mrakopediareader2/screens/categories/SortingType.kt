package tk.pokatomnik.mrakopediareader2.screens.categories

import tk.pokatomnik.mrakopediareader2.services.index.Category
import tk.pokatomnik.mrakopediareader2.ui.components.SortDirection

internal enum class SortingType {
    ALPHA,
    RATING,
    VOTED,
    QUANTITY
}

internal abstract class Sorting {
    abstract val sortDirection: SortDirection
    abstract val sortType: SortingType
    abstract fun sorted(items: List<Category>): List<Category>
    override fun toString(): String {
        return this::class.simpleName ?: ""
    }
}

internal class AlphaASC : Sorting() {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.ALPHA
    override fun sorted(items: List<Category>): List<Category> {
        return items.sortedWith { a, b ->
            a.name.lowercase().compareTo(b.name.lowercase())
        }
    }
}

internal class AlphaDESC : Sorting() {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.ALPHA
    override fun sorted(items: List<Category>): List<Category> {
        return items.sortedWith { a, b ->
            b.name.lowercase().compareTo(a.name.lowercase())
        }
    }
}

internal class RatingASC : Sorting() {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.RATING
    override fun sorted(items: List<Category>): List<Category> {
        return items.sortedWith { a, b ->
            a.avgRating - b.avgRating
        }
    }
}

internal class RatingDESC : Sorting() {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.RATING
    override fun sorted(items: List<Category>): List<Category> {
        return items.sortedWith { a, b ->
            b.avgRating - a.avgRating
        }
    }
}

internal class VotedASC : Sorting() {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.VOTED
    override fun sorted(items: List<Category>): List<Category> {
        return items.sortedWith { a, b ->
            a.avgVoted - b.avgVoted
        }
    }
}

internal class VotedDESC : Sorting() {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.VOTED
    override fun sorted(items: List<Category>): List<Category> {
        return items.sortedWith { a, b ->
            b.avgVoted - a.avgVoted
        }
    }
}

internal class QuantityASC : Sorting() {
    override val sortDirection: SortDirection = SortDirection.ASC
    override val sortType: SortingType = SortingType.QUANTITY
    override fun sorted(items: List<Category>): List<Category> {
        return items.sortedWith { a, b ->
            a.size - b.size
        }
    }
}

internal class QuantityDESC : Sorting() {
    override val sortDirection: SortDirection = SortDirection.DESC
    override val sortType: SortingType = SortingType.QUANTITY
    override fun sorted(items: List<Category>): List<Category> {
        return items.sortedWith { a, b ->
            b.size - a.size
        }
    }
}

internal val sortingMap = mapOf(
    AlphaASC::class.simpleName to AlphaASC(),
    AlphaDESC::class.simpleName to AlphaDESC(),
    RatingASC::class.simpleName to RatingASC(),
    RatingDESC::class.simpleName to RatingDESC(),
    VotedASC::class.simpleName to VotedASC(),
    VotedDESC::class.simpleName to VotedDESC(),
    QuantityASC::class.simpleName to QuantityASC(),
    QuantityDESC::class.simpleName to QuantityDESC(),
)
