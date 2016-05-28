package tcss450.uw.edu;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import tcss450.uw.edu.team12.model.Stop;

/**
 * Created by bethany on 5/21/16.
 */
public class StopTest extends TestCase {

    private Stop mStop;

    @Before
    public void setUp() {
        mStop = new Stop("8888", "222nd ST Kent");
    }

    @Test
    public void testStopId() {
        assertEquals("8888", mStop.getStopId());
    }

    @Test
    public void testSetNullStopId() {
        try {
            mStop.setStopId(null);
            fail("Stop id can be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testSetNullStopName() {
        try {
            mStop.setStopName(null);
            fail("Stop name can be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testConstructor() {
        Stop course = new Stop("8889", "029th ST");
        assertNotNull(course);
    }

    @Test
    public void testParseCourseJSON() {
        String courseJSON =
                "[{\"stop_id\":\"890983\",\"stop_name\":\"4555th ST\"}]";
        String message =  Stop.parseCourseJSON(courseJSON
                , new ArrayList<Stop>());
        assertTrue("JSON With Valid String", message == null);
    }


}