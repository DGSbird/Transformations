package ecb.transformations.functions;

import java.util.ArrayList;
import java.util.List;

import ecb.transformations.enums.Bracket;
import ecb.transformations.metadata.TContext;

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

    public static String coverInBrackets(String content, Bracket bracket) {
	String rString = bracket.getLeft() + content + bracket.getRight();
	return rString;
    }

    public static boolean isAlmostEmpty(String s) {
	return (s.replace("\n", "").trim().isEmpty());
    }

    public static boolean isEOF(String s) {
	return (s.equals(TContext.CONST_EOF));
    }

}
