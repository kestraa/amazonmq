package com.kestraa.activemq

import io.vertx.core.Vertx

fun main() {
    System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory")

    val app = Vertx.vertx()
    
    app.deployVerticle("com.kestraa.activemq.ConsumerVerticle")
    app.deployVerticle("com.kestraa.activemq.ProducerVerticle")

    println("[INFO] POC Artemis")
}
