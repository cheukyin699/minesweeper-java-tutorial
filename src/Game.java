import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This class holds the logic for the game. It houses the logic for playing the game, drawing the game, and
 * handling mouse input.
 */
public class Game extends JFrame {

    // We want to declare some variable here
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    public static final int TILE_WIDTH = 20;
    public static final int TILE_HEIGHT = 20;
    public static final int NUM_BOMBS = 10;

    private ArrayList<Tile> tiles;
    private Set<Dimension> bombs;
    private Set<Dimension> revealed;

    public Game() {
        tiles = new ArrayList<>();
        bombs = new HashSet<>();
        revealed = new HashSet<>();
        initTiles();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH * TILE_WIDTH, HEIGHT * TILE_HEIGHT);

        initMouseListener();
    }

    /**
     * Handles whenever you use a mouse
     */
    void initMouseListener() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // When you click the mouse, add it to the list of locations you clicked
                int x = e.getX() / TILE_WIDTH;
                int y = e.getY() / TILE_HEIGHT;
                revealed.add(new Dimension(x, y));

                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    /**
     * Start the game
     */
    public void play() {
        setVisible(true);
    }

    /**
     * Set a random number of bombs
     */
    void initBombs() {
        while (bombs.size() < NUM_BOMBS) {
            bombs.add(new Dimension((int) (Math.random() * WIDTH),
                    (int) (Math.random() * HEIGHT)));
        }
    }

    /**
     * Set up all of the tiles, and set the bombs in the tiles as well
     */
    void initTiles() {
        initBombs();

        // Go through all the tiles and set bombs if need be
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Dimension thisDimension = new Dimension(i, j);
                tiles.add(new Tile(bombs.contains(thisDimension)));
            }
        }
    }

    Tile getTile(int row, int col) {
        try {
            return tiles.get(row * WIDTH + col);
        } catch (IndexOutOfBoundsException e) {
            return new Tile(false);
        }
    }

    boolean containsDimension(int x, int y) {
        for (Dimension d: revealed) {
            if (d.width == x && d.height == y) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (containsDimension(i, j)) {
                    // Draw what the tile is if you have already clicked on it
                    Tile t = getTile(i, j);
                    if (t.hasBomb) {
                        // It is a bomb, so colour it red
                        g.setColor(Color.red);
                    } else {
                        // It does not have a bomb, so colour it white
                        g.setColor(Color.white);
                    }
                } else {
                    // If you don't know what it is, colour it gray
                    g.setColor(Color.gray);
                }
                g.fillRect(i * TILE_WIDTH, j * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                g.setColor(Color.black);
                g.drawRect(i * TILE_WIDTH, j * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
            }
        }
    }
}
