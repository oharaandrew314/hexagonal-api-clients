package dev.andrewohara.adoptapis

import dev.andrewohara.adoptapis.dao.*

fun main() {
    val v1 = CatsDao.v1("http://catdocs.com.com/api")
    val v2 = CatsDao.v2("https://api.catdocs.com")

    val apiFeatureFlag = FeatureFlag.static(
        treatments = mapOf("123" to "v1", "456" to "v2"),
        default = "v2"
    )

    val dao = CatsDao.toggled(apiFeatureFlag, v1 = v1, v2 = v2)

    val ui = CatUi(dao)
    println(ui.renderCatHtml("123"))
    println(ui.renderCatHtml("b692b290-40c4-4c0f-89ac-ed0a537e1736"))
}