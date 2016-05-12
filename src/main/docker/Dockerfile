FROM java:8-jre-alpine

MAINTAINER Zongzhi Bai <dolphineor@gmail.com>

ADD springboot-angular-seed.zip /opt/docker/

COPY startup.sh /
RUN chmod 0755 /startup.sh

ENTRYPOINT ["/startup.sh"]