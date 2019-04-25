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


<a name="intro"></a>
> ## Why Do We Need Real Time?
- To react to events as they happen, you need to process data as it arrives
- Process data in real time
- Value of data decreases with time
- Data is a continuous stream of events
- An event is a significant change in state
![13](https://user-images.githubusercontent.com/4720428/56757364-e04d9d80-6748-11e9-8a30-e668745905f6.png)
    
<a name="batchtorealtime"></a>
> ## Batch to Real Time => Lambda Architecture
- Data is stored in both HDFS and Apache Storm 
- Drawbacks
  - Events processed out of order
  - Events are lost due to node failures
  - Manage both a Hadoop cluster and a Storm cluster
  ![14](https://user-images.githubusercontent.com/4720428/56757501-39b5cc80-6749-11e9-8695-bb1a40ef534f.png)
  - Integrate source and target systems
  
  <a name="streamingarchitecture"></a>
> ## Streaming Architecture
- Is a Message Data Bus 
- Benefits
  - Only one cluster
  - Decouples source and target systems
  - Distributed, fault tolerant, resilient, scalable
- Popular Messaging Systems
  - Apache Kafka (Confluent)
  - MapR Event Store for Apache Kafka (MapR)
  
   <a name="concepts"></a>
> ## Kafka / MapR Stream Concepts
- Topic
  - Logical collection of messages or events
  - You can have as many topics as you want
  - Identified by its name
- Partition
  - Topics are split into partitions for parallelism
- Offsets
  - Partitions are split into offsets with incremental IDs
- Streams (specific to MapR Event Store)
  - Stream is a collection of topics
- Some important points:
  - To identify a message you need to specify the stream name, topic name, partition number, and offset ID 
  - Once data is written to a partition it cannot be changed
  - Data stored at the offsets is only kept for a limited amount of time
  - Order is guaranteed only within a partition and not across partitions
  - Data is written randomly to partition 0, 1, or 2, if you don't provide a key

<a name="streamingexample"></a>
> ## Streaming Example
- Each truck reports its GPS coordinates to Kafka using a some mechanism
  - Create a Kafka topic with name "trucks_gps“ with 10 partitions (arbitrarily chosen)
  - GPS coordinates sent to Kafka every 20 seconds
  - Each message constitutes the truck ID and coordinates
  - All trucks will send data to that one topic, you do not have one topic per truck
  - Once you have the data in Kafka, you can write a consumer application, say a location dashboard application or an application to calculate velocity of each truck
  
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
-List the Topic
```
[root@maprdemo mapr]# maprcli stream topic list -path /sample-stream
```
-List the Topic
```
[root@maprdemo mapr]# maprcli stream topic list -path /sample-stream
```
-Produce to the Topic
```
[root@maprdemo bin]# ./kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic /sample-stream:fast-messages
```
-Consume from the Topic
```
[root@maprdemo bin]# ./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic /sample-stream:fast-messages
```

<a name="backend"></a>
> ## Kafka Backend
- A topic or stream is split across the cluster
  - Broker or server: 3 (IDs 101, 102, 103) 
  - Topics: 1 (Topic-A) 
  - Partitions: 2 (0 and 1) 
  - Replication factor: 1
- For a given partition, you have only one leader that receives and serves data for that partition, the other brokers synchronize that data
- If a leader broker goes down, you have an election to choose new leader. The leader and in sync replicas are decided by Zookeeper

<a name="producer"></a>
> ## Producers
- Write data to topics
  - Producers automatically know which broker and partition to write to
  - Delivery semantics indicates the integrity of data as it moves from point A to point B:
   - acks = 0: Producer does not wait for acknowledgment (data loss) - At most once
   - acks = 1: Producer waits for leader acknowledgement (limited data loss) - At least once
   - acks = 2: Producer waits for leader + replica acknowledgement (no data loss) - Exactly once, expensive
   
<a name="consumer"></a>
> ## Consumers
- Read data from a topic 
  - A single consumer can read from only one partition so there is no contention among consumers
  - Consumer Groups are a group of consumers working together
  - Consumers in a consumer group read data from exclusive partitions for faster performance, each consumer would get a subset of the messages
  - Consumer group represents an application
  - Consumer offsets - Kafka stores offsets at which a consumer group has been reading to help the consumer group keep track of where it is as it's reading data from a topic
  
<a name="architecture"></a>
> ## Kafka Streaming Architecture 

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
-Create a Topic
```
C:\kafka_2.12-2.2.0> kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1
```
-Start a Consumer
```
C:\kafka_2.12-2.2.0> kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic twitter_tweets
```
<a name="javademo"></a>
> ## Kafka Demo – Part 1

<a name="eco"></a>
> ## Kafka Eco Components
- Kafka Connect Source
- Kafka Connect Sink
- KStreams (Spark Streaming or Apache Flink for advanced computation)
- KSQL

<a name="javademo2"></a>
> ## Kafka Demo – Part 2

<a name="admin"></a>
> ## Admin
- Kafka Admin
    -Kafka Cluster Setup
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

<a name="usecase2"></a>
> ## Use Cases 2 - TrendBook





 
 
    








