package oopproject1.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import oopproject1.utilities.Globals;

// Container Class that stores the data extracted from the chosen file
public class RecordsFile {
    private List<String> headers;
    private List<List<String>> dataTable;

    public RecordsFile(List<String> headersMap, List<List<String>> dataTable) {
        this.headers = headersMap;
        this.dataTable = dataTable;
    }

    // GETTERS

    public List<String> getHeaders() {
        return headers;
    }

    public List<List<String>> getDataTable() {
        return dataTable;
    }

    // METHODS

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

    public static boolean checkFileExistance(String fileName) {
        String absoluteFilePath = Globals.baseDir + File.separator + Globals.pathToData + File.separator + fileName;

        File f = new File(absoluteFilePath);

        if (!f.isDirectory()) {
            return f.exists();
        }

        return false;
    }

    public static RecordsFile readFile(String fileName) {
        String absoluteFilePath = Globals.baseDir + File.separator + Globals.pathToData + File.separator + fileName;

        try {
            File dataFile = new File(absoluteFilePath);
            Scanner fileReader = new Scanner(dataFile);

            List<String> headersList = new ArrayList<>();
            String regex = "[,\\;]";
            String[] headers = fileReader.nextLine().split(regex);

            for (String header : headers) {
                headersList.add(header);
            }

            List<List<String>> dataTable = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                String dataLine = fileReader.nextLine();
                if (dataLine.endsWith(",") || dataLine.endsWith(";")) {
                    dataLine += "N/A";
                }
                
                String[] spltDataLine = dataLine.split(regex);
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
            return new RecordsFile(headersList, dataTable);
        } catch (FileNotFoundException e) {
            System.out.println(Globals.errFileNotFound + ": " + absoluteFilePath);
            return null;
        }
    }
}
