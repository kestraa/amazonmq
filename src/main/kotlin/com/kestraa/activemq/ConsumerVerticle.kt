package com.kestraa.activemq

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.logging.LoggerFactory
import org.apache.qpid.proton.amqp.messaging.AmqpValue

class ConsumerVerticle : AbstractVerticle() {
    private val logger = LoggerFactory.getLogger(ConsumerVerticle::class.java)
    
    override fun start(promise: Promise<Void>) {
        Connection.create(vertx, "kestraa-producer")
            .future()
            .onSuccess { connection ->
                connection.container = "kestraa-receiver"
                connection.open()
                
                val receiver = connection.createReceiver("health.dev.queue").handler { _, message ->
                    val data = message.body as AmqpValue
                    val content = data.value as String
                    logger.info("Message received: $content")
                }
                receiver.open()
            }
            .onFailure { err -> logger.error(err.message) }
    }
    
}
