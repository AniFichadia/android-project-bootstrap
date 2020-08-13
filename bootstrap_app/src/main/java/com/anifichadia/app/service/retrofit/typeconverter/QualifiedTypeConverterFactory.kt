package com.anifichadia.app.service.retrofit.typeconverter

import com.anifichadia.app.service.Xml
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * @author Aniruddh Fichadia
 * @date 2018-07-10
 */
class QualifiedTypeConverterFactory(
    private val jsonFactory: Converter.Factory,
    private val xmlFactory: Converter.Factory
) : Converter.Factory() {

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return if (isAnnotatedAsXml(methodAnnotations)) {
            xmlFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
        } else {
            jsonFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return if (isAnnotatedAsXml(annotations)) {
            xmlFactory.responseBodyConverter(type, annotations, retrofit)
        } else {
            jsonFactory.responseBodyConverter(type, annotations, retrofit)
        }
    }

    private fun isAnnotatedAsXml(annotations: Array<out Annotation>?): Boolean {
        return annotations?.find { annotation -> annotation is Xml } != null
    }
}
