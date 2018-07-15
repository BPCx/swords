package de.meisterfuu.sip.portal;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class sql {

    static String path = "H:\\themen.txt";
    static String path2 = "H:\\themen2.txt";
    static String pathOut = "H:\\themenSQL.txt";

    static String sqlRaw = "('idd', 'nnam', 'comic', 'idd', 'nnam', '2018-07-01 00:00:00'), ";
    static String sqlRaw2 = "INSERT INTO `themen` (`them_id`, `them_name`, `them_bereich`, `id`, `name_de`, `datum_angelegt`) VALUES ('idd', 'nnam', 'comic', 'idd', 'nnam', '2018-07-01 00:00:00')";

    public static void main(String... args) throws IOException {
        IOUtils ioUtils = new IOUtils();

        Path file = Paths.get(path2);
        List<String> strings = IOUtils.readLines(Files.newInputStream(file));

        List<String> collect = strings.stream().map(sql::map).collect(Collectors.toList());
        collect.forEach(System.out::println);

    }

    private static String map(String s) {
        String[] split = s.split("\\|\\|");
        String id = split[0];
        String name = split[1];

        return sqlRaw.replaceAll("idd", id).replaceAll("nnam", name);
    }

}
