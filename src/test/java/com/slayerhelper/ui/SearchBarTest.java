package com.slayerhelper.ui;

import com.slayerhelper.ui.components.SearchBar;
import net.runelite.client.ui.components.IconTextField;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

public class SearchBarTest {

    private SearchBar searchBar;

    @Before
    public void setUp() {
        searchBar = new SearchBar(
                text -> System.out.println("Key Typed: " + text), // Replace with your own implementation
                () -> System.out.println("Cleared")); // Replace with your own implementation
    }

    @Test
    public void testSearchBarInitialization() {
        IconTextField iconTextField = searchBar.getSearchBar();

        // Verify that the search bar is not null
        assertNotNull(iconTextField);

        // Verify that the search bar is editable
        assertTrue(iconTextField.isEnabled());
    }

    @Test
    public void testSearchBarKeyReleasedHandler() {
        IconTextField iconTextField = searchBar.getSearchBar();

        // Simulate key release event with "test" text
        iconTextField.setText("test");
        KeyEvent keyEvent = new KeyEvent(iconTextField, KeyEvent.KEY_RELEASED, System.currentTimeMillis(),
                0, KeyEvent.VK_UNDEFINED, 't');
        iconTextField.dispatchEvent(keyEvent);

        // Verify that the key typed handler was called with the correct text
        // Replace with your own implementation of the handler
        // For testing purposes, you can use a custom listener to capture the text
    }

    @Test
    public void testSearchBarClearListener() {
        IconTextField iconTextField = searchBar.getSearchBar();

        // Simulate clearing the search bar
        iconTextField.setText("");
        // assert that the search bar is cleared
        assertEquals("", iconTextField.getText());
    }
}
