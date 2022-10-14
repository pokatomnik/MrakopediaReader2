package tk.pokatomnik.mrakopediareader2.services.textassetresolver

interface TextAssetResolver {
    fun resolve(filePath: String): String
}