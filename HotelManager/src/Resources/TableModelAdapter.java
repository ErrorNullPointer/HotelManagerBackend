/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PikeMobile
 */
public class TableModelAdapter extends DefaultTableModel{
    
    public TableModelAdapter(ArrayList<?> list)
    {
        ListIterator it = list.listIterator();
        while(it.hasNext())
        {
            //this.ad
            //this.addRow(it.next());
        }
    }
}
