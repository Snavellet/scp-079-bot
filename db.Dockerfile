FROM mysql/mysql-server

ARG MYSQL_ROOT_PASSWORD
ARG MYSQL_USER
ARG MYSQL_PASSWORD

ENV MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD \
    MYSQL_USER=$MYSQL_USER \
    MYSQL_PASSWORD=$MYSQL_PASSWORD
