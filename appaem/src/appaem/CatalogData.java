package appaem;

public class CatalogData {
	/** Potência (W) */
	private Long id;
	/** Potência (W) */
	private Double power;
	/** Velocidade nominal (RPM) */
	private Double nominalSpeed;
	/** Corrente nominal (A) */
	private Double nominalCurrent;
	/** Corrente Rotor Bloqueado (A) */
	private Double blockedCurrent;
	/** Conjugado nominal (N) */
	private Double nominalTorque;
	/** Conjugado Bloqueado (N) */
	private Double blockedTorque;
	/** Conjugado Máximo (N) */
	private Double maxTorque;
	/** Efficiêcia 50% Potência */
	private Double efficiency50;
	/** Efficiêcia 75% Potência */
	private Double efficiency75;
	/** Efficiêcia 100% Potência */
	private Double efficiency100;
	/** Fator de Potência 50% Potência */
	private Double powerFactor50;
	/** Fator de Potência 75% Potência */
	private Double powerFactor75;
	/** Fator de Potência 100% Potência */
	private Double powerFactor100;
	
	
	
	
	public CatalogData(Double power, Double nominalSpeed, Double nominalCurrent, Double blockedCurrent,
			Double nominalTorque, Double blockedTorque, Double maxTorque, Double efficiency50, Double efficiency75,
			Double efficiency100, Double powerFactor50, Double powerFactor75, Double powerFactor100) {
		this.power = power;
		this.nominalSpeed = nominalSpeed;
		this.nominalCurrent = nominalCurrent;
		this.blockedCurrent = blockedCurrent;
		this.nominalTorque = nominalTorque;
		this.blockedTorque = blockedTorque;
		this.maxTorque = maxTorque;
		this.efficiency50 = efficiency50;
		this.efficiency75 = efficiency75;
		this.efficiency100 = efficiency100;
		this.powerFactor50 = powerFactor50;
		this.powerFactor75 = powerFactor75;
		this.powerFactor100 = powerFactor100;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getPower() {
		return power;
	}
	public void setPower(Double power) {
		this.power = power;
	}
	public Double getNominalSpeed() {
		return nominalSpeed;
	}
	public void setNominalSpeed(Double nominalSpeed) {
		this.nominalSpeed = nominalSpeed;
	}
	public Double getNominalCurrent() {
		return nominalCurrent;
	}
	public void setNominalCurrent(Double nominalCurrent) {
		this.nominalCurrent = nominalCurrent;
	}
	public Double getBlockedCurrent() {
		return blockedCurrent;
	}
	public void setBlockedCurrent(Double blockedCurrent) {
		this.blockedCurrent = blockedCurrent;
	}
	public Double getNominalTorque() {
		return nominalTorque;
	}
	public void setNominalTorque(Double nominalTorque) {
		this.nominalTorque = nominalTorque;
	}
	public Double getBlockedTorque() {
		return blockedTorque;
	}
	public void setBlockedTorque(Double blockedTorque) {
		this.blockedTorque = blockedTorque;
	}
	public Double getMaxTorque() {
		return maxTorque;
	}
	public void setMaxTorque(Double maxTorque) {
		this.maxTorque = maxTorque;
	}
	public Double getEfficiency50() {
		return efficiency50;
	}
	public void setEfficiency50(Double efficiency50) {
		this.efficiency50 = efficiency50;
	}
	public Double getEfficiency75() {
		return efficiency75;
	}
	public void setEfficiency75(Double efficiency75) {
		this.efficiency75 = efficiency75;
	}
	public Double getEfficiency100() {
		return efficiency100;
	}
	public void setEfficiency100(Double efficiency100) {
		this.efficiency100 = efficiency100;
	}
	public Double getPowerFactor50() {
		return powerFactor50;
	}
	public void setPowerFactor50(Double powerFactor50) {
		this.powerFactor50 = powerFactor50;
	}
	public Double getPowerFactor75() {
		return powerFactor75;
	}
	public void setPowerFactor75(Double powerFactor75) {
		this.powerFactor75 = powerFactor75;
	}
	public Double getPowerFactor100() {
		return powerFactor100;
	}
	public void setPowerFactor100(Double powerFactor100) {
		this.powerFactor100 = powerFactor100;
	}
	
	
}
