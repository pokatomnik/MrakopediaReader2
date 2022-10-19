package tk.pokatomnik.mrakopediareader2.services.db.dao.favoritestories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteStoriesDAO {
    @Query("SELECT * FROM favorite_stories")
    suspend fun getAll(): List<FavoriteStory>

    @Query("SELECT * FROM favorite_stories WHERE title = :pageTitle")
    suspend fun has(pageTitle: String): FavoriteStory?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(favoriteStory: FavoriteStory)

    @Delete
    suspend fun delete(favoriteStory: FavoriteStory)
}