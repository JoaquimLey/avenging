/*
 * Copyright (c) Joaquim Ley 2016. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.joaquimley.mobile;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.list.ListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ListActivityTest {

    @Rule
    public ActivityTestRule<ListActivity> mActivityRule = new ActivityTestRule<>(ListActivity.class);

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void isCorrectTitleDisplayed() {
        onView(withText(R.string.app_name))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void isSearchIconClickable() {
        onView(withId(R.id.action_search))
                .check(ViewAssertions.matches(isClickable()));
    }

    @Test
    public void onSearchIconClicked_SearchBoxIsDisplayed() {
        onView(withId(R.id.action_search))
                .perform(click());

        onView(withId(R.id.search_src_text))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void onItemClicked_CorrectItemDisplayed() {

        mActivityRule.getActivity().findViewById(R.id.recycler_characters);
        // Click item at position 3
//        onView(withId(R.id.recycler_characters))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
//                .check()
    }
}