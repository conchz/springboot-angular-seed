FROM java:8-jre

MAINTAINER Zongzhi Bai <dolphineor@gmail.com>

COPY startup /
COPY springboot-angular-seed.jar /opt

RUN chmod +x /startup

ENTRYPOINT ["/startup"]