import java.io.*;
import java.util.Scanner;

public class FocusCompanion {
    public FocusCompanion(){
        text = "";
    }

    private String text;
    private double gradeForA;
    private double gradeForB;
    private double gradeForC;
    private double gradeForD;

    public String getGrades() throws IOException {
        try{
            Scanner config = new Scanner(new File("config.txt"));
            while(config.hasNextLine())
            {
                String line = config.nextLine();
                if(line.contains("gradeForA=")){
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
        } catch (Exception e)
        {
            gradeForA = 89.5;
            gradeForB = 79.5;
            gradeForC = 73.5;
            gradeForD = 69.5;
        }

        double current = 0;
        double finalPercentage = 0;


        double categoryPercent = 0;
        double categorySum = 0;
        double categoryN = 0;

        while(text.contains("\n"))
        {
            String line = "" + text.substring(0, text.indexOf('\n'));
            text = text.substring(text.indexOf('\n') + 1);

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
                }

                else if(line.contains("Average - ")){
                    double categoryAvg = (categorySum / categoryN);
                    current = current + (categoryAvg * categoryPercent);

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
                }

                else if(line.equals("None yet available.")){
                    finalPercentage = categoryPercent;
                }
            }
        }

        current = current / (1 - finalPercentage);
        String result = "Current Grade: " + current + "%";
        result += "\n";
        result += "\n" + "Grade needed for an A: " + (((gradeForA - current) / finalPercentage) + current) + "%";
        result += "\n" + "Grade needed for a B: " + (((gradeForB - current) / finalPercentage) + current) + "%";
        result += "\n" + "Grade needed for a C: " + (((gradeForC - current) / finalPercentage) + current) + "%";
        result += "\n" + "Grade needed for a D: " + (((gradeForD - current) / finalPercentage) + current) + "%";

        return result;
    }

    public void setText(String targetText){
        text = targetText;
    }
}