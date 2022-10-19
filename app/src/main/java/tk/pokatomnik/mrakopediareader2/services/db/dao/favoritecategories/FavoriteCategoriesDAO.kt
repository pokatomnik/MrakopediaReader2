package tk.pokatomnik.mrakopediareader2.services.db.dao.favoritecategories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteCategoriesDAO {
    @Query("SELECT * FROM favorite_categories")
    suspend fun getAll(): List<FavoriteCategory>

    @Query("SELECT * FROM favorite_categories WHERE title = :categoryTitle")
    suspend fun has(categoryTitle: String): FavoriteCategory?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(favoriteCategory: FavoriteCategory)

    @Delete
    suspend fun delete(favoriteCategory: FavoriteCategory)
}