/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.DefaultListModel;

/**
 *
 * @author PikeMobile
 */
public class ModelAdapter extends DefaultListModel{
    
    public ModelAdapter(ArrayList<?> list)
    {
        ListIterator it = list.listIterator();
        while(it.hasNext())
        {
            this.addElement(it.next().toString());
        }
    }
}
