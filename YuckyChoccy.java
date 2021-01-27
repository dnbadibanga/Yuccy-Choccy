import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 *  the main window of the gui
 *  notice that it extends JFrame - so we can add our own components
 *  notice that it implements ActionListener - so we can handle user input
 */
public class YuckyChoccy extends JFrame implements ActionListener
{
    // gui components that are contained in this frame:
    private JPanel topPanel, bottomPanel;   // top and bottom panels in the main window
    private JLabel topLabel;                // a text label to appear in the top panel
    private JButton topButton;              // a 'reset' button to appear in the top panel
    private GridSquare [][] gridSquares;    // squares to appear in grid formation in the bottom panel
    private int x,y;                        // the size of the grid
    private int player;
    
    /*
     *  constructor method takes as input how many rows and columns of gridsquares to create
     *  it then creates the panels, their subcomponents and puts them all together in the main frame
     *  it makes sure that action listeners are added to selectable items
     *  it makes sure that the gui will be visible
     */
    public YuckyChoccy(int x, int y)
    {
        this.x = x;
        this.y = y;
        
        Random randInt = new Random();
        player = randInt.nextInt() % 1;
        
        // first create the panels
        topPanel = new JPanel();
        topPanel.setLayout( new FlowLayout());
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout( new GridLayout( x, y));
        
        // then create the components for each panel and add them to it
        
        // for the top panel:
        topLabel = new JLabel("Humans first!");
        topButton = new JButton("Reset");
        topButton.addActionListener( this);         // IMPORTANT! Without this, clicking the button does nothing.
        
        topPanel.add( topLabel);
        topPanel.add ( topButton);
        
    
        // for the bottom panel:    
        // create the buttons and add them to the grid
        gridSquares = new GridSquare [x][y];
        for ( int column = 0; column < x; column ++)
        {
            for ( int row = 0; row < y; row ++)
            {
                gridSquares [column][row] = new GridSquare( column,row);
                gridSquares [column][row].setSize( 20, 20);
                gridSquares [column][row].setColor( column + row);
                gridSquares [column][row].setOpaque( true);             // without this line and the next the OS' default
                gridSquares [column][row].setBorderPainted( true);      // looks more like a bar of chocolate
                                                                        // (try commenting each out & see what happens)
                
                gridSquares [column][row].addActionListener( this);     // AGAIN, don't forget this line!
                
                bottomPanel.add( gridSquares [column][row]);
            }
        }
        
        // now add the top and bottom panels to the main frame
        getContentPane().setLayout( new BorderLayout());
        getContentPane().add( topPanel, BorderLayout.NORTH);
        getContentPane().add( bottomPanel, BorderLayout.SOUTH);
        pack();
        
        // housekeeping : behaviour
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        setResizable( false);
        setVisible( true);
    }
    
    public void play()
    {
        //while (!gridSquares [0][1].getLegality() && !gridSquares [1][0].getLegality()){

            while (player == 0)
            {
                Random randSide = new Random();
                int side = randSide.nextInt(2);
                int box = 0;
                if (!(gridSquares [0][1].getLegality()))
                {
                    side = 1;
                }
                if (!(gridSquares [1][0].getLegality()))
                {
                    side = 0;
                }
                
                if (side==0) //columns
                {
                    Random randBox = new Random();
                    box = randBox.nextInt(x);
                    while (!(gridSquares [0][box].getLegality()))
                    {
                        box = randBox.nextInt(x);
                    }
                    for (int column = 0; column < x; column ++)
                    {
                        for (int row = box; row < y; row ++)
                        {
                            gridSquares [column][row].eat();
                        }
                    }
                }
                else if (side == 1) //rows
                {
                    Random randBox = new Random();
                    box = randBox.nextInt(y);
                    while (!gridSquares [box][0].getLegality())
                    {
                        box = randBox.nextInt(y);
                    }
                    for (int column = box; column < x; column ++)
                    {
                        for (int row = 0; row < y; row ++)
                        {
                            gridSquares [column][row].eat();
                        }
                    }
                    //end game
                }
                player = 1;
                myTurn();
                loser();
            }
    }
    
    
    /*
     *  handles actions performed in the gui
     *  this method must be present to correctly implement the ActionListener interface
     */
    public void actionPerformed (ActionEvent aevt)
    {
        // get the object that was selected in the gui
        Object selected = aevt.getSource();
        
        /*
         * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
         * with just one piece of code.
         * In a real system you'll probably have one piece of action code per selectable item.
         * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
         */
        
        // if a gridsquare is selected then switch its color
        if ( selected instanceof GridSquare)
        {
            if (((GridSquare)selected).getLegality())
            {
                for (int column = ((GridSquare)selected).getx(); column < x; column ++)
                {
                    for (int row = ((GridSquare)selected).gety(); row < y; row ++)
                    {
                        gridSquares [column][row].eat();
                      }
                }
                player = 0;
                yourTurn();
                if (!gridSquares [0][1].getLegality() && !gridSquares [1][0].getLegality())
                {
                    gridSquares [0][0].uneat();
                }
                loser();
            }
        }
        
        // if resetting the squares' colours is requested then do so
        if ( selected.equals( topButton) )
        {
            for ( int column = 0; column < x; column ++)
            {
                for ( int row = 0; row < y; row ++)
                {
                    gridSquares [column][row].setColor( column + row);
                }
            }
            reset();
        }
    }
    
    private void loser()
    {
        if (!gridSquares [0][1].getLegality() && !gridSquares [1][0].getLegality())
        {
            if (player == 0) //computer's turn
            {
                topLabel.setText("Okay, you win, wanna play again?");
            }
            else
            {
                topLabel.setText("YUK! You lose!");
            }
        }
        else 
        {
            play();
        }
        getContentPane().add( topPanel, BorderLayout.NORTH);
        pack();
        setVisible(true);
    }
    
    private void yourTurn()
    {
        topLabel.setText("Computer's Turn");
        System.out.println("Give me a second, i'm buggy");
        
        getContentPane().add( topPanel, BorderLayout.NORTH);
        pack();
        setVisible(true);
        
        pause();
    }
    
    private void myTurn()
    {
        topLabel.setText("Your Turn, Human");
        
        getContentPane().add(topPanel, BorderLayout.NORTH);
        pack();
        setVisible(true);
    }
    
    private void pause()
    {
        TimeUnit time = TimeUnit.SECONDS;
        try
        {
            time.sleep(4);
        }
        catch (InterruptedException ex)
        {
        }
    }
    
    private void reset()
    {
        topLabel.setText("pick a square, a valid square");
        getContentPane().add( topPanel, BorderLayout.NORTH);
        pack();
        setVisible(true);
    }
}
