# apache-kafka-intro
Learn Apache Kafka 2.0 Ecosystem

> ## Contents

**[Why Apache Kafka](#intro)**<br>
**[Apache Kafka Theory](#theory)**<br>


<a name="intro"></a>
> ## Why Apache Kafka
- Initially, you have a source system and a target system and they have to exchange data
<img width="143" alt="1" src="https://user-images.githubusercontent.com/4720428/55256259-df911c80-5219-11e9-80dd-9131a18e8cb9.png">

- After a while, you have many source systems and many target systems and they all have to exchange data with one another and things become complicated
<img width="650" alt="2" src="https://user-images.githubusercontent.com/4720428/55256338-16673280-521a-11e9-92fe-092071f355f9.png">

- If you have 4 source systems and 6 target systems, you have 24 integrations to write
- Each time you integrate a source system with a target system, you will an increased load from the connection
- For each integration, you have choose:
    - Protocol - How data is transported (TCP, HTTP, REST, FTP, JDBC..)
    - Data Format - How the data is parsed (Binary, CSV, JSON, Avro, Thrift..)
    - Data Schema and Evolution - How the data is shaped and how it may change in the future 
- To solve these issues, you can use Apache Kafka
- Apache Kafka allows you to decouple your source and target systems
- You source systems will have their data in Apache Kafka and your target systems will source data from Apache Kafka
<img width="648" alt="3" src="https://user-images.githubusercontent.com/4720428/55256392-3565c480-521a-11e9-8e58-7386e4831c9e.png">

- It was created by LinkedIn and is now maintained by Confluent
- It is distributed, fault-tolerant, resilient, and it scales
- 35% of all Fortune 500 companies use Apache Kafka
- Use Cases (Apache Kafka is only a transportation mechanism)
    - Netflix uses Apache Kafka for real-time movie recommendations
    - Uber uses Apache Kafka for computing real-time surge pricing
    - LinkedIn uses Apache Kafka to make real-time connection recommendations 
    
<a name="theory"></a>
> ## Apache Kafka Theory
- A topic in Apache Kafka is the base of everything. A topic is particular stream of data. It is similar to a table in a database. You can have as many topics as you want in Apache Kafka. It's identified by its name
- Topics are split into partitions. Each partition is ordered and each message within a partition has an incremental id called an offset
- To identify a message, you have to say Kafka topic name, partition number, offset number
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

- 

    








