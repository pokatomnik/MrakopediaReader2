package tk.pokatomnik.mrakopediareader2.services.db.dao.favoritecategories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_categories",
    indices = [
        Index(value = ["title"], unique = true)
    ]
)
data class FavoriteCategory(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
)