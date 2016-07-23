package com.jack.list;

// Time-stamp: <RealCell.java   9 Oct 2002 11:06:20>

/**
 * Classe des cellules de listes
 * @author O. Carton
 * @version 1.0
 */
public class RealCell implements ListCell {
    Object value;		// Valeur contenue dans la cellule
    ListCell next;			// Cellule suivante
    /** Construction d'une cellule */
    RealCell(Object value, ListCell next) {
        this.value = value;
        this.next = next;
    }
    /** Retourne la valeur d'une cellule */
    public Object value() { return value; }
    /** Retourne la cellule suivante */
    public ListCell next() { return next; }
    /** Retourne que la liste n'est pas vide */
    public boolean empty() { return false; }
    /** Affichage de la valeur de la cellules et des suivantes */
    public void print() {
        System.out.println(value);
        next.print();
    }
}
