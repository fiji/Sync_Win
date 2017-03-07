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
import sc.fiji.i5d.Image5D;
import sc.fiji.i5d.gui.Image5DCanvas;
import sc.fiji.i5d.gui.Image5DWindow;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.gui.StackWindow;

/*
 * Created on 08.07.2005
 */

/** This class contains static methods, which handle the "open"-specific features of 
 * OpenImage5DWindow and OpenStackWindow: 
 * - converting a StackWindow to the appropriate class
 * - adding and removing displayChangeListener 
 * OpenImage5DAdapter is the implementation for the case that the Image5D plugins
 * are installed along with SyncWindows. 
 * When Image5DWindow is referenced in SyncWindows, directly, an Exception occurs, if
 * Image5D is not installed.  
 * @author Joachim Walter
 */
public class OpenImage5DAdapter {
    
    public static ImageWindow makeOpenWindow(ImageWindow iw) {
        ImageWindow iwOut = iw;
        if (iw instanceof StackWindow && !(iw instanceof Image5DWindow) && 
                !(iw instanceof OpenStackWindow)) {
            ImageCanvas ic = iw.getCanvas();
            ImagePlus img = iw.getImagePlus();
            img.setOpenAsHyperStack(true); // as required by IJ 1.46d or newer, now requires IJ 1.39g or newer
            double magn = ic.getMagnification();
            iwOut = new OpenStackWindow(img, ic);
            
            // set zoom to previous zoom
            ic.setMagnification(magn);
            img.repaintWindow();     
            
        } else if (iw instanceof Image5DWindow && !(iw instanceof OpenImage5DWindow)) {
            ImageCanvas ic = iw.getCanvas();
            ImagePlus img = iw.getImagePlus();
            double magn = ic.getMagnification();
            iwOut = new OpenImage5DWindow((Image5D)img, (Image5DCanvas)ic);
            
            // set zoom to previous zoom
            ic.setMagnification(magn);
            img.repaintWindow();   
        }
        return iwOut;
    }
    
    public static boolean isOpenWindow(ImageWindow iw) {
        return ((iw instanceof OpenStackWindow) | (iw instanceof OpenImage5DWindow));
    }
    
    public static boolean isImage5DWindow(ImageWindow iw) {
	return iw instanceof OpenImage5DWindow;
    }

    public static void addDisplayChangeListener(ImageWindow iw, DisplayChangeListener dcl) {
        if (iw instanceof OpenStackWindow) {
            ((OpenStackWindow)iw).addDisplayChangeListener(dcl);
        } else if (iw instanceof OpenImage5DWindow) {
            ((OpenImage5DWindow)iw).addDisplayChangeListener(dcl);
        }
    }
    
    public static void removeDisplayChangeListener(ImageWindow iw, DisplayChangeListener dcl) {
        if (iw instanceof OpenStackWindow) {
            ((OpenStackWindow)iw).removeDisplayChangeListener(dcl);
        } else if (iw instanceof OpenImage5DWindow) {
            ((OpenImage5DWindow)iw).removeDisplayChangeListener(dcl);
        }
    }
    
    public static void setChannel(ImagePlus imp, int value) {
        if (imp instanceof Image5D) {
            Image5D i5d = (Image5D) imp;
            i5d.setCurrentPosition(2, value-1);
        }  
    }
    
    public static void setFrame(ImagePlus imp, int value) {
        if (imp instanceof Image5D) {
            Image5D i5d = (Image5D) imp;
            i5d.setCurrentPosition(4, value-1);
        }  
    }
    
}
