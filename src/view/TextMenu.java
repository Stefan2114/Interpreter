package view;

import view.commands.Command;

import java.util.TreeMap;
import java.util.Scanner;

import java.util.Map;

public class TextMenu {

    private Map<Integer, Command> commands;

    public TextMenu() {
        this.commands = new TreeMap<>();

    }

    public void addCommand(Command command) {
        this.commands.put(command.getKey(), command);
    }

    private void printMenu() {
        for (Command command : this.commands.values()) {
            String line = String.format("%4s : {\n%s\n}\n", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Input your option: ");
            String input = scanner.nextLine();
            try{
                int key = Integer.parseInt(input);
                if (!this.commands.containsKey(key))
                    System.out.println("Invalid option");
                else {
                    Command command = this.commands.get(key);
                    command.execute();
                }
            }catch(NumberFormatException e){
                System.out.println("Invalid option: must be a number");
            }

        }
    }

}
