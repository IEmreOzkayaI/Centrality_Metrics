package concretes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import abstracts.VertexInterface;

public class Vertex<T> implements VertexInterface<T>{
	private T name;
	private ArrayList<Edge> edgeList;
	private boolean visited;
	private VertexInterface<T> previousVertex;
	private double cost;
	
	public Vertex(T vertexName) {
		this.name=vertexName;
		edgeList = new ArrayList<>();
		visited=false;
		previousVertex = null;
		cost = 0;
	}

	@Override
	public T getLabel() {
		return name;
	}

	@Override
	public void visit() {
		visited = true;
	}

	@Override
	public void unVisit() {
		visited = false;
	}

	@Override
	public boolean isVisited() {
		return visited == true;
	}

	@Override
	public boolean connect(VertexInterface<T> endVertex) {
		return connect(endVertex,0);
	}

	@Override
	public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
		boolean result = false;
		if(!this.equals(endVertex)) {
			Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
			boolean duplicateEdge = false;
			while(!duplicateEdge && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if(endVertex.equals(nextNeighbor))
					duplicateEdge=true;
			}
			if(!duplicateEdge) {
				edgeList.add(new Edge(endVertex,edgeWeight));
				result = true;
			}
		}
		return result;
	}

	@Override
	public Iterator<VertexInterface<T>> getNeighborIterator() {
		return new NeighborIterator();
	}
	
	@Override
	public Iterator<Double> getWeightIterator() {
		return new WeightIterator();
	}

	@Override
	public boolean hasNeighbor() {
		return !edgeList.isEmpty();
	}

	@Override
	public VertexInterface<T> getUnvisitedNeighbor() {
		VertexInterface<T> result = null;
		Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
		while(neighbors.hasNext() && result == null) {
			VertexInterface<T> nextNeighbor = neighbors.next();
			if(!nextNeighbor.isVisited())
				result = nextNeighbor;
		}
		
		return result;
	}

	@Override
	public void setPredecessor(VertexInterface<T> predecessor) {
		previousVertex = predecessor;
	}

	@Override
	public VertexInterface<T> getPredecessor() {
		return previousVertex;
	}

	@Override
	public boolean hasPredecessor() {
		return previousVertex != null;
	}

	@Override
	public void setCost(double newCost) {
		this.cost = newCost;
	}

	@Override
	public double getCost() {
		return cost;
	}
	
	protected class Edge{
		private VertexInterface<T> vertex;
		private double weight;
		
		protected Edge(VertexInterface<T> endVertex , double edgeWeight) {
			vertex = endVertex;
			weight = edgeWeight;
		}
		
		protected VertexInterface<T> getEndVertex(){
			return vertex;
		}
		
		protected double getWeight() {
			return weight;
		}
	}
	
	private class NeighborIterator implements Iterator<VertexInterface<T>>{
		
		private Iterator<Edge> edges;
		
		private NeighborIterator() {
			edges = edgeList.iterator();
		}
			
		@Override
		public boolean hasNext() {
			return edges.hasNext();
		}

		@Override
		public VertexInterface<T> next() {
			VertexInterface<T> nextNeighbor = null;
			if(edges.hasNext()) {
				Edge edgeToNextNeighor = edges.next();
				nextNeighbor = edgeToNextNeighor.getEndVertex();
			}else {
				throw new NoSuchElementException();
			}
			return nextNeighbor;
		}
		
	}

	private class WeightIterator implements Iterator<Double>{
		
		private Iterator<Edge> edges;
		
		private WeightIterator() {
			edges = edgeList.iterator();
		}
			
		@Override
		public boolean hasNext() {
			return edges.hasNext();
		}

		@Override
		public Double next() {
			double weight = 0;
			if(edges.hasNext()) {
				Edge edgeToNextNeighor = edges.next();
				weight = edgeToNextNeighor.getWeight();
			}else {
				throw new NoSuchElementException();
			}
			return weight;
		}
		
	}


	
	
}
