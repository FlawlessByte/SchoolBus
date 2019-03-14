package co.realinventor.schoolbus.Common;

public class StudentAttendence {
    public String rfid, name;
    public boolean am, pm;

    public StudentAttendence(String rfid, String name, boolean am, boolean pm) {
        this.rfid = rfid;
        this.name = name;
        this.am = am;
        this.pm = pm;
    }

    public StudentAttendence() {
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAm() {
        return am;
    }

    public void setAm(boolean am) {
        this.am = am;
    }

    public boolean isPm() {
        return pm;
    }

    public void setPm(boolean pm) {
        this.pm = pm;
    }
}
