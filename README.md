# amazonmq

Simple application that uses AmazonMQ

## Environment Variables

You must set up following environment variables:

    * HOST: "amqps://x-xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx-x.mq.us-east-1.amazonaws.com:5671"
    * USER: 
    * PASSWORD: 
    
## Running 
```bash
gradle run
```

You must see the similar output:
```bash
[INFO] POC Artemis
2020-03-06 20:17:33,869 INFO  o.a.q.j.s.SaslMechanismFinder - Best match for SASL auth was: SASL-PLAIN
2020-03-06 20:17:33,869 INFO  o.a.q.j.s.SaslMechanismFinder - Best match for SASL auth was: SASL-PLAIN
2020-03-06 20:17:34,545 INFO  o.a.q.jms.JmsConnection - Connection ID:16e5a10f-4662-4d33-b732-ffc07fa7d159:1 connected to remote Broker: amqps://b-07c61fc4-7790-4cd1-a222-55941ac92ccb-1.mq.us-east-2.amazonaws.com:5671
2020-03-06 20:17:34,545 INFO  o.a.q.jms.JmsConnection - Connection ID:bdaa4880-ba07-4e01-b636-880f13e73793:1 connected to remote Broker: amqps://b-07c61fc4-7790-4cd1-a222-55941ac92ccb-1.mq.us-east-2.amazonaws.com:5671
2020-03-06 20:17:36,224 INFO  c.k.a.ProducerVerticle - Message sent!
2020-03-06 20:17:36,386 INFO  c.k.a.ConsumerVerticle - Message received - {"message":"Hello from vertx! - 2020-03-06T20:17:35.586"}
2020-03-06 20:17:36,717 INFO  c.k.a.ProducerVerticle - Message sent!
2020-03-06 20:17:36,913 INFO  c.k.a.ConsumerVerticle - Message received - {"message":"Hello from vertx! - 2020-03-06T20:17:36.551"}
```