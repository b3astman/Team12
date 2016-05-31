package tcss450.uw.edu;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import tcss450.uw.edu.team12.model.Route;

/**
 * Tests for the Route class.
 *
 * Created by Bethany Eastman on 5/21/16.
 */
public class RouteTest extends TestCase {

    private Route mRoute;

    @Before
    public void setUp() {
        mRoute = new Route("55555", "12:12:20", "Tacoma to South Seattle", "12", "415th ST");
    }

    /**
     * Test that a route name is returned correctly.
     */
    @Test
    public void testRouteName() {
        assertEquals("55555", mRoute.getRouteName());
    }

    /**
     * Test that a Route name can not be set to null.
     */
    @Test
    public void testSetNullRouteName() {
        try {
            mRoute.setRouteName(null);
            fail("Route name can be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    /**
     * Test trip head sign for a Route is returned correctly.
     */
    @Test
    public void testRouteHeadSign() {
        assertEquals("Tacoma to South Seattle", mRoute.getTripHeadSign());
    }

    /**
     * Test that a trip head sign for a Route can't be set to null.
     */
    @Test
    public void testSetNullHeadSign() {
        try {
            mRoute.setTripHeadSign(null);
            fail("Head sign can't be set to null");
        }
        catch (IllegalArgumentException e) {
        }
    }


    /**
     * Test a departure time is returned correctly.
     */
    @Test
    public void testRouteDepartureTime() {
        assertEquals("12:12:20", mRoute.getDepartureTime());
    }

    /**
     * Test that a departure time can not be set to null.
     */
    @Test
    public void testSetNullRouteDepartureTime() {
        try {
            mRoute.setDepartureTime(null);
            fail("Departure time can't be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    /**
     * Test that the minutes to arrival for Route is returned correctly.
     */
    @Test
    public void testMinutes() {
        assertEquals("12", mRoute.getMinutes());
    }

    /**
     * Test that minutes to arrival can not be set to null.
     */
    @Test
    public void testSetNullMinutes() {
        try {
            mRoute.setMinutes(null);
            fail("Minutes can't be set to null");
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Test that the constructor creates an object.
     */
    @Test
    public void testConstructor() {
        assertNotNull(mRoute);
    }

    /**
     * Test correct JSON objects are parsed.
     */
    @Test
    public void testParseCourseJSON() {
        String courseJSON =
                "[{\"route_short_name\":\"412321\",\"departure_time\":\"12:12:12\"," +
                        "\"trip_headsign\":\"Seattle To Mexico\",\"minutes\":\"1000\"}]";
        String message =  Route.parseCourseJSON(courseJSON
                , new ArrayList<Route>());
        assertTrue("JSON With Valid String", message == null);
    }

}