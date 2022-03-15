package dev.andrewohara.adoptapis.dao

import dev.andrewohara.adoptapis.FeatureFlag

/**
 * This CatsDao will delegate to the v1 or v2 implementation, depending on the state of a feature flag.
 */
fun CatsDao.Companion.toggled(flag: FeatureFlag, v1: CatsDao, v2: CatsDao) = CatsDao { id ->
    val treatment = flag[id]
    val dao = if (treatment == "v1") v1 else v2
    dao[id]
}