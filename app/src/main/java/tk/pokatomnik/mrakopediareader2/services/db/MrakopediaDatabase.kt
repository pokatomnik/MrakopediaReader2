package tk.pokatomnik.mrakopediareader2.services.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritestories.FavoriteStory
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritestories.FavoriteStoriesDAO

@Database(
    entities = [FavoriteStory::class],
    version = 1,
    exportSchema = true,
// uncomment when needed
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2),
//        AutoMigration(
//            from = 2,
//            to = 3,
//            spec = tk.pokatomnik.mrakopediareader2.services.db.MrakopediaDatabase.SomeMigration::class
//        )
//    ]
)
abstract class MrakopediaDatabase : RoomDatabase() {
    abstract fun favoriteStoriesDAO(): FavoriteStoriesDAO

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