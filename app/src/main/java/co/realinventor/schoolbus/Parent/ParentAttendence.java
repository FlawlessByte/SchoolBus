package co.realinventor.schoolbus.Parent;

public class ParentAttendence {
    public String date, name;
    public boolean am, pm;

    public ParentAttendence(String date, String name, boolean am, boolean pm) {
        this.date = date;
        this.name = name;
        this.am = am;
        this.pm = pm;
    }

    public ParentAttendence() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
