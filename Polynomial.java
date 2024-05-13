public class Polynomial {

	double[] coefficients;

	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0;
	}

	public Polynomial(double[] array) {
		coefficients = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			coefficients[i] = array[i];
		}
	}

	public Polynomial add(Polynomial p) {
		double[] c1;
		double[] c2;
		if (coefficients.length >= p.coefficients.length) {
			c1 = coefficients.clone();
			c2 = p.coefficients.clone();
		} else {
			c1 = p.coefficients.clone();
			c2 = coefficients.clone();
		}
		for (int i = 0; i < c1.length; i++) {
			if (i < c2.length) {
				c1[i] += c2[i];
			}
		}
		return new Polynomial(c1);
	}

	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
			result += Math.pow(x, i + 1) * coefficients[i];
		}
		return result;
	}

	public boolean hasRoot(double d) {
		return Math.abs(evaluate(d)) < 1e-15;
	}

}