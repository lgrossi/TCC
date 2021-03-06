package appaem;

public class InductionMachine extends ElectricalMachine {
	/** Frequ�ncia. */
	private Integer frequency;
	/** N�mero de Polos. */
	private Integer nPoles;

	/** Reat�ncia de magnetiza��o. */
	private Double xMagnetic;

	/** Circuito estator. */
	private BasicCircuit stator;
	/** Circuito rotor. */
	private BasicCircuit rotor;
	/** Dados de placa */
	private CatalogData catalogData;

	public InductionMachine(InductionMachine machine) {
		this.frequency = machine.getFrequency();
		this.nPoles = machine.getnPoles();
		this.stator = new BasicCircuit(machine.getStator());
		this.rotor = new BasicCircuit(machine.getRotor());
		this.xMagnetic = machine.getXMagnetic();
	}

	public InductionMachine(Integer frequency, Integer nPoles) {
		this.frequency = frequency;
		this.nPoles = nPoles;
		this.xMagnetic = xMagnetic;
	}

	public InductionMachine(Integer frequency, Integer nPoles, BasicCircuit stator, BasicCircuit rotor,
			Double xMagnetic) {
		this.frequency = frequency;
		this.nPoles = nPoles;
		this.stator = stator;
		this.rotor = rotor;
		this.xMagnetic = xMagnetic;
	}

	/**
	 * @return this.the frequency
	 */
	public Integer getFrequency() {
		return this.frequency;
	}

	/**
	 * @param frequency
	 *            the frequency to set
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
	 * @param nPoles
	 *            the nPoles to set
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
	 * @param xMagnetic
	 *            the xMagnetic to set
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
	 * @param stator
	 *            the stator to set
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
	 * @param rotor
	 *            the rotor to set
	 */
	public void setRotor(BasicCircuit rotor) {
		this.rotor = rotor;
	}

	public CatalogData getCatalogData() {
		return catalogData;
	}

	public void setCatalogData(CatalogData catalogData) {
		this.catalogData = catalogData;
	}
}
