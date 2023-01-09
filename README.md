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
<br><br>

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
<br><br>

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
<br><br>

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
<br><br>
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
<br><br>
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
<br><br>
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
<br><br>
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
<br><br>
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
<br><br>
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
<br><br>
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
<br><br>
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
# Kode : Register Doubleton Scope
```java
@Bean
    public CustomScopeConfigurer customScopeConfigurer(){
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("doubleton", new DoubletonScope());
        return configurer;
    }
    
    @Bean
    @Scope("doubleton")
    public Bar bar(){
        log.info("Create new Bar");
        return new Bar();
    }
```
# Kode : Mengakses Doubleton Bean
```java
    Bar bar1= applicationContext.getBean(Bar.class);
    Bar bar2= applicationContext.getBean(Bar.class);
    Bar bar3= applicationContext.getBean(Bar.class);
    Bar bar4= applicationContext.getBean(Bar.class);
        
     Assertions.assertSame(bar1, bar3);
     Assertions.assertSame(bar2, bar4);         
     Assertions.assertNotSame(bar1, bar2);
     Assertions.assertNotSame(bar3, bar4);
```
<br><br>
# Life Cycle Callback
Secara default, bean tidak bisa tahu alur hidup Spring ketika selesai membuat bean dan ketika akan menghancurkan bean
Jika kita tertarik untuk bereaksi ketika alur hidup Spring terjadi, maka kita bisa implements interface InitializingBean dan DisposableBean
InitializingBean digunakan jika kita ingin bereaksi ketika Spring selesai membuat bean
```java
@Slf4j
public class Connection implements InitializingBean, DisposableBean{

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Connection is ready to be used");
    }

    @Override
    public void destroy() throws Exception {
        log.info("Connection is closed");
    } 
}
```
# Kode : LifeCycle Configuration
```java 
@Configuration
public class LifeCycleConfiguration {
    
    @Bean
    public Connection connection(){
        return new Connection();
    }
}
```
<br><br>
# Life Cycle Annotation
Selain menggunakan interface InitializingBean dan DisposableBean, kita juga bisa menggunakan annotation untuk mendaftarkan callback method untuk lifecycle
Pada annotation @Bean, terdapat method initMethod() dan destoyMethod()
initMethod() digunakan untuk meregistrasikan method yang akan dipanggil ketika bean selesai dibuat
destroyMethod() digunakan untuk meregistrasikan method yang akan dipanggil ketika bean akan dihancurkan
```java
@Slf4j
public class Server {

    public void start(){
        log.info("Start Server");
    }

    public void stop(){
        log.info("Stop Server");
    }
}
```
```java
@Bean (initMethod = "start", destroyMethod = "stop")
    public Server server(){
        return new Server();
    }
```
<br><br>
# @PostConstruct dan @PreDestroy
@PostConstruct merupakan method yang ditandai harus dipanggil ketika bean selesai dibuat
@PreDestroy merupakan method yang ditandai harus dipanggil ketika bean akan dihancurkan
```java
@Slf4j
public class Server {
    
    @PostConstruct
    public void start(){
        log.info("Start Server");
    }
    
    @PreDestroy
    public void stop(){
        log.info("Stop Server");
    }
}
```
<br><br>
# Import
Biasanya kita akan membuat banyak sekali, tergantung seberapa kompleks aplikasi kita
Spring mendukung import Configuration Class lain jika dibutuhkan
Kita bisa menggunakan annotation @Import, lalu sebutkan Configuration Class mana yang ingin kita import
Ketika kita melakukan import, kita bisa import lebih dari satu class
```java
@Configuration
public class FooConfiguration {
    
    @Bean
    @Primary
    public Foo foo(){
        return new Foo();
    }
}
```
```java
@Configuration
public class BarConfiguration {
    
    @Bean
    public Bar bar(){
        return new Bar();
    }
}
```
```java
@Configuration
@Import({
    FooConfiguration.class,
    BarConfiguration.class
})
public class MainConfiguration {
    
}
```
<br><br>
# Component Scan
Spring memiliki fitur component scan, dimana kita bisa secara otomatis mengimport Configuration di sebuah package dan sub package nya secara otomatis
Untuk melakukan itu, kita bisa gunakan annotation @ComponentScan
```java
@Configuration
@ComponentScan(basePackages = {
    "com.fariz.farizbelajarspringdasar.configuration"
})
public class ScanConfiguration {
    
}
```
<br><br>
# Multiple Constructor
Seperti di awal disebutkan bahwa Spring hanya mendukung satu constructor untuk Dependency Injection nya
Namun bagaimana jika terdapat multiple constructor?
Jika pada kasus seperti ini, kita harus menandai constructor mana yang akan digunakan oleh Spring
Caranya kita bisa menggunakan annotation @Autowired
```java
@Component
public class ProductService {
    
    @Getter
    private ProductRepository productRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    
    public ProductService(ProductRepository productRepository, String name){
        this.productRepository = productRepository;
    }
}
```
<br><br>
# Setter-based Dependency Injection
Selain menggunakan constructor parameter, kita juga bisa menggunakan setter method jika ingin melakukan dependency injection
Namun khusus untuk setter method, kita perlu menambah annotation @Autowired pada setter method nya
Secara otomatis Spring akan mencari bean yang dibutuhkan di setter method yang memiliki annotation @Autowired
Setter-based DI juga bisa digabung dengan Constructor-based DI
```java 
@Component
public class CategoryService {
    
    @Getter
    private CategoryRepository categoryRepository;
    
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
}
```
<br><br>
# Field-based Dependency Injection
Selain constructor dan setter, kita juga bisa melakukan dependency injection langsung menggunakan field
Caranya sama dengan setter, kita bisa tambahkan @Autowired pada fieldnya
Secara otomatis Spring akan mencari bean dengan tipe data tersebut
Field-based DI bisa digabung sekaligus dengan Setter-based DI dan Constructor-based DI
Khusus Field-based DI, Spring sendiri sudah tidak merekomendasikan penggunaan cara melakukan DI dengan Field
```java
@Component
public class CustomerService {
    
    @Getter
    @Autowired
    private CustomerRepository CustomerRepository;
}
```
<br><br>
# Qualifier
Seperti yang sudah dijelaskan di awal, jika terdapat bean dengan tipe data yang sama lebih dari satu, maka secara otomatis Spring akan bingung memilih bean yang mana yang akan digunakan
Kita perlu memilih salah satu menjadi primary, yang secara otomatis akan dipilih oleh Spring
Namun jika kita ingin memilih bean secara manual, kita juga bisa menggunakan @Qualifier
Kita bisa tambahkan @Qualifier di constructor parameter, di setter method atau di field
```java
@Component
public class CustomerService {
    
    @Getter
    @Autowired
    @Qualifier("normalCustomerRepository")
    private CustomerRepository normalCustomerRepository;
    
    @Getter
    @Autowired
    @Qualifier("premiumCustomerRepository")
    private CustomerRepository premiumCustomerRepository;
}
```
<br><br>
# Optional Dependency
Secara default, semua dependency itu wajib
Artinya  jika Spring tidak bisa menemukan bean yang dibutuhkan pada saat DI, maka secara otomatis akan terjadi error
Namun jika kita memang ingin membuat sebuah dependency menjadi Optional, artinya tidak wajib
```java
@Configuration
public class OptionalConfiguration {
    
    @Bean
    public Foo foo(){
        return new Foo();       
    }
    
    @Bean
    public FooBar fooBar(Optional<Foo> foo, Optional<Bar> bar){
        return new FooBar(foo.orElse(null), bar.orElse(null));
    }
}
```
<br><br>
# Factory Bean
Kadang ada kasus dimana sebuah class misal bukanlah milik kita, misal class third party library
Sehingga agak sulit jika kita harus menambahkan annotation pada class tersebut
Pada kasus seperti ini, cara terbaik untuk membuat bean nya adalah dengan menggunakan @Bean method
Atau di Spring, kita juga bisa menggunakan @Component, namun kita perlu wrap dalam class Factory Bean

