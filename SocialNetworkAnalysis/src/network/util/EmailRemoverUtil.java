/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rachita
 */
public class EmailRemoverUtil {

    static List<String> blackList = new ArrayList<>();
    static Map<String, String> uniqueMap = new HashMap<>();
    static BufferedReader bufferedReader;

    static {
        if (blackList.isEmpty() || uniqueMap.isEmpty()) {
            try {
                populateTwoLists("C:\\Users\\Rachita\\Documents\\workspace-sts-3.6.4.RELEASE\\SocialNetworkAnalysis\\src\\network\\util\\blacklist.properties", false);
                populateTwoLists("C:\\Users\\Rachita\\Documents\\workspace-sts-3.6.4.RELEASE\\SocialNetworkAnalysis\\src\\network\\util\\duplicates.properties", true);
            } catch (IOException ex) {
                Logger.getLogger(EmailRemoverUtil.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Populates blacklist and uniqueMap.
     * blacklist is a ',' separated list of String which are email addresses 
     * belonging to either groups or distribution lists.
     * 
     * uniqueMap contains mapping of secondary and primary email addresses of a 
     * user.
     */
    public static void populateTwoLists(String filename, boolean flag) throws FileNotFoundException, IOException {
        bufferedReader = new BufferedReader(new FileReader(filename));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();
            if (line.length() == 0) {
                continue;
            }
            if (line.startsWith("#")) {
                continue;
            }
            if (flag) {
                uniqueMap.put(line.split("=")[0], line.split("=")[1]);
            } else {
                blackList.addAll(Arrays.asList(line.split(",")));
            }
        }

        bufferedReader.close();
    }

    public static void main(String[] args) throws IOException {
        String email = "40enron@enron.com";
        if (EmailRemoverUtil.blackList.contains(email)) {
            System.out.println("hi you are there!");
        }

        String address = "plove@enron.com";
        if (EmailRemoverUtil.uniqueMap.containsKey(address)) {
            System.out.println("map size ::" + EmailRemoverUtil.uniqueMap.size());
        }
        System.err.println(EmailRemoverUtil.uniqueMap.get(address));
        System.out.println(EmailRemoverUtil.uniqueMap);
        System.out.println("blacklist" + EmailRemoverUtil.blackList.size());
        System.out.println(EmailRemoverUtil.blackList);

    }

}
