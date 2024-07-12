package ellie.study.utils;

import org.springframework.util.StopWatch;

public class EllieTimer {

    public static StopWatch start() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        return stopWatch;
    }

    public static double stop(StopWatch stopWatch) {
        stopWatch.stop();
        return stopWatch.getTotalTimeSeconds();
    }
}
