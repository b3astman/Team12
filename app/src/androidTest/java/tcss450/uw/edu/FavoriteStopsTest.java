package tcss450.uw.edu;


import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.robotium.solo.Solo;

import tcss450.uw.edu.team12.MainActivity;
import tcss450.uw.edu.team12.R;


/**
 * Test that favorites are being displayed properly. Uses robotium testing to test
 * that Favorite Stops are loaded correctly.
 *
 * Created by bethany on 5/19/16.
 */
public class FavoriteStopsTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public FavoriteStopsTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    /**
     * Test that load fragment is loaded when clicked.
     */
    public void testFavoritesLoads() {
        solo.clickOnMenuItem("Favorite Stops");
        solo.waitForFragmentById(R.id.fav_fragment);
        assertTrue("Favorites List fragment loaded",
                getActivity().getTitle().equals("Favorite Stops"));
    }

    /**
     * Test that a favorite is shown in list
     */
    public void testFavoriteLoadStopWithRoute() {
        solo.clickOnMenuItem("Favorite Stops");
        assertTrue("Stop 10050 should be in favorites", solo.searchText("10050"));
    }

    /**
     * Test that favorites are added into list.
     */
    public void testAddFavorites() {
        View image1 = (View) solo.getView(R.id.mfp_context_menu);
        solo.clickOnView(image1);
        solo.waitForText("Add to favorites");
        solo.clickOnText("Add to favorites");
        boolean toast = solo.searchText("Stop already exists") || solo.searchText("added to favorite");
        assertTrue("Toast should pop up notifying favorite was added", toast);
    }

    /**
     * Test that favorites are removed.
     */
    public void testFavoriteRemove() {
        solo.clickOnMenuItem("Favorite Stops");
        View image1 = (View) solo.getView(R.id.mfp_context_menu2);
        solo.clickOnView(image1);
        solo.waitForText("Remove from favorites");
        solo.clickOnText("Remove from favorites");
        assertTrue(solo.waitForText("removed"));
    }

}