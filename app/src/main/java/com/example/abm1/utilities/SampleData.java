package com.example.abm1.utilities;

import com.example.abm1.models.TermEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {
    private static final String SAMPLE_TEXT_1 = "Fall 2019";
    private static final String SAMPLE_TEXT_2 = "Spring 2020";
    private static final String SAMPLE_TEXT_3 ="Fall 2020";

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND,diff);
        return cal.getTime();
    }
        //Sample data for terms/////////////////////////////////////////////////////////////////////
        public static List<TermEntity> getTerms() {
        List<TermEntity> terms = new ArrayList<>();



        terms.add(new TermEntity(getDate(0), getDate(-1), SAMPLE_TEXT_1));
        terms.add(new TermEntity(getDate(-2), getDate(-3), SAMPLE_TEXT_2));
        terms.add(new TermEntity(getDate(-4), getDate(-5), SAMPLE_TEXT_3));
        return terms;
        }

        ///////////////////////////////////////////////////////////////////////////////////////////
}
