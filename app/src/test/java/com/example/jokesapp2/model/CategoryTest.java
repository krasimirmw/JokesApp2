package com.example.jokesapp2.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryTest {

    private static final String FAKE_CATEGORY = "FAKE CATEGORY";

    private static final String EMPTY_CATEGORY = "";

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    private Category category;

    @Test
    public void constructor_WithNullCategory() {
        thrownException.expect(NullPointerException.class);

        category = new Category(null);
    }
}
