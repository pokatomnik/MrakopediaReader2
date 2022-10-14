package tk.pokatomnik.mrakopediareader2.services.textassetresolver

import android.app.Application
import java.io.BufferedReader

class TextAssetResolverImpl(private val application: Application) : TextAssetResolver {
    override fun resolve(filePath: String): String {
        return application.assets
            .open(filePath)
            .bufferedReader(Charsets.UTF_8)
            .use(BufferedReader::readText)
    }
}