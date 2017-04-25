package UPS.reports;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BirtPojoDS {

	private Iterator<Object> iterator;
	private List<Object> pojoList1;
	public void setBirtPojos(List<Object> data){
		pojoList1 = new ArrayList<Object>();
		pojoList1.addAll(BirtPojoPrepare.getBirtPojos());
	}
	public List<Object> getBirtPojos(){//String BirtPojoId) {
		pojoList1 = new ArrayList<Object>();
//		List<BirtPojo> pojoList = new ArrayList<BirtPojo>();
		pojoList1.addAll(BirtPojoPrepare.getBirtPojos());//=pro
		return pojoList1;
	}

//	private BirtPojo getBirtPojo(String BirtPojoId) {
//		BirtPojo std = new BirtPojo();
//
//		// your logic to get the details of the BirtPojo
//		// goes here. Fetch the BirtPojo details and populate
//		// the BirtPojo.
//		
//		return std;
//	}

	// The following method will be called by BIRT engine once when
	// the report is invoked. It is also a mandatory method.
	
	@SuppressWarnings(value = { })
	public void open(Object obj, Map<String, Object> map) {
		iterator = getBirtPojos().iterator();
	}

	// this method is a mandatory method. It must be implemented. This method
	// is used by the BIRT Reporting engine.
	public Object next() {
		if (iterator.hasNext())
			return iterator.next();
		return null;
	}

	// The following method is also a mandatory method. This will be
	// called by the BIRT engine once at the end of the report.
	public void close() {
	}
}
