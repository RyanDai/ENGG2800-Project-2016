package gui;

import java.io.IOException;

public class Product {

	//Main function, the program starts here
	public static void main(String[] args) throws IOException {
		//Initialize the model, view and controller class
		Model model = new Model();
		View view = new View(model);
		new Controller(model,view);
		view.setVisible(true);
	}

}
