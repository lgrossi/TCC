package appaem;

import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class InductionMachineManager {
	
	private InductionMachine machine;
	
	public InductionMachineManager(InductionMachine machine) {
		this.machine = machine;
	}
	
	
	/**
	 * Calculates the Thevenin K. 
	 * Kth = Xm / (Rs + Xm) 
	 */
	public Double calculateKth () {
		Double xMagnetic = this.machine.getXMagnetic();
		return xMagnetic/(this.machine.getStator().getReactance() + xMagnetic);
	}

	/**
	 * Calculates the Thevenin Voltage.
	 *  Vth = Kth * Vs 
	 */
	public Double calculateVth () {
		return this.calculateKth() * this.machine.getStator().getVoltage();
	}

	/** 
	 * Calculates the Thevenin Resistance.
	 * Rth = Kth² * Rs
	 */
	public Double calculateRth () {
		return Math.pow(this.calculateKth(), 2) * this.machine.getStator().getResistance();
	}

	/** 
	 * Calculates the Thevenin Reactance.
	 * Xth = Xs 
	 */
	public Double calculateXth () {
		return this.machine.getStator().getReactance();
	}

	/** 
	 * Calculates the Synchronous Speed.
	 * Ns = 120 * F / nP 
	 */
	public Double calculateSynchronousSpeed () {
		return 120.0 * machine.getFrequency() / machine.getnPoles();
	}

	/** 
	 * Calculates the Synchronous Speed.
	 * F = Ns * nP / 120
	 */
	public Double calculateFrequencyFromSpeed (Double speed) {
		return speed * machine.getnPoles() / 120;
	}

	/** 
	 * Calculates the Synchronous Angular Speed.
	 * Ws = 2 * Ns * PI / 60 = Ns * PI / 30 
	 */
	public Double calculateSynchronousW () {
		return (this.calculateSynchronousSpeed() * Math.PI) / 30;
	}

	/** 
	 * Calculates the Split.
	 * S = (Ws - W) / Ws 
	 */
	public Double calculateSplit (Double n) {
		return (this.calculateSynchronousSpeed() - n)/this.calculateSynchronousSpeed();
	}
	
	/** 
	 * Calculates the Torque.
	 * T = 3 * (Rr / (s * Ws)) * (Vs² / ((Rs + Rr/s)² + (Xs + Xr)²))
	 */
	public Double calculateTorque (Double n) {
		return this.calculateTorque (n, this.machine.getRotor().getResistance());
	}
	
	/** 
	 * Calculates the Torque.
	 * T = 3 * (Rr / (s * Ws)) * (Vs² / ((Rs + Rr/s)² + (Xs + Xr)²))
	 */
	public Double calculateTorque (Double n, Double r2) {
		return 3 * r2 / (this.calculateSplit(n) * this.calculateSynchronousW()) * Math.pow(this.calculateRotorCurrent(n, r2), 2);
	}

	/** 
	 * Calculates the Maximum Torque.
	 * T = 3 / 2 * (1 / Ws) * (Vs² / ((Rs + sqrt(Xs + Xr)²)) 
	 */
	public Double calculateMaximumTorque () {		
		Double eqPart1 = 3 / (2 * this.calculateSynchronousW());
		Double eqPart2 = Math.pow(calculateVth(), 2) / (calculateRth() + Math.sqrt(calculateRth() + Math.pow(calculateXth() + this.machine.getRotor().getReactance(), 2)));
		
		return eqPart1 * eqPart2;
	}
	
	/** 
	 * Calculates the Stator Impedance.
	 * Z1 = R1 + jX1 + jXm * (r2/s + jX2)/(r2/s + j(Xm + X2)). 
	 */
	public Complex calculateStatorImpedance (Double n) {
		return this.calculateStatorImpedance (n, this.machine.getRotor().getResistance(), this.machine.getRotor().getReactance());
	}
	
	/** 
	 * Calculates the Stator Impedance.
	 * Z1 = R1 + jX1 + jXm * (r2/s + jX2)/(r2/s + j(Xm + X2)). 
	 */
	public Complex calculateStatorImpedance (Double n, Double r2, Double x2) {
		Double split = this.calculateSplit(n);
		Complex zR = new Complex(r2/split, x2), zS = new Complex(this.machine.getStator().getResistance(), this.machine.getStator().getReactance()), zM = new Complex(0, this.machine.getXMagnetic());
		return zS.plus(zM.times(zR).divides(zR.plus(zM)));
	}
	
	/** 
	 * Calculates the Stator Current.
	 * Is = V1/Z1. 
	 */
	public Complex calculateStatorCurrent (Double n) {
		return this.calculateStatorCurrent(n, this.machine.getRotor().getResistance(), this.machine.getRotor().getReactance());
	}
	
	/** 
	 * Calculates the Stator Current.
	 * Is = V1/Z1. 
	 */
	public Complex calculateStatorCurrent (Double n, Double r2, Double x2) {
		return new Complex(this.machine.getStator().getVoltage(), 0).divides(this.calculateStatorImpedance(n, r2, x2));
	}
	
	/** 
	 * Calculates the Rotor Current.
	 * I2 = |Vth| / |(Rth + R2/s) + j(X2 + Xth)| 
	 */
	public Double calculateRotorCurrent (Double n) {
		return this.calculateRotorCurrent(n, this.machine.getRotor().getResistance());
	}
	
	/** 
	 * Calculates the Rotor Current.
	 * I2 = |Vth| / |(Rth + R2/s) + j(X2 + Xth)| 
	 */
	public Double calculateRotorCurrent (Double n, Double r2) {
		return calculateVth() / Math.sqrt(Math.pow(calculateRth() + r2/this.calculateSplit(n), 2) + Math.pow(calculateXth() + this.machine.getRotor().getReactance(), 2));
	}
	
	
	/** 
	 * Calculates the Power Factor.
	 * PF = cos (teta1)
	 */
	public Double calculatePowerFactor (Double n) {
		return this.calculatePowerFactor(n, this.machine.getRotor().getResistance(), this.machine.getRotor().getReactance());
	}
	
	/** 
	 * Calculates the Power Factor.
	 * PF = cos (teta1)
	 */
	public Double calculatePowerFactor (Double n, Double r2, Double x2) {
		return Math.cos(this.calculateStatorCurrent(n, r2, x2).phase());
	}
	
	/** 
	 * Calculates the Input Power.
	 * Pin = 3 * |V1| * |I1| * cos (teta1)
	 */
	public Double calculateInputPower (Complex statorCurrent) {
		return Math.abs(3 * this.machine.getStator().getVoltage() * statorCurrent.abs() * Math.cos(statorCurrent.phase()));
	}
	
	/** 
	 * Calculates the Output Power.
	 * Pout = 3 * |I2|² * R2/s * (1 - s)
	 */
	public Double calculateOutputPower (Double rotorCurrent, Double split) {
		return 3 * Math.pow(rotorCurrent, 2) * (this.machine.getRotor().getResistance() / split) * (1 - split);
	}
	
	/** 
	 * Calculates the Efficiency.
	 * Is = V1/Z1. 
	 */
	public Double calculateEfficiency (Double n) {
		return this.calculateEfficiency(n, this.machine.getRotor().getResistance(), this.machine.getRotor().getReactance());
	}
	
	/** 
	 * Calculates the Efficiency.
	 * Is = V1/Z1. 
	 */
	public Double calculateEfficiency (Double n, Double r2, Double x2) {
		return this.calculateOutputPower(this.calculateRotorCurrent(n, r2), this.calculateSplit(n)) / this.calculateInputPower(this.calculateStatorCurrent(n, r2, x2));
	}

	/** 
	 * Gets the Torque X Speed profile points.
	 */
	public ArrayList<Pair<Double, Double>> getTorqueSpeedProfilePoints(Graph graph) {
		ArrayList<Pair<Double, Double>> points = new ArrayList<Pair<Double, Double>>();
		for (double speed = graph.getBegin(); speed < graph.getEnd(); speed += graph.getScale()) {
			points.add(new Pair<Double, Double>(speed, calculateTorque(speed)));
		}
		return points;
	}

	/** 
	 * Gets the Stator Current X Speed profile points.
	 */
	public ArrayList<Pair<Double, Double>> getStatorCurrentSpeedProfilePoints(Graph graph) {
		ArrayList<Pair<Double, Double>> points = new ArrayList<Pair<Double, Double>>();
		for (double speed = graph.getBegin(); speed < graph.getEnd(); speed += graph.getScale()) {
			points.add(new Pair<Double, Double>(speed, calculateStatorCurrent(speed).abs()));
		}
		return points;
	}

	/** 
	 * Gets the Power factor X Speed profile points.
	 */
	public ArrayList<Pair<Double, Double>> getPowerFactorSpeedProfilePoints(Graph graph) {
		ArrayList<Pair<Double, Double>> points = new ArrayList<Pair<Double, Double>>();
		for (double speed = graph.getBegin(); speed < graph.getEnd(); speed += graph.getScale()) {
			points.add(new Pair<Double, Double>(speed, calculatePowerFactor(speed)));
		}
		return points;
	}

	/** 
	 * Gets the Efficiency X Speed profile points.
	 */
	public ArrayList<Pair<Double, Double>> getEfficiencySpeedProfilePoints(Graph graph) {
		ArrayList<Pair<Double, Double>> points = new ArrayList<Pair<Double, Double>>();
		for (double speed = graph.getBegin(); speed < graph.getEnd(); speed += graph.getScale()) {
			points.add(new Pair<Double, Double>(speed, calculateEfficiency(speed)));
		}
		return points;
	}
	
	public static InductionMachine generateAuxMachine (InductionMachine machine, Double v, Integer f) {
		InductionMachine auxMachine = new InductionMachine(machine);
		if (v != null && v != machine.getStator().getVoltage()) {
			auxMachine.getStator().setVoltage(v);
			auxMachine.setFrequency(((Double) (v * machine.getFrequency() / machine.getStator().getVoltage())).intValue());
		} else if (f != null && f != machine.getFrequency()) {
			auxMachine.setFrequency(f);
			auxMachine.getStator().setVoltage((machine.getStator().getVoltage() * f / machine.getFrequency()));
		}
		return auxMachine;
	}

	/** 
	 * Generates the XYSerie of a graph according to its definition. 
	 */
	public XYSeriesCollection getGraph (Graph graph) {
		final XYSeries series = new XYSeries(graph.getType().name());
	    ArrayList<Pair<Double, Double>> list;
	    
		switch (graph.getType()) {
		case TorqueSpeedProfiling:
		default:
			list = getTorqueSpeedProfilePoints(graph);
			break;
		case StatorCurrentSpeedProfiling:
			list = getStatorCurrentSpeedProfilePoints(graph);
			break;
		case PowerFactorSpeedProfiling:
			list = getPowerFactorSpeedProfilePoints(graph);
			break;
		case EfficiencySpeedProfiling:
			list = getEfficiencySpeedProfilePoints(graph);
			break;
		}
		
		for (Pair<Double, Double> p : list) {
			series.add(p.getElement0(), p.getElement1());
		}
		return new XYSeriesCollection(series);
	}
}
