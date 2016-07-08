/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.util;

import java.math.BigInteger;
import java.util.List;
import network.Node;

/**
 *
 * @author Rachita
 */

/**
 * represents a Mail Object
 */
public class Mail {

    private List<String> to;
    private String from;
    private String subject;
    public byte[] md5;
    String hash = null;
    private List<String> cc;
    private List<String> bcc;
    private String message;
    /*
     we can add more elements of the mail here
     not parsing mail chain
     */

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public byte[] getMd5() {
        return md5;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public void setMd5(byte[] md5) {
        this.md5 = md5;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
    

    public Mail(String _from, List<String> _to, List<String> _cc, List<String> _bcc, String _subject, byte[] _md5, String _message) {
        to = _to;
        from = _from;
        cc = _cc;
        bcc = _bcc;
        subject = _subject;
        md5 = _md5;
        message = _message;
    }

    // over ride toStirng hashcode and equals

    @Override
    public String toString() {

        return "Subj: " + subject + " # " + "From: " + from + " # " + "To: " + to + " # " + "Cc: " + cc + " # " + "Bcc: " + bcc;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Mail) obj).hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        if (hash == null) {

//			byte[] digest = md5;
//
//			for (int i = 0; i < digest.length; i++) {
//				String hex = Integer.toHexString(digest[i]);
//				if (hex.length() == 1)
//					hex = "0" + hex;
//				hex = hex.substring(hex.length() - 2);
//				hash += hex;
//			}
            BigInteger bi = new BigInteger(1, md5);
            hash = bi.toString(16);
        }

        return hash.hashCode();
    }

}
