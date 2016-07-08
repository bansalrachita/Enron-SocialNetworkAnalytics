/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Factory.NodeFactory;
import graph.algorithms.BetweenessAlgorithm;
import graph.algorithms.ClosenessCentrality;
import graph.algorithms.EigenUtil;
import graph.algorithms.Farness;
import graph.algorithms.FloydWarshall;
import graph.algorithms.Transitivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import jgraphutil.GraphMatrix;
import network.sentiment.ProcessString;
import network.util.Mail;
import network.util.ParseMail;
import network.util.ParseMultiLineMail;
import network.util.ReadSpecificFolders;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;
import org.jblas.DoubleMatrix;
import org.jblas.Eigen;
import sorting.Element;
import sorting.SelectTopIndex;

public class NodeNetwork {

    private Network networkObj;
    private NodeFactory nodeFactory;
//    private static final String FILENAME = "DBFile.db4o";
//    private static ObjectContainer db = null; 
//    private int count=0;         

    public NodeNetwork() {
        networkObj = new Network();
        nodeFactory = new NodeFactory();
    }

    /**
     *
     *
     * represents a unit of Thread which runs by taking a folder as input and
     * adds mails into a unique set of mails.
     */
    private class ProcessDir implements Runnable {

        private final String dirStart;
        private NodeNetwork nodenet;
        private ParseMultiLineMail parseObj = new ParseMultiLineMail();
        private ReadSpecificFolders reader = new ReadSpecificFolders();
        private ParseMail mailparser = null;
        private Set<Mail> mailFolderSet = new HashSet<>();

        public ProcessDir(NodeNetwork _nodenet, String id) {
            this.dirStart = id;
            nodenet = _nodenet;
            mailparser = new ParseMail();
        }

        @Override
        public void run() {
            long threadId = Thread.currentThread().getId();
//            System.out.println("count :" + ++count);
            System.out.println("Start processing request " + dirStart
                    + " by thread " + threadId);
            try {
                for (File f : reader.getFiles(dirStart)) {
                    List<String> content = parseObj.returnContents(f);
                    Mail newMail = mailparser.returnMail(content);

                    if (newMail != null && newMail.getFrom() != null
                            && newMail.getTo() != null
                            && !newMail.getTo().isEmpty()) {
                        //adding in the local Map
                        mailFolderSet.add(newMail);
                    }
                }

                //adding in the unique Map
                nodenet.networkObj.addUniqueMail(mailFolderSet);

            } catch (InterruptedException ex) {
            } catch (IOException e) {
            } catch (Exception e) {
            }

            System.out.println("Finish processing request " + dirStart
                    + " by thread " + threadId);
        }
    }

    public static void main(String[] args) throws Exception {
        long starttime = System.currentTimeMillis();
        NodeNetwork mainNodeNetwork = new NodeNetwork();
        int topper = 100;

        PrintWriter out = new PrintWriter(new FileWriter("networkoutput.txt",
                true), true);

        PrintWriter out_messages = new PrintWriter(new FileWriter("messagesClosest.txt",
                true), true);
        
        PrintWriter out_mat = new PrintWriter(new FileWriter("matrix.txt",
                true), true);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                100, // core pool size
                100, // max pool size
                100L, TimeUnit.MILLISECONDS, // keep alive
                new LinkedBlockingQueue<>(50), // queue
                new ThreadPoolExecutor.CallerRunsPolicy()); // handler

        ReadSpecificFolders r = new ReadSpecificFolders();
        List<File> f = r.getInitialNamedFolders("C:\\Users\\Rachita\\Google Drive\\Algos\\maildir1");
//        int count = 0;
        for (File f1 : f) {
            threadPoolExecutor.submit(mainNodeNetwork.new ProcessDir(
                    mainNodeNetwork, f1.toString()));
//            count++;
//            if (count > 1) {
//                break;
//            }
        }

        // Tell threads to finish off.
        threadPoolExecutor.shutdown();

        // Wait for everything to finish.
        while (!threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Awaiting completion of threads. Active# "
                    + threadPoolExecutor.getActiveCount() + " mails# : "
                    + mainNodeNetwork.networkObj.getMails_in_network().size()
                    + " timepassed " + (System.currentTimeMillis() - starttime)
                    / 60000 + "mins");
        }

