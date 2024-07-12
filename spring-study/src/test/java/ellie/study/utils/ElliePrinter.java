package ellie.study.utils;

public class ElliePrinter {

    public static void print(String message) {
        System.out.println(Thread.currentThread().getName() + " :: " + message);
    }
}
