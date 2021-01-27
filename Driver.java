import javax.swing.*;
import java.awt.event.*;


/*
 *  a simple demonstration of using swing components to make a gui
 *  and of basic event handling
 */
public class Driver extends WindowAdapter
{
    public Driver()
    {
        // create a new GUI window
        Object[] options = {"10x10", "15x15", "20x15"};
        int x = JOptionPane.showOptionDialog(null,
        "pick a size, any size!",
        "Please pick a size for the chocolate bar",
        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
        null, options, options[0]);
        
        if (x==0)
        {
            ten();
        }
        else if (x==1)
        {
            fifteen();
        }
        else if (x==2)
        {
            twenty();
        }
    }
    
    private static void ten()
    {
        YuckyChoccy demo = new YuckyChoccy(10, 10);
    }

    private static void fifteen()
    {
        YuckyChoccy demo = new YuckyChoccy(15, 15);
    }
    
    private static void twenty()
    {
        YuckyChoccy demo = new YuckyChoccy(20, 15);
    }
}