//        System.out.println("mails in network  :: --->"
//                + mainNodeNetwork.networkObj.getMails_in_network().size());
//
//		System.out.println("saving networkmails to db40");
//		try {
//			db = Db4o.openFile("networkmails.db40");
//			for (Mail m : mainNodeNetwork.networkObj.getMails_in_network())
//				db.store(m);
//			db.commit();
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			if (db != null) {
//				db.commit();
//				db.close();
//			}
//		}
        // processing mails for creating nodes and edges
        
        for (Mail mail : mainNodeNetwork.networkObj.getMails_in_network()) {

            Node _from = mainNodeNetwork.nodeFactory.getNode(mail.getFrom());
            _from.getMessageRepo().add(mail.getMessage());
            for (String to : mail.getTo()) {
                Node _to = mainNodeNetwork.nodeFactory.getNode(to);
                mainNodeNetwork.networkObj.getEdgeInNetwork(_from, _to);
            }

            if (mail.getCc() != null) {
                for (String cc_ed : mail.getCc()) {
                    Node _cc_ed = mainNodeNetwork.nodeFactory.getNode(cc_ed);
                    mainNodeNetwork.networkObj.getEdgeInNetwork(_from, _cc_ed);
                }
            }

            if (mail.getBcc() != null) {
                for (String bcc_ed : mail.getBcc()) {
                    Node _bcc_ed = mainNodeNetwork.nodeFactory.getNode(bcc_ed);
                    mainNodeNetwork.networkObj.getEdgeInNetwork(_from, _bcc_ed);
                }
            }
            System.out.println(" mail [ " + mail + " ]");
        }
        
        
        //non directional graph
      /*  for (Mail mail : mainNodeNetwork.networkObj.getMails_in_network()) {

            Node _from = mainNodeNetwork.nodeFactory.getNode(mail.getFrom());
   
            for (String to : mail.getTo()) {
                Node _to = mainNodeNetwork.nodeFactory.getNode(to);
                mainNodeNetwork.networkObj.getEdgeInNetwork(_from, _to);
                mainNodeNetwork.networkObj.getEdgeInNetwork(_to, _from);
            }

            if (mail.getCc() != null) {
                for (String cc_ed : mail.getCc()) {
                    Node _cc_ed = mainNodeNetwork.nodeFactory.getNode(cc_ed);
                    mainNodeNetwork.networkObj.getEdgeInNetwork(_from, _cc_ed);
                    mainNodeNetwork.networkObj.getEdgeInNetwork(_cc_ed, _from);
                }
            }

            if (mail.getBcc() != null) {
                for (String bcc_ed : mail.getBcc()) {
                    Node _bcc_ed = mainNodeNetwork.nodeFactory.getNode(bcc_ed);
                    mainNodeNetwork.networkObj.getEdgeInNetwork(_from, _bcc_ed);
                    mainNodeNetwork.networkObj.getEdgeInNetwork(_bcc_ed, _from);
                }
            }
            System.out.println(" mail [ " + mail + " ]");
        }*/

        long midtime = (System.currentTimeMillis());
        out.write("Mails#: " + mainNodeNetwork.networkObj.getMails_in_network().size());
        out.println();

        out.write("All Nodes: " + mainNodeNetwork.nodeFactory.getNodesSetStringBuffer(new NodeOutBoundComparator()));
        out.println();

        out.write("usersIds: " + mainNodeNetwork.nodeFactory.returnIdNameMap());
        out.println();
        
        out.write("OutBound Degree Centrality: "
                + mainNodeNetwork.nodeFactory.getNodesSetStringBuffer(new NodeOutBoundComparator()));
        out.println();

        out.write("InBound Degree Centrality: "
                + mainNodeNetwork.nodeFactory.getNodesSetStringBuffer(new NodeInboundComparator()));
        out.println();

        out.write("Nodes# " + mainNodeNetwork.nodeFactory.size());
        out.println();

        out.write("Edges# " + mainNodeNetwork.networkObj.uniqueEdges());
        out.println();

        out.write("Timetaken to create edges : " + ((midtime - starttime) / 60000) + " mins");
        out.println();

        out.write("Network: " + mainNodeNetwork.networkObj);
        out.println();

        mainNodeNetwork.networkObj.mails_in_network = null;

        midtime = (System.currentTimeMillis());
        double[][] networkMatrix = mainNodeNetwork.networkObj.returnMatrixAll(mainNodeNetwork.nodeFactory);

        double[][] floydwarshallmatrix = null;

        FloydWarshall floydwarshall = new FloydWarshall(networkMatrix.length);
        floydwarshallmatrix = floydwarshall.runFloydwarshall(networkMatrix);
        out.write("Timetaken to run floydwarshal : " + ((midtime - starttime) / 60000) + " mins");
        out.println();

        ClosenessCentrality ccentrality = new ClosenessCentrality();
        List<Element<String, Double>> closenesscentral = ccentrality.calculateClosenessCentrality(mainNodeNetwork.nodeFactory, floydwarshallmatrix);
        out.write("Closenesscentrality :: ----> " + closenesscentral);
        out.println();

        for (int i = 0; i < networkMatrix.length; i++) {
            for (int j = 0; j < networkMatrix[0].length; j++) {
                out_mat.write(networkMatrix[i][j] + " ");
            }
            out_mat.println();
        }
        out_mat.println();
        
        Node mostInfluencialPerson = mainNodeNetwork.nodeFactory.getNode(closenesscentral.get(0).index);
        
        out_messages.println("messages directory of closest person----> " + "\n" + mainNodeNetwork.nodeFactory.getMessagesForUser(mostInfluencialPerson));
        out_messages.println();
        
        //sentiment analysis on closest degree
        ProcessString proc = new ProcessString();
	String all = new String();
	BufferedReader reader;
	reader = new BufferedReader(new FileReader("C:\\Users\\Rachita\\Documents\\workspace-sts-3.6.4.RELEASE\\SocialNetworkAnalysis\\messagesClosest.txt"));

		String line = "";

		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line.length() == 0) {
				continue;
			}
			all += line;
		}
		reader.close();
		System.out.println(proc.returnSpecialPostings(all));
        
        //GC here...
        closenesscentral = null;

        //calculate farness
        Farness fceCentrality = new Farness();
        List<Element<String, Double>> farnesscentral = fceCentrality.calculateFarnessCentrality(mainNodeNetwork.nodeFactory, floydwarshallmatrix);

        out.write("Farness Centrality :: ----> " + farnesscentral);
        out.println();
        //gc here
        farnesscentral = null;

        //calculate betweenness
        midtime = System.currentTimeMillis();
        BetweenessAlgorithm betweenessAlgorithm = new BetweenessAlgorithm();
        List<Element<String, Double>> beElements = betweenessAlgorithm.calculateBetweeness(mainNodeNetwork.nodeFactory, networkMatrix);
        out.write("Betweenness Centrality :: ----> " + beElements);
        out.println();
        out.write("Timetaken to create edges : " + ((midtime - starttime) / 60000) + " mins");
        out.println();

        //GC here...
        betweenessAlgorithm = null;

        // calculate transitivity
        Transitivity transitivity = new Transitivity(); 
        Boolean[][] boolmatrix = transitivity.tClosure(mainNodeNetwork.networkObj.returnBooleanMatrix(mainNodeNetwork.nodeFactory));
        transitivity.print(boolmatrix);
        transitivity.printclusters(boolmatrix);
        
        //gc here...
        transitivity = null;
