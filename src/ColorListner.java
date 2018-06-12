import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorListner implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		 Board.Square buttonClicked = (Board.Square)e.getSource();
		 buttonClicked.setBackground(Color.green);
	}

}
