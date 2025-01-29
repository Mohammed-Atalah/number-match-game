
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * Number Match Game clase que esta extendido por JPanel clase
 * <p>
 * Version de Movil: <a href=
 * "https://play.google.com/store/apps/details?id=com.easybrain.number.puzzle.game&pcampaignid=web_share">Andriod</a>
 * <a href=
 * "https://apps.apple.com/us/app/number-match-numbers-game/id1545567989">IOS</a>
 * <ul>
 * <li><b>Como jugar?<b>
 * <ul>
 * <li>El objetivo es despejar el tablero.
 * <li>Encuentra pares de números iguales (1 y 1, 7 y 7) o pares que sumen 10 (6
 * y 4, 8 y 2) en la cuadrícula de números.
 * <li>Toca los números uno por uno para tacharlos y obtener puntos.
 * <li>Puedes conectar pares en celdas adyacentes horizontales, verticales y
 * diagonales, así como al final de una línea y al principio de la siguiente.
 * <li>Cuando te quedes sin movimientos, puedes agregar líneas adicionales con
 * los números restantes en la parte inferior.
 * <li>Acelera tu progreso con pistas si te quedas atascado.
 * <li>Ganas después de que todos los números se eliminen de la cuadrícula del
 * rompecabezas numérico.
 * </ul>
 * </ul>
 * 
 * @author Atalah Elbatsh, Mohammed
 * @since 10/01/2024
 * @version 1.2
 * @see JPanel
 */
public class NumberMatch extends JPanel {
	/** El Socre del Jugador */
	private int score;
	/** Numero de vidas del Jugador */
	private int lives;
	/** Variable para cargar el icon de anadir */
	private BufferedImage addIcon;
	/** Variable para cargar el icon de mode */
	private BufferedImage modeIcon;
	/** Un array con las coordinadas de los dos números selectados */
	private int[] twoNumbersPostion;
	/** Un array para countar el numero de numeros en cada fila */
	private int[] rowsCounter;
	/** Un array con los numeros del tablero */
	private int[][] numbers;
	/** Un objeto de tipo Color que contine el color de texto */
	private Color textColor;

	/**
	 * Constructor del objeto
	 */
	public NumberMatch() {
		// poner los valors del clase
		newGame();
		// anadir el mouse listener
		addMouseListener(new MouseHandler());
	}

	/**
	 * Set los valores de los atributos del objeto por defecto
	 */
	public void newGame() {
		// cargar el add icon
		setAddIcon("addBlack.png");
		// cargar el mode icon
		setModeIcon("darkMode.png");
		// set el score al valor 0
		setScore(0);
		// set el numero de vidas al valor 5
		setLives(5);
		// set el color de texto a negro
		setTextColor(Color.black);
		// set las coordinadas de los dos numeros selectados a -1 como no hay todavia
		twoNumbersPostion = new int[] { -1, -1, -1, -1 };
		// Generar los numeros del tablero
		tableNumbersGenerator();
	}

	/**
	 * Cargar el add icon
	 * 
	 * @param fileName el nombre del fichero del icon
	 */
	public void setAddIcon(String fileName) {
		try {
			addIcon = ImageIO.read(new File(String.format("./images/%s", fileName)));
		} catch (IOException exc) {

		}
	}

	/**
	 * Cargar el mode icon
	 * 
	 * @param fileName el nombre del fichero del icon
	 */
	public void setModeIcon(String fileName) {
		try {
			modeIcon = ImageIO.read(new File(String.format("./images/%s", fileName)));
		} catch (IOException exc) {

		}
	}

	/**
	 * Set el valor del score
	 * 
	 * @param ns el nuevo score
	 */
	public void setScore(int ns) {
		if (ns >= 0)
			score = ns;
	}

	/**
	 * retorna el valor del score
	 * 
	 * @return el valor del score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Set el numeros de vidas
	 * 
	 * @param nl el nuevo numero de vidas
	 */
	public void setLives(int nl) {
		if (nl >= 0)
			lives = nl;
	}

	/**
	 * retorna el numero de vidas
	 * 
	 * @return el numero de vidas
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * set el color del text
	 * 
	 * @param c el color
	 */
	public void setTextColor(Color c) {
		textColor = c;
	}

