FROM websphere-liberty:19.0.0.3-microProfile1
MAINTAINER IBM

USER root
RUN mkdir -p /opt/ibm/wlp/usr/servers/defaultServer/resources/security \
    && chown -R 1001:0 /opt/ibm/wlp/usr/servers/defaultServer/resources/security \
    && rm -rf /opt/ibm/wlp/usr/servers/defaultServer/configDropins/defaults \
    && mkdir /opt/ibm/wlp/usr/servers/defaultServer/logs \
    && chown -R 1001:0 /opt/ibm/wlp/usr/servers/defaultServer/logs \
    && mkdir -p /opt/ibm/MobileFirst/licenses \
    && chown -R 1001:0 /opt/ibm/MobileFirst/licenses
USER 1001


# Copy MFP Server war files
COPY mfpf-libs/analytics.ear  /opt/ibm/wlp/usr/servers/defaultServer/apps

COPY licenses/ /opt/ibm/MobileFirst/licenses
# Copy Precreated databases

COPY keystore.xml /opt/ibm/wlp/usr/servers/defaultServer/configDropins/overrides
COPY server.xml /opt/ibm/wlp/usr/servers/defaultServer
COPY jvm.options /opt/ibm/wlp/usr/servers/defaultServer
COPY bootstrap.properties /opt/ibm/wlp/usr/servers/defaultServer
# Override keystore file
COPY key.jks /opt/ibm/wlp/usr/servers/defaultServer/resources/security

