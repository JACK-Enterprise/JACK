package com.jack.list;

// Time-stamp: <Fifo.java   9 Oct 2002 11:19:20>

/**
 * Interface d'une file
 * @author O. Carton
 * @version 1.0
 */
public interface Fifo {
    /** Retourne si la file est vide */
    boolean empty();
    /** Ajoute un élément en queue de file */
    void put(Object o);
    /** Retourne l'élément en tête de file */
    Object get();
}
