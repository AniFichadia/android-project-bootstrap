package com.anifichadia.bootstrap.service.retrofit

import com.anifichadia.bootstrap.service.ResponseMapper
import retrofit2.Response

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-29
 */
class RetrofitPassthroughResponseMapper<PassthroughT> :
    ResponseMapper<Response<PassthroughT>, PassthroughT, PassthroughT> {

    override fun map(rawResponse: Response<PassthroughT>, body: PassthroughT?): PassthroughT = body!!
}
