package pets.ui.mpa.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pets.ui.mpa.connector.ReportsConnectorUi;
import pets.ui.mpa.model.ReportsModel;

@Service
public class ReportsServiceUi {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportsServiceUi.class);
	private final ReportsConnectorUi reportsConnector;
	
	public ReportsServiceUi(ReportsConnectorUi reportsConnector) {
		this.reportsConnector = reportsConnector;
	}
	
	public ReportsModel getCurrentBalancesReport(String username) {
		try {
			return ReportsModel.builder()
					.reportCurrentBalances(reportsConnector.getCurrentBalancesReport(username).getReportCurrentBalances())
					.build();
		} catch (Exception ex) {
			return ReportsModel.builder()
					.errMsg(errMsg(username, ex, "Current Balances Report"))
					.build();
		}
	}
	
	public CompletableFuture<ReportsModel> getCurrentBalancesReportFuture(String username) {
		return CompletableFuture.supplyAsync(() -> getCurrentBalancesReport(username));
	}
	
	public ReportsModel getCashFlowsReport(String username, int selectedYear) {
		try {
			return ReportsModel.builder()
					.selectedYear(String.valueOf(selectedYear))
					.reportCashFlows(reportsConnector.getCashFlowsReport(username, selectedYear).getReportCashFlows())
					.build();
		} catch (Exception ex) {
			return ReportsModel.builder()
					.errMsg(errMsg(username, ex, "Cash Flows Report"))
					.build();
		}
	}
	
	public CompletableFuture<ReportsModel> getCashFlowsReportFuture(String username, int selectedYear) {
		return CompletableFuture.supplyAsync(() -> getCashFlowsReport(username, selectedYear));
	}
	
	public ReportsModel getCategoriesReport(String username, int selectedYear) {
		try {
			return ReportsModel.builder()
					.selectedYear(String.valueOf(selectedYear))
					.reportCategoryTypes(reportsConnector.getCategoriesReport(username, selectedYear).getReportCategoryTypes())
					.build();
		} catch (Exception ex) {
			return ReportsModel.builder()
					.errMsg(errMsg(username, ex, "Categories Report"))
					.build();
		}
	}
	
	public CompletableFuture<ReportsModel> getCategoriesReportFuture(String username, int selectedYear) {
		return CompletableFuture.supplyAsync(() -> getCategoriesReport(username, selectedYear));
	}
	
	private String errMsg(String username, Exception ex, String methodName) {
		logger.error("Exception in {}: {}", methodName, username, ex);
		return String.format("Error in %s! Please Try Again!!!", methodName);
	}

}
