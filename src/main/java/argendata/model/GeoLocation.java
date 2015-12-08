package argendata.model;
import org.openrdf.elmo.annotations.rdf;
@rdf("http://www.tidalwave.it/rdf/geo/2009/02/22#location")
public class GeoLocation
  {
    @rdf("http://www.w3.org/2003/01/geo/wgs84_pos#lat")
    private Double latitude;
    @rdf("http://www.w3.org/2003/01/geo/wgs84_pos#long")
    private Double longitude;
    @rdf("http://www.w3.org/2003/01/geo/wgs84_pos#alt")
    private Double altitude;
    @rdf("http://www.tidalwave.it/rdf/geo/2009/02/22#code")
    private String code;
    public Double getAltitude()
      {
        return altitude;
      }
    public void setAltitude(Double altitude)
      {
        this.altitude = altitude;
      }
    public String getCode()
      {
        return code;
      }
    public void setCode(String code)
      {
        this.code = code;
      }
    public Double getLatitude()
      {
        return latitude;
      }
    public void setLatitude(Double latitude)
      {
        this.latitude = latitude;
      }
    public Double getLongitude()
      {
        return longitude;
      }
    public void setLongitude(Double longitude)
      {
        this.longitude = longitude;
      }
    public String toString()
      {
        return String.format("GeoLocation[%s %f %f]", code, latitude, longitude);
      }
  }