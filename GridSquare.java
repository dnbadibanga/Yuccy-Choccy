import java.awt.Color;
import javax.swing.*;
/*
 *  a simple extension of JButton which allows the background colour to be set and switched back and forth with ease
 *  
 *  there are other ways of doing this, but it's a neat way to demonstrate how to create your own gui components
 *  (as well as how to use ternary operators)
 */
public class GridSquare extends JButton
{
    private int xcoord, ycoord;         // not used in this demo, but might be helpful in future...
    private boolean legal;
    
    // constructor takes the x and y coordinates of this square
    public GridSquare( int xcoord, int ycoord)
    {
        super();
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.legal = setLegal();
    }
    
    // if the decider is even, it chooses black, otherwise white (for 'column+row' will allow a chequerboard effect)
    public void setColor( int decider)
    {
        Color colour = (int) (decider) == 0 ? Color.pink : new Color(175,50,0);
        this.setBackground( colour);
        this.setBorderPainted(true);
        this.legal = setLegal();
    }
    
    private boolean setLegal()
    {
        boolean valid = false;
        if (this.xcoord == 0 || this.ycoord == 0)
        {
            valid = true;
        }
        if (this.xcoord == 0 && this.ycoord == 0)
        {
            valid = false;
        }
        return valid;
    }
    
    public boolean getLegality()
    {
        return legal;
    }
    
    public int getx()
    {
        return xcoord;
    }
    
    public int gety()
    {
        return ycoord;
    }
    
    // if the square is uneaten, it is eaten
    public void eat()
    {
        this.setBackground(Color.white);
        this.setBorderPainted(false);
        this.legal = false;
    }
    
    public void uneat()
    {
        this.legal = true;
    }
    
    public void reset(int decider)
    {
        Color colour = (int) (decider) == 0 ? Color.pink : new Color(175,50,0);
        this.setBackground( colour);
        this.setBorderPainted(true);
        this.legal = setLegal();
    }
}
