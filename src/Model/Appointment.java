package Model;

import java.time.LocalDate;

/*Author: Anthony E Rodriguez
 *Date: 02/23/2019
 *Description: Contains the table model for mysql Appointment
 */
public class Appointment extends Address{

    private String appointmentId;
    private String title;
    private String description;
    private LocalDate date;
    private String type;
    private String location;
    private String contact;
    private String url;
    private String start;
    private String end;
    private String month;
    private String typeCount;

	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String timestamp) {
		this.start = timestamp;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTypeCount() {
		return typeCount;
	}
	public void setTypeCount(String typeCount) {
		this.typeCount = typeCount;
	}


}
