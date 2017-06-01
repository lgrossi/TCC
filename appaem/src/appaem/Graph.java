package appaem;

public class Graph {
	public enum GraphType {
		TorqueSpeedProfiling, StatorCurrentSpeedProfiling, PowerFactorSpeedProfiling, EfficiencySpeedProfiling; 
	}
	
	private GraphType type;
	private String title;
	private String axisX;
	private String axisY;
	private Double begin;
	private Double end;
	private Double scale;
	
	public Graph(GraphType type, String title, String axisX, String axisY, Double begin, Double end, Double scale) {
		this.type = type;
		this.title = title;
		this.axisX = axisX;
		this.axisY = axisY;
		this.begin = begin;
		this.end = end;
		this.scale = scale;
	}
	
	public GraphType getType() {
		return type;
	}
	public void setType(GraphType type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAxisX() {
		return axisX;
	}
	public void setAxisX(String axisX) {
		this.axisX = axisX;
	}
	public String getAxisY() {
		return axisY;
	}
	public void setAxisY(String axisY) {
		this.axisY = axisY;
	}
	public Double getBegin() {
		return begin;
	}
	public void setBegin(Double begin) {
		this.begin = begin;
	}
	public Double getEnd() {
		return end;
	}
	public void setEnd(Double end) {
		this.end = end;
	}
	public Double getScale() {
		return scale;
	}
	public void setScale(Double scale) {
		this.scale = scale;
	}
	
	
}
