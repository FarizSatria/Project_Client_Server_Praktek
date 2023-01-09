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
# Dependency Injection
Dependency Injection (DI) adalah teknik dimana kita bisa mengotomatisasi proses pembuatan object yang tergantung dengan object lain, atau kita sebut dependencies
Dependencies akan secara otomatis di-inject (dimasukkan) kedalam object yang membutuhkannya
```java
@AllArgsConstructor
@Data
public class FooBar {
    
    private Foo foo;
    
    private Bar bar;
}
```
# Memilih Depedency
Saat terdapat duplicate bean dengan tipe data yang sama, secara otomatis Spring akan memilih bean yang primary
Namun kita juga bisa memilih secara manual jika memang kita inginkan
Kita bisa menggunakan annotation @Qualifier(value=”namaBean”) pada parameter di method nya
```java 
@Bean
public FooBar fooBar(@Qualifier("fooSecond") Foo foo, Bar bar){
    return new FooBar(foo, bar);
}
```
```java
Foo foo = applicationContext.getBean("fooSecond", Foo.class);
Bar bar = applicationContext.getBean(Bar.class);
FooBar fooBar = applicationContext.getBean(FooBar.class);
        
Assertions.assertSame(foo, fooBar.getFoo());
Assertions.assertSame(bar, fooBar.getBar());
```
# Circular Dependencies
Circular dependencies adalah kasus dimana sebuah lingkaran dependency terjadi, misal bean A membutuhkan bean B, bean B membutuhkan bean C, dan ternyata bean C membutuhkan A
Jika terjadi cyclic seperti ini, secara otomatis Spring bisa mendeteksinya, dan akan mengganggap bahwa itu adalah error
```java
@Configuration
public class CyclicConfiguration {
    
    @Bean
    public CyclicA cyclicA(CyclicB cyclicB){
        return new CyclicA(cyclicB);
    }
    
    @Bean
    public CyclicB cyclicB(CyclicC cyclicC){
        return new CyclicB(cyclicC);
    }
    
    @Bean
    public CyclicC cyclicC(CyclicA cyclicA){
        return new CyclicC(cyclicA);
    }
}
```
# Depends On
Saat sebuah bean membutuhkan bean lain, secara otomatis bean tersebut akan dibuat setelah bean yang dibutuhkan dibuat
Namun bagaimana jika bean tersebut tidak membutuhkan bean lain, namun kita ingin sebuah bean dibuat setelah bean lain dibuat?
Jika ada kasus seperti itu, kita bisa menggunakan annotation @DependsOn(value={”namaBean”})
Secara otomatis, Spring akan memprioritaskan pembuatan bean yang terdapat di DependsOn terlebih dahulu
```java
@Slf4j
@Configuration
public class DependsOnConfiguration {
    
    @Bean
    public Foo foo(){
        log.info("Create new Foo");
        return new Foo();
    }
    
    @Bean
    public Bar bar(){
        log.info("Create new Bar");
        return new Bar();
    }   
}
```

# Lazy Bean
Secara default, bean di Spring akan dibuat ketika aplikasi Spring pertama kali berjalan
Oleh karena itu, kadang ketika aplikasi Spring pertama berjalan akan sedikit lambat, hal ini dikarenakan semua bean akan dibuat di awal
Namun jika kita mau, kita juga bisa membuat sebuah bean menjadi lazy (malas), dimana bean tidak akan dibuat, sampai memang diakses atau dibutuhkan
Untuk membuat sebuah bean menjadi lazy, kita bisa tambahkan annotation @Lazy pada bean tersebut
```java
@Slf4j
@Configuration
public class DependsOnConfiguration {
    
    @Lazy
    @Bean
    @DependsOn({
        "bar"
    })
    public Foo foo(){
        log.info("Create new Foo");
        return new Foo();
    }
    
    @Bean
    public Bar bar(){
        log.info("Create new Bar");
        return new Bar();
    }
    
}
```
# Scope
Secara default strategy object di Spring adalah singleton, artinya hanya dibuat sekali, dan ketika kita akses, akan mengembalikan object yang sama
Namun kita juga bisa mengubah scope bean yang kita mau di Spring
Untuk mengubah scope sebuah bean, kita bisa tambahkan annotation @Scope(value=”namaScope”)
```java
@Slf4j
@Configuration
public class ScopeConfiguration {
    
    @Bean
    @Scope("prototype")
    public Foo foo(){
        return new Foo();
    }
}
```
# Membuat Scope
Jika scope yang disediakan oleh Spring tidak bisa memenuhi kebutuhan kita, kita juga bisa membuat scope sendiri
Caranya dengan membuat class yang implement interface Scope
Selanjutnya untuk meregistrasikannya, kita bisa membuat bean CustomScopeConfigurer
```java
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        counter ++;
        
        if(objects.size()==2){
            int index = (int)(counter % 2);
            return objects.get(index);
        } else {
            Object object = objectFactory.getObject();
            objects.add(object);
            return object;
        }
        
    }
    @Override
    public Object remove(String name) {
        if(!objects.isEmpty()){
            return objects.remove(0);
        }
        return null;
    }
```
## Kode : Register Doubleton Scope








