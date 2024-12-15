package view.commands;

import controller.IController;
import exceptions.ControllerException;
import exceptions.TypeCheckException;


public class RunExempleCommand extends Command {


    private IController controller;

    public RunExempleCommand(int key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }


    @Override
    public void execute() {
        try {
            this.controller.allSteps();
        } catch (ControllerException | InterruptedException | TypeCheckException e) {
            System.out.println(e);
        }
    }
}
