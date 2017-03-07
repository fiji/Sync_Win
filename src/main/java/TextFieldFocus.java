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
import java.awt.AWTEvent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/* -------------------------------------------------------------------------
/*
/* CLASS TextFieldFocus
/*
/* ------------------------------------------------------------------------- */

/** TextField that sends an ActionEvent when the integer Value has changed
 * either by hitting return (as the normal AWT TextField) or by
 * loss of Mouse Focus (new).*/

public class TextFieldFocus extends TextField implements FocusListener {

	private String currentText;
	private String actionCommand;

/* Constructors */
	public TextFieldFocus() {
		this("", "TextFieldFocus text changed");
	}

	public TextFieldFocus(int columns) {
		this("", "TextFieldFocus text changed");
		setColumns(columns);
	}

	public TextFieldFocus(String text, int columns) {
		this(text, "TextFieldFocus text changed");
		setColumns(columns);
	}

	public TextFieldFocus(String text, int columns, String actionCommand) {
		this(text, actionCommand);
		setColumns(columns);
	}

	public TextFieldFocus(String text, String actionCommand) {
		super(text);
		currentText = text;
		enableEvents(AWTEvent.ACTION_EVENT_MASK);
		addFocusListener(this);
		this.actionCommand = actionCommand;
	}

/* End of Constructors */

	public void setText(String text) {
		super.setText(text);
	}
	
/** Sets the command string returned by an ActionEvent. */
	public void setActionCommand(String newCommand) {
		actionCommand = newCommand;
	}
	
/** Returns the action command string. */
	public String getActionCommand() {
		return actionCommand;
	}

/* Hand on an ActionEvent to listeners only if a new Text has been entered. */
	protected void processActionEvent(ActionEvent e) {
		String tempText = getText();

		if (e.getSource() == this && !tempText.equals((Object)currentText)) {
			currentText = tempText;
			super.processActionEvent(new ActionEvent(this, e.getID(), actionCommand, e.getModifiers()));
		}
	}

/* If mouse focus is lost, trigger by the processActionEvent method. */
	public void focusLost(FocusEvent e) {
		if (e.getSource() == this) {
			processActionEvent(new ActionEvent(this, ActionEvent.ACTION_FIRST, actionCommand));
		}
	}

	public void focusGained(FocusEvent e) {
	}
}
