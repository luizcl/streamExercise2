package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.UK);
		Scanner sc = new Scanner(System.in);
		
		String path = "/Users/luizclaudiomoraes/temp/products.csv";
		
		if(path.isEmpty()) {
			System.out.println("Enter full file path: ");
			path = sc.nextLine();
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Product> list = new ArrayList<Product>();
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1]) ) );
				line = br.readLine();
			}
			
			Comparator<String> comparator = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			double sum = list.stream().map(p -> p.getPrice()).reduce(0.0, (x,y) -> x + y );
			double average = sum / list.size();
			System.out.println("Average price: " + String.format("%.2f", average));
			
			List<String> names = list.stream()
					.filter(p -> p.getPrice() < average)
					.map( p -> p.getName())
					.sorted(comparator.reversed())
					.collect(Collectors.toList());
			
			names.forEach(System.out::println);
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}finally {
			sc.close();
		}
		
	}
	
}
