package org.rgn.snippets.s01;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author ragnarokkrr@gmail.com 
 */
public class FilterAndTransformTest {

    private List<String> names;

    @Before
    public void setUp() {
        names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
    }

    @Test
    public void whenFilterWithIterables_thenFiltered() {
        //names
        Iterable<String> result = Iterables.filter(names, Predicates.containsPattern("a"));
        assertThat(result, containsInAnyOrder("Jane", "Adam"));
    }


    @Test
    public void whenFilterWithCollections2_thenFiltered() {
        //names  
        Collection<String> result = Collections2.filter(names, Predicates.containsPattern("a"));

        assertEquals(2, result.size());
        assertThat(result, containsInAnyOrder("Jane", "Adam"));

        result.add("anna");
        assertEquals(5, names.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenFilteredCollection_whenAddingInvalidElement_thenException() {
        //names
        Collection<String> result = Collections2.filter(names, Predicates.containsPattern("a"));

        result.add("elvis");
    }


    @Test
    public void whenFilterCollectionWithCustomPredicate_thenFiltered() {
        Predicate<String> predicate = new Predicate<String>() {
            public boolean apply(String input) {
                return input.startsWith("A") || input.startsWith("J");
            }
        };

        //names
        Collection<String> result = Collections2.filter(names, predicate);

        assertEquals(3, result.size());
        assertThat(result, containsInAnyOrder("John", "Jane", "Adam"));
    }


    @Test
    public void whenRemoveNullFromCollection_thenRemoved() {

        List<String> names = Lists.newArrayList("John", null, "Jane", null, "Adam", "Tom");

        Collection<String> result = Collections2.filter(names, Predicates.notNull());

        assertEquals(4, result.size());
    }

}
