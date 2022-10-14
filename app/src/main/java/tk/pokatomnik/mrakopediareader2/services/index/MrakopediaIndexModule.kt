package tk.pokatomnik.mrakopediareader2.services.index

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tk.pokatomnik.mrakopediareader2.services.textassetresolver.TextAssetResolverImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MrakopediaIndexModule {
    @Singleton
    @Provides
    fun provideMrakopediaIndex(indexJSONAssetResolver: TextAssetResolverImpl): MrakopediaIndex {
        return MrakopediaIndex(indexJSONAssetResolver)
    }
}