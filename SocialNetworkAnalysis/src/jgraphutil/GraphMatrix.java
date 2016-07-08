/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jgraphutil;

import Factory.NodeFactory;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxStylesheet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

/**
 *
 * @author Rachita
 */
public class GraphMatrix extends JApplet{

    
    
    double[][] matrix; 
    NodeFactory nodes =null;
    private static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final int WIDTH = 550;
    private JGraphXAdapter<String,DefaultEdge> jgxAdapter;
    private mxGraphComponent mxComponent ;
    
    
    
    public GraphMatrix(double[][] matrix, NodeFactory nodes) throws HeadlessException {
        this.matrix = matrix;
        this.nodes=nodes;
    }
    
   public void init()
    {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g =
            new ListenableDirectedGraph<>(
                DefaultEdge.class);

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);
        mxComponent = new mxGraphComponent(jgxAdapter);
        mxComponent.getBackground().brighter().getGreen();
        getContentPane().add(mxComponent);
        mxComponent.setAutoScroll(true);
        resize(DEFAULT_SIZE);
        mxStylesheet stylesheet = jgxAdapter.getStylesheet();
        
        Hashtable<String, Object>style = new Hashtable<>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_FONTFAMILY, "Calibri");
        style.put(mxConstants.STYLE_FILLCOLOR, "lightgreen");
        style.put(mxConstants.STYLE_FONTCOLOR, "black");
        stylesheet.putCellStyle("ROUNDED", style);
//        style.put(mxConstants.STYLE_PERIMETER,getPerimeterPoint(1000, 1000) );
           
        stylesheet.setDefaultVertexStyle(style);
        jgxAdapter.setStylesheet(stylesheet);
  
        
        Map<String, Object> edgeStyle = new HashMap<>();
        edgeStyle.put(mxConstants.STYLE_SHAPE,    mxConstants.SHAPE_CONNECTOR);
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        edgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
        edgeStyle.put(mxConstants.STYLE_LABEL_POSITION,mxConstants.EDGESTYLE_ORTHOGONAL);
       
        
        stylesheet.setDefaultEdgeStyle(edgeStyle);
//        System.out.println("nodes.getNodeMap() " + nodes.getNodesSetStringBuffer());
        // add some sample data (graph manipulated via JGraphX)
        for(int i = 0; i < matrix.length; i++){
            g.addVertex(i + " ");
            if(g.vertexSet().size() > 50)
                break;
        }
        
        
        for(int i = 0; i < g.vertexSet().size(); i++){
            for(int j = 0; j< g.vertexSet().size(); j++){
                if(matrix[i][j] > 0.0)
                    g.addEdge(i + " ", j + " ");
            }
            
        }
       
        
        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        layout.setRadius(WIDTH);
        layout.execute(jgxAdapter.getDefaultParent()); 
        
            
    }
   
    // Returns Perimeter Point for Ellipses
    public mxPoint getPerimeterPoint(double source, double p) { 
      
        return new mxPoint(source, p);

    }
   
   

   
    private void exportJPG(final String path) {
        Dimension d = mxComponent.getGraphControl().getSize();

        // For testing purposes, if no Panel exists 
        
            d.width = 50000;
            d.height = 50000;
        
        
        BufferedImage bi = mxCellRenderer.createBufferedImage(mxComponent.getGraph(), null, 1, Color.WHITE, mxComponent.isAntiAlias(), null, mxComponent.getCanvas());
            try {
                    ImageIO.write(bi, "JPG", new File(path));
		} catch (IOException e) {}
    }
    
    public final void export(final String format, final String path) {
        if (format.equals("JPG")) {
            exportJPG(path);
        } 
    }

   public void drawGraph(GraphMatrix applet){
       
        
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT of the small network");
        applet.export("JPG", "C:\\Users\\Rachita\\Documents\\workspace-sts-3.6.4.RELEASE\\SocialNetworkAnalysis\\graph.JPG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
        
       
   }
   
   
   //testing...
//    public static void main(String[] args) {
//       
////                double[][] matrix = new double[][] {
////				{ 0, 1.5, 1.5, 1.5, 2, 3 }, 
////				{ 0, 0, 0, 0, 0, 0 },
////				{ 0, 0.5, 0, 0.5, 0.5, 1 }, 
////				{ 0, 0, 0, 0, 0, 0 },
////				{ 0, 0, 0, 0, 0, 0 }, 
////				{ 0, 1.5, 1.5, 1.5, 1.5, 0 },};
//                int N = 1000;
//                double[][] matrix = new double[N][N];
//                for(int i = 0 ; i < N; i++){
//                    for(int j =0 ; j < N ; j++){
//                        matrix[i][j]= (i+1)*(j+1);
//                    }
//                }
//        GraphMatrix graphMatrix = new GraphMatrix(matrix);
//        graphMatrix.drawGraph(graphMatrix);
//        
//    }
   
}
    
