package UPS.reports;

import java.util.ArrayList;
import java.util.List;

public class BirtPojoPrepare {

	protected static List<BirtPojo> getBirtPojos() {

		List<BirtPojo> pojoList = new ArrayList<BirtPojo>();
		BirtPojo pojo;
		for (int i = 0; i <6; i++) {
			for (int j = 0; j < 25; j++) {
				pojo= new BirtPojo();
				pojo.setCol("aa"+Math. random() * 50 + 1);
				pojo.setCol1(i+"ww"+j);
				pojo.setCol2("Two"+j);
				pojo.setCol3(i+"three"+j);
				pojo.setCol4("thisisdata,aadqwfqwfqwthisisdata,aadqwfqwfqwthisisdata,aadqwfqwfqw");
				pojo.setCol5("this is data ,aadqwfqwfqw");
				pojoList.add(pojo);
			
			}
			
		}
		return pojoList;
	}

}
