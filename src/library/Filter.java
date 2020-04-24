/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author ercan
 */
public class Filter extends DocumentFilter {

    /**
     * Number of characters allowed.
     */
    private int type = 0;
    private int length = 0;

    /**
     * Restricts the number of characters can be entered by given length.
     *
     * @param type Type of text.
     * @param length Number of characters allowed.
     */
    public Filter(int type, int length) {
        this.type = type;
        this.length = length;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (this.length > 0 && fb.getDocument().getLength() + string.length() > this.length) {
            return;
        }

        if (type == 0) {
            super.insertString(fb, offset, string, attr);
        } else if (type == 1 && isNumeric(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (this.length > 0 && fb.getDocument().getLength() + text.length() > this.length) {
            return;
        }

        if (type == 0) {
            super.insertString(fb, offset, text, attrs);
        } else if (type == 1 && isNumeric(text)) {
            super.insertString(fb, offset, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int i, int i1) throws BadLocationException {
        super.remove(fb, i, i1);
    }

    /**
     * This method tests whether given text can be represented as number. This
     * method can be enhanced further for specific needs.
     *
     * @param text Input text.
     * @return {@code true} if given string can be converted to number;
     * otherwise returns {@code false}.
     */
    private boolean isNumeric(String text) {
        if (text == null || text.trim().equals("")) {
            return false;
        }
        for (int iCount = 0; iCount < text.length(); iCount++) {
            if (!Character.isDigit(text.charAt(iCount))) {
                return false;
            }
        }
        return true;
    }
}
