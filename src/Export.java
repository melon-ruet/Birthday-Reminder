import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;

/**
 * Created by melon on 6/4/14.
 */
public class Export implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser exportData = new JFileChooser();
        FileNameExtensionFilter extensions1 = new FileNameExtensionFilter("*.txt", "txt");
        FileNameExtensionFilter extensions2 = new FileNameExtensionFilter("*.doc", "doc");
        exportData.setFileFilter(extensions1);
        exportData.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
        exportData.setFileFilter(extensions2);
        exportData.setDialogTitle("Select location");
        exportData.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
        exportData.removeChoosableFileFilter(exportData.getAcceptAllFileFilter());
        exportData.setCurrentDirectory(new File(GlobalVariables.prefs.get("EXPORT_PATH", "")));
        int choose = exportData.showSaveDialog(null);
        if(JFileChooser.APPROVE_OPTION == choose)
        {
            GlobalVariables.prefs.put("EXPORT_PATH", exportData.getCurrentDirectory().getPath());
            try{
                FileWriter writer;
                ResultSet result = new BirthdayDatabase().getAllData();

                if(exportData.getFileFilter() == extensions1)
                {
                    writer = new FileWriter(exportData.getSelectedFile().getAbsolutePath()+".txt");
                    PrintWriter printWrite = new PrintWriter(writer);
                    writer.append("Name\t\t\tDate of Birth");
                    printWrite.println();
                    writer.append("--------------------------------------");
                    printWrite.println();

                    while(result.next())
                    {
                        writer.append(result.getString("name"));
                        writer.append(new String(new char[30-result.getString("name").length()]).replace('\0', ' ') );
                        writer.append((result.getInt("day")+1)+" "+ new GlobalVariables().strmon[result.getInt("month")]);
                        printWrite.println();
                    }
                }
                else if(exportData.getFileFilter() == extensions2)
                {
                    writer = new FileWriter(exportData.getSelectedFile().getAbsolutePath()+".doc");
                    writer.append("Name\t\t\t\t\t\tDate of Birth\n-----------------------------------------------\n");

                    while(result.next())
                    {
                        writer.append(result.getString("name"));
                        writer.append(new String(new char[30-result.getString("name").length()]).replace('\0', ' ') );
                        writer.append((result.getInt("day")+1)+" "+ new GlobalVariables().strmon[result.getInt("month")] + "\n");
                    }
                }
                else
                {
                    writer = new FileWriter(exportData.getSelectedFile().getAbsolutePath()+".csv");
                    writer.append("Name,Date of Birth,\n,\n");

                    while(result.next())
                    {
                        writer.append(result.getString("name")+",");
                        writer.append((result.getInt("day")+1)+"  "+ new GlobalVariables().strmon[result.getInt("month")]+"\n");
                    }
                }

                writer.close();
            }
            catch(Exception eee) {}
        }
    }
}
