package argendata.Start;
import java.util.Properties;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class Start {
	static final String LOG_PROPERTIES_FILE = "LOG4J.properties";
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Properties logProperties = new Properties();
//		try {
//			logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
//			PropertyConfigurator.configure(logProperties);
//		} catch (IOException e) {
//			throw new RuntimeException("Unable to load logging property "
//					+ LOG_PROPERTIES_FILE);
//		}
		
		
		Server server = new Server();
		SocketConnector connector = new SocketConnector();
		
		connector.setMaxIdleTime(1000*60*60);
		connector.setSoLingerTime(-1);
		connector.setPort(8090);
		server.setConnectors(new Connector[] {connector});
		
		WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/argendata");
		bb.setWar("src/main/webapp");
		server.addHandler(bb);
		
		try{
			System.out.println("ARRANCANDO JETTY");
			server.start();
			
			while (System.in.available()==0){
				Thread.sleep(5000);
			}
			
			server.stop();
			server.join();
			
		}catch (Exception a){
			a.printStackTrace();
			System.exit(100);
		}
		
		
	}
}
