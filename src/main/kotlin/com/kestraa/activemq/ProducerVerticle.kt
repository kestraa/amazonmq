package com.kestraa.activemq

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.logging.LoggerFactory
import io.vertx.core.json.JsonObject
import java.time.LocalDateTime
import java.util.*
import javax.jms.DeliveryMode
import javax.jms.Session

class ProducerVerticle : AbstractVerticle() {
    private val logger = LoggerFactory.getLogger(ProducerVerticle::class.java)
    
    override fun start(promise: Promise<Void>) {
        val connection: javax.jms.Connection? = Connection.build("kestraa-producer")
        if (Objects.isNull(connection)) {
            promise.fail("Could not connect to the server.")
            return
        }
        
        vertx.periodicStream(1000).handler {
            val now = LocalDateTime.now().toString()
            send(connection!!, JsonObject().put("message", "Hello from vertx! - $now"))
        }
    }

    override fun stop(promise: Promise<Void>) {
        promise.complete()
    }
    
    private fun send(connection: javax.jms.Connection, json: JsonObject) {
        connection.runCatching {
            val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
            val destination = session.createQueue("health.dev.queue")
            val producer = session.createProducer(destination)
            producer.deliveryMode = DeliveryMode.PERSISTENT
            producer.timeToLive = 15000
            
            val message = session.createTextMessage(json.encode())
            producer.send(message)
            
            producer.close()
            session.close()
        }.onFailure { logger.error(it.message) }
        .onSuccess { logger.info("Message sent!") }
    }
    
}
