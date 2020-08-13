package com.anifichadia.app.service.client

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-20
 */
open class HeaderEntry(
    val name: String,
    val value: String?
)

interface HeaderEntryProvider {
    fun provideEntries(): List<HeaderEntry>
}
