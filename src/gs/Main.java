package gs;

import GS.Controller;
import GS.Model;
import GS.View;

public class Main {

	public static void main(String[] args) {
		new Controller(new Model(), new View());
	}
	
	public static void openGSFrame(){
		new Controller(new Model(), new View());
	}
	
}
