package com.zebsoft.right.domain;

import java.util.Comparator;

public class MenuComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		TreeNode t1 = (TreeNode) o1;
		TreeNode t2 = (TreeNode) o2;
		if (t1.getTheSort() > t2.getTheSort()) {
			return 1;
		} else if (t1.getTheSort() == t2.getTheSort()) {
			return 0;
		} else {
			return -1;
		}
	}
}
