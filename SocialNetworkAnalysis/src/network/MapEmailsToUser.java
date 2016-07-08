/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rachita
 */
public class MapEmailsToUser {
    	List<String> lst_names = new ArrayList<String>();
	Map<String, String> unique_names = new HashMap<String, String>();
	String suffix = "@enron.com";

	public void readFile() throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(
				"C:\\Users\\Rachita\\Documents\\workspace-sts-3.6.4.RELEASE\\SocialNetworkAnalysis\\src\\network\\util\\emails.txt"));
		String line = "";
		while ((line = r.readLine()) != null) {
			lst_names.addAll(Arrays.asList(line.split(",")));
		}
		
		lst_names = null;
		lst_names.add("kaminski.vincent@enron.com");
		lst_names.add("kaminski.vincent@enron.com");
		List<String> single_names = new ArrayList<String>();
		String new_name = null;
		for (String name : lst_names) {
			name = name.replace(suffix, "");
			new_name = check_single_names(name, single_names);

			if (new_name == null) {
				unique_names.put(name + suffix, name + suffix);
				single_names.add(name);
			} else {
				unique_names.put(name + suffix, new_name + suffix);
			}
			//System.out.println("unq  " + unique_names );
		}

	}

	public String check_single_names(String name, List<String> single_names) {
		String[] two_parts = new String[2];
		String single_parts_1 = "";
		String single_parts_2 = "";
		//System.out.println("single names" + single_names);
		if (name.length() - name.replace(".", "").length() > 2
				|| name.length() - name.replace("-", "").length() > 2)
			return null;// do something

		two_parts = name.contains(".") ? name.split("\\.") : name.split("\\-");

		for (String single : single_names) {
			//System.out.println("single " + single);
			if (two_parts != null && single.contains("\\.")) {
				single_parts_1 = single.split("\\.")[0];
				single_parts_2 = single.split("\\.")[1];
				
				if(two_parts[0] == single_parts_1 || two_parts[0] == single_parts_2
						&& two_parts[1] == single_parts_1 || two_parts[1] == single_parts_2){
					return single;
				}
				
			} else if (two_parts != null && single.contains("-")) {
				single_parts_1 = single.split("\\-")[0];
				single_parts_2 = single.split("\\-")[1];
				
				if(two_parts[0] == single_parts_1 || two_parts[0] == single_parts_2
						&& two_parts[1] == single_parts_1 || two_parts[1] == single_parts_2){
					return single;
				}
				
			} else {
				break;
			}

		}

		return null;
	}

	public static void main(String[] args) throws IOException {
		MapEmailsToUser r = new MapEmailsToUser();
		//System.out.println("**Single " + "Kay.mann".split("\\.")[0]);
		r.readFile();
		System.out.println(r.lst_names);
		System.out.println(" size " + r.lst_names.size());
		
		
		FileWriter fw = new FileWriter(new File("op").getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for(Map.Entry<String, String> entry : r.unique_names.entrySet()){
			//System.out.println(entry);
			bw.write(entry.toString());
			bw.newLine();
			
		
		}
		bw.close();
	}
}
