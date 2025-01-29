import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Game {
	/**
	 * funcion para inicializar el juego
	 */
	public static void createGame() {
		// crear un objeto de tipo JFrame
		JFrame app = new JFrame("Number Match");
		// crear un objeto de tipo NumberMatch el juego (extendidio por JPanel)
		NumberMatch t = new NumberMatch();

		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set el tamano de la pantalla por defecto
		app.setSize(500, 500);
		// hacer la pantalla redimensionable
		app.setResizable(true);
		// anadir el juego al app y activar el barra de desplazamiento vertical
		app.add(BorderLayout.CENTER,
				new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		// hacer la pantalla del juego en el centro de la pantalla del dipositivo
		app.setLocationRelativeTo(null);
		// hacer la pantalle visible
		app.setVisible(true);
	}

	public static void main(String[] args) {
		// empezar el juego
		createGame();
	}
}
