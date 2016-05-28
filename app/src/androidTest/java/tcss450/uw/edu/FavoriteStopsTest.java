package tcss450.uw.edu;


import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.robotium.solo.Solo;

import tcss450.uw.edu.team12.MainActivity;
import tcss450.uw.edu.team12.R;


/**
 * Test that favorites are being displayed properly.
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

//    /**
//     * Test that load fragment is loaded when clicked.
//     */
//    public void testFavoritesLoads() {
//        solo.clickOnMenuItem("Favorite Stops");
//        solo.waitForFragmentById(R.id.list_fav);
//        assertTrue("Favorites List fragment loaded",
//                getActivity().getTitle().equals("Favorite Stops"));
//    }
//
//    /**
//     * Test favorite stop loads when there are routes.
//     */
//    public void testFavoriteLoadStopWithRoute() {
//        solo.clickOnMenuItem("Favorite Stops");
//        solo.clickInRecyclerView(0);
//        solo.waitForFragmentById(R.id.list_route);
//        assertTrue(getActivity().getTitle().equals("View Bus Arrivals"));
//    }

    /**
     * Test that favorites are added into list.
     */
    public void testAddFavorites() {
        solo.clickOnMenuItem("View All Stops");
        View image1 = (View) solo.getView(R.id.mfp_context_menu);
        solo.clickOnView(image1);
        solo.waitForText("Add to favorites");
        solo.clickOnText("Add to favorites");
        assertTrue(solo.searchText("Stop already exists") || solo.searchText("added to favorite"));
    }

    /**
     * Test that favorites are remove.
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