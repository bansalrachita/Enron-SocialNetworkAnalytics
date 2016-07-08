/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db4outil;
import java.util.Set;

import network.util.Mail;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ta.TransparentPersistenceSupport;

/**
 *
 * @author Rachita
 */
public class Db4oUtil {
//    private static final String FILENAME = "network.db401";
//    private static Db4oUtil db4oUtil;
    
//    public synchronized static Db4oUtil getInstance(){
//        if(db4oUtil==null){
//            db4oUtil = new Db4oUtil();
//        }
//        return db4oUtil;
//    }
    
    public synchronized static void shutDown(ObjectContainer connection){
        if(connection!=null){
            connection.close();
        }
    }
    
    private ObjectContainer createConnection(String FILENAME){
        ObjectContainer dataBase =null;
        try{
            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
            config.common().add(new TransparentPersistenceSupport());
            //Controls the number of objects in memory
            config.common().activationDepth(Integer.MAX_VALUE);
            //Controls the depth/level of updation of Object
            config.common().updateDepth(Integer.MAX_VALUE);
            //Register your top most Class here
            config.common().objectClass(Mail.class).cascadeOnUpdate(true);
            // Change to the object you want to save
            ObjectContainer db = Db4oEmbedded.openFile(config, FILENAME);
            dataBase = db;
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
        System.out.println("db " + dataBase);
        return dataBase;
    }
    
//    public synchronized void storeSystem(Mail business){
//        ObjectContainer connection = createConnection();
//        connection.store(business);
//        connection.commit();
//        //connection.close();
//    }
    
    public  ObjectSet<Mail> retrieveSystem(Class clazz, String FILENAME){
        ObjectContainer connection = createConnection(FILENAME);
        ObjectSet<Mail> business = connection.query(clazz);
        //ObjectSet<Set> b = business;
        ObjectSet<Mail> b = business;
//        if (business.size() == 0){
//            //b = ConfigureBusiness.configure();  
//            // If there's no System in the record, create a new one
//        }
//        else{
//           //b = business.get(business.size()-1);
//            // b = business.get(0);
//        }
      
        //  connection.close();
        return b;
        
    }
}
