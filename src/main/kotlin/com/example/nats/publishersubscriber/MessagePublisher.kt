package com.example.nats.publishersubscriber

import com.example.nats.Configuration
import java.util.*

fun main() {
    try {
        val nats = Configuration()
        val natsConnection = nats.initConnection()

        print("About to publish...")

        var count = 0
        while(true) {
            val payload = "payload # $count + @${Date()}".toByteArray()
            natsConnection.publish("kotlin-nats-demo", payload)
            natsConnection.flush(10000)
            Thread.sleep(2000)
            println("Done. #$count")
            count++
        }


    } catch (exp: Exception) {
        exp.printStackTrace()
    }

}