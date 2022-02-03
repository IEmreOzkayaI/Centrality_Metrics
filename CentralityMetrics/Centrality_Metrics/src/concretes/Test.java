package concretes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Test {

	public static void main(String[] args) {
		System.out.println("2020510137 EMRE ÖZKAYA: \n");
		karate_club();
		facebook_social();
	}

	private static void facebook_social() {

		Graph<String> facebookGraph = new Graph<String>();
		Stack<String> path = new Stack<>();
		List<String> list = new ArrayList<String>();
		List<String> vertList = new ArrayList<String>();
		String first = "n";
		try {
			// Create f1 object of the file to read data
			File f1 = new File("facebook_social_network.txt");
			Scanner dataReader = new Scanner(f1);
			dataReader.useDelimiter("\\W+");
			while (dataReader.hasNext()) {
				first = dataReader.next();
				list.add(first);
				if (!vertList.contains(first))
					vertList.add(first);
			}
			dataReader.close();

			// adding vertices
			for (int i = 0; i < list.size() - 1; i++) {
				facebookGraph.addVertex(list.get(i));
			}

			// connecting vertices
			for (int i = 0; i < list.size() - 1; i++) {
				facebookGraph.addEdge(list.get(i), list.get(i + 1));
			}

			// default queue for counting number of them
			List<Entry<String, Integer>> mostImp = new ArrayList<Entry<String, Integer>>();
			List<Entry<String, Double>> closeness = new ArrayList<Entry<String, Double>>();
			for (int i = 0; i < vertList.size(); i++) {
				mostImp.add(new Entry<>(vertList.get(i), 0));

			}

			// Betwennes and closeness calculation
			double totalPath = 0;
			double totalPathWeight = 0;
			double totalShortestPath = 0 ;
			for (int i = 0; i < vertList.size(); i++) {
				for (int j = 0; j < vertList.size(); j++) {
					Stack<String> pathlength = facebookGraph.getShortestPath(vertList.get(i), vertList.get(j), path,
							mostImp);
					totalPath = totalPath + (pathlength.size() - 1);

					// displaying way
					Object[] way = pathlength.toArray();
					for (int k = way.length - 1; k > -1; k--) {
						for (int l = 0; l < way.length; l++) {
							if (mostImp.get(l).getName().equals(way[k]))
								mostImp.get(l).setNumber(mostImp.get(l).getNumber() + 1);
						}
					} // end way
					totalShortestPath++;
				}
				totalPathWeight = 1 / totalPath;
				closeness.add(new Entry<>(vertList.get(i), totalPathWeight));
				totalPath = 0;
			}

			// priority queue
			int betwennesMax = 0;
			for (Entry<String, Integer> mostImpVertex : mostImp) {
				if (mostImpVertex.getNumber() > betwennesMax) {
					betwennesMax = mostImpVertex.getNumber();
				}
			}
			for (int k = 0; k < mostImp.size(); k++) {
				if (mostImp.get(k).getNumber() == betwennesMax)
					System.out.printf("\nFacebook Social Network – The Highest Node for Betweennes "
							+ mostImp.get(k).getName() + " and the value %.2f \n",(mostImp.get(k).getNumber()/totalShortestPath));
			}

			double closenessMax = 0;
			for (Entry<String, Double> mostImpVertex : closeness) {
				if (mostImpVertex.getNumber() > closenessMax) {
					closenessMax = mostImpVertex.getNumber();
				}
			}
			for (int k = 0; k < closeness.size(); k++) {
				if (closeness.get(k).getNumber() == closenessMax) {
					System.out.printf("Facebook Social Network – The Highest Node for Closeness  "
							+ closeness.get(k).getName() + " and the value %.5f \n",closeness.get(k).getNumber());
					break;

				}

			}
		} catch (FileNotFoundException exception) {
			System.out.println("Unexcpected error occurred!");
			exception.printStackTrace();
		}
	}

	private static void karate_club() {

		Graph<String> karategraph = new Graph<String>();
		Stack<String> path = new Stack<>();
		List<String> list = new ArrayList<String>();
		List<String> vertList = new ArrayList<String>();
		String first = "n";
		try {
			// Create f1 object of the file to read data
			File f1 = new File("karate_club_network.txt");
			Scanner dataReader = new Scanner(f1);
			dataReader.useDelimiter("\\W+");
			while (dataReader.hasNext()) {
				first = dataReader.next();
				list.add(first);
				if (!vertList.contains(first))
					vertList.add(first);
			}
			dataReader.close();

			// adding vertices
			for (int i = 0; i < list.size() - 1; i++) {
				karategraph.addVertex(list.get(i));
			}

			// connecting vertices
			for (int i = 0; i < list.size() - 1; i++) {
				karategraph.addEdge(list.get(i), list.get(i + 1));
			}

			// default queue for counting number of them
			List<Entry<String, Integer>> mostImp = new ArrayList<Entry<String, Integer>>();
			List<Entry<String, Double>> closeness = new ArrayList<Entry<String, Double>>();
			for (int i = 0; i < vertList.size(); i++) {
				mostImp.add(new Entry<>(vertList.get(i), 0));

			}

			// Betwennes and closeness calculation
			double totalPath = 0;
			double totalPathWeight = 0;
			double totalShortestPath=0;
			for (int i = 0; i < vertList.size(); i++) {
				for (int j = 0; j < vertList.size(); j++) {
					Stack<String> pathlength = karategraph.getShortestPath(vertList.get(i), vertList.get(j), path,
							mostImp);
					totalPath = totalPath + (pathlength.size() - 1);

					// displaying way
					Object[] way = pathlength.toArray();
					for (int k = way.length - 1; k > -1; k--) {
						for (int l = 0; l < way.length; l++) {
							if (mostImp.get(l).getName().equals(way[k]))
								mostImp.get(l).setNumber(mostImp.get(l).getNumber() + 1);
						}
					} // end way
					totalShortestPath++;
				}
				totalPathWeight = 1 / totalPath;
				closeness.add(new Entry<>(vertList.get(i), totalPathWeight));
				totalPath = 0;
			}

			// priority queue
			int betwennesMax = 0;
			for (Entry<String, Integer> mostImpVertex : mostImp) {
				if (mostImpVertex.getNumber() > betwennesMax) {
					betwennesMax = mostImpVertex.getNumber();
				}
			}
			for (int k = 0; k < mostImp.size(); k++) {
				if (mostImp.get(k).getNumber() == betwennesMax)
					System.out.printf("Zachary Karate Club Network – The Highest Node for Betweennes "
							+ mostImp.get(k).getName() + " and the value %.2f \n",(mostImp.get(k).getNumber()/totalShortestPath));
			}

			double closenessMax = 0;
			for (Entry<String, Double> mostImpVertex : closeness) {
				if (mostImpVertex.getNumber() > closenessMax) {
					closenessMax = mostImpVertex.getNumber();
				}
			}
			for (int k = 0; k < closeness.size(); k++) {
				if (closeness.get(k).getNumber() == closenessMax) {
					System.out.printf("Zachary Karate Club Network – The Highest Node for Closeness  "
							+ closeness.get(k).getName() + " and the value %.2f \n",closeness.get(k).getNumber());
					break;

				}

			}

		} catch (FileNotFoundException exception) {
			System.out.println("Unexcpected error occurred!");
			exception.printStackTrace();
		}
	}

}
