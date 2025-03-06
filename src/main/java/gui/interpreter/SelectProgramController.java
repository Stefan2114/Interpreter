package gui.interpreter;

import gui.interpreter.controller.Controller;
import gui.interpreter.controller.IController;
import gui.interpreter.exceptions.RepoException;
import gui.interpreter.exceptions.TypeCheckException;
import gui.interpreter.model.expressions.*;
import gui.interpreter.model.statements.*;
import gui.interpreter.model.types.BoolType;
import gui.interpreter.model.types.IntType;
import gui.interpreter.model.types.RefType;
import gui.interpreter.model.types.StringType;
import gui.interpreter.model.values.BoolValue;
import gui.interpreter.model.values.IntValue;
import gui.interpreter.model.values.StringValue;
import gui.interpreter.repo.IRepository;
import gui.interpreter.repo.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class SelectProgramController {
    @FXML
    private ListView<Command> listView;

    @FXML
    private void onSelectButtonClick(ActionEvent event) {
        Command selectedItem = this.listView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            System.out.println("No item selected");
        } else {
            try {

                IController prgController = selectedItem.getController();
                prgController.initProgramState();
                Stage stage = (Stage) this.listView.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
                Parent root = loader.load();
                MainWindowController controller = loader.getController();
                controller.setPrgController(prgController);

                Scene scene = new Scene(root);
                stage.setTitle("Interpreter");
                stage.setScene(scene);
                stage.show();

            } catch (IOException | NullPointerException | TypeCheckException | RepoException e) {
                e.printStackTrace();
                // Create and configure the error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An Error Occurred");
                alert.setContentText(e.getMessage()); // Show the exception's message

                // Show the alert window
                alert.showAndWait();
            }

        }
    }

    @FXML
    private void initialize() {

        this.listView.setItems(getList());
    }

    private ObservableList<Command> getList() {
        IStatement st1 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v",
                        new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));

        IRepository repo1 = new Repository(st1, "log1.txt");
        IController controller1 = new Controller(repo1);


        IStatement st2 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()), new CompStatement(new AssignStatement("a",
                        new ArithmeticalExpression(new ValueExpression(new IntValue(2)), new ArithmeticalExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), ArithmeticalOperator.MULTIPLY), ArithmeticalOperator.ADD)
                ), new CompStatement(new AssignStatement("b", new ArithmeticalExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), ArithmeticalOperator.ADD)),
                        new PrintStatement(new VariableExpression("b"))))
                ));


        IRepository repo2 = new Repository(st2, "log2.txt");
        IController controller2 = new Controller(repo2);


        PrintStatement st35 = new PrintStatement(new VariableExpression("v"));
        CompStatement st34 = new CompStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), st35);
        CompStatement st33 = new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))), st34);
        CompStatement st32 = new CompStatement(new VarDeclStatement("v", new IntType()), st33);
        IStatement st3 = new CompStatement(new VarDeclStatement("a", new BoolType()), st32);

        IRepository repo3 = new Repository(st3, "log3.txt");
        IController controller3 = new Controller(repo3);


        CloseRFileStatement st49 = new CloseRFileStatement(new VariableExpression("varf"));
        CompStatement st48 = new CompStatement(new PrintStatement(new VariableExpression("varc")), st49);
        CompStatement st47 = new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), st48);
        CompStatement st46 = new CompStatement(new PrintStatement(new VariableExpression("varc")), st47);
        CompStatement st45 = new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), st46);
        CompStatement st44 = new CompStatement(new VarDeclStatement("varc", new IntType()), st45);
        //CompStatement st43 = new CompStatement(new OpenRFileStatement(new VariableExpression("varf")), st44);
        CompStatement st43 = new CompStatement(new OpenRFileStatement(new ValueExpression(new StringValue("test.in"))), st44);

        CompStatement st42 = new CompStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))), st43);
        IStatement st4 = new CompStatement(new VarDeclStatement("varf", new StringType()), st42);

        IRepository repo4 = new Repository(st4, "log4.txt");
        IController controller4 = new Controller(repo4);


        PrintStatement st56 = new PrintStatement(new VariableExpression("a"));
        CompStatement st55 = new CompStatement(new PrintStatement(new VariableExpression("v")), st56);
        CompStatement st54 = new CompStatement(new HeapAllocStatement("a", new VariableExpression("v")), st55);
        CompStatement st53 = new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))), st54);

        CompStatement st52 = new CompStatement(new HeapAllocStatement("v", new ValueExpression(new IntValue(20))), st53);
        IStatement st5 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), st52);

        IRepository repo5 = new Repository(st5, "log5.txt");
        IController controller5 = new Controller(repo5);


        PrintStatement st66 = new PrintStatement(new ArithmeticalExpression(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)), ArithmeticalOperator.ADD));
        CompStatement st65 = new CompStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), st66);
        CompStatement st64 = new CompStatement(new HeapAllocStatement("a", new VariableExpression("v")), st65);
        CompStatement st63 = new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))), st64);
        CompStatement st62 = new CompStatement(new HeapAllocStatement("v", new ValueExpression(new IntValue(20))), st63);
        IStatement st6 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), st62);

        IRepository repo6 = new Repository(st6, "log6.txt");
        IController controller6 = new Controller(repo6);

        PrintStatement st75 = new PrintStatement(new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5)), ArithmeticalOperator.ADD));
        CompStatement st74 = new CompStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))), st75);
        CompStatement st73 = new CompStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), st74);
        CompStatement st72 = new CompStatement(new HeapAllocStatement("v", new ValueExpression(new IntValue(20))), st73);
        IStatement st7 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), st72);

        IRepository repo7 = new Repository(st7, "log7.txt");
        IController controller7 = new Controller(repo7);


        PrintStatement st86 = new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))));
        CompStatement st85 = new CompStatement(new HeapAllocStatement("v", new ValueExpression(new IntValue(30))), st86);
        CompStatement st84 = new CompStatement(new HeapAllocStatement("a", new VariableExpression("v")), st85);
        CompStatement st83 = new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))), st84);
        CompStatement st82 = new CompStatement(new HeapAllocStatement("v", new ValueExpression(new IntValue(20))), st83);
        IStatement st8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), st82);

        IRepository repo8 = new Repository(st8, "log8.txt");
        IController controller8 = new Controller(repo8);


        PrintStatement st94 = new PrintStatement(new HeapReadingExpression(new VariableExpression("v")));
        CompStatement st93 = new CompStatement(new HeapAllocStatement("v", new ValueExpression(new IntValue(30))), st94);
        CompStatement st92 = new CompStatement(new HeapAllocStatement("v", new ValueExpression(new IntValue(20))), st93);
        IStatement st9 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), st92);

        IRepository repo9 = new Repository(st9, "log9.txt");
        IController controller9 = new Controller(repo9);


        PrintStatement st104 = new PrintStatement(new VariableExpression("v"));
        IStatement whileBodyStatement = new CompStatement(
                new PrintStatement(new VariableExpression("v")),
                new AssignStatement(
                        "v",
                        new ArithmeticalExpression(
                                new VariableExpression("v"),
                                new ValueExpression(new IntValue(1)),
                                ArithmeticalOperator.SUBTRACT
                        )
                )
        );
        IExpression whileExpression = new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), RelationalOperator.GreaterThan);
        CompStatement st103 = new CompStatement(new WhileStatement(whileExpression, whileBodyStatement), st104);
        CompStatement st102 = new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))), st103);
        IStatement st10 = new CompStatement(new VarDeclStatement("v", new IntType()), st102);

        IRepository repo10 = new Repository(st10, "log10.txt");
        IController controller10 = new Controller(repo10);


        PrintStatement st1111 = new PrintStatement(new HeapReadingExpression(new VariableExpression("a")));
        CompStatement st1110 = new CompStatement(new PrintStatement(new VariableExpression("v")), st1111);

        PrintStatement st119 = new PrintStatement(new HeapReadingExpression(new VariableExpression("a")));
        CompStatement st118 = new CompStatement(new PrintStatement(new VariableExpression("v")), st119);
        CompStatement st117 = new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))), st118);
        CompStatement st116 = new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))), st117);
        ThreadCreationStatement st115 = new ThreadCreationStatement(st116);

        CompStatement st114 = new CompStatement(new HeapAllocStatement("a", new ValueExpression(new IntValue(22))), new CompStatement(st115, st1110));
        CompStatement st113 = new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))), st114);
        CompStatement st112 = new CompStatement(new VarDeclStatement("a", new RefType(new IntType())), st113);
        IStatement st11 = new CompStatement(new VarDeclStatement("v", new IntType()), st112);

        IRepository repo11 = new Repository(st11, "log11.txt");
        IController controller11 = new Controller(repo11);


        PrintStatement st123 = new PrintStatement(new VariableExpression("v"));
        CompStatement st122 = new CompStatement(new VarDeclStatement("v", new IntType()), st123);
        IStatement st12 = new CompStatement(new VarDeclStatement("v", new IntType()), st122);

        IRepository repo12 = new Repository(st12, "log12.txt");
        IController controller12 = new Controller(repo12);


        PrintStatement st133 = new PrintStatement(new VariableExpression("v"));
        CompStatement st132 = new CompStatement(new AssignStatement("v", new ValueExpression(new StringValue("10"))), st133);
        IStatement st13 = new CompStatement(new VarDeclStatement("v", new IntType()), st132);

        IRepository repo13 = new Repository(st13, "log13.txt");
        IController controller13 = new Controller(repo13);



        IStatement st1420 = new UnlockStatement("q");
        IStatement st1419 = new CompStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v2"))), st1420);
        IStatement st1418 = new CompStatement(new LockStatement("q"), st1419);

        IStatement st1417 = new CompStatement(new UnlockStatement("x"), st1418);
        IStatement st1416 = new CompStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v1"))), st1417);
        IStatement st1415 = new CompStatement(new LockStatement("x"), st1416);

        IStatement st1414 = new CompStatement(new NopeStatement(), st1415);
        IStatement st1413 = new CompStatement(new NopeStatement(), st1414);
        IStatement st1412 = new CompStatement(new NopeStatement(), st1413);
        IStatement st1411 = new CompStatement(new NopeStatement(), st1412);
        
        CompStatement st1410 = new CompStatement(new ThreadCreationStatement(
            new CompStatement(new ThreadCreationStatement(new CompStatement(new LockStatement("q"),
            new CompStatement(new HeapWritingStatement("v2", 
            new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v2")), 
            new ValueExpression(new IntValue(5)), ArithmeticalOperator.ADD)), new UnlockStatement("q")))),
             new CompStatement(new LockStatement("q"),
             new CompStatement(new HeapWritingStatement("v2", 
             new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v2")), 
             new ValueExpression(new IntValue(10)), ArithmeticalOperator.MULTIPLY)), new UnlockStatement("q"))))
        ), st1411);

        
        IStatement st149 = new CompStatement(new NewLockStatement("q"), st1410);
        CompStatement st148 = new CompStatement(new ThreadCreationStatement(
            new CompStatement(new ThreadCreationStatement(new CompStatement(new LockStatement("x"),
            new CompStatement(new HeapWritingStatement("v1", 
            new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v1")), 
            new ValueExpression(new IntValue(1)), ArithmeticalOperator.SUBTRACT)), new UnlockStatement("x")))),
             new CompStatement(new LockStatement("x"),
             new CompStatement(new HeapWritingStatement("v1", 
             new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v1")), 
             new ValueExpression(new IntValue(10)), ArithmeticalOperator.MULTIPLY)), new UnlockStatement("x"))))
        ), st149);
        IStatement st147 = new CompStatement(new NewLockStatement("x"), st148);
        IStatement st146 = new CompStatement(new HeapAllocStatement("v2", new ValueExpression(new IntValue(30))), st147);
        IStatement st145 = new CompStatement(new HeapAllocStatement("v1", new ValueExpression(new IntValue(20))), st146);
        IStatement st144 = new CompStatement(new VarDeclStatement("q", new IntType()), st145);
        IStatement st143 = new CompStatement(new VarDeclStatement("x", new IntType()), st144);
        IStatement st142 = new CompStatement(new VarDeclStatement("v2", new RefType(new IntType())), st143);
        IStatement st14 = new CompStatement(new VarDeclStatement("v1", new RefType(new IntType())), st142);

        IRepository repo14 = new Repository(st14, "log14.txt");
        IController controller14 = new Controller(repo14);




        ObservableList<Command> commands = FXCollections.observableArrayList();
        commands.add(new Command(st1.toString(), controller1));
        commands.add(new Command(st2.toString(), controller2));
        commands.add(new Command(st3.toString(), controller3));
        commands.add(new Command(st4.toString(), controller4));
        commands.add(new Command(st5.toString(), controller5));
        commands.add(new Command(st6.toString(), controller6));
        commands.add(new Command(st7.toString(), controller7));
        commands.add(new Command(st8.toString(), controller8));
        commands.add(new Command(st9.toString(), controller9));
        commands.add(new Command(st10.toString(), controller10));
        commands.add(new Command(st11.toString(), controller11));
        commands.add(new Command(st12.toString(), controller12));
        commands.add(new Command(st13.toString(), controller13));
        commands.add(new Command(st14.toString(), controller14));




        return commands;
    }
}