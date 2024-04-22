package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

 
public class OrgChartImpl implements OrgChart{

	//Employee is your generic 'E'..
	private List<GenericTreeNode<Employee>> nodes = new ArrayList<>();
	private Employee root ;

	@Override
	public void addRoot(Employee e) {
		root = e;
	    GenericTreeNode<Employee> rootNode = new GenericTreeNode<>(e);
	    nodes.add(rootNode);
	}

	@Override
	public void clear() {
		nodes.clear();
	}

	@Override
	public void addDirectReport(Employee manager, Employee newPerson) {
		for (GenericTreeNode<Employee> node : nodes) {
	        if (node.data.equals(manager)) {
	            GenericTreeNode<Employee> newChild = new GenericTreeNode<>(newPerson);
	            node.addChild(newChild);
	            nodes.add(newChild);
	            return;
	        }
	    }
	}

	@Override
	public void removeEmployee(Employee firedPerson) {
		GenericTreeNode<Employee> nodeToRemove = null;
	    GenericTreeNode<Employee> parent = null;

	    // Locate the node and its parent
	    for (GenericTreeNode<Employee> node : nodes) {
	        if (node.data.equals(firedPerson)) {
	            nodeToRemove = node;
	        }
	        for (GenericTreeNode<Employee> child : node.children) {
	            if (child.data.equals(firedPerson)) {
	                parent = node;
	                nodeToRemove = child;
	                break;
	            }
	        }
	        if (nodeToRemove != null) break;
	    }

	    if (nodeToRemove != null) {
	        // If the employee to be fired has direct reports, handle them
	        if (!nodeToRemove.children.isEmpty()) {
	            if (parent != null) {
	                // Promote all children of the fired employee to their grandparent
	                for (GenericTreeNode<Employee> child : nodeToRemove.children) {
	                    parent.addChild(child);  // Promoting children directly under the parent of fired person
	                }
	            } else {
	                // If no parent (i.e., Todd is a root or similar high-level position), handle appropriately
	                // This might involve adding children to the top-level or managing them differently
	                nodes.addAll(nodeToRemove.children);  // Promoting to top-level if no parent available
	            }
	        }

	        // Remove the employee
	        if (parent != null) {
	            parent.children.remove(nodeToRemove);  // Removing from parent's children list
	        }
	        nodes.remove(nodeToRemove);  // Removing from the main list
	    }
		
	}

	@Override
	public void showOrgChartDepthFirst() {
		StringBuilder output = new StringBuilder();
	    depthfirst(root, output);
	    System.out.println(output.toString());
	}
	
	private void depthfirst(Employee current, StringBuilder output) {
	    if (current == null) return;
	    output.append(current.getName()).append(" ").append(current.getId()).append(" ").append(current.getPosition()).append("\n");
	    for (GenericTreeNode<Employee> node : nodes) {
	        if (node.data.equals(current)) {
	            for (GenericTreeNode<Employee> child : node.children) {
	                depthfirst(child.data, output);
	            }
	        }
	    }
	}

	@Override
	public void showOrgChartBreadthFirst() {
	    Queue<GenericTreeNode<Employee>> queue = new LinkedList<>();
	    StringBuilder output = new StringBuilder();
	    queue.add(nodes.get(0));  // Assuming nodes.get(0) is the root

	    while (!queue.isEmpty()) {
	        GenericTreeNode<Employee> current = queue.poll();
	        output.append(current.data.getName()).append(" ").append(current.data.getId()).append(" ").append(current.data.getPosition()).append("\n");
	        queue.addAll(current.children);
	    }

	    System.out.println(output.toString());
	}
	
	
}
