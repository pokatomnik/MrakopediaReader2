package tk.pokatomnik.mrakopediareader2.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import tk.pokatomnik.mrakopediareader2.services.preferences.global.LocalThemeIdentifier
import tk.pokatomnik.mrakopediareader2.services.preferences.global.ThemeIdentifier
import tk.pokatomnik.mrakopediareader2.services.preferences.global.rememberThemeIdentifier
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
private fun MrakopediaReader2ThemeInternal(
    content: @Composable () -> Unit
) {
    val themeIdentifierState = rememberThemeIdentifier()
    val darkTheme = isSystemInDarkTheme()
    val darkThemeEnabledByPreferences = when (themeIdentifierState.value) {
        ThemeIdentifier.DARK -> true
        ThemeIdentifier.LIGHT -> false
        else -> darkTheme
    }

    val colors = if (darkThemeEnabledByPreferences) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun MrakopediaReader2Theme(
    content: @Composable () -> Unit
) {
    val themeIdentifier = rememberPreferences().globalPreferences.themeIdentifier
    val state = remember { mutableStateOf(themeIdentifier) }
    CompositionLocalProvider(LocalThemeIdentifier provides state) {
        MrakopediaReader2ThemeInternal {
            content()
        }
    }
}