package ecb.transformations.functions;

import java.util.ArrayList;
import java.util.List;

public class Functions {

    public static <T> List<T> getReverseOrderedList(List<T> list) {
	List<T> rList = new ArrayList<>();
	if (list != null && !list.isEmpty()) {
	    for (int i = list.size() - 1; i >= 0; i--) {
		rList.add(list.get(i));
	    }
	}
	return rList;
    }
}