```java
@Data
public class PaymentGatewayClient {
    
    private String endpoint;
    
    private String publicKey;
    
    private String privateKey;
}
```
<br><br>
Kode : Factory Bean
```java
@Component("paymentGatewayClient")
public class PaymentGatewayClientFactoryBean implements FactoryBean<PaymentGatewayClient>{

    @Override
    public PaymentGatewayClient getObject() throws Exception {
        PaymentGatewayClient client = new PaymentGatewayClient();
        client.setEndpoint("https://example.com");
        client.setPrivateKey("private");
        client.setPublicKey("public");
        return client;
    }
}
```
<br><br>
Kode : Configuration
```java
@Configuration
@Import({
    PaymentGatewayClientFactoryBean.class
})
public class FactoryConfiguration {
    
}
```
<br><br>
Kode : Mengakses Bean
```java
PaymentGatewayClient client = applicationContext.getBean(PaymentGatewayClient.class);
        
Assertions.assertEquals("https://example.com", client.getEndpoint());
Assertions.assertEquals("private", client.getPrivateKey());
Assertions.assertEquals("public", client.getPublicKey());
```
# Inheritance
Saat kita mengakses bean, kita bisa langsung menyebutkan tipe class bean tersebut, atau bisa juga dengan parent class / parent interface bean
Misal jika kita memiliki sebuah interface bernama MerchantService, lalu kita memiliki bean dengan object implementasi class nya MerchantServiceImpl, maka untuk mengakses bean nya, kita tidak hanya bisa menggunakan tipe MerchantServiceImpl, namun juga bisa dengan MerchantService
Namun perlu berhati-hati, jika misal MerchantService memiliki banyak bean turunan, pastikan tidak terjadi error duplicate
```java
public interface MerchantService {
    
}
```
```java
@Component
public class MerchantServiceImpl implements MerchantService{
    
}
```
```java
@Configuration
@Import(MerchantServiceImpl.class)
public class InheritanceConfiguration {
    
}
```

