// 代码生成时间: 2025-08-13 14:21:53
package com.example.theme;

import io.micronaut.context.annotation.Value;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ThemeService {

    // A map to store theme configurations
    private final Map<String, ThemeConfig> themes = new HashMap<>();

    // Inject the theme configurations from the configuration file
    @Value('${app.themes}')
    private Map<String, String> themeConfigs;

    public ThemeService() {
        // Initialize themes with default configurations
        themes.put("light", new ThemeConfig("light", "#ffffff", "#000000"));
        themes.put("dark", new ThemeConfig("dark", "#000000", "#ffffff"));

        // Load additional themes from configuration
        for (Map.Entry<String, String> entry : themeConfigs.entrySet()) {
            String[] config = entry.getValue().split(":");
            if (config.length == 3) {
                themes.put(entry.getKey(), new ThemeConfig(entry.getKey(), config[0], config[1]));
            } else {
                // Handle malformed configuration entries
                System.err.println("Malformed theme configuration for theme: " + entry.getKey());
            }
        }
    }

    /**
     * Switch the current theme.
     *
     * @param themeName The name of the theme to switch to.
     * @return The new theme configuration.
     */
    public ThemeConfig switchTheme(String themeName) {
        if (!themes.containsKey(themeName)) {
            // Handle theme not found
            throw new IllegalArgumentException("Theme '