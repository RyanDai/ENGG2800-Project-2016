package gui;

import java.io.IOException;

public class Product {

	public static void main(String[] args) throws IOException {
		Model model = new Model();
		View view = new View(model);
		new Controller(model,view);
		view.setVisible(true);
	}

}
