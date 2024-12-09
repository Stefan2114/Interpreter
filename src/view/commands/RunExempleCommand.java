package view.commands;

import controller.IController;
import exceptions.ControllerRuntimeException;
import model.statements.ReadFileStatement;

public class RunExempleCommand extends Command {


    private IController controller;

    public RunExempleCommand(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }


    @Override
    public void execute() {
        try {
            this.controller.allSteps();
        } catch (ControllerRuntimeException | InterruptedException e) {
            System.out.println(e);
        }
    }
}
