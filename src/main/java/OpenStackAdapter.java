/*-
 * #%L
 * Fiji distribution of ImageJ for the life sciences.
 * %%
 * Copyright (C) 2009 - 2017 Fiji developers.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.gui.StackWindow;

/*
 * Created on 08.07.2005
 */

/** This class contains static methods, which handle the "open"-specific features of 
 * OpenStackWindow: 
 * - converting a StackWindow to the appropriate class
 * - adding and removing displayChangeListener 
 * OpenStackAdapter is the implementation for the case that the Image5D plugins
 * are not installed along with SyncWindows.
 * When Image5DWindow is referenced in SyncWindow, directly, an Exception occurs, if
 * Image5D is not installed. 
 * @author Joachim Walter
 */
public class OpenStackAdapter {
    
    public static ImageWindow makeOpenWindow(ImageWindow iw) {
        ImageWindow iwOut = iw;
        if (iw instanceof StackWindow && !(iw instanceof OpenStackWindow)) {
            ImageCanvas ic = iw.getCanvas();
            ImagePlus img = iw.getImagePlus();
            double magn = ic.getMagnification();
            iwOut = new OpenStackWindow(img, ic);
            
            // set zoom to previous zoom
            ic.setMagnification(magn);
            img.repaintWindow();                
        }
        return iwOut;
    }
    
    public static boolean isOpenWindow(ImageWindow iw) {
        return (iw instanceof OpenStackWindow);
    }
    
    public static void addDisplayChangeListener(ImageWindow iw, DisplayChangeListener dcl) {
        if (iw instanceof OpenStackWindow) {
            ((OpenStackWindow)iw).addDisplayChangeListener(dcl);
        }
    }
    
    public static void removeDisplayChangeListener(ImageWindow iw, DisplayChangeListener dcl) {
        if (iw instanceof OpenStackWindow) {
            ((OpenStackWindow)iw).removeDisplayChangeListener(dcl);
        }
    }
}
