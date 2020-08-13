package com.anifichadia.app.service

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-08
 */
interface ResponseMapper<RawResponseT, BodyT, MappedT> {
    @Throws(Throwable::class)
    fun map(rawResponse: RawResponseT, body: BodyT?): MappedT
}
