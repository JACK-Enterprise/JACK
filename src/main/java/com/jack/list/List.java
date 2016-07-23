package com.jack.list;

// Time-stamp: <List.java   9 Oct 2002 11:07:15>

/**
 * Classe des listes
 * @author O. Carton
 * @version 1.0
 */
public class List {
    ListCell first;			// Première cellule de la liste
    /** Construction d'une liste vide */
    List () {
        first = new EmptyCell();
    }
    /** Ajout d'un élément en tête de liste */
    void put(Object o) { first = new RealCell(o, first); }
    /** Retourne si la liste est vide */
    boolean empty() { return first.empty(); }
    /** Suppression de l'élément en tête de liste */
    Object get() {
        Object o = first.value();
        first = first.next();
        return o;
    }
    /** Affichage des éléments de la liste */
    void print() { first.print(); }
}
