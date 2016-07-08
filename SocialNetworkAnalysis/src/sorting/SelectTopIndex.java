package sorting;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

public class SelectTopIndex {

	public class ElementComparator implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			//return (int) (((Element) o1).value - ((Element) o2).value) ;
			return Double.compare((Double) ((Element) o1).value, (Double) ((Element) o2).value);
		}
		
	}
	
	public int[] sortTop(int topper, Double[] array) {

		Queue<Element<Integer, Double>> top = new PriorityQueue<>();
		//SortedSet<Element> topset = new TreeSet<>();
		
		
		
		// static assigning
		for (int i = 0; i < topper; i++) {
			top.add(new Element(i, array[i]));
		}

		for (int i = topper; i < array.length; i++) {
			if (top.peek().value < array[i]) {
				top.poll();
				top.add(new Element(i, array[i]));				
			}
			//System.out.println("top m " + top + " topmost " + top.peek() + " element " + array[i]);

		}
		

		System.out.println("top " + top);
		int[] elements_array  = new int[topper];
		for(int i = topper - 1; i >= 0 ; i--){
			elements_array[i] = top.poll().index;
		}
		// gc here 
		top = null;
		return elements_array;
	}
	
	public static void main(String[] args) {
		
		Double[] a ={24.0, 0.0, 20.0, 6.0, 1.0, 6.0, 7.0, 8.0, 3.0, -4.0, -2.0 , 10.0, 11.0, 14.0, 15.0, 17.0, 30.0};
		
		SelectTopIndex s = new SelectTopIndex();
		int[] p = s.sortTop(5, a);
		System.out.println();
		for(int i = 0 ; i < 5 ; i++){
			System.out.print(p[i] +" ");
		}
		System.out.println();
		
	}
}
