package com.example.tomicsandroidappclone.presentation.navigator

/**
 * Available screens.
 */
enum class Screens {
    TAB1,
    TAB2,
    TAB3,
    TAB4,
    TAB5,
    TAB6
}

/**
 * Interfaces that defines an app navigator.
 */
interface AppNavigator {
    // Navigate to a given screen.
    fun navigateTo(screen: Screens)
}
