package ellie.study.utils;

public class ElliePrinter {

    public static void print(String message) {
        System.out.println(Thread.currentThread().getName() + " :: " + message);
    }

    public static void print(String title, String value) {
        System.out.println(title + ": " + value);
    }

    public static void print(String title, boolean value) {
        System.out.println(title + ": " + value);
    }
}
