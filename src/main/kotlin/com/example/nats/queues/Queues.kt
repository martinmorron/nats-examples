package com.example.nats.queues

import com.example.nats.Configuration
import io.nats.client.Message

/**
 * Using queues, Nats will route the messages to some queue, and they will be consumed once.
 * Instead, using normal subscribers since both are subscribed, both will consume the message.
 * Expected result:
 *
 * Messages in queue: 1 (Using queues)
 * Messages in queue: 2 (Using subscribers)
 */
fun main() {
    val nats = Configuration()
    val natsConnection = nats.initConnection()

    val queue1 = natsConnection.subscribe("foo", "queue-test")
    val queue2 = natsConnection.subscribe("foo", "queue-test")

    natsConnection.publish("foo", "Queue-example".toByteArray())

    val messages: MutableList<Message> = ArrayList()

    while(messages.isEmpty()) {
        var message: Message? = queue1.nextMessage(2000)
        if (message != null) messages.add(message)

        message = queue2.nextMessage(2000)
        if (message != null) messages.add(message)

        println("Messages in queue: ${messages.size} = [${messages}]")
    }

    val subscription1 = natsConnection.subscribe("foo")
    val subscription2 = natsConnection.subscribe("foo")

    val messagesNoQueued: MutableList<Message> = ArrayList()

    natsConnection.publish("foo", "No-Queue-example".toByteArray())

    while(messagesNoQueued.isEmpty()) {
        var message: Message? = subscription1.nextMessage(2000)
        if (message != null) messagesNoQueued.add(message)

        message = subscription2.nextMessage(2000)
        if (message != null) messagesNoQueued.add(message)

        println("Messages in queue: ${messagesNoQueued.size} = [${messagesNoQueued}]")
    }
}