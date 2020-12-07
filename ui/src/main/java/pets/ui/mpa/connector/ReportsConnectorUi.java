package pets.ui.mpa.connector;

import org.springframework.stereotype.Component;

import pets.models.model.ReportsResponse;
import pets.service.service.ReportsServiceSvc;

@Component
public class ReportsConnectorUi {
	
	private final ReportsServiceSvc reportsServiceSvc;
	
	public ReportsConnectorUi(ReportsServiceSvc reportsServiceSvc) {
		this.reportsServiceSvc = reportsServiceSvc;
	}
	
	public ReportsResponse getCurrentBalancesReport(String username) {
		return reportsServiceSvc.getCurrentBalancesReport(username);
	}
	
	public ReportsResponse getCashFlowsReport(String username, int selectedYear) {
		return reportsServiceSvc.getCashFlowsReport(username, String.valueOf(selectedYear));
	}
	
	public ReportsResponse getCategoriesReport(String username, int selectedYear) {
		return reportsServiceSvc.getCategoriesReport(username, String.valueOf(selectedYear));
	}
}
