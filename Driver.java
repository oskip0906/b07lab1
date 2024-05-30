import java.io.*;
import java.util.*;

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println("Polynomial p has a root at x = 2? " + p.hasRoot(2));
		double [] c1 = {6,-2,5};
		int [] e1 = {0,1,3};
		Polynomial p1 = new Polynomial(c1, e1);
		System.out.println("Polynomial p1 has a root at x = 3? " + p1.hasRoot(3));
		double [] c2 = {3,2,1};
		int [] e2 = {0,1,2};
		Polynomial p2 = new Polynomial(c2, e2);
		System.out.println("Polynomial p2 at x = 1: " + p2.evaluate(1));
		Polynomial p3 = p1.add(p2);
		System.out.println("Polynomial p3:");
		System.out.println("Coefficients: " + Arrays.toString(p3.coefficients));
		System.out.println("Exponents: " + Arrays.toString(p3.exponents));
		Polynomial p4 = p1.multiply(p2);
		System.out.println("Polynomial p4:");
		System.out.println("Coefficients: " + Arrays.toString(p4.coefficients));
		System.out.println("Exponents: " + Arrays.toString(p4.exponents));
		File file = new File("input.txt");
		Polynomial p5 = new Polynomial(file);
		System.out.println("Polynomial p5:");
		System.out.println("Coefficients: " + Arrays.toString(p5.coefficients));
		System.out.println("Exponents: " + Arrays.toString(p5.exponents));
		p5.saveToFile("output.txt");
	}
}