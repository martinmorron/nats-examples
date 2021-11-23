package com.example.nats.wildcards

import com.example.nats.Configuration

/***
    Wildcards operate on topic tokens that are separated with the ’.’ character.
    The asterisk character ‘*' matches an individual token.
    The greater-than symbol ‘>' is a wildcard match for the remainder of a topic, which may be more than one token.
    For example:
        foo.* matches foo.bar, foo.requests, but not foo.bar.requests
        foo.> matches foo.bar, foo.requests, foo.bar.requests, foo.bar.baeldung, etc.
 */
fun main() {
    val nats = Configuration()
    val client = nats.initConnection()

    val fooSubscription = client.subscribeSync("foo.*")

    client.publish("foo.bar", "bar.foo", "hello there".toByteArray())

    var message = fooSubscription.nextMessage(200)
    println("Receiver:${message.subject} @  ${String(message.data)}")

    client.publish("foo.bar.plop", "bar.foo", "hello there".toByteArray())
    message = fooSubscription.nextMessage(200)
    if(message == null) {
        println("Got message! :${message}")
    }

    val barSubscription = client.subscribeSync("foo.>")

    client.publish("foo.bar.plop", "bar.foo", "hello there".toByteArray())

    message = barSubscription.nextMessage(200)
    println("Receiver:${message.subject} @  ${String(message.data)}")

}