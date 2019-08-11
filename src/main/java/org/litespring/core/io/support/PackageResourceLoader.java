package org.litespring.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;
import org.litespring.util.Assert;
import org.litespring.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class PackageResourceLoader {

    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);

    private final ClassLoader classLoader;


    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "Resource must  not be null");
        this.classLoader = classLoader;

    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }


    public Resource[] getResources(String basePackage) throws IOException {
        Assert.notNull(basePackage, "basePackage must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);

        ClassLoader cl = getClassLoader();
        URL url = cl.getResource(location);
        File rootDir = new File(url.getFile());

        Set<File> matchingFiles = retriveMatchingFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];

        int i = 0;
        for (File file :
                matchingFiles) {
            result[i++] = new FileSystemResource(file);
        }
        return result;

    }

    protected Set<File> retriveMatchingFiles(File rootDir) throws IOException {
        if (!rootDir.exists()) {
            // silently skip to non-exsiting directories
            if (logger.isDebugEnabled()) {
                logger.debug("Skiping [" + rootDir.getAbsolutePath() + "] because it does't exist");
            }
            return Collections.emptySet();
        }
        if (!rootDir.isDirectory()) {
            if (logger.isWarnEnabled()) {
                logger.warn("skiping [" + rootDir.getAbsolutePath() + "] because it doesn't a  dirctory");
            }
            return Collections.emptySet();
        }
        if (!rootDir.canRead()) {
            if (logger.isWarnEnabled()) {
                logger.warn("cannot search for matching files  underneath directory [" + rootDir.getAbsolutePath() + "] because our application is not allowed to read this dirctory");
            }
            return Collections.emptySet();
        }

        Set<File> result = new LinkedHashSet<File>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    private void doRetrieveMatchingFiles(File dire, Set<File> result) {
        File[] dirContents = dire.listFiles();
        if (dirContents == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("could not retrive contents of directory[" + dire.getAbsolutePath() + "]");
            }
            return;
        }

        for (File content :
                dirContents) {
            if (content.isDirectory()) {
                if (!content.canRead()) {
                    if (logger.isDebugEnabled()
                    ) {
                        logger.debug("skip subdirctory [" + dire.getAbsolutePath() + "] because the application is not allow to read this dirctory");
                    }
                } else {
                    doRetrieveMatchingFiles(content, result);
                }

            } else {
                result.add(content);
            }
        }



    }
}
