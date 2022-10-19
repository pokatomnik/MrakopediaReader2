package tk.pokatomnik.mrakopediareader2.ui.components

val emojiByRating = listOf(
    "☹",
    "🙁",
    "😕",
    "🥱",
    "😒",
    "😐",
    "😏",
    "🙂",
    "😃",
    "🤩",
    "💯"
)

fun ratingToEmoji(rating: Int): String {
    val coercedRating = rating.coerceIn(0..100)
    return emojiByRating[(coercedRating / 10)]
}