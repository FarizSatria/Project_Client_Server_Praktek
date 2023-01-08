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
public class FarizConfiguration {
}
```
<br>

# Membuat Application Context
Selanjutnya, setelah membuat Class Configuration, kita bisa menggunakan class AnnotationConfigApplicationContext untuk membuat Application Context<br> <br>
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/AnnotationConfigApplicationContext.html 
```java
public class ApplicationContextTest {
    @Test
    void testApplicationContext(){
        ApplicationContext context = new AnnotationConfigApplicationContext(FarizConfiguration.class);  
        Assertions.assertNotNull(context);
    }
}
```
<br>

# Singleton
Singleton adalah salah satu Design Patterns untuk pembuatan objek, dimana sebuah object hanya dibuat satu kali saja
Dan ketika kita membutuhkan object tersebut, kita hanya akan menggunakan object yang sama
```java
public class Database {
    private static Database database;
    public static Database getInstance(){
        if(database == null){
            database = new Database();
        }
        return database;
    }
    private Database(){   
    }
}
```
```java
public class DatabaseTest {
    @Test
    void testSingleton(){
        
        var database1 = Database.getInstance();
        var database2 = Database.getInstance();
        
        Assertions.assertSame(database1, database2);
    }
}public class DatabaseTest {
    @Test
    void testSingleton(){
        
        var database1 = Database.getInstance();
        var database2 = Database.getInstance();
        
        Assertions.assertSame(database1, database2);
    }
}
```








