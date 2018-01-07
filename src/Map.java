// Author: Won Kuk Lee

import java.awt.*;
import javax.swing.JFrame;

public class Map extends JFrame {

	static int width = 800;
	static int height = 800;
	Canvas canvas;
	
	public Map(String title) {
		
		super(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(width, height);
		canvas = new Canvas();
		add(canvas);
	}


}