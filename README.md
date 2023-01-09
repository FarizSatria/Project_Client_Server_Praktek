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
<br>

# Bean
Secara default, bean merupakan singleton, artinya jika kita mengakses bean yang sama, maka dia akan mengembalikan object yang sama. Kita juga bisa mengubahnya jika tidak ingin singleton<br><br>
Untuk membuat bean, kita bisa membuat sebuah method di dalam class Configuration
Selanjutnya nama method tersebut akan menjadi nama bean nya, dan return object nya menjadi object bean nya
Method tersebut perlu kita tambahkan annotation @Bean, untuk menandakan bahwa itu adalah bean
Secara otomatis Spring akan mengeksekusi method tersebut, dan return value nya akan dijadikan object bean secara otomatis, dan disimpan di container IoC
```java
@Slf4j
@Configuration
public class BeanConfiguration {
    @Bean
    public Foo foo(){
        Foo foo = new Foo();
        log.info("Create new foo");
        return foo;
    }
}
```
```java
public class BeanTest {
    @Test
    void testCreateBean(){
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class); 
        Assertions.assertNotNull(context);
    }
    @Test
    void testGetBean(){
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);  
        Foo foo1 = context.getBean(Foo.class);
        Foo foo2 = context.getBean(Foo.class);  
        Assertions.assertSame(foo1, foo2);
    }
}
```
<br>

# Duplicate Bean
Di Spring, kita bisa mendaftarkan beberapa bean dengan tipe yang sama
Namun perlu diperhatikan, jika kita membuat bean dengan tipe data yang sama, maka kita harus menggunakan nama bean yang berbeda
Selain itu, saat kita mengakses bean nya, kita wajib menyebutkan nama bean nya, karena jika tidak, Spring akan bingung harus mengakses bean yang mana
```java
@Configuration
public class DuplicateConfiguration {
    @Bean
    public Foo foo1(){
        return new Foo();
    }    
    @Bean
    public Foo foo2(){
        return new Foo();
    }
}
```
```java
public class DuplicateTest {   
    @Test
    void testDuplicate(){       
        ApplicationContext context = new AnnotationConfigApplicationContext(DuplicateConfiguration.class);       
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,() -> {
            Foo foo = context.getBean(Foo.class);
        });      
    }
}
```
# Primary Bean
Dengan memilih salah satunya menjadi primary, secara otomatis jika kita mengakses bean tanpa menyebutkan nama bean nya, secara otomatis primary nya yang akan dipilih
Untuk memilih primary bean, kita bisa tambahkan annotaiton @Primary
```java
@Configuration
public class PrimaryConfiguration {
    @Primary
    @Bean
    public Foo foo1(){
        return new Foo();
    }  
    @Bean
    public Foo foo2(){
        return new Foo();
    }
}
```
```java
public class PrimaryTest {    
    private ApplicationContext applicationContext;    
    @BeforeEach
    void setup(){
        applicationContext = new AnnotationConfigApplicationContext(PrimaryConfiguration.class);
    }
    @Test
    void testGetPrimary(){
        Foo foo = applicationContext.getBean(Foo.class);
        Foo foo1 = applicationContext.getBean("foo1",Foo.class);
        Foo foo2 = applicationContext.getBean("foo2",Foo.class);      
        Assertions.assertSame(foo, foo1);
        Assertions.assertNotSame(foo, foo2);
        Assertions.assertNotSame(foo1, foo2);
    }
}
```
# Mengubah Nama Bean
Jika kita ingin mengubah nama bean, kita bisa menggunakan method value() milik annotation @Bean
```java
@Configuration
public class BeanNameConfiguration {
    @Primary
    @Bean(name = "fooFirst")
    public Foo foo1(){
        return new Foo();
    }   
    @Bean(name = "fooSecond")
    public Foo foo2(){
        return new Foo();
    }
}
```
```java
public class BeanNameTest {   
    private ApplicationContext applicationContext;   
    @BeforeEach
    void setup(){
        applicationContext= new AnnotationConfigApplicationContext(BeanNameConfiguration.class);
    }   
    @Test
    void testBeanName(){
        Foo foo = applicationContext.getBean(Foo.class);
        Foo fooFirst = applicationContext.getBean("fooFirst",Foo.class);
        Foo fooSecond = applicationContext.getBean("fooSecond",Foo.class);
        
        Assertions.assertSame(foo, fooFirst);
        Assertions.assertNotSame(foo, fooSecond);
    }
}
```









