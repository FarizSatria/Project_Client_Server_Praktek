# Project Client Server Teori

Nama : Fariz Satria Refandino <br>
NoBP : 2101082034 <br>
Kelas : Tekom 2B <br><br>

# Application Context

ApplicationContext adalah sebuah interface representasi container IoC di Spring
dan inti dari Spring Framework.<br>
ApplicationContext banyak sekali class implementasinya, secara garis besar dibagi menjadi 2 jenis implementasi, XML dan Annotation<br> <br>
Pada versi Spring 3, XML masih menjadi pilihan utama, namun sekarang sudah banyak orang beralih dari XML ke Annotation, bahkan Spring Boot pun merekomendasikan menggunakan Annotation untuk membuat aplikasi Spring <br>
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContext.html <br><br>

# Configuration

Untuk membuat ApplicationContext menggunakan Annotation, pertama kita bisa perlu membuat Configuration class
Configuration Class adalah sebuah class yang terdapat annotation @Configuration pada class tersebut
```java
@Configuration
public class HelloWorldConfiguration{
}
```
<br>




