# apache-kafka-intro
Learn Apache Kafka 2.0 Ecosystem

> ## Contents

**[Why Apache Kafka](#intro)**<br>
**[Apache Kafka Theory](#theory)**<br>
**[Apache Kafka API](#api)**<br>


<a name="intro"></a>
> ## Why Do We Need Real Time?
- To react to events as they happen, you need to process data as it arrives
- Process data in real time
- Value of data decreases with time
- Data is a continuous stream of events
- An event is a significant change in state
    
<a name="batchtorealtime"></a>
> ## Batch to Real Time => Lambda Architecture
- Data is stored in both HDFS and Apache Storm 
- Drawbacks
  - Events processed out of order
  - Events are lost due to node failures
  - Manage both a Hadoop cluster and a Storm cluster
  
  <a name="streamingarchitecture"></a>
> ## Batch to Real Time => Lambda Architecture
- Is a Message Data Bus 
- Benefits
  - Only one cluster
  - Decouples source and target systems
  - Distributed, fault tolerant, resilient, scalable
- Popular Messaging Systems
  - Apache Kafka (Confluent)
  - MapR Event Store for Apache Kafka (MapR)
  
   <a name="streamingarchitecture"></a>
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

<a name="StreamingExample"></a>
> ## Streaming Example
- Each truck reports its GPS coordinates to Kafka using a some mechanism
  - Create a Kafka topic with name "trucks_gps“ with 10 partitions (arbitrarily chosen)
  - GPS coordinates sent to Kafka every 20 seconds
  - Each message constitutes the truck ID and coordinates
  - All trucks will send data to that one topic, you do not have one topic per truck
  - Once you have the data in Kafka, you can write a consumer application, say a location dashboard application or an application to calculate velocity of each truck


 
 
    








