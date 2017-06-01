package appaem;

public class InductionMachine extends ElectricalMachine {
	/** Frequência. */
	private Integer frequency;
	/** Número de Polos. */
	private Integer nPoles;
	
	/** Reatância de magnetização. */
	private Double xMagnetic;

	/** Circuito estator. */
	private BasicCircuit stator;
	/** Circuito rotor. */
	private BasicCircuit rotor;
	/** Circuito equivalente de Thevenin. */
	private BasicCircuit thevenin;
	
	public InductionMachine(Integer frequency, Integer nPoles, BasicCircuit stator, BasicCircuit rotor, BasicCircuit thevenin, Double xMagnetic) {
		this.frequency = frequency;
		this.nPoles = nPoles;
		this.stator = stator;
		this.rotor = rotor;
		this.thevenin = thevenin;
		this.xMagnetic = xMagnetic;
	}
	
	public InductionMachine(Integer frequency, Integer nPoles, BasicCircuit stator, BasicCircuit rotor, Double xMagnetic) {
		this.frequency = frequency;
		this.nPoles = nPoles;
		this.stator = stator;
		this.rotor = rotor;
		this.xMagnetic = xMagnetic;
		
		InductionMachineManager manager = new InductionMachineManager(this);
		this.thevenin = new BasicCircuit(manager.calculateVth(), null, manager.calculateRth(), manager.calculateXth());
	}
	
	/**
	 * @return this.the frequency
	 */
	public Integer getFrequency() {
		return this.frequency;
	}
	
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * @return the nPoles
	 */
	public Integer getnPoles() {
		return this.nPoles;
	}
	
	/**
	 * @param nPoles the nPoles to set
	 */
	public void setnPoles(Integer nPoles) {
		this.nPoles = nPoles;
	}
	
	/**
	 * @return this.the xMagnetic
	 */
	public Double getXMagnetic() {
		return this.xMagnetic;
	}
	
	/**
	 * @param xMagnetic the xMagnetic to set
	 */
	public void setXMagnetic(Double xMagnetic) {
		this.xMagnetic = xMagnetic;
	}

	/**
	 * @return the stator
	 */
	public BasicCircuit getStator() {
		return this.stator;
	}

	/**
	 * @param stator the stator to set
	 */
	public void setStator(BasicCircuit stator) {
		this.stator = stator;
	}

	/**
	 * @return the rotor
	 */
	public BasicCircuit getRotor() {
		return this.rotor;
	}

	/**
	 * @param rotor the rotor to set
	 */
	public void setRotor(BasicCircuit rotor) {
		this.rotor = rotor;
	}

	/**
	 * @return the thevenin
	 */
	public BasicCircuit getThevenin() {
		return this.thevenin;
	}

	/**
	 * @param thevenin the thevenin to set
	 */
	public void setThevenin(BasicCircuit thevenin) {
		this.thevenin = thevenin;
	}
}
