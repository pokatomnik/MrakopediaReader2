package tk.pokatomnik.mrakopediareader2.services.navigation

interface Serializer {
    fun serialize(source: String): String
    fun parse(serialized: String): String
}