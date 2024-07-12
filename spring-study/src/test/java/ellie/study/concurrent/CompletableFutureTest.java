package ellie.study.concurrent;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class CompletableFutureTest {

    @Test
    void 비동기_수행() {
        Thread mainThread = Thread.currentThread();
        System.out.printf("%s test start%n", mainThread.getName());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        /*
            모든 테스크를 시작시키고 바로 리턴된 CompletableFuture를 모은다.
            => 비동기 수행
         */
        List<CompletableFuture<Integer>> futures = IntStream.rangeClosed(1, 5)
                .boxed()
                .map(i -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread thread = Thread.currentThread();
                        System.out.printf("%s start %d%n", thread.getName(), i);
                        Thread.sleep(2000);
                        System.out.printf("%s end %d%n", thread.getName(), i);
                        return i;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .toList();

        /*
            모든 CompletableFuture가 끝날때까지 기다리게 한 후, 결과를 모은다.
         */
        List<Integer> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        stopWatch.stop();

        System.out.printf("%s test end %s%n", mainThread.getName(), results);

        System.out.printf("%s elapsed time %f%n", mainThread.getName(), stopWatch.getTotalTimeSeconds());
    }

    @Test
    void 잘못된사용_동기적으로_수행됨() {
        Thread mainThread = Thread.currentThread();
        System.out.printf("%s test start%n", mainThread.getName());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        /*
            첫번째 테스크를 시작하고 종료될때까지 기다린 후, 다음 테스트를 시작한다.
            => 동기 수행

            스트림은 각 요소에 대해 모든 중간 연산자를 수행 한 후 다음 요소에 대한 처리를 하기 때문이다.
         */
        List<Integer> results = IntStream.rangeClosed(1, 5)
                .boxed()
                .map(i -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread thread = Thread.currentThread();
                        System.out.printf("%s start %d%n", thread.getName(), i);
                        Thread.sleep(2000);
                        System.out.printf("%s end %d%n", thread.getName(), i);
                        return i;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .map(CompletableFuture::join)
                .toList();

        stopWatch.stop();

        System.out.printf("%s test end %s%n", mainThread.getName(), results);

        System.out.printf("%s elapsed time %f%n", mainThread.getName(), stopWatch.getTotalTimeSeconds());
    }
}
