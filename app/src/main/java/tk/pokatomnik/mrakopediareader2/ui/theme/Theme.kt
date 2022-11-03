package tk.pokatomnik.mrakopediareader2.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import tk.pokatomnik.mrakopediareader2.services.preferences.global.LocalThemeIdentifier
import tk.pokatomnik.mrakopediareader2.services.preferences.global.ThemeIdentifier
import tk.pokatomnik.mrakopediareader2.services.preferences.global.rememberThemeIdentifier
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    primaryVariant = PrimaryVariantDark,
    secondary = SecondaryDark,
    secondaryVariant = SecondaryVariantDark
)

private val LightColorPalette = lightColors(
    primary = PrimaryLight,
    primaryVariant = PrimaryVariantLight,
    secondary = SecondaryLight,
    secondaryVariant = SecondaryVariantLight
)

@Composable
private fun MrakopediaReader2ThemeInternal(
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
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

    systemUiController.setSystemBarsColor(colors.primarySurface)

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