package com.example.abm1;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.abm1.database.AppDatabase;
import com.example.abm1.database.TermDAO;
import com.example.abm1.models.TermEntity;
import com.example.abm1.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    public static final String TAG = "junittest";
    private AppDatabase testDb;
    private TermDAO testDAO;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        testDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();

        testDAO = testDb.termDAO();
        Log.i(TAG, "Create Database");
    }

    @After
    public void closeDatabase() {
        testDb.close();
        Log.i(TAG, "Closed Database");
    }

    @Test
    public void createAndRetrieveTerms() {
        testDAO.insertAllTerms(SampleData.getTerms());
        int count = testDAO.getCount();
        Log.i(TAG, "Create and Retrieve terms test: count = " + count);
        assertEquals(SampleData.getTerms().size(),count);
    }

    @Test
    public void compareStrings() {
        testDAO.insertAllTerms((SampleData.getTerms()));
        TermEntity orig = SampleData.getTerms().get(0);
        TermEntity fromDb = testDAO.getTermById(1);
        assertEquals(orig.getTermTitle(), fromDb.getTermTitle());
        assertEquals(1,fromDb.getId());
    }

}
