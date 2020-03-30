package com.company;

/*

1918 Introduction to the tags
Read the name of the file in HTML format from the console.
Example:
Info about Leela <span xml: lang = "en" lang = "en"> <b> <span> Turanga Leela
</span></b></span><span>Super</span> <span> girl </span>
The first parameter to the main method is a tag. For example, "span".
Print to the console all tags that match the specified tag.
Each tag on a new line, the order should correspond to the order in the file.
The number of spaces, n, r does not affect the result.
The file does not contain a CDATA tag, for all opening tags there is a separate closing tag, there are no single tags.
A tag may contain nested tags.
Output Example:
<span xml: lang = "en" lang = "en"> <b> <span> Turanga Leela </span> </b> </span>
<span> Turanga Leela </span>
<span> Super </span>
<span> girl </span>
Tag Template:
<tag> text1 </tag>
<tag text2> text1 </tag>
<tag
text2> text1 </tag>
text1, text2 may be empty

Requirements:
1. The program should read the file name from the console (use BufferedReader).
2. BufferedReader must be closed for reading data from the console.
3. The program should read the contents of the file (use FileReader).
4. The stream of reading from the file (FileReader) should be closed.
5. The program should output to the console all tags that correspond to the tag specified in the parameter of the main method.


*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader conReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = conReader.readLine();
        conReader.close();

        //Read File
        BufferedReader fileBufReader = new BufferedReader(new FileReader(fileName));
        StringBuffer content = new StringBuffer();
        while (fileBufReader.ready())
            content.append(fileBufReader.readLine());
        fileBufReader.close();
        StringBuffer text = new StringBuffer(content.toString().replaceAll("\r\n", ""));

        String tagOpen = "<" + args[0];
        String tagClose = "</" + args[0] + ">";

        int pozitionOpen = -1;
        int pozitionClose = -1;
        int shift = -1;
        Stack<Integer> openedTags = new Stack<>();
        Map<Integer, Integer> tags = new TreeMap<>(new MyComparator());
        while (true) {
            pozitionOpen = text.indexOf(tagOpen, shift);
            pozitionClose = text.indexOf(tagClose, shift);
            if (pozitionOpen < 0 && pozitionClose < 0)
                break;

            if (pozitionOpen != -1 && pozitionOpen < pozitionClose) { //Open ближе чем close
                openedTags.push(pozitionOpen);
                shift = pozitionOpen + tagOpen.length();
                continue;
            }

            if (pozitionClose != -1 && (pozitionOpen > pozitionClose || pozitionOpen == -1)) { //Close ближе чем open
                if (openedTags.isEmpty())
                    break;
                tags.put(openedTags.pop(), pozitionClose + tagClose.length());
                shift = pozitionClose + tagClose.length();
            }
        }

        for (Map.Entry<Integer, Integer> pair : tags.entrySet()) {
            System.out.println(text.substring(pair.getKey(), pair.getValue()));
        }
    }

    static class MyComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer a, Integer b) {
            return a.compareTo(b);
        }
    }
}
