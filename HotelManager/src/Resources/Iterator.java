/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;
import java.lang.IndexOutOfBoundsException;
/**
 *
 * @author PikeMobile
 */
public interface Iterator {
    public boolean hasNext();
    public Object next() throws IndexOutOfBoundsException;
}
