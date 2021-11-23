package com.example.nats

import io.nats.client.Connection
import io.nats.client.Nats
import io.nats.client.Options
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Configuration {

    companion object {
        val log : Logger = LoggerFactory.getLogger(this.javaClass)
    }

    fun initConnection(): Connection {
        val options: Options = Options.Builder()
            .errorCb { ex -> log.error("Connection Exception: ", ex) }
            .disconnectedCb { event -> log.error("Channel disconnected: {}", event.connection) }
            .reconnectedCb { event -> log.error("Reconnected to server: {}", event.connection) }
            .build()

        return Nats.connect("nats://localhost:4222", options)
    }
}