package HelperMethods;

import java.util.*;

import java.lang.Object;

public class tokenizer {
	public static final String delim = " *+-/()";

	public static void main(String[] args) {

		String expression = "   a-(b+A[B[2]])*d+3";
		StringTokenizer st = new StringTokenizer(expression, delim);
		String output = "";
		while (st.hasMoreElements()) {

			String value = st.nextToken();

			int index = value.indexOf('[');

			boolean digit = Character.isDigit(value.charAt(0));

			if (index == -1 && !(digit)) {

				output += value + " ";

			} else if (!(value.indexOf('[') == -1)) {
				output += value + arraySaperator(st);

			}
		}
		
		System.out.println(output);

	}

	public static String arraySaperator(StringTokenizer st) {
		String output = "] ";

		while (st.hasMoreElements()) {
			String value = st.nextToken();

			if (value.indexOf(']') != -1) {
				
				if(!(Character.isDigit(value.charAt(0)))){

				output += value.substring(0, value.indexOf(']')) + " ";
				
				
				}
				 if (st.hasMoreTokens()){
					 
				st.nextToken();
			}
				break;
			} else if (value.indexOf('[') == -1 && !(Character.isDigit(value.charAt(0)))) {

				output += value + " ";
				
			} else if (value.indexOf('[') != -1) {
				
				output += value + arraySaperator(st);

			}

		}
		return output;
	}

}
