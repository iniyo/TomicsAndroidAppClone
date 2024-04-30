package com.example.tomicsandroidappclone.presentation.util.navigator

/**
 * Available screens.
 */
enum class Fragments {
    MAIN_PAGE,
    WEBTOON_PAGE,
}

enum class Activitys {
    BASE_ACTIVITY
}

/**
 * Interfaces that defines an app navigator.
 */
interface AppNavigator {
    // Navigate to a given screen.
    fun navigateTo(screen: Fragments, tabItmes: Array<String>)
    fun navigateTo(screen: Activitys)
}
