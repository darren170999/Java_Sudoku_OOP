import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.*;  // Uses AWT's Event Handlers
import javax.swing.*;     // Uses Swing's Container/Components
import java.math.*;
import java.io.*;
import java.util.*;
import java.net.URL;
import javax.sound.sampled.*;
/**
 * The Sudoku game.
 * To solve the number puzzle, each row, each column, and each of the
 * nine 3×3 sub-grids shall contain all of the digits from 1 to 9
 */
public class SudokuGeneration extends JFrame 
{
   /**
     *
     */
    private static final long serialVersionUID = 1L;
// Name-constants for the game properties
    public static final int GRID_SIZE = 9;    // Size of the board
    public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
   // Name-constants for UI control (sizes, colors and fonts)
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int CANVAS_WIDTH  = CELL_SIZE * GRID_SIZE;
    public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE; // Board width/height in pixels
    public static final Color OPEN_CELL_BGCOLOR = Color.YELLOW; // open cells are yellow in color
    public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // if correct green
    public static final Color OPEN_CELL_TEXT_NO = Color.RED; //if wrong red
    public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // Cells that are not editable are white in color
    public static final Color CLOSED_CELL_TEXT = Color.BLACK; // color of words and numbers are black
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20); // font type and size
    public static int num1, num2, num3;
    public final static boolean SENTINEL = true;
   // The game board composes of 9x9 JTextFields, each containing String "1" to "9", or empty String
    private static JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
    // Puzzle to be solved and the mask (which can be used to control the difficulty level).
    static int[][] puzzle = new int[9][9];
    //static int[][] puzzleempty = new int[9][9];
    static int[][] ANSWER_KEY = new int[9][9];
    static int[][] original = new int[9][9];
    static int count = 0;
    public static boolean checkSudokuStatus(int[][] grid, int[][] ANSWER_KEY) 
    {
       for(int i = 0; i < 9 ; i++)
       {
            for(int j = 0; j<9; j++)
            {
                if(!(ANSWER_KEY[i][j] == grid[i][j]))
                {
                return false;
                }
            }
        }
        return true;
    }
   /**
    * Constructor to setup the game and the UI Components
    */
    public SudokuGeneration() 
    { //UI
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));  // 9x9 GridLayout
      // Allocate a common listener as the ActionEvent listener for all the JTextFields
        this.setBounds(600,100,GRID_SIZE,GRID_SIZE);
        InputListener listener = new InputListener();
        SudokuMenuBar menuBar = new SudokuMenuBar();
        Random random = new Random();
        puzzle = random.getRandom();     
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j<9;j++)
            {
                ANSWER_KEY[i][j] = puzzle[i][j];
            }
        }
        if(Difficulty.getDifficulty() == 2) // easy difficulty
        {
            num1 = Difficulty.getDifficulty();      
            for(int i = 0; i < 9; i+=num1)
            {
                for(int j = 0; j<9;j+=num1)
                {
                    if((i+j) % 2 == 0)
                    {
                        puzzle[i][j] = 0;
                        count++;
                    }
                }
            }
        }
        else if(Difficulty.getDifficulty() == 1) // medium difficulty
        { 
            num1 = Difficulty.getDifficulty();
            for(int i = 0; i < 9; i+=num1)
            {
                for(int j = 0; j<9;j+=num1)
                {
                    if(((i / 3) * 3 + j / 3) == i || (i * 3 % 9 + j % 3) == i)
                    {
                        puzzle[i][j] = 0;
                        count++;
                    }
                }
            }
        }
        else if(Difficulty.getDifficulty() == 3) // hard difficulty
        { 
            num1 = Difficulty.getDifficulty();
            for(int i = 0; i < 9; i+=num1-2)
            {
                for(int j = 0; j<9;j+=num1-2)
                {
                    if(i == 0 || i == 2 || i == 4 || i == 6 || i == 8 || i + j == 8 || i + j == 2 * i )
                    {
                        puzzle[i][j] = 0;
                        count++;
                    }
                }
            }
        }
      // reset to state
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j<9;j++)
            {
                original[i][j] = puzzle[i][j];
            }
        }
      // Construct 9x9 JTextFields and add to the content-pane      
        for (int row = 0; row < GRID_SIZE; ++row) 
        {
            for (int col = 0; col < GRID_SIZE; ++col) 
            {
                tfCells[row][col] = new JTextField(); // Allocate element of array
                cp.add(tfCells[row][col]);            // ContentPane adds JTextField
                if((puzzle[row][col]) == 0) 
                { //if open cell's mask is true, set cell to have no number allow it to be editable with a yellow color.
                    tfCells[row][col].setText("");     // set to empty string
                    tfCells[row][col].setEditable(true);
                    tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
                    tfCells[row][col].addActionListener(listener); // with this every cell that is open can have an action allocated to it
                    //count++; // count number of empty squares
                } 
                else 
                {
                    tfCells[row][col].setText(puzzle[row][col] + "");// set cell to solution's number if its closed
                    tfCells[row][col].setEditable(false);// do not allow editing
                    tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR); // white color
                    tfCells[row][col].setForeground(CLOSED_CELL_TEXT); // black color
                }
            // Beautify all the cells
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                tfCells[row][col].setFont(FONT_NUMBERS);
            }
        }
      // Set the size of the content-pane and pack all the components under this container.
        cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT)); //preferred size
        pack(); // pack() sets our window to preferred size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        setTitle("Sudoku by Darren and Bob");
        setVisible(true); //set the window to be visible DEFAULT
}
public static void main(String[] args) 
{ // top down approach
   SwingUtilities.invokeLater(() -> new SudokuGeneration()); // invoke later invoke after everything is made. lambda code
}
   private class InputListener implements ActionListener 
   {

   @Override
   public void actionPerformed(ActionEvent e) 
   { // ANY ACTIONS PUT HERE
       // All the 9*9 JTextFileds invoke this handler. We need to determine
       // which JTextField (which row and column) is the source for this invocation.
      int rowSelected = -1; // assigned -1 as none of the cells are -1
      int colSelected = -1;
       // Get the source object that fired the event
      JTextField source = (JTextField)e.getSource(); // (JTextField) u click the box type shit and press enter to fire event.
       // Scan JTextFileds for all rows and columns, and match with the source object
       // check if correct or wrong!
      boolean found = false;
      for (int row = 0; row < GRID_SIZE && !found; ++row) 
      {
         for (int col = 0; col < GRID_SIZE && !found; ++col) 
         {
            //System.out.print(puzzle[row][col]);
            if (tfCells[row][col] == source) 
            { // if the open cell is selected
                rowSelected = row;
                colSelected = col;
                found = true;  // break the inner/outer loops
                String comparable = tfCells[rowSelected][colSelected].getText();
                int attempt = Integer.parseInt((comparable));
                    if(attempt == ANSWER_KEY[rowSelected][colSelected])
                    {
                    tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_YES);
                    puzzle[rowSelected][colSelected] = attempt;  
                    Rite rite = new Rite();
                    count--;             
                    } 
                    else if (attempt != ANSWER_KEY[rowSelected][colSelected]) 
                    {
                    tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
                    puzzle[rowSelected][colSelected] = attempt;
                    Beep beep = new Beep();
                    }
            }
         }
      }
      if(checkSudokuStatus(puzzle,ANSWER_KEY))
      {
         JOptionPane.showMessageDialog(null, "Congratulation!");
        //restart dialog to restart application
         int answer = JOptionPane.showConfirmDialog(null, "Restart (returns an int)", "Restart Dialog", JOptionPane.YES_NO_CANCEL_OPTION);
         switch (answer) 
         {
         case JOptionPane.YES_OPTION:
         //restart
         reset();
         break;
         case JOptionPane.NO_OPTION:
         System.exit(0);
         break;
         case JOptionPane.CANCEL_OPTION:
         System.out.print("You clicked Cancel"); break;
         }
      } 
   }
}
public static void reset()
{
   for(int i = 0; i < 9; i++)
   {
      for(int j = 0;j < 9; j++)
      {
         puzzle[i][j] = original[i][j];
         if(puzzle[i][j] == 0)
         {
            tfCells[i][j].setText("");
            tfCells[i][j].setBackground(OPEN_CELL_BGCOLOR);
         }
      }
   }
}
public static int getCount(){
    return count;
}
}