# apache-kafka-intro
Learn Apache Kafka 2.0 Ecosystem

> ## Contents

**[Apache Kafka Intro](#history)**<br>


<a name="intro"></a>
> ## Why Apache Kafka
- Initially, you have a source system and a target system and they have to exchange data
- After a while, you have many source systems and many target systems and they all have to exchange data with one another and things become complicated
- If you have 4 source systems and 6 target systems, you have 24 integrations to write
- Each time you integrate a source system with a target system, you will an increased load from the connection
- For each integration, you have choose:
    - Protocol - How data is transported (TCP, HTTP, REST, FTP, JDBC..)
    - Data Format - How the data is parsed (Binary, CSV, JSON, Avro, Thrift..)
    - Data Schema and Evolution - How the data is shaped and how it may change in the future ()
- To solve these issues, you can use Apache Kafka
- Apache Kafka allows ou to decouple your source and target systems
- You source systems will have their data in Apache Kafka and your target systems will source data from Apache Kafka
- It was created by LinkedIn and is now maintained by Confluent
- It is distributed, fault-tolerant, resilient and it scales
- 35% of all Fortune 500 companies use Apache Kafka
- Use Cases (Apache Kafka is only a transportation mechanism)
    - Netflix uses Apache Kafka for real-time movie recommendations
    - Uber uses Apache Kafka for computing real-time surge pricing
    - LinkedIn uses Apache Kafka to make real-time connection recommendations 
 
    








