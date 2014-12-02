
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.util.Rotation;

public class Chart extends ApplicationFrame {
	

    public Chart(final String title, final double correct, final double incorrect) {

        super(title);
        
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // create a dataset...
        final PieDataset dataset = createSampleDataset(correct, incorrect);
        
        // create the chart...
        final JFreeChart chart = createChart(dataset);
        
        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

    private PieDataset createSampleDataset(final double correct, final double incorrect) {
        
        final DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Corretos = " + String.valueOf(correct) + "%", new Double(correct));
        result.setValue("Incorretos = " + String.valueOf(incorrect) + "%", new Double(incorrect));
        return result;
        
    }

    private JFreeChart createChart(final PieDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createPieChart3D(
            "Grafico comparativo entre árvores corretas e incorretas"
            + "",  // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        return chart;
        
    }

}
