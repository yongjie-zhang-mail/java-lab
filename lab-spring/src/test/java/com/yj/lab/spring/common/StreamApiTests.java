package com.yj.lab.spring.common;

import com.yj.lab.spring.model.enums.DemoEnum;
import com.yj.lab.spring.model.vo.response.DemoVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhangyj21
 * Stream API & Lambda expression & Functional Interface
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class StreamApiTests {

    private static final List<DemoVo> demoVoList = Arrays.asList(
            new DemoVo("001", "name001", 18, 1, 700),
            new DemoVo("002", "name002", 19, 1, 800),
            new DemoVo("003", "name003", 20, 2, 900),
            new DemoVo("004", "name004", 20, 1, 500),
            new DemoVo("005", "name005", 21, 2, 400),
            new DemoVo("006", "name006", 17, 1, 300),
            new DemoVo("007", "name007", 16, 1, 400),
            new DemoVo("008", "name008", 19, 2, 600),
            new DemoVo("009", "name009", 18, 1, 800),
            new DemoVo("010", "name010", 22, 1, 900)
    );

    private static final DemoVo demoVo = new DemoVo("100", "name100", 18, 1, 1000);

    public static void main(String[] args) {
//        test3();
        test4();
    }

    private static void test4() {

        // filter sorted map skip limit distinct collect
        List<String> ids = demoVoList.stream()
                .filter(StreamApiTests::filter)
//                .sorted(Comparator.comparing(DemoVo::getName))
                .sorted(StreamApiTests::compare)
                .map(DemoVo::getId)
                .skip(1)
                .limit(3)
                .distinct()
                .collect(Collectors.toList());

        log.info(ids.toString());

        // reduce
        // Optional<T>
        Optional<Integer> totalSalary = demoVoList.stream().map(DemoVo::getSalary).reduce(Integer::sum);
        totalSalary.ifPresent(System.out::println);

        // group
        Map<Integer, List<DemoVo>> ageGroup = demoVoList.stream().collect(Collectors.groupingBy(DemoVo::getAge));

        // Optional<T>
        Optional.ofNullable(demoVo).ifPresentOrElse(System.out::println, StreamApiTests::logFixed);

        // allMatch
        List<DemoVo> maleList = demoVoList.stream().filter(StreamApiTests::filter).collect(Collectors.toList());
        boolean allMatch = maleList.stream().allMatch(StreamApiTests::testMale);
        System.out.println(allMatch);

    }

    /**
     * @param demoVo1 o1
     * @param demoVo2 o2
     * @return judge value
     * @FunctionalInterface Comparator
     */
    private static int compare(DemoVo demoVo1, DemoVo demoVo2) {
        int result = 0;
        if (demoVo1.getAge() > demoVo2.getAge()) {
            result = 1;
        } else {
            result = -1;
        }
        return result;
    }

    /**
     * @param demoVo Object
     * @return judge condition
     * @FunctionalInterface Predicate
     */
    private static boolean testMale(DemoVo demoVo) {
        return Objects.equals(demoVo.getGender(), DemoEnum.MALE.getCode());
    }

    /**
     * @FunctionalInterface Runnable
     */
    private static void logFixed() {
        log.info("fixed log");
    }

    /**
     * @param d Object
     * @return judge condition
     * @FunctionalInterface Predicate
     */
    private static Boolean filter(DemoVo d) {
        return d.getAge() < 20 && Objects.equals(DemoEnum.MALE.getCode(), d.getGender());
    }

    private static void test3() {

        Random r = new Random();
        System.out.println("limit & sort: ");
        r.ints().limit(3).sorted().forEach(System.out::println);
        System.out.println("sort & skip & limit: ");
        // foreach sort skip limit
        r.ints(10).sorted().skip(2).limit(5).forEach(System.out::println);

    }

    private static void test2() {
//        IntStream.range(1, 10).forEach(System.out::println);
        // peek
        IntStream.range(1, 10)
                .peek(x -> System.out.println("\nA" + x))
                .forEach(x -> System.out.println("C" + x));
    }

    private static void test1() {
        // parallel toSet
        Set<Integer> ageSet = demoVoList.stream().parallel().map(DemoVo::getAge).collect(Collectors.toSet());
    }
}



















