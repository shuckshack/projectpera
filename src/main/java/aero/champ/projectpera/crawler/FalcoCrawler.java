package aero.champ.projectpera.crawler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public class FalcoCrawler extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(FalcoCrawler.class);

	private static final String END_DATE = "15/04/2016";

	private static final String START_DATE = "01/04/2016";

	private static final String EMPLOYEE_CARD_NO = "25370";

	private static final String EMPLOYEE_NAME = "MARCO ARUTA";

	private static final String URL = "http://falcoweb.champ.aero/FALCOWEB/(S(3wr5vyiqzhg3icucabjj31aq))/Report/TAReport.aspx";
	
	private static final String OUT_FOLDER = "out/";
	
	private WebClient client;

	public static void main(String[] args) throws Exception {
		final FalcoCrawler crawler = new FalcoCrawler();
		crawler.run();
	}

	public FalcoCrawler() {
		setup();
	}

	public void setup() {
		client = new WebClient(BrowserVersion.FIREFOX_38);
		client.getOptions().setThrowExceptionOnFailingStatusCode(false);
		client.getOptions().setThrowExceptionOnScriptError(false);
		client.getOptions().setRedirectEnabled(true);
		client.getOptions().setCssEnabled(false);
		client.setIncorrectnessListener(new IncorrectnessListener() {
			public void notify(String message, Object origin) {
				//Do nothing
			}
		});

		client.setAjaxController(new NicelyResynchronizingAjaxController());
	}

	private HtmlPage fillAndSubmitForm(HtmlPage page) throws Exception {
		HtmlDivision btnOk = (HtmlDivision) page
				.getFirstByXPath("//div[contains(@id, 'btnOk')]");
		LOG.info(btnOk.getId());

		HtmlInput cardName = (HtmlInput) page
				.getFirstByXPath("//input[contains(@id, 'cboCardName')]");
		cardName.click();
		cardName.setValueAttribute(EMPLOYEE_NAME);
		LOG.info(cardName.getId());
		LOG.info(cardName.getValueAttribute());

		HtmlInput radioDailySummary = (HtmlInput) page
				.getFirstByXPath("//input[contains(@id, 'rb21')]");
		LOG.info(radioDailySummary.getId());
		radioDailySummary.setChecked(true);

		HtmlInput radioAllCard = (HtmlInput) page
				.getFirstByXPath("//input[contains(@id, 'ChkAllCard')]");
		LOG.info(radioAllCard.getId());
		radioAllCard.setChecked(false);

		HtmlInput radioStartCardNo = (HtmlInput) page
				.getFirstByXPath("//input[contains(@id, 'chk1')]");
		LOG.info(radioStartCardNo.getId());
		radioStartCardNo.setChecked(true);

		HtmlInput cardNoStrt = (HtmlInput) page
				.getFirstByXPath("//input[contains(@id, 'cboStartCard')]");
		HtmlInput cardNoEnd = (HtmlInput) page
				.getFirstByXPath("//input[contains(@id, 'cboEndCard')]");
		cardNoStrt.setValueAttribute(EMPLOYEE_CARD_NO);
		cardNoEnd.setValueAttribute(EMPLOYEE_CARD_NO);

		HtmlInput dateFrom = (HtmlInput) page
				.getFirstByXPath("//input[contains(@id, 'StartDate')]");
		HtmlInput dateTo = (HtmlInput) page
				.getFirstByXPath("//input[contains(@id, 'EndDate')]");
		dateFrom.setValueAttribute(START_DATE);
		dateTo.setValueAttribute(END_DATE);

		HtmlPage pageResult = btnOk.click();
		return pageResult;
	}

	public void run() {
		try {
			final HtmlPage page = client.getPage(URL);
			HtmlDivision btnOk = (HtmlDivision) page
					.getFirstByXPath("//div[contains(@id, 'btnOk')]");

			HtmlPage pageResult = fillAndSubmitForm(page);
			LOG.info(pageResult.asText());
			if (pageResult.asText().contains("Login")) {
				pageResult = btnOk.click();
				LOG.info(pageResult.asText());
				if (pageResult.asText().contains("Daily Report")) {
					pageResult = fillAndSubmitForm(pageResult);
					LOG.info(pageResult.asText());
				}
			}
			
			getReportHtml(pageResult);
			getReportPdf(pageResult);
			getReportCsv(pageResult);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public void getReportHtml(HtmlPage pageResult) {
		LOG.info(pageResult.getTitleText());
		HtmlTableDataCell printReport = (HtmlTableDataCell) pageResult
				.getFirstByXPath("//td[contains(@title, 'Print the report')]");
		LOG.info(printReport.getAttribute("title"));

		String printClick = printReport.getOnClickAttribute();
		LOG.info(printClick);
		ScriptResult result = pageResult
				.executeJavaScript("aspxMIClick('', 'ReportToolbar1_Menu', '1')");

		Page reportPage = result.getNewPage();
		LOG.info("IsHtml: " + reportPage.isHtmlPage());
		LOG.info("URL: " + reportPage.getUrl().toString());

		try {
			exportToFile("Report_"+System.currentTimeMillis()+".html", reportPage.getWebResponse().getContentAsStream());
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public void getReportPdf(HtmlPage pageResult) {
		HtmlTableDataCell pdfExport = (HtmlTableDataCell) pageResult
				.getFirstByXPath("//td[contains(@title, 'Export a report and save it to the disk')]");
		LOG.info(pdfExport.getAttribute("title"));
		
		HtmlInput saveFormat = (HtmlInput) pageResult.getFirstByXPath("//input[contains(@id, 'SaveFormat')]");
		LOG.info(saveFormat.getId());
		saveFormat.setValueAttribute("Pdf");

		ScriptResult result = pageResult
				.executeJavaScript("aspxMIClick('', 'ReportToolbar1_Menu', '11')");

		try {
            Page saveResult  = result.getNewPage();
            LOG.info("POST: " + saveResult.getWebResponse().getWebRequest().toString());
            LOG.info("IsHtml: " + saveResult.isHtmlPage());
            LOG.info("URL: " + saveResult.getUrl().toString());
			
			if (saveResult.isHtmlPage()) {
				exportToFile("Result_"+System.currentTimeMillis()+".html", saveResult.getWebResponse().getContentAsStream());
			} else {
				exportToFile("Result_"+System.currentTimeMillis()+".pdf", saveResult.getWebResponse().getContentAsStream());
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public void getReportCsv(HtmlPage pageResult) {
		HtmlTableDataCell pdfExport = (HtmlTableDataCell) pageResult
				.getFirstByXPath("//td[contains(@title, 'Export a report and save it to the disk')]");
		LOG.info(pdfExport.getAttribute("title"));
		
		try {
            HtmlInput saveFormat = (HtmlInput) pageResult.getFirstByXPath("//input[contains(@id, 'SaveFormat_VI')]");
            LOG.info(saveFormat.getId());
			saveFormat.click();
            saveFormat.setValueAttribute("csv");
			LOG.info(saveFormat.getValueAttribute());

            HtmlInput saveFormat2 = (HtmlInput) pageResult.getFirstByXPath("//input[contains(@id, 'SaveFormat_I')]");
            LOG.info(saveFormat2.getId());
			saveFormat2.click();
            saveFormat2.setValueAttribute("Csv");
			LOG.info(saveFormat2.getValueAttribute());

		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		
//        ScriptResult result = pageResult.executeJavaScript("aspxETextChanged('ReportToolbar1_Menu_ITCNT13_SaveFormat')");
//        LOG.info(result.getJavaScriptResult().toString());
		
		ScriptResult result = pageResult
				.executeJavaScript("aspxMIClick('', 'ReportToolbar1_Menu', '11')");
		LOG.info(result.getJavaScriptResult().toString());

		try {
            Page saveResult  = result.getNewPage();

            LOG.info("POST: " + saveResult.getWebResponse().getWebRequest().toString());
            LOG.info("IsHtml: " + saveResult.isHtmlPage());
            LOG.info("URL: " + saveResult.getUrl().toString());
			
			if (saveResult.isHtmlPage()) {
				exportToFile("Result_"+System.currentTimeMillis()+".html", saveResult.getWebResponse().getContentAsStream());
			} else {
				exportToFile("Result_"+System.currentTimeMillis()+".csv", saveResult.getWebResponse().getContentAsStream());
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	public void exportToFile(String outputFile, InputStream is) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(OUT_FOLDER + outputFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
                LOG.error(e.getMessage(), e);
			}
		}
	}

}
