*java.util.AbstractMap
    This is groovy Map [:]
    Note java.util.Map is not java.util.Collection 
    String toString()
        Returns the string representation of the given map. 
    //from interface Map methods 
    //from Object methods 
    
*java.lang.Object 
    void addShutdownHook(Closure closure)
        Allows the usage of addShutdownHook without getting the runtime first.
    boolean any()
        Iterates over the elements of a collection, and checks whether at least one element is true according to the Groovy Truth.
    boolean any(Closure predicate)
        Iterates over the contents of an object or collection, and checks whether a predicate is valid for at least one element.
    boolean asBoolean()
        Coerce an object instance to a boolean value.
    Object asType(Class clazz)
        Converts the given array to either a List, Set, or SortedSet.
    Collection collect()
        Iterates through this aggregate Object transforming each item into a new value using Closure.IDENTITY as a transformer, basically returning a list of items copied from the original object.
    List collect(Closure transform)
        Iterates through this aggregate Object transforming each item into a new value using the transform closure, returning a list of transformed values.
    Collection collect(Collection collector, Closure transform)
        Iterates through this aggregate Object transforming each item into a new value using the transform closure and adding it to the supplied collector.
    boolean contains(Object value)
        Checks whether the array contains the given value.
    Number count(Object value)
        Counts the number of occurrences of the given value inside this array.
    String dump()
        Generates a detailed dump string of an object showing its class, hashCode and fields.
    Object each(Closure closure)
        Iterates through an aggregate type or data structure, passing each item to the given closure.
    Object eachWithIndex(Closure closure)
        Iterates through an aggregate type or data structure, passing each item and the items index (a counter starting at zero) to the given closure.
    boolean equals(List right)
        Determines if the contents of this array are equal to the contents of the given list, in the same order.
    boolean every()
        Iterates over every element of a collection, and checks whether all elements are true according to the Groovy Truth.
    boolean every(Closure predicate)
        Used to determine if the given predicate closure is valid (i.e. returns true for all items in this data structure).
    Object find()
        Finds the first item matching the IDENTITY Closure (i.e. matching Groovy truth).
    Object find(Closure closure)
        Finds the first value matching the closure condition.
    Collection findAll()
        Finds all items matching the IDENTITY Closure (i.e. matching Groovy truth).
    Collection findAll(Closure closure)
        Finds all items matching the closure condition.
    int findIndexOf(Closure condition)
        Iterates over the elements of an aggregate of items and returns the index of the first item that matches the condition specified in the closure.
    int findIndexOf(int startIndex, Closure condition)
        Iterates over the elements of an aggregate of items, starting from a specified startIndex, and returns the index of the first item that matches the condition specified in the closure.
    List findIndexValues(Closure condition)
        Iterates over the elements of an aggregate of items and returns the index values of the items that match the condition specified in the closure.
    List findIndexValues(Number startIndex, Closure condition)
        Iterates over the elements of an aggregate of items, starting from a specified startIndex, and returns the index values of the items that match the condition specified in the closure.
    int findLastIndexOf(Closure condition)
        Iterates over the elements of an aggregate of items and returns the index of the last item that matches the condition specified in the closure.
    int findLastIndexOf(int startIndex, Closure condition)
        Iterates over the elements of an aggregate of items, starting from a specified startIndex, and returns the index of the last item that matches the condition specified in the closure.
    Object findResult(Closure condition)
        Treats the object as iterable, iterating through the values it represents and returns the first non-null result obtained from calling the closure, otherwise returns null.
    Object findResult(Object defaultResult, Closure condition)
        Treats the object as iterable, iterating through the values it represents and returns the first non-null result obtained from calling the closure, otherwise returns the defaultResult.
    Collection flatten()
        Flatten an array.
    Object getAt(String property)
        Allows the subscript operator to be used to lookup dynamic property values.
    MetaClass getMetaClass()
        Obtains a MetaClass for an object either from the registry or in the case of a GroovyObject from the object itself.
    List getMetaPropertyValues()
        Retrieves the list of MetaProperty objects for self and wraps it in a list of PropertyValue objects that additionally provide the value for each property of self.
    Map getProperties()
        Convenience method that calls Object#getMetaPropertyValues()(self) and provides the data in form of simple key/value pairs, i.e. without type() information.
    Collection grep()
        Iterates over the collection of items which this Object represents and returns each item that matches using the IDENTITY Closure as a filter - effectively returning all elements which satisfy Groovy truth.
    Collection grep(Object filter)
        Iterates over the collection of items which this Object represents and returns each item that matches the given filter - calling the Object#isCase(java.lang.Object) method used by switch statements.
    Map groupBy(Object closures)
        Sorts all array members into (sub)groups determined by the supplied mapping closures as per the Iterable variant of this method.
    Map groupBy(List closures)
        Sorts all array members into (sub)groups determined by the supplied mapping closures as per the list variant of this method.
    MetaProperty hasProperty(String name)
        Returns true of the implementing MetaClass has a property of the given nameNote that this method will only return true for realised properties and does not take into account implementation of getProperty or propertyMissing
    Object identity(Closure closure)
        Allows the closure to be called for the object reference self.
    Object inject(Closure closure)
        Iterates through the given Object, passing in the first value to the closure along with the first item.
    Object inject(Object initialValue, Closure closure)
        Iterates through the given Object, passing in the initial value to the closure along with the first item.
    String inspect()
        Inspects returns the String that matches what would be typed into a terminal to create this object.
    Object invokeMethod(String method, Object arguments)
        Provide a dynamic method invocation method which can be overloaded in classes to implement dynamic proxies easily.
    boolean is(Object other)
        Identity check.
    boolean isCase(Object switchValue)
        Method for overloading the behavior of the case method in switch statements.
    Iterator iterator()
        Attempts to create an Iterator for the given object by first converting it to a Collection.
    String join(String separator)
        Concatenates the toString() representation of each items in this array, with the given String as a separator between each item.
    MetaClass metaClass(Closure closure)
        Sets/updates the metaclass for a given object to a closure.
    void print(PrintWriter out)
        Print to a console in interactive format.
    void print(Object value)
        Print a value formatted Groovy style to self if it is a Writer, otherwise to the standard output stream.
    void printf(String format, Object arg)
        Prints a formatted string using the specified format string and arguments.
    void printf(String format, Object[] values)
        Printf to a console.
    void println()
        Print a linebreak to the standard output stream.
    void println(PrintWriter out)
        Print to a console in interactive format.
    void println(Object value)
        Print a value formatted Groovy style (followed by a newline) to self if it is a Writer, otherwise to the standard output stream.
    void putAt(String property, Object newValue)
        Allows the subscript operator to be used to set dynamically named property values.
    List respondsTo(String name)
        Returns an object satisfying Groovy truth if the implementing MetaClass responds to a method with the given name regardless of the arguments.
    List respondsTo(String name, Object[] argTypes)
        Returns an object satisfying Groovy truth if the implementing MetaClass responds to a method with the given name and arguments types.
    void setMetaClass(MetaClass metaClass)
        Set the metaclass for an object.
    int size()
        Provide the standard Groovy size() method for an array.
    static void sleep(long milliseconds)
        Sleep for so many milliseconds, even if interrupted.
    static void sleep(long milliseconds, Closure onInterrupt)
        Sleep for so many milliseconds, using a given closure for interrupt processing.
    Collection split(Closure closure)
        Splits all items into two lists based on the closure condition.
    String sprintf(String format, Object arg)
        Returns a formatted string using the specified format string and arguments.
    String sprintf(String format, Object[] values)
        Sprintf to a string.
    Object sum()
        Sums the items in an array.
    Object sum(Object initialValue)
        Sums the items in an array, adding the result to some initial value.
    Object tap(Closure closure)
        Allows the closure to be called for the object reference self (similar to with and always returns self.
    String toArrayString()
        Returns the string representation of the given array.
    SpreadMap toSpreadMap()
        Creates a spreadable map from this array.
    String toString()
        Returns the string representation of this arrays contents.
    Object use(Class categoryClass, Closure closure)
        Scoped use method
    Object use(Object[] array)
        Allows you to use a list of categories, specifying the list as varargs.
    Object use(List categoryClassList, Closure closure)
        Scoped use method with list of categories.
    Object with(boolean returning, Closure closure)
        Allows the closure to be called for the object reference self.
    Object with(Closure closure)
        Allows the closure to be called for the object reference self.
    Object withTraits(Class traits)
        Dynamically wraps an instance into something which implements the supplied trait classes. 




*java.util.Map
    Closures in Map take either one (Entry ie entry.key and entry.value) or two (key, value) args 
    boolean any(Closure predicate)
        Iterates over the entries of a map, and checks whether a predicate is valid for at least one entry.
    boolean asBoolean()
        Coerce a map instance to a boolean value.
    Map asImmutable()
        A convenience method for creating an immutable Map.
    Map asSynchronized()
        A convenience method for creating a synchronized Map.
    Object asType(Class clazz)
        Coerces this map to the given type, using the maps keys as the public method names, and values as the implementation.
    Map asUnmodifiable()
        Creates an unmodifiable view of a Map.
    List collect(Closure transform)
        Iterates through this Map transforming each map entry into a new value using the transform closure returning a list of transformed values.
    Collection collect(Collection collector, Closure transform)
        Iterates through this Map transforming each map entry into a new value using the transform closure returning the collector with all transformed values added to it.
    Map collectEntries(Closure transform)
        Iterates through this Map transforming each entry using the transform closure and returning a map of the transformed entries.
    Map collectEntries(Map collector, Closure transform)
        Iterates through this Map transforming each map entry using the transform closure returning a map of the transformed entries.
    Collection collectMany(Closure projection)
        Projects each item from a source map to a result collection and concatenates (flattens) the resulting collections adding them into a collection.
    Collection collectMany(Collection collector, Closure projection)
        Projects each item from a source map to a result collection and concatenates (flattens) the resulting collections adding them into the collector.
    Number count(Closure closure)
        Counts the number of occurrences which satisfy the given closure from inside this map.
    Map countBy(Closure closure)
        Groups the members of a map into groups determined by the supplied mapping closure and counts the frequency of the created groups.
    Map drop(int num)
        Drops the given number of key/value pairs from the head of this map if they are available.
    Map dropWhile(Closure condition)
        Create a suffix of the given Map by dropping as many entries as possible from the front of the original Map such that calling the given closure condition evaluates to true when passed each of the dropped entries (or key/value pairs).
    Map each(Closure closure)
        Allows a Map to be iterated through using a closure.
    Map eachWithIndex(Closure closure)
        Allows a Map to be iterated through using a closure.
    boolean equals(Map other)
        Compares two Maps treating coerced numerical values as identical.
    boolean every(Closure predicate)
        Iterates over the entries of a map, and checks whether a predicate is valid for all entries.
    Entry find(Closure closure)
        Finds the first entry matching the closure condition.
    Map findAll(Closure closure)
        Finds all entries matching the closure condition.
    Object findResult(Closure condition)
        Returns the first non-null closure result found by passing each map entry to the closure, otherwise null is returned.
    Object findResult(Object defaultResult, Closure condition)
        Returns the first non-null closure result found by passing each map entry to the closure, otherwise the defaultResult is returned.
    Collection findResults(Closure filteringTransform)
        Iterates through the map transforming items using the supplied closure and collecting any non-null results.
    Object get(Object key, Object defaultValue)
        Looks up an item in a Map for the given key and returns the value - unless there is no entry for the given key in which case add the default value to the map and return that.
    Object getAt(Object key)
        Support the subscript operator for a Map.
    Map groupBy(Closure closure)
        Groups the members of a map into sub maps determined by the supplied mapping closure.
    Map groupBy(Object closures)
        Groups the members of a map into sub maps determined by the supplied mapping closures.
    Map groupBy(List closures)
        Groups the members of a map into sub maps determined by the supplied mapping closures.
    Map groupEntriesBy(Closure closure)
        Groups all map entries into groups determined by the supplied mapping closure.
    Object inject(Object initialValue, Closure closure)
        Iterates through the given Map, passing in the initial value to the 2-arg Closure along with the first item (or 3-arg Closure along with the first key and value).
    Map intersect(Map right)
        Create a Map composed of the intersection of both maps.
    boolean isCase(Object switchValue)
        Case implementation for maps which tests the groovy truth value obtained using the switch operand as key.
    Map leftShift(Map other)
        Overloads the left shift operator to provide an easy way to put one maps entries into another map.
    Map leftShift(Entry entry)
        Overloads the left shift operator to provide an easy way to append Map.Entry values to a Map.
    Entry max(Closure closure)
        Selects an entry in the map having the maximum calculated value as determined by the supplied closure.
    Entry min(Closure closure)
        Selects an entry in the map having the minimum calculated value as determined by the supplied closure.
    Map minus(Map removeMe)
        Create a Map composed of the entries of the first map minus the entries of the given map.
    Map plus(Collection entries)
        Returns a new Map containing all entries from self and entries, giving precedence to entries.
    Map plus(Map right)
        Returns a new Map containing all entries from left and right, giving precedence to right.
    Map putAll(Collection entries)
        Provides an easy way to append multiple Map.Entry values to a Map.
    Object putAt(Object key, Object value)
        A helper method to allow maps to work with subscript operators
    boolean removeAll(Closure condition)
        Modifies this map by removing the elements that are matched according to the specified closure condition.
    boolean retainAll(Closure condition)
        Modifies this map so that it retains only its elements that are matched according to the specified closure condition.
    Map reverseEach(Closure closure)
        Allows a Map to be iterated through in reverse order using a closure.
    Map sort()
        Sorts the elements from the given map into a new ordered Map using the natural ordering of the keys to determine the ordering.
    Map sort(Closure closure)
        Sorts the elements from the given map into a new ordered map using the closure as a comparator to determine the ordering.
    Map sort(Comparator comparator)
        Sorts the elements from the given map into a new ordered Map using the specified key comparator to determine the ordering.
    SpreadMap spread()
        Synonym for Map#toSpreadMap().
    Map subMap(Object[] keys)
        Creates a sub-Map containing the given keys.
    Map subMap(Collection keys)
        Creates a sub-Map containing the given keys.
    Map take(int num)
        Returns a new map containing the first num elements from the head of this map.
    Map takeWhile(Closure condition)
        Returns the longest prefix of this Map where each entry (or key/value pair) when passed to the given closure evaluates to true.
    String toMapString()
        Returns the string representation of this map.
    String toMapString(int maxSize)
        Returns the string representation of this map.
    Map toSorted()
        Sorts the elements from the given map into a new ordered map using a NumberAwareComparator on map entry values to determine the resulting order.
    Map toSorted(Closure condition)
        Sorts the elements from the given map into a new ordered map using the supplied Closure condition as a comparator to determine the ordering.
    Map toSorted(Comparator comparator)
        Sorts the elements from the given map into a new ordered map using the supplied comparator to determine the ordering.
    SpreadMap toSpreadMap()
        Returns a new SpreadMap from this map.
    Map withDefault(Closure init)
        Wraps a map using the decorator pattern with a wrapper that intercepts all calls to get(key)
    
*java.util.List 
    boolean addAll(int index, Object[] items)
        Modifies this list by inserting all of the elements in the specified array into the list at the specified position.
    List asImmutable()
        A convenience method for creating an immutable List.
    List asSynchronized()
        A convenience method for creating a synchronized List.
    List asUnmodifiable()
        Creates an unmodifiable view of a List.
    BufferedIterator bufferedIterator()
        Returns a BufferedIterator that allows examining the next element without consuming it.
    List drop(int num)
        Drops the given number of elements from the head of this List.
    List dropRight(int num)
        Drops the given number of elements from the tail of this List.
    List dropWhile(Closure condition)
        Returns a suffix of this List where elements are dropped from the front while the given Closure evaluates to true.
    List each(Closure closure)
        Iterates through a List, passing each item to the given closure.
    List eachWithIndex(Closure closure)
        Iterates through a List, passing each item and the items index (a counter starting at zero) to the given closure.
    boolean equals(Object[] right)
        Determines if the contents of this list are equal to the contents of the given array in the same order.
    boolean equals(List right)
        Compare the contents of two Lists.
    Process execute()
        Executes the command specified by the given list.
    Process execute(String[] envp, File dir)
        Executes the command specified by the given list, with the environment defined by envp and under the working directory dir.
    Process execute(List envp, File dir)
        Executes the command specified by the given list, with the environment defined by envp and under the working directory dir.
    List findAll()
        Finds the items matching the IDENTITY Closure (i.e. matching Groovy truth).
    List findAll(Closure closure)
        Finds all values matching the closure condition.
    Object first()
        Returns the first item from the List.
    List flatten()
        Flatten a List.
    List getAt(EmptyRange range)
        Support the range subscript operator for a List.
    List getAt(Range range)
        Support the range subscript operator for a List.
    Object getAt(int idx)
        Support the subscript operator for a List.
    List getAt(Collection indices)
        Select a List of items from a List using a Collection to identify the indices to be selected.
    List grep()
        Iterates over the collection returning each element that matches using the IDENTITY Closure as a filter - effectively returning all elements which satisfy Groovy truth.
    List grep(Object filter)
        Iterates over the collection of items and returns each item that matches the given filter - calling the Object#isCase(java.lang.Object) method used by switch statements.
    Object head()
        Returns the first item from the List.
    List init()
        Returns the items from the List excluding the last item.
    List intersect(Iterable right)
        Create a List composed of the intersection of a List and an Iterable.
    List intersect(Iterable right, Comparator comparator)
        Create a List composed of the intersection of a List and an Iterable.
    Object last()
        Returns the last item from the List.
    List leftShift(Object value)
        Overloads the left shift operator to provide an easy way to append objects to a List.
    List minus(Iterable removeMe)
        Create a new List composed of the elements of the first List minus every occurrence of elements of the given Iterable.
    List minus(Object removeMe)
        Create a new List composed of the elements of the first List minus every occurrence of the given element to remove.
    List minus(Collection removeMe)
        Create a List composed of the elements of the first list minus every occurrence of elements of the given Collection.
    List multiply(Number factor)
        Create a List composed of the elements of this Iterable, repeated a certain number of times.
    List plus(int index, Iterable additions)
        Creates a new List by inserting all of the elements in the given Iterable to the elements from this List at the specified index.
    List plus(int index, Object[] items)
        Creates a new List by inserting all of the elements in the specified array to the elements from the original List at the specified index.
    List plus(int index, List additions)
        Creates a new List by inserting all of the elements in the given additions List to the elements from the original List at the specified index.
    List plus(Iterable right)
        Create a List as a union of a List and an Iterable.
    List plus(Object right)
        Create a List as a union of a List and an Object.
    List plus(Collection right)
        Create a List as a union of a List and a Collection.
    Object pop()
        Removes the initial item from the List.
    boolean push(Object value)
        Prepends an item to the start of the List.
    void putAt(EmptyRange range, Object value)
        A helper method to allow lists to work with subscript operators.
    void putAt(EmptyRange range, Collection value)
        A helper method to allow lists to work with subscript operators.
    void putAt(IntRange range, Object value)
        List subscript assignment operator when given a range as the index.
    void putAt(IntRange range, Collection col)
        List subscript assignment operator when given a range as the index and the assignment operand is a collection.
    void putAt(int idx, Object value)
        A helper method to allow lists to work with subscript operators.
    void putAt(List splice, Object value)
        A helper method to allow lists to work with subscript operators.
    void putAt(List splice, List values)
        A helper method to allow lists to work with subscript operators.
    Object removeAt(int index)
        Modifies this list by removing the element at the specified position in this list.
    Object removeLast()
        Removes the last item from the List.
    List reverse()
        Creates a new List with the identical contents to this list but in reverse order.
    List reverse(boolean mutate)
        Reverses the elements in a list.
    List reverseEach(Closure closure)
        Iterate over each element of the list in the reverse order.
    List split(Closure closure)
        Splits all items into two collections based on the closure condition.
    Set subsequences()
        Finds all non-null subsequences of a list.
    List swap(int i, int j)
        Swaps two elements at the specified positions.
    List tail()
        Returns the items from the List excluding the first item.
    List take(int num)
        Returns the first num elements from the head of this List.
    List takeRight(int num)
        Returns the last num elements from the tail of this List.
    List takeWhile(Closure condition)
        Returns the longest prefix of this list where each element passed to the given closure condition evaluates to true.
    SpreadMap toSpreadMap()
        Creates a spreadable map from this list.
    List toUnique()
        Returns a List containing the items from the List but with duplicates removed using the natural ordering of the items to determine uniqueness.
    List toUnique(Closure condition)
        Returns a List containing the items from the List but with duplicates removed.
    List toUnique(Comparator comparator)
        Returns a List containing the items from the List but with duplicates removed.
    List transpose()
        Adds GroovyCollections#transpose(List) as a method on lists.
    List unique()
        Modifies this List to remove all duplicated items, using Groovys default number-aware comparator.
    List unique(boolean mutate)
        Remove all duplicates from a given List using Groovys default number-aware comparator.
    List unique(boolean mutate, Closure closure)
        A convenience method for making a List unique using a Closure to determine duplicate (equal) items.
    List unique(boolean mutate, Comparator comparator)
        Remove all duplicates from a given List.
    List unique(Closure closure)
        A convenience method for making a List unique using a Closure to determine duplicate (equal) items.
    List unique(Comparator comparator)
        Remove all duplicates from a given List.
    ListWithDefault withDefault(Closure init)
        An alias for withLazyDefault which decorates a list allowing it to grow when called with index values outside the normal list bounds.
    ListWithDefault withEagerDefault(Closure init)
        Decorates a list allowing it to grow when called with a non-existent index value.
    ListWithDefault withLazyDefault(Closure init)
        Decorates a list allowing it to grow when called with a non-existent index value. 
    //from Collections
    //from Iterable 

*java.util.Collection 
    boolean addAll(Iterable items)
        Adds all items from the iterable to the Collection.
    boolean addAll(Object[] items)
        Modifies the collection by adding all of the elements in the specified array to the collection.
    boolean addAll(Iterator items)
        Adds all items from the iterator to the Collection.
    boolean asBoolean()
        Coerce a collection instance to a boolean value.
    Collection asImmutable()
        A convenience method for creating an immutable Collection.
    Collection asSynchronized()
        A convenience method for creating a synchronized Collection.
    Object asType(Class clazz)
        Converts the given collection to another type.
    Collection asUnmodifiable()
        Creates an unmodifiable view of a Collection.
    List collectNested(Closure transform)
        Recursively iterates through this collection transforming each non-Collection value into a new value using the closure as a transformer.
    Collection each(Closure closure)
        Iterates through a Collection, passing each item to the given closure.
    Collection eachWithIndex(Closure closure)
        Iterates through a Collection, passing each item and the items index (a counter starting at zero) to the given closure.
    Object find()
        Finds the first item matching the IDENTITY Closure (i.e. matching Groovy truth).
    Object find(Closure closure)
        Finds the first value matching the closure condition.
    Collection findAll()
        Finds the items matching the IDENTITY Closure (i.e. matching Groovy truth).
    Collection findAll(Closure closure)
        Finds all values matching the closure condition.
    Collection flatten()
        Flatten a Collection.
    List getAt(String property)
        Support the subscript operator for Collection.
    IntRange getIndices()
        Returns indices of the collection.
    Collection grep()
        Iterates over the collection returning each element that matches using the IDENTITY Closure as a filter - effectively returning all elements which satisfy Groovy truth.
    Collection grep(Object filter)
        Iterates over the collection of items and returns each item that matches the given filter - calling the Object#isCase(java.lang.Object) method used by switch statements.
    Object inject(Closure closure)
        Performs the same function as the version of inject that takes an initial value, but uses the head of the Collection as the initial value, and iterates over the tail.
    Object inject(Object initialValue, Closure closure)
        Iterates through the given Collection, passing in the initial value to the 2-arg closure along with the first item.
    Collection intersect(Collection right)
        Create a Collection composed of the intersection of both collections.
    Collection intersect(Collection right, Comparator comparator)
        Create a Collection composed of the intersection of both collections.
    boolean isCase(Object switchValue)
        Case implementation for collections which tests if the switch operand is contained in any of the case values.
    Collection leftShift(Object value)
        Overloads the left shift operator to provide an easy way to append objects to a Collection.
    Collection minus(Collection removeMe)
        Create a new Collection composed of the elements of the first Collection minus every occurrence of elements of the given Collection.
    Collection plus(Iterable right)
        Create a Collection as a union of a Collection and an Iterable.
    Collection plus(Object right)
        Create a collection as a union of a Collection and an Object.
    Collection plus(Collection right)
        Create a Collection as a union of two collections.
    boolean removeAll(Closure condition)
        Modifies this collection by removing the elements that are matched according to the specified closure condition.
    boolean removeAll(Object[] items)
        Modifies this collection by removing its elements that are contained within the specified object array.
    boolean removeElement(Object o)
        Modifies this collection by removing a single instance of the specified element from this collection, if it is present.
    boolean retainAll(Closure condition)
        Modifies this collection so that it retains only its elements that are matched according to the specified closure condition.
    boolean retainAll(Object[] items)
        Modifies this collection so that it retains only its elements that are contained in the specified array.
    Collection split(Closure closure)
        Splits all items into two collections based on the closure condition.
    String toListString()
        Returns the string representation of the given list.
    String toListString(int maxSize)
        Returns the string representation of the given list.
    Set toSet()
        Convert a Collection to a Set.
    Collection unique()
        Modifies this collection to remove all duplicated items, using Groovys default number-aware comparator.
    Collection unique(boolean mutate)
        Remove all duplicates from a given Collection using Groovys default number-aware comparator.
    Collection unique(boolean mutate, Closure closure)
        A convenience method for making a collection unique using a Closure to determine duplicate (equal) items.
    Collection unique(boolean mutate, Comparator comparator)
        Remove all duplicates from a given Collection.
    Collection unique(Closure closure)
        A convenience method for making a collection unique using a Closure to determine duplicate (equal) items.
    Collection unique(Comparator comparator)
        Remove all duplicates from a given Collection. 

*java.lang.Iterable 
    Convert to iterator by .iterator 
    Note List,Set are Iterable , but not Map 
    boolean any(Closure predicate)
        Iterates over the contents of an iterable, and checks whether a predicate is valid for at least one element.
    Collection asCollection()
        Converts this Iterable to a Collection.
    List asList()
        Converts this Iterable to a List.
    Object asType(Class clazz)
        Converts the given iterable to another type.
    BufferedIterator bufferedIterator()
        Returns a BufferedIterator that allows examining the next element without consuming it.
    List collate(int size)
        Collates this iterable into sub-lists of length size.
    List collate(int size, boolean keepRemainder)
        Collates this iterable into sub-lists of length size.
    List collate(int size, int step)
        Collates this iterable into sub-lists of length size stepping through the code step elements for each subList.
    List collate(int size, int step, boolean keepRemainder)
        Collates this iterable into sub-lists of length size stepping through the code step elements for each sub-list.
    List collect()
        Iterates through this collection transforming each entry into a new value using Closure.IDENTITY as a transformer, basically returning a list of items copied from the original collection.
    List collect(Closure transform)
        Iterates through this Iterable transforming each entry into a new value using the transform closure returning a list of transformed values.
    Collection collect(Collection collector, Closure transform)
        Iterates through this collection transforming each value into a new value using the transform closure and adding it to the supplied collector.
    Map collectEntries()
        A variant of collectEntries for Iterable objects using the identity closure as the transform.
    Map collectEntries(Closure transform)
        Iterates through this Iterable transforming each item using the transform closure and returning a map of the resulting transformed entries.
    Map collectEntries(Map collector)
        A variant of collectEntries for Iterables using the identity closure as the transform and a supplied map as the destination of transformed entries.
    Map collectEntries(Map collector, Closure transform)
        Iterates through this Iterable transforming each item using the closure as a transformer into a map entry, returning the supplied map with all of the transformed entries added to it.
    List collectMany(Closure projection)
        Projects each item from a source Iterable to a collection and concatenates (flattens) the resulting collections into a single list.
    Collection collectMany(Collection collector, Closure projection)
        Projects each item from a source collection to a result collection and concatenates (flattens) the resulting collections adding them into the collector.
    List collectNested(Closure transform)
        Recursively iterates through this Iterable transforming each non-Collection value into a new value using the closure as a transformer.
    Collection collectNested(Collection collector, Closure transform)
        Recursively iterates through this Iterable transforming each non-Collection value into a new value using the transform closure.
    List combinations()
        Adds GroovyCollections#combinations(Iterable) as a method on Iterables.
    List combinations(Closure function)
        Adds GroovyCollections#combinations(Iterable, Closure) as a method on collections.
    boolean contains(Object item)
        Returns true if this iterable contains the item.
    boolean containsAll(Object[] items)
        Returns true if this iterable contains all of the elements in the specified array.
    Number count(Closure closure)
        Counts the number of occurrences which satisfy the given closure from inside this Iterable.
    Number count(Object value)
        Counts the number of occurrences of the given value inside this Iterable.
    Map countBy(Closure closure)
        Sorts all collection members into groups determined by the supplied mapping closure and counts the group size.
    boolean disjoint(Iterable right)
        Returns true if the intersection of two iterables is empty.
    Collection drop(int num)
        Drops the given number of elements from the head of this Iterable.
    Collection dropRight(int num)
        Drops the given number of elements from the tail of this Iterable.
    Collection dropWhile(Closure condition)
        Returns a suffix of this Iterable where elements are dropped from the front while the given closure evaluates to true.
    Iterable each(Closure closure)
        Iterates through an Iterable, passing each item to the given closure.
    void eachCombination(Closure function)
        Applies a function on each combination of the input lists.
    Iterator eachPermutation(Closure closure)
        Iterates over all permutations of a collection, running a closure for each iteration.
    Iterable eachWithIndex(Closure closure)
        Iterates through an iterable type, passing each item and the items index (a counter starting at zero) to the given closure.
    boolean every(Closure predicate)
        Used to determine if the given predicate closure is valid (i.e. returns true for all items in this iterable).
    int findIndexOf(Closure condition)
        Iterates over the elements of an Iterable and returns the index of the first item that satisfies the condition specified by the closure.
    int findIndexOf(int startIndex, Closure condition)
        Iterates over the elements of an Iterable, starting from a specified startIndex, and returns the index of the first item that satisfies the condition specified by the closure.
    List findIndexValues(Closure condition)
        Iterates over the elements of an Iterable and returns the index values of the items that match the condition specified in the closure.
    List findIndexValues(Number startIndex, Closure condition)
        Iterates over the elements of an Iterable, starting from a specified startIndex, and returns the index values of the items that match the condition specified in the closure.
    int findLastIndexOf(Closure condition)
        Iterates over the elements of an Iterable and returns the index of the last item that matches the condition specified in the closure.
    int findLastIndexOf(int startIndex, Closure condition)
        Iterates over the elements of an Iterable, starting from a specified startIndex, and returns the index of the last item that matches the condition specified in the closure.
    Object findResult(Closure condition)
        Iterates through the Iterable calling the given closure condition for each item but stopping once the first non-null result is found and returning that result.
    Object findResult(Object defaultResult, Closure condition)
        Iterates through the Iterable calling the given closure condition for each item but stopping once the first non-null result is found and returning that result.
    Collection findResults(Closure filteringTransform)
        Iterates through the Iterable transforming items using the supplied closure and collecting any non-null results.
    Object first()
        Returns the first item from the Iterable.
    Collection flatten()
        Flatten an Iterable.
    Collection flatten(Closure flattenUsing)
        Flatten an Iterable.
    Object getAt(int idx)
        Support the subscript operator for an Iterable.
    Map groupBy(Closure closure)
        Sorts all Iterable members into groups determined by the supplied mapping closure.
    Map groupBy(Object closures)
        Sorts all Iterable members into (sub)groups determined by the supplied mapping closures.
    Map groupBy(List closures)
        Sorts all Iterable members into (sub)groups determined by the supplied mapping closures.
    Object head()
        Returns the first item from the Iterable.
    Map indexed()
        Zips an Iterable with indices in (index, value) order.
    Map indexed(int offset)
        Zips an Iterable with indices in (index, value) order.
    Collection init()
        Returns the items from the Iterable excluding the last item.
    List inits()
        Calculates the init values of this Iterable: the first value will be this list of all items from the iterable and the final one will be an empty list, with the intervening values the results of successive applications of init on the items.
    Collection intersect(Iterable right)
        Create a Collection composed of the intersection of both iterables.
    Collection intersect(Iterable right, Comparator comparator)
        Create a Collection composed of the intersection of both iterables.
    boolean isEmpty()
        Check whether an Iterable has elements
    String join(String separator)
        Concatenates the toString() representation of each item in this Iterable, with the given String as a separator between each item.
    Object last()
        Returns the last item from the Iterable.
    Object max()
        Adds max() method to Iterable objects.
    Object max(Closure closure)
        Selects the item in the iterable which when passed as a parameter to the supplied closure returns the maximum value.
    Object max(Comparator comparator)
        Selects the maximum value found in the Iterable using the given comparator.
    Object min()
        Adds min() method to Collection objects.
    Object min(Closure closure)
        Selects the item in the iterable which when passed as a parameter to the supplied closure returns the minimum value.
    Object min(Comparator comparator)
        Selects the minimum value found in the Iterable using the given comparator.
    Collection minus(Iterable removeMe)
        Create a new Collection composed of the elements of the first Iterable minus every occurrence of elements of the given Iterable.
    Collection minus(Object removeMe)
        Create a new Collection composed of the elements of the first Iterable minus every occurrence of the given element to remove.
    Collection multiply(Number factor)
        Create a Collection composed of the elements of this Iterable, repeated a certain number of times.
    Set permutations()
        Finds all permutations of an iterable.
    List permutations(Closure function)
        Finds all permutations of an iterable, applies a function to each permutation and collects the result into a list.
    Collection plus(Iterable right)
        Create a Collection as a union of two iterables.
    Collection plus(Object right)
        Create a collection as a union of an Iterable and an Object.
    int size()
        Provide the standard Groovy size() method for Iterable.
    List sort()
        Sorts the Collection.
    List sort(boolean mutate)
        Sorts the Iterable.
    List sort(boolean mutate, Closure closure)
        Sorts this Iterable using the given Closure to determine the correct ordering.
    List sort(boolean mutate, Comparator comparator)
        Sorts the Iterable using the given Comparator.
    List sort(Closure closure)
        Sorts this Iterable using the given Closure to determine the correct ordering.
    Object sum()
        Sums the items in an Iterable.
    Object sum(Closure closure)
        Sums the result of apply a closure to each item of an Iterable.
    Object sum(Object initialValue)
        Sums the items in an Iterable, adding the result to some initial value.
    Object sum(Object initialValue, Closure closure)
        Sums the result of applying a closure to each item of an Iterable to some initial value.
    Collection tail()
        Returns the items from the Iterable excluding the first item.
    List tails()
        Calculates the tail values of this Iterable: the first value will be this list of all items from the iterable and the final one will be an empty list, with the intervening values the results of successive applications of tail on the items.
    Collection take(int num)
        Returns the first num elements from the head of this Iterable.
    Collection takeRight(int num)
        Returns the last num elements from the tail of this Iterable.
    Collection takeWhile(Closure condition)
        Returns a Collection containing the longest prefix of the elements from this Iterable where each element passed to the given closure evaluates to true.
    List toList()
        Convert an Iterable to a List.
    Set toSet()
        Convert an Iterable to a Set.
    List toSorted()
        Sorts the Iterable.
    List toSorted(Closure closure)
        Sorts this Iterable using the given Closure to determine the correct ordering.
    List toSorted(Comparator comparator)
        Sorts the Iterable using the given Comparator.
    SpreadMap toSpreadMap()
        Creates a spreadable map from this iterable.
    Collection toUnique()
        Returns a Collection containing the items from the Iterable but with duplicates removed using the natural ordering of the items to determine uniqueness.
    Collection toUnique(Closure condition)
        Returns a Collection containing the items from the Iterable but with duplicates removed.
    Collection toUnique(Comparator comparator)
        Returns a Collection containing the items from the Iterable but with duplicates removed.
    List withIndex()
        Zips an Iterable with indices in (value, index) order.
    List withIndex(int offset)
        Zips an Iterable with indices in (value, index) order. 



*java.util.Set 
    Set asImmutable()
        A convenience method for creating an immutable Set.
    Set asSynchronized()
        A convenience method for creating a synchronized Set.
    Set asUnmodifiable()
        Creates an unmodifiable view of a Set.
    Set each(Closure closure)
        Iterates through a Set, passing each item to the given closure.
    Set eachWithIndex(Closure closure)
        Iterates through a Set, passing each item and the items index (a counter starting at zero) to the given closure.
    boolean equals(Set other)
        Compare the contents of two Sets for equality using Groovys coercion rules.
    Set findAll()
        Finds the items matching the IDENTITY Closure (i.e. matching Groovy truth).
    Set findAll(Closure closure)
        Finds all values matching the closure condition.
    Set flatten()
        Flatten a Set.
    Set grep()
        Iterates over the collection returning each element that matches using the IDENTITY Closure as a filter - effectively returning all elements which satisfy Groovy truth.
    Set grep(Object filter)
        Iterates over the collection of items and returns each item that matches the given filter - calling the Object#isCase(java.lang.Object) method used by switch statements.
    Set intersect(Iterable right)
        Create a Set composed of the intersection of a Set and an Iterable.
    Set intersect(Iterable right, Comparator comparator)
        Create a Set composed of the intersection of a Set and an Iterable.
    Set leftShift(Object value)
        Overloads the left shift operator to provide an easy way to append objects to a Set.
    Set minus(Iterable removeMe)
        Create a Set composed of the elements of the first Set minus the elements from the given Iterable.
    Set minus(Object removeMe)
        Create a Set composed of the elements of the first Set minus the given element.
    Set minus(Collection removeMe)
        Create a Set composed of the elements of the first Set minus the elements of the given Collection.
    Set plus(Iterable right)
        Create a Set as a union of a Set and an Iterable.
    Set plus(Object right)
        Create a Set as a union of a Set and an Object.
    Set plus(Collection right)
        Create a Set as a union of a Set and a Collection.
    List split(Closure closure)
        Splits all items into two collections based on the closure condition. 
    //from Collection methods 
    //from Iterable methods


*java.util.Iterator 
    boolean any(Closure predicate)
        Iterates over the contents of an iterator, and checks whether a predicate is valid for at least one element.
    boolean asBoolean()
        Coerce an iterator instance to a boolean value.
    BufferedIterator buffered()
        Returns a BufferedIterator that allows examining the next element without consuming it.
    List collect(Closure transform)
        Iterates through this Iterator transforming each item into a new value using the transform closure, returning a list of transformed values.
    Collection collect(Collection collector, Closure transform)
        Iterates through this Iterator transforming each item into a new value using the transform closure and adding it to the supplied collector.
    Map collectEntries()
        A variant of collectEntries for Iterators using the identity closure as the transform.
    Map collectEntries(Closure transform)
        A variant of collectEntries for Iterators.
    Map collectEntries(Map collector)
        A variant of collectEntries for Iterators using the identity closure as the transform and a supplied map as the destination of transformed entries.
    Map collectEntries(Map collector, Closure transform)
        A variant of collectEntries for Iterators using a supplied map as the destination of transformed entries.
    List collectMany(Closure projection)
        Projects each item from a source iterator to a collection and concatenates (flattens) the resulting collections into a single list.
    Number count(Closure closure)
        Counts the number of occurrences which satisfy the given closure from the items within this Iterator.
    Number count(Object value)
        Counts the number of occurrences of the given value from the items within this Iterator.
    Map countBy(Closure closure)
        Sorts all iterator items into groups determined by the supplied mapping closure and counts the group size.
    Iterator drop(int num)
        Drops the given number of elements from the head of this iterator if they are available.
    Iterator dropRight(int num)
        Drops the given number of elements from the tail of this Iterator.
    Iterator dropWhile(Closure condition)
        Creates an Iterator that returns a suffix of the elements from an original Iterator.
    Iterator each(Closure closure)
        Iterates through an Iterator, passing each item to the given closure.
    Iterator eachWithIndex(Closure closure)
        Iterates through an iterator type, passing each item and the items index (a counter starting at zero) to the given closure.
    boolean every(Closure predicate)
        Used to determine if the given predicate closure is valid (i.e. returns true for all items in this iterator).
    int findIndexOf(Closure condition)
        Iterates over the elements of an Iterator and returns the index of the first item that satisfies the condition specified by the closure.
    int findIndexOf(int startIndex, Closure condition)
        Iterates over the elements of an Iterator, starting from a specified startIndex, and returns the index of the first item that satisfies the condition specified by the closure.
    List findIndexValues(Closure condition)
        Iterates over the elements of an Iterator and returns the index values of the items that match the condition specified in the closure.
    List findIndexValues(Number startIndex, Closure condition)
        Iterates over the elements of an Iterator, starting from a specified startIndex, and returns the index values of the items that match the condition specified in the closure.
    int findLastIndexOf(Closure condition)
        Iterates over the elements of an Iterator and returns the index of the last item that matches the condition specified in the closure.
    int findLastIndexOf(int startIndex, Closure condition)
        Iterates over the elements of an Iterator, starting from a specified startIndex, and returns the index of the last item that matches the condition specified in the closure.
    Object findResult(Closure condition)
        Iterates through the Iterator calling the given closure condition for each item but stopping once the first non-null result is found and returning that result.
    Object findResult(Object defaultResult, Closure condition)
        Iterates through the Iterator calling the given closure condition for each item but stopping once the first non-null result is found and returning that result.
    Collection findResults(Closure filteringTransform)
        Iterates through the Iterator transforming items using the supplied closure and collecting any non-null results.
    Object getAt(int idx)
        Support the subscript operator for an Iterator.
    Iterator indexed()
        Zips an iterator with indices in (index, value) order.
    Iterator indexed(int offset)
        Zips an iterator with indices in (index, value) order.
    Iterator init()
        Returns an Iterator containing all of the items from this iterator except the last one.
    Object inject(Object initialValue, Closure closure)
        Iterates through the given Iterator, passing in the initial value to the closure along with the first item.
    Iterator iterator()
        An identity function for iterators, supporting duck-typing when trying to get an iterator for each object within a collection, some of which may already be iterators.
    String join(String separator)
        Concatenates the toString() representation of each item from the iterator, with the given String as a separator between each item.
    Object max()
        Adds max() method to Iterator objects.
    Object max(Closure closure)
        Selects the maximum value found from the Iterator using the closure to determine the correct ordering.
    Object max(Comparator comparator)
        Selects the maximum value found from the Iterator using the given comparator.
    Object min()
        Adds min() method to Iterator objects.
    Object min(Closure closure)
        Selects the minimum value found from the Iterator using the closure to determine the correct ordering.
    Object min(Comparator comparator)
        Selects the minimum value found from the Iterator using the given comparator.
    Iterator reverse()
        Reverses the iterator.
    int size()
        Provide the standard Groovy size() method for Iterator.
    Iterator sort()
        Sorts the given iterator items into a sorted iterator.
    Iterator sort(Closure closure)
        Sorts the given iterator items into a sorted iterator using the Closure to determine the correct ordering.
    Iterator sort(Comparator comparator)
        Sorts the given iterator items into a sorted iterator using the comparator.
    Object sum()
        Sums the items from an Iterator.
    Object sum(Closure closure)
        Sums the result of apply a closure to each item returned from an iterator.
    Object sum(Object initialValue)
        Sums the items from an Iterator, adding the result to some initial value.
    Object sum(Object initialValue, Closure closure)
        Sums the result of applying a closure to each item of an Iterator to some initial value.
    Iterator tail()
        Returns the original iterator after throwing away the first element.
    Iterator take(int num)
        Returns an iterator of up to the first num elements from this iterator.
    Iterator takeWhile(Closure condition)
        Returns the longest prefix of elements in this iterator where each element passed to the given condition closure evaluates to true.
    List toList()
        Convert an iterator to a List.
    Set toSet()
        Convert an iterator to a Set.
    Iterator toSorted()
        Sorts the Iterator.
    Iterator toSorted(Closure closure)
        Sorts the given iterator items into a sorted iterator using the Closure to determine the correct ordering.
    Iterator toSorted(Comparator comparator)
        Sorts the given iterator items using the comparator.
    Iterator toUnique()
        Returns an iterator equivalent to this iterator with all duplicated items removed by using the natural ordering of the items.
    Iterator toUnique(Closure condition)
        Returns an iterator equivalent to this iterator but with all duplicated items removed where duplicate (equal) items are deduced by calling the supplied Closure condition.
    Iterator toUnique(Comparator comparator)
        Returns an iterator equivalent to this iterator with all duplicated items removed by using the supplied comparator.
    Iterator unique()
        Returns an iterator equivalent to this iterator with all duplicated items removed by using Groovys default number-aware comparator.
    Iterator unique(Closure condition)
        Returns an iterator equivalent to this iterator but with all duplicated items removed by using a Closure to determine duplicate (equal) items.
    Iterator unique(Comparator comparator)
        Returns an iterator equivalent to this iterator with all duplicated items removed by using the supplied comparator.
    Iterator withIndex()
        Zips an iterator with indices in (value, index) order.
    Iterator withIndex(int offset)
        Zips an iterator with indices in (value, index) order. 



*java.util.concurrent.BlockingQueue
    BlockingQueue 	leftShift(Object value)
        Overloads the left shift operator to provide an easy way to append objects to a BlockingQueue.
    //Methods inherited from interface java.util.Collection
    //Methods inherited from interface java.lang.Iterable


*java.util.stream.Stream
    List 	toList()
        Accumulates the elements of stream into a new List.
    Set 	toSet()
        Accumulates the elements of stream into a new Set. 
    
*java.lang.Object[] 
    Base class of all java array[] ie String[], Integer[] etc 
    boolean any(Closure predicate)
        Iterates over the contents of an Array, and checks whether a predicate is valid for at least one element.
    List collate(int size)
        Collates an array.
    List collate(int size, boolean keepRemainder)
        Collates this array into sub-lists.
    List collate(int size, int step)
        Collates an array into sub-lists.
    List collate(int size, int step, boolean keepRemainder)
        Collates this array into into sub-lists.
    List collect(Closure transform)
        Iterates through this Array transforming each item into a new value using the transform closure, returning a list of transformed values.
    Collection collect(Collection collector, Closure transform)
        Iterates through this Array transforming each item into a new value using the transform closure and adding it to the supplied collector.
    Map collectEntries()
        A variant of collectEntries using the identity closure as the transform.
    Map collectEntries(Closure transform)
        Iterates through this array transforming each item using the transform closure and returning a map of the resulting transformed entries.
    Map collectEntries(Map collector)
        A variant of collectEntries using the identity closure as the transform.
    Map collectEntries(Map collector, Closure transform)
        Iterates through this array transforming each item using the transform closure and returning a map of the resulting transformed entries.
    List collectMany(Closure projection)
        Projects each item from a source array to a collection and concatenates (flattens) the resulting collections into a single list.
    Number count(Closure closure)
        Counts the number of occurrences which satisfy the given closure from inside this array.
    Map countBy(Closure closure)
        Sorts all array members into groups determined by the supplied mapping closure and counts the group size.
    Object[] drop(int num)
        Drops the given number of elements from the head of this array if they are available.
    Object[] dropRight(int num)
        Drops the given number of elements from the tail of this array if they are available.
    Object[] dropWhile(Closure condition)
        Create a suffix of the given array by dropping as many elements as possible from the front of the original array such that calling the given closure condition evaluates to true when passed each of the dropped elements.
    Object[] each(Closure closure)
        Iterates through an array passing each array entry to the given closure.
    Object[] eachWithIndex(Closure closure)
        Iterates through an array, passing each array element and the elements index (a counter starting at zero) to the given closure.
    boolean every(Closure predicate)
        Used to determine if the given predicate closure is valid (i.e. returns true for all items in this Array).
    Object find(Closure condition)
        Finds the first element in the array that matches the given closure condition.
    Collection findAll()
        Finds the elements of the array matching the IDENTITY Closure (i.e. matching Groovy truth).
    Collection findAll(Closure condition)
        Finds all elements of the array matching the given Closure condition.
    int findIndexOf(Closure condition)
        Iterates over the elements of an Array and returns the index of the first item that satisfies the condition specified by the closure.
    int findIndexOf(int startIndex, Closure condition)
        Iterates over the elements of an Array, starting from a specified startIndex, and returns the index of the first item that satisfies the condition specified by the closure.
    List findIndexValues(Closure condition)
        Iterates over the elements of an Array and returns the index values of the items that match the condition specified in the closure.
    List findIndexValues(Number startIndex, Closure condition)
        Iterates over the elements of an Array, starting from a specified startIndex, and returns the index values of the items that match the condition specified in the closure.
    int findLastIndexOf(Closure condition)
        Iterates over the elements of an Array and returns the index of the last item that matches the condition specified in the closure.
    int findLastIndexOf(int startIndex, Closure condition)
        Iterates over the elements of an Array, starting from a specified startIndex, and returns the index of the last item that matches the condition specified in the closure.
    Object findResult(Closure condition)
        Iterates through the Array calling the given closure condition for each item but stopping once the first non-null result is found and returning that result.
    Object findResult(Object defaultResult, Closure condition)
        Iterates through the Array calling the given closure condition for each item but stopping once the first non-null result is found and returning that result.
    Collection findResults(Closure filteringTransform)
        Iterates through the Array transforming items using the supplied closure and collecting any non-null results.
    Object first()
        Returns the first item from the array.
    List getAt(EmptyRange range)
        List getAt(IntRange range)
    List getAt(ObjectRange range)
    List getAt(Range range)
        Support the range subscript operator for an Array
    List getAt(Collection indices)
        Select a List of items from an array using a Collection to identify the indices to be selected.
    IntRange getIndices()
        Returns indices of the array.
    Collection grep()
        Iterates over the array returning each element that matches using the IDENTITY Closure as a filter - effectively returning all elements which satisfy Groovy truth.
    Collection grep(Object filter)
        Iterates over the array of items and returns a collection of items that match the given filter - calling the Object#isCase(java.lang.Object) method used by switch statements.
    Map groupBy(Closure closure)
        Sorts all array members into groups determined by the supplied mapping closure.
    Object head()
        Returns the first item from the Object array.
    Object[] init()
        Returns the items from the Object array excluding the last item.
    Object inject(Closure closure)
        Iterates through the given array as with inject(Object[],initialValue,closure), but using the first element of the array as the initialValue, and then iterating the remaining elements of the array.
    Object inject(Object initialValue, Closure closure)
        Iterates through the given array, passing in the initial value to the closure along with the first item.
    Iterator iterator()
        Attempts to create an Iterator for the given object by first converting it to a Collection.
    Object last()
        Returns the last item from the array.
    Object max()
        Adds max() method to Object arrays.
    Object max(Closure closure)
        Selects the maximum value found from the Object array using the closure to determine the correct ordering.
    Object max(Comparator comparator)
        Selects the maximum value found from the Object array using the given comparator.
    Object min()
        Adds min() method to Object arrays.
    Object min(Closure closure)
        Selects the minimum value found from the Object array using the closure to determine the correct ordering.
    Object min(Comparator comparator)
        Selects the minimum value found from the Object array using the given comparator.
    Object[] minus(Iterable removeMe)
        Create an array composed of the elements of the first array minus the elements of the given Iterable.
    Object[] minus(Object removeMe)
        Create a new object array composed of the elements of the first array minus the element to remove.
    Object[] minus(Object[] removeMe)
        Create an array composed of the elements of the first array minus the elements of the given array.
    Object[] plus(Iterable right)
        Create an array containing elements from an original array plus those from an Iterable.
    Object[] plus(Object right)
        Create an array containing elements from an original array plus an additional appended element.
    Object[] plus(Object[] right)
        Create an array as a union of two arrays.
    Object[] plus(Collection right)
        Create an array containing elements from an original array plus those from a Collection.
    Object[] reverse()
        Creates a new array containing items which are the same as this array but in reverse order.
    Object[] reverse(boolean mutate)
        Reverse the items in an array.
    Object[] reverseEach(Closure closure)
        Iterate over each element of the array in the reverse order.
    Object[] sort()
        Modifies this array so that its elements are in sorted order.
    Object[] sort(boolean mutate)
        Sorts the given array into sorted order.
    Object[] sort(boolean mutate, Closure closure)
        Modifies this array so that its elements are in sorted order using the Closure to determine the correct ordering.
    Object[] sort(boolean mutate, Comparator comparator)
        Modifies this array so that its elements are in sorted order as determined by the given comparator.
    Object[] sort(Closure closure)
        Sorts the elements from this array into a newly created array using the Closure to determine the correct ordering.
    Object[] sort(Comparator comparator)
        Sorts the given array into sorted order using the given comparator.
    Collection split(Closure closure)
        Splits all items into two collections based on the closure condition.
    Stream stream()
        Returns a sequential Stream with the specified array as its source.
    Object sum(Closure closure)
        Sums the result of apply a closure to each item of an array.
    Object sum(Object initialValue, Closure closure)
        Sums the result of applying a closure to each item of an array to some initial value.
    Object[] swap(int i, int j)
        Swaps two elements at the specified positions.
    Object[] tail()
        Returns the items from the array excluding the first item.
    Object[] take(int num)
        Returns the first num elements from the head of this array.
    Object[] takeRight(int num)
        Returns the last num elements from the tail of this array.
    Object[] takeWhile(Closure condition)
        Returns the longest prefix of this array where each element passed to the given closure evaluates to true.
    List toList()
        Allows conversion of arrays into a mutable List.
    Object[] toSorted()
        Returns a sorted version of the given array using the supplied comparator.
    Object[] toSorted(Closure condition)
        Sorts the elements from this array into a newly created array using the Closure to determine the correct ordering.
    Object[] toSorted(Comparator comparator)
        Returns a sorted version of the given array using the supplied comparator to determine the resulting order.
    Object[] toUnique()
        Returns a new Array containing the items from the original Array but with duplicates removed using the natural ordering of the items in the array.
    Object[] toUnique(Closure condition)
        Returns a new Array containing the items from the original Array but with duplicates removed with the supplied comparator determining which items are unique.
    Object[] toUnique(Comparator comparator)
        Returns a new Array containing the items from the original Array but with duplicates removed with the supplied comparator determining which items are unique. 

        


*java.nio.file.Path  
    void append(byte[] bytes)
        Append bytes to the end of a Path.
    void append(InputStream stream)
        Append binary data to the file.
    void append(Reader reader)
        Append the text supplied by the Writer at the end of the File without writing a BOM.
    void append(Reader reader, boolean writeBom)
        Append the text supplied by the Reader at the end of the File, using a specified encoding.
    void append(Reader reader, String charset)
        Append the text supplied by the Reader at the end of the File without writing a BOM, using a specified encoding.
    void append(Reader reader, String charset, boolean writeBom)
        Append the text supplied by the Reader at the end of the File, using a specified encoding.
    void append(Writer writer)
        Append the text supplied by the Writer at the end of the File without writing a BOM.
    void append(Writer writer, boolean writeBom)
        Append the text supplied by the Writer at the end of the File, using a specified encoding.
    void append(Writer writer, String charset)
        Append the text supplied by the Writer at the end of the File without writing a BOM, using a specified encoding.
    void append(Writer writer, String charset, boolean writeBom)
        Append the text supplied by the Writer at the end of the File, using a specified encoding.
    void append(Object text)
        Append the text at the end of the Path without writing a BOM.
    void append(Object text, boolean writeBom)
        Append the text at the end of the Path.
    void append(Object text, String charset)
        Append the text at the end of the Path without writing a BOM, using a specified encoding.
    void append(Object text, String charset, boolean writeBom)
        Append the text at the end of the Path, using a specified encoding.
    Object asType(Class c)
        Converts this Path to a Writable or delegates to default DefaultGroovyMethods#asType(Object, Class).
    Path asWritable()
        Converts this Path to a Writable.
    Path asWritable(String encoding)
        Allows a file to return a Writable implementation that can output itself to a Writer stream.
    boolean deleteDir()
        Deletes a directory with all contained files and subdirectories.
    void eachByte(Closure closure)
        Traverse through each byte of this Path
    void eachByte(int bufferLen, Closure closure)
        Traverse through the bytes of this Path, bufferLen bytes at a time.
    void eachDir(Closure closure)
        Invokes the closure for each subdirectory in this directory, ignoring regular files.
    void eachDirMatch(Object nameFilter, Closure closure)
        Invokes the closure for each subdirectory whose name (dir.name) matches the given nameFilter in the given directory - calling the DefaultGroovyMethods#isCase(java.lang.Object, java.lang.Object) method to determine if a match occurs.
    void eachDirRecurse(Closure closure)
        Recursively processes each descendant subdirectory in this directory.
    void eachFile(FileType fileType, Closure closure)
        Invokes the closure for each child file in this parent folder/directory.
    void eachFile(Closure closure)
        Invokes the closure for each child file in this parent folder/directory.
    void eachFileMatch(FileType fileType, Object nameFilter, Closure closure)
        Invokes the closure for each file whose name (file.name) matches the given nameFilter in the given directory - calling the DefaultGroovyMethods#isCase(Object, Object) method to determine if a match occurs.
    void eachFileMatch(Object nameFilter, Closure closure)
        Invokes the closure for each file whose name (file.name) matches the given nameFilter in the given directory - calling the DefaultGroovyMethods#isCase(Object, Object) method to determine if a match occurs.
    void eachFileRecurse(FileType fileType, Closure closure)
        Processes each descendant file in this directory and any sub-directories.
    void eachFileRecurse(Closure closure)
        Processes each descendant file in this directory and any sub-directories.
    Object eachLine(Closure closure)
        Iterates through this path line by line.
    Object eachLine(int firstLine, Closure closure)
        Iterates through this file line by line.
    Object eachLine(String charset, Closure closure)
        Iterates through this file line by line.
    Object eachLine(String charset, int firstLine, Closure closure)
        Iterates through this file line by line.
    void eachObject(Closure closure)
        Iterates through the given file object by object.
    Writable filterLine(Closure closure)
        Filters the lines of a Path and creates a Writable in return to stream the filtered lines.
    void filterLine(Writer writer, Closure closure)
        Filter the lines from this Path, and write them to the given writer based on the given closure predicate.
    void filterLine(Writer writer, String charset, Closure closure)
        Filter the lines from this Path, and write them to the given writer based on the given closure predicate.
    Writable filterLine(String charset, Closure closure)
        Filters the lines of a Path and creates a Writable in return to stream the filtered lines.
    byte[] getBytes()
        Read the content of the Path and returns it as a byte[].
    String getText()
        Read the content of the Path and returns it as a String.
    String getText(String charset)
        Read the content of the Path using the specified encoding and return it as a String.
    Path leftShift(byte[] bytes)
        Write bytes to a Path.
    Path leftShift(InputStream data)
        Append binary data to the file.
    Path leftShift(Object text)
        Write the text to the Path.
    DataInputStream newDataInputStream()
        Create a data input stream for this file
    DataOutputStream newDataOutputStream()
        Creates a new data output stream for this file.
    BufferedInputStream newInputStream()
        Creates a buffered input stream for this file.
    ObjectInputStream newObjectInputStream()
        Create an object input stream for this file.
    ObjectInputStream newObjectInputStream(ClassLoader classLoader)
        Create an object input stream for this path using the given class loader.
    ObjectOutputStream newObjectOutputStream()
        Create an object output stream for this path.
    BufferedOutputStream newOutputStream()
        Create a buffered output stream for this file.
    PrintWriter newPrintWriter()
        Create a new PrintWriter for this file.
    PrintWriter newPrintWriter(String charset)
        Create a new PrintWriter for this file, using specified charset.
    BufferedReader newReader()
        Create a buffered reader for this file.
    BufferedReader newReader(String charset)
        Create a buffered reader for this file, using the specified charset as the encoding.
    BufferedWriter newWriter()
        Create a buffered writer for this file.
    BufferedWriter newWriter(boolean append)
        Creates a buffered writer for this file, optionally appending to the existing file content.
    BufferedWriter newWriter(String charset)
        Creates a buffered writer for this file without writing a BOM, writing data using the given encoding.
    BufferedWriter newWriter(String charset, boolean append)
        Helper method to create a buffered writer for a file without writing a BOM.
    BufferedWriter newWriter(String charset, boolean append, boolean writeBom)
        Helper method to create a buffered writer for a file.
    byte[] readBytes()
        Reads the content of the file into a byte array.
    List readLines()
        Reads the file into a list of Strings, with one item for each line.
    List readLines(String charset)
        Reads the file into a list of Strings, with one item for each line.
    boolean renameTo(String newPathName)
        Renames a file.
    boolean renameTo(URI newPathName)
        Renames a file.
    void setBytes(byte[] bytes)
        Write the bytes from the byte array to the Path.
    void setText(String text)
        Synonym for write(text) allowing file.text = foo.
    void setText(String text, String charset)
        Synonym for write(text, charset) allowing:
    long size()
        Provide the standard Groovy size() method for Path.
    Object splitEachLine(String regex, Closure closure)
        Iterates through this file line by line, splitting each line using the given regex separator.
    Object splitEachLine(String regex, String charset, Closure closure)
        Iterates through this file line by line, splitting each line using the given regex separator.
    Object splitEachLine(Pattern pattern, Closure closure)
        Iterates through this file line by line, splitting each line using the given separator Pattern.
    Object splitEachLine(Pattern pattern, String charset, Closure closure)
        Iterates through this file line by line, splitting each line using the given regex separator Pattern.
    void traverse(Closure closure)
        Processes each descendant file in this directory and any sub-directories.
    void traverse(Map options)
        Invokes the closure specified with key visit in the options Map for each descendant file in this directory tree.
    void traverse(Map options, Closure closure)
        Processes each descendant file in this directory and any sub-directories.
    Object withDataInputStream(Closure closure)
        Create a new DataInputStream for this file and passes it into the closure.
    Object withDataOutputStream(Closure closure)
        Create a new DataOutputStream for this file and passes it into the closure.
    Object withInputStream(Closure closure)
        Create a new InputStream for this file and passes it into the closure.
    Object withObjectInputStream(Closure closure)
        Create a new ObjectInputStream for this file and pass it to the closure.
    Object withObjectInputStream(ClassLoader classLoader, Closure closure)
        Create a new ObjectInputStream for this file associated with the given class loader and pass it to the closure.
    Object withObjectOutputStream(Closure closure)
        Create a new ObjectOutputStream for this path and then pass it to the closure.
    Object withOutputStream(Closure closure)
        Creates a new OutputStream for this file and passes it into the closure.
    Object withPrintWriter(Closure closure)
        Create a new PrintWriter for this file which is then passed it into the given closure.
    Object withPrintWriter(String charset, Closure closure)
        Create a new PrintWriter with a specified charset for this file.
    Object withReader(Closure closure)
        Create a new BufferedReader for this file and then passes it into the closure, ensuring the reader is closed after the closure returns.
    Object withReader(String charset, Closure closure)
        Create a new BufferedReader for this file using the specified charset and then passes it into the closure, ensuring the reader is closed after the closure returns.
    Object withWriter(Closure closure)
        Creates a new BufferedWriter for this file, passes it to the closure, and ensures the stream is flushed and closed after the closure returns.
    Object withWriter(String charset, boolean writeBom, Closure closure)
        Creates a new BufferedWriter for this file, passes it to the closure, and ensures the stream is flushed and closed after the closure returns.
    Object withWriter(String charset, Closure closure)
        Creates a new BufferedWriter for this file, passes it to the closure, and ensures the stream is flushed and closed after the closure returns.
    Object withWriterAppend(Closure closure)
        Create a new BufferedWriter for this file in append mode.
    Object withWriterAppend(String charset, boolean writeBom, Closure closure)
        Create a new BufferedWriter which will append to this file.
    Object withWriterAppend(String charset, Closure closure)
        Create a new BufferedWriter which will append to this file.
    void write(String text)
        Write the text to the Path without writing a BOM .
    void write(String text, boolean writeBom)
        Write the text to the Path.
    void write(String text, String charset)
        Write the text to the Path without writing a BOM, using the specified encoding.
    void write(String text, String charset, boolean writeBom)
        Write the text to the Path, using the specified encoding.
    //Methods inherited from interface java.lang.Comparable
    //Methods inherited from interface java.lang.Iterable


*java.io.File 
    void append(byte[] bytes)
        Append bytes to the end of a File.
    void append(InputStream stream)
        Append binary data to the file.
    void append(Reader reader)
        Append the text supplied by the Writer at the end of the File without writing a BOM.
    void append(Reader reader, boolean writeBom)
        Append the text supplied by the Reader at the end of the File, using a specified encoding.
    void append(Reader reader, String charset)
        Append the text supplied by the Reader at the end of the File without writing a BOM, using a specified encoding.
    void append(Reader reader, String charset, boolean writeBom)
        Append the text supplied by the Reader at the end of the File, using a specified encoding.
    void append(Writer writer)
        Append the text supplied by the Writer at the end of the File without writing a BOM.
    void append(Writer writer, boolean writeBom)
        Append the text supplied by the Writer at the end of the File.
    void append(Writer writer, String charset)
        Append the text supplied by the Writer at the end of the File without writing a BOM, using a specified encoding.
    void append(Writer writer, String charset, boolean writeBom)
        Append the text supplied by the Writer at the end of the File, using a specified encoding.
    void append(Object text)
        Append the text at the end of the File without writing a BOM.
    void append(Object text, boolean writeBom)
        Append the text at the end of the File.
    void append(Object text, String charset)
        Append the text at the end of the File without writing a BOM, using a specified encoding.
    void append(Object text, String charset, boolean writeBom)
        Append the text at the end of the File, using a specified encoding.
    Object asType(Class c)
        Converts this File to a Writable or delegates to default DefaultGroovyMethods#asType(java.lang.Object, java.lang.Class).
    File asWritable()
        Converts this File to a Writable.
    File asWritable(String encoding)
        Allows a file to return a Writable implementation that can output itself to a Writer stream.
    static File createTempDir()
        static File createTempDir(String prefix, String suffix)
    boolean deleteDir()
        Deletes a directory with all contained files and subdirectories.
    long directorySize()
        Calculates directory size as total size of all its files, recursively.
    void eachByte(Closure closure)
        Traverse through each byte of this File
    void eachByte(int bufferLen, Closure closure)
        Traverse through the bytes of this File, bufferLen bytes at a time.
    void eachDir(Closure closure)
        Invokes the closure for each subdirectory in this directory, ignoring regular files.
    void eachDirMatch(Object nameFilter, Closure closure)
        Invokes the closure for each subdirectory whose name (dir.name) matches the given nameFilter in the given directory - calling the DefaultGroovyMethods#isCase(java.lang.Object, java.lang.Object) method to determine if a match occurs.
    void eachDirRecurse(Closure closure)
        Recursively processes each descendant subdirectory in this directory.
    void eachFile(FileType fileType, Closure closure)
        Invokes the closure for each child file in this parent folder/directory.
    void eachFile(Closure closure)
        Invokes the closure for each child file in this parent folder/directory.
    void eachFileMatch(FileType fileType, Object nameFilter, Closure closure)
        Invokes the closure for each file whose name (file.name) matches the given nameFilter in the given directory - calling the DefaultGroovyMethods#isCase(java.lang.Object, java.lang.Object) method to determine if a match occurs.
    void eachFileMatch(Object nameFilter, Closure closure)
        Invokes the closure for each file whose name (file.name) matches the given nameFilter in the given directory - calling the DefaultGroovyMethods#isCase(java.lang.Object, java.lang.Object) method to determine if a match occurs.
    void eachFileRecurse(FileType fileType, Closure closure)
        Processes each descendant file in this directory and any sub-directories.
    void eachFileRecurse(Closure closure)
        Processes each descendant file in this directory and any sub-directories.
    Object eachLine(Closure closure)
        Iterates through this file line by line.
    Object eachLine(int firstLine, Closure closure)
        Iterates through this file line by line.
    Object eachLine(String charset, Closure closure)
        Iterates through this file line by line.
    Object eachLine(String charset, int firstLine, Closure closure)
        Iterates through this file line by line.
    void eachObject(Closure closure)
        Iterates through the given file object by object.
    Writable filterLine(Closure closure)
        Filters the lines of a File and creates a Writable in return to stream the filtered lines.
    void filterLine(Writer writer, Closure closure)
        Filter the lines from this File, and write them to the given writer based on the given closure predicate.
    void filterLine(Writer writer, String charset, Closure closure)
        Filter the lines from this File, and write them to the given writer based on the given closure predicate.
    Writable filterLine(String charset, Closure closure)
        Filters the lines of a File and creates a Writable in return to stream the filtered lines.
    byte[] getBytes()
        Read the content of the File and returns it as a byte[].
    String getText()
        Read the content of the File and returns it as a String.
    String getText(String charset)
        Read the content of the File using the specified encoding and return it as a String.
    File leftShift(byte[] bytes)
        Write bytes to a File.
    File leftShift(InputStream data)
        Append binary data to the file.
    File leftShift(Object text)
        Write the text to the File.
    DataInputStream newDataInputStream()
        Create a data input stream for this file
    DataOutputStream newDataOutputStream()
        Creates a new data output stream for this file.
    BufferedInputStream newInputStream()
        Creates a buffered input stream for this file.
    ObjectInputStream newObjectInputStream()
        Create an object input stream for this file.
    ObjectInputStream newObjectInputStream(ClassLoader classLoader)
        Create an object input stream for this file using the given class loader.
    ObjectOutputStream newObjectOutputStream()
        Create an object output stream for this file.
    BufferedOutputStream newOutputStream()
        Create a buffered output stream for this file.
    PrintWriter newPrintWriter()
        Create a new PrintWriter for this file.
    PrintWriter newPrintWriter(String charset)
        Create a new PrintWriter for this file, using specified charset.
    BufferedReader newReader()
        Create a buffered reader for this file.
    BufferedReader newReader(String charset)
        Create a buffered reader for this file, using the specified charset as the encoding.
    BufferedWriter newWriter()
        Create a buffered writer for this file.
    BufferedWriter newWriter(boolean append)
        Creates a buffered writer for this file, optionally appending to the existing file content.
    BufferedWriter newWriter(String charset)
        Creates a buffered writer for this file, writing data without writing a BOM, using a specified encoding.
    BufferedWriter newWriter(String charset, boolean append)
        Helper method to create a buffered writer for a file without writing a BOM.
    BufferedWriter newWriter(String charset, boolean append, boolean writeBom)
        Helper method to create a buffered writer for a file.
    byte[] readBytes()
        Reads the content of the file into a byte array.
    List readLines()
        Reads the file into a list of Strings, with one item for each line.
    List readLines(String charset)
        Reads the file into a list of Strings, with one item for each line.
    String relativePath(File to)
        Relative path to file.
    boolean renameTo(String newPathName)
        Renames the file.
    void setBytes(byte[] bytes)
        Write the bytes from the byte array to the File.
    void setText(String text)
        Synonym for write(text) allowing file.text = foo.
    void setText(String text, String charset)
        Synonym for write(text, charset) allowing
    long size()
        Provide the standard Groovy size() method for File.
    Object splitEachLine(String regex, Closure closure)
        Iterates through this file line by line, splitting each line using the given regex separator.
    Object splitEachLine(String regex, String charset, Closure closure)
        Iterates through this file line by line, splitting each line using the given regex separator.
    Object splitEachLine(Pattern pattern, Closure closure)
        Iterates through this file line by line, splitting each line using the given separator Pattern.
    Object splitEachLine(Pattern pattern, String charset, Closure closure)
        Iterates through this file line by line, splitting each line using the given regex separator Pattern.
    void traverse(Closure closure)
        Processes each descendant file in this directory and any sub-directories.
    void traverse(Map options)
        Invokes the closure specified with key visit in the options Map for each descendant file in this directory tree.
    void traverse(Map options, Closure closure)
        Processes each descendant file in this directory and any sub-directories.
    Object withDataInputStream(Closure closure)
        Create a new DataInputStream for this file and passes it into the closure.
    Object withDataOutputStream(Closure closure)
        Create a new DataOutputStream for this file and passes it into the closure.
    Object withInputStream(Closure closure)
        Create a new InputStream for this file and passes it into the closure.
    Object withObjectInputStream(Closure closure)
        Create a new ObjectInputStream for this file and pass it to the closure.
    Object withObjectInputStream(ClassLoader classLoader, Closure closure)
        Create a new ObjectInputStream for this file associated with the given class loader and pass it to the closure.
    Object withObjectOutputStream(Closure closure)
        Create a new ObjectOutputStream for this file and then pass it to the closure.
    Object withOutputStream(Closure closure)
        Creates a new OutputStream for this file and passes it into the closure.
    Object withPrintWriter(Closure closure)
        Create a new PrintWriter for this file which is then passed it into the given closure.
    Object withPrintWriter(String charset, Closure closure)
        Create a new PrintWriter with a specified charset for this file.
    Object withReader(Closure closure)
        Create a new BufferedReader for this file and then passes it into the closure, ensuring the reader is closed after the closure returns.
    Object withReader(String charset, Closure closure)
        Create a new BufferedReader for this file using the specified charset and then passes it into the closure, ensuring the reader is closed after the closure returns.
    Object withWriter(Closure closure)
        Creates a new BufferedWriter for this file, passes it to the closure, and ensures the stream is flushed and closed after the closure returns.
    Object withWriter(String charset, Closure closure)
        Creates a new BufferedWriter for this file, passes it to the closure, and ensures the stream is flushed and closed after the closure returns.
    Object withWriterAppend(Closure closure)
        Create a new BufferedWriter for this file in append mode.
    Object withWriterAppend(String charset, Closure closure)
        Create a new BufferedWriter which will append to this file.
    void write(String text)
        Write the text to the File without writing a BOM.
    void write(String text, boolean writeBom)
        Write the text to the File.
    void write(String text, String charset)
        Write the text to the File without writing a BOM, using the specified encoding.
    void write(String text, String charset, boolean writeBom)
        Write the text to the File, using the specified encoding.
    //Methods inherited from class java.lang.Object
    //Methods inherited from Comparator 


*java.io.InputStream  
    void eachByte(Closure closure)
        Traverse through each byte of the specified stream.
    void eachByte(int bufferLen, Closure closure)
        Traverse through each the specified stream reading bytes into a buffer and calling the 2 parameter closure with this buffer and the number of bytes.
    Object eachLine(Closure closure)
        Iterates through this stream, passing each line to the given 1 or 2 arg closure.
    Object eachLine(int firstLine, Closure closure)
        Iterates through this stream, passing each line to the given 1 or 2 arg closure.
    Object eachLine(String charset, Closure closure)
        Iterates through this stream reading with the provided charset, passing each line to the given 1 or 2 arg closure.
    Object eachLine(String charset, int firstLine, Closure closure)
        Iterates through this stream reading with the provided charset, passing each line to the given 1 or 2 arg closure.
    Writable filterLine(Closure predicate)
        Filter lines from an input stream using a closure predicate.
    void filterLine(Writer writer, Closure predicate)
        Uses a closure to filter lines from this InputStream and pass them to the given writer.
    void filterLine(Writer writer, String charset, Closure predicate)
        Uses a closure to filter lines from this InputStream and pass them to the given writer.
    Writable filterLine(String charset, Closure predicate)
        Filter lines from an input stream using a closure predicate.
    byte[] getBytes()
        Read the content of this InputStream and return it as a byte[].
    String getText()
        Read the content of this InputStream and return it as a String.
    String getText(String charset)
        Read the content of this InputStream using specified charset and return it as a String.
    Iterator iterator()
        Standard iterator for a input stream which iterates through the stream content in a byte-based fashion.
    ObjectInputStream newObjectInputStream()
        Create an object input stream for this input stream.
    ObjectInputStream newObjectInputStream(ClassLoader classLoader)
        Create an object input stream for this input stream using the given class loader.
    BufferedReader newReader()
        Creates a reader for this input stream.
    BufferedReader newReader(String charset)
        Creates a reader for this input stream, using the specified charset as the encoding.
    List readLines()
        Reads the stream into a list, with one element for each line.
    List readLines(String charset)
        Reads the stream into a list, with one element for each line.
    Object splitEachLine(String regex, Closure closure)
        Iterates through the given InputStream line by line, splitting each line using the given separator.
    Object splitEachLine(String regex, String charset, Closure closure)
        Iterates through the given InputStream line by line using the specified encoding, splitting each line using the given separator.
    Object splitEachLine(Pattern pattern, Closure closure)
        Iterates through the given InputStream line by line, splitting each line using the given separator Pattern.
    Object splitEachLine(Pattern pattern, String charset, Closure closure)
        Iterates through the given InputStream line by line using the specified encoding, splitting each line using the given separator Pattern.
    Object withObjectInputStream(Closure closure)
        Create a new ObjectInputStream for this file and pass it to the closure.
    Object withObjectInputStream(ClassLoader classLoader, Closure closure)
        Create a new ObjectInputStream for this file and pass it to the closure.
    Object withReader(Closure closure)
        Helper method to create a new Reader for a stream and then passes it into the closure.
    Object withReader(String charset, Closure closure)
        Helper method to create a new Reader for a stream and then passes it into the closure.
    Object withStream(Closure closure)
        Allows this input stream to be used within the closure, ensuring that it is flushed and closed before this method returns. 
        
        
*java.io.OutputStream  
    OutputStream leftShift(byte[] value)
        Overloads the leftShift operator to provide an append mechanism to add bytes to a stream.
    OutputStream leftShift(InputStream in)
        Pipe an InputStream into an OutputStream for efficient stream copying.
    Writer leftShift(Object value)
        Overloads the leftShift operator to provide an append mechanism to add values to a stream.
    ObjectOutputStream newObjectOutputStream()
        Create an object output stream for this output stream.
    PrintWriter newPrintWriter()
        Create a new PrintWriter for this OutputStream.
    Writer newWriter()
        Creates a writer for this stream.
    Writer newWriter(String charset)
        Creates a writer for this stream using the given charset.
    void setBytes(byte[] bytes)
        Write the byte[] to the output stream.
    Object withObjectOutputStream(Closure closure)
        Create a new ObjectOutputStream for this output stream and then pass it to the closure.
    Object withPrintWriter(Closure closure)
        Create a new PrintWriter for this OutputStream.
    Object withStream(Closure closure)
        Passes this OutputStream to the closure, ensuring that the stream is closed after the closure returns, regardless of errors.
    Object withWriter(Closure closure)
        Creates a writer from this stream, passing it to the given closure.
    Object withWriter(String charset, Closure closure)
        Creates a writer from this stream, passing it to the given closure. 

*java.io.Writer  
    Writer leftShift(Object value)
        Overloads the leftShift operator for Writer to allow an object to be written using Groovys default representation for the object.
    PrintWriter newPrintWriter()
        Create a new PrintWriter for this Writer.
    Object withPrintWriter(Closure closure)
        Create a new PrintWriter for this Writer.
    Object withWriter(Closure closure)
        Allows this writer to be used within the closure, ensuring that it is flushed and closed before this method returns.
    void write(Writable writable)
        A helper method so that dynamic dispatch of the writer.write(object) method will always use the more efficient Writable.writeTo(writer) mechanism if the object implements the Writable interface. 


*java.io.Reader  
    Object eachLine(Closure closure)
        Iterates through the given reader line by line.
    Object eachLine(int firstLine, Closure closure)
        Iterates through the given reader line by line.
    Writable filterLine(Closure closure)
        Filter the lines from this Reader, and return a Writable which can be used to stream the filtered lines to a destination.
    void filterLine(Writer writer, Closure closure)
        Filter the lines from a reader and write them on the writer, according to a closure which returns true if the line should be included.
    String getText()
        Read the content of the Reader and return it as a String.
    Iterator iterator()
        Creates an iterator which will traverse through the reader a line at a time.
    String readLine()
        Read a single, whole line from the given Reader.
    List readLines()
        Reads the reader into a list of Strings, with one entry for each line.
    Object splitEachLine(String regex, Closure closure)
        Iterates through the given reader line by line, splitting each line using the given regex separator.
    Object splitEachLine(Pattern pattern, Closure closure)
        Iterates through the given reader line by line, splitting each line using the given regex separator Pattern.
    void transformChar(Writer writer, Closure closure)
        Transforms each character from this reader by passing it to the given closure.
    void transformLine(Writer writer, Closure closure)
        Transforms the lines from a reader with a Closure and write them to a writer.
    Object withReader(Closure closure)
        Allows this reader to be used within the closure, ensuring that it is closed before this method returns. 

        
        
*java.lang.Number 
    int abs()
        Get the absolute value
    Number and(Number right)
        Bitwise AND together two Numbers.
    boolean asBoolean()
        Coerce a number to a boolean value.
    Object asType(Class c)
        Transform this number to a the given type, using the as operator.
    Number bitwiseNegate()
        Bitwise NEGATE a Number.
    int compareTo(Character right)
        Compare a Number and a Character.
    int compareTo(Number right)
        Compare two Numbers.
    Number div(Character right)
        Divide a Number by a Character.
    Number div(Number right)
        Divide two Numbers.
    void downto(Number to, Closure closure)
        Iterates from this number down to the given number, inclusive, decrementing by one each time.
    Number intdiv(Character right)
        Integer Divide a Number by a Character.
    Number intdiv(Number right)
        Integer Divide two Numbers.
    boolean isCase(Number switchValue)
        Special case implementation for all numbers, which delegates to the compareTo() method for comparing numbers of different types.
    Number leftShift(Number operand)
        Implementation of the left shift operator for integral types.
    Number minus(Character right)
        Subtract a Character from a Number.
    Number minus(Number right)
        Subtraction of two Numbers.
    Number mod(Number right)
        Performs a division modulus operation.
    Number multiply(Character right)
        Multiply a Number by a Character.
    Number multiply(Number right)
        Multiply two Numbers.
    Number next()
        Increment a Number by one.
    Number or(Number right)
        Bitwise OR together two numbers.
    Number plus(Character right)
        Add a Number and a Character.
    Number plus(Number right)
        Add two numbers and return the result.
    String plus(String right)
        Appends a String to the string representation of this number.
    Number power(Number exponent)
        Power of a Number to a certain exponent.
    Number previous()
        Decrement a Number by one.
    Number rightShift(Number operand)
        Implementation of the right shift operator for integral types.
    Number rightShiftUnsigned(Number operand)
        Implementation of the right shift (unsigned) operator for integral types.
    void step(Number to, Number stepNumber, Closure closure)
        Iterates from this number up to the given number using a step increment.
    void times(Closure closure)
        Executes the closure this many times, starting from zero.
    BigDecimal toBigDecimal()
        Transform a Number into a BigDecimal
    BigInteger toBigInteger()
        Transform this Number into a BigInteger.
    Double toDouble()
        Transform a Number into a Double
    Float toFloat()
        Transform a Number into a Float
    Integer toInteger()
        Transform a Number into an Integer
    Long toLong()
        Transform a Number into a Long
    Number unaryMinus()
        Negates the number.
    Number unaryPlus()
        Returns the number, effectively being a noop for numbers.
    void upto(Number to, Closure closure)
        Iterates from this number up to the given number, inclusive, incrementing by one each time.
    Number xor(Number right)
        Bitwise XOR together two Numbers.
    //Methods inherited from class java.lang.Object



*java.lang.Integer 
    Number power(Integer exponent)
        Power of an integer to an integer certain exponent. 
    //Methods from Number 

*java.lang.Double  
    double abs()
        Get the absolute value
    void downto(Number to, Closure closure)
        Iterates from this number down to the given number, inclusive, decrementing by one each time.
    long round()
        Round the value
    double round(int precision)
        Round the value
    double trunc()
        Truncate the value
    double trunc(int precision)
        Truncate the value
    void upto(Number to, Closure closure)
        Iterates from this number up to the given number, inclusive, incrementing by one each time. 
    //Methods from Number 

*java.math.BigInteger  
    void downto(Number to, Closure closure)
        Iterates from this number down to the given number, inclusive, decrementing by one each time.
    Number power(Integer exponent)
        Power of a BigInteger to an integer certain exponent.
    BigInteger power(BigInteger exponent)
        Power of a BigInteger to a BigInteger certain exponent.
    void upto(Number to, Closure closure)
        Iterates from this number up to the given number, inclusive, incrementing by one each time. 
    //Methods from Number 

*java.math.BigDecimal  
    void downto(Number to, Closure closure)
        Iterates from this number down to the given number, inclusive, decrementing by one each time.
    Number multiply(Double right)
        Multiply a BigDecimal and a Double.
    Number multiply(BigInteger right)
        Multiply a BigDecimal and a BigInteger.
    Number power(Integer exponent)
        Power of a BigDecimal to an integer certain exponent.
    BigDecimal round()
        Round the valueNote that this method differs from BigDecimal#round(java.math.MathContext) which specifies the digits to retain starting from the leftmost nonzero digit.
    BigDecimal round(int precision)
        Round the valueNote that this method differs from BigDecimal#round(java.math.MathContext) which specifies the digits to retain starting from the leftmost nonzero digit.
    BigDecimal trunc()
        Truncate the value
    BigDecimal trunc(int precision)
        Truncate the value
    void upto(Number to, Closure closure)
        Iterates from this number up to the given number, inclusive, incrementing by one each time. 
    //Methods from Number 

*java.lang.String  
    Object asType(Class c)
        Provides a method to perform custom dynamic type conversion to the given class using the as operator.
    String collectReplacements(Closure transform)
        Iterate through this String a character at a time collecting either the original character or a transformed replacement String.
    byte[] decodeBase64()
        Decode the String from Base64 into a byte array.
    byte[] decodeBase64Url()
        Decodes a Base64 URL and Filename Safe encoded String into a byte array.
    byte[] decodeHex()
        Decodes a hex string to a byte array.
    String eachMatch(String regex, Closure closure)
        Process each regex group matched substring of the given string.
    String eachMatch(Pattern pattern, Closure closure)
        Process each regex group matched substring of the given pattern.
    Process execute()
        Executes the command specified by self as a command-line process.
    Process execute(String[] envp, File dir)
        Executes the command specified by self with environment defined by envp and under the working directory dir.
    Process execute(List envp, File dir)
        Executes the command specified by self with environment defined by envp and under the working directory dir.
    String getAt(IntRange range)
        Support the range subscript operator for String with IntRange
    String getAt(Range range)
        Support the range subscript operator for String
    String getAt(int index)
        Support the subscript operator for String.
    StringBuffer leftShift(Object value)
        Overloads the left shift operator to provide an easy way to append multiple objects as string representations to a String.
    String plus(CharSequence value)
        Appends the String representation of the given operand to this string.
    int size()
        Provide the standard Groovy size() method for String.
    Boolean toBoolean()
        Converts the given string into a Boolean object.
    Character toCharacter()
        Converts the given string into a Character object using the first character in the string.
    URI toURI()
        Transforms a String representing a URI into a URI object.
    URL toURL()
        Transforms a String representing a URL into a URL object.
    //Methods inherited from interface java.lang.CharSequence
    //Methods inherited from class java.lang.Object


*java.lang.CharSequence
    boolean asBoolean()
        Coerce a string (an instance of CharSequence) to a boolean value.
    Object asType(Class c)
        Provides a method to perform custom dynamic type conversion to the given class using the as operator.
    Pattern bitwiseNegate()
        Turns a CharSequence into a regular expression Pattern
    String capitalize()
        Convenience method to capitalize the first letter of a CharSequence (typically the first letter of a word).
    String center(Number numberOfChars)
        Pad a CharSequence to a minimum length specified by numberOfChars by adding the space character around it as many times as needed so that it remains centered.
    String center(Number numberOfChars, CharSequence padding)
        Pad a CharSequence to a minimum length specified by numberOfChars, appending the supplied padding CharSequence around the original as many times as needed keeping it centered.
    boolean contains(CharSequence text)
        Provide an implementation of contains() like Collection#contains(Object) to make CharSequences more polymorphic.
    int count(CharSequence text)
        Count the number of occurrences of a sub CharSequence.
    String denormalize()
        Return a CharSequence with lines (separated by LF, CR/LF, or CR) terminated by the platform specific line separator.
    String digest(String algorithm)
        digest the CharSequence instance
    CharSequence drop(int num)
        Drops the given number of chars from the head of this CharSequence if they are available.
    String dropWhile(Closure condition)
        Create a suffix of the given CharSequence by dropping as many characters as possible from the front of the original CharSequence such that calling the given closure condition evaluates to true when passed each of the dropped characters.
    Object eachLine(Closure closure)
        Iterates through this CharSequence line by line.
    Object eachLine(int firstLine, Closure closure)
        Iterates through this CharSequence line by line.
    Object eachMatch(CharSequence regex, Closure closure)
        Process each regex group matched substring of the given CharSequence.
    Object eachMatch(Pattern pattern, Closure closure)
        Process each regex group matched substring of the given pattern.
    boolean endsWithAny(CharSequence suffixes)
        Tests if this CharSequence ends with any specified suffixes.
    String expand()
        Expands all tabs into spaces with tabStops of size 8.
    String expand(int tabStop)
        Expands all tabs into spaces.
    String expandLine(int tabStop)
        Expands all tabs into spaces.
    String find(CharSequence regex)
        Finds the first occurrence of a regular expression String within a String.
    String find(CharSequence regex, Closure closure)
        Returns the result of calling a closure with the first occurrence of a regular expression found within a CharSequence.
    String find(Pattern pattern)
        Finds the first occurrence of a compiled regular expression Pattern within a String.
    String find(Pattern pattern, Closure closure)
        Returns the result of calling a closure with the first occurrence of a compiled regular expression found within a String.
    List findAll(CharSequence regex)
        Returns a (possibly empty) list of all occurrences of a regular expression (provided as a CharSequence) found within a CharSequence.
    List findAll(CharSequence regex, Closure closure)
        Finds all occurrences of a regular expression string within a CharSequence.
    List findAll(Pattern pattern)
        Returns a (possibly empty) list of all occurrences of a regular expression (in Pattern format) found within a CharSequence.
    List findAll(Pattern pattern, Closure closure)
        Finds all occurrences of a compiled regular expression Pattern within a CharSequence.
    String getAt(EmptyRange range)
        Support the range subscript operator for CharSequence or StringBuffer with EmptyRange
    CharSequence getAt(IntRange range)
        Support the range subscript operator for CharSequence with IntRange
    CharSequence getAt(Range range)
        Support the range subscript operator for CharSequence
    CharSequence getAt(int index)
        Support the subscript operator for CharSequence.
    String getAt(Collection indices)
        Select a List of characters from a CharSequence using a Collection to identify the indices to be selected.
    char[] getChars()
        Converts the given CharSequence into an array of characters.
    boolean isAllWhitespace()
        True if a CharSequence only contains whitespace characters.
    boolean isBigDecimal()
        Determine if a CharSequence can be parsed as a BigDecimal.
    boolean isBigInteger()
        Determine if a CharSequence can be parsed as a BigInteger.
    boolean isBlank()
        Tests if this CharSequence is blank
    boolean isCase(Object switchValue)
        Case implementation for a CharSequence, which uses equals between the toString() of the caseValue and the switchValue.
    boolean isDouble()
        Determine if a CharSequence can be parsed as a Double.
    boolean isFloat()
        Determine if a CharSequence can be parsed as a Float.
    boolean isInteger()
        Determine if a CharSequence can be parsed as an Integer.
    boolean isLong()
        Determine if a CharSequence can be parsed as a Long.
    boolean isNumber()
        Determine if a CharSequence can be parsed as a Number.
    StringBuilder leftShift(Object value)
        Overloads the left shift operator to provide an easy way to append multiple objects as string representations to a CharSequence.
    boolean matches(Pattern pattern)
        Tells whether or not a CharSequence matches the given compiled regular expression Pattern.
    String md5()
        Calculate md5 of the CharSequence instance
    String minus(Object target)
        Remove a part of a CharSequence by replacing the first occurrence of target within self with  and returns the result.
    String minus(Pattern pattern)
        Remove a part of a CharSequence.
    String multiply(Number factor)
        Repeat a CharSequence a certain number of times.
    String next()
        This method is called by the ++ operator for the class CharSequence.
    String normalize()
        Return a String with linefeeds and carriage returns normalized to linefeeds.
    String padLeft(Number numberOfChars)
        Pad a CharSequence to a minimum length specified by numberOfChars by adding the space character to the left as many times as needed.
    String padLeft(Number numberOfChars, CharSequence padding)
        Pad a CharSequence to a minimum length specified by numberOfChars, adding the supplied padding CharSequence as many times as needed to the left.
    String padRight(Number numberOfChars)
        Pad a CharSequence to a minimum length specified by numberOfChars by adding the space character to the right as many times as needed.
    String padRight(Number numberOfChars, CharSequence padding)
        Pad a CharSequence to a minimum length specified by numberOfChars, adding the supplied padding CharSequence as many times as needed to the right.
    String plus(Object value)
        Appends the String representation of the given operand to this CharSequence.
    String previous()
        This method is called by the -- operator for the class CharSequence.
    List readLines()
        Return the lines of a CharSequence as a List of String.
    String replace(int capacity, Map replacements)
        Replaces all occurrences of replacement CharSequences (supplied via a map) within a provided CharSequence with control over the internally created StringBuilders capacity.
    String replace(Map replacements)
        Replaces all occurrences of replacement CharSequences (supplied via a map) within a provided CharSequence.
    String replaceAll(CharSequence regex, Closure closure)
        Replaces all occurrences of a captured group by the result of calling a closure on that text.
    String replaceAll(CharSequence regex, CharSequence replacement)
        Replaces each substring of this CharSequence that matches the given regular expression with the given replacement.
    String replaceAll(Pattern pattern, Closure closure)
        Replaces all occurrences of a captured group by the result of a closure call on that text.
    String replaceAll(Pattern pattern, CharSequence replacement)
        Replaces all substrings of a CharSequence that match the given compiled regular expression with the given replacement.
    String replaceFirst(CharSequence regex, Closure closure)
        Replaces the first occurrence of a captured group by the result of a closure call on that text.
    String replaceFirst(CharSequence regex, CharSequence replacement)
        Replaces the first substring of this CharSequence that matches the given regular expression with the given replacement.
    String replaceFirst(Pattern pattern, Closure closure)
        Replaces the first occurrence of a captured group by the result of a closure call on that text.
    String replaceFirst(Pattern pattern, CharSequence replacement)
        Replaces the first substring of a CharSequence that matches the given compiled regular expression with the given replacement.
    String reverse()
        Creates a String which is the reverse (backwards) of this CharSequence
    int size()
        Provide the standard Groovy size() method for CharSequence.
    String[] split()
        Convenience method to split a CharSequence (with whitespace as delimiter).
    Object splitEachLine(CharSequence regex, Closure closure)
        Iterates through the given CharSequence line by line, splitting each line using the given regex delimiter.
    Object splitEachLine(Pattern pattern, Closure closure)
        Iterates through the given CharSequence line by line, splitting each line using the given separator Pattern.
    boolean startsWithAny(CharSequence prefixes)
        Tests if this CharSequence starts with any specified prefixes.
    String stripIndent()
        Strip leading spaces from every line in a CharSequence.
    String stripIndent(int numChars)
        Strip numChar leading characters from every line in a CharSequence.
    String stripMargin()
        Strip leading whitespace/control characters followed by | from every line in a CharSequence.
    String stripMargin(char marginChar)
        Strip leading whitespace/control characters followed by marginChar from every line in a CharSequence.
    String stripMargin(CharSequence marginChar)
        Strip leading whitespace/control characters followed by marginChar from every line in a CharSequence.
    CharSequence take(int num)
        Returns the first num elements from this CharSequence.
    String takeWhile(Closure condition)
        Returns the longest prefix of this CharSequence where each element passed to the given closure evaluates to true.
    BigDecimal toBigDecimal()
        Parse a CharSequence into a BigDecimal
    BigInteger toBigInteger()
        Parse a CharSequence into a BigInteger
    Double toDouble()
        Parse a CharSequence into a Double
    Float toFloat()
        Parse a CharSequence into a Float
    Integer toInteger()
        Parse a CharSequence into an Integer
    List toList()
        Converts the given CharSequence into a List of Strings of one character.
    Long toLong()
        Parse a CharSequence into a Long
    Set toSet()
        Converts the given CharSequence into a Set of unique Strings of one character.
    Short toShort()
        Parse a CharSequence into a Short
    URI toURI()
        Transforms a CharSequence representing a URI into a URI object.
    URL toURL()
        Transforms a CharSequence representing a URL into a URL object.
    List tokenize()
        Tokenize a CharSequence (with a whitespace as the delimiter).
    List tokenize(CharSequence delimiters)
        Tokenize a CharSequence based on the given CharSequence.
    List tokenize(Character delimiter)
        Tokenize a CharSequence based on the given character delimiter.
    String tr(CharSequence sourceSet, CharSequence replacementSet)
        Translates a CharSequence by replacing characters from the sourceSet with characters from replacementSet.
    String uncapitalize()
        Convenience method to uncapitalize the first letter of a CharSequence (typically the first letter of a word).
    String unexpand()
        Replaces sequences of whitespaces with tabs using tabStops of size 8.
    String unexpand(int tabStop)
        Replaces sequences of whitespaces with tabs.
    String unexpandLine(int tabStop)
        Replaces sequences of whitespaces with tabs within a line. 


*java.lang.StringBuilder  
    StringBuilder leftShift(Object value)
        Overloads the left shift operator to provide syntactic sugar for appending to a StringBuilder.
    String plus(String value)
        Appends a String to this StringBuilder.
    void putAt(EmptyRange range, Object value)
        Support the range subscript operator for StringBuilder.
    void putAt(IntRange range, Object value)
        Support the range subscript operator for StringBuilder.
    int size()
        Standard Groovy size() method for StringBuilders.
    //Methods inherited from class java.lang.Object
    //Methods inherited from interface java.lang.CharSequence
    

*java.util.regex.Matcher  
    boolean asBoolean()
        Coerce a Matcher instance to a boolean value.
    Object getAt(int idx)
        Support the subscript operator, e.g. matcher[index], for a regex Matcher.
    List getAt(Collection indices)
        Select a List of values from a Matcher using a Collection to identify the indices to be selected.
    int getCount()
        Find the number of Strings matched to the given Matcher.
    static Matcher getLastMatcher()
        Get the last hidden matcher that the system used to do a match.
    boolean hasGroup()
        Check whether a Matcher contains a group or not.
    Iterator iterator()
        Returns an Iterator which traverses each match.
    boolean matchesPartially()
        Given a matcher that matches a string against a pattern, this method returns true when the string matches the pattern or if a longer string, could match the pattern.
    void setIndex(int idx)
        Set the position of the given Matcher to the given index.
    long size()
        Provide the standard Groovy size() method for Matcher. 


*java.net.URL  
    void eachByte(Closure closure)
        Reads the InputStream from this URL, passing each byte to the given closure.
    void eachByte(int bufferLen, Closure closure)
        Reads the InputStream from this URL, passing a byte[] and a number of bytes to the given closure.
    Object eachLine(Closure closure)
        Iterates through the lines read from the URLs associated input stream passing each line to the given 1 or 2 arg closure.
    Object eachLine(int firstLine, Closure closure)
        Iterates through the lines read from the URLs associated input stream passing each line to the given 1 or 2 arg closure.
    Object eachLine(String charset, Closure closure)
        Iterates through the lines read from the URLs associated input stream passing each line to the given 1 or 2 arg closure.
    Object eachLine(String charset, int firstLine, Closure closure)
        Iterates through the lines read from the URLs associated input stream passing each line to the given 1 or 2 arg closure.
    Writable filterLine(Closure predicate)
        Filter lines from a URL using a closure predicate.
    void filterLine(Writer writer, Closure predicate)
        Uses a closure to filter lines from this URL and pass them to the given writer.
    void filterLine(Writer writer, String charset, Closure predicate)
        Uses a closure to filter lines from this URL and pass them to the given writer.
    Writable filterLine(String charset, Closure predicate)
        Filter lines from a URL using a closure predicate.
    byte[] getBytes()
        Read the content of this URL and returns it as a byte[].
    byte[] getBytes(Map parameters)
        Read the content of this URL and returns it as a byte[].
    String getText()
        Read the content of this URL and returns it as a String.
    String getText(String charset)
        Read the data from this URL and return it as a String.
    String getText(Map parameters)
        Read the content of this URL and returns it as a String.
    String getText(Map parameters, String charset)
        Read the data from this URL and return it as a String.
    BufferedInputStream newInputStream()
        Creates a buffered input stream for this URL.
    BufferedInputStream newInputStream(Map parameters)
        Creates a buffered input stream for this URL.
    BufferedReader newReader()
        Creates a buffered reader for this URL.
    BufferedReader newReader(String charset)
        Creates a buffered reader for this URL using the given encoding.
    BufferedReader newReader(Map parameters)
        Creates a buffered reader for this URL.
    BufferedReader newReader(Map parameters, String charset)
        Creates a buffered reader for this URL using the given encoding.
    List readLines()
        Reads the URL contents into a list, with one element for each line.
    List readLines(String charset)
        Reads the URL contents into a list, with one element for each line.
    Object splitEachLine(String regex, Closure closure)
        Iterates through the input stream associated with this URL line by line, splitting each line using the given regex separator.
    Object splitEachLine(String regex, String charset, Closure closure)
        Iterates through the input stream associated with this URL line by line, splitting each line using the given regex separator.
    Object splitEachLine(Pattern pattern, Closure closure)
        Iterates through the input stream associated with this URL line by line, splitting each line using the given regex separator Pattern.
    Object splitEachLine(Pattern pattern, String charset, Closure closure)
        Iterates through the input stream associated with this URL line by line, splitting each line using the given regex separator Pattern.
    Object withInputStream(Closure closure)
        Creates a new InputStream for this URL and passes it into the closure.
    Object withReader(Closure closure)
        Helper method to create a new BufferedReader for a URL and then passes it to the closure.
    Object withReader(String charset, Closure closure)
        Helper method to create a new Reader for a URL and then passes it to the closure. 


*java.net.Socket  
    OutputStream leftShift(byte[] value)
        Overloads the left shift operator to provide an append mechanism to add bytes to the output stream of a socket
    Writer leftShift(Object value)
        Overloads the left shift operator to provide an append mechanism to add things to the output stream of a socket
    Object withObjectStreams(Closure closure)
        Creates an InputObjectStream and an OutputObjectStream from a Socket, and passes them to the closure.
    Object withStreams(Closure closure)
        Passes the Sockets InputStream and OutputStream to the closure. 


*java.net.SocketServer  
    Socket accept(boolean runInANewThread, Closure closure)
        Accepts a connection and passes the resulting Socket to the closure which runs in a new Thread or the calling thread, as needed.
    Socket accept(Closure closure)
        Accepts a connection and passes the resulting Socket to the closure which runs in a new Thread. 


*java.lang.Thread  
    static Thread start(Closure closure)
        Start a Thread with the given closure as a Runnable instance.
    static Thread start(String name, Closure closure)
        Start a Thread with a given name and the given closure as a Runnable instance.
    static Thread startDaemon(Closure closure)
        Start a daemon Thread with the given closure as a Runnable instance.
    static Thread startDaemon(String name, Closure closure)
        Start a daemon Thread with a given name and the given closure as a Runnable instance. 


*java.lang.Process  
    void closeStreams()
        Closes all the streams associated with the process (ignoring any IOExceptions).
    Thread consumeProcessErrorStream(OutputStream err)
        Gets the error stream from a process and reads it to keep the process from blocking due to a full buffer.
    Thread consumeProcessErrorStream(Appendable error)
        Gets the error stream from a process and reads it to keep the process from blocking due to a full buffer.
    void consumeProcessOutput()
        Gets the output and error streams from a process and reads them to keep the process from blocking due to a full output buffer.
    void consumeProcessOutput(OutputStream output, OutputStream error)
        Gets the output and error streams from a process and reads them to keep the process from blocking due to a full output buffer.
    void consumeProcessOutput(Appendable output, Appendable error)
        Gets the output and error streams from a process and reads them to keep the process from blocking due to a full output buffer.
    Thread consumeProcessOutputStream(OutputStream output)
        Gets the output stream from a process and reads it to keep the process from blocking due to a full output buffer.
    Thread consumeProcessOutputStream(Appendable output)
        Gets the output stream from a process and reads it to keep the process from blocking due to a full output buffer.
    InputStream getErr()
        An alias method so that a process appears similar to System.out, System.in, System.err; you can use process.in, process.out, process.err in a similar fashion.
    InputStream getIn()
        An alias method so that a process appears similar to System.out, System.in, System.err; you can use process.in, process.out, process.err in a similar fashion.
    OutputStream getOut()
        An alias method so that a process appears similar to System.out, System.in, System.err; you can use process.in, process.out, process.err in a similar fashion.
    String getText()
        Read the text of the output stream of the Process.
    OutputStream leftShift(byte[] value)
        Overloads the left shift operator to provide an append mechanism to pipe into a Process
    Writer leftShift(Object value)
        Overloads the left shift operator (<<) to provide an append mechanism to pipe data to a Process.
    Process or(Process right)
        Overrides the or operator to allow one Process to asynchronously pipe data to another Process.
    Process pipeTo(Process right)
        Allows one Process to asynchronously pipe data to another Process.
    void waitForOrKill(long numberOfMillis)
        Wait for the process to finish during a certain amount of time, otherwise stops the process.
    void waitForProcessOutput()
        Gets the output and error streams from a process and reads them to keep the process from blocking due to a full output buffer.
    void waitForProcessOutput(OutputStream output, OutputStream error)
        Gets the output and error streams from a process and reads them to keep the process from blocking due to a full output buffer.
    void waitForProcessOutput(Appendable output, Appendable error)
        Gets the output and error streams from a process and reads them to keep the process from blocking due to a full output buffer.
    void withOutputStream(Closure closure)
        Creates a new buffered OutputStream as stdin for this process, passes it to the closure, and ensures the stream is flushed and closed after the closure returns.
    void withWriter(Closure closure)
        Creates a new BufferedWriter as stdin for this process, passes it to the closure, and ensures the stream is flushed and closed after the closure returns. 

       

*groovy.util.Node 
    Represents an arbitrary tree node which can be used for structured metadata or any arbitrary XML-like tree. 
    A node can have a name, a value and an optional Map of attributes. 
    Typically the name is a String and a value is either a String or a List of other Nodes, 
    Constructors 
        Node (Node parent, Object name)
            Creates a new Node named name and if a parent is supplied, adds the newly created node as a child of the parent.
        Node (Node parent, Object name, Object value)
            Creates a new Node named name with value value and if a parent is supplied, adds the newly created node as a child of the parent.
        Node (Node parent, Object name, Map attributes)
            Creates a new Node named name with attributes specified in the attributes Map.
        Node (Node parent, Object name, Map attributes, Object value)
            Creates a new Node named name with value value and with attributes specified in the attributes Map.
    Methods 
        boolean append(Node child)
            Appends a child to the current node.
        Node appendNode(Object name, Map attributes)
            Creates a new node as a child of the current node.
        Node appendNode(Object name)
            Creates a new node as a child of the current node.
        Node appendNode(Object name, Object value)
            Creates a new node as a child of the current node.
        Node appendNode(Object name, Map attributes, Object value)
            Creates a new node as a child of the current node.
        Object attribute(Object key)
            Provides lookup of attributes by key.
        Map attributes()
            Returns a Map of the attributes of the node or an empty Map if the node does not have any attributes.
        List breadthFirst()
            Provides a collection of all the nodes in the tree using a breadth-first preorder traversal.
        List breadthFirst(boolean preorder)
            Provides a collection of all the nodes in the tree using a breadth-first traversal.
        void breadthFirst(Closure c)
            Calls the provided closure for all the nodes in the tree using a breadth-first preorder traversal.
        void breadthFirst(Map<String, Object> options, Closure c)
            Calls the provided closure for all the nodes in the tree using a breadth-first traversal.
        List children()
            Returns a List of the nodes children.
        Object clone()
            Creates a new Node with the same name, no parent, shallow cloned attributes and if the value is a NodeList, a (deep) clone of those nodes.
        List depthFirst()
            Provides a collection of all the nodes in the tree using a depth-first preorder traversal.
        List depthFirst(boolean preorder)
            Provides a collection of all the nodes in the tree using a depth-first traversal.
        void depthFirst(Closure c)
            Provides a collection of all the nodes in the tree using a depth-first preorder traversal.
        void depthFirst(Map<String, Object> options, Closure c)
            Provides a collection of all the nodes in the tree using a depth-first traversal.
        Object get(String key)
            Provides lookup of elements by non-namespaced name
        NodeList getAt(QName name)
            Provides lookup of elements by QName.
        Iterator iterator()
            Returns an Iterator of the children of the node.
        List<String> localText()
            Returns the list of any direct String nodes of this node.
        Object name()
            Returns an Object representing the name of the node.
        Node parent()
            Returns the parent of the node.
        void plus(Closure c)
            Adds sibling nodes (defined using builder-style notation via a Closure) after the current node.
        void print(PrintWriter out)
            Writes the node to the specified PrintWriter.
        boolean remove(Node child)
            Removes a child of the current node.
        Node replaceNode(Closure c)
            Replaces the current node with nodes defined using builder-style notation via a Closure.
        Node replaceNode(Node n)
            Replaces the current node with the supplied node.
        protected static void setMetaClass(MetaClass metaClass, Class nodeClass)
            Extension point for subclasses to override the metaclass.
        protected void setParent(Node parent)
            Adds or replaces the parent of the node.
        void setValue(Object value)
            Adds or replaces the value of the node.
        String text()
            Returns the textual representation of the current node and all its child nodes.
        BigDecimal toBigDecimal()
            Converts the text of this GPathResult to a BigDecimal object.
        BigInteger toBigInteger()
            Converts the text of this GPathResult to a BigInteger object.
        Double toDouble()
            Converts the text of this GPathResult to a Double object.
        Float toFloat()
            Converts the text of this GPathResult to a Float object.
        Integer toInteger()
            Converts the text of this GPathResult to a Integer object.
        Long toLong()
            Converts the text of this GPathResult to a Long object.
        String toString()
            Object value()
        Returns an Object representing the value of the node.        