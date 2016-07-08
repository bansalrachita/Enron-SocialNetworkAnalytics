/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Rachita
 */
/**
 *
 * represents an edge of the graph from From to To
 */
public class Edge {

    private Node to;
    private Node from;
    
    public Edge() {
        to = null;
        from = null;
    }

    public Edge(Node _from, Node _to) {
        to = _to;
        from = _from;
    }
   

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }
    
    @Override
    public String toString() {
        return from + "->" + to;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Edge && ((Edge) obj).from == from && ((Edge) obj).to == to;
    }

    @Override
    public int hashCode() {
        return to.hashCode() + from.hashCode();
    }

}
