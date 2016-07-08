package sorting;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import network.Node;

public class SelectTopNode {
	
	
	public List<Node> returnTopNode(Collection<Node> nodes, int maxvalue) {

		Queue<Node> pqq = new PriorityQueue(nodes);
		List<Node> orderedList = new LinkedList<>();
		while(!pqq.isEmpty() && maxvalue > 0){
			maxvalue--;
			orderedList.add(pqq.poll());
		}
		pqq = null;
		return orderedList;
	}
	
		
	public static void main(String[] args) {
		SelectTopNode tp = new SelectTopNode();
		Map<String, Node> nodes = new HashMap<>();
		
		Node n1 = new Node(1, "one");
		n1.increaseOutBound();
		Node n2 = new Node(2, "two");
		n2.increaseOutBound();n2.increaseOutBound();
		Node n3 = new Node(3, "three");
		n3.increaseOutBound();n3.increaseOutBound();n3.increaseOutBound();
		Node n5 = new Node(5, "five");
		n5.increaseOutBound();n5.increaseOutBound();n5.increaseOutBound();n5.increaseOutBound();n5.increaseOutBound();
		nodes.put("1", n1);
		nodes.put("2", n2);
		nodes.put("3", n3);
		nodes.put("4", n5);
		
		List<Node> ret = tp.returnTopNode(nodes.values(), 3);
		System.out.println(ret);
	}

}
