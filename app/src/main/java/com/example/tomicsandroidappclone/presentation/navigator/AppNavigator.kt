package com.example.tomicsandroidappclone.presentation.navigator

/**
 * Available screens.
 */
enum class MainFragments {
    MAIN_PAGE,
    TAB1,
    TAB2,
    TAB3,
    TAB4,
    TAB5
}

/**
 * Interfaces that defines an app navigator.
 */
interface AppNavigator {
    // Navigate to a given screen.
    fun navigateTo(screen: MainFragments)
}
