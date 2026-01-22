/**
 * Theme Switcher for 2090 Futuristic Design
 * ==========================================
 * Handles light/dark theme toggle with:
 * - localStorage persistence
 * - prefers-color-scheme support
 * - Smooth transitions
 * - Ripple effect on click
 */

(function() {
  'use strict';

  // Constants
  const STORAGE_KEY = 'integration-service-theme';
  const DARK_THEME = 'dark';
  const LIGHT_THEME = 'light';
  const TRANSITION_CLASS = 'theme-transition';
  const TRANSITION_DURATION = 500; // ms

  /**
   * Get the user's preferred theme from localStorage or system preference
   * @returns {string} 'dark' or 'light'
   */
  function getPreferredTheme() {
    // Check localStorage first
    const storedTheme = localStorage.getItem(STORAGE_KEY);
    if (storedTheme && (storedTheme === DARK_THEME || storedTheme === LIGHT_THEME)) {
      return storedTheme;
    }

    // Fall back to system preference
    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: light)').matches) {
      return LIGHT_THEME;
    }

    // Default to dark theme (futuristic default)
    return DARK_THEME;
  }

  /**
   * Apply the theme to the document
   * @param {string} theme - 'dark' or 'light'
   * @param {boolean} animate - Whether to animate the transition
   */
  function applyTheme(theme, animate) {
    if (animate === undefined) animate = false;

    const html = document.documentElement;

    // Add transition class for smooth switching if animating
    if (animate) {
      html.classList.add(TRANSITION_CLASS);
    }

    // Set the theme attribute
    html.setAttribute('data-theme', theme);

    // Update meta theme-color for mobile browsers
    updateMetaThemeColor(theme);

    // Update any theme toggle buttons
    updateToggleButtons(theme);

    // Remove transition class after animation completes
    if (animate) {
      setTimeout(function() {
        html.classList.remove(TRANSITION_CLASS);
      }, TRANSITION_DURATION);
    }

    // Store preference
    try {
      localStorage.setItem(STORAGE_KEY, theme);
    } catch (e) {
      // localStorage might be unavailable
      console.warn('Could not save theme preference:', e);
    }

    // Dispatch custom event for any listeners
    if (typeof CustomEvent === 'function') {
      window.dispatchEvent(new CustomEvent('themechange', { detail: { theme: theme } }));
    }
  }

  /**
   * Update meta theme-color for mobile browsers
   * @param {string} theme
   */
  function updateMetaThemeColor(theme) {
    var metaThemeColor = document.querySelector('meta[name="theme-color"]');
    if (!metaThemeColor) {
      metaThemeColor = document.createElement('meta');
      metaThemeColor.name = 'theme-color';
      document.head.appendChild(metaThemeColor);
    }
    metaThemeColor.content = theme === LIGHT_THEME ? '#f0f4f8' : '#0a0a0f';
  }

  /**
   * Update all theme toggle buttons to reflect current state
   * @param {string} theme
   */
  function updateToggleButtons(theme) {
    var toggleButtons = document.querySelectorAll('[data-theme-toggle]');
    for (var i = 0; i < toggleButtons.length; i++) {
      var button = toggleButtons[i];
      var sunIcon = button.querySelector('.icon-sun');
      var moonIcon = button.querySelector('.icon-moon');

      if (sunIcon && moonIcon) {
        if (theme === LIGHT_THEME) {
          sunIcon.style.display = 'none';
          moonIcon.style.display = 'flex';
        } else {
          sunIcon.style.display = 'flex';
          moonIcon.style.display = 'none';
        }
      }

      // Update aria-label
      button.setAttribute('aria-label',
        theme === LIGHT_THEME ? 'Switch to dark theme' : 'Switch to light theme'
      );

      // Update title
      button.setAttribute('title',
        theme === LIGHT_THEME ? 'Switch to dark theme' : 'Switch to light theme'
      );
    }
  }

  /**
   * Toggle between light and dark themes
   */
  function toggleTheme() {
    var currentTheme = document.documentElement.getAttribute('data-theme') || DARK_THEME;
    var newTheme = currentTheme === DARK_THEME ? LIGHT_THEME : DARK_THEME;
    applyTheme(newTheme, true);
  }

  /**
   * Create ripple effect on button click
   * @param {Event} event
   * @param {HTMLElement} element
   */
  function createRipple(event, element) {
    // Remove any existing ripples
    var existingRipples = element.querySelectorAll('.ripple');
    for (var i = 0; i < existingRipples.length; i++) {
      existingRipples[i].remove();
    }

    var ripple = document.createElement('span');
    ripple.classList.add('ripple');

    var rect = element.getBoundingClientRect();
    var size = Math.max(rect.width, rect.height);

    ripple.style.width = size + 'px';
    ripple.style.height = size + 'px';
    ripple.style.left = (event.clientX - rect.left - size / 2) + 'px';
    ripple.style.top = (event.clientY - rect.top - size / 2) + 'px';

    element.appendChild(ripple);

    // Remove ripple after animation
    ripple.addEventListener('animationend', function() {
      ripple.remove();
    });
  }

  /**
   * Initialize theme toggle buttons
   */
  function initToggleButtons() {
    var buttons = document.querySelectorAll('[data-theme-toggle]');
    for (var i = 0; i < buttons.length; i++) {
      (function(button) {
        button.addEventListener('click', function(e) {
          e.preventDefault();
          toggleTheme();

          // Add ripple effect if button has ripple-container class
          if (button.classList.contains('ripple-container')) {
            createRipple(e, button);
          }
        });

        // Keyboard support
        button.addEventListener('keydown', function(e) {
          if (e.key === 'Enter' || e.key === ' ') {
            e.preventDefault();
            toggleTheme();
          }
        });
      })(buttons[i]);
    }
  }

  /**
   * Listen for system theme preference changes
   */
  function watchSystemPreference() {
    if (window.matchMedia) {
      try {
        // Modern browsers
        window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', function(e) {
          // Only auto-switch if user hasn't explicitly set a preference
          if (!localStorage.getItem(STORAGE_KEY)) {
            applyTheme(e.matches ? DARK_THEME : LIGHT_THEME, true);
          }
        });
      } catch (e) {
        // Older browsers - use deprecated addListener
        try {
          window.matchMedia('(prefers-color-scheme: dark)').addListener(function(e) {
            if (!localStorage.getItem(STORAGE_KEY)) {
              applyTheme(e.matches ? DARK_THEME : LIGHT_THEME, true);
            }
          });
        } catch (e2) {
          // No support for media query listeners
        }
      }
    }
  }

  /**
   * Initialize the theme system
   */
  function init() {
    // Apply initial theme immediately (without animation) to prevent flash
    applyTheme(getPreferredTheme(), false);

    // Wait for DOM to be ready for button initialization
    if (document.readyState === 'loading') {
      document.addEventListener('DOMContentLoaded', function() {
        initToggleButtons();
        watchSystemPreference();
      });
    } else {
      initToggleButtons();
      watchSystemPreference();
    }
  }

  /**
   * Set a specific theme
   * @param {string} theme - 'dark' or 'light'
   */
  function setTheme(theme) {
    if (theme === DARK_THEME || theme === LIGHT_THEME) {
      applyTheme(theme, true);
    }
  }

  /**
   * Get current theme
   * @returns {string}
   */
  function getTheme() {
    return document.documentElement.getAttribute('data-theme') || DARK_THEME;
  }

  /**
   * Clear stored preference (will use system preference on next load)
   */
  function clearPreference() {
    try {
      localStorage.removeItem(STORAGE_KEY);
    } catch (e) {
      // Ignore
    }
  }

  // Expose public API
  window.ThemeSwitcher = {
    toggle: toggleTheme,
    setTheme: setTheme,
    getTheme: getTheme,
    clearPreference: clearPreference,
    init: init
  };

  // Auto-initialize
  init();

})();
