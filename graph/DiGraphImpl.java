package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


 
//test comment another and another
public class DiGraphImpl implements DiGraph{

	private List<GraphNode> nodeList = new ArrayList<>();

	@Override
	public Boolean addNode(GraphNode node) {
		if (!nodeList.contains(node)) {
            nodeList.add(node);
            return true;
        }
		return false;
	}

	@Override
	public Boolean removeNode(GraphNode node) {
		if (!nodeList.contains(node)) {
	        return false;
	    }
	    nodeList.remove(node);
	    // Also remove all edges associated with this node
	    for (GraphNode n : nodeList) {
	        n.removeNeighbor(node);
	    }
	    return true;
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		if (nodeList.contains(node)) {
	        node.setValue(newNodeValue);
	        return true;
	    }
	    return false;
	}

	@Override
	public String getNodeValue(GraphNode node) {
		return node.getValue();
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());

		if (targetFromNode == null || targetToNode == null)
			return false; // nodes don't exist, can't make the edge

		return targetFromNode.addNeighbor(targetToNode, weight);
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		if (nodeList.contains(fromNode) && nodeList.contains(toNode)) {
            return fromNode.removeNeighbor(toNode);
        }
        return false;
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		if (fromNode != null && toNode != null) {
	        Integer existingWeight = fromNode.getDistanceToNeighbor(toNode);
	        if (existingWeight != null) {
	            fromNode.addNeighbor(toNode, newWeight);
	            return true;
	        }
	    }
		return false;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		GraphNode targetNode = getNode(node.getValue());
		return targetNode.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
		if (!nodeList.contains(targetFromNode) || !nodeList.contains(targetToNode)) {
            return false;
        }

        Set<GraphNode> visited = new HashSet<>();
        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(targetFromNode);
        visited.add(targetFromNode);

        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();
            if (current.equals(targetToNode)) {
                return true;
            }
            for (GraphNode neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return false;
	}

	@Override
	public Boolean hasCycles() {
        Set<GraphNode> visited = new HashSet<>();
        Set<GraphNode> recStack = new HashSet<>();

        for (GraphNode node : nodeList) {
            if (detectCycle(node, visited, recStack)) {
                return true;
            }
        }
        return false;
    }

    private Boolean detectCycle(GraphNode node, Set<GraphNode> visited, Set<GraphNode> recStack) {
        if (recStack.contains(node)) {
            return true;  // Cycle found
        }
        if (visited.contains(node)) {
            return false;  // Node already visited, no cycle found along this path
        }

        visited.add(node);
        recStack.add(node);

        for (GraphNode neighbor : node.getNeighbors()) {
            if (detectCycle(neighbor, visited, recStack)) {
                return true;
            }
        }

        recStack.remove(node);
        return false;
    }


	@Override
	public List<GraphNode> getNodes() {
		return nodeList;
	}

	@Override
	public GraphNode getNode(String nodeValue) {
		for (GraphNode thisNode : nodeList) {
			if (thisNode.getValue().equals(nodeValue))
				return thisNode;
		}
		return null;
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
		GraphNode fNode = getNode(fromNode.getValue());			//finds the inputed node in the graph
	    GraphNode tNode = getNode(toNode.getValue());		//finds the inputed node in the graph

	    if (fNode.equals(tNode)) {		//if end is start:
	        return 0; 		//zero "hops"
	    }
	    
	    Queue<GraphNode> queue = new LinkedList<>();		//initializes queue to store nodes in temporarily 
	    HashMap<GraphNode, Integer> visited = new HashMap<>();		//initializes HashMap to store nodes along with the number of hops to node
	    
	    queue.add(fNode);		//adds the start to the queue
	    visited.put(fNode, 0); 		//puts the start node in the HashMap along with the number of hops (0)
	    
	    while (!queue.isEmpty()) {		//while there are still nodes to be looked at
	        GraphNode current = queue.poll();		//initializes the current node to be used below
	        int currentHops = visited.get(current);		//gets the number of hops (if current has already been visited) 
	        
	        for (GraphNode neighbor : (ArrayList<GraphNode>) current.getNeighbors()) {		//for all the neighbors of current (brethfirst)
	            if (!visited.containsKey(neighbor)) {		//if neighbor has not been visited already
	                visited.put(neighbor, currentHops + 1);		//puts the neighbor along with number of hops into HashMap
	                queue.add(neighbor);		//adds the neighbor to queue so it's neighbors can be looked at after iteration
	                if (neighbor.equals(tNode)) {		//if the neighbor is the end node:
	                    return currentHops + 1; 		//returns number of hops 
	                }
	            }
	        }
	    }
	    
	    return -1;		//no route found "ERROR"
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		GraphNode fNode = getNode(fromNode.getValue());			//finds the inputed node in the graph
	    GraphNode tNode = getNode(toNode.getValue());		//finds the inputed node in the graph
		Map<GraphNode, Integer> weights = new HashMap<>();		//initializes map to store the node along with the total weight to that node
	    Set<GraphNode> visited = new HashSet<>();		//initializes HashSet to store visited nodes

	    for (GraphNode node : nodeList) {		//for all the nodes in the graph:
	    	weights.put(node, Integer.MAX_VALUE);		//puts nodes and large number into each weight in HashMap
	    }
	    weights.put(fNode, 0);		//puts weightp0 0 into starting node in HashMap

	    GraphNode current = fNode;		//initializes current node with starting node

	    while (current != null) {		//while there are still nodes to look at in the graph:
	        visited.add(current);		//adds the current to the visited Set

	        if (current.equals(tNode)) {		//if the current is the end node
	            return weights.get(current);		//returns weight to the current
	        }	

	        for (GraphNode neighbor : current.getNeighbors()) {		//for all the neighbors of the current: 
	            if (!visited.contains(neighbor)) {		//if neighbor has not been visited: 
	                int tempWeight = weights.get(current) + current.getDistanceToNeighbor(neighbor);		//stores the weight to the node from the last plus the previous weight
	                if (tempWeight < weights.get(neighbor)) {		//if less weight :
	                	weights.put(neighbor, tempWeight);		//puts the temp into the HashMap
	                }
	            }
	        }

	        current = null;		//resets current
	        int smallestWeight = Integer.MAX_VALUE;		//sets smallest weight to large number
	        for (GraphNode node : nodeList) {		//for all the nodes in the graph:
	            if (!visited.contains(node) && weights.get(node) < smallestWeight) {		//if the node is not visited and the weight is less than the smallest weight:
	            	smallestWeight = weights.get(node);		//sets the smallest weight to node weight
	                current = node;		//makes current set to node
	            }
	        }
	    }

	    return weights.getOrDefault(tNode, -1);		//no route found "ERROR"
	}
	
	
	
}
