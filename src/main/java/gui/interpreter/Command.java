package gui.interpreter;

import controller.IController;
import exceptions.ControllerException;
import exceptions.TypeCheckException;

public class Command {

    private String description;
    private IController controller;

    public Command(String description, IController controller) {
        this.description = description;
        this.controller = controller;
    }

    public IController getController() {
        return this.controller;
    }

//    public void execute() {
//        try {
//            this.controller.allSteps();
//        } catch (ControllerException | InterruptedException | TypeCheckException e) {
//            System.out.println(e);
//        }
//    }

    @Override
    public String toString() {
        return this.description;
    }



}
