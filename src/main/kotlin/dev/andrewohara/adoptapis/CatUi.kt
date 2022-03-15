package dev.andrewohara.adoptapis

import dev.andrewohara.adoptapis.dao.CatsDao

/**
 * Renders the HTML for our "beautiful" webpage.
 */
class CatUi(private val cats: CatsDao) {

    fun renderCatHtml(id: String): String {
        val cat = cats[id] ?: return "<h1>Cat $id not found</h1>"

        val isBrown = if (cat.brown) "Yes" else "No"
        val isGrey = if (cat.grey) "Yes" else "No"
        val latestAppointment = cat.appointments.maxOrNull()

        return """
            <html><body>
                <h1>${cat.name}</h1>
                <h2>Owner: ${cat.ownerId}</h2>
                
                <b>Is Brown:</b> $isBrown<br/>
                <b>Is Grey:</b> $isGrey<br/>
                <b>Breed:</b> ${cat.breed}<br/>
                <b>Latest Appointment:</b> $latestAppointment<br/>
                <b>Favourite Food:</b> ${cat.favouriteFood}<br/>
            </body></html>
        """
    }
}