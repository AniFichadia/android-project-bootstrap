package com.anifichadia.app.service.retrofit

import com.anifichadia.app.service.ApiResult
import com.anifichadia.app.service.ApiResult.Failure
import com.anifichadia.app.service.ApiResult.FailureType.ResponseMapping
import com.anifichadia.app.service.ApiResult.FailureType.ResponseWithStatus
import com.anifichadia.app.service.ApiResult.FailureType.ResponseWithStatusAndError
import com.anifichadia.app.service.ApiResult.FailureType.Ssl
import com.anifichadia.app.service.ApiResult.FailureType.UnclassifiedException
import com.anifichadia.app.service.ApiResult.Success
import com.anifichadia.app.service.ResponseMapper
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import javax.net.ssl.SSLException

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-08
 */
class RetrofitCallWrapper<NetworkResponseT, MappedT, NetworkErrorResponseT, ErrorT>(
    private val retrofit: Retrofit,
    private val responseMapper: ResponseMapper<Response<NetworkResponseT>, NetworkResponseT, MappedT>,
    private val errorClass: Class<NetworkErrorResponseT>,
    private val errorResponseMapper: ResponseMapper<Response<NetworkResponseT>, NetworkErrorResponseT, ErrorT>? = null
) {

    fun execute(call: Call<NetworkResponseT>): ApiResult<MappedT, ErrorT> {
        try {
            val response: Response<NetworkResponseT> = call.execute()
            val httpStatusCode: Int = response.code()

            if (response.isSuccessful) {
                try {
                    val data = responseMapper.map(response, response.body())

                    return Success<MappedT, ErrorT>(httpStatusCode, data)
                } catch (e: Throwable) {
                    return Failure<MappedT, ErrorT>(ResponseMapping(httpStatusCode, e, true))
                }
            } else {
                val errorBody = response.errorBody()

                if (errorResponseMapper != null && errorBody != null) {
                    try {
                        val responseConverter =
                            retrofit.responseBodyConverter<NetworkErrorResponseT>(errorClass, emptyArray())
                        val errorNetworkResponse = responseConverter.convert(errorBody)
                        val error = errorResponseMapper.map(response, errorNetworkResponse)

                        return Failure(ResponseWithStatusAndError(httpStatusCode, error))
                    } catch (e: Throwable) {
                        return Failure(ResponseMapping(httpStatusCode, e, false))
                    }
                } else {
                    return Failure(ResponseWithStatus(httpStatusCode))
                }
            }
        } catch (e: SSLException) {
            return Failure(Ssl(e))
        } catch (e: Throwable) {
            return Failure(UnclassifiedException(e))
        }
    }
}