//		System.out.println("saving networkmatrix to db40");
//		try {
//			db = Db4o.openFile("networkmatrix.db40");
//			db.store(networkMatrix);
//			db.commit();
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			if (db != null) {
//				db.commit();
//				db.close();
//			}
//		}
// working eigen values
        out.write("Calling DoubleMatrix Constr" + ((System.currentTimeMillis() - starttime) / 60000) + "m");
        out.println();
        DoubleMatrix matrix = new DoubleMatrix(networkMatrix);
        out.write("Ending DoubleMatrix Constr" + ((System.currentTimeMillis() - starttime) / 60000) + "m");
        out.println();

        out.write("Calling DoubleMatrix Constr" + +((System.currentTimeMillis() - starttime) / 60000) + "m");
        out.println();
        ComplexDoubleMatrix eigenvalues = Eigen.eigenvalues(matrix);
        out.write("Calling DoubleMatrix Constr" + +((System.currentTimeMillis() - starttime) / 60000) + "m");
        out.println();

        for (ComplexDouble eigenvalue : eigenvalues.toArray()) {
            System.out.print("eigen values  :: " + String.format("%.2f ", eigenvalue.abs()));
        }
        System.out.println();
        List<Double> principalEigenvector = EigenUtil
                .getPrincipalEigenvector(matrix);
        System.out.println("principalEigenvector = " + principalEigenvector);

        out.write("principalEigenvector " + principalEigenvector);
        out.println();

        List<Double> normalisedPrincipalEigenvector = EigenUtil
                .normalised(principalEigenvector);
        System.out.println("normalisedPrincipalEigenvector = "
                + normalisedPrincipalEigenvector);

        out.write("normalisedPrincipalEigenvector "
                + normalisedPrincipalEigenvector);
        out.println();

        SelectTopIndex s = new SelectTopIndex();
        Double[] d = new Double[topper];
        int[] p = s.sortTop(topper, principalEigenvector.toArray(d));
        System.out.println();
        out.write("Eigenvector Centrality :: ---->" );
        for (int i = 0; i < topper; i++) {
            out.write( mainNodeNetwork.nodeFactory.returnAddress(p[i]) + " ");
            System.out.print(mainNodeNetwork.nodeFactory.returnAddress(p[i])
                    + ", ");
        }
        out.println();

        System.out.println("visualizing graph---->");
        GraphMatrix graphMatrix = new GraphMatrix(networkMatrix, mainNodeNetwork.nodeFactory);
        graphMatrix.drawGraph(graphMatrix);
    }
}
