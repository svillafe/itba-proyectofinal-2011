package argendata.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.sun.jersey.spi.resource.Singleton;


@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public final class GSONReader implements MessageBodyReader<Object> {

	private Gson gson;
	private final Logger logger = Logger.getLogger(GSONReader.class);

	public GSONReader() {
		this.gson = GsonProvider.getGson();
	}

	public boolean isReadable(Class<?> arg0, Type arg1, Annotation[] arg2,
			MediaType arg3) {
		return true;
	}


	public Object readFrom(Class<Object> arg0, Type arg1, Annotation[] arg2,
			MediaType arg3, MultivaluedMap<String, String> arg4,
			InputStream is) throws IOException, WebApplicationException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		StringBuilder sb = new StringBuilder();
		int c;

		while ((c = reader.read()) != -1) {
			sb.append((char)c);
		}
		logger.debug(sb.toString());

		is.close();

		return gson.fromJson(sb.toString(), arg0);
	}


}