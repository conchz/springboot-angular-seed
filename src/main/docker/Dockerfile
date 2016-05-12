FROM java:8-jre-alpine

MAINTAINER Zongzhi Bai <dolphineor@gmail.com>

ADD springboot-angular-seed.jar /opt/docker/

WORKDIR /opt/docker

CMD ["java", "-server", "-Xms1g", "-Xmx1g", "-Dfile.encoding=UTF-8", "-jar", "springboot-angular-seed.jar"]