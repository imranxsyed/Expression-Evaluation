package apps;

import java.io.*;

import java.util.*;


import structures.Stack;

public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;                
    
	/**
	 * Scalar symbols in the expression 
	 */
	ArrayList<ScalarSymbol> scalars;   
	
	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;
    
    /**
     * String containing all delimiters (characters other than variables and constants), 
     * to be used with StringTokenizer
     */
    public static final String delims = " \t*+-/()[]";
    
    /**
     * Initializes this Expression object with an input expression. Sets all other
     * fields to null.
     * 
     * @param expr Expression
     */
    public Expression(String expr) {
        this.expr = expr;
    }
    
	private StringTokenizer ExpressionSaperator(String expression) {//Helper Method that saperates the expression
		
		 
		StringTokenizer st = new StringTokenizer(expression, " *+-/()" );
		String output = "";
		
		while (st.hasMoreElements()) {

			String value = st.nextToken();

			int openArr = value.indexOf('[');
			
			int closeArr = value.indexOf(']');
			
			if (openArr == -1 && closeArr ==-1) {
				
				output += value + " ";
			}
			else if(closeArr != -1 && openArr==-1){
				
				output += value.length()>1? value.substring(0,closeArr)+" " : "";
			}
			else	{ 
				
				output += value.length()== value.substring(0,openArr).length()+1?  value.substring(0, openArr+1)+"] ":
					
					value.substring(0, openArr+1) + arraySaperator(value.substring(openArr+1));
			
				
			}
			

			
		}
		st = new StringTokenizer(output," ");
		return st;
	}
	
