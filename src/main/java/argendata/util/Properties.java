package argendata.util;

public class Properties {
	private String solrServer;
	private String reCaptchaPrivateKey;
	private String reCaptchaPublicKey;
	private String smtpServer;
	private String smtpPort;
	private String mailAddressFrom;
	private String mailProtocol;
	private String mailUsername;
	private String mailPassword;
	private String mainURL;
	private String sesameServer;
	private String sesameRepositoryName;
	private String sesameQueryLimit;
	private String expirationMillis;
	private String namespace;
	private String maxQueryLength;
	private String defaultQuery;
	private String mailStartTLSEnable;
	private String projectName;
	private String diaryMillis;
	
	public String getSesameServer() {
		return sesameServer;
	}

	public void setSesameServer(String sesameServer) {
		this.sesameServer = sesameServer;
	}

	public String getSesameRepositoryName() {
		return sesameRepositoryName;
	}

	public void setSesameRepositoryName(String sesameRepositoryName) {
		this.sesameRepositoryName = sesameRepositoryName;
	}

	public String getMainURL() {
		return mainURL;
	}

	public void setMainURL(String mainURL) {
		this.mainURL = mainURL;
	}

	public String getMailUsername() {
		return mailUsername;
	}

	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getSolrServer() {
		return solrServer;
	}

	public void setSolrServer(String solrServer) {
		this.solrServer = solrServer;
	}

	public String getReCaptchaPrivateKey() {
		return reCaptchaPrivateKey;
	}

	public void setReCaptchaPrivateKey(String reCaptchaPrivateKey) {
		this.reCaptchaPrivateKey = reCaptchaPrivateKey;
	}

	public String getReCaptchaPublicKey() {
		return reCaptchaPublicKey;
	}

	public void setReCaptchaPublicKey(String reCaptchaPublicKey) {
		this.reCaptchaPublicKey = reCaptchaPublicKey;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getMailAddressFrom() {
		return mailAddressFrom;
	}

	public void setMailAddressFrom(String mailAddressFrom) {
		this.mailAddressFrom = mailAddressFrom;
	}

	public String getMailProtocol() {
		return mailProtocol;
	}

	public void setMailProtocol(String mailProtocol) {
		this.mailProtocol = mailProtocol;
	}

	public String getSesameQueryLimit() {
		return sesameQueryLimit;
	}

	public void setSesameQueryLimit(String sesameQueryLimit){
		this.sesameQueryLimit = sesameQueryLimit;
	}

	public String getExpirationMillis() {
		return expirationMillis;
	}
	
	public void setExpirationMillis(String expirationMillis){
		this.expirationMillis= expirationMillis;
	}

	public String getNamespace() {
		return namespace;
	}
	
	public void setNamespace(String namespace){
		this.namespace = namespace;
	}

	public String getMaxQueryLength() {
		return this.maxQueryLength;
	}
	
	public void setMaxQueryLength(String maxQueryLength){
		this.maxQueryLength = maxQueryLength;
	}

	public String getDefaultQuery() {
		
		return defaultQuery;
	}
	
	public void setDefaultQuery(String query){
		this.defaultQuery = query;
	}

	public final String getMailStartTLSEnable() {
		return mailStartTLSEnable;
	}

	public final void setMailStartTLSEnable(String mailStartTLSEnable) {
		this.mailStartTLSEnable = mailStartTLSEnable;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName=projectName;
	}

	public String getDiaryMillis() {
		return diaryMillis;
	}
	
	public void setDiaryMillis(String expirationMillis){
		this.diaryMillis= expirationMillis;
	}

	
}
