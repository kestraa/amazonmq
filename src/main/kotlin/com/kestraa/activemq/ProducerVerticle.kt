package com.kestraa.activemq

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.proton.ProtonConnection
import io.vertx.proton.ProtonHelper
import io.vertx.proton.ProtonSender
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger

class ProducerVerticle : AbstractVerticle() {
    private val logger = LoggerFactory.getLogger(ProducerVerticle::class.java)
    
    private val counter = AtomicInteger()
    
    override fun start(promise: Promise<Void>) {
        Connection.create(vertx, "kestraa-producer")
            .future()
            .onSuccess { connection ->
                connection.container = "kestraa-producer"
                connection.open()
    
                val sender = connection.createSender("health.dev.queue")
                sender.open()
                produceMessage(sender)
            }
            .onFailure { err -> logger.error(err.message) }
    }
    
    private fun produceMessage(sender: ProtonSender) {
        vertx.periodicStream(1000).handler {
            val msgNum = counter.incrementAndGet()
            val now = LocalDateTime.now().toString()
            val data = JsonObject()
                .put("counter", msgNum)
                .put("message", "Hello from vertx Proton! - $now")
        
            val message = ProtonHelper.message(data.encode())
            sender.send(message) {
                logger.info("Message $msgNum sent.")
            }
        }
    }
    
    override fun stop(promise: Promise<Void>) {
        promise.complete()
    }
    
}
