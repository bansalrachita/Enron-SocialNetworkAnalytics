package network;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rachita
 */
/**
 * represents a person with its inbound and out bounds and email address along
 * with a unique Id assigned
 */
public class Node implements Comparable {

    private String address;
    private int id;
    private int inBound;
    private int outBound;
    private List<String> messageRepo = new ArrayList<>();
    
    public Node() {
        id = 0;
        address = "";
        messageRepo = null;
    }

    public Node(int _id, String _address, String message) {
        this.id = _id;
        this.address = _address;
        messageRepo.add(message);
    }
    
    public Node(int _id, String _address) {
        this.id = _id;
        this.address = _address;
    }

    public Node(String address, int id) {
        this.address = address;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getMessageRepo() {
        return messageRepo;
    }

    public void setMessageRepo(List<String> messageRepo) {
        this.messageRepo = messageRepo;
    }

    
    @Override
    public boolean equals(Object obj) {

        return ((Node) obj).id == id;
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public String toString() {
        return "<" + id + ":" + address + ">";

    }

    public String specialToString() {

        return "< " + id + ":" + address + ":" + "(" + inBound + ", " + outBound + ")" + " >";
    }

    public int getInbound() {
        return inBound;
    }

    public void increaseInbound() {
        inBound++;
    }

    public void increaseOutBound() {
        outBound++;
    }

    public void setInbound(int inbound) {
        this.inBound = inbound;
    }

    public int getOutbound() {
        return outBound;
    }

    public void setOutbound(int outbound) {
        this.outBound = outbound;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Object o) {

        return ((Node) o).outBound - outBound;

    }
    
     

}
