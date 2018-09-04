package demo;

/**
 * Created by beka on 9/4/18.
 */
public class AAAA {
    public static void main(String[] args) {
        String a = "артерия;аномалия;артерии";
        String[] split = a.split(";");
        System.out.println(split[1] +"+"+ split[2] +";"+ split[0]);
    }
}
