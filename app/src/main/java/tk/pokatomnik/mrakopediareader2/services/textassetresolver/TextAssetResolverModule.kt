package tk.pokatomnik.mrakopediareader2.services.textassetresolver

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class TextAssetResolverModule {
    @Singleton
    @Provides
    fun provideTextAssetResolver(application: Application): TextAssetResolverImpl {
        return TextAssetResolverImpl(application)
    }
}