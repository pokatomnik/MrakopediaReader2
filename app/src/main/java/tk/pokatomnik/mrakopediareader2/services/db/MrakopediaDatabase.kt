package tk.pokatomnik.mrakopediareader2.services.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritecategories.FavoriteCategoriesDAO
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritecategories.FavoriteCategory
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritestories.FavoriteStory
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritestories.FavoriteStoriesDAO
import tk.pokatomnik.mrakopediareader2.services.db.dao.history.HistoryDAO
import tk.pokatomnik.mrakopediareader2.services.db.dao.history.HistoryItem

@Database(
    entities = [FavoriteStory::class, FavoriteCategory::class, HistoryItem::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
//        AutoMigration(
//            from = 2,
//            to = 3,
//            spec = tk.pokatomnik.mrakopediareader2.services.db.MrakopediaDatabase.SomeMigration::class
//        )
    ]
)
abstract class MrakopediaDatabase : RoomDatabase() {
    abstract fun favoriteStoriesDAO(): FavoriteStoriesDAO

    abstract fun favoriteCategoriesDAO(): FavoriteCategoriesDAO

    abstract fun historyDAO(): HistoryDAO

//    @DeleteColumn(tableName = "favorite_stories", columnName = "title")
//    @DeleteColumn(tableName = "recent", columnName = "url")
//    class SomeMigration : AutoMigrationSpec {
//        @Override
//        override fun onPostMigrate(db: SupportSQLiteDatabase) {
//            super.onPostMigrate(db)
//            db.execSQL("DELETE FROM favorite_stories")
//        }
//    }
}