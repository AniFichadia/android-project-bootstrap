package com.anifichadia.bootstrap.service

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-29
 */
class PassthroughMapper<RawResponseT, PassthroughT> : ResponseMapper<RawResponseT, PassthroughT, PassthroughT> {

    override fun map(rawResponse: RawResponseT, body: PassthroughT?): PassthroughT = body!!
}
