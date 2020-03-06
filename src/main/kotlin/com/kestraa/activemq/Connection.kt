package com.kestraa.activemq

import org.apache.activemq.jms.pool.PooledConnectionFactory
import java.util.*
import javax.jms.Connection
import javax.jms.ConnectionFactory
import javax.naming.Context
import javax.naming.InitialContext

object Connection {
    private lateinit var connection: Connection
    
    fun build(clientId: String): javax.jms.Connection? {
        val host = System.getenv("HOST")
        val username = System.getenv("USER")
        val password = System.getenv("PASSWORD")
        
        val env = Hashtable<String, String>()
        env[Context.INITIAL_CONTEXT_FACTORY] = "org.apache.qpid.jms.jndi.JmsInitialContextFactory"
        env["connectionfactory.factoryLookup"] = "amqps://$host"
        val context = InitialContext(env)
    
        val factory: ConnectionFactory = context.lookup("factoryLookup") as ConnectionFactory
    
        val pooledConnectionFactory = PooledConnectionFactory()
        pooledConnectionFactory.connectionFactory = factory
        pooledConnectionFactory.maxConnections = 10
    
        pooledConnectionFactory.runCatching {
            connection = pooledConnectionFactory.createConnection(username, password)
            connection.clientID = clientId
            connection.start()
            return connection
        }.onFailure {
            it.printStackTrace()
        }
        
        return null
    }
    
}
