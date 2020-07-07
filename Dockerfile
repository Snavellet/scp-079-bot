FROM amazoncorretto:11

RUN mkdir /root/rick-sanchez-bot

WORKDIR /root/rick-sanchez-bot

ADD . .

RUN chmod +x ./build-scripts/run.sh

ENTRYPOINT ["./build-scripts/run.sh"]
