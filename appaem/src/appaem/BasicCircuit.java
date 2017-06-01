package appaem;

/** Circuito Básico. */
public class BasicCircuit {

	/** Tensão, corrente, resistência e reatância. */
	private Double voltage;
	private Double current;
	private Double resistance;
	private Double reactance;
	private Long id;
	
	public BasicCircuit(Double v, Double i, Double r, Double x) {
		voltage = v;
		current = i;
		resistance = r;
		reactance = x;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the voltage
	 */
	public Double getVoltage() {
		return voltage;
	}
	/**
	 * @param voltage the voltage to set
	 */
	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}
	/**
	 * @return the current
	 */
	public Double getCurrent() {
		return current;
	}
	/**
	 * @param current the current to set
	 */
	public void setCurrent(Double current) {
		this.current = current;
	}
	/**
	 * @return the resistance
	 */
	public Double getResistance() {
		return resistance;
	}
	/**
	 * @param resistance the resistance to set
	 */
	public void setResistance(Double resistance) {
		this.resistance = resistance;
	}
	/**
	 * @return the reactance
	 */
	public Double getReactance() {
		return reactance;
	}
	/**
	 * @param reactance the reactance to set
	 */
	public void setReactance(Double reactance) {
		this.reactance = reactance;
	}
		
}
