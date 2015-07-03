package cn.limw.summer.svnkit;

import java.io.File;

import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * @author li
 * @version 1 (2015年6月4日 下午3:13:30)
 * @since Java7
 */
public class SvnUtil {
    public static SVNRepository createRepository(String url, String username, String password) {
        try {
            SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
            ISVNAuthenticationManager authManager = createDefaultAuthenticationManager(username, password);
            repository.setAuthenticationManager(authManager);
            return repository;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static ISVNAuthenticationManager createDefaultAuthenticationManager(String username, String password) {
        File configDir = null;
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(configDir, true);
        boolean storeAuth = options.isAuthStorageEnabled();

        final char[] passwordValue = password != null ? password.toCharArray() : null;
        final char[] passphraseValue = null;

        File privateKey = null;

        return SVNWCUtil.createDefaultAuthenticationManager(configDir, username, passwordValue, privateKey, passphraseValue, storeAuth);
    }

    public static SVNClientManager newClientManager(SVNRepository repository) {
        return SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true), repository.getAuthenticationManager());
    }
}