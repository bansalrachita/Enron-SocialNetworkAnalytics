/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.util.Comparator;

/**
 *
 * @author Rachita
 */
public class NodeInboundComparator implements Comparator<Node> {

    /**
     * @returns an integer after comparing the in bounds of two Node elements
     */
    @Override
    public int compare(Node o1, Node o2) {
        return (o2).getInbound() - (o1).getInbound();
    }
}
