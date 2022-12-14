package tk.pokatomnik.mrakopediareader2.services.preferences

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PreferencesModule {
    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): Preferences {
        return Preferences(context)
    }
}