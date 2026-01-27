package tyrkanych_marriageagency.ui.forms;

import java.util.Scanner;

public class ComplaintForm {

    private final Scanner sc = new Scanner(System.in);

    public String show(String aboutUserEmail) {
        System.out.println("\n--- Подача скарги на " + aboutUserEmail + " ---");
        System.out.print("Причина: ");
        return sc.nextLine();
    }
}
