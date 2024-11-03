import controller.Controller;
import exceptions.*;
import repo.IRepository;
import controller.IController;
import repo.Repository;
import view.IView;
import view.View;



public class Main {



    public static void main(String[] args) throws EmptyStackException, StatementException, ControllerException, KeyNotFoundException, ExpressionException {

        IRepository repo = new Repository();
        IController controller = new Controller(repo);
        IView view = new View(controller);
        view.start();

    }
}