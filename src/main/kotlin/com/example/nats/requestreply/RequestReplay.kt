package com.example.nats.requestreply

import com.example.nats.Configuration

/***
 * This method creates a temporary mailbox for the response, and write the reply-to address for us.
 * Request() returns the response, or null if the request times out.
 * The last argument is the number of milliseconds to wait.
 */
fun main() {
    val nats = Configuration()
    val natsConnection = nats.initConnection()

    println("Asking salary request...")
    natsConnection.subscribe("salary.requests") {
        natsConnection.publish(it.replyTo, "Origin[${it.subject}], raise denied!".toByteArray());
    }

    val reply = natsConnection.request("salary.requests", "I need a raise.".toByteArray(), 100);
    println("Received at ${reply.subject} @ ${String(reply.data)}")
}