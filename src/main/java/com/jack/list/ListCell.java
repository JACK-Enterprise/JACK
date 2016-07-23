package com.jack.list;

// Time-stamp: <ListCell.java   9 Oct 2002 11:05:27>

/**
 * Interface des cellules de listes
 * @author O. Carton
 * @version 1.0
 */
public interface ListCell  {
    Object value();
    ListCell next();
    boolean empty();
    void print();
}