private String arraySaperator(String input){// method that saperates arrays brakcets// not for personal use. ExpressionSaperator;
		
		int closeBrac= input.indexOf(']');
		
		int openBrac = input.indexOf('[');
		
		String output = "] "; 
		if (closeBrac!=-1 && openBrac == -1 ){
			
			output +=input.substring(0,closeBrac); //if the string contains a close bracket only. (not open bracket), everyting from zero index to one index before the close bracket copies into ouput
			
			return output+" ";
		} 
		
		else if(openBrac != -1){// if the string contains an open bracket
			
			int excessBrac=input.substring(0, openBrac).length()+1;	 //length of substring from fist letter to open bracket
			
	output += input.length()>excessBrac? input.substring(0,openBrac+1)+ arraySaperator(input.substring(openBrac+1)): 
		input.substring(0)+"] ";
			// if the String has excess character than substring , it calls the method again, else copies subsString into output and adds space
			
			return output;
			
		}else{
			
			output += input+" "; //if substring contains only a letter or digit.
		}
		
		
		
		return output;
	}

    /**
     * Populates the scalars and arrays lists with symbols for scalar and array
     * variables in the expression. For every variable, a SINGLE symbol is created and stored,
     * even if it appears more than once in the expression.
     * At this time, values for all variables are set to
     * zero - they will be loaded from a file in the loadSymbolValues method.
     */
    public void buildSymbols() {
    	
    	String expression = expr;
    	
    	scalars = new ArrayList<ScalarSymbol>();
    	
    	arrays= new ArrayList<ArraySymbol>();
    	
    	StringTokenizer st= ExpressionSaperator(expression);
    	
    	while(st.hasMoreTokens()){
    		
    		String value = st.nextToken();
    		
    		int containsArr = (value.indexOf('['));
    		
    		boolean containsDigit = Character.isDigit(value.charAt(0));
    		
    		if(containsArr<0 && !(containsDigit)){ // making sure it does not already exist
    			
    			
    			ScalarSymbol create = new ScalarSymbol(value);
    			
    			if (!(scalars.contains(create))){
    			
    			scalars.add(create);
    			
    			}
    		}
    		else if (!(containsDigit)){
    			
    			ArraySymbol create = new ArraySymbol(value.substring(0,containsArr));
    			
    			if (!(arrays.contains(create))){ // making sure it does not already exist
    			
    			arrays.add(create);
    			}
    		}
    	}
    		/** COMPLETE THIS METHOD **/
    }
    

    
    /**
     * Loads values for symbols in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     */
    public void loadSymbolValues(Scanner sc) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String sym = st.nextToken();
            ScalarSymbol ssymbol = new ScalarSymbol(sym);
            ArraySymbol asymbol = new ArraySymbol(sym);
            int ssi = scalars.indexOf(ssymbol);
            int asi = arrays.indexOf(asymbol);
            if (ssi == -1 && asi == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                scalars.get(ssi).value = num;
            } else { // array symbol
            	asymbol = arrays.get(asi);
            	asymbol.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    asymbol.values[index] = val;              
                }
            }
        }
    }
    
    
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array 
     * subscript expressions.
     * 
     * @return Result of evaluation
     */
    public float evaluate() {
    	
    	String input = expr;
    	
    	input= input.replace(" ","");
    	
    	int bracket = input.indexOf('(');
    	
		int arrBrac =input.indexOf('[');
		
			while (bracket >= 0 || arrBrac >=0){
				
				if ((bracket< arrBrac && bracket>=0) ||(bracket>arrBrac && arrBrac<0) ){
					
					input = paren(input,bracket);
					
					bracket = input.indexOf('(');
					
					arrBrac =input.indexOf('[');
					
				}else{
					
					String name = "";
					
					int track = arrBrac -1;
					
					while (track >=0 &&Character.isLetter(input.charAt(track))){
						
						name = input.charAt(track)+ name;
						
						track--;
					}
					
					input = arren(input,name, arrBrac);
					
					bracket = input.indexOf('(');
					
					 arrBrac =input.indexOf('[');
				}
			}
			
		float output = Float.parseFloat(calculation(input));
		
    		/** COMPLETE THIS METHOD **/
    		// following line just a placeholder for compilation
    		return output;
    }

    /**
     * Utility method, prints the symbols in the scalars list
     */
    public  void printScalars() {
        for (ScalarSymbol ss: scalars) {
            System.out.println(ss);
        }
    }
    
    /**
     * Utility method, prints the symbols in the arrays list
     */
    public void printArrays() {
    		for (ArraySymbol as: arrays) {
    			System.out.println(as);
    		}
    }
    /** it takes two digits or words from left and right parameters, and and operator from ope.. if left or right are scalars, 
     * it gets scalar value from "findScalValue" method.
     * 
     * @param left
     * @param oper
     * @param right
     * @return
     */
    private String solution(String left,String oper, String right){
		
		float solve =0;
		
		if (oper.isEmpty()&& right.isEmpty()&& !(left.isEmpty())){
			
		return	left = findScalValue(left);
		}
		
		StringTokenizer letter= new StringTokenizer(left,"-");
		
		StringTokenizer letter2= new StringTokenizer(right,"-");
		String let ="";
		String ret ="";
		while(letter.hasMoreElements()|| letter2.hasMoreTokens()){
			if (letter.hasMoreTokens()){
		let = letter.nextToken();}
			if(letter2.hasMoreElements()){
		ret = letter2.nextToken();}
		}
		
		
		if (!(let.isEmpty())&&Character.isLetter(let.charAt(0))|| !(ret.isEmpty())&&Character.isLetter(ret.charAt(0))){
			
			if (Character.isLetter(let.charAt(0))){
				
				left= left.replace(let,findScalValue(let));
				
			}
			if (Character.isLetter(ret.charAt(0))){
				
				right= right.replace(ret,findScalValue(ret));
			}
			
		}
			switch (oper){
				case "*": solve = Float.parseFloat(left) * Float.parseFloat(right); break;
				case "/" : solve = Float.parseFloat(left) / Float.parseFloat(right); break;
				case "-" : if (left.equals("-")){ solve = Float.parseFloat(right); break;}
					solve = Float.parseFloat(left) -Float.parseFloat(right); break;
				case "+" : solve = Float.parseFloat(left) + Float.parseFloat(right); break;
			}
			
		 String output = String.valueOf(solve);
		 
		return output;
				
	}
    
    
    /**
     * it takes a simple with no () and [] , and sends pieces of substring to "Solution" method accordoing to the precedence of the operator,
     * ex: a+b*c/d or 1+2*3/5 for each of the example it will send c/d=i and 3/5=k from expressions, and then 2*k and b*i and so on..
     * k and i are the actual solution from 3/5 and c/d returned from "Solution" method
     * 
     * @param input
     * @return
     */
    
