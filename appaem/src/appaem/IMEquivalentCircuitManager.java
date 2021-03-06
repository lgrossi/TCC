package appaem;

public class IMEquivalentCircuitManager {
	
	public static final Double KGF_TO_NEWTON = 9.81;
	
	public static Pair<Double, Double> calculateRAndXfromTests(Double p, Double i, Double v) {
		Double r = p / (3* Math.pow(i, 2));
		return new Pair<Double, Double>(r, Math.sqrt((Math.pow(v/i, 2) * 1/3 - Math.pow(r, 2))));
	}

	public static Double calculateXmFromTests(Double x0, Double xBl) {
		return x0 - xBl/2;
	}

	public static Pair<Double, BasicCircuit> calculateEquivalentCircuitFromTests(Double p0, Double i0, Double v0, Double pBl, Double iBl, Double vBl, Double r1) {
		Pair<Double, Double> rxBl = calculateRAndXfromTests(pBl, iBl, vBl);
		Pair<Double, Double> rx0 = calculateRAndXfromTests(p0, i0, v0);
		return new Pair<Double, BasicCircuit>(IMEquivalentCircuitManager.calculateXmFromTests(rx0.getElement1(), rxBl.getElement1()), new BasicCircuit(null, null, rxBl.getElement0() - r1, rxBl.getElement1()/2));
	}
	
	public static Double calculateXFromCatalog(Double wS, Double v1, Double r1, Double tMax, Double kTh) {
		return Math.sqrt((Math.pow(((3 / (2 * wS)) * (Math.pow(v1 * kTh, 2) / tMax)) - (r1 * kTh), 2) - Math.pow(r1 * kTh, 2)) / (2 * (1 + kTh)));
	}
	
	public static Double calculateR2FromCatalog(Double wS, Double vTh, Double tN, Double sN) {
		return 3/(wS * tN) * Math.pow(vTh, 2) * sN;
	}
	
	public static Double calculateI2FromCatalog(Double wS, Double r2, Double tN, Double sN) {
		return Math.sqrt(tN * wS * sN / (3 * r2));
	}
	
	public static Double calculateImFromCatalog(Double pf100, Double iN, Double wS, Double r2, Double tN, Double sN) {
		return new Complex(IMEquivalentCircuitManager.calculateI2FromCatalog(wS, r2, tN, sN), 0).minus(Complex.phasorToRectangular(iN, Math.acos(pf100))).abs();
	}
	
	public static Double calculateXmFromCatalog(Double pf100, Double iN, Double wS, Double r2, Double tN, Double sN, Double v1) {
		return v1 / IMEquivalentCircuitManager.calculateImFromCatalog(pf100, iN, wS, r2, tN, sN);
	}
	
	public static Pair<Double, BasicCircuit> calculateEquivalentCircuitFromCatalog(Double pf100, Double iN, Double wS, Double tN, Double sN, Double v1, Double tMax, Double kTh) {
		return IMEquivalentCircuitManager.calculateEquivalentCircuitFromCatalog(pf100, iN, wS, tN, sN, v1, tMax, kTh, 0.0);
	}
	
	public static Double calculateKth (Double xM, Double x1) {
		return xM / (x1 + xM);
	}
	
	public static Pair<Double, BasicCircuit> calculateEquivalentCircuitFromCatalog(Double pf100, Double iN, Double wS, Double tN, Double sN, Double v1, Double tMax, Double kTh, Double r1) {
		Double r2 = IMEquivalentCircuitManager.calculateR2FromCatalog(wS, v1 * kTh, tN, sN);
		Double x2 = IMEquivalentCircuitManager.calculateXFromCatalog(wS, v1, r1, tMax, kTh);
		Double xM = IMEquivalentCircuitManager.calculateXmFromCatalog(pf100, iN, wS, r2, tN, sN, v1);
		Double newKth = IMEquivalentCircuitManager.calculateKth(xM, x2);
		if (Math.abs(newKth - kTh) * 1000 > 1) {
			return IMEquivalentCircuitManager.calculateEquivalentCircuitFromCatalog(pf100, iN, wS, tN, sN, v1, tMax, newKth, r1);
		}
		return new Pair<Double, BasicCircuit>(xM, new BasicCircuit(null, null, r2, x2));
	}
	
	public static void main(String[] args) {
		Pair<Double, BasicCircuit> circuitsFromTest = IMEquivalentCircuitManager.calculateEquivalentCircuitFromTests(210.0, 8.01, 222.5, 322.0, 10.75, 40.05, 0.395);
		//Double wS = 377.5, kTh = 1.0, iN = 5.85, tN = 8.036, sN = 0.0367, v1 = 440.0, pf100 = 0.8, tMax = 23.06, r2 = 5.4, xm = 227.0, x1 = 14.195;
		Double wS = 188.5, kTh = 1.0, iN = 8.70, tN = 1.25*9.806, sN = 0.0444, v1 = 127.0, pf100 = 0.85, tMax = 3.3635*1.25*9.806, r2 = 0.853, xm = 29.17, x1 = 1.42;
		//Double wS = 188.5, kTh = 1.0, iN = 6.12, tN = 0.82*9.81, sN = 0.0466, v1 = 127.0, pf100 = 0.78, tMax = 0.82*9.81*3.0, r2 = 1.42, xm = 53.55, x1 = 2.36;
		//Double wS = 188.5, kTh = 1.0, iN = 14.0, tN = 20.5, sN = 0.0472, v1 = 127.0, pf100 = 0.81, tMax = 63.54, r2 = 0.535, xm = 15.02, x1 = 0.97;
		
		Pair<Double, BasicCircuit> fromCatalog = IMEquivalentCircuitManager.calculateEquivalentCircuitFromCatalog(pf100, iN, wS, tN, sN, v1, tMax, kTh, 0.0);
		
		System.out.println(fromCatalog.getElement1().getResistance() + " / " + r2 + " = " + Math.abs(1 - fromCatalog.getElement1().getResistance() / r2) * 100 + "%");
		System.out.println(fromCatalog.getElement1().getReactance() + " / " + x1 + " = " + Math.abs(1 - fromCatalog.getElement1().getReactance() / x1) * 100 + "%");
		System.out.println(fromCatalog.getElement0() + " / " + xm + " = " + Math.abs(1 - fromCatalog.getElement0() / xm) * 100 + "%");
	}
	
	
	
	
	
}
