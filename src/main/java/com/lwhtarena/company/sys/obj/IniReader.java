package com.lwhtarena.company.sys.obj;

import com.lwhtarena.company.sys.util.StringUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:10 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class IniReader {

    protected static HashMap<String, Properties> sections;
    private transient String currentSecion;
    private transient Properties current;
    private String iniFileName;

    public IniReader(String filename)
            throws IOException {
        InputStreamReader isr = null;
        FileInputStream fis = new FileInputStream(filename);
        try {
            isr = new InputStreamReader(fis, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (isr != null) {
            sections = null;
            sections = new HashMap();
            BufferedReader reader = new BufferedReader(isr);

            this.iniFileName = filename;
            try {
                read(reader);
                reader.close();
                isr.close();
                reader = null;
                isr = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fis != null) {
            fis.close();
            fis = null;
        }
    }

    protected void read(BufferedReader reader)
            throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
        reader.close();
        reader = null;
    }

    protected void parseLine(String line) {
        line = line.trim();
        if (line.matches("\\[.*\\]")) {
            this.currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
            this.current = new Properties();
            if ((sections != null) && (this.currentSecion != null) && (this.current != null)) {
                try {
                    sections.put(this.currentSecion, this.current);
                } catch (Exception e) {
                    if (sections == null) {
                        System.out.println("Lerx Err Log:sections is null! sections.toString():" + sections.toString());
                    }
                    if (this.currentSecion == null) {
                        System.out.println("Lerx Err Log:currentSecion is null! currentSecion.toString():" + this.currentSecion.toString());
                    }
                    if (this.current == null) {
                        System.out.println("Lerx Err Log:current is null! current.toString():" + this.current.toString());
                    }
                    e.printStackTrace();
                }
            }
        } else if ((line.matches(".*=.*")) &&
                (this.current != null)) {
            int i = line.indexOf('=');
            String name = line.substring(0, i);
            String value = line.substring(i + 1);
            this.current.setProperty(name, value);
        }
    }

    public String getValue(String section, String name) {
        this.currentSecion = section;
        if (sections == null) {
            System.out.println("Lerx Err Log:sections is null");
            return null;
        }
        Properties p = (Properties) sections.get(section);
        if (p == null) {
            System.out.println("Lerx Err Log:properties is null");
            System.out.println("Lerx Err Log:----section----���" + section);
            return null;
        }
        String value = p.getProperty(name);
        if ((value == null) || (value.trim().equals(""))) {
            value = StringUtil.propertiesValue(p, name);
        }
        if ((value == null) || (value.trim().equalsIgnoreCase(name.trim()))) {
            return null;
        }
        return value;
    }

    public boolean setValue(String section, String variable, String value)
            throws IOException {
        FileReader fr = new FileReader(this.iniFileName);

        BufferedReader bufferedReader = new BufferedReader(fr);
        boolean isInSection = false;
        String fileContent = "";
        try {
            String allLine;
            while ((allLine = bufferedReader.readLine()) != null) {
                allLine = allLine.trim();
                String remarkStr;
                if (allLine.split("[;]").length > 1) {
                    remarkStr = ";" + allLine.split(";")[1];
                } else {
                    remarkStr = "";
                }
                String strLine = allLine.split(";")[0];

                Pattern p = Pattern.compile("\\[\\s*.*\\s*\\]");
                Matcher m = p.matcher(strLine);
                if (m.matches()) {
                    p = Pattern.compile("\\[\\s*" + section + "\\s*\\]");
                    m = p.matcher(strLine);
                    if (m.matches()) {
                        isInSection = true;
                    } else {
                        isInSection = false;
                    }
                }
                if (isInSection) {
                    strLine = strLine.trim();
                    String[] strArray = strLine.split("=");
                    String getValue = strArray[0].trim();
                    if (getValue.equalsIgnoreCase(variable)) {
                        String newLine = getValue + " = " + value + " " + remarkStr;
                        fileContent = fileContent + newLine + "\r\n";
                        while ((allLine = bufferedReader.readLine()) != null) {
                            fileContent = fileContent + allLine + "\r\n";
                        }
                        bufferedReader.close();
                        FileWriter fw = new FileWriter(this.iniFileName, false);
                        BufferedWriter bufferedWriter = new BufferedWriter(fw);
                        bufferedWriter.write(fileContent);
                        bufferedWriter.flush();
                        fw.close();
                        bufferedWriter.close();

                        return true;
                    }
                }
                fileContent = fileContent + allLine + "\r\n";
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            bufferedReader.close();
            fr.close();
        }
        String allLine;
        bufferedReader.close();
        fr.close();

        return false;
    }
}
