
package com.example.root.ixigosearchapp.model;

import java.util.List;

public class Ixigo {

    private Appendix appendix;
    private List<Flight> flights = null;

    public Appendix getAppendix() {
        return appendix;
    }

    public void setAppendix(Appendix appendix) {
        this.appendix = appendix;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

}
