package version3.controller;

import java.util.Scanner;

import version3.controller.conversion.ConvertModel;
import version3.model.library.Library;
import version3.view.CommandLineInterface;

/**
 * This Class serves as the hub of the Controller package's operations, though due to the distribution of
 * responsibility to classes that explicitly deal with certain aspects of the Controller, this Class is
 * left pretty bare in terms of behaviour. Which is fine, it's kind of what we want to happen.
 * 
 * I made a bunch of the support Classes static, though, so it's kind of weird how they interact, but do
 * be assured that all responsibilities of the Controller are satisfied by this. Probably wouldn't hurt to
 * just instantiate regular versions of each sub-Class that handles each responsibility and store them as
 * instance variables, but I have to do some weird stuff to keep 12 hours of refactoring interesting.
 * 
 * The only interaction of the MainController Class to the View is assigning it the ConvertModel object
 * that allows the View to receive genericized information about the Model, to which the provided Library
 * object is given to the ConvertModel object during instantiation. That Library object is also given
 * to the InterpretInput object which needs it to manipulate the Model's state data.
 * 
 * Otherwise this Class just runs the main loop of the program in the runProgram() function, taking input
 * in a while-loop so long as a keyword is not seen to end the program. You can see pretty well where the
 * interpret() function of InterpretModel was torn of out MainController to reduce complexity at the higher
 * level.
 * 
 * Usually your Controller main class would instantiate the Model and the View, but because I prepare a custom
 * Library during my JUnit test and I want the user to have something to look at, I have instead made the
 * MainController assume it will receive a prepared Library for usage as a Model. It's a case-by-case basis
 * on whether or not you want the Controller to generate the Model or not, do what works in your project.
 * 
 * @author Ada Clevinger
 * @version 3.0
 *
 */

public class MainController {
	
//---  Instance Variables   -------------------------------------------------------------------
	
	/** InterpretInput object that takes user input (and the means to receive more) to interpret user commands on retrieving/manipulating data from the Model*/
	private InterpretInput intInp;
	
//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for the MainController Class that takes a prepared Library object representing
	 * the program's Model for reference when manipulating or accessing Model data. Also the whole
	 * project revolves around having a Model, the Controller and View are just formalities necessary
	 * to make the Model something you can interact with.
	 * 
	 * The View and Controller can get fancy, but usually the brunt of the work goes into your Model
	 * unless your tools for developing a View are a terror to work with (*cough* JavaFX *cough*
	 * javax.swing *cough* pretty much any UI you have to do ever *cough*).
	 * 
	 * Allot your time responsibly and try to figure out what's gonna take the most time, you don't want
	 * to be surprised last minute by 'oh, the View is actually really time-consuming'.
	 * 
	 * @param in - Library object representing the Model that the Controller needs for manipulating/displaying
	 */
	
	public MainController(Library in) {
		CommandLineInterface.assignConvertModel(new ConvertModel(in));
		intInp = new InterpretInput(in);
		runProgram();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * This function represents the main loop of the program, taking and reacting to user input
	 * according to the behaviors set down in InterpretInput and displaying Model information
	 * based on the way that ConvertModel prepares information for the CommandLineInterface class
	 * to display.
	 * 
	 */
	
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
