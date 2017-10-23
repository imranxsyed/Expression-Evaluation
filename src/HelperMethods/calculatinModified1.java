package HelperMethods;

import java.util.Scanner;

public class calculatinModified1 {

	public static Float calc(String input) {

		input = input.replace("--", "+");
		String l = "";
		String r = "";
		char subtitute = '\n';
		String oper = "";
		Float solution = (float) 0;

		int sub = input.indexOf('-');
		int add = input.indexOf('+');

		boolean replace = false;

		if (input.charAt(0) == '-') {

			replace = true;

			subtitute = input.charAt(0);

			input = input.substring(1);

			sub = input.indexOf('-');

			add = input.indexOf('+');
		}
		while (sub != -1 || add != -1) {
			l="";r="";oper="";

			if ((add < sub && add != -1) || (sub == -1 && add > sub)) {

				oper = "" + input.charAt(add);

				int left = add - 1;

				int right = add + 1;

				while (left >= 0 && (Character.isDigit(input.charAt(left)) || input.charAt(left) == '.')) {

					l = input.charAt(left) + l;
					left--;
				}
				while (right < input.length()
						&& (Character.isDigit(input.charAt(right)) || input.charAt(right) == '.')) {

					r += input.charAt(right);
					right++;
				}
				if (replace) {
					l = subtitute + l;
					replace = false;
				}
				solution = Float.parseFloat(l) + Float.parseFloat(r);
				input = input.replace(input.substring(left + 1, right), String.valueOf(solution));
				if (input.charAt(0) == '-') {

					replace = true;

					subtitute = input.charAt(0);

					input = input.substring(1);

					sub = input.indexOf('-');

					add = input.indexOf('+');
				} else {

				}
				sub = input.indexOf('-');
				add = input.indexOf('+');

			} else {

				oper = "" + input.charAt(sub);

				int left = sub - 1;

				int right = sub + 1;

				while (left >= 0 && (Character.isDigit(input.charAt(left)) || input.charAt(left) == '.')) {

					l = input.charAt(left) + l;
					left--;
				}
				while (right < input.length()
						&& (Character.isDigit(input.charAt(right)) || input.charAt(right) == '.')) {

					r += input.charAt(right);
					right++;
				}
				if (replace) {
					l = subtitute + l;
					replace = false;
				}
				solution = Float.parseFloat(l) - Float.parseFloat(r);
				input = input.replace(input.substring(left + 1, right), String.valueOf(solution));
				if (input.charAt(0) == '-') {

					replace = true;

					subtitute = input.charAt(0);

					input = input.substring(1);

					sub = input.indexOf('-');

					add = input.indexOf('+');
				} else {
					sub = input.indexOf('-');
					add = input.indexOf('+');
				}
			}
		}

		return solution;

	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("enter your expression");
		System.out.println(calc(sc.nextLine()));

	}
}
