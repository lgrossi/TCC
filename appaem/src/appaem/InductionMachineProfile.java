package appaem;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import appaem.Graph.GraphType;

public class InductionMachineProfile  extends ApplicationFrame {

	private static final long serialVersionUID = 682965788839392503L;

	public InductionMachineProfile(Graph graph, InductionMachineManager manager) {
		super(graph.getTitle());
	    final JFreeChart chart = ChartFactory.createXYLineChart(
	    		graph.getTitle(),
	    		graph.getAxisX(), 
	    		graph.getAxisY(), 
		        manager.getGraph(graph),
		        PlotOrientation.VERTICAL,
		        true,
		        true,
		        false
		    );
		
	    final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	    setContentPane(chartPanel);
	}

	public static void main(final String[] args) {	
		
		InductionMachine machine = new InductionMachine(60, 6, new BasicCircuit(1270.2, null, 2.8, 7.96), new BasicCircuit(null, null, 2.0, 7.96), 273.04);
	    final InductionMachineProfile demo = new InductionMachineProfile(new Graph(GraphType.TorqueSpeedProfiling, "Torque x Speed Profiling", "Speed", "Torque", 0.0, 1200.0, 0.1),  new InductionMachineManager(machine));
	    final InductionMachineProfile demo2 = new InductionMachineProfile(new Graph(GraphType.PowerFactorSpeedProfiling, "Power Factor x Speed Profiling", "Speed", "Power Factor", 0.0, 1200.0, 0.1),  new InductionMachineManager(machine));
	    final InductionMachineProfile demo3 = new InductionMachineProfile(new Graph(GraphType.StatorCurrentSpeedProfiling, "Stator Current x Speed Profiling", "Speed", "Stator Current", 0.0, 1200.0, 0.1),  new InductionMachineManager(machine));
	    final InductionMachineProfile demo4 = new InductionMachineProfile(new Graph(GraphType.EfficiencySpeedProfiling, "Efficiency x Speed Profiling", "Speed", "Efficiency", 0.0, 1200.1, 0.1),  new InductionMachineManager(machine));
	    demo.pack();
	    RefineryUtilities.centerFrameOnScreen(demo);
	    demo.setVisible(true);
	    demo2.pack();
	    RefineryUtilities.centerFrameOnScreen(demo3);
	    demo2.setVisible(true);
	    demo3.pack();
	    RefineryUtilities.centerFrameOnScreen(demo3);
	    demo3.setVisible(true);
	    demo4.pack();
	    RefineryUtilities.centerFrameOnScreen(demo4);
	    demo4.setVisible(true);
	    System.out.println(new InductionMachineManager(machine).calculateMaximumTorque());
	}
}
