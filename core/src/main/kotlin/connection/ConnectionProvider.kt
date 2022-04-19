package connection

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import model.ConnectionProperties

internal class ConnectionProvider(
    private val connectionProperties: ConnectionProperties,
    private val virtualHost: String
) {
    private val connectionFactory: ConnectionFactory = ConnectionFactory()
    private var connection: Connection

    init {
        connectionFactory.apply {
            username = connectionProperties.username
            password = connectionProperties.password
            host = connectionProperties.host
            port = connectionProperties.port
            virtualHost = virtualHost
            isAutomaticRecoveryEnabled = false
        }
        connection = createConnection()
    }

    private fun createConnection() =
        connectionFactory.newConnection()

    fun createChannel(): Channel {
        if(!connection.isOpen){
            connection = createConnection()
        }
        return connection.createChannel()
    }

    fun close() {
        connection.close()
    }
}
