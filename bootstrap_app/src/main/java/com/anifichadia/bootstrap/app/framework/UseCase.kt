package com.anifichadia.bootstrap.app.framework

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-13
 */
interface UseCase<InputT, OutputT> {
    suspend fun execute(input: InputT): OutputT
}
