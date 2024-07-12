package ellie.study.stream;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static ellie.study.utils.ElliePrinter.print;
import static ellie.study.utils.EllieTimer.start;
import static ellie.study.utils.EllieTimer.stop;

public class ParallelStreamTest {

    @Test
    void 병렬처리시_순서가_보장되지_않는다() {
        IntStream.rangeClosed(1, 5)
                .parallel()
                .forEach(i -> print(String.valueOf(i)));
    }

    @Test
    void 큰_데이터를_다루는_경우_병렬처리로_성능을_개선할_수_있다() {
        long dataSize = 1_000_000_000;

        StopWatch stopWatch = start();
        long sum1 = LongStream.rangeClosed(1, dataSize)
                .map(i -> i / 5)
                .sum();
        System.out.printf("sequential sum: %d, elapsed sec: %f%n", sum1, stop(stopWatch));

        /*
            병렬스트림은 내부적으로 ForkJoinPool을 이용하며, 분할정복 방식을 활용한다.
            합을 구하는 경우에도 1 + 2, 3 + 4 를 동시에 수행하고 3 + 7을 수행하는 방식으로 수행 시간을 개선한다.
        */
        StopWatch stopWatch_ = start();
        long sum2 = LongStream.rangeClosed(1, dataSize)
                .parallel()
                .map(i -> i / 5)
                .sum();
        System.out.printf("parallel sum: %d, elapsed sec: %f%n", sum2, stop(stopWatch_));
    }

    @Test
    void 각각의_수행시간이_긴_테스크를_실행하는_경우_병렬처리로_성능을_개선할_수_있다() {
        StopWatch stopWatch = start();
        List<Integer> integers1 = IntStream.rangeClosed(1, 5)
                .map(i -> {
                    try {
                        Thread.sleep(500);
                        return i;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .boxed()
                .toList();
        System.out.printf("sequential elapsed sec: %f%n", stop(stopWatch));

        StopWatch stopWatch_ = start();
        List<Integer> integers2 = IntStream.rangeClosed(1, 5)
                .parallel()
                .map(i -> {
                    try {
                        Thread.sleep(500);
                        return i;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .boxed()
                .toList();
        System.out.printf("parallel elapsed sec: %f%n", stop(stopWatch_));
    }

    @Test
    void 종단연산에서_순서를_보장하게_할_수_있다() {
        IntStream.rangeClosed(1, 5)
                .parallel()
                // 병렬 수행
                .map(i -> {
                    try {
                        print("start map " + i);
                        Thread.sleep(500);
                        print("end map " + i);
                        return i;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                /*
                    요소의 순서를 기억하고 있다가,
                    순서가 빠른 요소가 완료되는데로 바로바로 수행한다.
                 */
                .forEachOrdered(i -> print("forEachOrdered " + i));
    }
}
