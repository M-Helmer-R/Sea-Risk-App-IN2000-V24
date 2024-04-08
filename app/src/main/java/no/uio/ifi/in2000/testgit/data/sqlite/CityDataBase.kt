package no.uio.ifi.in2000.testgit.data.sqlite

import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "Cities"
        const val NAME = "Name"
        const val LONGITUDE = "Longitude"
        const val LATITUDE = "Latitude"
    }
}

private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${FeedReaderContract.FeedEntry.NAME} TEXT," +
            "${FeedReaderContract.FeedEntry.LONGITUDE} TEXT)" +
            "${FeedReaderContract.FeedEntry.LATITUDE} Text)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"