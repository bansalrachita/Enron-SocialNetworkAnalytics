/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Factory.NodeFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import network.util.Mail;


/**
 *
 * @author Rachita
 */
/**
 * Creates the edges from the mails in the network and holds the mails and the
 * edges.
 */
public class Network {

    public Set<Mail> mails_in_network = null;
    public Map<Edge, Integer> network = null;
    private static int uniqueEdges = 0;
//	public NodeFactory small_nodeFactory = null;

    public Network() {
        network = new HashMap<>();
        mails_in_network = new HashSet<>();
//		small_nodeFactory = new NodeFactory();
    }

    @Override
    public String toString() {
        return network.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return ((Network) obj).equals(network);
    }

    /**
     * Creates edges from the mails in the network and increases its count It
     * increments the in-bounds and out-bounds of the Node elements too.
     *
     */
    public synchronized void getEdgeInNetwork(Node from, Node to) {

        Edge newEdge = new Edge(from, to);
        uniqueEdges++;
        if (network.containsKey(newEdge)) {
            
            network.put(newEdge, network.get(newEdge) + 1);
        } else {
            network.put(newEdge, 1);
            from.increaseOutBound();
            to.increaseInbound();
        }
    }

    public Map<Edge, Integer> getNetwork() {
        return network;
    }

    public int size() {
        return network.size();
    }

    public int uniqueEdges() {
        return uniqueEdges;
    }

    /**
     * @returns a double 2D matrix from the edges in the network
     */
    public double[][] returnMatrixAll(NodeFactory small_nodeFactory) {
//        System.out.println("nodeFactory :: " + small_nodeFactory.size());
        double[][] realMatrix = new double[small_nodeFactory.getCount()][small_nodeFactory
                .getCount()];

        for (Entry<Edge, Integer> e : network.entrySet()) {
//            realMatrix[e.getKey().getFrom().getId()][e.getKey().getTo().getId()] = e
//                    .getValue();
            realMatrix[e.getKey().getFrom().getId()][e.getKey().getTo().getId()] = 1;
        }
        return realMatrix;
    }
    
    public Boolean[][] returnBooleanMatrix(NodeFactory small_nodeFactory) {
        Boolean[][] boolMatrix = new Boolean[small_nodeFactory.getCount()][small_nodeFactory
                .getCount()];
        
        for (int i = 0; i < boolMatrix.length; i++) {
            for (int j = 0; j < boolMatrix.length; j++) {
                
                boolMatrix[i][j] = false;
            }
        }
        for (Entry<Edge, Integer> e : network.entrySet()) {
            boolMatrix[e.getKey().getFrom().getId()][e.getKey().getTo().getId()]= e.getValue() > 0 ? true: false;
        }
        return boolMatrix;

    }
    

    /**
     * @returns an adjacency list represented by Map<Integer, List<Integer>>
     * where List<Iteger> represents the node adjacent to the Integer
     */
    public Map<Integer, List<Integer>> returnAdjList(NodeFactory small_nodeFactory) {
        Map<Integer, List<Integer>> realMatrix = new HashMap<>(small_nodeFactory.getCount());

        for (Entry<Edge, Integer> e : network.entrySet()) {
            //realMatrix[e.getKey().getFrom().getId()][e.getKey().getTo().getId()] = 1;
            if (realMatrix.containsKey(e.getKey().getFrom().getId())) {
                List<Integer> oldList = realMatrix.get(e.getKey().getFrom().getId());
                oldList.add(e.getKey().getTo().getId());
            } else {
                List<Integer> newList = new ArrayList<>();
                newList.add(e.getKey().getTo().getId());
                realMatrix.put(e.getKey().getFrom().getId(), newList);
            }
        }

        return realMatrix;

    }

    public Set<Mail> getMails_in_network() {
        return mails_in_network;
    }

    public void setMails_in_network(Set<Mail> _mails_in_network) {
        mails_in_network = _mails_in_network;
    }

    public synchronized boolean addUniqueMail(Set<Mail> newMails) {
        return mails_in_network.addAll(newMails);
    }

}
