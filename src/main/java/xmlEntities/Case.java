package xmlEntities;

import xmlEntities.caseData.*;
import xmlEntities.caseData.participantData.Participant;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Case {
    private Vehicle vehicle;
    private Admin_Data admin_data;
   // private Calculation calculation;
    private List<Participant> participants;
    private List<Attachment> attachments;
    private ClaimnetInfo claimnetInfo;

    public Case(){

    }

    public Case(Vehicle vehicle, Admin_Data admin_data, List<Participant> participants, List<Attachment> attachments, ClaimnetInfo claimnetInfo) {
        this.vehicle = vehicle;
        this.admin_data = admin_data;
       // this.calculation = calculation;
        this.participants = participants;
        this.attachments = attachments;
        this.claimnetInfo = claimnetInfo;
    }

    @XmlElement
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @XmlElement
    public Admin_Data getAdmin_data() {
        return admin_data;
    }

    public void setAdmin_data(Admin_Data admin_data) {
        this.admin_data = admin_data;
    }

   /* @XmlElement
    public Calculation getCalculation() {
        return calculation;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }*/

    @XmlElement
    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    @XmlElement
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @XmlElement
    public ClaimnetInfo getClaimnetInfo() {
        return claimnetInfo;
    }

    public void setClaimnetInfo(ClaimnetInfo claimnetInfo) {
        this.claimnetInfo = claimnetInfo;
    }
}
