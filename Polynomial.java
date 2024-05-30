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
			System.out.println(e);
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
					if (coefficients[i] > 0 && i != 0) {
						writer.write("+");
					}
					writer.write(Double.toString(coefficients[i]) + "x" + Integer.toString(exponents[i]));
				}
			}
			writer.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public int getIndex(int[] array, int element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}

	public int[] getExponents(int[] exponents1, int[] exponents2, double[] coefficients1, double[] coefficients2) {
		int[] combined = new int[exponents1.length + exponents2.length];
		for (int i = 0; i < exponents1.length; i++) {
			combined[i] = exponents1[i];
		}
		for (int i = 0; i < exponents2.length; i++) {
			combined[i + exponents1.length] = exponents2[i];
		}
		int unique = combined.length;
		for (int i = 0; i < combined.length; i++) {
			for (int j = i + 1; j < combined.length; j++) {
				if (combined[i] == combined[j] && combined[i] != -1) {
					combined[j] = -1;
					unique--;
				}
			}
			int index1 = getIndex(exponents1, combined[i]);
			int index2 = getIndex(exponents2, combined[i]);
			if (index1 != -1 || index2 != -1) {
				double c1 = 0;
				double c2 = 0;
				if (index1 != -1) {
					c1 = coefficients1[index1];
				}
				if (index2 != -1) {
					c2 = coefficients2[index2];
				}
				if (Math.abs(c1 + c2) < 1e-15) {
					combined[i] = -1;
					unique--;
				}
			}
		}
		int[] exponents = new int[unique];
		int index = 0;
		for (int i = 0; i < combined.length; i++) {
			if (combined[i] != -1) {
				exponents[index] = combined[i];
				index++;
			}
		}
		return exponents;
	}

	public Polynomial add(Polynomial p) {
		int[] new_exponents = getExponents(exponents, p.exponents, coefficients, p.coefficients);
		double[] new_coefficients = new double[new_exponents.length];
		for (int i = 0; i < new_exponents.length; i++) {
			int exponent = new_exponents[i];
			int index1 = getIndex(exponents, exponent);
			int index2 = getIndex(p.exponents, exponent);
			if (index1 != -1 && index2 != -1) {
				new_coefficients[i] = coefficients[index1] + p.coefficients[index2];
			}
			else if (index1 != -1) {
				new_coefficients[i] = coefficients[index1];
			}
			else {
				new_coefficients[i] = p.coefficients[index2];
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