package ee.ttu.vk.attendance.utils;

import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.domain.Student;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocParser implements IParser<Student> {
    private List<Student> students;
    public DocParser() {
        students = new ArrayList<>();
    }
    @Override
    public void parse(InputStream io){
        try(POIFSFileSystem fileSystem = new POIFSFileSystem(io)) {
            HWPFDocument document = new HWPFDocument(fileSystem);
            WordExtractor extractor = new WordExtractor(document);
            String[] lines = extractor.getText().replaceAll("(?m)^[ \t]*\r?\n", "").split(System.getProperty("line.separator"));
            Pattern pattern = Pattern.compile("Õpperühm:(.+?)Kood");
            String group_name = null;
            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                if(matcher.find())
                    group_name = matcher.group(1).replaceAll(" ", "").replaceAll("-", "").replaceAll("\t", "");
                else if(line.charAt(0) == '\t'){
                    String userLine = line.replaceAll("OK", "").replaceAll("REV", "").replaceAll("TREV", "").replaceAll("\\*", "").trim();
                    String[] userParts = Arrays.stream(userLine.split("\\t")).map(x -> x = x.trim()).toArray(String[]::new);
                    Programme programme = new Programme().setName(group_name);
                    Student student = new Student();
                    student.setCode(userParts[2]);
                    student.setFullname(String.format("%1$s %2$s", userParts[0], userParts[1]));
                    student.setProgramme(programme);
                    students.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getElements() {
        return students;
    }
}
