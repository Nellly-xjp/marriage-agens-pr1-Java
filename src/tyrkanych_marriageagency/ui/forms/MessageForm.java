package tyrkanych_marriageagency.ui.forms;

import java.util.Scanner;

public class MessageForm {

    private final Scanner sc = new Scanner(System.in);

    public String show(String toUserEmail) {
        System.out.println("\n--- Напишіть повідомлення для " + toUserEmail + " ---");
        System.out.print("Ваше повідомлення: ");
        return sc.nextLine();
    }
}
