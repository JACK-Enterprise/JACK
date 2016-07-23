package com.jack.list;

// Time-stamp: <EmptyCell.java   9 Oct 2002 11:06:32>

import java.util.NoSuchElementException;

/**
 * Classe de la cellule vide en queue de liste
 * @author O. Carton
 * @version 1.0
 */
public class EmptyCell implements ListCell {
    public void print() {}
    public boolean empty() { return true; }
    public Object value() { throw new NoSuchElementException(); }
    public ListCell next() { throw new NoSuchElementException(); }
}
