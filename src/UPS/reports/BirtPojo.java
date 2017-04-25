package UPS.reports;


public class BirtPojo {

	private String col;
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	
	private static int colSize=0;
	private static int col1Size=0;
	private static int col2Size=0;
	private static int col3Size=0;
	private static int col4Size=0;
	private static int col5Size=0;


	public static int getColSize() {
		return colSize;
	}
	public static void setColSize(int colSize) {
		BirtPojo.colSize = colSize;
	}
	public static int getCol1Size() {
		return col1Size;
	}
	public static void setCol1Size(int col1Size) {
		BirtPojo.col1Size = col1Size;
	}
	public static int getCol2Size() {
		return col2Size;
	}
	public static void setCol2Size(int col2Size) {
		BirtPojo.col2Size = col2Size;
	}
	public static int getCol3Size() {
		return col3Size;
	}
	public static void setCol3Size(int col3Size) {
		BirtPojo.col3Size = col3Size;
	}
	public static int getCol4Size() {
		return col4Size;
	}
	public static void setCol4Size(int col4Size) {
		BirtPojo.col4Size = col4Size;
	}
	public static int getCol5Size() {
		return col5Size;
	}
	public static void setCol5Size(int col5Size) {
		BirtPojo.col5Size = col5Size;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		if(col4.length()>col4Size)
			col4Size=col4.length();
		this.col4 = col4;
	}
	public String getCol5() {
		return col5;
	}
	public void setCol5(String col5) {
		if(col5.length()>col5Size)
			col5Size=col5.length();
		this.col5 = col5;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		if(col2.length()>col2Size)
			col2Size=col2.length();
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		if(col3.length()>col3Size)
			col3Size=col3.length();
		this.col3 = col3;
	}
	/**
	 * @return the col1
	 */
	public String getCol1() {
		return col1;
	}
	/**
	 * @param col1 the col1 to set
	 */
	public void setCol1(String col1) {
		if(col1.length()>col1Size)
			col1Size=col1.length();
		this.col1 = col1;
	}
	/**
	 * @return the col
	 */
	public String getCol() {
		return col;
	}
	/**
	 * @param col the col to set
	 */
	public void setCol(String col) {
		if(col.length()>colSize)
			colSize=col.length();
		this.col = col;
	}
	
}
