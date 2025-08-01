// 代码生成时间: 2025-08-01 11:57:06
package com.example.theme;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.Nullable;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ThemeSwitchService {

    // Map to store available themes with their respective styles
    private final Map<String, String> themes;

    // Constructor injecting the available themes configuration
    public ThemeSwitchService(@Value('${app.themes}') Map<String, String> themes) {
        this.themes = new HashMap<>(themes);
    }

    /**<ol>
     * Switches the theme based on the provided theme name.
     *
     * @param themeName The name of the theme to switch to
     * @return The new theme's style
     * @throws IllegalArgumentException If the provided theme is not available
     */
    public String switchTheme(String themeName) {
        // Check if the theme is available
        if (!themes.containsKey(themeName)) {
            throw new IllegalArgumentException("Theme not available: " + themeName);
        }

        // Return the style of the new theme
        return themes.get(themeName);
    }

    /**<ol>
     * Retrieves a list of all available themes.
     *
     * @return A map of available themes with their styles
     */
    public Map<String, String> getAvailableThemes() {
        return new HashMap<>(themes);
    }
}