private String calculation(String input){
		
		
		
		String oper = ""; boolean replace=false;
		input = input.replace("--", "+"); char subtitute = '\n';
		
		
		int sub,add,div,mul;
	boolean sub2 = true;
	
	if (input.charAt(0) == '-') {

		replace = true;

		subtitute = input.charAt(0);

		input = input.substring(1);

		sub = input.indexOf('-');

		add = input.indexOf('+');
		mul = input.indexOf("*");
		div = input.indexOf("/");
	}else{
	 mul = input.indexOf("*");  sub = input.indexOf("-");  add = input.indexOf("+");  div = input.indexOf("/");}
	String l="";
	
	String r="";
	boolean subexceptional= (mul==-1 && div ==-1 && add==-1);
		
		
		while(!(add==-1 && mul ==-1 &&  div==-1 && sub==-1)) {
			if((subexceptional&&  !(sub2) && sub<=0)){
				
				break;
			}
			
			if(mul!=-1 || div!=-1){
				
				
				while (!(mul ==-1 && div ==-1)){
			
			
				
				//if ((mul<div && mul!=-1) || (mul>div && div==-1)){
					
					if( (mul<div && mul!=-1) || (mul>div && div==-1)){
							
							 int left = mul-1;		
					int right = mul+1;
					while (( left>=0&&Character.isLetter(input.charAt(left)))|| (left>=0&&Character.isDigit(input.charAt(left)))|| left>=0 && input.charAt(left)=='.'){
						
						l= input.charAt(left) + l;
						left--;
						
					}
					oper+= input.charAt(mul);
					
					while ((right<= input.length()-1)&&
							(Character.isLetter(input.charAt(right))||Character.isDigit(input.charAt(right))||
									(right==mul+1&&input.charAt(right)== '-')||input.charAt(right)=='.')){
						
						r+= input.charAt(right);
						right++;
						
						
					}if (replace){
						l = "-"+l;
						replace =false;
					}
					String value = solution(l,oper,r);
					String regex = input.substring(left+1, right);
					input =input.replace(regex, value);
					input = input.replace("--", "+");
					
					if (input.charAt(0) == '-') {

						replace = true;

						subtitute = input.charAt(0);

						input = input.substring(1);

						sub = input.indexOf('-');

						add = input.indexOf('+');
						mul =input.indexOf("*");
						div =input.indexOf("/");
					}else{
					add =input.indexOf("+");
					sub =input.indexOf("-");
					mul =input.indexOf("*");
					div =input.indexOf("/");
					}
					l="";
					r="";
					oper= "";
					
					
				
					}
					else
					{
					
					
						int left = div-1;	
						
					int right = div+1;
					
					while ((left >= 0 &&Character.isLetter(input.charAt(left)))|| (left >= 0 &&Character.isDigit(input.charAt(left)))|| (left>=0 && input.charAt(left)=='.')){
						
						l = input.charAt(left) + l;
						left--;
						
					}
					oper += input.charAt(div);
					
					while ((right<= input.length()-1)&&//length
							(Character.isLetter(input.charAt(right))||Character.isDigit(input.charAt(right))||//character or digit
									(right==div+1&&input.charAt(right)== '-')//exceptional case
									||input.charAt(right)=='.')){//dot
						r+= input.charAt(right);
						right++;
						
						
					}if (replace){
						l= "-"+ l;
						replace=false;
					}
					String value = solution(l,oper,r);
					String regex = input.substring(left+1, right);
					input = input.replace(regex, value);
					input = input.replace("--", "+");
					if (input.charAt(0) == '-') {

						replace = true;

						subtitute = input.charAt(0);

						input = input.substring(1);

						sub = input.indexOf('-');

						add = input.indexOf('+');
						mul =input.indexOf("*");
						div =input.indexOf("/");
					}else{
					div = input.indexOf("/");
					mul =input.indexOf("*");
					sub =input.indexOf("-");
					add =input.indexOf("+");
					}
					l="";
					r="";
					oper= "";
					}
				
					
					
			}
		
			} // the intial statement for multiplt and divide
			
			else if (add != -1 || sub != -1){
				input = input.replace("--", "+");
				if (input.charAt(0) == '-') {

					replace = true;

					subtitute = input.charAt(0);

					input = input.substring(1);

					sub = input.indexOf('-');

					add = input.indexOf('+');
				}
				
				while (!(add==-1 && sub ==-1)){
					
					if ((add<sub && add!=-1) || (add>sub && sub==-1)){
						
						
						 int left = add-1;		
							int right = add+1;
							while (left>=0 &&(Character.isLetter(input.charAt(left)))|| (left >=0 &&(Character.isDigit(input.charAt(left))))|| (left>=0 && input.charAt(left)=='.')){
								
								l = input.charAt(left) + l;
								left--;
								
							}
							oper += input.charAt(add);
							
							while ((right<= input.length()-1)&&
									(Character.isLetter(input.charAt(right))||Character.isDigit(input.charAt(right))||
											(right==add+1&&input.charAt(right)== '-')||input.charAt(right)=='.')){	
								r+= input.charAt(right);
								right++;	
							}
							if(replace){
								
								l= '-'+l;
								replace=false;
							}
							String value = solution(l,oper,r);
							String regex = input.substring(left+1, right);
							input =input.replace(regex, value);
							input = input.replace("--", "+");
							
							if (input.charAt(0) == '-') {

								replace = true;

								subtitute = input.charAt(0);

								input = input.substring(1);

								sub = input.indexOf('-');

								add = input.indexOf('+');
								div= input.indexOf("/");
								mul =input.indexOf("*");
							}else{
							sub =input.indexOf("-");
							add =input.indexOf("+");
							div= input.indexOf("/");
							mul =input.indexOf("*");
							}
							r= "";
							l="";
							oper="";
							
							}
							else {
							
								subexceptional= (mul==-1 && div ==-1 && add==-1);
								
							 sub2 = checkNeg(input);
								 
								 if (subexceptional && !(sub2) && sub <=0){
									 break;
								 }
							
								int left = sub-1;		
							int right = sub+1;
							while (left >= 0 && Character.isLetter(input.charAt(left))|| left>=0 && Character.isDigit(input.charAt(left))|| (left>=0 && input.charAt(left)=='.')){
								
								l = input.charAt(left) +l;
								left--;
								
							}
							oper += input.charAt(sub);
							
							while ((right<=input.length()-1)&&Character.isLetter(input.charAt(right))||(right<=input.length()-1)&& Character.isDigit(input.charAt(right))|| (right<=input.length()-1 && input.charAt(right)=='.')){
								
								r+= input.charAt(right);
								right++;
								
								
							} 
							//calc = value;
							if (replace){
								l= '-' + l;
								replace=false;
							}
							
							String value = solution(l,oper,r);
							String regex = input.substring(left+1, right);
							input =input.replace(regex, value);
							input = input.replace("--", "+");
							
							if (input.charAt(0) == '-') {

								replace = true;

								subtitute = input.charAt(0);

								input = input.substring(1);

								sub = input.indexOf('-');

								add = input.indexOf('+');
								div = input.indexOf('/');
								mul =input.indexOf("*");
							}else{
							add =input.indexOf("+");
							sub =input.indexOf("-");
							div = input.indexOf('/');
							mul =input.indexOf("*");
							}
							l= "";
							r= "";
							oper="";
							// sub2 = checkNeg(input);
						
					}
					
					
					
				}
			}
			
			
		}
		if (Character.isLetter(input.charAt(0))){
			
			input = solution(input, "","");
		}if(replace){
			return "-"+input;
		}
		return input;
}		

		private  boolean checkNeg(String input){
	int count=0;
	for (int i=0; i<input.length()-1;i++){
		
		if (input.charAt(i)== '-'){
			 count++;
		}if (count>1){
			return true;
			
		}
	}return false;
	
		}
		
		/**
		 * it takes expression and name of the array and startring index of array bracket and solves it recursively.
		 * ex: a+b*c[somethind].. it will take whole expression with index 5 and name c
		 * @param input
		 * @param name
		 * @param start
		 * @return
		 */
		private  String arren(String input, String name, int start){
			
			String toPush=input.charAt(start)+"";
			
			int index = start+1;
			Stack<String> st = new Stack<>();
			st.push(toPush);
			
			while (input.charAt(index)!= ']'){
				toPush ="";
				int track= index;
				
	while (Character.isLetter(input.charAt(index)) || Character.isDigit(input.charAt(index))|| input.charAt(index)=='.'|| input.charAt(index)=='-'){
					
					toPush += input.charAt(index);
					index++;
				}
				if (!(toPush.isEmpty()) ){
					
					st.push(toPush);
					toPush = "";
				}
				if (input.charAt(index)== '(' || input.charAt(index)== '['){
					
					if (input.charAt(index)== '('){
						
					
						input = paren (input, index);
				
					
					}
					else {
						input = arren(input, st.pop(),index);
						index=track;
						
					}
				}else if (input.charAt(index)!= ']'){
					
					toPush = input.charAt(index)+"";
					st.push(toPush);
					index ++;
				}
			}
			toPush = input.charAt(index)+"";
			
			st.push(toPush);
			
			String solution = stackToString(st);
			String sub = name+"["+solution+"]";;
			solution = calculation(solution);
		   
			
			
			
			String output = findArrValue( name, (int)Float.parseFloat(solution));
			
			output = input.replace(sub, output);
				
			
			
			return output;
			

		}
		/**
		 * same thing as arren but with parenthesis. takes the expression and index of starting parenthesis/
		 * ex: my+name(whatever).. takes the while expression as input and index 7
		 * @param input
		 * @param start
		 * @return
		 */
		
		private   String paren(String input, int start) {// for parenthesis and nestes parenthesis
			
			//int start =0;
			
			String toPush=input.charAt(start)+"";
			
			int index = start+1;
			Stack<String> st = new Stack<>();
			st.push(toPush);
			
			while (input.charAt(index)!= ')'){
				toPush ="";
				int track = index;
				while (Character.isLetter(input.charAt(index)) || Character.isDigit(input.charAt(index))|| input.charAt(index)=='.'){
					
					toPush += input.charAt(index);
					index++;
				}
				if (!(toPush.isEmpty()) ){
					
					st.push(toPush);
					toPush = "";
				}
				if (input.charAt(index)== '(' || input.charAt(index)== '['){
					
					if (input.charAt(index)== '('){
						
					//	toPush = paren(input.substring(index));
						input = paren (input, index);
					//	st.push(toPush);
					//	toPush = "";
					}
					else {
						input = arren(input, st.pop(),index);
						index= track;
						//we have to add method for arrayBrakcet
					}
				}else if (input.charAt(index)!= ')'){
					
					toPush = input.charAt(index)+"";
					st.push(toPush);
					index ++;
				}
			}  
			toPush =input.charAt(index)+"";
			st.push(toPush);
			
			String sub = input.substring(start,index+1);
			
			String output = calculation(stackToString(st));//the output inside () and the solution
			
			
			
			// the substring for which the method solved
			
			output = input.replace(sub, output);// replacing output with method solved
			
			
			return output;
			
		}
		/**
		 * takes stack of() or [] and return the input inside without () and []
		 * stack: {)54321)} and return string inside the stack with paren. returns 12345
		 * stack: {[54321]} and return string inside the stack with array bracket. returns 12345
		 * @param input
		 * @return
		 */

		private String stackToString(Stack<String> input){
			
			String value = input.pop();
			String output ="";
			
			if (value.equals(")")){
				
				
				while (!(value= input.pop()) .equals("(")){
					
					output = value + output;
					
				}
			} else{
				
				while (!(value= input.pop()) .equals("[")){
					
					output = value +output;
					
				}
			}
			
			return output;
		}
		
		
		/**
		 * finds array value 
		 * @param name
		 * @param index
		 * @return
		 */
		private  String findArrValue(String name, int index){
			
			
				for (int i=0 ; i<arrays.size(); i++){
					
					ArraySymbol check = arrays.get(i);
					
					if (name.equals(check.name)){
						
						String output =  String.valueOf(check.values[index]);
						return output;
					}
				}
				
				
			
			return "0";
		}
		
		/**
		 * finds  scalar value of the name
		 * @param name
		 * @return
		 */
		private String findScalValue(String name){
			
			for (int i=0 ; i<scalars.size();i++){
			
				ScalarSymbol check=	scalars.get(i);
				if (check.name.equals(name)){
					String output =String.valueOf(check.value);
					return  output;
				}
					
				
				
			}return "0";
		}	

}
