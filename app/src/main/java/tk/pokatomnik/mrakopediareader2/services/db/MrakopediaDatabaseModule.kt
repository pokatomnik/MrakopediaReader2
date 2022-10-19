package tk.pokatomnik.mrakopediareader2.services.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MrakopediaDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MrakopediaDatabase {
        return Room.databaseBuilder(context, MrakopediaDatabase::class.java, "mrakopedia.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}