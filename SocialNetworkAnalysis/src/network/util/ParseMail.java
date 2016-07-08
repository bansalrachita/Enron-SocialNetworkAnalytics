/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.util;

import Factory.NodeFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import network.Node;

/**
 *
 * @author Rachita
 */
public class ParseMail {

    NodeFactory nodeFactory = null;
    public static final List<String> distributionList = new ArrayList<>();

    public ParseMail() {
//        nodeFactory = _nodeFactory;

    }

    /**
     * @returns a mail object by applying Regex to the List<String>. It has the
     * logic to use 1. blacklist.properties - by which we remove mails having
     * distribution list 2. duplicates.properties - by which we map secondary
     * email address of user to primary email addresses 3. It removes duplicate
     * Bccs and Ccs. 4. It removes non enronic emails. 5. It creates a digest
     * using From, To, subject and body with the help of MD5 algorithm which
     * then helps to idenitify duplicate messages.
     */
    public Mail returnMail(List<String> content) throws NoSuchAlgorithmException {
        String subj = null;
        String body = null;
        String tempTo = null;
        List<String> cc = null;
        List<String> bcc = null;
        String from = null;
        List<String> to = null;
        String tempCc = null;
        String tempBcc = null;
        String bodyline = null;
        String[] email_pattern = new String[6];
        email_pattern[0] = "^From: [A-Za-z0-9+_.-]+@enron+(.+)$";
        email_pattern[1] = "^To: [A-Za-z0-9+_.-]+@enron(.+)$";
        email_pattern[2] = "^Subject: .*";
        email_pattern[3] = "^Cc: [A-Za-z0-9+_.-]+@enron(.+)$";
        email_pattern[4] = "^Bcc: [A-Za-z0-9+_.-]+@enron(.+)$";
        email_pattern[5] = "^Body: .*";

        for (String line : content) {
            if (null == from && Pattern.compile(email_pattern[0]).matcher(line).matches()) {
                String address = line.split(":")[1].trim();

                if (EmailRemoverUtil.blackList.contains(address)) {
                    break;
                }

                if (EmailRemoverUtil.uniqueMap.containsKey(address)) {
                    from = EmailRemoverUtil.uniqueMap.get(address);
                } 
                else {
                    from = address;
//                    break;
                }

            } else if (null == to && Pattern.compile(email_pattern[1]).matcher(line).matches()) {
                to = new ArrayList<>();
                Set<String> tos_hs = new HashSet<>();
                StringTokenizer st = new StringTokenizer(line.split(":")[1], ",");
                while (st.hasMoreTokens()) {
                    tos_hs.add(st.nextToken().trim());
                }

                for (String tos : tos_hs) {
                    if (EmailRemoverUtil.uniqueMap.containsKey(tos)) {
                        tempTo = EmailRemoverUtil.uniqueMap.get(tos);
                        to.add(tempTo);
                    } 
                    else {
                        tempTo = tos;
                    }
                }
            } else if (null == subj
                    && Pattern.compile(email_pattern[2]).matcher(line)
                    .matches()) {
                if (line.split(":")[1].trim().length() > 1) {
                    subj = line.split(":")[1].trim();
                }
            } else if (null == cc
                    && Pattern.compile(email_pattern[3]).matcher(line)
                    .matches()) {
                cc = new ArrayList<>();
                Set<String> ccs = new HashSet<>();
                StringTokenizer str = new StringTokenizer(line.split(":")[1], ",");
                while (str.hasMoreTokens()) {
                    ccs.add(str.nextToken().trim());
                }
                for (String cc_ed : ccs) {
                    if (EmailRemoverUtil.uniqueMap.containsKey(cc_ed)) {
                        tempCc = EmailRemoverUtil.uniqueMap.get(cc_ed);
                        cc.add(tempCc);
                    } 
                    else {
                        tempCc = cc_ed;
//                        continue;
                    }
                }
            } else if (null == bcc
                    && Pattern.compile(email_pattern[4]).matcher(line)
                    .matches()) {
                bcc = new ArrayList<>();
                Set<String> bccs = new HashSet<>();
                StringTokenizer str = new StringTokenizer(line.split(":")[1], ",");
                while (str.hasMoreTokens()) {
                    bccs.add(str.nextToken().trim());
                }
                for (String bcc_ed : bccs) {
                    if (EmailRemoverUtil.uniqueMap.containsKey(bcc_ed)) {
                        tempBcc = EmailRemoverUtil.uniqueMap.get(bcc_ed);
                    } 
                    else {

                        tempBcc = bcc_ed;
//                        continue;
                    }
                    if (!cc.contains(tempBcc)) {
                        bcc.add(tempBcc);
                    }
                }

            } else if (null == body && Pattern.compile(email_pattern[5]).matcher(line).matches()) {
                body = line;
            }
        }
        
        if(body == null)
            bodyline = "";
        else
            bodyline = body;
        
//        System.out.println("body : " + bodyline);
        String uniqueString = from + "##" + to + "##" + body +"";
        byte[] bytesOfMessage = uniqueString.getBytes();
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] thedigest = md.digest(bytesOfMessage);
        // return "not matched";
       
        Mail returnMail = new Mail(from, to, cc, bcc, subj, thedigest, bodyline);
        // System.out.println("return returnMail " + returnMail);
        return returnMail;
    }

    public static void main(String[] args) throws FileNotFoundException,
            IOException,
            NoSuchAlgorithmException {
        System.out.println("^From".startsWith("From"));
        ParseMail parsemail = new ParseMail();
        ParseMultiLineMail p = new ParseMultiLineMail();
        List<String> content = new ArrayList<>();
        NodeFactory f = new NodeFactory();

        System.out.println(p.returnContents(new File("C:\\enron_mail_20150507\\maildir\\allen-p\\inbox\\6_")));

        System.out.println("parsemail " + parsemail.returnMail(p.returnContents(new File("C:\\enron_mail_20150507\\maildir\\allen-p\\inbox\\6_"))));

    }
}
