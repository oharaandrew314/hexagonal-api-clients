package dev.andrewohara.adoptapis

typealias Treatment = String

/**
 * Implement with the feature-flag provider of your choice (e.g. Unleashed, Split, LaunchDarkly).
 *
 * Feature flags allow you to delegate migration rules to an external dashboard.
 * They can be much faster to update than deploying code changes.
 */
fun interface FeatureFlag {
    operator fun get(key: String): Treatment

    companion object
}

fun FeatureFlag.Companion.static(treatments: Map<String, String>, default: String) = FeatureFlag { key ->
    treatments.getOrDefault(key, default)
}