package com.udacity.asteroidradar.adapter

import com.udacity.asteroidradar.R

class DataSourceTest {

    fun loadAffirmations(): List<AffirmationTest> {
        return listOf<AffirmationTest>(
                AffirmationTest(R.string.affirmation1),
                AffirmationTest(R.string.affirmation2),
                AffirmationTest(R.string.affirmation3),
                AffirmationTest(R.string.affirmation4)
        )
    }
}

