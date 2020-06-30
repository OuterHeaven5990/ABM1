package com.example.abm1.utilities;

import com.example.abm1.models.TermEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {
    private static final String SAMPLE_TEXT_1 = "Simple Term Description";
    private static final String SAMPLE_TEXT_2 = "A text string with a \nline feed";
    private static final String SAMPLE_TEXT_3 ="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND,diff);
        return cal.getTime();
    }

        public static List<TermEntity> getTerms() {
        List<TermEntity> terms = new ArrayList<>();
        terms.add(new TermEntity(1,getDate(0),SAMPLE_TEXT_1));
        terms.add(new TermEntity(2,getDate(-1),SAMPLE_TEXT_2));
        terms.add(new TermEntity(3,getDate(-2),SAMPLE_TEXT_3));
        return terms;
        }
}
