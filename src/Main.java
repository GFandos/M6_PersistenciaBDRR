import java.util.Scanner;

/**
 * Created by Gerard on 11/12/2016.
 */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Menu menu = new Menu(sc);

        while(true) {
            menu.init();
        }

    }

}
