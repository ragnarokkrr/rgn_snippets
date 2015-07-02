package org.rgn.snippets.s01;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import org.junit.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

/**
 *
 * @author ragnarokkrr@gmail.com 
 */
public class FilterAndTransformTest {


    @Test
    public void whenFilterWithIterables_thenFiltered() {
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Iterable<String> result = Iterables.filter(names, Predicates.containsPattern("a"));
        assertThat(result, containsInAnyOrder("Jane", "Adam"));

    }
}
