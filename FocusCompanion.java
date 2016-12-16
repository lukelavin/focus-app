/*
Unorganized code written by Luke Lavin.
If you like it, let me know :)

Released: 12/15/2016
Last Updated: 12/15/2016
 */


import java.io.*;
import java.util.Scanner;

public class FocusCompanion {
    public FocusCompanion(){

    }

    public static String getGrades() throws IOException {
        Scanner config = new Scanner(new File("config.txt"));
        String readFrom = "grades.txt";
        double gradeForA = 89.5;
        double gradeForB = 79.5;
        double gradeForC = 73.5;
        double gradeForD = 69.5;

        while(config.hasNextLine())
        {
            String line = config.nextLine();
            if(line.contains("readFrom=")){
                readFrom = line.substring(9);
            }
            else if(line.contains("gradeForA=")){
                gradeForA = Double.parseDouble(line.substring(10));
            }
            else if(line.contains("gradeForB=")){
                gradeForB = Double.parseDouble(line.substring(10));
            }
            else if(line.contains("gradeForC=")){
                gradeForC = Double.parseDouble(line.substring(10));
            }
            else if(line.contains("gradeForD=")){
                gradeForD = Double.parseDouble(line.substring(10));
            }
        }

        double current = 0;
        double finalPercentage = 0;
        Scanner scanner = new Scanner(new File(readFrom));


        double categoryPercent = 0;
        double categorySum = 0;
        double categoryN = 0;

        while(scanner.hasNextLine()){
            String line = "" + scanner.nextLine();

            if(!line.isEmpty()){
                //if the line is an assignment line, parse the numbers out of the line, and use them in the proper column
                if(Character.isDigit(line.charAt(0)) && (line.charAt(1) =='/' || line.charAt(2) == '/')) {
                    String[] tabs = line.split("\t");

                    int numCount = 0;
                    double grade = 0;
                    double max = 0;
                    double weight = 0;
                    //go through each token
                    for (int i = 0; i < tabs.length; i++) {
                        //see if it's only numbers
                        boolean isNumber = true;
                        for (int j = 0; j < tabs[i].length(); j++) {
                            if (!Character.isDigit(tabs[i].charAt(j)))
                                isNumber = false;
                        }

                        //if it is, assign it to the proper grade, max, or weight column
                        if (isNumber) {
                            if (numCount == 0)
                                grade = Double.parseDouble(tabs[i]);
                            else if (numCount == 1)
                                max = Double.parseDouble(tabs[i]);
                            else if (numCount == 2)
                                weight = Double.parseDouble(tabs[i]);

                            numCount++;
                        }
                    }
                    categorySum += ((grade / max * 100) * weight);
                    categoryN += weight;

                    //System.out.println(line + " | grade: " + grade + ", max: " + max + ", weight: " + weight);
                }

                else if(line.contains("Average - ")){
                    double categoryAvg = (categorySum / categoryN);
                    current = current + (categoryAvg * categoryPercent);

                    //System.out.println(line + " | average, categorySum: "  + categorySum + ", categoryAvg: " + categoryAvg);
                    categorySum = 0;
                    categoryN = 0;
                }

                //check if it's a header. if it is, calculate the previous section total, and assign the new correct percentage
                else if(line.contains("% of Overall Grade")){

                    int endIndex = line.indexOf("% of Overall Grade");
                    int startIndex = endIndex - 2;

                    if(!Character.isDigit(line.charAt(startIndex)))
                        startIndex += 1;

                    categoryPercent = Double.parseDouble(line.substring(startIndex, endIndex)) * .01;

                    //System.out.println(line + " | contains %, categoryPercent: " + categoryPercent);
                }

                else if(line.equals("None yet available.")){
                    finalPercentage = categoryPercent;

                    //System.out.println(line + " | none, finalPercentage: " + finalPercentage);
                }

                else {
                    //System.out.println(line + " useless");
                }
            }
            else {
                //System.out.print("!!!" + line);
                //System.out.println(" " + line.isEmpty());
            }
        }
        current = current / (1 - finalPercentage);
        String result = "Current Grade: " + current + "%";
        result += "\n--------------------------------------";
        result += "\n" + "Grade needed for an A: " + (((gradeForA - current) / finalPercentage) + current) + "%";
        result += "\n" + "Grade needed for a B: " + (((gradeForB - current) / finalPercentage) + current) + "%";
        result += "\n" + "Grade needed for a C: " + (((gradeForC - current) / finalPercentage) + current) + "%";
        result += "\n" + "Grade needed for a D: " + (((gradeForD - current) / finalPercentage) + current) + "%";
//
//        System.out.println("Current Grade: " + current + "%");
//        System.out.println();
//        System.out.println("Grade needed for an A: " + (((gradeForA - current) / finalPercentage) + current) + "%");
//        System.out.println("Grade needed for a B: " + (((gradeForB - current) / finalPercentage) + current) + "%");
//        System.out.println("Grade needed for a C: " + (((gradeForC - current) / finalPercentage) + current) + "%");
//        System.out.println("Grade needed for a D: " + (((gradeForD - current) / finalPercentage) + current) + "%");

        return result;
    }
}
