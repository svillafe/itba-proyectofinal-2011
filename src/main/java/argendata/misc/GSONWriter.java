package argendata.misc;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;


import com.google.gson.Gson;
import com.sun.jersey.spi.resource.Singleton;


@SuppressWarnings("rawtypes")
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public final class GSONWriter implements MessageBodyWriter<Object> {

	private Gson gson;

	public GSONWriter() {
		this.gson = GsonProvider.getGson();
	}

	public long getSize(Object t, Class type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	public boolean isWriteable(Class type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	public void writeTo(Object t, Class type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap httpHeaders, OutputStream entityStream)
	throws IOException, WebApplicationException {
		entityStream.write(gson.toJson(t).getBytes("UTF-8"));

	}
}