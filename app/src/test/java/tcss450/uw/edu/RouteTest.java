package tcss450.uw.edu;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import tcss450.uw.edu.team12.model.Route;

/**
 * Created by bethany on 5/21/16.
 */
public class RouteTest extends TestCase {

    private Route mRoute;

    @Before
    public void setUp() {
        mRoute = new Route("55555", "12:12:20", "Tacoma to South Seattle", "12", "415th ST");
    }

    @Test
    public void testRouteName() {
        assertEquals("55555", mRoute.getRouteName());
    }

    @Test
    public void testSetNullRouteName() {
        try {
            mRoute.setRouteName(null);
            fail("Route name can be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testRouteHeadSign() {
        assertEquals("Tacoma to South Seattle", mRoute.getTripHeadSign());
    }

    @Test
    public void testSetNullHeadSign() {
        try {
            mRoute.setTripHeadSign(null);
            fail("Head sign can't be set to null");
        }
        catch (IllegalArgumentException e) {
        }
    }


    @Test
    public void testRouteDepartureTime() {
        assertEquals("12:12:20", mRoute.getDepartureTime());
    }

    @Test
    public void testSetNullRouteDepartureTime() {
        try {
            mRoute.setDepartureTime(null);
            fail("Departure time can't be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testMinutes() {
        assertEquals("12", mRoute.getMinutes());
    }

    @Test
    public void testSetNullMinutes() {
        try {
            mRoute.setMinutes(null);
            fail("Minutes can't be set to null");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testConstructor() {
        assertNotNull(mRoute);
    }

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