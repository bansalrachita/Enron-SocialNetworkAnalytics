/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rachita
 */
public class ParseMultiLineMail {

    /**
     * @returns List<String> after a reading a File Object. It has the logic to
     * look for labels and append line to the previous line if no label is
     * found. Captures From, To, Cc, Bcc, Subject, body(250 Characters).
     */
    public List<String> returnContents(File f) throws IOException {

        List<String> content = new ArrayList<>();
        String label = "^[a-zA-Z-]+:.*";
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line = reader.readLine();
        String newLine = "";
        boolean newLineBool = false;
        int max_contents = 250;
        String X_File_Label = "X-FileName:";
        boolean x_file_label = false;
        String body = "";
        int body_count = 10;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.length() == 0 || line.startsWith("#")) {
                continue;
            }

            if (line.startsWith(X_File_Label)) {
                x_file_label = true;
            }

            if (!line.matches(label)) {
                // means its on a non label string
                newLineBool = true;
                newLine += line;
                //System.out.println("appending " + newLine);
            } else {
                //System.out.println("final append " + newLine);
                newLineBool = false;
            }
            if (!newLineBool) {
                if (newLine.length() > 0) {
                    String lastline = content.get(content.size() - 1);
                    // assigns last line to the string
                    content.remove(content.size() - 1);
                    //remove previous line
                    content.add(lastline + newLine.toString());
                    //adds new line to the previous line
                    newLine = "";
                    //makes newline as empty
                }

                content.add(line);
            }
            if (x_file_label && !line.matches(label)) {
                body = body + line.trim().replaceAll("\\t", " ").replaceAll("\\n", " ").replaceAll(" ", " ").replaceAll("--", "");
            } else if (body_count < 0) {
                break;
            }

        }

        if (body != null && body.length() > max_contents) {
            body = body.substring(0, max_contents);
        }
        // appending body to content with Tag
        content.add("Body: " + body);

        reader.close();
        return content;
    }

}
