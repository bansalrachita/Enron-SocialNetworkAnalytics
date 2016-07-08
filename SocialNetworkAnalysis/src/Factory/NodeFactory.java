/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import network.Node;

/**
 *
 *
 * @author Rachita
 */
public class NodeFactory {

    private Map<String, Node> nodeMap = new HashMap<>();
    private Map<Integer, String> IdNameMap = new HashMap<>();
    private int count = 0;

    public int getCount() {
        return count;
    }

    /**
     *
     *
     * @return a unique Node from the NodeMap which is a Map<Sting, Node> else
     * creates a new Node
     */
    public synchronized Node getNode(String address) {

        Node newNode = null;
        if (address != null) {
            newNode = nodeMap.get(address.trim());

            if (newNode == null) {
                newNode = new Node(count++, address);
                nodeMap.put(address, newNode);
                IdNameMap.put(newNode.getId(), newNode.getAddress());
            }
        }

        return newNode;
    }

    public Map<String, Node> getNodeMap() {
        return nodeMap;
    }

    public int size() {
        return nodeMap.size();
    }

    public String returnAddress(int id) {
        return IdNameMap.get(id);
    }

    public Map<Integer, String> returnIdNameMap() {
        return IdNameMap;
    }

    /**
     *
     *
     * @return ordered values in the form of String of NodeFactory based on the
     * inbounds or out-bounds comparator
     */
    public String getNodesSetStringBuffer(Comparator<Node> genericComparator) {
        StringBuilder b = new StringBuilder();
        Queue<Node> pqq = new PriorityQueue<>(genericComparator);
        pqq.addAll(nodeMap.values());

//        System.out.println("nodemap :" + nodeMap.values().size());
        b.append("[");
        while (!pqq.isEmpty()) {
            Node n1 = pqq.poll();
            b.append("< ").append(n1.getId()).append(":").append(n1.getAddress()).append(":" + "(").append(n1.getInbound()).append(", ").append(n1.getOutbound()).append(")" + " >");
            b.append(", ");
        }
        b.append("]");
        // gc here
        pqq = null;
        return b.toString();
    }
    
    public String getMessagesForUser(Node node){
        StringBuilder str = new StringBuilder();
        int count = 0;
        for(String s: node.getMessageRepo()){
            str.append(++count).append(s).append("\n");
        }
        
        return str.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("NodeFactory finalize");
    }

}
