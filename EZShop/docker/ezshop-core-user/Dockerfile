FROM centos

# MAINTAINER
#MAINTAINER cqf
LABEL maintainer="cqf"

RUN echo "Asia/Shanghai" > /etc/timezone;dpkg-reconfigure -f noninteractive tzdata

RUN mkdir /opt/domains
RUN mkdir /opt/software
RUN mkdir /opt/consul
RUN mkdir /opt/wars
RUN mkdir /opt/sh

ADD /opt/software/jdk-8u181-linux-x64.tar /opt/software
ADD /opt/software/apache-tomcat-8.5.33.tar /opt/software
ADD /opt/software/config-server.json /opt/consul
ADD /opt/software/config-client.json /opt/consul
ADD /opt/software/consul /opt/consul
ADD /opt/sh/domain.sh /opt/sh
ADD /opt/sh/jdk.sh /opt/sh
ADD /opt/wars/ezshop-core-user.war /opt/wars

RUN cd /opt/sh
RUN ./jdk.sh
RUN ./domain.sh -c user_domain ezshop-core-user

WORKDIR /opt/sh
