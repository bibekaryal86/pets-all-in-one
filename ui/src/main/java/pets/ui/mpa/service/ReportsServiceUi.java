package pets.ui.mpa.service;

import static org.springframework.util.StringUtils.hasText;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pets.models.model.ReportsResponse;
import pets.models.model.Status;
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
			ReportsResponse reportsResponse = reportsConnector.getCurrentBalancesReport(username);
			
			if (reportsResponse.getStatus() != null && hasText(reportsResponse.getStatus().getErrMsg())) {
				return ReportsModel.builder()
						.errMsg(errMsg(username, "Current Balances Report", reportsResponse.getStatus()))
						.build();
			} else {
				return ReportsModel.builder()
						.reportCurrentBalances(reportsResponse.getReportCurrentBalances())
						.build();
			}
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
			ReportsResponse reportsResponse = reportsConnector.getCashFlowsReport(username, selectedYear);
			
			if (reportsResponse.getStatus() != null && hasText(reportsResponse.getStatus().getErrMsg())) {
				return ReportsModel.builder()
						.selectedYear(String.valueOf(selectedYear))
						.errMsg(errMsg(username, "Cash Flows Report", reportsResponse.getStatus()))
						.build();
			} else {
				return ReportsModel.builder()
						.selectedYear(String.valueOf(selectedYear))
						.reportCashFlows(reportsResponse.getReportCashFlows())
						.build();
			}
		} catch (Exception ex) {
			return ReportsModel.builder()
					.selectedYear(String.valueOf(selectedYear))
					.errMsg(errMsg(username, ex, "Cash Flows Report"))
					.build();
		}
	}
	
	public CompletableFuture<ReportsModel> getCashFlowsReportFuture(String username, int selectedYear) {
		return CompletableFuture.supplyAsync(() -> getCashFlowsReport(username, selectedYear));
	}
	
	public ReportsModel getCategoriesReport(String username, int selectedYear) {
		try {
			ReportsResponse reportsResponse = reportsConnector.getCategoriesReport(username, selectedYear);
			
			if (reportsResponse.getStatus() != null && hasText(reportsResponse.getStatus().getErrMsg())) {
				return ReportsModel.builder()
						.selectedYear(String.valueOf(selectedYear))
						.errMsg(errMsg(username, "Categories Report", reportsResponse.getStatus()))
						.build();
			} else {
				return ReportsModel.builder()
						.selectedYear(String.valueOf(selectedYear))
						.reportCategoryTypes(reportsResponse.getReportCategoryTypes())
						.build();
			}
		} catch (Exception ex) {
			return ReportsModel.builder()
					.selectedYear(String.valueOf(selectedYear))
					.errMsg(errMsg(username, ex, "Categories Report"))
					.build();
		}
	}
	
	public CompletableFuture<ReportsModel> getCategoriesReportFuture(String username, int selectedYear) {
		return CompletableFuture.supplyAsync(() -> getCategoriesReport(username, selectedYear));
	}
	
	private String errMsg(String username, String methodName, Status status) {
		logger.error("Error in {}: {} | {}", username, methodName, status);
		return status.getErrMsg();
	}
	
	private String errMsg(String username, Exception ex, String methodName) {
		logger.error("Exception in {}: {}", username, methodName, ex);
		return String.format("Error in %s! Please Try Again!!!", methodName);
	}
}