	/**
	 * retorna el color del text
	 * 
	 * @return el color del text
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * Generar los numeros del tablero
	 */
	public void tableNumbersGenerator() {
		// set el array numbers con dimensión 3 x 9
		numbers = new int[3][9];
		// set el numero de numeros en cada fila a 9
		rowsCounter = new int[] { 9, 9, 9 };
		// generar los numeros usaando el funcion randomBetween()
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				numbers[i][j] = randomBetween(1, 10);
			}
		}
	}

	/**
	 * generar un numero entero random entre [l,h)
	 * 
	 * @param l el minimo valor (incluido)
	 * @param h el maximo valor (excluido)
	 * @return el numero generado
	 */
	public int randomBetween(int l, int h) {
		Random r = new Random();
		return r.nextInt(h - l) + l;
	}

	/**
	 * dibujar los elementos en la pantalla
	 * 
	 * @param g el objeto de tipo Graphics para dibujar
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// dibujar los textos del juego
		paintText(g);
		// dibujar el add icon
		paintAddIcon(g);
		// dibujar el mode icon
		paintModeIcon(g);
		// dibujar el tablero
		paintTable(g);
	}

	/**
	 * dibujar los textos del juego (el score, numero de vidas, hints, possible
	 * moves)
	 * 
	 * @param g el objeto de tipo Graphics para dibujar
	 */
	public void paintText(Graphics g) {
		// crear el fuente para usarlo
		Font fuente = new Font("Arial", Font.PLAIN, 20);
		// set el funte del g al fuente anterior
		g.setFont(fuente);
		// set el color de g al color elegido por el mode
		g.setColor(getTextColor());
		// escribir el texto del score
		g.drawString(String.format("Socore: %d", getScore()), 68, 50);
		// escribir el texto de numero de vidas
		g.drawString(String.format("Lives: %d", getLives()), 68, 75);
		// escribir el texto "Hints"
		g.drawString(String.format("Hint", getScore()), 68, 150);
		// escribir el text "Possible Moves"
		g.drawString(String.format("Possible Moves", getScore()), 290, 150);

	}

	/**
	 * dibujar el add icon
	 * 
	 * @param g el objeto de tipo Graphics para dibujar
	 */
	public void paintAddIcon(Graphics g) {
		g.drawImage(addIcon, 300, 40, this);
	}

	/**
	 * dibujar el mode icon
	 * 
	 * @param g el objeto de tipo Graphics para dibujar
	 */
	public void paintModeIcon(Graphics g) {
		g.drawImage(modeIcon, 380, 40, this);
	}

	/**
	 * dibujar el tablero
	 * 
	 * @param g el objeto de tipo Graphics para dibujar
	 */
	public void paintTable(Graphics g) {
		// crear el fuente para usarlo
		Font fuente = new Font("Arial", Font.PLAIN, 30);
		// set el funte del g al fuente anterior
		g.setFont(fuente);
		// set el color de g al color elegido por el mode
		g.setColor(getTextColor());

		// las coordinadas del primer numero (el texto no el cuadrado)
		int x = 80;
		int y = 200;
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[0].length; j++) {
				// dibujar el cuadrado solo las frunteras
				g.drawRect(x + (j * 40) - 12, y + (i * 40) - 32, 40, 40);
				// ver si es el numero elegido primero
				if (i == twoNumbersPostion[0] && j == twoNumbersPostion[1]) {
					// set el color a gris
					g.setColor(new Color(170, 170, 170));
					// dibujar cuadrado con color gris
					g.fillRect(x + (j * 40) - 12, y + (i * 40) - 32, 40, 40);
					// set el color de g al color elegido por el mode
					g.setColor(getTextColor());
				}
				// si el numero no es 0 escribir el numero
				if (numbers[i][j] != 0)
					g.drawString(String.format("%d", numbers[i][j]), x + (j * 40), y + (i * 40));
			}

		}

	}

	/**
	 * Un clase extendido de MouseAdapter para tratar las clics del raton
	 */
	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			// hacer el trabajo de la parte en la que se hizo clic
			onClickDo(e.getX(), e.getY());
			// dibujar otra vez los elementos
			repaint();
			// ver si tiene mas vidas o no O si hay numeros o no en la tablero
			checkLivesRows();
		}
	}

	/**
	 * Ver si las coordinadas estan en la zona de un elemento y hacer su funcion
	 * 
	 * @param x la coordinada X del raton
	 * @param y la coordinada Y del raton
	 */
	public void onClickDo(int x, int y) {
		// ver si las coordinadas estan en la zona del add icon
		if (300 <= x && x <= 332 && 30 <= y && y <= 72) {
			// anadir numeros al tablero
			addNumbers();
		}
		// ver si las coordinadas estan en la zona del mode icon
		else if (380 <= x && x <= 412 && 30 <= y && y <= 72) {
			// cambiar el mode del juego
			changeMode();
		}
		// ver si las coordinadas estan en la zona del tablero
		else if (68 <= x && x <= 68 + (9 * 40) && 168 <= y
				&& y <= 168 + numbers.length * 40) {
			// get las coordinadas en el tablero como array
			int[] result = getNumberCoordinates(x, y);
			// si el numero es 0 no hace nada
			if (numbers[result[0]][result[1]] == 0)
				return;
			// ver si es el primer numero
			if (twoNumbersPostion[0] == -1) {
				// set las coordinadas del primer numero
				twoNumbersPostion[0] = result[0];
				twoNumbersPostion[1] = result[1];
			} else {
				// set las coordinadas del segundo numero
				twoNumbersPostion[2] = result[0];
				twoNumbersPostion[3] = result[1];
				// trabajar con los dos numeros
				twoNumbersOperator();
			}
		}
		// ver si las coordinadas estan en la zona del texto "Hint"
		else if (65 <= x && x <= 105 && 130 <= y && y <= 155) {
			// mostrar un mensage con una pista
			movesMessage(true);
		}
		// ver si las coordinadas estan en la zona del texto "Possible Moves"
		else if (290 <= x && x <= 430 && 130 <= y && y <= 155) {
			// mostrar todas las posibles movimientos
			movesMessage(false);

		}
	}

	/**
	 * ver si el jugador no tiene mas vidas o no tiene mas numeros y tratar cada
	 * caso
	 * si no tiene mas vidas finaliza el juego con perdida
	 * si no tiene mas numeros finaliza el juego con gana
	 */
	public void checkLivesRows() {
		// ver si no tiene mas vidas
		if (getLives() == 0) {
			// mostrar un mensaje que el jugador no tiene mas vidas y perdió
			JOptionPane.showMessageDialog(null,
					String.format("You don't have more Lives :(\nYour Score is %d", getScore()));
			// preguntar el jugador si quiere jugar otra vez
			playAgainQ();
		}
		// ver si no hay numeros en el tablero
		if (rowsCounter.length == 0) {
			// mostrar un mensaje que el jugador ganó
			JOptionPane.showMessageDialog(null, String.format("Winner!\n Your Score is %d", getScore()));
			// preguntar el jugador si quiere jugar otra vez
			playAgainQ();
		}
	}

	/**
	 * preguntar si el jugador quiere jugar otra vez
	 */
	public void playAgainQ() {
		// mostrar una pregunta si quiere jugar otra vez
		int o = JOptionPane.showConfirmDialog(null, String.format("Do you want to play again?"), "Message",
				JOptionPane.YES_NO_OPTION);
		// ver si el jugador quiere jugar otra vez
		if (o == 0) {
			// reset los valores del juego
			newGame();
			// dibujar de nuevo los elementos
			repaint();
		} else {
			// salir y cerrar todo el codigo
			System.exit(0);
		}
	}

	/**
	 * Anadir mas numeros al tablero, lo numeros son los mismos que quedan y en le
	 * mismo orden doonde empieza el primer numero después el ultimo numero en el
	 * tablero antiguo
	 */
	public void addNumbers() {
		// array para memorizar los numeros que quedan el tablero con maximo length
		// (worst case)
		int[] v = new int[numbers.length * numbers[0].length];
		// array para apontar al tablero antiguo
		int[][] numbersOld = numbers;
		// array para apontar al array de numeros de numeros en cada fila
		int[] rowsCounterOld = rowsCounter;
		// contador del numero de numeros que quedan
		int o = 0;
		// contador del numero de ceros al final del ultima fila
		int w = 0;
		// anadir los numeros que quedan al array v
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[0].length; j++) {
				if (numbers[i][j] != 0) {
					v[o] = numbers[i][j];
					o++;
					w = 0;
				}
				// contar los ceros al final del ultima fila
				if (i == numbers.length - 1 && numbers[i][j] == 0) {
					w++;
				}
			}
		}
		// calcular el numero de filas necesarias
		int rows = numbers.length + (int) Math.ceil((o - w) / 9.0);
		// crear una nueva array con las nuevas dimensiones del tablero
		numbers = new int[rows][9];
		// crear una nueva array con la nueva numero de filas para rowsCounter
		rowsCounter = new int[rows];
		// copiar los valores antiguos
		for (int i = 0; i < numbersOld.length; i++) {
			numbers[i] = numbersOld[i];
			rowsCounter[i] = rowsCounterOld[i];
		}

		// el indice en el array v
		int o2 = 0;
		for (int i = numbersOld.length - 1; i < numbers.length; i++) {
			for (int j = 0; j < numbers[0].length; j++) {
				// si esta en la ultima fila del tablero antiguo hace el indice de la columnas
				// el siguiente del utlimo numero no cero
				if (i == numbersOld.length - 1 && j == 0) {
					j = numbers[0].length - w - 1;
					continue;
				}
				// ver si todavia falta numeros no anadados
				if (o2 < o) {
					numbers[i][j] = v[o2];
					rowsCounter[i]++;
					o2++;
				}
				// el resto de cells ceros
				else {
					numbers[i][j] = 0;
				}

			}
		}
		// cambiar la altura de la pantalla (JPanel)
		changePanelHeight(Math.max(getHeight(), 170 - 32 + (numbers.length + 1) * 40));
	}

	/**
	 * cambiar la altura de la pantalla (JPanle)
	 * 
	 * @param e el nuevo valor de la altura
	 */
	public void changePanelHeight(int e) {
		setPreferredSize(new Dimension(getWidth(), e));
		revalidate();
	}

	/**
	 * Cambiar el mode del juego de light a dark o el opuesto
	 */
	public void changeMode() {
		// si el mode presente es dark
		if (getTextColor() == Color.black) {
			// cambiar el color del background
			setBackground(new Color(50, 59, 66));
			// cambiar el color por defecto a blanco
			setTextColor(Color.white);
			// cambiar el icon del add icon
			setAddIcon("addWhite.png");
			// cambiar el icon del mode icon
			setModeIcon("lightMode.png");
		}
		// si el mode presente es light
		else {
			// cambiar el color del background
			setBackground(new Color(255, 255, 255));
			// cambiar el color por defecto a negro
			setTextColor(Color.black);
			// cambiar el icon del add icon
			setAddIcon("addBlack.png");
			// cambiar el icon del mode icon
			setModeIcon("darkMode.png");
		}
		// actualizar el JPanel
		revalidate();
	}

	/**
	 * calcula con las coordinadas del raton las coordinadas respecto el array
	 * numbers
	 * 
	 * @param x la coordinada X del raton
	 * @param y la coordinada Y del raton
	 * @return las coordinadas respecto el array numbers
	 */
	public int[] getNumberCoordinates(int x, int y) {
		return new int[] { (y - 168) / 40, (x - 68) / 40 };
	}

	/**
	 * tratar los dos numeros selectados
	 */
	public void twoNumbersOperator() {
		// si los dos numeros son iguales o a la sumalos = 10
		boolean c1 = checkNumbersValidity(numbers[twoNumbersPostion[0]][twoNumbersPostion[1]],
				numbers[twoNumbersPostion[2]][twoNumbersPostion[3]]);
		// el valor anadido al score si los posiciones son validos / -1 si no son
		// validos
		int c2 = checkNumbersPostionValidity(twoNumbersPostion[0], twoNumbersPostion[1], twoNumbersPostion[2],
				twoNumbersPostion[3]);

		if (c1 && c2 > 0) {
			// cambiar los valores de los dos numeros a cero en el tablero
			numbers[twoNumbersPostion[0]][twoNumbersPostion[1]] = 0;
			numbers[twoNumbersPostion[2]][twoNumbersPostion[3]] = 0;
			// restar el numero de numeros en sus filas
			rowsCounter[twoNumbersPostion[0]]--;
			rowsCounter[twoNumbersPostion[2]]--;
			// si ambos filas no tienen numeros
			if (rowsCounter[twoNumbersPostion[0]] == 0 &&
					rowsCounter[twoNumbersPostion[2]] == 0) {
				// si estan en la misma fila
				if (twoNumbersPostion[0] == twoNumbersPostion[2]) {
					deleteRows(twoNumbersPostion[0], -1);
				}
				// si son diferentes filas
				else {
					deleteRows(twoNumbersPostion[0], twoNumbersPostion[2]);
				}
			}
			// si solo la fila del primer numero no tiene numeros
			else if (rowsCounter[twoNumbersPostion[0]] == 0) {
				deleteRows(twoNumbersPostion[0], -1);
			}
			// si solo la fila del segunda numero no tiene numeros
			else if (rowsCounter[twoNumbersPostion[2]] == 0) {
				deleteRows(twoNumbersPostion[2], -1);
			}
			// actualizar el valor del socre
			setScore(getScore() + c2);
		}
		// jugada no valida
		else if (!c1 || c2 == -1) {
			// restar el numero de vidas
			setLives(getLives() - 1);
		}
		// reset las coordinadas de numeros selectados
		twoNumbersPostion = new int[] { -1, -1, -1, -1 };

	}

	/**
	 * la validad de los valores del los dos numeros
	 * 
	 * @param n1 el valor del primer numero
	 * @param n2 el valor del segundo numero
	 * @return si sus valores son validos o no
	 */
	public boolean checkNumbersValidity(int n1, int n2) {
		if (n1 == n2 || n1 + n2 == 10)
			return true;
		return false;
	}

	/**
	 * la validad de las coordinadas y el valor anadido al score
	 * 
	 * @param r1 el numero de fila del primer numero
	 * @param c1 el numero de columna del primer numero
	 * @param r2 el numero de fila del segundo numero
	 * @param c2 el numero de columna del segundo numero
	 * @return -1 si las coordinadas no son validas / un numero representa el valor
	 *         anadido al score
	 */
	public int checkNumbersPostionValidity(int r1, int c1, int r2, int c2) {
		// si los dos numeros son lo mismo en coordinadas
		if ((r1 == r2) && (c1 == c2)) {
			return 0;
		}
		// diagonal
		boolean case1 = (Math.abs(r2 - r1) == Math.abs(c2 - c1));
		// la misma fila
		boolean case2 = (r1 == r2);
		// la misma columna
		boolean case3 = (c1 == c2);
		// al final de la fila y el principio del siguiente
		boolean case4 = (Math.abs(r2 - r1) == 1);
		// si no cumple ningon casos
		if (!(case1 || case2 || case3 || case4)) {
			return -1;
		}
		// ordenar las coordinadas respecto el orden de la fila
		if (r2 < r1) {
			int rr1 = r1;
			int rr2 = r2;
			int cc1 = c1;
			int cc2 = c2;
			r1 = rr2;
			r2 = rr1;
			c1 = cc2;
			c1 = cc1;
		}

		// el caso de diagonal
		if (case1) {
			// ver si hay otro numero que el cero entre los dos numeros
			if (c1 < c2) {
				for (int i = 1; i < c2 - c1; i++) {
					if (numbers[r1 + i][c1 + i] != 0)
						return -1;
				}
			} else if (c2 < c1) {
				for (int i = 1; i < c1 - c2; i++) {
					if (numbers[r1 + i][c1 - i] != 0)
						return -1;
				}
			}
			// return el valor anadido al score
			return 1 + 4 * (Math.abs(r2 - r1) - 1);
		} else if (case2) {
			// ver si hay otro numero que el cero entre los dos numeros
			for (int i = Math.min(c1, c2) + 1; i < Math.max(c1, c2); i++) {
				if (numbers[r1][i] != 0)
					return -1;
			}
			// return el valor anadido al score
			return 1 + 2 * (Math.abs(c2 - c1) - 1);
		} else if (case3) {
			// ver si hay otro numero que el cero entre los dos numeros
			for (int i = r1 + 1; i < r2; i++) {
				if (numbers[i][c1] != 0)
					return -1;
			}
			return 1 + 2 * (r2 - r1 - 1);
		} else if (case4) {
			// ver si hay otro numero que el cero entre los dos numeros
			for (int i = c1 + 1; i < numbers[0].length; i++) {
				if (numbers[r1][i] != 0)
					return -1;
			}
			for (int i = 0; i < c2; i++) {
				if (numbers[r2][i] != 0)
					return -1;
			}
			// return el valor anadido al score
			return 1 + ((numbers[0].length - c1 - 1) + c2) * 3;

		}
		return -1;
	}

	/**
	 * borrar las filas vacias
	 * 
	 * @param r1 primera fila
	 * @param r2 segunda fila / -1 si no hay
	 */
	public void deleteRows(int r1, int r2) {
		// numero de filas para borrar
		int e = 1;
		// si hay segunda fila
		if (r2 != -1)
			e += 1;
		// array para apontar al tablero antiguo
		int[][] numbersOld = numbers;
		// array para apontar a rowCounter antiguo
		int[] rowsCounterOld = rowsCounter;
		// crear nuevo tablero con el numero de filas nuevo
		numbers = new int[numbers.length - e][numbers[0].length];
		// crear nuevo contador de filas con el numero de filas nuevo
		rowsCounter = new int[rowsCounter.length - e];
		// copiar los valores
		int o = 0;
		for (int i = 0; i < numbersOld.length; i++) {
			if (i == r1 || i == r2)
				continue;
			numbers[o] = numbersOld[i];
			rowsCounter[o] = rowsCounterOld[i];
			o++;
		}
	}

	/**
	 * mostrar un mensaje con una o todas los movimientos posibles
	 * 
	 * @param oneOnly si quiere solo una movimiento o todas (true para uno solo)
	 */
	public void movesMessage(boolean oneOnly) {
		// variable para saber si encontrado uno o no
		boolean f = false;
		// el texto del mensaje
		String result = "Possible Moves:\n";

		// iterar todos los numeros
		for (int i1 = 0; i1 < numbers.length && !(f && oneOnly); i1++) {
			for (int j1 = 0; j1 < numbers[i1].length && !(f && oneOnly); j1++) {
				// si es cero continue
				if (numbers[i1][j1] == 0)
					continue;

				// variable para saber si todos los numeros despuse del numero son ceros o no
				boolean clear = true;
				// el caso de la misma fila
				for (int j2 = j1 + 1; j2 < numbers[i1].length && !(f && oneOnly); j2++) {
					if (numbers[i1][j2] == 0) {
						continue;
					} else if (numbers[i1][j1] != numbers[i1][j2] && numbers[i1][j1] + numbers[i1][j2] != 10) {
						clear = false;
						break;
					}
					result += String.format("%d (%d,%d) <-> %d (%d,%d)\n", numbers[i1][j1], i1 + 1, j1 + 1,
							numbers[i1][j2], i1 + 1, j2 + 1);
					f = true;
					clear = false;
					break;
				}
				// caso de al final de la fila y principo del sigunte
				for (int j2 = 0; j2 < numbers[i1].length && clear && i1 + 1 < numbers.length; j2++) {
					if (numbers[i1 + 1][j2] == 0) {
						continue;
					} else if (numbers[i1][j1] != numbers[i1 + 1][j2] && numbers[i1][j1] + numbers[i1 + 1][j2] != 10) {
						break;
					}
					result += String.format("%d (%d,%d) <-> %d (%d,%d)\n", numbers[i1][j1], i1 + 1, j1 + 1,
							numbers[i1 + 1][j2], i1 + 2, j2 + 1);
					f = true;
					break;
				}

				// caso de misma columna
				for (int i2 = i1 + 1; i2 < numbers.length && !(f && oneOnly); i2++) {
					if (numbers[i2][j1] == 0) {
						continue;
					} else if (numbers[i1][j1] != numbers[i2][j1] && numbers[i1][j1] + numbers[i2][j1] != 10) {
						break;
					}
					result += String.format("%d (%d,%d) <-> %d (%d,%d)\n", numbers[i1][j1], i1 + 1, j1 + 1,
							numbers[i2][j1], i2 + 1, j1 + 1);
					f = true;
					break;
				}
				// diagonal derecha
				for (int i2 = i1 + 1, j2 = j1 + 1; j2 < numbers[i1].length && i2 < numbers.length
						&& !(f && oneOnly); i2++, j2++) {

					if (numbers[i2][j2] == 0) {
						continue;
					} else if (numbers[i1][j1] != numbers[i2][j2] && numbers[i1][j1] + numbers[i2][j2] != 10) {
						break;
					}
					result += String.format("%d (%d,%d) <-> %d (%d,%d)\n", numbers[i1][j1], i1 + 1, j1 + 1,
							numbers[i2][j2], i2 + 1, j2 + 1);
					f = true;
					break;

				}
				// diagonal izquierda
				for (int i2 = i1 + 1, j2 = j1 - 1; j2 >= 0 && i2 < numbers.length && !(f && oneOnly); i2++, j2--) {
					if (numbers[i2][j2] == 0) {
						continue;
					} else if (numbers[i1][j1] != numbers[i2][j2] && numbers[i1][j1] + numbers[i2][j2] != 10) {
						break;
					}
					result += String.format("%d (%d,%d) <-> %d (%d,%d)\n", numbers[i1][j1], i1 + 1, j1 + 1,
							numbers[i2][j2], i2 + 1, j2 + 1);
					f = true;
					break;
				}

			}
		}
		// si no hay posibles movimientos
		if (!f) {
			result = "ADD NEW NUMBERS!";
		}
		// monstrar el mensaje
		JOptionPane.showMessageDialog(null, result);

	}

}
