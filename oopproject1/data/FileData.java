package oopproject1.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// Container Class that stores the data extracted from the chosen file
public class FileData {
    private List<String> headers;
    private List<List<String>> dataTable;

    public FileData(List<String> headersMap, List<List<String>> dataTable) {
        this.headers = headersMap;
        this.dataTable = dataTable;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headersMap) {
        this.headers = headersMap;
    }

    public List<List<String>> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<List<String>> dataTable) {
        this.dataTable = dataTable;
    }

    public String toString(int index) {
        String result = "";

        for (int j = 0; j < headers.size(); j++) {
            result += headers.get(j) + ": " + dataTable.get(index).get(j);

            if (j < headers.size() - 1) {
                result += ", ";
            }
        }
        result += "\r\n";

        return result;
    }

    public static FileData readFile(String fileName) {
        String workingDirectory = System.getProperty("user.dir");
        String pathToData = "oopproject1" + File.separator + "data";
        String absoluteFilePath = "";

        absoluteFilePath = workingDirectory + File.separator + pathToData + File.separator + fileName;

        try {
            File dataFile = new File(absoluteFilePath);
            Scanner fileReader = new Scanner(dataFile);

            List<String> headersList = new ArrayList<>();
            String[] headers = fileReader.nextLine().split(",");

            for (String header : headers) {
                headersList.add(header);
            }

            List<List<String>> dataTable = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                String dataLine = fileReader.nextLine();
                if (dataLine.endsWith(",")) {
                    dataLine += "N/A";
                }
                
                String[] spltDataLine = dataLine.split(",");
                List<String> dataList = new ArrayList<>();
                for (String dat : spltDataLine) {
                    if (dat.equals("")) {
                        dat = "N/A";
                    }
                    dataList.add(dat);
                }

                dataTable.add(dataList);
            }

            fileReader.close();
            return new FileData(headersList, dataTable);
        } catch (FileNotFoundException e) {
            System.out.println("File " + absoluteFilePath + " not found");
            return null;
        }
    }
}
