package gui.interpreter;

import gui.interpreter.controller.IController;

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

    @Override
    public String toString() {
        return this.description;
    }

}
