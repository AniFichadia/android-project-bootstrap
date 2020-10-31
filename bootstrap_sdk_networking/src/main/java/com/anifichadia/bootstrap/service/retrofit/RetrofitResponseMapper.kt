package com.anifichadia.bootstrap.service.retrofit

import com.anifichadia.bootstrap.service.ResponseMapper
import retrofit2.Response

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
interface RetrofitResponseMapper<BodyT, MappedT> : ResponseMapper<Response<BodyT>, BodyT, MappedT>
