package tyrkanych_marriageagency.ui.forms;

import java.util.Scanner;

public class MessageForm {

    private final Scanner sc = new Scanner(System.in);

    public String show(String firstName, String lastName) {
        System.out.println(
              "\n--- Напишіть повідомлення для " +
                    firstName + " " + lastName + " ---"
        );
        System.out.print("Ваше повідомлення: ");
        return sc.nextLine();
    }

}
