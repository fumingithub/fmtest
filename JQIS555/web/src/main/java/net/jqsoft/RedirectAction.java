package net.jqsoft;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Administrator on 2015/12/14.
 */
public class RedirectAction extends ActionSupport {

	public String execute() throws Exception {
//		File file = new File("D:/大庆四县一区行政区划.csv");
//		List<String[]> strsList = CSVUtils.readCsv(file, "GBK");
//		List<ImportTemp> tempList = new ArrayList<ImportTemp>();
//		ImportTemp temp = null;
//		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//		for (int i = 1; i < strsList.size(); i++) {
//			String[] tempStr = strsList.get(i);
//			temp = new ImportTemp();
//			temp.setId(UUID.generate("AREA"));
//			temp.setSkey(tempStr[0]);
//			temp.setScatalogcode(tempStr[1]);
//			temp.setScatalogname(tempStr[2]);
//			temp.setSrangecodevalue(tempStr[3]);
//			temp.setSrangecodename(tempStr[4]);
//			temp.setShelp(tempStr[5]);
//			temp.setSunit(tempStr[6]);
//			temp.setIflag(tempStr[7]);
//			temp.setDupdatedate(df.parse(tempStr[8]));
//			temp.setSmemo1(tempStr[9]);
//			temp.setSmemo2(tempStr[10]);
//			temp.setSmemo3(tempStr[11]);
//			temp.setSmemo4(tempStr[12]);
//			temp.setSmemo5(tempStr[13]);
//			temp.setSmemo6(tempStr[14]);
//			temp.setSmemo7(tempStr[15]);
//			temp.setSmemo8(tempStr[16]);
//			temp.setSmemo9(tempStr[17]);
//			temp.setSmemo10(tempStr[18]);
//			tempList.add(temp);
//			if (tempList.size() == 20000 || i == strsList.size()-1) {
//				try {
//					importTempService.save(tempList);
//					tempList.clear();
//				} catch (Exception e) {
//					e.printStackTrace();
//					throw e;
//				}
//			}
//		}
		
//		File file = new File("D:/大庆四县一区行政区划.csv");
//		List<String[]> strsList = CSVUtils.readCsv(file, "GBK");
//		List<NcmsAreaDictTmp> tempList = new ArrayList<NcmsAreaDictTmp>();
//		NcmsAreaDictTmp temp = null;
//		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//		for (int i = 0; i < strsList.size(); i++) {
//			String[] tempStr = strsList.get(i);
//			temp = new NcmsAreaDictTmp();
//			temp.setCode(tempStr[0]);
//			temp.setName(tempStr[1]);
//			tempList.add(temp);
//			
//			if (tempList.size() == 20000 || i == strsList.size()-1) {
//				try {
//					ncmsAreaDictTmpService.save(tempList);
//					tempList.clear();
//				} catch (Exception e) {
//					e.printStackTrace();
//					throw e;
//				}
//			}
//		}

		return SUCCESS;
	}

}
