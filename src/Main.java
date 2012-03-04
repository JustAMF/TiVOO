
import controller.TivooSystem;

import view.TiVOOViewer;

public class Main {
	
	public static void main (String args[]) {
		TiVOOViewer tvv = new TiVOOViewer("TiVOO");

		tvv.setModel(new TivooSystem());
	}
}
