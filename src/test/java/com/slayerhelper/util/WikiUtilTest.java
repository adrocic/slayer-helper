package com.slayerhelper.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class WikiUtilTest {
    @Test
    public void testSpaceFormatting() {
        String formattableString = "Test's Place";

        String wikiUrl = WikiUtil.getWikiUrl(WikiUtil.TaskDetail.MAP_LOCATION, formattableString);

        assertEquals("https://oldschool.runescape.wiki/w/Test's_Place", wikiUrl);
    }
}
