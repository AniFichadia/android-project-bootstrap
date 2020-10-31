package com.anifichadia.bootstrap.service.client

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-20
 */
open class UserAgentEntry(
    val name: String,
    val value: String?
)

interface UserAgentEntryProvider {
    fun provideEntries(): List<UserAgentEntry>
}
