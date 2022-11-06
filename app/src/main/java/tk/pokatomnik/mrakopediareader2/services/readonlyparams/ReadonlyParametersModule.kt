package tk.pokatomnik.mrakopediareader2.services.readonlyparams

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ReadonlyParametersModule {
    @Singleton
    @Provides
    fun provideReadonlyParameters(): ReadonlyParameters {
        return ReadonlyParameters()
    }
}