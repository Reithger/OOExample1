package version3.controller;

import java.util.Scanner;

import version3.controller.conversion.ConvertModel;
import version3.model.library.Library;
import version3.view.CommandLineInterface;

public class MainController {

	private InterpretInput intInp;
	
	public MainController(Library in) {
		CommandLineInterface.assignConvertModel(new ConvertModel(in));
		intInp = new InterpretInput(in);
		runProgram();
	}
	
	public void runProgram() {
		Scanner sc = new Scanner(System.in);
		CommandLineInterface.start();
		String input = sc.nextLine();
		while(!input.equals("end")) {
			intInp.interpret(sc, input);
			input = sc.nextLine();
		}
		sc.close();
	}
	
}
