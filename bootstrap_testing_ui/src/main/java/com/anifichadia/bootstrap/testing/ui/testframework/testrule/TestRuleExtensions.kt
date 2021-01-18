package com.anifichadia.bootstrap.testing.ui.testframework.testrule

import org.junit.runner.Description
import kotlin.reflect.KClass

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-09
 */

internal fun Description.testName() = "$className.$methodName"


internal fun <AnnotationT : Annotation> Description.retrieveAnnotation(annotationClass: Class<AnnotationT>): AnnotationT? {
    return this.getAnnotation(annotationClass) ?: this.testClass.getAnnotation(annotationClass)
}

internal fun <AnnotationT : Annotation> Description.retrieveAnnotation(annotationClass: KClass<AnnotationT>): AnnotationT? {
    return retrieveAnnotation(annotationClass.java)
}
