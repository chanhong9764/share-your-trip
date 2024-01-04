package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the `libs` extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final ChLibraryAccessors laccForChLibraryAccessors = new ChLibraryAccessors(owner);
    private final ComLibraryAccessors laccForComLibraryAccessors = new ComLibraryAccessors(owner);
    private final CommonsLibraryAccessors laccForCommonsLibraryAccessors = new CommonsLibraryAccessors(owner);
    private final IoLibraryAccessors laccForIoLibraryAccessors = new IoLibraryAccessors(owner);
    private final JavaxLibraryAccessors laccForJavaxLibraryAccessors = new JavaxLibraryAccessors(owner);
    private final JunitLibraryAccessors laccForJunitLibraryAccessors = new JunitLibraryAccessors(owner);
    private final OrgLibraryAccessors laccForOrgLibraryAccessors = new OrgLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Returns the group of libraries at ch
     */
    public ChLibraryAccessors getCh() {
        return laccForChLibraryAccessors;
    }

    /**
     * Returns the group of libraries at com
     */
    public ComLibraryAccessors getCom() {
        return laccForComLibraryAccessors;
    }

    /**
     * Returns the group of libraries at commons
     */
    public CommonsLibraryAccessors getCommons() {
        return laccForCommonsLibraryAccessors;
    }

    /**
     * Returns the group of libraries at io
     */
    public IoLibraryAccessors getIo() {
        return laccForIoLibraryAccessors;
    }

    /**
     * Returns the group of libraries at javax
     */
    public JavaxLibraryAccessors getJavax() {
        return laccForJavaxLibraryAccessors;
    }

    /**
     * Returns the group of libraries at junit
     */
    public JunitLibraryAccessors getJunit() {
        return laccForJunitLibraryAccessors;
    }

    /**
     * Returns the group of libraries at org
     */
    public OrgLibraryAccessors getOrg() {
        return laccForOrgLibraryAccessors;
    }

    /**
     * Returns the group of versions at versions
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Returns the group of bundles at bundles
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Returns the group of plugins at plugins
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class ChLibraryAccessors extends SubDependencyFactory {
        private final ChQosLibraryAccessors laccForChQosLibraryAccessors = new ChQosLibraryAccessors(owner);

        public ChLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at ch.qos
         */
        public ChQosLibraryAccessors getQos() {
            return laccForChQosLibraryAccessors;
        }

    }

    public static class ChQosLibraryAccessors extends SubDependencyFactory {
        private final ChQosLogbackLibraryAccessors laccForChQosLogbackLibraryAccessors = new ChQosLogbackLibraryAccessors(owner);

        public ChQosLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at ch.qos.logback
         */
        public ChQosLogbackLibraryAccessors getLogback() {
            return laccForChQosLogbackLibraryAccessors;
        }

    }

    public static class ChQosLogbackLibraryAccessors extends SubDependencyFactory {
        private final ChQosLogbackLogbackLibraryAccessors laccForChQosLogbackLogbackLibraryAccessors = new ChQosLogbackLogbackLibraryAccessors(owner);

        public ChQosLogbackLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at ch.qos.logback.logback
         */
        public ChQosLogbackLogbackLibraryAccessors getLogback() {
            return laccForChQosLogbackLogbackLibraryAccessors;
        }

    }

    public static class ChQosLogbackLogbackLibraryAccessors extends SubDependencyFactory {

        public ChQosLogbackLogbackLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for classic (ch.qos.logback:logback-classic)
         * with versionRef 'ch.qos.logback.logback.classic'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getClassic() {
                return create("ch.qos.logback.logback.classic");
        }

    }

    public static class ComLibraryAccessors extends SubDependencyFactory {
        private final ComMysqlLibraryAccessors laccForComMysqlLibraryAccessors = new ComMysqlLibraryAccessors(owner);

        public ComLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.mysql
         */
        public ComMysqlLibraryAccessors getMysql() {
            return laccForComMysqlLibraryAccessors;
        }

    }

    public static class ComMysqlLibraryAccessors extends SubDependencyFactory {
        private final ComMysqlMysqlLibraryAccessors laccForComMysqlMysqlLibraryAccessors = new ComMysqlMysqlLibraryAccessors(owner);

        public ComMysqlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.mysql.mysql
         */
        public ComMysqlMysqlLibraryAccessors getMysql() {
            return laccForComMysqlMysqlLibraryAccessors;
        }

    }

    public static class ComMysqlMysqlLibraryAccessors extends SubDependencyFactory {
        private final ComMysqlMysqlConnectorLibraryAccessors laccForComMysqlMysqlConnectorLibraryAccessors = new ComMysqlMysqlConnectorLibraryAccessors(owner);

        public ComMysqlMysqlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at com.mysql.mysql.connector
         */
        public ComMysqlMysqlConnectorLibraryAccessors getConnector() {
            return laccForComMysqlMysqlConnectorLibraryAccessors;
        }

    }

    public static class ComMysqlMysqlConnectorLibraryAccessors extends SubDependencyFactory {

        public ComMysqlMysqlConnectorLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for j (com.mysql:mysql-connector-j)
         * with versionRef 'com.mysql.mysql.connector.j'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJ() {
                return create("com.mysql.mysql.connector.j");
        }

    }

    public static class CommonsLibraryAccessors extends SubDependencyFactory {
        private final CommonsFileuploadLibraryAccessors laccForCommonsFileuploadLibraryAccessors = new CommonsFileuploadLibraryAccessors(owner);

        public CommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at commons.fileupload
         */
        public CommonsFileuploadLibraryAccessors getFileupload() {
            return laccForCommonsFileuploadLibraryAccessors;
        }

    }

    public static class CommonsFileuploadLibraryAccessors extends SubDependencyFactory {
        private final CommonsFileuploadCommonsLibraryAccessors laccForCommonsFileuploadCommonsLibraryAccessors = new CommonsFileuploadCommonsLibraryAccessors(owner);

        public CommonsFileuploadLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at commons.fileupload.commons
         */
        public CommonsFileuploadCommonsLibraryAccessors getCommons() {
            return laccForCommonsFileuploadCommonsLibraryAccessors;
        }

    }

    public static class CommonsFileuploadCommonsLibraryAccessors extends SubDependencyFactory {

        public CommonsFileuploadCommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for fileupload (commons-fileupload:commons-fileupload)
         * with versionRef 'commons.fileupload.commons.fileupload'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getFileupload() {
                return create("commons.fileupload.commons.fileupload");
        }

    }

    public static class IoLibraryAccessors extends SubDependencyFactory {
        private final IoSpringfoxLibraryAccessors laccForIoSpringfoxLibraryAccessors = new IoSpringfoxLibraryAccessors(owner);

        public IoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at io.springfox
         */
        public IoSpringfoxLibraryAccessors getSpringfox() {
            return laccForIoSpringfoxLibraryAccessors;
        }

    }

    public static class IoSpringfoxLibraryAccessors extends SubDependencyFactory {
        private final IoSpringfoxSpringfoxLibraryAccessors laccForIoSpringfoxSpringfoxLibraryAccessors = new IoSpringfoxSpringfoxLibraryAccessors(owner);

        public IoSpringfoxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at io.springfox.springfox
         */
        public IoSpringfoxSpringfoxLibraryAccessors getSpringfox() {
            return laccForIoSpringfoxSpringfoxLibraryAccessors;
        }

    }

    public static class IoSpringfoxSpringfoxLibraryAccessors extends SubDependencyFactory {
        private final IoSpringfoxSpringfoxBootLibraryAccessors laccForIoSpringfoxSpringfoxBootLibraryAccessors = new IoSpringfoxSpringfoxBootLibraryAccessors(owner);
        private final IoSpringfoxSpringfoxSwaggerLibraryAccessors laccForIoSpringfoxSpringfoxSwaggerLibraryAccessors = new IoSpringfoxSpringfoxSwaggerLibraryAccessors(owner);

        public IoSpringfoxSpringfoxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at io.springfox.springfox.boot
         */
        public IoSpringfoxSpringfoxBootLibraryAccessors getBoot() {
            return laccForIoSpringfoxSpringfoxBootLibraryAccessors;
        }

        /**
         * Returns the group of libraries at io.springfox.springfox.swagger
         */
        public IoSpringfoxSpringfoxSwaggerLibraryAccessors getSwagger() {
            return laccForIoSpringfoxSpringfoxSwaggerLibraryAccessors;
        }

    }

    public static class IoSpringfoxSpringfoxBootLibraryAccessors extends SubDependencyFactory {

        public IoSpringfoxSpringfoxBootLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for starter (io.springfox:springfox-boot-starter)
         * with versionRef 'io.springfox.springfox.boot.starter'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getStarter() {
                return create("io.springfox.springfox.boot.starter");
        }

    }

    public static class IoSpringfoxSpringfoxSwaggerLibraryAccessors extends SubDependencyFactory {

        public IoSpringfoxSpringfoxSwaggerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ui (io.springfox:springfox-swagger-ui)
         * with versionRef 'io.springfox.springfox.swagger.ui'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getUi() {
                return create("io.springfox.springfox.swagger.ui");
        }

    }

    public static class JavaxLibraryAccessors extends SubDependencyFactory {
        private final JavaxInjectLibraryAccessors laccForJavaxInjectLibraryAccessors = new JavaxInjectLibraryAccessors(owner);
        private final JavaxServletLibraryAccessors laccForJavaxServletLibraryAccessors = new JavaxServletLibraryAccessors(owner);

        public JavaxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at javax.inject
         */
        public JavaxInjectLibraryAccessors getInject() {
            return laccForJavaxInjectLibraryAccessors;
        }

        /**
         * Returns the group of libraries at javax.servlet
         */
        public JavaxServletLibraryAccessors getServlet() {
            return laccForJavaxServletLibraryAccessors;
        }

    }

    public static class JavaxInjectLibraryAccessors extends SubDependencyFactory {
        private final JavaxInjectJavaxLibraryAccessors laccForJavaxInjectJavaxLibraryAccessors = new JavaxInjectJavaxLibraryAccessors(owner);

        public JavaxInjectLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at javax.inject.javax
         */
        public JavaxInjectJavaxLibraryAccessors getJavax() {
            return laccForJavaxInjectJavaxLibraryAccessors;
        }

    }

    public static class JavaxInjectJavaxLibraryAccessors extends SubDependencyFactory {

        public JavaxInjectJavaxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for inject (javax.inject:javax.inject)
         * with versionRef 'javax.inject.javax.inject'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getInject() {
                return create("javax.inject.javax.inject");
        }

    }

    public static class JavaxServletLibraryAccessors extends SubDependencyFactory {

        public JavaxServletLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for jstl (javax.servlet:jstl)
         * with versionRef 'javax.servlet.jstl'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJstl() {
                return create("javax.servlet.jstl");
        }

    }

    public static class JunitLibraryAccessors extends SubDependencyFactory {

        public JunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for junit (junit:junit)
         * with versionRef 'junit.junit'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJunit() {
                return create("junit.junit");
        }

    }

    public static class OrgLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheLibraryAccessors laccForOrgApacheLibraryAccessors = new OrgApacheLibraryAccessors(owner);
        private final OrgAspectjLibraryAccessors laccForOrgAspectjLibraryAccessors = new OrgAspectjLibraryAccessors(owner);
        private final OrgMybatisLibraryAccessors laccForOrgMybatisLibraryAccessors = new OrgMybatisLibraryAccessors(owner);
        private final OrgProjectlombokLibraryAccessors laccForOrgProjectlombokLibraryAccessors = new OrgProjectlombokLibraryAccessors(owner);
        private final OrgSlf4jLibraryAccessors laccForOrgSlf4jLibraryAccessors = new OrgSlf4jLibraryAccessors(owner);
        private final OrgSpringframeworkLibraryAccessors laccForOrgSpringframeworkLibraryAccessors = new OrgSpringframeworkLibraryAccessors(owner);

        public OrgLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache
         */
        public OrgApacheLibraryAccessors getApache() {
            return laccForOrgApacheLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.aspectj
         */
        public OrgAspectjLibraryAccessors getAspectj() {
            return laccForOrgAspectjLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.mybatis
         */
        public OrgMybatisLibraryAccessors getMybatis() {
            return laccForOrgMybatisLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.projectlombok
         */
        public OrgProjectlombokLibraryAccessors getProjectlombok() {
            return laccForOrgProjectlombokLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.slf4j
         */
        public OrgSlf4jLibraryAccessors getSlf4j() {
            return laccForOrgSlf4jLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.springframework
         */
        public OrgSpringframeworkLibraryAccessors getSpringframework() {
            return laccForOrgSpringframeworkLibraryAccessors;
        }

    }

    public static class OrgApacheLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheCommonsLibraryAccessors laccForOrgApacheCommonsLibraryAccessors = new OrgApacheCommonsLibraryAccessors(owner);
        private final OrgApacheTomcatLibraryAccessors laccForOrgApacheTomcatLibraryAccessors = new OrgApacheTomcatLibraryAccessors(owner);

        public OrgApacheLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache.commons
         */
        public OrgApacheCommonsLibraryAccessors getCommons() {
            return laccForOrgApacheCommonsLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.apache.tomcat
         */
        public OrgApacheTomcatLibraryAccessors getTomcat() {
            return laccForOrgApacheTomcatLibraryAccessors;
        }

    }

    public static class OrgApacheCommonsLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheCommonsCommonsLibraryAccessors laccForOrgApacheCommonsCommonsLibraryAccessors = new OrgApacheCommonsCommonsLibraryAccessors(owner);

        public OrgApacheCommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache.commons.commons
         */
        public OrgApacheCommonsCommonsLibraryAccessors getCommons() {
            return laccForOrgApacheCommonsCommonsLibraryAccessors;
        }

    }

    public static class OrgApacheCommonsCommonsLibraryAccessors extends SubDependencyFactory {

        public OrgApacheCommonsCommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for dbcp2 (org.apache.commons:commons-dbcp2)
         * with versionRef 'org.apache.commons.commons.dbcp2'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getDbcp2() {
                return create("org.apache.commons.commons.dbcp2");
        }

    }

    public static class OrgApacheTomcatLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheTomcatEmbedLibraryAccessors laccForOrgApacheTomcatEmbedLibraryAccessors = new OrgApacheTomcatEmbedLibraryAccessors(owner);

        public OrgApacheTomcatLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache.tomcat.embed
         */
        public OrgApacheTomcatEmbedLibraryAccessors getEmbed() {
            return laccForOrgApacheTomcatEmbedLibraryAccessors;
        }

    }

    public static class OrgApacheTomcatEmbedLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheTomcatEmbedTomcatLibraryAccessors laccForOrgApacheTomcatEmbedTomcatLibraryAccessors = new OrgApacheTomcatEmbedTomcatLibraryAccessors(owner);

        public OrgApacheTomcatEmbedLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache.tomcat.embed.tomcat
         */
        public OrgApacheTomcatEmbedTomcatLibraryAccessors getTomcat() {
            return laccForOrgApacheTomcatEmbedTomcatLibraryAccessors;
        }

    }

    public static class OrgApacheTomcatEmbedTomcatLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheTomcatEmbedTomcatEmbedLibraryAccessors laccForOrgApacheTomcatEmbedTomcatEmbedLibraryAccessors = new OrgApacheTomcatEmbedTomcatEmbedLibraryAccessors(owner);

        public OrgApacheTomcatEmbedTomcatLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.apache.tomcat.embed.tomcat.embed
         */
        public OrgApacheTomcatEmbedTomcatEmbedLibraryAccessors getEmbed() {
            return laccForOrgApacheTomcatEmbedTomcatEmbedLibraryAccessors;
        }

    }

    public static class OrgApacheTomcatEmbedTomcatEmbedLibraryAccessors extends SubDependencyFactory {

        public OrgApacheTomcatEmbedTomcatEmbedLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for jasper (org.apache.tomcat.embed:tomcat-embed-jasper)
         * with versionRef 'org.apache.tomcat.embed.tomcat.embed.jasper'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJasper() {
                return create("org.apache.tomcat.embed.tomcat.embed.jasper");
        }

    }

    public static class OrgAspectjLibraryAccessors extends SubDependencyFactory {

        public OrgAspectjLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for aspectjrt (org.aspectj:aspectjrt)
         * with versionRef 'org.aspectj.aspectjrt'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getAspectjrt() {
                return create("org.aspectj.aspectjrt");
        }

            /**
             * Creates a dependency provider for aspectjweaver (org.aspectj:aspectjweaver)
         * with versionRef 'org.aspectj.aspectjweaver'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getAspectjweaver() {
                return create("org.aspectj.aspectjweaver");
        }

    }

    public static class OrgMybatisLibraryAccessors extends SubDependencyFactory {
        private final OrgMybatisSpringLibraryAccessors laccForOrgMybatisSpringLibraryAccessors = new OrgMybatisSpringLibraryAccessors(owner);

        public OrgMybatisLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.mybatis.spring
         */
        public OrgMybatisSpringLibraryAccessors getSpring() {
            return laccForOrgMybatisSpringLibraryAccessors;
        }

    }

    public static class OrgMybatisSpringLibraryAccessors extends SubDependencyFactory {
        private final OrgMybatisSpringBootLibraryAccessors laccForOrgMybatisSpringBootLibraryAccessors = new OrgMybatisSpringBootLibraryAccessors(owner);

        public OrgMybatisSpringLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.mybatis.spring.boot
         */
        public OrgMybatisSpringBootLibraryAccessors getBoot() {
            return laccForOrgMybatisSpringBootLibraryAccessors;
        }

    }

    public static class OrgMybatisSpringBootLibraryAccessors extends SubDependencyFactory {
        private final OrgMybatisSpringBootMybatisLibraryAccessors laccForOrgMybatisSpringBootMybatisLibraryAccessors = new OrgMybatisSpringBootMybatisLibraryAccessors(owner);

        public OrgMybatisSpringBootLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.mybatis.spring.boot.mybatis
         */
        public OrgMybatisSpringBootMybatisLibraryAccessors getMybatis() {
            return laccForOrgMybatisSpringBootMybatisLibraryAccessors;
        }

    }

    public static class OrgMybatisSpringBootMybatisLibraryAccessors extends SubDependencyFactory {
        private final OrgMybatisSpringBootMybatisSpringLibraryAccessors laccForOrgMybatisSpringBootMybatisSpringLibraryAccessors = new OrgMybatisSpringBootMybatisSpringLibraryAccessors(owner);

        public OrgMybatisSpringBootMybatisLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.mybatis.spring.boot.mybatis.spring
         */
        public OrgMybatisSpringBootMybatisSpringLibraryAccessors getSpring() {
            return laccForOrgMybatisSpringBootMybatisSpringLibraryAccessors;
        }

    }

    public static class OrgMybatisSpringBootMybatisSpringLibraryAccessors extends SubDependencyFactory {
        private final OrgMybatisSpringBootMybatisSpringBootLibraryAccessors laccForOrgMybatisSpringBootMybatisSpringBootLibraryAccessors = new OrgMybatisSpringBootMybatisSpringBootLibraryAccessors(owner);

        public OrgMybatisSpringBootMybatisSpringLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.mybatis.spring.boot.mybatis.spring.boot
         */
        public OrgMybatisSpringBootMybatisSpringBootLibraryAccessors getBoot() {
            return laccForOrgMybatisSpringBootMybatisSpringBootLibraryAccessors;
        }

    }

    public static class OrgMybatisSpringBootMybatisSpringBootLibraryAccessors extends SubDependencyFactory {

        public OrgMybatisSpringBootMybatisSpringBootLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for starter (org.mybatis.spring.boot:mybatis-spring-boot-starter)
         * with versionRef 'org.mybatis.spring.boot.mybatis.spring.boot.starter'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getStarter() {
                return create("org.mybatis.spring.boot.mybatis.spring.boot.starter");
        }

    }

    public static class OrgProjectlombokLibraryAccessors extends SubDependencyFactory {

        public OrgProjectlombokLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for lombok (org.projectlombok:lombok)
         * with versionRef 'org.projectlombok.lombok'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getLombok() {
                return create("org.projectlombok.lombok");
        }

    }

    public static class OrgSlf4jLibraryAccessors extends SubDependencyFactory {
        private final OrgSlf4jSlf4jLibraryAccessors laccForOrgSlf4jSlf4jLibraryAccessors = new OrgSlf4jSlf4jLibraryAccessors(owner);

        public OrgSlf4jLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.slf4j.slf4j
         */
        public OrgSlf4jSlf4jLibraryAccessors getSlf4j() {
            return laccForOrgSlf4jSlf4jLibraryAccessors;
        }

    }

    public static class OrgSlf4jSlf4jLibraryAccessors extends SubDependencyFactory {

        public OrgSlf4jSlf4jLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for api (org.slf4j:slf4j-api)
         * with versionRef 'org.slf4j.slf4j.api'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getApi() {
                return create("org.slf4j.slf4j.api");
        }

    }

    public static class OrgSpringframeworkLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkBootLibraryAccessors laccForOrgSpringframeworkBootLibraryAccessors = new OrgSpringframeworkBootLibraryAccessors(owner);
        private final OrgSpringframeworkSpringLibraryAccessors laccForOrgSpringframeworkSpringLibraryAccessors = new OrgSpringframeworkSpringLibraryAccessors(owner);

        public OrgSpringframeworkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.springframework.boot
         */
        public OrgSpringframeworkBootLibraryAccessors getBoot() {
            return laccForOrgSpringframeworkBootLibraryAccessors;
        }

        /**
         * Returns the group of libraries at org.springframework.spring
         */
        public OrgSpringframeworkSpringLibraryAccessors getSpring() {
            return laccForOrgSpringframeworkSpringLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkBootLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkBootSpringLibraryAccessors laccForOrgSpringframeworkBootSpringLibraryAccessors = new OrgSpringframeworkBootSpringLibraryAccessors(owner);

        public OrgSpringframeworkBootLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.springframework.boot.spring
         */
        public OrgSpringframeworkBootSpringLibraryAccessors getSpring() {
            return laccForOrgSpringframeworkBootSpringLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkBootSpringBootLibraryAccessors laccForOrgSpringframeworkBootSpringBootLibraryAccessors = new OrgSpringframeworkBootSpringBootLibraryAccessors(owner);

        public OrgSpringframeworkBootSpringLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at org.springframework.boot.spring.boot
         */
        public OrgSpringframeworkBootSpringBootLibraryAccessors getBoot() {
            return laccForOrgSpringframeworkBootSpringBootLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkBootSpringBootStarterLibraryAccessors laccForOrgSpringframeworkBootSpringBootStarterLibraryAccessors = new OrgSpringframeworkBootSpringBootStarterLibraryAccessors(owner);

        public OrgSpringframeworkBootSpringBootLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for devtools (org.springframework.boot:spring-boot-devtools)
         * with versionRef 'org.springframework.boot.spring.boot.devtools'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getDevtools() {
                return create("org.springframework.boot.spring.boot.devtools");
        }

        /**
         * Returns the group of libraries at org.springframework.boot.spring.boot.starter
         */
        public OrgSpringframeworkBootSpringBootStarterLibraryAccessors getStarter() {
            return laccForOrgSpringframeworkBootSpringBootStarterLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootStarterLibraryAccessors extends SubDependencyFactory {

        public OrgSpringframeworkBootSpringBootStarterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for mail (org.springframework.boot:spring-boot-starter-mail)
         * with versionRef 'org.springframework.boot.spring.boot.starter.mail'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getMail() {
                return create("org.springframework.boot.spring.boot.starter.mail");
        }

            /**
             * Creates a dependency provider for test (org.springframework.boot:spring-boot-starter-test)
         * with versionRef 'org.springframework.boot.spring.boot.starter.test'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getTest() {
                return create("org.springframework.boot.spring.boot.starter.test");
        }

            /**
             * Creates a dependency provider for web (org.springframework.boot:spring-boot-starter-web)
         * with versionRef 'org.springframework.boot.spring.boot.starter.web'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getWeb() {
                return create("org.springframework.boot.spring.boot.starter.web");
        }

            /**
             * Creates a dependency provider for websocket (org.springframework.boot:spring-boot-starter-websocket)
         * with versionRef 'org.springframework.boot.spring.boot.starter.websocket'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getWebsocket() {
                return create("org.springframework.boot.spring.boot.starter.websocket");
        }

    }

    public static class OrgSpringframeworkSpringLibraryAccessors extends SubDependencyFactory {

        public OrgSpringframeworkSpringLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for tx (org.springframework:spring-tx)
         * with versionRef 'org.springframework.spring.tx'.
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getTx() {
                return create("org.springframework.spring.tx");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final ChVersionAccessors vaccForChVersionAccessors = new ChVersionAccessors(providers, config);
        private final ComVersionAccessors vaccForComVersionAccessors = new ComVersionAccessors(providers, config);
        private final CommonsVersionAccessors vaccForCommonsVersionAccessors = new CommonsVersionAccessors(providers, config);
        private final IoVersionAccessors vaccForIoVersionAccessors = new IoVersionAccessors(providers, config);
        private final JavaxVersionAccessors vaccForJavaxVersionAccessors = new JavaxVersionAccessors(providers, config);
        private final JunitVersionAccessors vaccForJunitVersionAccessors = new JunitVersionAccessors(providers, config);
        private final OrgVersionAccessors vaccForOrgVersionAccessors = new OrgVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.ch
         */
        public ChVersionAccessors getCh() {
            return vaccForChVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.com
         */
        public ComVersionAccessors getCom() {
            return vaccForComVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.commons
         */
        public CommonsVersionAccessors getCommons() {
            return vaccForCommonsVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.io
         */
        public IoVersionAccessors getIo() {
            return vaccForIoVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.javax
         */
        public JavaxVersionAccessors getJavax() {
            return vaccForJavaxVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.junit
         */
        public JunitVersionAccessors getJunit() {
            return vaccForJunitVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org
         */
        public OrgVersionAccessors getOrg() {
            return vaccForOrgVersionAccessors;
        }

    }

    public static class ChVersionAccessors extends VersionFactory  {

        private final ChQosVersionAccessors vaccForChQosVersionAccessors = new ChQosVersionAccessors(providers, config);
        public ChVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.ch.qos
         */
        public ChQosVersionAccessors getQos() {
            return vaccForChQosVersionAccessors;
        }

    }

    public static class ChQosVersionAccessors extends VersionFactory  {

        private final ChQosLogbackVersionAccessors vaccForChQosLogbackVersionAccessors = new ChQosLogbackVersionAccessors(providers, config);
        public ChQosVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.ch.qos.logback
         */
        public ChQosLogbackVersionAccessors getLogback() {
            return vaccForChQosLogbackVersionAccessors;
        }

    }

    public static class ChQosLogbackVersionAccessors extends VersionFactory  {

        private final ChQosLogbackLogbackVersionAccessors vaccForChQosLogbackLogbackVersionAccessors = new ChQosLogbackLogbackVersionAccessors(providers, config);
        public ChQosLogbackVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.ch.qos.logback.logback
         */
        public ChQosLogbackLogbackVersionAccessors getLogback() {
            return vaccForChQosLogbackLogbackVersionAccessors;
        }

    }

    public static class ChQosLogbackLogbackVersionAccessors extends VersionFactory  {

        public ChQosLogbackLogbackVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: ch.qos.logback.logback.classic (1.2.12)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getClassic() { return getVersion("ch.qos.logback.logback.classic"); }

    }

    public static class ComVersionAccessors extends VersionFactory  {

        private final ComMysqlVersionAccessors vaccForComMysqlVersionAccessors = new ComMysqlVersionAccessors(providers, config);
        public ComVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.mysql
         */
        public ComMysqlVersionAccessors getMysql() {
            return vaccForComMysqlVersionAccessors;
        }

    }

    public static class ComMysqlVersionAccessors extends VersionFactory  {

        private final ComMysqlMysqlVersionAccessors vaccForComMysqlMysqlVersionAccessors = new ComMysqlMysqlVersionAccessors(providers, config);
        public ComMysqlVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.mysql.mysql
         */
        public ComMysqlMysqlVersionAccessors getMysql() {
            return vaccForComMysqlMysqlVersionAccessors;
        }

    }

    public static class ComMysqlMysqlVersionAccessors extends VersionFactory  {

        private final ComMysqlMysqlConnectorVersionAccessors vaccForComMysqlMysqlConnectorVersionAccessors = new ComMysqlMysqlConnectorVersionAccessors(providers, config);
        public ComMysqlMysqlVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.com.mysql.mysql.connector
         */
        public ComMysqlMysqlConnectorVersionAccessors getConnector() {
            return vaccForComMysqlMysqlConnectorVersionAccessors;
        }

    }

    public static class ComMysqlMysqlConnectorVersionAccessors extends VersionFactory  {

        public ComMysqlMysqlConnectorVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: com.mysql.mysql.connector.j (8.0.33)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJ() { return getVersion("com.mysql.mysql.connector.j"); }

    }

    public static class CommonsVersionAccessors extends VersionFactory  {

        private final CommonsFileuploadVersionAccessors vaccForCommonsFileuploadVersionAccessors = new CommonsFileuploadVersionAccessors(providers, config);
        public CommonsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.commons.fileupload
         */
        public CommonsFileuploadVersionAccessors getFileupload() {
            return vaccForCommonsFileuploadVersionAccessors;
        }

    }

    public static class CommonsFileuploadVersionAccessors extends VersionFactory  {

        private final CommonsFileuploadCommonsVersionAccessors vaccForCommonsFileuploadCommonsVersionAccessors = new CommonsFileuploadCommonsVersionAccessors(providers, config);
        public CommonsFileuploadVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.commons.fileupload.commons
         */
        public CommonsFileuploadCommonsVersionAccessors getCommons() {
            return vaccForCommonsFileuploadCommonsVersionAccessors;
        }

    }

    public static class CommonsFileuploadCommonsVersionAccessors extends VersionFactory  {

        public CommonsFileuploadCommonsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: commons.fileupload.commons.fileupload (1.3.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getFileupload() { return getVersion("commons.fileupload.commons.fileupload"); }

    }

    public static class IoVersionAccessors extends VersionFactory  {

        private final IoSpringfoxVersionAccessors vaccForIoSpringfoxVersionAccessors = new IoSpringfoxVersionAccessors(providers, config);
        public IoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.io.springfox
         */
        public IoSpringfoxVersionAccessors getSpringfox() {
            return vaccForIoSpringfoxVersionAccessors;
        }

    }

    public static class IoSpringfoxVersionAccessors extends VersionFactory  {

        private final IoSpringfoxSpringfoxVersionAccessors vaccForIoSpringfoxSpringfoxVersionAccessors = new IoSpringfoxSpringfoxVersionAccessors(providers, config);
        public IoSpringfoxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.io.springfox.springfox
         */
        public IoSpringfoxSpringfoxVersionAccessors getSpringfox() {
            return vaccForIoSpringfoxSpringfoxVersionAccessors;
        }

    }

    public static class IoSpringfoxSpringfoxVersionAccessors extends VersionFactory  {

        private final IoSpringfoxSpringfoxBootVersionAccessors vaccForIoSpringfoxSpringfoxBootVersionAccessors = new IoSpringfoxSpringfoxBootVersionAccessors(providers, config);
        private final IoSpringfoxSpringfoxSwaggerVersionAccessors vaccForIoSpringfoxSpringfoxSwaggerVersionAccessors = new IoSpringfoxSpringfoxSwaggerVersionAccessors(providers, config);
        public IoSpringfoxSpringfoxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.io.springfox.springfox.boot
         */
        public IoSpringfoxSpringfoxBootVersionAccessors getBoot() {
            return vaccForIoSpringfoxSpringfoxBootVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.io.springfox.springfox.swagger
         */
        public IoSpringfoxSpringfoxSwaggerVersionAccessors getSwagger() {
            return vaccForIoSpringfoxSpringfoxSwaggerVersionAccessors;
        }

    }

    public static class IoSpringfoxSpringfoxBootVersionAccessors extends VersionFactory  {

        public IoSpringfoxSpringfoxBootVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: io.springfox.springfox.boot.starter (3.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getStarter() { return getVersion("io.springfox.springfox.boot.starter"); }

    }

    public static class IoSpringfoxSpringfoxSwaggerVersionAccessors extends VersionFactory  {

        public IoSpringfoxSpringfoxSwaggerVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: io.springfox.springfox.swagger.ui (3.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getUi() { return getVersion("io.springfox.springfox.swagger.ui"); }

    }

    public static class JavaxVersionAccessors extends VersionFactory  {

        private final JavaxInjectVersionAccessors vaccForJavaxInjectVersionAccessors = new JavaxInjectVersionAccessors(providers, config);
        private final JavaxServletVersionAccessors vaccForJavaxServletVersionAccessors = new JavaxServletVersionAccessors(providers, config);
        public JavaxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.javax.inject
         */
        public JavaxInjectVersionAccessors getInject() {
            return vaccForJavaxInjectVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.javax.servlet
         */
        public JavaxServletVersionAccessors getServlet() {
            return vaccForJavaxServletVersionAccessors;
        }

    }

    public static class JavaxInjectVersionAccessors extends VersionFactory  {

        private final JavaxInjectJavaxVersionAccessors vaccForJavaxInjectJavaxVersionAccessors = new JavaxInjectJavaxVersionAccessors(providers, config);
        public JavaxInjectVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.javax.inject.javax
         */
        public JavaxInjectJavaxVersionAccessors getJavax() {
            return vaccForJavaxInjectJavaxVersionAccessors;
        }

    }

    public static class JavaxInjectJavaxVersionAccessors extends VersionFactory  {

        public JavaxInjectJavaxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: javax.inject.javax.inject (1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getInject() { return getVersion("javax.inject.javax.inject"); }

    }

    public static class JavaxServletVersionAccessors extends VersionFactory  {

        public JavaxServletVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: javax.servlet.jstl (1.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJstl() { return getVersion("javax.servlet.jstl"); }

    }

    public static class JunitVersionAccessors extends VersionFactory  {

        public JunitVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: junit.junit (4.13.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJunit() { return getVersion("junit.junit"); }

    }

    public static class OrgVersionAccessors extends VersionFactory  {

        private final OrgApacheVersionAccessors vaccForOrgApacheVersionAccessors = new OrgApacheVersionAccessors(providers, config);
        private final OrgAspectjVersionAccessors vaccForOrgAspectjVersionAccessors = new OrgAspectjVersionAccessors(providers, config);
        private final OrgMybatisVersionAccessors vaccForOrgMybatisVersionAccessors = new OrgMybatisVersionAccessors(providers, config);
        private final OrgProjectlombokVersionAccessors vaccForOrgProjectlombokVersionAccessors = new OrgProjectlombokVersionAccessors(providers, config);
        private final OrgSlf4jVersionAccessors vaccForOrgSlf4jVersionAccessors = new OrgSlf4jVersionAccessors(providers, config);
        private final OrgSpringframeworkVersionAccessors vaccForOrgSpringframeworkVersionAccessors = new OrgSpringframeworkVersionAccessors(providers, config);
        public OrgVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache
         */
        public OrgApacheVersionAccessors getApache() {
            return vaccForOrgApacheVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.aspectj
         */
        public OrgAspectjVersionAccessors getAspectj() {
            return vaccForOrgAspectjVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.mybatis
         */
        public OrgMybatisVersionAccessors getMybatis() {
            return vaccForOrgMybatisVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.projectlombok
         */
        public OrgProjectlombokVersionAccessors getProjectlombok() {
            return vaccForOrgProjectlombokVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.slf4j
         */
        public OrgSlf4jVersionAccessors getSlf4j() {
            return vaccForOrgSlf4jVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.springframework
         */
        public OrgSpringframeworkVersionAccessors getSpringframework() {
            return vaccForOrgSpringframeworkVersionAccessors;
        }

    }

    public static class OrgApacheVersionAccessors extends VersionFactory  {

        private final OrgApacheCommonsVersionAccessors vaccForOrgApacheCommonsVersionAccessors = new OrgApacheCommonsVersionAccessors(providers, config);
        private final OrgApacheTomcatVersionAccessors vaccForOrgApacheTomcatVersionAccessors = new OrgApacheTomcatVersionAccessors(providers, config);
        public OrgApacheVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache.commons
         */
        public OrgApacheCommonsVersionAccessors getCommons() {
            return vaccForOrgApacheCommonsVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.apache.tomcat
         */
        public OrgApacheTomcatVersionAccessors getTomcat() {
            return vaccForOrgApacheTomcatVersionAccessors;
        }

    }

    public static class OrgApacheCommonsVersionAccessors extends VersionFactory  {

        private final OrgApacheCommonsCommonsVersionAccessors vaccForOrgApacheCommonsCommonsVersionAccessors = new OrgApacheCommonsCommonsVersionAccessors(providers, config);
        public OrgApacheCommonsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache.commons.commons
         */
        public OrgApacheCommonsCommonsVersionAccessors getCommons() {
            return vaccForOrgApacheCommonsCommonsVersionAccessors;
        }

    }

    public static class OrgApacheCommonsCommonsVersionAccessors extends VersionFactory  {

        public OrgApacheCommonsCommonsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.apache.commons.commons.dbcp2 (2.9.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getDbcp2() { return getVersion("org.apache.commons.commons.dbcp2"); }

    }

    public static class OrgApacheTomcatVersionAccessors extends VersionFactory  {

        private final OrgApacheTomcatEmbedVersionAccessors vaccForOrgApacheTomcatEmbedVersionAccessors = new OrgApacheTomcatEmbedVersionAccessors(providers, config);
        public OrgApacheTomcatVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache.tomcat.embed
         */
        public OrgApacheTomcatEmbedVersionAccessors getEmbed() {
            return vaccForOrgApacheTomcatEmbedVersionAccessors;
        }

    }

    public static class OrgApacheTomcatEmbedVersionAccessors extends VersionFactory  {

        private final OrgApacheTomcatEmbedTomcatVersionAccessors vaccForOrgApacheTomcatEmbedTomcatVersionAccessors = new OrgApacheTomcatEmbedTomcatVersionAccessors(providers, config);
        public OrgApacheTomcatEmbedVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache.tomcat.embed.tomcat
         */
        public OrgApacheTomcatEmbedTomcatVersionAccessors getTomcat() {
            return vaccForOrgApacheTomcatEmbedTomcatVersionAccessors;
        }

    }

    public static class OrgApacheTomcatEmbedTomcatVersionAccessors extends VersionFactory  {

        private final OrgApacheTomcatEmbedTomcatEmbedVersionAccessors vaccForOrgApacheTomcatEmbedTomcatEmbedVersionAccessors = new OrgApacheTomcatEmbedTomcatEmbedVersionAccessors(providers, config);
        public OrgApacheTomcatEmbedTomcatVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.apache.tomcat.embed.tomcat.embed
         */
        public OrgApacheTomcatEmbedTomcatEmbedVersionAccessors getEmbed() {
            return vaccForOrgApacheTomcatEmbedTomcatEmbedVersionAccessors;
        }

    }

    public static class OrgApacheTomcatEmbedTomcatEmbedVersionAccessors extends VersionFactory  {

        public OrgApacheTomcatEmbedTomcatEmbedVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.apache.tomcat.embed.tomcat.embed.jasper (9.0.82)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJasper() { return getVersion("org.apache.tomcat.embed.tomcat.embed.jasper"); }

    }

    public static class OrgAspectjVersionAccessors extends VersionFactory  {

        public OrgAspectjVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.aspectj.aspectjrt (1.9.7)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getAspectjrt() { return getVersion("org.aspectj.aspectjrt"); }

            /**
             * Returns the version associated to this alias: org.aspectj.aspectjweaver (1.9.7)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getAspectjweaver() { return getVersion("org.aspectj.aspectjweaver"); }

    }

    public static class OrgMybatisVersionAccessors extends VersionFactory  {

        private final OrgMybatisSpringVersionAccessors vaccForOrgMybatisSpringVersionAccessors = new OrgMybatisSpringVersionAccessors(providers, config);
        public OrgMybatisVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.mybatis.spring
         */
        public OrgMybatisSpringVersionAccessors getSpring() {
            return vaccForOrgMybatisSpringVersionAccessors;
        }

    }

    public static class OrgMybatisSpringVersionAccessors extends VersionFactory  {

        private final OrgMybatisSpringBootVersionAccessors vaccForOrgMybatisSpringBootVersionAccessors = new OrgMybatisSpringBootVersionAccessors(providers, config);
        public OrgMybatisSpringVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.mybatis.spring.boot
         */
        public OrgMybatisSpringBootVersionAccessors getBoot() {
            return vaccForOrgMybatisSpringBootVersionAccessors;
        }

    }

    public static class OrgMybatisSpringBootVersionAccessors extends VersionFactory  {

        private final OrgMybatisSpringBootMybatisVersionAccessors vaccForOrgMybatisSpringBootMybatisVersionAccessors = new OrgMybatisSpringBootMybatisVersionAccessors(providers, config);
        public OrgMybatisSpringBootVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.mybatis.spring.boot.mybatis
         */
        public OrgMybatisSpringBootMybatisVersionAccessors getMybatis() {
            return vaccForOrgMybatisSpringBootMybatisVersionAccessors;
        }

    }

    public static class OrgMybatisSpringBootMybatisVersionAccessors extends VersionFactory  {

        private final OrgMybatisSpringBootMybatisSpringVersionAccessors vaccForOrgMybatisSpringBootMybatisSpringVersionAccessors = new OrgMybatisSpringBootMybatisSpringVersionAccessors(providers, config);
        public OrgMybatisSpringBootMybatisVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.mybatis.spring.boot.mybatis.spring
         */
        public OrgMybatisSpringBootMybatisSpringVersionAccessors getSpring() {
            return vaccForOrgMybatisSpringBootMybatisSpringVersionAccessors;
        }

    }

    public static class OrgMybatisSpringBootMybatisSpringVersionAccessors extends VersionFactory  {

        private final OrgMybatisSpringBootMybatisSpringBootVersionAccessors vaccForOrgMybatisSpringBootMybatisSpringBootVersionAccessors = new OrgMybatisSpringBootMybatisSpringBootVersionAccessors(providers, config);
        public OrgMybatisSpringBootMybatisSpringVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.mybatis.spring.boot.mybatis.spring.boot
         */
        public OrgMybatisSpringBootMybatisSpringBootVersionAccessors getBoot() {
            return vaccForOrgMybatisSpringBootMybatisSpringBootVersionAccessors;
        }

    }

    public static class OrgMybatisSpringBootMybatisSpringBootVersionAccessors extends VersionFactory  {

        public OrgMybatisSpringBootMybatisSpringBootVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.mybatis.spring.boot.mybatis.spring.boot.starter (2.1.3)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getStarter() { return getVersion("org.mybatis.spring.boot.mybatis.spring.boot.starter"); }

    }

    public static class OrgProjectlombokVersionAccessors extends VersionFactory  {

        public OrgProjectlombokVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.projectlombok.lombok (1.18.30)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLombok() { return getVersion("org.projectlombok.lombok"); }

    }

    public static class OrgSlf4jVersionAccessors extends VersionFactory  {

        private final OrgSlf4jSlf4jVersionAccessors vaccForOrgSlf4jSlf4jVersionAccessors = new OrgSlf4jSlf4jVersionAccessors(providers, config);
        public OrgSlf4jVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.slf4j.slf4j
         */
        public OrgSlf4jSlf4jVersionAccessors getSlf4j() {
            return vaccForOrgSlf4jSlf4jVersionAccessors;
        }

    }

    public static class OrgSlf4jSlf4jVersionAccessors extends VersionFactory  {

        public OrgSlf4jSlf4jVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.slf4j.slf4j.api (1.7.36)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getApi() { return getVersion("org.slf4j.slf4j.api"); }

    }

    public static class OrgSpringframeworkVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkBootVersionAccessors vaccForOrgSpringframeworkBootVersionAccessors = new OrgSpringframeworkBootVersionAccessors(providers, config);
        private final OrgSpringframeworkSpringVersionAccessors vaccForOrgSpringframeworkSpringVersionAccessors = new OrgSpringframeworkSpringVersionAccessors(providers, config);
        public OrgSpringframeworkVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.springframework.boot
         */
        public OrgSpringframeworkBootVersionAccessors getBoot() {
            return vaccForOrgSpringframeworkBootVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.org.springframework.spring
         */
        public OrgSpringframeworkSpringVersionAccessors getSpring() {
            return vaccForOrgSpringframeworkSpringVersionAccessors;
        }

    }

    public static class OrgSpringframeworkBootVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkBootSpringVersionAccessors vaccForOrgSpringframeworkBootSpringVersionAccessors = new OrgSpringframeworkBootSpringVersionAccessors(providers, config);
        public OrgSpringframeworkBootVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.springframework.boot.spring
         */
        public OrgSpringframeworkBootSpringVersionAccessors getSpring() {
            return vaccForOrgSpringframeworkBootSpringVersionAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkBootSpringBootVersionAccessors vaccForOrgSpringframeworkBootSpringBootVersionAccessors = new OrgSpringframeworkBootSpringBootVersionAccessors(providers, config);
        public OrgSpringframeworkBootSpringVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.org.springframework.boot.spring.boot
         */
        public OrgSpringframeworkBootSpringBootVersionAccessors getBoot() {
            return vaccForOrgSpringframeworkBootSpringBootVersionAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkBootSpringBootStarterVersionAccessors vaccForOrgSpringframeworkBootSpringBootStarterVersionAccessors = new OrgSpringframeworkBootSpringBootStarterVersionAccessors(providers, config);
        public OrgSpringframeworkBootSpringBootVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.springframework.boot.spring.boot.devtools (2.7.17)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getDevtools() { return getVersion("org.springframework.boot.spring.boot.devtools"); }

        /**
         * Returns the group of versions at versions.org.springframework.boot.spring.boot.starter
         */
        public OrgSpringframeworkBootSpringBootStarterVersionAccessors getStarter() {
            return vaccForOrgSpringframeworkBootSpringBootStarterVersionAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootStarterVersionAccessors extends VersionFactory  {

        public OrgSpringframeworkBootSpringBootStarterVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.springframework.boot.spring.boot.starter.mail (2.7.17)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getMail() { return getVersion("org.springframework.boot.spring.boot.starter.mail"); }

            /**
             * Returns the version associated to this alias: org.springframework.boot.spring.boot.starter.test (2.7.17)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getTest() { return getVersion("org.springframework.boot.spring.boot.starter.test"); }

            /**
             * Returns the version associated to this alias: org.springframework.boot.spring.boot.starter.web (2.7.17)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getWeb() { return getVersion("org.springframework.boot.spring.boot.starter.web"); }

            /**
             * Returns the version associated to this alias: org.springframework.boot.spring.boot.starter.websocket (2.7.17)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getWebsocket() { return getVersion("org.springframework.boot.spring.boot.starter.websocket"); }

    }

    public static class OrgSpringframeworkSpringVersionAccessors extends VersionFactory  {

        public OrgSpringframeworkSpringVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: org.springframework.spring.tx (5.3.30)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getTx() { return getVersion("org.springframework.spring.tx"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
