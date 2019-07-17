FROM websphere-liberty:19.0.0.3-microProfile1
MAINTAINER IBM

USER root
RUN mkdir -p /opt/ibm/wlp/usr/servers/defaultServer/resources/security \
    && chown -R 1001:0 /opt/ibm/wlp/usr/servers/defaultServer/resources/security \
    && mkdir /opt/ibm/wlp/databases \
    && chown -R 1001:0 /opt/ibm/wlp/databases \
    && rm -rf /opt/ibm/wlp/usr/servers/defaultServer/configDropins/defaults \
    && mkdir -p /opt/ibm/MobileFirst/licenses \
    && chown -R 1001:0 /opt/ibm/MobileFirst/licenses
USER 1001


# Copy MFP Server war files
COPY mfpf-libs/mfp-server.war  /opt/ibm/wlp/usr/servers/defaultServer/apps
COPY mfpf-libs/mfp-admin-service.war  /opt/ibm/wlp/usr/servers/defaultServer/apps
COPY mfpf-libs/mfp-admin-ui.war  /opt/ibm/wlp/usr/servers/defaultServer/apps
COPY mfpf-libs/mfp-config-service.war  /opt/ibm/wlp/usr/servers/defaultServer/apps
COPY mfpf-libs/mfp-push-service.war  /opt/ibm/wlp/usr/servers/defaultServer/apps
COPY mfpf-libs/mfp-server-swagger-ui.war  /opt/ibm/wlp/usr/servers/defaultServer/apps
COPY mfpf-libs/applicationcenter.war  /opt/ibm/wlp/usr/servers/defaultServer/apps
COPY mfpf-libs/appcenterconsole.war  /opt/ibm/wlp/usr/servers/defaultServer/apps


#Copy Analytics ear
COPY mfpf-libs/analytics.ear  /opt/ibm/wlp/usr/servers/defaultServer/apps

# Copy dropins
COPY mfpf-libs/mfp-dev-artifacts.war /opt/ibm/wlp/usr/servers/defaultServer/dropins 

COPY licenses/ /opt/ibm/MobileFirst/licenses
# Copy Precreated databases
COPY databases/ /opt/ibm/wlp/databases
USER root
RUN chown -R 1001:0 /opt/ibm/wlp/databases
USER 1001

COPY keystore.xml /opt/ibm/wlp/usr/servers/defaultServer/configDropins/overrides
COPY server.xml /opt/ibm/wlp/usr/servers/defaultServer
COPY jvm.options /opt/ibm/wlp/usr/servers/defaultServer
COPY mfpf-libs/derby.jar /opt/ibm/wlp/usr/shared/resources
COPY bootstrap.properties /opt/ibm/wlp/usr/servers/defaultServer
# Override keystore file
COPY key.jks /opt/ibm/wlp/usr/servers/defaultServer/resources/security

