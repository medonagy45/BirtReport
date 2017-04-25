package UPS.reports;

//import DECreateDynamicTable;

//import CreateDynamicTable;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IExcelRenderOption;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.model.api.AutoTextHandle;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.ColumnHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.ImageHandle;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.ReportElementHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SimpleMasterPageHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.TableGroupHandle;
import org.eclipse.birt.report.model.api.TableHandle;
import org.eclipse.birt.report.model.api.TextItemHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.elements.interfaces.IAutoTextModel;
import org.eclipse.birt.report.model.elements.interfaces.ICellModel;
import org.eclipse.birt.report.model.elements.interfaces.IInternalReportItemModel;
import org.eclipse.birt.report.model.elements.interfaces.IMasterPageModel;
import org.eclipse.birt.report.model.elements.interfaces.IStyleModel;

public class ReportBuilder {
	IReportRunnable designRunnable= null;
	ReportDesignHandle designHandle = null;
	ElementFactory designFactory = null;
	StructureFactory structFactory = null;

	public static void main(String[] args) {
		
		try {

			
			// CreateDynamicTable de = new CreateDynamicTable();
			ReportBuilder de = new ReportBuilder();
			LinkedHashMap<String, String> columnNames = new LinkedHashMap<String, String>();
			columnNames.put("Client No.", "Col");
			columnNames.put("First Name", "Col1");
			columnNames.put("Second Name", "Col2");
			columnNames.put("Third Name", "Col3");
			columnNames.put("Fourth Name", "Col4");
			columnNames.put("fifth Name", "Col5");

			Map<String, String> reportParams = new TreeMap<String, String>();

			reportParams.put("To", "9/9/2009");
			reportParams.put("From", "75/26/1547");
			reportParams.put("Account Number", "01125478");
			
			ArrayList<String> groupBy = new ArrayList<String>();
			 groupBy.add("Col2");
			 groupBy.add("Col3");
			 groupBy.add("Col1");
//			 groupBy.add("Col4");
			// al.add("SerialNumber");
//			 Scanner in = new Scanner(System.in);
			   
//		      System.out.println("Enter a string");
//		      String repeat=in.next();
//		      while(true){
		    	  long startTime = System.currentTimeMillis();	
			de.buildReport(columnNames,reportParams, groupBy,"Issues Report","Loooooooooooooooooooooooooooooooooooooooool");
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("the total time is "+(totalTime/1000.0)+" sec");
//			repeat=in.nextLine();
//		      }
//		      System.out.println("exit");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates: <ColumnMapping index="1" name="City" odaDataType="String">
	 * <Method name="getCity"/> </ColumnMapping>
	 */
	protected static String getColumnMapping(String colName, String colType,
			int index) {

		return "<ColumnMapping index=\"" + index + "\" name=\"" + colName
				+ "\" odaDataType=\"" + colType + "\">" + "<Method name=\"get"
				+ colName + "\"/></ColumnMapping>";
	}

	void buildDataSource() throws SemanticException {
		OdaDataSourceHandle dsHandle = designFactory.newOdaDataSource(
				"Data Source", "org.eclipse.birt.data.oda.pojo");

		dsHandle.setProperty("pojoDataSetClassPath",
				"D:/development/Mohamed Nagy/workspace_afterGesh/BirtReport/bin/UPS/reports");// +
																								// BirtPojoDS.class.getName());
																								// BirtPojo.class.getProtectionDomain().getCodeSource().getLocation().getHost()
																								// //
																								// //"C:/_Workplace43/JavaProj-Birt/bin/JavaProj-Birt.jar;"
		// );
		dsHandle.setPrivateDriverProperty("pojoClassPath",
				"D:/development/Mohamed Nagy/workspace_afterGesh/BirtReport/bin/UPS/reports");// BirtPojo.class.getName());//"C:/_Workplace43/JavaProj-Birt/bin/JavaProj-Birt.jar;");

		designHandle.getDataSources().add(dsHandle);
	}

	protected String getQueryPrefix(String dataSource) {

		/*
		 * <?xml version="1.0" encoding="UTF-8" standalone="no"?> <PojoQuery
		 * appContextKey="APP_CONTEXT_KEY_POJO_DATA_SET"
		 * dataSetClass="pojo.Pojo1DaoMock" version="1.0">
		 */
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
				+ "<PojoQuery appContextKey=\"APP_CONTEXT_KEY_POJO_DATA_SET\" dataSetClass=\"UPS.reports.BirtPojoDS\" version=\"1.0\">";
	}

	void buildDataSet(List<String> cols) throws SemanticException {
		OdaDataSetHandle dsHandle = designFactory.newOdaDataSet("ds",
				"org.eclipse.birt.data.oda.pojo.dataSet");

		dsHandle.setDataSource("Data Source");
		dsHandle.setPrivateDriverProperty("methodNameRegx", "get*");
		dsHandle.setPrivateDriverProperty("pojoClass", "BirtPojo");

		String qry = getQueryPrefix(null);
		for (int i = 0; i < cols.size(); i++) {
			qry += getColumnMapping(cols.get(i), "String", i + 1);
		}

		// Add the suffix
		qry += "</PojoQuery>";

		// System.out.println("qry=" + qry);
		dsHandle.setQueryText(qry);

		designHandle.getDataSets().add(dsHandle);
	}

	void designGrid(Map<String, String> params) throws SemanticException{
		GridHandle grid = designFactory.newGridItem("Header", 3 /* cols */, 1/* row */);

		grid.setWidth("100%");
		designHandle.getBody().add(grid);
		Iterator<String> iterator = params.keySet().iterator();
		int rowCounter = 0;
		while (iterator.hasNext()) {

			String key = iterator.next();
			key += " : " + params.get(key);

			RowHandle row = (RowHandle) grid.getRows().get(0);
			CellHandle cell = (CellHandle) row.getCells().get(rowCounter++);
			LabelHandle label = designFactory.newLabel(null);
			cell.getContent().add(label);
			label.setText(key);
			label.setStyleName("data");
//			StyleHandle style = label.getStyle();
		}
	}
//	@SuppressWarnings("deprecation")
	
	void designHeader(String reportName, String subReportName) {

//		GridHandle grid = designFactory
//				.newGridItem("Header", 3 /* cols */, 1 /* row */);

		try {
			setStyles();
			/************** the header **************/
			GridHandle grid = designFactory.newGridItem("Header", 1 /* cols */, 1 /* row */);

			grid.setWidth("100%");
			designHandle.getBody().add(grid);

			RowHandle row = (RowHandle) grid.getRows().get(0);

			CellHandle cell = (CellHandle) row.getCells().get(0);

			LabelHandle label = designFactory.newLabel(null);
			// cell = (CellHandle) row.getCells().get(1);
			cell.getContent().add(label);
			label.setStyleName("HeaderFont");
			label.setText(reportName); 
			StyleHandle style = label.getStyle();
			style.getFontSize().setValue("18px");
			/************** The Subheader **************/
			grid = designFactory.newGridItem("Header", 1 /* cols */, 1 /* row */);

			grid.setWidth("100%");
			designHandle.getBody().add(grid);
			row = (RowHandle) grid.getRows().get(0);
			cell = (CellHandle) row.getCells().get(0);
			// cell.setDrop(DesignChoiceConstants.DROP_TYPE_NONE);

			label = designFactory.newLabel(null);
			cell.getContent().add(label);
			label.setText(subReportName); 
			label.setStyleName("SubHeaderFont");
			style = label.getStyle();
			style.getFontSize().setValue("12px");
			//$NON-NLS-1$
			/****************************/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @SuppressWarnings("static-access")
	@SuppressWarnings("deprecation")
	public static void generateOutPutFormat(IReportEngine engine,IReportRunnable designRunnable,String s) {
		try {
			IRunAndRenderTask runAndRender = engine.createRunAndRenderTask( designRunnable );	
			IPDFRenderOption option = new PDFRenderOption();
			option.setOutputFileName("D:/development/Birt/Transactions/output/Report"
					+ new Date().getMinutes() + "."+s);
			option.setOutputFormat(s);
			option.setOption(IPDFRenderOption.PDF_TEXT_WRAPPING, true);
			option.setOption(IPDFRenderOption.PDF_HYPHENATION, true);
			
//			IExcelRenderOption option = new EXCELRenderOption();
//			option.setOutputFileName("D:/development/Birt/Transactions/Report"+ new Date().getMinutes() + "."+s);
////			option.setOption(IExcelRenderOption., arg1);
//			option.setOutputFormat(s);
//			option.setOption(IExcelRenderOption.WRAPPING_TEXT, true);
//			option.setOption(IExcelRenderOption., true);
			
			runAndRender.setRenderOption(option);
			

			runAndRender.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setBorder(ReportElementHandle cell) throws SemanticException {

		cell.setProperty(IStyleModel.BORDER_TOP_COLOR_PROP, "#ffffff");
		cell.setProperty(IStyleModel.BORDER_TOP_STYLE_PROP, "solid");
		cell.setProperty(IStyleModel.BORDER_TOP_WIDTH_PROP, "1px");

		cell.setProperty(IStyleModel.BORDER_LEFT_COLOR_PROP, "#ffffff");
		cell.setProperty(IStyleModel.BORDER_LEFT_STYLE_PROP, "solid");
		cell.setProperty(IStyleModel.BORDER_LEFT_WIDTH_PROP, "1px");

		cell.setProperty(IStyleModel.BORDER_RIGHT_COLOR_PROP, "#ffffff");
		cell.setProperty(IStyleModel.BORDER_RIGHT_STYLE_PROP, "solid");
		cell.setProperty(IStyleModel.BORDER_RIGHT_WIDTH_PROP, "1px");

		cell.setProperty(IStyleModel.BORDER_BOTTOM_COLOR_PROP, "#ffffff");
		cell.setProperty(IStyleModel.BORDER_BOTTOM_STYLE_PROP, "solid");
		cell.setProperty(IStyleModel.BORDER_BOTTOM_WIDTH_PROP, "1px");

	}

	void setStyles() throws SemanticException {
		StyleHandle style = designFactory.newStyle("TableHeader");//$NON-NLS-1$
		style.setProperty(IStyleModel.FONT_WEIGHT_PROP,
				DesignChoiceConstants.FONT_WEIGHT_BOLD);
		style.setProperty(IStyleModel.FONT_FAMILY_PROP, "Verdana");//$NON-NLS-1$
		style.setProperty(IStyleModel.COLOR_PROP, "#ffffff");//$NON-NLS-1$
		style.setProperty(IStyleModel.BACKGROUND_COLOR_PROP, "#0842A5");//$NON-NLS-1$
		designHandle.getStyles().add(style);

		style = designFactory.newStyle("HeaderFont");//$NON-NLS-1$
		style.setProperty(IStyleModel.FONT_WEIGHT_PROP,
				DesignChoiceConstants.FONT_WEIGHT_BOLD);
		style.setProperty(IStyleModel.FONT_SIZE_PROP,
				"18");
		style.setProperty(IStyleModel.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_CENTER);
		style.setProperty(IStyleModel.FONT_FAMILY_PROP, "Verdana");//$NON-NLS-1$
		designHandle.getStyles().add(style);
		
		style = designFactory.newStyle("SubHeaderFont");//$NON-NLS-1$
		style.setProperty(IStyleModel.FONT_WEIGHT_PROP,
				DesignChoiceConstants.FONT_WEIGHT_BOLD);
		style.setProperty(IStyleModel.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_CENTER);//0842A5
		style.setProperty(IStyleModel.FONT_FAMILY_PROP, "Verdana");//$NON-NLS-1$
		designHandle.getStyles().add(style);

		style = designFactory.newStyle("GroupHeader");//$NON-NLS-1$
		style.setProperty(IStyleModel.FONT_WEIGHT_PROP,
				DesignChoiceConstants.FONT_WEIGHT_BOLD);
		style.setProperty(IStyleModel.FONT_FAMILY_PROP, "Verdana");//$NON-NLS-1$
		style.setProperty(IStyleModel.COLOR_PROP, "#0842A5");//$NON-NLS-1$
		style.setProperty(IStyleModel.BACKGROUND_COLOR_PROP, "#DFDFDF");//097a09");//$NON-NLS-1$
		style.setProperty(IStyleModel.FONT_SIZE_PROP, "9px");//$NON-NLS-1$
		style.setProperty(IStyleModel.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_LEFT);
//		style.setProperty(StyleHandle.BACKGROUND_SIZE_WIDTH, "1250px");//$NON-NLS-1$
		designHandle.getStyles().add(style);

		style = designFactory.newStyle("data");//$NON-NLS-1$
		// style.setProperty(StyleHandle.FONT_WEIGHT_PROP,
		// DesignChoiceConstants.FONT_WEIGHT_BOLD);
		style.setProperty(IStyleModel.FONT_FAMILY_PROP, "Arial");//$NON-NLS-1$
		style.setProperty(IStyleModel.COLOR_PROP, "#000000");//$NON-NLS-1$
		style.setProperty(IStyleModel.FONT_SIZE_PROP, "10px");//$NON-NLS-1$
		style.setProperty(IStyleModel.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_CENTER);//$NON-NLS-1$
		
		designHandle.getStyles().add(style);
		
		style = designFactory.newStyle("dataCell");//$NON-NLS-1$
		// style.setProperty(StyleHandle.FONT_WEIGHT_PROP,
		// DesignChoiceConstants.FONT_WEIGHT_BOLD);
//		style.setProperty(IStyleModel.FONT_FAMILY_PROP, "Arial");//$NON-NLS-1$
		style.setProperty(IStyleModel.BACKGROUND_COLOR_PROP, "#EFF7FE");//$NON-NLS-1$
		style.setProperty(IStyleModel.FONT_SIZE_PROP, "10px");//$NON-NLS-1$
		designHandle.getStyles().add(style);
		
		
        style = designFactory.newStyle("footerCount"); //$NON-NLS-1$
        // style.setProperty(StyleHandle.FONT_WEIGHT_PROP,
        // DesignChoiceConstants.FONT_WEIGHT_BOLD);
        style.setProperty(IStyleModel.FONT_FAMILY_PROP, "Arial");//$NON-NLS-1$
        style.setProperty(IStyleModel.COLOR_PROP, "#0842A5"); //$NON-NLS-1$
        style.setProperty(IStyleModel.BACKGROUND_COLOR_PROP, "#DFDFDF"); //$NON-NLS-1$
        style.setProperty(IStyleModel.FONT_SIZE_PROP, "10px"); //$NON-NLS-1$
        designHandle.getStyles().add(style);

	}

    void tableBuild(Map<String, String> params, ArrayList<String> groupBy) throws SemanticException {
        ArrayList<String> fakeName = new ArrayList<String>();
        ArrayList<String> DataBaseName = new ArrayList<String>();
        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            fakeName.add(key);
            DataBaseName.add(params.get(key));
        }
        buildDataSource();
        buildDataSet(DataBaseName);
        TableHandle table = designFactory.newTableItem("table", (DataBaseName.size() - groupBy.size()));

        table.setWidth("100%"); //ToFitColumns();
        table.setDataSet(designHandle.findDataSet("ds"));
        PropertyHandle computedSet = table.getColumnBindings();
        ComputedColumn cs1 = null;

        for (int i = 0; i < DataBaseName.size(); i++) {
            cs1 = StructureFactory.createComputedColumn();
            cs1.setName(DataBaseName.get(i));
            cs1.setExpression("dataSetRow[\"" + DataBaseName.get(i) + "\"]");
            computedSet.addItem(cs1);

        }
        //Aggregate fields for footer of group

        //Total Footer
        ComputedColumn groupVarianceExp = StructureFactory.createComputedColumn();
        groupVarianceExp.setName("TotalExp");
        groupVarianceExp.setDisplayName("TotalDisp");
        groupVarianceExp.setExpression("dataSetRow[\"" + DataBaseName.get(0) + "\"]");
        groupVarianceExp.setAggregateFunction(DesignChoiceConstants.MEASURE_FUNCTION_COUNT);
        computedSet.addItem(groupVarianceExp);


        // table header
        RowHandle tableheader = (RowHandle)table.getHeader().get(0);

        for (int i = 0, cellLocation = 0; i < fakeName.size(); i++) {
            if (!groupBy.contains(DataBaseName.get(i))) {

                LabelHandle label1 = designFactory.newLabel(fakeName.get(i));
                label1.setText(fakeName.get(i));
                CellHandle cell = (CellHandle)tableheader.getCells().get( // i);
                        cellLocation);
                cell.getContent().add(label1);
                StyleHandle style = label1.getPrivateStyle();
                style.getFontSize().setValue("9px");
                setBorder(cell);
                cell.setStyleName("TableHeader");
                cellLocation++;
            }
        }


        // table detail
        // table.ins

        RowHandle tabledetail = (RowHandle)table.getDetail().get(0);
        //		tabledetail.setStringProperty(RowHandle., value);
        for (int i = 0, cellLocation = 0; i < DataBaseName.size(); i++) {
            if (!groupBy.contains(DataBaseName.get(i))) {
                CellHandle cell = (CellHandle)tabledetail.getCells().get( // i);
                        cellLocation);
                DataItemHandle data = designFactory.newDataItem("data_" + DataBaseName.get(i));
                data.setResultSetColumn(DataBaseName.get(i));
                cell.getContent().add(data);
                cell.setStyleName("dataCell");
                // cell.getWidth().setStringValue("3in");
                setBorder(cell);
                cellLocation++;
            }
        }

        // setBorder(tabledetail);
        // Table Group
        TableGroupHandle group = designFactory.newTableGroup();
        // group.setw;
        for (int i = 0; i < groupBy.size(); i++) {
            group = designFactory.newTableGroup();
            group.setName(groupBy.get(i));
            group.setKeyExpr("row[\"" + groupBy.get(i) + "\"]");
            table.getGroups().add(group);
            int rowSize = DataBaseName.size() - groupBy.size();
            RowHandle groupHeader = designFactory.newTableRow(2);
            CellHandle cell = (CellHandle)groupHeader.getCells().get(0);
            // cell.setDrop(DesignChoiceConstants.DROP_TYPE_NONE);
            //			cell.setProperty(ICellModel.COL_SPAN_PROP, rowSize);
            DataItemHandle data = designFactory.newDataItem(null);
            //
            cell.setStyleName("GroupHeader"); //$NON-NLS-1$
            data.setResultSetColumn(groupBy.get(i));
            data.setStyleName("GroupHeader");
            
            LabelHandle label = designFactory.newLabel("label" + "Group" + i);
            label.setStyleName("GroupHeader");
            label.setText(fakeName.get(DataBaseName.indexOf(groupBy.get(i))));
            cell.getContent().add(label);
            cell.setProperty(CellHandle.WIDTH_PROP, "10%");
            cell = (CellHandle)groupHeader.getCells().get(1);
            cell.setProperty(ICellModel.COL_SPAN_PROP, rowSize - 1);
            //
            cell.setStyleName("GroupHeader");
            cell.getContent().add(data);

            group.getHeader().add(groupHeader);
            setBorder(groupHeader);

            //Group Footer
            ComputedColumn groupMedianExp = StructureFactory.createComputedColumn();
            groupMedianExp.setName("MedianExp"+i);
            groupMedianExp.setDisplayName("MedDisp");
            groupMedianExp.setExpression("dataSetRow[\"" + groupBy.get(i) + "\"]");
            groupMedianExp.setAggregateFunction(DesignChoiceConstants.MEASURE_FUNCTION_COUNT);
            groupMedianExp.setAggregateOn(groupBy.get(i));
            computedSet.addItem(groupMedianExp);


            //the footer of the group

            RowHandle groupMedFooter = designHandle.getElementFactory().newTableRow(2);
            CellHandle tblCellHandle = (CellHandle)groupMedFooter.getCells().get(0);
            tblCellHandle.setProperty(StyleHandle.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_CENTER);
            label = designFactory.newLabel("Count");
            //                      label.setStyleName("Count");
            label.setText("Count");

            tblCellHandle.getContent().add(label);
            tblCellHandle = (CellHandle)groupMedFooter.getCells().get(1);
            DataItemHandle dataHandle = designHandle.getElementFactory().newDataItem("MedExp");
            dataHandle.setResultSetColumn("MedianExp"+i);
            dataHandle.setDisplayName("Median");
            tblCellHandle.getContent().add(dataHandle);
            group.getFooter().add(groupMedFooter);


        }
        //column size
        //		ColumnHandle col;
        //		String [] temp={"15 mm"
        //				,"15 mm"
        //				,"15 mm"
        //				,"15 mm"
        //				,"15 mm"
        //				,"30 mm"};
        //		for (int i = 0; i < DataBaseName.size() - groupBy.size(); i++) {
        //
        //			col = (ColumnHandle) table.getGroups().get(0)//Columns().get(i);
        //			col.setProperty(ColumnHandle.WIDTH_PROP,temp[i]);
        //		}
        //Total Footer
        int rowSize=0;
        if(groupBy.size()==0){
            rowSize = DataBaseName.size();
            
        }else
            rowSize=DataBaseName.size()-groupBy.size();
        RowHandle groupVarFooter = designHandle.getElementFactory().newTableRow(2);
        groupVarFooter.setStyleName("footerCount");
        CellHandle tblCellHandle = (CellHandle)groupVarFooter.getCells().get(0);
        tblCellHandle.setProperty(StyleHandle.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_CENTER);

        LabelHandle label = designFactory.newLabel("Total");
//        label.setStyleName("footerCount");
        label.setText("Total");

        tblCellHandle.getContent().add(label);
        tblCellHandle = (CellHandle)groupVarFooter.getCells().get(1);
        DataItemHandle dataVarHandle = designHandle.getElementFactory().newDataItem("VarExp");
        dataVarHandle.setResultSetColumn("TotalExp");
        dataVarHandle.setDisplayName("Total");
        dataVarHandle.setStyleName("footerCount");
        tblCellHandle.getContent().add(dataVarHandle);
        dataVarHandle.setProperty(StyleHandle.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_LEFT);
        tblCellHandle.setProperty(ICellModel.COL_SPAN_PROP, rowSize - 1);

        table.getFooter().add(groupVarFooter);

        designHandle.getBody().add( table);
    }

	IReportEngine birtEngine() {
		// Configure the Engine and start the Platform
		EngineConfig config = new EngineConfig();

		config.setProperty("BIRT_HOME",
				"D:/development/Birt/birt-runtime-4_4_2/ReportEngine");
		IReportEngine engine = null;

//	  config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, Thread.currentThread().getContextClassLoader()); 
		try {
			Platform.startup(config);
			// org.eclipse.birt.report.model.api.impl.DesignEngineFactory
			// f=null;

			IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			engine = factory.createReportEngine(config);
			// org.eclipse.birt.report.engine.ReportEngineFactory f= null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return engine;
	}

	GridHandle designfooter(String reportTitle) throws SemanticException {

		GridHandle dataGridHandle = designFactory.newGridItem("dataGrid", 5, 1);
		dataGridHandle.setProperty(IInternalReportItemModel.WIDTH_PROP, "100%");
		RowHandle row1 = (RowHandle) dataGridHandle.getRows().get(0);

		CellHandle gridCellHandle = (CellHandle) row1.getCells().get(0);
		TextItemHandle fenix = designHandle.getElementFactory().newTextItem(
				"Fenix");
		fenix.setContentType(DesignChoiceConstants.TEXT_CONTENT_TYPE_PLAIN);
		fenix.setProperty(IStyleModel.FONT_SIZE_PROP, "8pt");
		fenix.setProperty(IStyleModel.FONT_FAMILY_PROP,
				DesignChoiceConstants.FONT_FAMILY_SERIF);
		fenix.setContent(reportTitle);
		gridCellHandle.getContent().add(fenix);

		gridCellHandle = (CellHandle) row1.getCells().get(1);
		AutoTextHandle autoTextPage = designHandle.getElementFactory()
				.newAutoText("Pages");
		autoTextPage.setProperty(IAutoTextModel.AUTOTEXT_TYPE_PROP, "page-number");
		autoTextPage.setProperty(IStyleModel.FONT_SIZE_PROP, "8pt");
		autoTextPage.setProperty(IStyleModel.FONT_FAMILY_PROP,
				DesignChoiceConstants.FONT_FAMILY_SERIF);
		gridCellHandle.getContent().add(autoTextPage);

		gridCellHandle = (CellHandle) row1.getCells().get(2);
		TextItemHandle slash = null;
		slash = designHandle.getElementFactory().newTextItem("Slash");
		slash.setContentType(DesignChoiceConstants.TEXT_CONTENT_TYPE_PLAIN);
		slash.setProperty(IStyleModel.FONT_SIZE_PROP, "8pt");
		slash.setProperty(IStyleModel.FONT_FAMILY_PROP,
				DesignChoiceConstants.FONT_FAMILY_SERIF);
		slash.setContent("/");
		gridCellHandle.getContent().add(slash);

		gridCellHandle = (CellHandle) row1.getCells().get(3);
		AutoTextHandle autoTextTotalPages = designHandle.getElementFactory()
				.newAutoText("Total");
		autoTextTotalPages.setProperty(IAutoTextModel.AUTOTEXT_TYPE_PROP,
				"total-page");
		autoTextTotalPages.setProperty(IStyleModel.FONT_SIZE_PROP, "8pt");
		autoTextTotalPages.setProperty(IStyleModel.FONT_FAMILY_PROP,
				DesignChoiceConstants.FONT_FAMILY_SERIF);
		gridCellHandle.getContent().add(autoTextTotalPages);

		gridCellHandle = (CellHandle) row1.getCells().get(4);
		TextItemHandle date = designHandle.getElementFactory().newTextItem(
				"Date");
		date.setContentType(DesignChoiceConstants.TEXT_CONTENT_TYPE_PLAIN);
		date.setProperty(IStyleModel.FONT_SIZE_PROP, "8pt");
		date.setProperty(IStyleModel.FONT_FAMILY_PROP,
				DesignChoiceConstants.FONT_FAMILY_SERIF);
		date.setContent("UICRM");
		date.setProperty(IStyleModel.TEXT_ALIGN_PROP, "right");
		gridCellHandle.getContent().add(date);

		ColumnHandle col = (ColumnHandle) dataGridHandle.getColumns().get(0);
		col.setProperty("width", "45%");
		col = (ColumnHandle) dataGridHandle.getColumns().get(4);
		col.setProperty("width", "45%");
		col = (ColumnHandle) dataGridHandle.getColumns().get(1);
		col.setProperty("width", "2%");
		col = (ColumnHandle) dataGridHandle.getColumns().get(2);
		col.setProperty("width", "1%");
		col = (ColumnHandle) dataGridHandle.getColumns().get(3);
		col.setProperty("width", "2%");
		// dataGridHandle.add(StyleHandle., content);
		return dataGridHandle;

	}
	GridHandle designPermentantHeader() throws SemanticException {

		GridHandle dataGridHandle = designFactory.newGridItem("dataGrid", 2, 1);
		dataGridHandle.setProperty(IInternalReportItemModel.WIDTH_PROP, "100%");
		RowHandle row1 = (RowHandle) dataGridHandle.getRows().get(0);

		CellHandle gridCellHandle = (CellHandle) row1.getCells().get(1);
		TextItemHandle fenix = designHandle.getElementFactory().newTextItem(
				"Fenix");
		fenix.setContentType(DesignChoiceConstants.TEXT_CONTENT_TYPE_PLAIN);
		fenix.setProperty(IStyleModel.FONT_SIZE_PROP, "8pt");
		fenix.setProperty(IStyleModel.FONT_FAMILY_PROP,
				DesignChoiceConstants.FONT_FAMILY_SERIF);

		fenix.setProperty(IStyleModel.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_RIGHT);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		fenix.setContent("print date:"+dateFormat.format(date));
		gridCellHandle.getContent().add(fenix);

		gridCellHandle = (CellHandle) row1.getCells().get(0);
		fenix = designHandle.getElementFactory().newTextItem(
				"Fenix");
		
		 ImageHandle image = designFactory.newImage(null);
		 gridCellHandle.getContent().add(image);
		 // File imagefile= new
		 // File("D:/development/Birt/Transactions/uiCRM.jpg") ;
		 // image.setURI("D:/development/Birt/Transactions/uiCRM.jpg" );
		 String imageFile="D:/development/Birt/Transactions/uiCRM.jpg";
		 image.setFile("\""+imageFile+"\"");
		 image.setScale(0.1);
		gridCellHandle.getContent().add(fenix);

		ColumnHandle col = (ColumnHandle) dataGridHandle.getColumns().get(0);
		col.setProperty("width", "75%");
		col = (ColumnHandle) dataGridHandle.getColumns().get(1);
		col.setProperty("width", "25%");
		// dataGridHandle.add(StyleHandle., content);
		return dataGridHandle;

	}

	/**
	 * @param reportTitle
	 * @throws SemanticException
	 */
	void masterPage(String reportTitle) throws SemanticException {
		SimpleMasterPageHandle simpleMasterPage = designFactory
				.newSimpleMasterPage("Master Page");
		simpleMasterPage.setPageType("a4");
		// simpleMasterPage.setOrientation(orientation);
		simpleMasterPage.setProperty(IMasterPageModel.BOTTOM_MARGIN_PROP, "1cm");

		// simpleMasterPage.setProperty(MasterPage.WIDTH_PROP,
		// MasterPage.A4_WIDTH);

		simpleMasterPage.setProperty(IMasterPageModel.TOP_MARGIN_PROP, "1cm");
		simpleMasterPage.setProperty(IMasterPageModel.LEFT_MARGIN_PROP, "1cm");
		simpleMasterPage.setProperty(IMasterPageModel.RIGHT_MARGIN_PROP, "1cm");
		// simpleMasterPage.getPageHeader().add(imageHandle);

		// add footer
		simpleMasterPage.getPageFooter().add(designfooter(reportTitle));
//		simpleMasterPage.getBottomMargin().setStringValue("30cm");//toString(
		simpleMasterPage.setShowFooterOnLast(false);//.
		simpleMasterPage.getPageHeader().add(designPermentantHeader());
		designHandle.getMasterPages().add(simpleMasterPage);
	}
	IReportEngine engine;
	   public void buildReport(Map<String, String> columnNames, Map<String, String> reportParams,
               ArrayList<String> grouping, String title, String subTitle) throws IOException,
                                                                                 SemanticException {
		  
		   
		try {//xls pdf html doc docx ods
			String type="pdf";
			start();
			
			designHeader(title, "");
			designGrid	(reportParams);
			/**********************/
			tableBuild(columnNames, grouping);
//			designHandle.getBody().add(table f(columnNames, grouping));
			// designfooter();
			masterPage(title);
			// Save the design and close it.
			String outFilepath = "D:/development/Birt/Transactions/output/Report"
					+ new Date().getMinutes() + "."+type;
			generateOutPutFormat(engine,designRunnable,type);

//			
			if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new File(outFilepath);
					Desktop.getDesktop().open(myFile);
//Desktop.getDesktop().
//					myFile.deleteOnExit();
//			    	System.out.println(myFile.getPath());
//					Runtime.getRuntime().addShutdownHook(new Thread() {
//
//					      @Override
//					      public void run() {
//					    	  System.out.println(myFile.getPath());
//					    	  myFile.deleteOnExit();
//
//					    	  System.out.println(myFile.getPath());
//					        /* Delete your file here. */
//					      }
//					 });
					// myFile.deleteOnExit();
				} catch (IOException ex) {
					// no application registered for PDFs
				}
			}
			System.out.println("Finished");
			engine.shutdown();
			 Platform.shutdown();
			 engine = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	   
	   
	   /**
	 * @throws EngineException ***********************************************/
	   public void start() throws EngineException{
		   engine = birtEngine();
		   designRunnable = engine.openReportDesign("D:/development/Birt/Transactions/Sample.rptdesign");
			designHandle=(ReportDesignHandle) designRunnable.getDesignHandle();
			designFactory = designHandle.getElementFactory();
	   }
	   /*************************************************/

}
