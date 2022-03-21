package model

data class PendingRabbitMQMessage<T>(val value: T, val deliveryTag: Long, val channelVersion: Int)
