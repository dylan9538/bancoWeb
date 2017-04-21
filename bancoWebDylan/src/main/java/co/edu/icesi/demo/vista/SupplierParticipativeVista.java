package co.edu.icesi.demo.vista;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectoneradio.SelectOneRadio;

@ManagedBean
@ViewScoped
public class SupplierParticipativeVista {
	
	private final static int VitalDatosFirstYear = 2013;

	private SelectCheckboxMenu menuCustomers;
	private List<SelectItem> listCustomers;

	private SelectCheckboxMenu menuProductsF;
	private List<SelectItem> listProductsF;

	private SelectCheckboxMenu menuCountries;
	private List<SelectItem> listCountries, listYear, listMonth;

	public void eventParticipativeSupplier() {
			System.out.println("Generate Participative Analysis!");
	}

	public List<SelectItem> getListYear() {
		
		listYear = new ArrayList<SelectItem>();
		
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		
		for (int y=VitalDatosFirstYear; y<=currentYear; y++){
			listYear.add( new SelectItem(y, y+"") );
		}
		
		return listYear;
	}

	public void setListYear(List<SelectItem> listYear) {
		this.listYear = listYear;
	}

	public List<SelectItem> getListMonth() {
		
		listMonth = new ArrayList<SelectItem>();
		
		listMonth.add(new SelectItem(1, "January"));
		listMonth.add(new SelectItem(2, "February"));
		listMonth.add(new SelectItem(3, "March"));

		listMonth.add(new SelectItem(4, "April"));
		listMonth.add(new SelectItem(5, "May"));
		listMonth.add(new SelectItem(6, "June"));

		listMonth.add(new SelectItem(7, "July"));
		listMonth.add(new SelectItem(8, "August"));
		listMonth.add(new SelectItem(9, "September"));

		listMonth.add(new SelectItem(10, "October"));
		listMonth.add(new SelectItem(11, "November"));
		listMonth.add(new SelectItem(12, "December"));

		return listMonth;
	}

	public void setListMonth(List<SelectItem> listMonth) {
		this.listMonth = listMonth;
	}

	public SelectOneMenu getMenuInitialYear() {
		return menuInitialYear;
	}

	public void setMenuInitialYear(SelectOneMenu menuInitialYear) {
		this.menuInitialYear = menuInitialYear;
	}

	public SelectOneMenu getMenuFinalYear() {
		return menuFinalYear;
	}

	public void setMenuFinalYear(SelectOneMenu menuFinalYear) {
		this.menuFinalYear = menuFinalYear;
	}

	public SelectOneMenu getMenuInitialMonth() {
		return menuInitialMonth;
	}

	public void setMenuInitialMonth(SelectOneMenu menuInitialMonth) {
		this.menuInitialMonth = menuInitialMonth;
	}

	public SelectOneMenu getMenuFinalMonth() {
		return menuFinalMonth;
	}

	public void setMenuFinalMonth(SelectOneMenu menuFinalMonth) {
		this.menuFinalMonth = menuFinalMonth;
	}

	private SelectOneMenu menuInitialYear, menuFinalYear, menuInitialMonth, menuFinalMonth;

	public SelectOneRadio getMenuTimes() {
		return menuTimes;
	}

	public void setMenuTimes(SelectOneRadio menuTimes) {
		this.menuTimes = menuTimes;
	}

	public SelectOneRadio getMenuUni() {
		return menuUni;
	}

	public void setMenuUni(SelectOneRadio menuUni) {
		this.menuUni = menuUni;
	}

	private SelectOneRadio menuTimes, menuUni;

	public SelectCheckboxMenu getMenuCustomers() {
		return menuCustomers;
	}

	public void setMenuCustomers(SelectCheckboxMenu menuCustomers) {
		this.menuCustomers = menuCustomers;
	}

	public List<SelectItem> getListCustomers() {
			
		return listCustomers;
	}

	public void setListCustomers(List<SelectItem> listCustomers) {
		this.listCustomers = listCustomers;
	}

	public SelectCheckboxMenu getMenuProductsF() {
		return menuProductsF;
	}

	public void setMenuProductsF(SelectCheckboxMenu menuProductsF) {
		this.menuProductsF = menuProductsF;
	}

	public List<SelectItem> getListProductsF() {
		return listProductsF;
	}

	public void setListProductsF(List<SelectItem> listProductsF) {
		this.listProductsF = listProductsF;
	}

	public SelectCheckboxMenu getMenuCountries() {
		return menuCountries;
	}

	public void setMenuCountries(SelectCheckboxMenu menuSuppliers) {
		this.menuCountries = menuSuppliers;
	}

	public List<SelectItem> getListCountries() {
		return listCountries;
	}

	public void setListCountries(List<SelectItem> listCountries) {
		this.listCountries = listCountries;
	}

}
