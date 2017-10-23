package HelperMethods;
import java.util.*;

import apps.ArraySymbol;
import apps.ScalarSymbol;


public  class StackToString {
	 ArrayList<ScalarSymbol> scalars= new ArrayList<>();   
	  ArrayList<ArraySymbol> arrays= new ArrayList<>();
	
	
		public  boolean addvalue(String name,int item){//for my purposes
		ScalarSymbol toAdd  = new ScalarSymbol(name);
		toAdd.value= item;
		this.scalars.add(toAdd);
		return true;
		//scalars.contains(o)
		
	}public static String findScalValue(ArrayList<ScalarSymbol> scalars,String name)throws Exception{
		
		for (int i=0 ; i<scalars.size();i++){
			try{
			ScalarSymbol check=	scalars.get(i);
			if (check.name.equals(name)){
				String output =String.valueOf(check.value);
				return  output;
			}
				
			}catch (NoSuchElementException e){
				return "0";
			}
			
		}return "0";
	}
	public  boolean AddScalValue(String name, int x, int index){
		ArraySymbol toAdd;
		
		for (int i=0; i<arrays.size(); i++){
			if (arrays.get(i).name.equals(name)){
				
				arrays.get(i).values[index]= x;
			}
			// toAdd = new ArraySymbol(name);
		}
		 toAdd = new ArraySymbol(name);
		toAdd.values= new int[20];
		toAdd.values[index]= x;
		arrays.add(toAdd);
		return true;
	}
	
	
	public  String findArrValue( ArrayList<ArraySymbol> arrays,String name, int index){
		
		try {
			for (int i=0 ; i<arrays.size(); i++){
				
				ArraySymbol check = arrays.get(i);
				
				if (name.equals(check.name)){
					
					String output =  String.valueOf(check.values[index]);
					return output;
				}
			}
			
			
		}catch (NoSuchElementException e){
			return "0";
		}catch (NullPointerException e){
			return "0";
		}
		return "0";
	}
	

	
	
	
	public  static String stackToString(Stack<String> input){//takes stack of() and [] and return the input inside without () and []
		
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
	
	public static  String paren(String input, int start) throws Exception{// for parenthesis and nestes parenthesis
		
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
	
	public static String arren(String input, String name, int start) throws Exception{
		
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
	   //String sub = name+"["+stackToString(st)+"]";
	//	String sub = output.substring(0, output.length()-1)+"]";
		
		//temporary
		StackToString test= new StackToString();
		test.AddScalValue("arrayA",5, 3);
		test.AddScalValue("arrayA", 12, 8);
		test.AddScalValue("arrayA",1, 9);
		test.AddScalValue("A", 3,2);
		test.AddScalValue("A", 5, 4);
		test.AddScalValue("B", 1, 2);
		//test.AddScalValue("fifth", 5, 4);
		
		//delete afterwards
		String output = test.findArrValue(test.arrays, name, (int)Float.parseFloat(solution));
		
		output = input.replace(sub, output);
			
		
		
		return output;
		

	} 
	public static boolean checkNeg(String input){
		int count=0;
		for (int i=0; i<input.length()-1;i++){
			
			if (input.charAt(i)== '-'){
				 count++;
			}if (count>1){
				return true;
				
			}
		}return false;
	}
	
	public  static String calculation(String input) throws Exception{
		
		
		int mul = input.indexOf("*"); int sub = input.indexOf("-"); int add = input.indexOf("+"); int div = input.indexOf("/");
		String l="";
		
		String r="";
		
		String oper = "";
		
		boolean subexceptional= (mul==-1 && div ==-1 && add==-1);
		
	boolean sub2 = true;
		
		
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
					
					while ((right<= input.length()-1&& Character.isLetter(input.charAt(right)))|| (right<= input.length()-1&&Character.isDigit(input.charAt(right)))|| right<=input.length()-1&& (input.charAt(mul+1)== '-'||input.charAt(right)=='.')){
						
						r+= input.charAt(right);
						right++;
						
						
					}
					String value = solution(l,oper,r);
					String regex = input.substring(left+1, right);
					input =input.replace(regex, value);
					mul =input.indexOf("*");
					div =input.indexOf("/");
					add =input.indexOf("+");
					sub =input.indexOf("-");
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
					
					while (right<= input.length()-1 &&Character.isLetter(input.charAt(right))|| (right<= input.length()-1 &&Character.isDigit(input.charAt(right)))|| (right<=input.length()-1 && (input.charAt(div+1)== '-'||input.charAt(right)=='.'))){
						
						r+= input.charAt(right);
						right++;
						
						
					}
					String value = solution(l,oper,r);
					String regex = input.substring(left+1, right);
					input = input.replace(regex, value);
					div = input.indexOf("/");
					mul =input.indexOf("*");
					sub =input.indexOf("-");
					add =input.indexOf("+");
					l="";
					r="";
					oper= "";
					}
				
					
					
			}
		
			} // the intial statement for multiplt and divide
			
