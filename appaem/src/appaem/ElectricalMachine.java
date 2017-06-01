package appaem;

/**
 * Classe referente a uma máquina elétrica
 */
public class ElectricalMachine {
	/** Id da máquina. */
	private Long id;
	/** Nome da máquina. */
	private String name;
	/** Ano de fabricação da máquina. */
	private String year;
	/** Modelo da máquina. */
	private String model;
	/** Id do tipo da máquina. */
	private String typeId;
	/** Fabricante da máquina. */
	private String manufacturer;
	/** Descrição. */
	private String description;
	
	public void defineBasicMachineData (Long id, String name, String year, String model, String typeId, String manufacturer, String description) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.model = model;
		this.typeId = typeId;
		this.manufacturer = manufacturer;
		this.description = description;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
