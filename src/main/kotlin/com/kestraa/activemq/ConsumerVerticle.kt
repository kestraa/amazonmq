package com.kestraa.activemq

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.logging.LoggerFactory
import java.util.Objects.isNull
import javax.jms.*

class ConsumerVerticle : AbstractVerticle() {
    private val logger = LoggerFactory.getLogger(ConsumerVerticle::class.java)
    
    override fun start(promise: Promise<Void>) {
        val connection: javax.jms.Connection? = Connection.build("kestraa-consumer")
        if (isNull(connection)) {
            promise.fail("Could not connect to the server.")
            return
        }
        
        receive(connection!!)
    }
    
    private fun receive(connection: javax.jms.Connection) {
        connection.runCatching {
            val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
            val destination = session.createQueue("health.dev.queue")
            val consumer = session.createConsumer(destination)
            
            consumer.messageListener = MessageListener { msg ->
                val textMsg = msg as TextMessage
                logger.info("Message received - ${textMsg.text}")
            }
        }.onFailure { logger.error(it.message) }
    }
    
}
