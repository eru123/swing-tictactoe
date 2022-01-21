import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tictactoe {
  private static enum CELL { EMPTY, X, O }

  final static Color GRAY = new Color(204, 204, 204);
  final static Color LIGHT_GRAY = new Color(242, 242, 242);
  final static Color LIGHT_GREEN = new Color(179, 255, 204);
  final static Color LIGHT_BLUE = new Color(153, 214, 255);
  final static Color LIGHT_RED = new Color(255, 204, 204);
  final static Color BLACK = new Color(51, 51, 51);

  protected static Player player_x = new Player(CELL.X);
  protected static Player player_o = new Player(CELL.O);
  protected static Player current_player = player_x;

  static GameBoard board;
  static JLabel player_x_score;
  static JLabel player_o_score;
  static JLabel current_player_label;
  static JPanel text_panel;
  static JPanel text_panel2;
  static JButton start_button;
  static JButton reset_button;
  static JPanel button_panel;

  public static void main(String[] args) {
    player_x_score = new JLabel("Player X Score: " + player_x.score);
    player_x_score.setForeground(Color.BLUE);
    player_x_score.setFont(new Font("Arial", Font.BOLD, 15));
    player_o_score = new JLabel("Player O Score: " + player_o.score);
    player_o_score.setForeground(Color.RED);
    player_o_score.setFont(new Font("Arial", Font.BOLD, 15));
    current_player_label = new JLabel("Current Player: " + current_player.name);
    current_player_label.setForeground(current_player.symbol == CELL.X ? Color.BLUE : Color.RED);
    current_player_label.setFont(new Font("Arial", Font.BOLD, 15));
    text_panel = new JPanel(new GridLayout(1, 2));
    text_panel.add(player_x_score);
    text_panel.add(player_o_score);
    text_panel.setBounds(0, 10, 317, 50);
    text_panel2 = new JPanel(new GridLayout(1,1));
    text_panel2.add(text_panel);
    text_panel2.add(current_player_label);
    text_panel2.setBounds(0, 60, 317, 20);
    start_button = new JButton("Start (SPACE)");
    start_button.setBounds(0, 0, 317/2, 20);
    reset_button = new JButton("Reset (R)");
    reset_button.setBounds(317/2, 0, 317/2, 20);
    button_panel = new JPanel(new GridLayout(1, 2));
    button_panel.add(start_button);
    button_panel.add(reset_button);
    button_panel.setBounds(0, 80, 317, 20);
    new MainFrame();
  }

  static void update_text_panel(){
    player_x_score.setText("Player X Score: " + player_x.score);
    player_o_score.setText("Player O Score: " + player_o.score);
    current_player_label.setText("Current Player: " + current_player.name);
    current_player_label.setForeground(current_player.symbol == CELL.X ? Color.BLUE : Color.RED);
  }

  // jframe window class
  static class MainFrame extends JFrame {
    
    // constructor
    public MainFrame() {
      setTitle("Tic Tac Toe");
      setSize(317, 430);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(null);

      // create game board
      board = new GameBoard();
      board.setBounds(0, 100, 300, 300);
      add(board);
      board.revalidate();
      board.setVisible(false);
      
      add(text_panel);
      add(text_panel2);
      add(button_panel);

      start_button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          start_button.setSelected(false);
          start_button.setFocusable(false);
          start();
        }
      });

      reset_button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          reset_button.setSelected(false);
          reset_button.setFocusable(false);
          reset();
        }
      });

      addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            start();
          }
          if (e.getKeyCode() == KeyEvent.VK_R) {
            reset();
          }
        }
      });
    }

    static void start(){
      update_text_panel();
      board.emptyBoxes();
      board.setVisible(true);
    }

    static void reset(){
      int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to reset the game?","Warning", JOptionPane.YES_NO_OPTION);
      if(dialogResult == JOptionPane.YES_OPTION){
        player_x.score = 0;
        player_o.score = 0;
        update_text_panel();
        board.emptyBoxes();
        board.setVisible(false);
      }
    }
  }

  // create box class with mouse listener for each box in tic tac toe
  static class GameBoard extends JPanel {
    Box[][] boxes;
    // constructor
    public GameBoard() {
      // set the background color of the game board
      // set the size of the game board
      setLayout(null);
      setPreferredSize(new Dimension(300, 300));
      setBackground(BLACK);
      // set the layout of the game board

      // create a new instances of the box class
      boxes = new Box[3][3];
      for (int i = 0; i< 3;i++){
        for (int j = 0; j< 3;j++){
          boxes[i][j] = new Box(i,j);
          boxes[i][j].setBounds(i*100,j*100,100,100);
          add(boxes[i][j]);
        }
      }
    }

    public void emptyBoxes(){
      for (int i = 0; i< 3;i++){
        for (int j = 0; j< 3;j++){
          boxes[i][j].clear();
        }
      }
    }

    public boolean hasWinner(){
      // check horizontal
      for (int i = 0; i< 3;i++){
        if (boxes[i][0].state == boxes[i][1].state && boxes[i][1].state == boxes[i][2].state && boxes[i][0].state != CELL.EMPTY){
          boxes[i][0].setBackground(LIGHT_GREEN);
          boxes[i][1].setBackground(LIGHT_GREEN);
          boxes[i][2].setBackground(LIGHT_GREEN);
          return true;
        } 
      }

      // check vertical
      for (int i = 0; i< 3;i++){
        if (boxes[0][i].state == boxes[1][i].state && boxes[1][i].state == boxes[2][i].state && boxes[0][i].state != CELL.EMPTY){
          boxes[0][i].setBackground(LIGHT_GREEN);
          boxes[1][i].setBackground(LIGHT_GREEN);
          boxes[2][i].setBackground(LIGHT_GREEN);
          return true;
        } 
      }

      // check diagonal
      if (boxes[0][0].state == boxes[1][1].state && boxes[1][1].state == boxes[2][2].state && boxes[0][0].state != CELL.EMPTY){
        boxes[0][0].setBackground(LIGHT_GREEN);
        boxes[1][1].setBackground(LIGHT_GREEN);
        boxes[2][2].setBackground(LIGHT_GREEN);
        return true;
      } else if (boxes[0][2].state == boxes[1][1].state && boxes[1][1].state == boxes[2][0].state && boxes[0][2].state != CELL.EMPTY){
        boxes[0][2].setBackground(LIGHT_GREEN);
        boxes[1][1].setBackground(LIGHT_GREEN);
        boxes[2][0].setBackground(LIGHT_GREEN);
        return true;
      }

      return false;
    }
    
    public boolean isFull(){
      for (int i = 0; i< 3;i++){
        for (int j = 0; j< 3;j++){
          if (boxes[i][j].state == CELL.EMPTY){
            return false;
          }
        }
      }
      return true;
    }
  }

  static class Box extends JPanel implements MouseListener {
    CELL state = CELL.EMPTY;
    JLabel label;

    // constructor
    public Box(int row, int col) {
      setBackground(Color.WHITE);
      setSize(100, 100);
      setLayout(new GridBagLayout());
      setBounds(col * 100, row *100, 100, 100);
      setBorder(BorderFactory.createLineBorder(BLACK));
      addMouseListener(this);
      label = new JLabel();
      label.setText("");
      label.setFont(new Font("Consolas", Font.PLAIN, 30));
      add(label);
    }

    public void clear(){
      state = CELL.EMPTY;
      label.setText("");
      setBackground(Color.WHITE);
    }

    // mouse listener for each box
    public void mouseClicked(MouseEvent e) {
      if (state == CELL.EMPTY) {
        if (current_player.symbol == CELL.X) {
          state = CELL.X;
          label.setText("X");
          label.setForeground(Color.BLUE);
          if (!board.hasWinner()) current_player = player_o;
        } else {
          state = CELL.O;
          label.setText("O");
          label.setForeground(Color.RED);
          if (!board.hasWinner()) current_player = player_x;
        }

        if(board.hasWinner()){
          if (current_player.symbol == CELL.X){
            player_x.score++;
          } else {
            player_o.score++;

          }
          MainFrame.start();
          JOptionPane.showMessageDialog(null, current_player.name + " wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
        } else if (board.isFull()){
          JOptionPane.showMessageDialog(null, "It's a tie!", "Tie", JOptionPane.INFORMATION_MESSAGE);
          MainFrame.start();
        }


        update_text_panel();
      }
    }

    public void mousePressed(MouseEvent e) {
      // System.out.println("mouse pressed");

    }

    public void mouseReleased(MouseEvent e) {
      // System.out.println("mouse released");
    }

    public void mouseEntered(MouseEvent e) {
      if (state == CELL.EMPTY) {
        if (current_player.symbol == CELL.X) {
          setBackground(LIGHT_BLUE);
        } else {
          setBackground(LIGHT_RED);
        }
      }
    }

    public void mouseExited(MouseEvent e) {
      if (state == CELL.EMPTY) {
        setBackground(Color.WHITE);
      }
    }
  }
  
  static class Player {
    CELL symbol;
    String name;
    int score;
    int wins;
  
    public Player(CELL symbol) {
      this.symbol = symbol;
      this.score = 0;
      this.wins = 0;

      if (symbol == CELL.X) {
        this.name = "Player X";
      } else {
        this.name = "Player O";
      }
    }
  }
}