# Bean Factory
ApplicationContext adalah interface turunan dari BeanFactory
BeanFactory merupakan kontrak untuk management bean di Spring
Method-method yang sebelumnya kita gunakan untuk mengambil bean, sebenarnya merupakan method kontrak dari interface BeanFactory

# Listable Bean Factory
Listable Bean Factory adalah turunan dari Bean Factory yang bisa kita gunakan untuk mengakses beberapa bean sekaligus
Dalam beberapa kasus, ini sangat berguna, seperti misal kita ingin mengambil semua bean dengan tipe tertentu

```java 
ObjectProvider<Foo> fooObjectProvider = applicationContext.getBeanProvider(Foo.class);

Map<String, Foo> beans = applicationContext.getBeansOfType(Foo.class);
```
# Bean Post Processor
Bean Post Processor merupakan sebuah interface yang bisa kita gunakan untuk memodifikasi proses pembuatan bean di Application Context
Bean Post Processor mirip seperti middleware, yang diakses sebelum bean di initialized dan setelah bean di initialized
```java
public interface IdAware {
    
    void setId(String Id);
}
```

Kode : Bean Post Processor

```java
@Component
public class IdGeneratorBeanPostProcessor implements BeanPostProcessor {

@Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
        if(bean instanceof IdAware){
            IdAware idAware = (IdAware) bean;
            idAware.setId(UUID.randomUUID().toString());
    }
        return bean;
    }
```

Component
```java
@Component
public class Car implements IdAware{
    
    @Getter
    private String id;

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
```
