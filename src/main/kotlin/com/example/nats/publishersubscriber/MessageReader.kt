package com.example.nats.publishersubscriber

import com.example.nats.Configuration

fun main() {
    try {
        val nats = Configuration()
        val natsConnection = nats.initConnection()

        print("Reading...")

        while(true){
            println("Waiting for a message...")

            val sub = natsConnection.subscribe("kotlin-nats-demo")
            natsConnection.flush(5000)

            val msg = sub.nextMessage(10000)
            if(msg != null) {
                println("Received ${msg.subject} ${String(msg.data)}")
            }
        }

    } catch (exp: Exception) {
        exp.printStackTrace()
    }

}