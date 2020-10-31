  
- Spring Boot
- Docker, Docker compose  
- Swagger
- Lombok
- H2 - JPA
- Kafka
- Saga

### Overview

| Application & modules         | Port |
|-------------------------------|------|
| Kafka                         | 9092 |
| Zookeeper                     | 2181 |
| cloud-config-server           | 9091 | 
| cloud-gateway                 | 9090 |
| customer-service              | 9192 | 
| order-service                 | 9191 |

### Run the application using Docker Compose
The main folder of this repository contains a functional docker-compose.yml file. Run the application using it as shown below:

```
$ mvn clean package -DskipTests
$ docker-compose build
$ docker-compose up -d
```

##### Example Request
```
POST- http://localhost:9090/customer/saveCustomer
BODY: {
        "name":"TestUser1",
        "creditLimit": 300,
        "creditReservation": 0
       }

POST- http://localhost:9090/order/saveOrder
BODY: {
          "customerId":1,
          "amount":50
      }

GET- http://localhost:9090/customer/getCustomerById/1

GET- http://localhost:9090/order/getOrderById/1

```

### Run the application on your local machine 
The main folder of this repository contains a functional docker-compose-local-kafka.yml file. 
Run the kafka using it as shown below:

```
$ docker-compose -f docker-compose-local-kafka.yml up -d
```

**`Then start applications from IDE`**

### Swagger 

- **order:** [http://localhost:9191/swagger-ui.html](http://localhost:9191/swagger-ui.html)
- **customer:** [http://localhost:9192/swagger-ui.html](http://localhost:9192/swagger-ui.html)

### h2 Database 

- **order:** [http://localhost:9191/h2-console](http://localhost:9191/h2-console)
- **customer:** [http://localhost:9192/h2-console](http://localhost:9192/h2-console)
```
spring.datasource.url = jdbc:h2:mem:testdb
spring.datasource.driverClassName= org.h2.Driver
spring.datasource.username = sa				
spring.datasource.password = 
```