			else if (add != -1 || sub != -1){
				
				while (!(add==-1 && sub ==-1)){
					
					if ((add<sub && add!=-1) || (add>sub && sub==-1)){
						
						
						 int left = add-1;		
							int right = add+1;
							while (left>=0 &&(Character.isLetter(input.charAt(left)))|| (left >=0 &&(Character.isDigit(input.charAt(left))))|| (left>=0 && input.charAt(left)=='.')){
								
								l = input.charAt(left) + l;
								left--;
								
							}
							oper += input.charAt(add);
							
							while (right<= input.length()-1 &&Character.isLetter(input.charAt(right))|| (right<= input.length()-1 &&Character.isDigit(input.charAt(right)))|| (right<=input.length()-1 && ( input.charAt(add+1)== '-'||input.charAt(right)=='.'))){
								
								r+= input.charAt(right);
								right++;
								
								
							}
							String value = solution(l,oper,r);
							String regex = input.substring(left+1, right);
							input =input.replace(regex, value);
							add= input.indexOf("+");
							mul =input.indexOf("*");
							sub =input.indexOf("-");
							div =input.indexOf("/");
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
								
								
							} if (l.isEmpty()){
								
								l = oper+r;
								char temp = input.charAt(0);
								input= input.substring(1);
								r="";
								oper="";
								oper += input.charAt(right-1);
								r+= input.charAt(right);
								right++;
								while ((right<=input.length()-1)&&Character.isLetter(input.charAt(right))||(right<=input.length()-1)&& Character.isDigit(input.charAt(right))|| (right<=input.length()-1 && input.charAt(right)=='.')){
									
									r+= input.charAt(right);
									right++;
								}input = temp + input;
								right++;
							}
							//calc = value;
							
							String value = solution(l,oper,r);
							String regex = input.substring(left+1, right);
							input =input.replace(regex, value);
							sub = input.indexOf('-');
							mul =input.indexOf("*");
							add =input.indexOf("+");
							sub =input.indexOf("-");
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
		}
		return input;
	}  
	public static String solution(String left,String oper, String right) throws Exception{
		//temporary 
		StackToString test= new StackToString();
		test.addvalue("a", 3);
		test.addvalue("b",2);
		test.addvalue("d", 56);
		test.addvalue("vary", 5);
		test.addvalue("varx", 6);
	//	test.addvalue("apa", 5); //delete it afterwards
		
		float solve =0;
		if (oper.isEmpty()&& right.isEmpty()&& !(left.isEmpty())){
			
		return	left = findScalValue(test.scalars, left);
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
				
				left= left.replace(let,findScalValue(test.scalars,let));
				
			}
			if (Character.isLetter(ret.charAt(0))){
				
				right= right.replace(ret,findScalValue(test.scalars,ret));
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
	
	
	
	public  static void main(String[] args)throws Exception{
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("please enter the expression");
		String input = sc.nextLine();
		
		//Stack<String> st= new Stack();
		
	//	StringTokenizer st= new StringTokenizer(input);
		input= input.replace(" ","");
		
		//st.push("(");
		
		String output="";
		
		/*for (int i=0; i<input.length();i++){
			String toPush="";
			int track = i;
			while ((i<=input.length()-1)&&  (input.charAt(i)!= '(' && input.charAt(i)!= '[')  ){
				toPush += input.charAt(i);
				i++;
			}
			st.push(toPush);
			if (i<input.length()-1 ){
				if (input.charAt(i)== '('){
					
					input = paren(input,i);
					i--;
				}else{
					
					input = arren(input, st.pop(), i);
					i=track-1;
				}
				
			}
			
		} 
		st.push(")"); */
		
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
		output = calculation(input);
		
		
	
		//String output =arren(input,"second" ,input.indexOf('['));
		System.out.println( String.valueOf(output));
	}
	

}
