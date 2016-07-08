package network.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadSpecificFolders {

    /**
     * @returns list of folders as File Object for a given directory location as
     * String.
     */
    public List<File> getInitialNamedFolders(String dirStr) throws Exception {

        List<File> list = new ArrayList<>();
        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path file) throws IOException {
                return (Files.isDirectory(file));
            }
        };

        Path dir = FileSystems.getDefault().getPath(dirStr);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,
                filter)) {
            for (Path path : stream) {
                try {
                    DirectoryStream<Path> sub_stream = Files.newDirectoryStream(path);
                    for (Path sub_path : sub_stream) {

                        if (sub_path.toString().contains("all_documents") || sub_path.toString().contains("discussion_threads")
                                || sub_path.toString().contains("notes_inbox")) {
                            continue;
                        }
//                                                if(sub_path.toString().contains("_sent_mail") || sub_path.toString().contains("sent_items")){
                        list.add(new File(dirStr + System.getProperty("file.separator")
                                + path.getFileName().toString() + System.getProperty("file.separator")
                                + sub_path.getFileName().toString()));
//                                                System.out.println(sub_path.getFileName());
                                                }
//                    }
                } catch (IOException e) {

                }

//		        		list.add(new File( dirStr  + System.getProperty("file.separator") + path.getFileName().toString()));
                //System.out.println("list " + getFiles( dirStr + "//" + path.getFileName().toString()));
                //}
            }
        } catch (IOException e) {
            System.out.println("File Path Not Found");
            System.exit(0);
        }

        return list;
    }

    /**
     * @returns list of files for a given directory
     */
    public List<File> getFiles(String dir)
            throws Exception {
        // System.out.println("ENTERED");
        List<File> list = new ArrayList<>();
        addFile(new File(dir), list);
        // System.out.println("EXIT");
        return list;
    }

    /**
     * helper function for getFiles(...)
     */
    private void addFile(File file, List<File> list) {
        // logger.info("ENTERED");
        //System.out.println("file is? " + file);
        if (file.isDirectory()) {
            //System.out.println("addFile Directory " + file);
            String[] subNodes = file.list();
            for (String subNode : subNodes) {
                //System.err.println(" subnode " + subNode);
                addFile(new File(file, subNode), list);
            }
        } else {
            list.add(file);
        }
        // logger.info("EXIT");
    }

    public static void main(String[] args) throws Exception {
        ReadSpecificFolders r = new ReadSpecificFolders();
        System.out.println(System.getProperty("file.separator"));
        List<File> f = r.getInitialNamedFolders("C:\\Users\\Rachita\\Google Drive\\Algos\\maildir");
//		System.out.println(f + "\n");
//		System.out.println(f.size());
        System.out.println(r.getFiles(f.get(0).toString()));
    }

}
