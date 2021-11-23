package com.example.nats.exchange

import com.example.nats.Configuration
import io.nats.client.Message
import io.nats.client.SyncSubscription

fun main() {
    val nats = Configuration()
    val natsConnection = nats.initConnection()

    val fooSubscription: SyncSubscription = natsConnection.subscribe("foo.bar")
    val barSubscription: SyncSubscription = natsConnection.subscribe("bar.foo")
    natsConnection.publish("foo.bar", "bar.foo", "hello there".toByteArray())

    var message: Message = fooSubscription.nextMessage()
    println("Receiver:${message.subject} @  ${String(message.data)}")

    natsConnection
        .publish(message.replyTo, message.subject, "hello back".toByteArray())

    message = barSubscription.nextMessage()
    println("Receiver:${message.subject} @  ${String(message.data)}")
}