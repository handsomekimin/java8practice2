
import java.sql.Array;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        System.out.println("FUNCTIONAL INTERFACE =================");
        //자바 기본제공 함수형 인터페이스
        Function<Integer, Integer> add10 = (i) -> i + 10;
        System.out.println(add10.apply(10));
        Function<Integer, Integer> mul2 = (i) -> i * 2;

        //Unary (같은 형태 받고 출력
        UnaryOperator<Integer> add11 = (i) -> i+11;
        //Binary BiFunction 의 특수한 형태 세 개 타입 모두 같은 경우우
        BinaryOperator<Integer> addij = (i,j) -> i+j;
        System.out.println(addij.apply(1,2));

        //supplier
        Supplier<Integer> get10 = () -> 10;

        //Consumer
        Consumer<Integer> put10 = (i) -> System.out.println(i);

        //Static method reference
        Consumer<Integer> HelloDlrow = App::prnt;
        HelloDlrow.accept(10);

        //Object Instance method reference
        //Constructor can be used
        Supplier<chicken> newChicken = chicken::new;
        chicken myChicken = newChicken.get();//생성
        System.out.println(myChicken.getS());


        //Method reference method (Non static method)
        String[] names = {"kimin", "whiteship", "Iaan","Bart", "Jade", "COCO", "coco"};
        Arrays.sort(names, String::compareToIgnoreCase);// 대소문자 무시
        System.out.println(Arrays.toString(names));
        Arrays.sort(names, String::compareTo);
        System.out.println(Arrays.toString(names));//대소문자 구분

        intpractice.ssomething();

