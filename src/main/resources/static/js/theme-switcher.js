/**
 * Theme Switcher for Integration Service
 * Handles dark/light theme toggle with localStorage persistence
 */
(function() {
    'use strict';

    var STORAGE_KEY = 'integration-service-theme';
    var LIGHT_THEME_CLASS = 'light-theme';

    /**
     * Get saved theme preference from localStorage
     */
    function getSavedTheme() {
        try {
            return localStorage.getItem(STORAGE_KEY);
        } catch (e) {
            return null;
        }
    }

    /**
     * Save theme preference to localStorage
     */
    function saveTheme(theme) {
        try {
            localStorage.setItem(STORAGE_KEY, theme);
        } catch (e) {
            // localStorage not available
        }
    }

    /**
     * Check if light theme is currently active
     */
    function isLightTheme() {
        return document.body.classList.contains(LIGHT_THEME_CLASS);
    }

    /**
     * Apply theme to document
     */
    function applyTheme(theme) {
        if (theme === 'light') {
            document.body.classList.add(LIGHT_THEME_CLASS);
        } else {
            document.body.classList.remove(LIGHT_THEME_CLASS);
        }
        updateButtonIcon();
    }

    /**
     * Update button icon based on current theme
     */
    function updateButtonIcon() {
        var button = document.querySelector('.theme-switcher');
        if (button) {
            var moonIcon = button.querySelector('.theme-icon-moon');
            var sunIcon = button.querySelector('.theme-icon-sun');
            if (moonIcon && sunIcon) {
                if (isLightTheme()) {
                    moonIcon.style.display = 'none';
                    sunIcon.style.display = 'block';
                } else {
                    moonIcon.style.display = 'block';
                    sunIcon.style.display = 'none';
                }
            }
        }
    }

    /**
     * Toggle between light and dark themes
     */
    function toggleTheme() {
        var newTheme = isLightTheme() ? 'dark' : 'light';
        applyTheme(newTheme);
        saveTheme(newTheme);
    }

    /**
     * Initialize theme on page load
     */
    function initTheme() {
        var savedTheme = getSavedTheme();
        if (savedTheme === 'light') {
            applyTheme('light');
        }
    }

    /**
     * Create and inject theme switcher button
     */
    function createThemeSwitcherButton() {
        if (document.querySelector('.theme-switcher')) {
            return;
        }

        var button = document.createElement('button');
        button.className = 'theme-switcher';
        button.setAttribute('type', 'button');
        button.setAttribute('title', 'Toggle theme');

        // Moon icon (for dark theme - click to switch to light)
        var moonSvg = '<svg class="theme-icon-moon" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path></svg>';

        // Sun icon (for light theme - click to switch to dark)
        var sunSvg = '<svg class="theme-icon-sun" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="5"></circle><line x1="12" y1="1" x2="12" y2="3"></line><line x1="12" y1="21" x2="12" y2="23"></line><line x1="4.22" y1="4.22" x2="5.64" y2="5.64"></line><line x1="18.36" y1="18.36" x2="19.78" y2="19.78"></line><line x1="1" y1="12" x2="3" y2="12"></line><line x1="21" y1="12" x2="23" y2="12"></line><line x1="4.22" y1="19.78" x2="5.64" y2="18.36"></line><line x1="18.36" y1="5.64" x2="19.78" y2="4.22"></line></svg>';

        button.innerHTML = moonSvg + sunSvg;
        button.addEventListener('click', toggleTheme);
        document.body.appendChild(button);

        updateButtonIcon();
    }

    /**
     * Main initialization
     */
    function init() {
        initTheme();
        createThemeSwitcherButton();
    }

    // Initialize when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

    // Expose toggle function globally
    window.toggleTheme = toggleTheme;
})();
