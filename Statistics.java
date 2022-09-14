// This program reads a dataset from a txt file, calculates the mean, the median, the first and third quartile, the mode, the varience, the standard deviation and the range of the dataset
// and write these statistics to a HTML file.
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Statistics {

	public static void main(String[] args) throws IOException, FileNotFoundException		// Driver method.
	{
		String inputFileName = args[0];					// Get input file name from a command line argument.
		String outputFileName = args[1];				// Get output file name from a command line argument.
		
		
		System.out.println("Input File: " + inputFileName);			// Display input file name on console.
		System.out.println("Output File: " + outputFileName);		// Display output file name on console.
		
		File inputFile = new File(inputFileName);					// Create a File object.
		Scanner input = new Scanner(inputFile);						// Create a Scanner object.
		PrintWriter output = new PrintWriter(outputFileName);		// Create a PrintWriter object.
		
		int inputArray[] = new int[dataCount(inputFileName)];		// Create a dynamically sized array (depending on the integer count in the data set).
		
		writeToArr(input, inputArray);			// Call the method that writes data from the scanner to the input array.
		
		int ogArray[] = inputArray.clone();		// Make a copy of the unsorted array to use in the final HTML file.
		sortArray(inputArray);					//Call the method that sorts the array.
		
		createHTML(inputArray, ogArray, output);		//Call the method that creates the HTML file.
		
		System.out.println("Statistics Report Created");	// Display success message on console.
		
		input.close();		// Close scanner.		
		output.close();		// Close writer.
	}
	
	public static int dataCount(String file) throws FileNotFoundException, IOException			// Method that counts how many lines in the input file.
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lines = 0;
		while (reader.readLine() != null) 
			lines++;
		reader.close();
		return lines;
	}
	
	public static void writeToArr(Scanner data, int[] arr)			// Method that writes data from the scanner to the input array
	{
		int pos = 0;
		while(data.hasNextInt())
		{
			arr[pos] = data.nextInt();
			pos++;
		}
	}
	
	public static void sortArray(int arr[])				// Method that sorts the input array using bubble sort.
	{
		for (int i = 0; i < arr.length-1; i++)
		{
			for (int j = 0; j < arr.length-1-i; j++)
			{
				if (arr[j] > arr[j+1])
				{
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
	}
	
	public static double calcMean(int arr[])			// Method that calculates the mean value of the data set.
	{
		double total = 0;
		for (int n : arr)
			total = total + n;
		return total/arr.length;
	}
	
	public static double calcMedian(int arr[])			// Method that calculates the median value of the data set.
	{
		int n = arr.length;
		if (n % 2 != 0)
			return arr[(n-1)/2];
		else
			return ((double)arr[(n/2)-1] + (double)arr[n/2])/2;

	}
	
	public static int calcMode(int arr[])			// Method that calculates the mode value of the data set.
	{
		int pos = 0;
		int maxCount = 0;
		
		for (int i = 0; i < arr.length; i++)
		{
			int count = 0;
			for(int j = 0; j < arr.length; j++)
			{
				if (arr[i] == arr[j])
					count++;
			}
			
			if (count > maxCount)
			{
				maxCount = count;
				pos = arr[i];
			}
		}
		return pos;
	}
	
	public static double calcFirstQrt(int arr[])			// Method that calculates the first quartile value of the data set.
	{	
		int n = arr.length;			
		if (n % 2 != 0)
			return arr[(int)Math.floor(n/4)];
		else
			return ((double)arr[(int)Math.floor(n/4)] + (double)arr[(int)Math.ceil(n/4)])/2;
	}
	
	public static double calcThirdQrt(int arr[])			// Method that calculates the third quartile value of the data set.
	{
		int n = arr.length;
		if (n % 2 != 0)
			return arr[(int)Math.floor(n/4)];
		else
			return ((double)arr[(int)Math.floor(3*n/4)] + (double)arr[(int)Math.ceil(3*n/4)])/2;
	}
	
	public static double calcVariance(int arr[])			// Method that calculates the variance value of the data set.
	{
		double m = calcMean(arr);
		double total = 0;
		for (int x : arr)		
			total = total + Math.pow(x-m,2);
		return total/arr.length;
	}
	
	public static double calcStdDev(int arr[])				// Method that calculates the standard deviance value of the data set.
	{		
		return Math.sqrt(calcVariance(arr));
	}
	
	public static int calcRange(int arr[])				// Method that calculates the range of the data set.
	{
		return arr[arr.length-1] - arr[0];
	}
	
	public static void createHTML(int arr[], int ogarr[], PrintWriter output)				// Method that creates the HTML file.
	{
		output.println("<!DOCTYPE html>");
		output.println("<html>");
		output.println("<body  bgcolor=\"#BDBDBD\" >");
		output.println("<h1 style=\"text-align:center;\">CSC-201 Project-4 </h1>");
		output.println("<h2 style=\"text-align:center;\">Enes Kalinsazlioglu</h2>");
		output.println("<br><br>");
		output.println("<h4>Data</h4>");
		output.println("<br>");
		output.println("<table style=\"width:40%; margin-left:2em;\">");
		output.println("<tr>");
		
		int counter = 0;
		for(int num : ogarr)
		{			
			if (counter > 0 && counter % 10 == 0)
				output.println("</tr><tr>");							// HTML tables used to display the data.
			output.println("<td>" + num + "</td>");
			counter++;
		}
		
		output.println("</tr>");
		output.println("</table>");
		output.println("<br>");
		output.println("<h4>Descriptive Statistics</h4>");
		output.println("<br>");
		output.println("<table style=\"width:15%; border: 1px solid black; margin-left:2em;\">");
		output.println("<tr>");
		output.println("<td>Mean:</td>");
		output.printf("<td>%.2f</td>\n",calcMean(arr));
		output.println("</tr>");
		output.println("<tr>");
		output.println("<td>Median:</td>");
		output.println("<td>" + calcMedian(arr) + "</td>");
		output.println("</tr>");
		output.println("<tr>");
		output.println("<td>Mode:</td>");
		output.println("<td>" + calcMode(arr) + "</td>");
		output.println("</tr>");
		output.println("<tr>");
		output.println("<td>First Quartile:</td>");
		output.println("<td>" + calcFirstQrt(arr) + "</td>");
		output.println("</tr>");
		output.println("<tr>");
		output.println("<td>Third Quartile:</td>");
		output.println("<td>" + calcThirdQrt(arr) + "</td>");
		output.println("</tr>");
		output.println("<tr>");
		output.println("<td>Standart Deviation:</td>");
		output.printf("<td>%.2f</td>\n",calcStdDev(arr));
		output.println("</tr>");
		output.println("<tr>");
		output.println("<td>Variance:</td>");
		output.printf("<td>%.2f</td>\n",calcVariance(arr));
		output.println("</tr>");
		output.println("<tr>");
		output.println("<td>Range:</td>");
		output.println("<td>" + calcRange(arr) + "</td>");
		output.println("</tr>");
		output.println("</table>");
		output.println("</body>");
		output.println("</html>");
		
	}		
}
