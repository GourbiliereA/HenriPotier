package com.gourbiliere.henripotier;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.gourbiliere.henripotier.OrientationChangeAction.orientationLandscape;

/**
 * Created by Alex GOURBILIERE on 23/02/2017.
 */

public class HenriPotierActivityTest extends ActivityInstrumentationTestCase2<HenriPotierActivity> {

    private String BOOK_TO_TEST_TITLE = "Henri Potier et les Reliques de la Mort";

    public HenriPotierActivityTest() {
        super(HenriPotierActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        getActivity();
    }

    // Testing the display of the list of books
    public void testBookList() throws Exception {

        Espresso.onView(withId(R.id.containerFrameLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(withId(R.id.bookRecyclerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        waitToBeLoaded(R.id.coverImageView);

        Espresso.onView(ViewMatchers.withText(BOOK_TO_TEST_TITLE))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    // Testing the display of the details of a book
    public void testBookDetails() throws Exception {

        Espresso.onView(withId(R.id.containerFrameLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        waitToBeLoaded(R.id.coverImageView);

        Espresso.onView(ViewMatchers.withText(BOOK_TO_TEST_TITLE))
                .perform(ViewActions.click());

        waitToBeLoaded(R.id.textView_titleDetails);

        Espresso.onView(withId(R.id.containerLinearLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.textView_titleDetails))
                .check(ViewAssertions.matches(withText(R.string.title_details)));

        Espresso.onView(ViewMatchers.withId(R.id.textView_bookTitle))
                .check(ViewAssertions.matches(withText(BOOK_TO_TEST_TITLE)));

        waitToBeLoaded(R.id.imageView_cover);
    }

    // Testing to pur the app in landscape orientation after have going to the details of a book
    public void testLandscapeMode() throws Exception {

        waitToBeLoaded(R.id.coverImageView);
        Espresso.onView(ViewMatchers.withText(BOOK_TO_TEST_TITLE))
                .perform(ViewActions.click());
        waitToBeLoaded(R.id.imageView_cover);

        // Turn the screen into landscape
        Espresso.onView(isRoot()).perform(orientationLandscape());

        // Both fragments displayed ?
        waitToBeLoaded(R.id.coverImageView);
        Espresso.onView(withId(R.id.bookRecyclerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.textView_titleDetails))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }


    private void waitToBeLoaded(int ref) {
        Espresso.onView(isRoot()).perform(waitId(ref, TimeUnit.SECONDS.toMillis(15)));
    }

    // Used to wait asynchronous action to be performed
    private static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }
}