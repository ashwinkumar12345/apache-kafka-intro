# apache-kafka-intro
Learn Apache Kafka 2.0 Ecosystem

> ## Contents

**[Why Do We Need Real Time?](#intro)**<br>
**[Batch to Real Time => Lambda Architecture](#batchtorealtime)**<br>
**[Streaming Architecture](#streamingarchitecture)**<br>
**[Kafka / MapR Stream Concepts](#concepts)**<br>
**[Streaming Example](#streamingexample)**<br>
**[Kafka CLI (MapR Sandbox) Demo](#clidemo)**<br>
**[Kafka Backend](#backend)**<br>
**[Producers](#producer)**<br>
**[Consumers](#consumer)**<br>
**[Kafka Streaming Architecture](#architecture)**<br>
**[Kafka Java Demo](#javademo)**<br>
**[Kafka Demo – Part 1](#javademo1)**<br>
**[Kafka Eco Components](#eco)**<br>
**[Kafka Demo – Part 2](#javademo2)**<br>
**[Admin Responsibilities](#admin)**<br>
**[Use Cases 1 - MovieFlix](#usecase1)**<br>
**[Use Cases 2 - TrendBook](#usecase2)**<br>


<a name="intro"></a>
> ## Why Do We Need Real Time?

Data is a continuous stream of events. An event is a significant change in state. The value of events usually decreases with time. So, to react to events as they happen, you need to process data as it arrives.

<img src="https://user-images.githubusercontent.com/4720428/56757364-e04d9d80-6748-11e9-8a30-e668745905f6.png" alt="alt text" width="400" height="100">
    
<a name="batchtorealtime"></a>
> ## Batch to Real Time => Lambda Architecture

Twitter used to write their data to an HDFS and an Apache Storm cluster.
Writing data to both clusters had drawbacks:

 - Events were processed out of order
 - Events were lost because of node failures
 - Keeping both clusters in sync was difficult
  
<img src="https://user-images.githubusercontent.com/4720428/56757501-39b5cc80-6749-11e9-8695-bb1a40ef534f.png" alt="alt text" width="300" height="300">
  
It became important to find a reliable way to integrate source and target systems without needing to maintain data in two separate places.
  
  <a name="streamingarchitecture"></a>
> ## Streaming Architecture

A message bus was the solution.
With a message bus, you could decouple your source and target systems.
The most popular messaging systems are:

  - Apache Kafka (initially developed at LinkedIn, now managed by Confluent)
  - MapR Event Store for Apache Kafka (MapR)
  
These systems are distributed, fault tolerant, resilient, and scalable.

<img src="https://user-images.githubusercontent.com/4720428/56757653-9ca76380-6749-11e9-927a-433f77b77c1a.png" alt="alt text" width="200" height="200">
  
   <a name="concepts"></a>
> ## Kafka / MapR Stream Concepts

Topic:

 - Logical collection of messages or events
 - You can have as many topics as you want
 - Identified by its name
  
Partition:

 - Topics are split into partitions for parallelism
  
Offsets:

 - Partitions are split into offsets with incremental IDs
  
Streams (specific to MapR Event Store)

 - Stream is a collection of topics
  
Some important points:

 - To identify a message you need to specify the stream name, topic name, partition number, and offset ID 
 - Once data is written to a partition it cannot be changed
 - Data stored at the offsets is only kept for a limited amount of time
 - Order is guaranteed only within a partition and not across partitions
 - Data is written randomly to partitions 0, 1, or 2, if you don't provide a key
  
  <img width="438" alt="4" src="https://user-images.githubusercontent.com/4720428/56757751-d7110080-6749-11e9-9909-ef818b752255.png">

<a name="streamingexample"></a>
> ## Streaming Example

Trucks report their GPS coordinates to Kafka using a some mechanism.
GPS coordinates are sent to Kafka every 20 seconds.
Each message constitutes the truck ID and coordinates.
All trucks send data to one topic, you do not have one topic per truck.
Create a Kafka topic with name "trucks_gps“ with 10 partitions (arbitrarily chosen).
Once you have the data in Kafka, you can write a consumer application, say a location dashboard application or an application to calculate velocity of each truck
  
<img width="608" alt="5" src="https://user-images.githubusercontent.com/4720428/56758255-08d69700-674b-11e9-971f-9a9e2bea5ca8.png">
  
<a name="clidemo"></a>
> ## Kafka CLI (MapR Sandbox) Demo

- Create a Stream
```
[root@maprdemo mapr]# maprcli stream create -path /sample-stream
```
- Create a Topic
```
[root@maprdemo mapr]# maprcli stream topic create -path /sample-stream  -topic fast-messages --partitions 3
```
- List the Topic
```
[root@maprdemo mapr]# maprcli stream topic list -path /sample-stream
```
- List the Topic
```
[root@maprdemo mapr]# maprcli stream topic list -path /sample-stream
```
- Produce to the Topic
```
[root@maprdemo bin]# ./kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic /sample-stream:fast-messages
```
- Consume from the Topic
```
[root@maprdemo bin]# ./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic /sample-stream:fast-messages
```

<a name="backend"></a>
> ## Kafka Backend

A topic or stream is split across the cluster
 - Broker or server: 3 (IDs 101, 102, 103) 
 - Topics: 1 (Topic-A) 
 - Partitions: 2 (0 and 1) 
 - Replication factor: 1

For a given partition, you have only one leader that receives and serves data for that partition, the other brokers synchronize that data. If a leader broker goes down, you have an election to choose the new leader. The leader and in-sync replicas are decided by Zookeeper.

<img width="622" alt="6" src="https://user-images.githubusercontent.com/4720428/56758312-2dcb0a00-674b-11e9-9532-eac3e4876624.png">

<a name="producer"></a>
> ## Producers

Write data to topics. Producers automatically know which broker and partition to write to
Delivery semantics indicates the integrity of data as it moves from point A to point B:
 - acks = 0: Producer does not wait for acknowledgment (data loss) - At most once
 - acks = 1: Producer waits for leader acknowledgement (limited data loss) - At least once
 - acks = 2: Producer waits for leader + replica acknowledgement (no data loss) - Exactly once, expensive

<img width="638" alt="7" src="https://user-images.githubusercontent.com/4720428/56758357-489d7e80-674b-11e9-98b1-51f6f13eeb1a.png">
   
<a name="consumer"></a>
> ## Consumers

Read data from a topic. A single consumer can read from only one partition so there is no contention among consumers.
Consumer Groups are a group of consumers working together.
Consumers in a consumer group read data from exclusive partitions for faster performance, each consumer would get a subset of the messages. Consumer group represents an application
Consumer offsets: Kafka stores offsets at which a consumer group has been reading to help the consumer group keep track of it's location as it's reading data from a topic
  
<img width="690" alt="8" src="https://user-images.githubusercontent.com/4720428/56758392-62d75c80-674b-11e9-92a6-439b86f60a58.png">
  
<a name="architecture"></a>
> ## Kafka Streaming Architecture 

<img width="635" alt="9" src="https://user-images.githubusercontent.com/4720428/56758441-826e8500-674b-11e9-913d-9d8fb3d1b493.png">

<a name="javademo"></a>
> ## Kafka Java Demo
- Start Zookeeper
```
C:\kafka_2.12-2.2.0> zookeeper-server-start.bat config\zookeeper.properties
```
- Start Kafka Server
```
C:\kafka_2.12-2.2.0> kafka-server-start.bat config\server.properties
```
- Create a Topic
```
C:\kafka_2.12-2.2.0> kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1
```
- Start a Consumer
```
C:\kafka_2.12-2.2.0> kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic twitter_tweets
```
<a name="javademo1"></a>
> ## Kafka Demo – Part 1

<img src="https://user-images.githubusercontent.com/4720428/56758586-dbd6b400-674b-11e9-835f-00c4bd64a6de.png" alt="alt text" width="500" height="200">

<a name="eco"></a>
> ## Kafka Eco Components

- Kafka Connect Source
- Kafka Connect Sink
- KStreams (Spark Streaming or Apache Flink for advanced computation)
- KSQL

<a name="javademo2"></a>
> ## Kafka Demo – Part 2

<img src="https://user-images.githubusercontent.com/4720428/56758671-13456080-674c-11e9-96ff-447d4e7e1408.png" alt="alt text" width="600" height="300">

<a name="admin"></a>
> ## Admin Responsibilities

- Kafka Admin

   - Kafka Cluster Setup
   - Isolate Zookeeper and Kafka brokers
   - Setup Kafka Monitoring (ES + Kibana, Confluent Control Center…)
   - Setup Security
   - Setup MirrorMaker (Manage Replication)
    
- MapR Admin

   - MapR Cluster
   - MapR Monitoring
   - Secured by default


<a name="usecase1"></a>
> ## Use Cases 1 - MovieFlix

<img src="https://user-images.githubusercontent.com/4720428/56758746-396b0080-674c-11e9-91b1-4b6af1da246a.png" alt="alt text" width="600" height="300">

<a name="usecase2"></a>
> ## Use Cases 2 - TrendBook

<img src="https://user-images.githubusercontent.com/4720428/56758850-6d462600-674c-11e9-869b-172b33d3b378.png" alt="alt text" width="600" height="300">





 
 
    








