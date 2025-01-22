package others.model;

public class Event {
    private Interruption interruption;
    private AbroadProgramme abroadProgramme;

    // Constructor for Interruption
    public Event(Interruption interruption) {
        this.interruption = interruption;
        this.abroadProgramme = null;
    }

    // Constructor for AbroadProgramme
    public Event(AbroadProgramme abroadProgramme) {
        this.abroadProgramme = abroadProgramme;
        this.interruption = null;
    }


    public String getReason() {
        if (interruption != null) {
            return interruption.getReason();
        } else if (abroadProgramme != null) {
            return abroadProgramme.getUniversity();
        }
        return "";
    }

    public String getStartDate() {
        if (interruption != null) {
            return interruption.getStartDate();
        } else if (abroadProgramme != null) {
            return abroadProgramme.getStartDate();
        }
        return "";
    }

    public String getEndDate() {
        if (interruption != null) {
            return interruption.getEndDate();
        } else if (abroadProgramme != null) {
            return abroadProgramme.getEndDate();
        }
        return "";
    }
}
