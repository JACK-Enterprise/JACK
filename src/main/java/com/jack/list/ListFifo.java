package com.jack.list;

// Time-stamp: <ListFifo.java   9 Oct 2002 11:30:35>

/**
 * Implémentation d'une file avec une liste
 * @author O. Carton
 * @version 1.0
 */
public class ListFifo implements Fifo {
    private ListCell first;	// Première cellule de la liste
    private ListCell last;	// Dernière cellule de la liste
    /** Création d'une pile vide */
    public ListFifo() { first = last = new EmptyCell(); }
    /** Retourne si la file est vide */
    public boolean empty() { return  first.empty(); }
    /** Ajoute un élément en queue de file */
    public void put(Object o) {
        if (empty()) {
            first = last = new RealCell(o, last);
        } else {
            ((RealCell) last).next = last = new RealCell(o, last.next());
        }
    }
    /** Retourne l'élément en tête de file */
    public Object get() {
        Object o = first.value();
        first = first.next();
        return o;
    }
}