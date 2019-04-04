# apache-kafka-intro
Learn Apache Kafka 2.0 Ecosystem

> ## Contents

**[Why Apache Kafka](#intro)**<br>
**[Apache Kafka Theory](#theory)**<br>


<a name="intro"></a>
> ## Why Apache Kafka
- You want to process data points as they arrive
- You could have millions of sources, thousands of destinations, and the demand is for real-time analytics
- Initially, you have a source system and a target system and they have to exchange data
<img width="143" alt="1" src="https://user-images.githubusercontent.com/4720428/55256259-df911c80-5219-11e9-80dd-9131a18e8cb9.png"  width="100" height="200">

- After a while, you have many source systems and many target systems and they all have to exchange data with one another and things become complicated

<img width="650" alt="2" src="https://user-images.githubusercontent.com/4720428/55256338-16673280-521a-11e9-92fe-092071f355f9.png"
 width="100" height="200">

- If you have 4 source systems and 6 target systems, you have 24 integrations to write
- Each time you integrate a source system with a target system, you will have an increased load from the connection
- For each integration, you have choose:
    - Protocol - How data is transported (TCP, HTTP, REST, FTP, JDBC..)
    - Data Format - How the data is parsed (Binary, CSV, JSON, Avro, Thrift..)
    - Data Schema and Evolution - How the data is shaped and how it may change in the future 
- To solve these issues, you can use Apache Kafka
- Apache Kafka allows you to decouple your source and target systems
- You source systems will have their data in Apache Kafka and your target systems will source data from Apache Kafka

<img width="648" alt="3" src="https://user-images.githubusercontent.com/4720428/55256392-3565c480-521a-11e9-8e58-7386e4831c9e.png"
 width="100" height="200">

- It was created by LinkedIn and is now maintained by Confluent
- It is distributed, fault-tolerant, resilient, and it scales
- 35% of all Fortune 500 companies use Apache Kafka
- Use Cases (Apache Kafka is only a transportation mechanism)
    - Netflix uses Apache Kafka for real-time movie recommendations
    - Uber uses Apache Kafka for computing real-time surge pricing
    - LinkedIn uses Apache Kafka to make real-time connection recommendations 
    
<a name="theory"></a>
> ## Apache Kafka Theory
- Topics are logical collections of messages. It is similar to a table in a database. You can have as many topics as you want in Apache Kafka. It's identified by its name
- Topics are split into partitions. 
- Each partition is split into offsets that have an incremental id called an offset
- To identify a message, you have to specify the Kafka topic name, partition number, and offset number
<img width="438" alt="4" src="https://user-images.githubusercontent.com/4720428/55266539-c51b6b00-523a-11e9-9797-45dc066db90b.png">

- Order is guaranteed only within a partition and not across partitions
- Data stored at the offsets is only kept for a limited amount of time (default is one week)
- Once data is written to a partition it cannot be changed (immutable)
- Data is written randomly to partition 0, 1, or 2, if you don't provide a key
- An example of a topic is as follows:
    - You have a fleet of trucks and each truck reports its GPS coordinates to Kafka using a some mechanism
    - You can create a Kafka topic with name "trucks_gps"
    - Each truck will send data to Kafka every 20 seconds and each message will contain the ID of the truck and its latitude and longitudinal coordinates
    - All trucks will send data to that one topic, you do not have one topic per truck
    - You can create the topic with 10 partitions (arbitarily chosen)
    - Once you have the data in Kafka, you can consumers of data, say an location dashboard application, or you can have a notification application, if a truck is driving too long without a break send a message asking the driver to take a break
<img width="608" alt="5" src="https://user-images.githubusercontent.com/4720428/55267013-ff860780-523c-11e9-9871-a39c8011c649.png">

- A Kafka cluster is composed of multiple brokers (servers)
- Each broker is identified by its ID
- Each broker contains some topic partitions
- If you connect to one broker, you are connected to the entire Kafka cluster
- A good number to get started is 3 brokers, some large companies have more than 100 brokers
- For example, you have 3 brokers with IDs 101, 102, 103 (arbitary) and you have two topics: Topic-A with two partitions. When you create a topic, you also need to decide on the replication factor, say 2.
- For a given partition, you have only one leader that receives and serves data for that partition, the other brokers synchronize that data. If a leader broker goes down, you have an election to choose new leader. The leader and in sync replicas are decided by Zookeeper.
<img width="622" alt="6" src="https://user-images.githubusercontent.com/4720428/55285499-2bd77c00-5342-11e9-8657-5bd3ff5c1a6f.png">

- Producers write data to topics (made up of partitions)
- Producers automatically know which broker and partition to write to
- Producer can choose to recieve acknowledgements for data writes
    - acks = 0: Producer does not wait for acknowldegment (data loss)
    - acks = 1: Producer waits for leader acknowledgement (limited data loss)
    - acks = 2: Leader + replica acknowledgement (no data loss)
- If message key = null, sends round robin to broker 101, 102, and 103
- If message key is sent, all messages for that key will always go to the same partition (key hashing)
<img width="638" alt="7" src="https://user-images.githubusercontent.com/4720428/55285611-16635180-5344-11e9-9b1b-01513c3e260e.png">

- Consumers read data from a topic (identified by name)
- Consumers know which broker to read from
- Consumer reads data in order from each partition
- Consumers can be grouped as a "Consumer Group" to read data from exclusive partitions for faster performance
- Consumer group represents an application
<img width="690" alt="8" src="https://user-images.githubusercontent.com/4720428/55285816-c8504d00-5347-11e9-83d5-a8f9cb9d5601.png">

- Consumer offsets - Kafka stores offsets at which a consumer group has been reading, checkpointing, or bookmarking
- The offsets are commited live into a topic named __consumer_offsets
- When a consumer in a group has processed data recieved data from Kafka , it commits the offsets
- This is done because if a consumer goes down , it will be able to read back from where it left off 
- Kafka Bootstrap Server - You pnly have to connect to one broker and you are automatically connected to the entire Kafka cluster
- Zookeeper - Keeps a list of all brokers. If a broker goes down it enables election of a new leader
- Kafka cannot work without Zookeeper
<img width="635" alt="9" src="https://user-images.githubusercontent.com/4720428/55285971-4ca3cf80-534a-11e9-9ba3-e03a2f8064c4.png">


    








