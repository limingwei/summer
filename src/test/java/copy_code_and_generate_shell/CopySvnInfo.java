package copy_code_and_generate_shell;

import java.io.File;
import java.io.FileWriter;

import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;

import cn.limw.summer.svnkit.SvnUtil;
import cn.limw.summer.time.Clock;
import cn.limw.summer.util.Files;

/**
 * @author li
 * @version 1 (2015年5月29日 下午2:15:10)
 * @since Java7
 */
public class CopySvnInfo {
    private static final String svnRoot = "http://svn-server:8082/svn/code/helpdesk";

    private static final String username = "lmw";

    private static final String password = "123456";

    private static String getLatestRevision(String pro) {
        try {
            SVNRepository repository = SvnUtil.createRepository(svnRoot, username, password);
            SVNClientManager clientManager = SvnUtil.newClientManager(repository);

            SVNStatus doStatus = clientManager.getStatusClient().doStatus(new File("D:/workspaces/*/" + pro), true);

            SVNRevision committedRevision = doStatus.getCommittedRevision();
            return "{" //
                    + "\"Repository\":\"" + "dev" + "\""//
                    + ",\"Project\":\"" + pro + "\"" //
                    + ",\"Version\":\"" + doStatus.getRevision().getNumber() + "\"" //
                    + ",\"ChangedVersion\":\"" + committedRevision.getNumber() + "\""// 
                    + ",\"Author\":\"" + doStatus.getAuthor() + "\""// 
                    + ",\"Time\":\"" + Clock.when(doStatus.getCommittedDate()).toYYYY_MM_DD_HH_MM_SS() + "\""// 
                    + "}";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void write_svn_revision(String fileToPath, String pro) {
        try {
            File file = new File(fileToPath + "/*-support/about/svn_revision/" + pro /* + "-" + Clock.now().toString("yyyyMMddHHmmss") */+ ".txt");
            if (file.exists()) {
                file.delete();
            }
            Files.createNewFile(file);
            FileWriter fileWriter = new FileWriter(file, true);
            String string = getLatestRevision(pro) + "\n";
            fileWriter.write(string);
            Files.close(fileWriter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}