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
public class Homework1 {

    
    static Stack<Character> Stackdata = new Stack<Character>();
    public static Node datatree; 
    
    public static void main(String[] args) {
		// Begin of arguments input sample
		String data = "251-*32*+";
                int i = 0;
                while(i<data.length())
                {
                    Stackdata.add(data.charAt(i));
                    i++;
                }
                
                datatree = new Node(Stackdata.pop());
                infix(datatree);
                inorder(datatree);
                System.out.print("=");
                System.out.print(Calculate(datatree));
                
		// End of arguments input sample
		
		// TODO: Implement your project here
	}
    public static class Node{
        public Character data;
        public Node left;
        public Node right;
        
        public Node(Character x){
            data = x;
        }
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
