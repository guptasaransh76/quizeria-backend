# quizeria-backend

## setup
install maven

```
https://maven.apache.org/download.cgi
```

add maven to bash_profile

```
export PATH=$PATH:<Maven_Home/bin>
export JAVA_HOME=<Java_Home>
```

edit database credentials in application.properties

```
spring.datasource.url=jdbc:mysql://<MYSQL_Server>/quizeria?useSSL=false
spring.datasource.username=<DB_Username>
spring.datasource.password=<DB_Password>
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

package code

```
mvn clean package
```

run as a java jar

```
java -jar ./target/quiz-0.0.1-SNAPSHOT.jar	
```