/*
        intpractice intp1 = new intpractice() {
            @Override
            public void something() {
                System.out.println("Hello");
            }
        };
        intp1.something();*/

        intpractice intp1 = () -> System.out.println("Hello");
        intp1.something();
        //compose -> 계산한 것을 앞에꺼랑 계산해줌
        Function<Integer,Integer> mul2andadd10  = add10.compose(mul2);
        System.out.println(mul2andadd10.apply(1));

        //andThen -> 계산한 것을 뒤에꺼랑 계산해줌
        Function<Integer, Integer> add10andmul2 = add10.andThen(mul2);
        System.out.println(add10andmul2.apply(1));

        System.out.println("API FUNDAMENTAL METHODS AND STATIC METHODS =================");
        //iterable
        //forEach
        System.out.println("FOREACH====================");
        /*

        Arrays.stream(names).toLIst() 를 해버릴 경우 immutable list 를 반환해버린다. 아마
        파이프라인에서 원본을 수정할 이유가 없어서 그런 것으로 추정됨.

         */
        List<String> name = Arrays.stream(names).collect(Collectors.toList());
        name.forEach(System.out::println);

        //Spliterator
        System.out.println("SPLITERATOR====================");
        Spliterator<String> spliterator = name.spliterator();
        while(spliterator.tryAdvance(System.out::println));

        //DEVIDE INTO HALF
        spliterator = name.spliterator();
        Spliterator<String> spliterator1 = spliterator.trySplit();
        while(spliterator.tryAdvance(System.out::println));
        System.out.println("END");
        while(spliterator1.tryAdvance(System.out::println));


        ArrayList<Integer> newArray = new ArrayList<>();
        newArray.add(1);
        newArray.add(2);
        newArray.add(3);
        newArray.add(4);
        for(Integer i : newArray){
            System.out.println(i);
        }
        newArray.forEach(System.out::println);

        //COLLECTION
        //stream
        System.out.println("STREAM=======================");
        System.out.println(name.stream().map(String::toUpperCase).filter(s-> s.startsWith("K")).toList().toString());
        System.out.println(name.toString());
        //remove if
        name.removeIf(s-> s.startsWith("C"));
        System.out.println(name.toString());
        name.add("COCO");

        //COMPARATOR
        System.out.println("COMPARATOR======================");
        name.sort(Comparator.reverseOrder());
        System.out.println(name.toString());
        //reversed, thenComparing
        Comparator<String > compareToIgnoreCase = String::compareToIgnoreCase;
        name.sort(compareToIgnoreCase.reversed().thenComparing(String::compareTo));
        System.out.println(name.toString());

        //naturalOrder
        name.sort(Comparator.naturalOrder());
        System.out.println(name.toString());
        //reverseOrder
        name.sort(Comparator.reverseOrder());
        System.out.println(name.toString());

        //STREAM API
        System.out.println("STREAM API =================================");

        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> keesunEvents = new ArrayList<>();
        keesunEvents.add(springClasses);
        keesunEvents.add(javaClasses);

        System.out.println("spring 으로 시작하는 수업");
        // TODO
        keesunEvents.stream().flatMap(Collection::stream).filter(oc -> oc.getTitle().startsWith("spring")).forEach(oc -> System.out.println(oc.getTitle()));

        System.out.println("close 되지 않은 수업");
        // TODO
        keesunEvents.stream().flatMap(l -> l.stream()).filter(oc -> !oc.isClosed()).forEach(oc-> System.out.println(oc.getTitle()));

        System.out.println("수업 이름만 모아서 스트림 만들기");
        // TODO
        keesunEvents.stream().flatMap(l -> l.stream()).map(oc -> oc.getTitle()).forEach(System.out::println);

        System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
        // TODO
        keesunEvents.stream().flatMap(l -> l.stream()).map(oc -> oc.getId()).forEach(System.out::println);

        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        // TODO
        Stream.iterate(10, i-> i+1)
                .skip(10)
                .limit(10)
                .forEach(System.out::println);
        System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        // TODO
        System.out.println(javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("Test")));

        System.out.println("스프링 수업 중에 제목에 spring이 들어간 것만 모아서 List로 만들기");
        // TODO
        List<String> spring = springClasses.stream().filter(oc -> oc.getTitle().contains("spring")).map(OnlineClass::getTitle).collect(Collectors.toList());
        spring.forEach(System.out::println);


        //OPTIONAL API
        System.out.println("OPTIONAL API ============================");
        //get 은 지양하는게 좋다. Optional 이 비어있을 경우 exception
        for (int i =0; i<=10; ++i ){
            //System.out.println(optest("handsome" + i).get());
        }
        //orElse() 는 값이 있으면 가져오고 없으면 뒤의 값 리턴
        for (int i =0; i<=10; ++i ){
            System.out.println(optest("handsome" + i).orElse("NOT HANDSOME"));
        }
        //orElseGet(Supplier) 는 값이 있으면 가져오고 없으면 뒤의 함수 리턴
        for (int i =0; i<=10; ++i ){
            System.out.println(optest("handsome" + i).orElseGet(() -> "nothandsome"));
        }
        //ifPresent 없으면 건너뜀
        System.out.println("IFPRESENT ");
        for (int i =0; i<=10; ++i ){
            optest("handsome" + i).ifPresent(System.out::println);
        }


        //DATE/TIME API
        System.out.println("DATE/TIME API ============================");
        //INSTANT 기계용 API
        Instant instant = Instant.now(); // 기준시 GMT UTC
        System.out.println(instant);
        instant.atZone(ZoneId.systemDefault()); // 내 시스템 타임존 기준
        System.out.println(instant.atZone(ZoneId.of("Asia/Seoul")));
        System.out.println(instant);

        //LocalDateTime 인간용
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime birthDay= LocalDateTime.of(1995,3,4,0,0,0);
        //ZonedDateTime
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println(nowInKorea);
        System.out.println(nowInKorea.toInstant());
        //LocalDate 일까지
        //Period 인간 LocalDate와 연계
        LocalDate today = LocalDate.now();
        LocalDate thisYearBirthday = LocalDate.of(2022, Month.MARCH, 4);
        Period period = Period.between(today, thisYearBirthday);
        System.out.println(period.getDays());
        Period until = today.until(thisYearBirthday);
        System.out.println(until.get(ChronoUnit.DAYS));
        //Duration 기계 Instant 와 연계
        Instant now = Instant.now();
        Instant plus = now.plus(10, ChronoUnit.SECONDS);
        Duration between = Duration.between(now, plus);
        System.out.println(between.getSeconds());
        //DateTimeFormatter + LocalDateTime
        System.out.println(DateTimeFormatter.ISO_DATE);
        LocalDateTime ldt1 = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println(ldt1.format(dateTimeFormatter));


        //=============================문제

    }


    public static void prnt(int i){
        System.out.println("Hello World" + i);
    }

    public static Optional<String> optest(String s){
        if (s.contains("3")){
            return Optional.ofNullable(null);// or Optional.empty()
        }
        return Optional.ofNullable(s+"Kimin tail");
    }

}


