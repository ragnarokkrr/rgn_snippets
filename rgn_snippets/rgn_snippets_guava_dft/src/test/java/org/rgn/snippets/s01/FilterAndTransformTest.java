package org.rgn.snippets.s01;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.contains;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
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

    @Test
    public void whenCheckingIfAllElementsMatchACondition_thenCorrect() {
        //names
        boolean result = Iterables.all(names, Predicates.containsPattern("n|m"));

        assertTrue(result);

        result = Iterables.all(names, Predicates.containsPattern("a"));
        assertFalse(result);

    }


    @Test
    public void whenTransformWithIterables_thenTransformed() {
        Function<String, Integer> function = new Function<String, Integer>() {
            public Integer apply(String input) {
                return input.length();
            }
        };

        Iterable<Integer> result = Iterables.transform(names, function);

        assertThat(result, contains(4, 4, 4, 3));
    }

    @Test
    public void whenTransformingUsingComposedFunction_thenTransformed() {
			Function<String, Integer> f1 = new Function<String, Integer>(){
           	public Integer apply (String input){
              return input.length();
            }
         };
 
      	Function<Integer, Boolean> f2 = new Function<Integer, Boolean>(){
           	public Boolean apply(Integer input){
              return input %2 ==0;
            }
         };
    
    		Collection<Boolean> result = Collections2.transform(names,Functions.compose(f2, f1));
      
      assertEquals(4, result.size());
      
      assertThat(result, contains(true, true, true,false));
    }
  
  	@Test
  public void whenFilteringAndTransformingCollection_thenCorrect(){
   	Predicate<String> predicate = new Predicate<String>(){
        	public boolean apply(String input){
           return input.startsWith("A") |  input.startsWith("T");
         }
      };
    
    
    Function<String, Integer> func = new Function<String,Integer>(){
      public Integer apply(String input){
        return input.length();
      }
    };
  
    Collection<Integer> result = FluentIterable.from(names)
      	.filter(predicate)
      	.transform(func)
      	.toList();
  
  	assertEquals(2, result.size());
  assertThat (result, containsInAnyOrder(4,3));
  }

}