FROM centos

# MAINTAINER
MAINTAINER cqf

RUN mkdir /opt/consul
WORKDIR /opt/consul

ADD consul /opt/consul
ADD config-server.json /opt/consul

WORKDIR /opt/consul
