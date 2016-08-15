package aero.champ.projectpera.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.attachment.Attachment;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInlineFrame;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public class FalcoCrawler extends Thread {

	private static final String FALCO_DOMAIN = "http://falcoweb.champ.aero";

	private static final Logger LOG = LoggerFactory.getLogger(FalcoCrawler.class);

	private static final String START_DATE = "01/07/2016"; //mbbb

	private static final String END_DATE = "15/07/2016";

	private static final String FILE_DATE = "18JUL16-";

	private static final String EMPLOYEE_CARD_NO = "25370";

	private static final String EMPLOYEE_NAME = "MARCO ARUTA";

	private static final String URL = "http://falcoweb.champ.aero/FALCOWEB/(S(3wr5vyiqzhg3icucabjj31aq))/Report/TAReport.aspx";
	
	private static final String OUT_FOLDER = ".//out//";
	
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
//			getReportPdf(pageResult);
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

		pageResult.executeJavaScript("var event = document.createEvent('Event');");
		ScriptResult result = pageResult
				.executeJavaScript(pdfExport.getOnClickAttribute());

		try {
            Page saveResult  = result.getNewPage();
            LOG.info("POST: " + saveResult.getWebResponse().getWebRequest().toString());
            LOG.info("IsHtml: " + saveResult.isHtmlPage());
            LOG.info("URL: " + saveResult.getUrl().toString());
			
			if (saveResult.isHtmlPage()) {
				exportToFile("Result_"+System.currentTimeMillis()+".html", saveResult.getWebResponse().getContentAsStream());
			} else {
                Attachment attch = new Attachment(saveResult);
                exportToFile(attch.getSuggestedFilename(), saveResult.getWebResponse().getContentAsStream());
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public void getReportCsv(HtmlPage pageResult) {
		try {
            pageResult.getWebClient().getOptions().setThrowExceptionOnScriptError(false);
			pageResult.initialize();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		try {
            HtmlInput saveFormat = (HtmlInput) pageResult.getFirstByXPath("//input[contains(@id, 'SaveFormat_VI')]");
            LOG.info(saveFormat.getId());
            saveFormat.setValueAttribute("csv");
			LOG.info(saveFormat.getValueAttribute());
			pageResult.executeJavaScript(saveFormat.getOnChangeAttribute());

            HtmlInput saveFormat2 = (HtmlInput) pageResult.getFirstByXPath("//input[contains(@id, 'SaveFormat_I')]");
            LOG.info(saveFormat2.getId());
            saveFormat2.setValueAttribute("Csv");
			LOG.info(saveFormat2.getValueAttribute());
			pageResult.executeJavaScript(saveFormat.getOnChangeAttribute());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		HtmlTableDataCell pdfExport = (HtmlTableDataCell) pageResult
				.getFirstByXPath("//td[contains(@title, 'Export a report and save it to the disk')]");
		LOG.info(pdfExport.getAttribute("title"));
		
		pageResult.executeJavaScript("var event = document.createEvent('Event');");
		ScriptResult result = pageResult
				.executeJavaScript(pdfExport.getOnClickAttribute());
		LOG.info("Script: " + result.getJavaScriptResult().toString());
		
		try {
            Page saveResult = pageResult;

            LOG.info("POST: " + saveResult.getWebResponse().getWebRequest().toString());
            LOG.info("IsHtml: " + saveResult.isHtmlPage());
            LOG.info("URL: " + saveResult.getUrl().toString());
			
			if (saveResult.isHtmlPage()) {
                HtmlInlineFrame frame = (HtmlInlineFrame) pageResult.getFirstByXPath("//iframe[contains(@id, 'DXPrinter')]");
                String src = frame.getSrcAttribute();
                LOG.info("DXPrinter Src: " + src);

                Page page = client.getPage(FALCO_DOMAIN + src);
                LOG.info("Page is HTML? " + page.isHtmlPage());
                Attachment attch = new Attachment(page);
                exportToFile(attch.getSuggestedFilename(), page.getWebResponse().getContentAsStream());
			} else {
				exportToFile("Result_"+System.currentTimeMillis()+".csv", saveResult.getWebResponse().getContentAsStream());
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	public void exportToFile(String outputFile, InputStream in) {
		OutputStream os = null;

		try {
			File outFolder = new File(OUT_FOLDER);
            if (!outFolder.exists()) {
                outFolder.mkdir();
            }

            File outFile = new File(OUT_FOLDER + FILE_DATE + outputFile);
			os = new FileOutputStream(outFile);

            IOUtils.copy(in, os);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
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
