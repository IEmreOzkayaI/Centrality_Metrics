package concretes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import abstracts.GraphInterface;
import abstracts.VertexInterface;

public class Graph<T> implements GraphInterface<T> {
	private Map<T, VertexInterface<T>> vertices;
	private int edgeCount;

	public Graph() {
		vertices = new HashMap<T, VertexInterface<T>>();
		edgeCount = 0;
	}

	@Override
	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = vertices.put(vertexLabel, new Vertex<>(vertexLabel));

		return addOutcome == null; // is addition success
	}

	@Override
	public boolean addEdge(T begin, T end, double edgeWeight) {
		boolean resultBegin = false;
		boolean resultEnd = false;

		VertexInterface<T> beginVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);

		if ((beginVertex != null) && (endVertex != null)) {
			resultBegin = beginVertex.connect(endVertex, edgeWeight);
			resultEnd = endVertex.connect(beginVertex, edgeWeight);
		}

		if (resultBegin && resultEnd)
			edgeCount++;

		return resultBegin;
	}

	@Override
	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	}

	@Override
	public boolean hasEdge(T begin, T end) {
		boolean found = false;
		VertexInterface<T> beginVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);
		if ((beginVertex != null) && (endVertex != null)) {
			Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
			while (!found && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (endVertex.equals(nextNeighbor))
					found = true;
			}
		}
		return found;
	}

	@Override
	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	@Override
	public int getNumberOfVertices() {
		return vertices.size();
	}

	@Override
	public int getNumberOfEdges() {
		return edgeCount;
	}

	@Override
	public void clear() {
		vertices.clear();
		edgeCount = 0;
	}

	@Override
	public Stack<T> getShortestPath(T begin, T end, Stack<T> path, List<Entry<String, Integer>> mostImp) {
		path.clear();
		resetVertices();
		boolean done = false;
		LinkedBlockingQueue<VertexInterface<T>> vertexQueue = new LinkedBlockingQueue<>();
		VertexInterface<T> originVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);
		originVertex.visit();

		vertexQueue.add(originVertex);
		while (!done && !vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.poll();
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			while (!done && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					nextNeighbor.setCost(1 + frontVertex.getCost());
					nextNeighbor.setPredecessor(frontVertex);
					vertexQueue.add(nextNeighbor);
				}
				if (nextNeighbor.equals(endVertex))
					done = true;
			}
		}

		// Traversal
		path.push(endVertex.getLabel());

		VertexInterface<T> vertex = endVertex;
		while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}

		

		return path;
	}

	protected void resetVertices() {
		Iterator<VertexInterface<T>> vertexIterator = getValueIterator();
		while (vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			nextVertex.unVisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		} // end while
	}

	public Iterator<VertexInterface<T>> getValueIterator() {
		return new ValueIterator();
	}

	private class ValueIterator implements Iterator<VertexInterface<T>> {

		private Iterator<Map.Entry<T, VertexInterface<T>>> value;

		private ValueIterator() {
			value = vertices.entrySet().iterator();
		}

		@Override
		public boolean hasNext() {
			return value.hasNext();
		}

		@Override
		public VertexInterface<T> next() {
			return value.next().getValue();
		}

	}

}
