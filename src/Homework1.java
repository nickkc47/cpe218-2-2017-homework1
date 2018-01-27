/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Homework1;

/**
 *
 * @author Administrator
 */
import java.util.Stack;
import java.util.Scanner;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
 
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
 
import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;

public class Homework1 {

    
    public static Stack<Character> Stackdata = new Stack<Character>();
    public static Node datatree; 
    
    public static void main(String[] args) {
		// Begin of arguments input sample
		//String data = "251-*32*+";
                System.out.print("Input: ");
                Scanner inputdata = new Scanner(System.in);
                String number = inputdata.nextLine();
                String data = number;
                int i = 0;
                while(i<data.length())
                {
                    Stackdata.add(data.charAt(i));
                    i++;
                }
                
                datatree = new Node(Stackdata.pop());
                infix(datatree);
                System.out.print("Output: ");
                inorder(datatree);
                System.out.print("=");
                System.out.print(Calculate(datatree));
                
		// End of arguments input sample
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                TreeDemo.createAndShowGUI();
                }
                });
		// TODO: Implement your project here
	}
    
    
    
    public static void infix(Node x)
    {
        if(x.data == '+' || x.data == '-' || x.data == '*' || x.data == '/')
        {
            x.right = new Node(Stackdata.pop());
            infix(x.right);
            x.left = new Node(Stackdata.pop());
            infix(x.left);
        }
    }
    
    public static void inorder(Node y)
    {
        if(y.data == '+' || y.data == '-' || y.data == '*' || y.data == '/')
        {
            if(y!=datatree)
            {
                System.out.print("(");
            }
            inorder(y.left);
            System.out.print(y.data);
            inorder(y.right);
            if(y!=datatree)
            {
                System.out.print(")");
            }
        }
        else
        {
            if(y!=datatree)
            {
                System.out.print(y.data);
            }
        }
    }
    
    public static int Calculate(Node node)
    {
      if(node.data == '+')
      {
          return Calculate(node.left) + Calculate(node.right);
      }
      else if(node.data == '-')
      {
          return Calculate(node.left) - Calculate(node.right);
      }
      else if(node.data == '*')
      {
          return Calculate(node.left) * Calculate(node.right);
      }
      else if(node.data == '/')
      {
          return Calculate(node.left) / Calculate(node.right);
      }
      return Integer.parseInt(node.data.toString());
    }
        
}

class Node{
        public Character data;
        public Node left;
        public Node right;
        
        public Node(Character x){
            data = x;
        }
        public String toString(){
        return data.toString();
     }
    }

class TreeDemo extends JPanel
    implements TreeSelectionListener {
    private JEditorPane htmlPane;
    private JTree tree;
    private URL helpURL;
    private static boolean DEBUG = false;
 
    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";
     
    //Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = false;
 
    public TreeDemo() {
        super(new GridLayout(1,0));
 
        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode(Hw1.datatree);
        createNodes(top,Hw1.datatree);
 
        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
 
        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
 
        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
        }
 
        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);
 
        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        initHelp();
        JScrollPane htmlView = new JScrollPane(htmlPane);
 
        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);
 
        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100); 
        splitPane.setPreferredSize(new Dimension(500, 300));
        
        ImageIcon NodeI =  createImageIcon("middle.gif");
  		DefaultTreeCellRenderer rend = new DefaultTreeCellRenderer();
  		rend.setOpenIcon(NodeI);
  		rend.setClosedIcon(NodeI);
  		tree.setCellRenderer(rend);

 
        //Add the split pane to this panel.
        add(splitPane);
    }
 
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();
 
        if (node == null) return;
 
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            BookInfo book = (BookInfo)nodeInfo;
            displayURL(book.bookURL);
            if (DEBUG) {
                System.out.print(book.bookURL + ":  \n    ");
            }
        } else {
            displayURL(helpURL); 
        }
        if (DEBUG) {
            System.out.println(nodeInfo.toString());
        }
    }
 
    private class BookInfo {
        public String bookName;
        public URL bookURL;
 
        public BookInfo(String book, String filename) {
            bookName = book;
            bookURL = getClass().getResource(filename);
            if (bookURL == null) {
                System.err.println("Couldn't find file: "
                                   + filename);
            }
        }
 
        public String toString() {
            return bookName;
        }
    }
 
    private void initHelp() {
        String s = "TreeDemoHelp.html";
        helpURL = getClass().getResource(s);
        if (helpURL == null) {
            System.err.println("Couldn't open help file: " + s);
        } else if (DEBUG) {
            System.out.println("Help URL is " + helpURL);
        }
 
        displayURL(helpURL);
    }
 
    private void displayURL(URL url) {
        try {
            if (url != null) {
                htmlPane.setPage(url);
            } else { //null url
        htmlPane.setText("File Not Found");
                if (DEBUG) {
                    System.out.println("Attempted to display a null URL.");
                }
            }
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
        }
    }
 
    private void createNodes(DefaultMutableTreeNode top,Node datatree) {
        if(datatree.left != null){
                  DefaultMutableTreeNode LNode = new DefaultMutableTreeNode(datatree.left);
                  top.add(LNode);
                  createNodes(LNode,datatree.left);
            }
        if(datatree.right != null){
                  DefaultMutableTreeNode RNode = new DefaultMutableTreeNode(datatree.right);
                  top.add(RNode);
                  createNodes(RNode,datatree.right);
            }
    }
         
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    static void createAndShowGUI() {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }
 
        //Create and set up the window.
        JFrame frame = new JFrame("TreeDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new TreeDemo());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    protected static ImageIcon createImageIcon(String path) {
         java.net.URL imgURL = TreeDemo.class.getResource(path);
         if (imgURL != null) {
             return new ImageIcon(imgURL);
         } else {
           System.err.println("Couldn't find file: " + path);
           return null;
         }
     }
 
   
}

