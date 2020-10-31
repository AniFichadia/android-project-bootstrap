package com.anifichadia.bootstrap.service

/**
 * [RawResponseT] Is the raw response type returned from your networking stack. This allows better handling of headers,
 * status codes, etc.
 *
 * Eg, if using retrofit, and a response's body is mapped to a network DTO class named "MyBody", [RawResponseT] should be
 * retrofit2.Response<MyBody>
 *
 * @author Aniruddh Fichadia
 * @date 2020-08-08
 */
interface ResponseMapper<RawResponseT, BodyT, MappedT> {
    @Throws(Throwable::class)
    fun map(rawResponse: RawResponseT, body: BodyT?): MappedT
}
