package com.zea.company.route_u.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ResponseGeneral {
    List<String> html_attributions;
    List<results> results;

    public ResponseGeneral(List<String> html_attributions, List<results> results) {
        this.html_attributions = html_attributions;
        this.results = results;
    }

    public List getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List html_attributions) {
        this.html_attributions = html_attributions;
    }

    public List<results> getResultsArrayList() {
        return results;
    }

    public void setResultsArrayList(List<results> resultsArrayList) {
        this.results = resultsArrayList;
    }
}
