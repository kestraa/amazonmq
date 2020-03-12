package com.kestraa.activemq

import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.proton.ProtonClient
import io.vertx.proton.ProtonClientOptions
import io.vertx.proton.ProtonConnection

object Connection {
    private val PORT = 5671
    private val HOST = System.getenv("HOST")
    private val USERNAME = System.getenv("USER")
    private val PASSWORD = System.getenv("PASSWORD")
    
    fun create(vertx: Vertx, clientId: String): Promise<ProtonConnection> {
        val promise = Promise.promise<ProtonConnection>()
        val client = ProtonClient.create(vertx)
        
        val options = ProtonClientOptions()
        options.let {
            it.heartbeat = 5000
            it.connectTimeout = 3000
            it.isSsl = true
        }
        
        client.connect(options, HOST, PORT, USERNAME, PASSWORD) { res ->
            if (res.failed()) {
                promise.fail(res.cause().message)
                return@connect
            }
    
            val connection = res.result()
            connection.container = clientId
            promise.complete(connection)
        }
        
        return promise
    }
    
}
