FROM java:alpine
MAINTAINER liushaohui777@163.com
ENV TZ=Asia/Shanghai
ENV LANG C.UTF-8
ENV NLS_LANG "SIMPLIFIED CHINESE"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8086
ENV JAVA_OPT="-Xmx128m -Xms128m"
ADD  code-gengrate-1.0.0.jar   app.jar
ENTRYPOINT java $JAVA_OPT -jar app.jar
