package dev.andrewohara.adoptapis

import dev.andrewohara.adoptapis.client.ClientV1
import dev.andrewohara.adoptapis.client.ClientV2
import dev.andrewohara.adoptapis.dao.*

/**
 * Renders the HTML for our "beautiful" webpage.
 */
class CatUi(private val cats: CatsDao) {

    fun renderCatHtml(id: String): String {
        val cat = cats[id] ?: return "<h1>Cat $id not found</h1>"
        val latestAppointment = cat.appointments.maxOrNull()

        val optionalField = if (cat.favouriteFood != null) {
            "<b>Favourite Food:</b> ${cat.favouriteFood}<br/>"
        } else ""

        return """
            <html><body>
                <h1>${cat.name}</h1>
                <h2>Owner: ${cat.ownerId}</h2>
                
                <b>Colours:</b> ${cat.colours.joinToString(",")}<br/>
                <b>Breed:</b> ${cat.breed}<br/>
                <b>Latest Appointment:</b> $latestAppointment<br/>
                $optionalField
            </body></html>
        """
    }
}

fun main() {
    val v1 = CatsDao.v1(ClientV1("http://catdocs.com.com/api"))
    val v2 = CatsDao.v2(ClientV2("https://api.catdocs.com"))
    val backCompat = CatsDao.backCompat(v1 = v1, v2 = v2)

    val apiFeatureFlag = FeatureFlag.static(
        treatments = mapOf("123" to "v1", "b692b290-40c4-4c0f-89ac-ed0a537e1736" to "v2"),
        default = "v2"
    )

    val dao = CatsDao.toggled(apiFeatureFlag, v1 = v1, v2 = backCompat)

    val ui = CatUi(dao)
    println(ui.renderCatHtml("123"))
    println(ui.renderCatHtml("b692b290-40c4-4c0f-89ac-ed0a537e1736"))
}