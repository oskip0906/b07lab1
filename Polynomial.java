import java.io.*;
import java.util.*;

public class Polynomial {

	double[] coefficients;
	int[] exponents;

	public Polynomial() {
		coefficients = new double[1];
		exponents = new int[1];
		coefficients[0] = 0;
		exponents[0] = 0;
	}

	public Polynomial(double[] arr1, int[] arr2) {
		coefficients = new double[arr1.length];
		exponents = new int[arr2.length];
		for (int i = 0; i < arr1.length; i++) {
			coefficients[i] = arr1[i];
			exponents[i] = arr2[i];
		}
	}

	public Polynomial(File file) {
		try {
			Scanner scanner = new Scanner(file);
			String data = scanner.nextLine();
			data = data.replace("-", "+-");
			if (data.charAt(0) == '+') {
				data = data.substring(1);
			}
			String[] terms = data.split("[+]");
			coefficients = new double[terms.length];
			exponents = new int[terms.length];
			for (int i = 0; i < terms.length; i++) {
				if (!terms[i].contains("x")) {
					terms[i] = terms[i] + "x0";
				}
				String[] temp = terms[i].split("[x]");
				coefficients[i] = Double.parseDouble(temp[0]);
				exponents[i] = Integer.parseInt(temp[1]);
			}
			scanner.close();
		}
		catch (Exception e) {
			System.out.println("Error!");
		}
	}

	public void saveToFile(String file_name) {
		try {
			FileWriter writer = new FileWriter(file_name);
			for (int i = 0; i < coefficients.length; i++) {
				if (exponents[i] == 0) {
					writer.write(Double.toString(coefficients[i]));

				}
				else {
					if (coefficients[i] > 0) {
						writer.write("+");
					}
					writer.write(Double.toString(coefficients[i]) + "x" + Integer.toString(exponents[i]));
				}
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Error!");
		}
	}

	public int num_common(int[] arr1, int[] arr2) {
		int common = 0;
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr2.length; j++) {
				if (arr1[i] == arr2[j]) {
					common++;
					break;
				}
			}
		}
		return common;
	}

	public Polynomial add(Polynomial p) {
		int unique = exponents.length + p.exponents.length - num_common(exponents, p.exponents);
		int[] new_exponents = new int[unique];
		double[] new_coefficients = new double[unique];
		int e1 = 0;
		int e2 = 0;
		for (int i = 0; i < unique; i++) {
			if (e1 == exponents.length) {
				new_coefficients[i] = p.coefficients[e2];
				new_exponents[i] = p.exponents[e2];
				e2++;
			}
			else if (e2 == p.exponents.length) {
				new_coefficients[i] = coefficients[e1];
				new_exponents[i] = exponents[e1];
				e1++;
			}
			else if (exponents[e1] == p.exponents[e2]){
				new_coefficients[i] = coefficients[e1] + p.coefficients[e2];
				new_exponents[i] = exponents[e1];
				e1++;
				e2++;
			}
			else {
				if (exponents[e1] < p.exponents[e2]) {
					new_coefficients[i] = coefficients[e1];
					new_exponents[i] = exponents[e1];
					e1++;
				}
				else {
					new_coefficients[i] = p.coefficients[e2];
					new_exponents[i] = p.exponents[e2];
					e2++;
				}
			}
		}
		return new Polynomial(new_coefficients, new_exponents);
	}


	public Polynomial multiply(Polynomial p) {
		double[][] coefficients_arrays = new double[coefficients.length][p.coefficients.length];
		int[][] exponents_arrays = new int[exponents.length][p.exponents.length];
		for (int i = 0; i < coefficients.length; i++) {
			for (int j = 0; j < p.coefficients.length; j++) {
				coefficients_arrays[i][j] = coefficients[i] * p.coefficients[j];
				exponents_arrays[i][j] = exponents[i] + p.exponents[j];
			}
		}
		Polynomial result = new Polynomial(coefficients_arrays[0], exponents_arrays[0]);
		for (int i = 1; i < coefficients.length; i++) {
			Polynomial temp = new Polynomial(coefficients_arrays[i], exponents_arrays[i]);
			result = result.add(temp);
		}
		return result;
	}

	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
			result += Math.pow(x, exponents[i]) * coefficients[i];
		}
		return result;
	}

	public boolean hasRoot(double d) {
		return Math.abs(evaluate(d)) < 1e-15;
	}

}