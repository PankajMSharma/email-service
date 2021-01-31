# Email-Service
Listens email message from Kafka and sends respective email to appropriate recipient.

# What this project does?
1. Consumes email from topic in Kafka messenger service.
2. Sends out email from messages it consumes.

# Steps to start Kafka on console
1. Goto Kafka folder
2. Run Zookeeper Instance
 `.\bin\windows\zookeeper-server-start.bat config\zookeeper.properties`
3. Start Kafka Server
 `.\bin\windows\kafka-server-start.bat config\server.properties`
4. Create Kafka Topic (If not created)
 `.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic Kafka_Example`
5. Create Consumer
 `.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic Kafka_Example --from-beginning`