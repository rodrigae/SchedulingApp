package Model;
/*Author: Anthony E Rodriguez
 *Date: 02/23/2019
 *Description: Contains the table model for mysql Country
 */
public class Country extends City{

    private String countryId;
    private String country;

	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String newId) {
		this.countryId = newId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}




}
